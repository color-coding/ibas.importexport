package org.colorcoding.ibas.importexport.transformer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.colorcoding.ibas.bobas.data.DateTime;
import org.colorcoding.ibas.bobas.data.emYesNo;
import org.colorcoding.ibas.bobas.i18n.I18N;
import org.colorcoding.ibas.bobas.message.Logger;
import org.colorcoding.ibas.bobas.message.MessageLevel;
import org.colorcoding.ibas.importexport.MyConfiguration;
import org.colorcoding.ibas.importexport.bo.exporttemplate.IExportTemplateAppendix;
import org.colorcoding.ibas.importexport.bo.exporttemplate.IExportTemplateItem;
import org.colorcoding.ibas.importexport.data.emDataSourceType;
import org.colorcoding.ibas.importexport.data.emJustificationHorizontal;
import org.colorcoding.ibas.importexport.data.emJustificationVertical;
import org.colorcoding.ibas.importexport.data.emLineStyle;
import org.colorcoding.ibas.importexport.data.emTextSegment;
import org.colorcoding.ibas.importexport.data.emTextStyle;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import net.minidev.json.JSONArray;

/**
 * 业务对象转换html文件
 * 
 * @author Niuren.Zhu
 *
 */
@TransformerInfo(name = "TO_FILE_HTML", template = true)
public class TransformerHtml extends TemplateTransformer {

	public final static String DISPLAY_ELEMENT_IMAGE = "IMG";
	public final static String DISPLAY_ELEMENT_TEXT = "TEXT";

	private DocumentContext dataContext;

	private final DocumentContext getDataContext() {
		if (this.dataContext == null) {
			this.dataContext = JsonPath.parse(this.getInputData());
		}
		return dataContext;
	}

	@Override
	@SuppressWarnings("unchecked")
	protected <T> T dataValue(String name, T defaults) {
		try {
			Matcher matcher = Pattern.compile(MyConfiguration.VARIABLE_PATTERN).matcher(name);
			while (matcher.find()) {
				// 带格式名称${}
				String pName = matcher.group(0);
				Object pValue = this.paramValue(pName, null);
				if (pValue != null) {
					name = name.replace(pName, String.valueOf(pValue));
				}
			}
			if (name.indexOf("[]") > 0) {
				int index = this.paramValue(PARAM_DATA_INDEX, -1);
				if (index < 0) {
					return defaults;
				}
				Map<String, Integer> indexMap = DATA_INDEXES.get(index - 1);
				for (String item : name.split("\\.")) {
					if (item != null && item.endsWith("[]")) {
						String key = item.substring(0, item.length() - 2);
						Integer value = indexMap.get(key);
						if (value != null && value >= 0) {
							name = name.replace(item, key + String.format("[%s]", value));
						}
					}
				}
			}
			Object value = this.getDataContext().read(name);
			if (value == null) {
				return defaults;
			}
			Logger.log(MessageLevel.DEBUG, "transformer: data [%s], value [%s].", name, value);
			if (value instanceof JSONArray) {
				Double total = null;
				for (Object item : (JSONArray) value) {
					if (item instanceof Integer) {
						if (total == null) {
							total = 0.0d;
						}
						total += Double.parseDouble(String.valueOf(item));
					} else if (item instanceof Double) {
						if (total == null) {
							total = 0.0d;
						}
						total += (double) item;
					}
				}
				if (total != null) {
					return (T) total;
				}
			}
			return (T) value;
		} catch (Exception e) {
			Logger.log(MessageLevel.WARN, e);
			return defaults;
		}
	}

	@Override
	public void transform() throws TransformException {
		if (this.getTemplate() == null) {
			throw new TransformException(I18N.prop("msg_ie_no_template"));
		}
		if (this.getInputData() == null) {
			throw new TransformException(I18N.prop("msg_ie_no_input_data"));
		}
		this.init();
		File file = new File(String.format("%s%s.html", this.getWorkFolder(), UUID.randomUUID().toString()));
		try (OutputStream stream = new FileOutputStream(file)) {
			try (OutputStreamWriter writer = new OutputStreamWriter(stream, "utf-8")) {
				if (!file.getParentFile().exists()) {
					file.mkdirs();
				}
				if (!file.exists()) {
					file.createNewFile();
				}
				this.darwPage(writer);
				writer.flush();
				this.setOutputData(new File[] { file });
			}
		} catch (IOException e) {
			throw new TransformException(e);
		}
	}

	/**
	 * 数据的索引
	 */
	private List<Map<String, Integer>> DATA_INDEXES;

	private List<Map<String, Integer>> createDataIndexes(String path) {
		int index = path.indexOf("[]");
		if (index >= 0) {
			String cPath = path.substring(0, index);
			String[] tmps = cPath.split("\\.");
			String name = tmps[tmps.length - 1];
			int size = this.dataValue(String.format("%s.length()", cPath), 0);
			List<Map<String, Integer>> indexMaps = new ArrayList<>();
			for (int i = 0; i < size; i++) {
				cPath = path.replace("[]", String.format("[%s]", i));
				for (Map<String, Integer> item : this.createDataIndexes(cPath)) {
					item.put(name, i);
					indexMaps.add(item);
				}
			}
			return indexMaps;
		} else {
			String[] tmps = path.split("\\.");
			String name = tmps[tmps.length - 1];
			int size = this.dataValue(String.format("%s.length()", path), 0);
			List<Map<String, Integer>> indexMaps = new ArrayList<>();
			for (int i = 0; i < size; i++) {
				Map<String, Integer> indexMap = new HashMap<>();
				indexMap.put(name, i);
				indexMaps.add(indexMap);
			}
			return indexMaps;
		}
	}

	/**
	 * 初始化，分析数据构建变量
	 */
	protected void init() throws TransformException {
		// 初始变量值
		this.newParam(PARAM_TIME_NOW, DateTime.getNow());
		this.newParam(PARAM_DATA_INDEX, 0);
		// 获取数据长度
		int size = 0;
		for (IExportTemplateItem item : this.getTemplate().getRepetitions()) {
			if (item.getSourceType() != emDataSourceType.PATH) {
				continue;
			}
			String itemString = item.getItemString();
			if (itemString == null || itemString.isEmpty()) {
				continue;
			}
			int index = itemString.lastIndexOf("[]");
			if (index < 0) {
				continue;
			}
			this.DATA_INDEXES = this.createDataIndexes(itemString.substring(0, index));
			if (this.DATA_INDEXES != null && this.DATA_INDEXES.size() > 0) {
				size = this.DATA_INDEXES.size();
				this.newParam(PARAM_DATA_SIZE, size);
				this.newParam(PARAM_DATA_INDEX, 1);
				break;
			}
		}
		// 计算页数
		int count = 1;
		int pageHeigh = this.getTemplate().getHeight();
		pageHeigh -= this.getTemplate().getMarginTop();
		pageHeigh -= this.getTemplate().getMarginBottom();
		// 页眉区
		if (this.getTemplate().getPageHeaderHeight() > 0) {
			pageHeigh -= this.getTemplate().getPageHeaderHeight();
			pageHeigh -= this.getTemplate().getMarginArea();
		}
		// 页脚区
		if (this.getTemplate().getPageFooterHeight() > 0) {
			pageHeigh -= this.getTemplate().getMarginArea();
			pageHeigh -= this.getTemplate().getPageFooterHeight();
		}
		// 判断是否有空余位置
		if (pageHeigh < 0) {
			throw new TransformException(I18N.prop("msg_ie_template_size_not_enough", this.getTemplate().getName()));
		}
		int content = pageHeigh;
		for (int i = 1; i <= size; i++) {
			// 新页
			if (content == pageHeigh) {
				// 第一页
				if (count == 1) {
					// 开始区
					if (this.getTemplate().getStartSectionHeight() > 0) {
						content -= this.getTemplate().getStartSectionHeight();
						content -= this.getTemplate().getMarginArea();
					}
				}
				// 重复头区
				if (this.getTemplate().getRepetitionHeaderHeight() > 0) {
					content -= this.getTemplate().getRepetitionHeaderHeight();
				}
				// 重复脚区
				if (this.getTemplate().getRepetitionFooterHeight() > 0) {
					content -= this.getTemplate().getRepetitionFooterHeight();
				}
			}
			content -= this.getTemplate().getRepetitionHeight();
			if (content <= 0 || content < this.getTemplate().getRepetitionHeight()) {
				// 空间用完，新起页
				if (i < size) {
					this.newParam(String.format(PARAM_TEMPLATE_PAGE_DATA_INDEX, count), i);
					count++;
					content = pageHeigh;
				}
			}
			// 最后数据
			if (i == size) {
				// 结束区
				if (this.getTemplate().getEndSectionHeight() > 0) {
					content -= this.getTemplate().getMarginArea();
					content -= this.getTemplate().getEndSectionHeight();
				}
				if (content <= 0) {
					// 空间用完，新起页
					this.newParam(String.format(PARAM_TEMPLATE_PAGE_DATA_INDEX, count), i);
					count++;
				}
			}
		}
		// 正文页数
		this.newParam(PARAM_PAGE_MAIN_TOTAL, count);
		// 增加附录页数
		if (!this.getTemplate().getAppendixs().isEmpty()) {
			count += this.getTemplate().getAppendixs().size();
		}
		// 总页数
		this.newParam(PARAM_PAGE_TOTAL, count);
		this.newParam(PARAM_PAGE_INDEX, 1);
	}

	protected void darwPage(Writer writer) throws IOException {
		writer.write("<!DOCTYPE HTML>");
		writer.write("<html>");

		writer.write("<head>");
		writer.write(String.format("<title>%s</title>", this.getTemplate().getName()));
		writer.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />");
		writer.write("<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">");
		writer.write("<meta charset=\"utf-8\" />");
		writer.write("<meta name=\"referrer\" content=\"origin\" />");
		writer.write("<meta name=\"page_template\" content=\"");
		writer.write(this.getTemplate().getObjectKey().toString());
		writer.write("\" ");
		writer.write("/>");
		writer.write("<meta name=\"page_width\" content=\"");
		writer.write(this.getTemplate().getWidth().toString());
		writer.write("\" ");
		writer.write("/>");
		writer.write("<meta name=\"page_height\" content=\"");
		writer.write(this.getTemplate().getHeight().toString());
		writer.write("\" ");
		writer.write("/>");
		if (Integer.compare(this.getTemplate().getDpi(), 0) > 0) {
			writer.write("<meta name=\"page_dpi\" content=\"");
			writer.write(this.getTemplate().getDpi().toString());
			writer.write("\" ");
			writer.write("/>");
		}
		writer.write("<style>");
		writer.write("@media print {");
		// 不打印内容
		writer.write(".no_print { display:none;}");
		writer.write(" ");
		writer.write("@page {");
		writer.write("size: ");
		writer.write("auto");
		writer.write(" ");
		writer.write(";");
		// 默认不打印边距
		writer.write("margin: 0;");
		writer.write("}");
		writer.write("}");
		writer.write("</style>");

		writer.write("</head>");

		writer.write("<body>");

		int pageTop = 0, pageLeft = 0, pageWidth = this.getTemplate().getWidth(),
				pageHeight = this.getTemplate().getHeight();
		// 绘制正文
		for (int page = this.paramValue(PARAM_PAGE_INDEX, 1); page <= this.paramValue(PARAM_PAGE_MAIN_TOTAL,
				1); page++) {
			// 设置当前页变量
			this.newParam(PARAM_PAGE_INDEX, page);
			// 设置当前页数据
			this.newParam(PARAM_PAGE_DATA_BEGIN,
					this.paramValue(String.format(PARAM_TEMPLATE_PAGE_DATA_INDEX, page - 1), 0));
			this.newParam(PARAM_PAGE_DATA_END, this.paramValue(String.format(PARAM_TEMPLATE_PAGE_DATA_INDEX, page),
					this.paramValue(PARAM_DATA_SIZE, 0)));
			// 开始-页
			String pageName = String.format("page_%s", page);
			pageTop = (page - 1) * pageHeight;
			if (page > 1) {
				// 页分隔线
				this.drawPageSeparator(writer, pageName, pageTop, pageWidth);
			}
			int top = 0;
			this.startDiv(writer, pageName, pageLeft, pageTop, pageWidth, pageHeight);
			// 绘制页眉区域
			String areaName = String.format("%s_header", pageName);
			if (this.getTemplate().getPageHeaderHeight() > 0) {
				Logger.log(MessageLevel.DEBUG, "transformer: draw area [%s].", areaName);
				this.startDiv(writer, areaName, this.getTemplate().getPageHeaderLeft(),
						this.getTemplate().getPageHeaderTop(), this.getTemplate().getPageHeaderWidth(),
						this.getTemplate().getPageHeaderHeight());
				this.drawArea(writer, this.getTemplate().getPageHeaders());
				this.endDiv(writer);
				top += this.getTemplate().getPageHeaderHeight();
			}
			// 绘制开始区域
			if (page == 1) {
				// 第一页绘制
				areaName = String.format("%s_startsection", pageName);
				if (this.getTemplate().getStartSectionHeight() > 0) {
					Logger.log(MessageLevel.DEBUG, "transformer: draw area [%s].", areaName);
					top += this.getTemplate().getMarginArea();
					this.startDiv(writer, areaName, this.getTemplate().getStartSectionLeft(), top,
							this.getTemplate().getStartSectionWidth(), this.getTemplate().getStartSectionHeight());
					this.drawArea(writer, this.getTemplate().getStartSections());
					this.endDiv(writer);
					top += this.getTemplate().getStartSectionHeight();
				}
			}
			// 绘制重复区域
			areaName = String.format("%s_repetitions", pageName);
			Logger.log(MessageLevel.DEBUG, "transformer: draw area [%s].", areaName);
			int index = this.paramValue(PARAM_DATA_INDEX, 1), size = this.paramValue(PARAM_DATA_SIZE, 0);
			for (int i = index; i <= size; i++) {
				this.newParam(PARAM_DATA_INDEX, i);
				if (i == index) {
					areaName = String.format("%s_table", pageName);
					top += this.getTemplate().getMarginArea();
					Logger.log(MessageLevel.DEBUG, "transformer: draw area [%s].", areaName);
					this.startDiv(writer, areaName, this.getTemplate().getRepetitionHeaderLeft(), top,
							this.getTemplate().getRepetitionHeaderWidth(), -1);
					this.startTable(writer, areaName, this.getTemplate().getRepetitionHeaders());
					top += this.getTemplate().getRepetitionHeaderHeight();
				}
				if (this.getTemplate().getRepetitionHeight() > 0) {
					this.drawTableRow(writer, this.getTemplate().getRepetitions());
					top += this.getTemplate().getRepetitionHeight();
				}
				if (this.paramValue(String.format(PARAM_TEMPLATE_PAGE_DATA_INDEX, page), -1) == i) {
					if (this.getTemplate().getRepetitionFooterHeight() > 0) {
						this.endTable(writer, this.getTemplate().getRepetitionFooters());
						top += this.getTemplate().getRepetitionFooterHeight();
					} else {
						this.endTable(writer);
					}
					this.endDiv(writer);
					this.newParam(PARAM_DATA_INDEX, ++i);
					break;
				}
				if (i == size) {
					if (this.getTemplate().getRepetitionFooterHeight() > 0) {
						this.endTable(writer, this.getTemplate().getRepetitionFooters());
						top += this.getTemplate().getRepetitionFooterHeight();
					} else {
						this.endTable(writer);
					}
					this.endDiv(writer);
				}
			}
			// 绘制结束区域
			if (page == this.paramValue(PARAM_PAGE_MAIN_TOTAL, 1)) {
				// 最后一页绘制
				areaName = String.format("%s_endsection", pageName);
				if (this.getTemplate().getEndSectionHeight() > 0) {
					Logger.log(MessageLevel.DEBUG, "transformer: draw area [%s].", areaName);
					top += this.getTemplate().getMarginArea();
					this.startDiv(writer, areaName, this.getTemplate().getEndSectionLeft(), top,
							this.getTemplate().getEndSectionWidth(), this.getTemplate().getEndSectionHeight());
					this.drawArea(writer, this.getTemplate().getEndSections());
					this.endDiv(writer);
					top += this.getTemplate().getEndSectionHeight();
				}
			}
			// 绘制页脚区域
			areaName = String.format("%s_footer", pageName);
			if (this.getTemplate().getPageFooterHeight() > 0) {
				Logger.log(MessageLevel.DEBUG, "transformer: draw area [%s].", areaName);
				top += this.getTemplate().getMarginArea();
				this.startDiv(writer, areaName, this.getTemplate().getPageFooterLeft(),
						this.getTemplate().getPageFooterTop(), this.getTemplate().getPageFooterWidth(),
						this.getTemplate().getPageFooterHeight());
				this.drawArea(writer, this.getTemplate().getPageFooters());
				this.endDiv(writer);
			}
			// 结束-页
			this.endDiv(writer);
		}
		int page = this.paramValue(PARAM_PAGE_INDEX, 0);
		// 绘制附录
		for (int index = 0; index < this.getTemplate().getAppendixs().size(); index++) {
			// 设置当前页变量
			page += 1;
			this.newParam(PARAM_PAGE_INDEX, page);
			IExportTemplateAppendix appendix = this.getTemplate().getAppendixs().get(index);
			// 开始-页
			String pageName = String.format("page_%s", page), areaName;
			pageTop = (page - 1) * pageHeight;
			this.drawPageSeparator(writer, pageName, pageTop, pageWidth);
			this.startDiv(writer, pageName, pageLeft, pageTop, pageWidth, pageHeight);
			// 绘制页眉区域
			if (appendix.getPageHeader() == emYesNo.YES) {
				areaName = String.format("%s_header", pageName);
				Logger.log(MessageLevel.DEBUG, "transformer: draw area [%s].", areaName);
				this.startDiv(writer, areaName, this.getTemplate().getPageHeaderLeft(),
						this.getTemplate().getPageHeaderTop(), this.getTemplate().getPageHeaderWidth(),
						this.getTemplate().getPageHeaderHeight());
				this.drawArea(writer, this.getTemplate().getPageHeaders());
				this.endDiv(writer);
			}
			// 绘制附录
			areaName = String.format("%s_appendix", pageName);
			Logger.log(MessageLevel.DEBUG, "transformer: draw area [%s].", areaName);
			this.startDiv(writer, areaName, appendix.getContentLeft(), appendix.getContentTop(),
					appendix.getContentWidth(), appendix.getContentHeight());
			this.drawArea(writer, appendix.getContents());
			this.endDiv(writer);
			// 绘制页脚区域
			if (appendix.getPageFooter() == emYesNo.YES) {
				areaName = String.format("%s_footer", pageName);
				Logger.log(MessageLevel.DEBUG, "transformer: draw area [%s].", areaName);
				this.startDiv(writer, areaName, this.getTemplate().getPageFooterLeft(),
						this.getTemplate().getPageFooterTop(), this.getTemplate().getPageFooterWidth(),
						this.getTemplate().getPageFooterHeight());
				this.drawArea(writer, this.getTemplate().getPageFooters());
				this.endDiv(writer);
			}
			// 结束-页
			this.endDiv(writer);
		}
		writer.write("</body>");
		writer.write("</html>");
	}

	protected void startDiv(Writer writer, String id, int left, int top) throws IOException {
		this.startDiv(writer, id, left, top, -1, -1);
	}

	protected void startDiv(Writer writer, String id, int left, int top, int width, int height) throws IOException {
		this.startDiv(writer, id, left, top, width, height, null, -1, -1, -1, -1, -1, -1, -1);
	}

	protected void startDiv(Writer writer, String id, int left, int top, int width, int height, emLineStyle lineStyle,
			int lineLeft, int lineTop, int lineRight, int lineBottom, int borderRed, int borderGreen, int borderBlue)
			throws IOException {
		writer.write("<div");
		writer.write(" ");
		if (id != null) {
			writer.write("id=\"");
			writer.write(id);
			writer.write("\"");
		}
		writer.write(" ");
		writer.write("style=\"");
		writer.write("position:absolute;");
		writer.write("white-space:normal;overflow:hidden;");
		writer.write("left:");
		writer.write(String.valueOf(left));
		writer.write("px;");
		writer.write("top:");
		writer.write(String.valueOf(top));
		writer.write("px;");
		if (width > 0) {
			writer.write("width:");
			writer.write(String.valueOf(width));
			writer.write("px;");
		}
		if (height > 0) {
			writer.write("height:");
			writer.write(String.valueOf(height));
			writer.write("px;");
		}
		// 边框线条
		if (lineTop > 0 || lineBottom > 0 || lineLeft > 0 || lineRight > 0) {
			writer.write("border-style:");
			writer.write(String.valueOf(lineStyle != null ? lineStyle : emLineStyle.SOLID).toLowerCase());
			writer.write(";");
			if (borderRed > 0 || borderGreen > 0 || borderBlue > 0) {
				writer.write("border-color:rgb(");
				writer.write(String.valueOf(borderRed));
				writer.write(",");
				writer.write(String.valueOf(borderGreen));
				writer.write(",");
				writer.write(String.valueOf(borderBlue));
				writer.write(");");
			} else {
				writer.write("border-color:black;");
			}
			writer.write("border-left-width:");
			writer.write(String.valueOf(lineLeft));
			writer.write("px;");
			writer.write("border-top-width:");
			writer.write(String.valueOf(lineTop));
			writer.write("px;");
			writer.write("border-right-width:");
			writer.write(String.valueOf(lineRight));
			writer.write("px;");
			writer.write("border-bottom-width:");
			writer.write(String.valueOf(lineBottom));
			writer.write("px;");
		}
		writer.write("\"");
		writer.write(" ");
		writer.write(">");
	}

	protected void endDiv(Writer writer) throws IOException {
		writer.write("</div>");
	}

	protected void drawArea(Writer writer, List<IExportTemplateItem> templates) throws IOException {
		if (templates.isEmpty()) {
			return;
		}
		// 内容按左坐标排序
		templates.sort(new Comparator<IExportTemplateItem>() {
			@Override
			public int compare(IExportTemplateItem o1, IExportTemplateItem o2) {
				return o1.getItemLeft() - o2.getItemLeft();
			}
		});
		for (IExportTemplateItem item : templates) {
			if (item.getItemVisible() == emYesNo.NO) {
				continue;
			}
			this.startDiv(writer, null, item.getItemLeft(), item.getItemTop(), item.getItemWidth(),
					item.getItemHeight(), item.getLineStyle(), item.getLineLeft(), item.getLineTop(),
					item.getLineRight(), item.getLineBottom(), item.getBorderRed(), item.getBorderGreen(),
					item.getBorderBlue());
			this.drawElement(writer, item);
			this.endDiv(writer);
		}
	}

	protected void drawElement(Writer writer, IExportTemplateItem template) throws IOException {
		if (DISPLAY_ELEMENT_IMAGE.equalsIgnoreCase(template.getItemType())) {
			writer.write("<img");
			writer.write(" ");
			writer.write("id=\"");
			writer.write(template.getItemID());
			writer.write("\"");
			writer.write(" ");
			writer.write("style=\"");
			writer.write("height:inherit;width:inherit;");
			writer.write("\"");
			writer.write(" ");
			writer.write("src=\"");
			writer.write(this.templateValue(template));
			writer.write("\"");
			writer.write(" ");
			writer.write(
					"onerror=\"this.src='data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHZlcnNpb249IjEuMSI+Cjwvc3ZnPiA=';\"");
			writer.write(" ");
			writer.write(">");
			writer.write("</img>");
		} else {
			int widht = template.getItemWidth();
			int height = template.getItemHeight();
			widht -= template.getLineLeft();
			widht -= template.getLineRight();
			if (template.getJustificationHorizontal() == emJustificationHorizontal.LEFT
					|| template.getJustificationHorizontal() == emJustificationHorizontal.RIGHT) {
				widht -= 2;
			}
			height -= template.getLineTop();
			height -= template.getLineBottom();
			writer.write("<label");
			writer.write(" ");
			writer.write("id=\"");
			writer.write(template.getItemID());
			writer.write("\"");
			writer.write(" ");
			writer.write("style=\"");
			writer.write("display:block;");
			writer.write("height:");
			writer.write(String.valueOf((height)));
			writer.write("px;");
			writer.write("width:");
			writer.write(String.valueOf((widht)));
			writer.write("px;");
			writer.write("white-space:normal;overflow:hidden;");
			if (template.getFontSize() > 0) {
				writer.write("font-size:");
				writer.write(String.valueOf(template.getFontSize()));
				writer.write("px;");
			}
			if (template.getFontName() != null && !template.getFontName().isEmpty()) {
				writer.write("font-family:");
				writer.write(template.getFontName());
				writer.write(";");
			}
			if (template.getTextStyle() == emTextStyle.BOLD || template.getTextStyle() == emTextStyle.BOLD_ITALIC) {
				writer.write("font-weight:bold;");
			}
			if (template.getTextStyle() == emTextStyle.ITALIC || template.getTextStyle() == emTextStyle.BOLD_ITALIC) {
				writer.write("font-style:italic;");
			}
			if (template.getTextSegment() == emTextSegment.WORD) {
				writer.write("word-break:keep-all;");
			} else if (template.getTextSegment() == emTextSegment.CELL) {
				writer.write("word-break:break-all;");
			}
			if (template.getJustificationHorizontal() == emJustificationHorizontal.CENTER) {
				writer.write("text-align:center;");
			} else if (template.getJustificationHorizontal() == emJustificationHorizontal.LEFT) {
				writer.write("text-align:left;");
				writer.write("padding-left:2px;");
			} else if (template.getJustificationHorizontal() == emJustificationHorizontal.RIGHT) {
				writer.write("text-align:right;");
				writer.write("padding-right:2px;");
			}
			if (template.getJustificationVertical() == emJustificationVertical.CENTER) {
				if (template.getTextSegment() == emTextSegment.CELL) {
					writer.write("display: table-cell;");
					writer.write("vertical-align: middle;");
				} else {
					writer.write("line-height:");
					writer.write(String.valueOf((height)));
					writer.write("px;");
				}
			} else if (template.getJustificationVertical() == emJustificationVertical.BOTTOM) {
				writer.write("position:relative;");
				writer.write("line-height:");
				writer.write(String.valueOf((height)));
				writer.write("px;");
				writer.write("bottom:");
				writer.write("-");
				writer.write(String.valueOf(template.getFontSize()));
				writer.write("px;");
			}
			writer.write("\"");
			writer.write(" ");
			writer.write(">");
			writer.write(this.templateValue(template));
			writer.write("</label>");
		}
	}

	protected void startTable(Writer writer, String id, List<IExportTemplateItem> templates) throws IOException {
		writer.write("<");
		writer.write("table");
		writer.write(" ");
		writer.write("id=\"");
		writer.write(id);
		writer.write("\"");
		writer.write(" ");
		writer.write("cellspacing=\"0\" cellpadding=\"0\"");
		writer.write(" ");
		writer.write("style=\"");
		writer.write("table-layout:fixed;border-collapse:collapse;");
		writer.write("border:1px solid black;");
		writer.write("\"");
		writer.write(" ");
		writer.write(">");
		// 绘制列名
		// 内容按左坐标排序
		templates.sort(new Comparator<IExportTemplateItem>() {
			@Override
			public int compare(IExportTemplateItem o1, IExportTemplateItem o2) {
				return o1.getItemLeft() - o2.getItemLeft();
			}
		});
		writer.write("<tr>");
		for (IExportTemplateItem item : templates) {
			if (item.getItemVisible() == emYesNo.NO) {
				continue;
			}
			writer.write("<th");
			writer.write(" ");
			writer.write("style=\"");
			writer.write("width:");
			writer.write(String.valueOf(item.getItemWidth() - item.getLineLeft() - item.getLineRight()));
			writer.write("px;");
			writer.write("height:");
			writer.write(String.valueOf(item.getItemHeight() - item.getLineTop() - item.getLineBottom()));
			writer.write("px;");
			writer.write("border:1px solid black;");
			writer.write("\"");
			writer.write(" ");
			writer.write(">");
			this.drawElement(writer, item);
			writer.write("</th>");
		}
		writer.write("</tr>");
	}

	protected void endTable(Writer writer) throws IOException {
		writer.write("</table>");
	}

	protected void endTable(Writer writer, List<IExportTemplateItem> templates) throws IOException {
		// 内容按左坐标排序
		templates.sort(new Comparator<IExportTemplateItem>() {
			@Override
			public int compare(IExportTemplateItem o1, IExportTemplateItem o2) {
				return o1.getItemLeft() - o2.getItemLeft();
			}
		});
		writer.write("<tr>");
		writer.write("<td");
		writer.write(" ");
		writer.write("style=\"");
		writer.write("height:");
		writer.write(String.valueOf(this.getTemplate().getRepetitionFooterHeight()));
		writer.write("px;");
		writer.write("border:1px solid black;");
		writer.write("\"");
		writer.write(" ");
		writer.write("colspan=\"");
		writer.write(String.valueOf(this.getTemplate().getRepetitions().size()));
		writer.write("\"");
		writer.write(" ");
		writer.write(">");
		writer.write("<div");
		writer.write(" ");
		writer.write("style=\"");
		writer.write("position:relative;");
		writer.write("width:100%;");
		writer.write("height:100%;");
		writer.write("\"");
		writer.write(" ");
		writer.write(">");
		for (IExportTemplateItem item : templates) {
			if (item.getItemVisible() == emYesNo.NO) {
				continue;
			}
			this.startDiv(writer, null, item.getItemLeft(), item.getItemTop(), item.getItemWidth(),
					item.getItemHeight());
			this.drawElement(writer, item);
			this.endDiv(writer);
		}
		writer.write("</div>");
		writer.write("</td>");
		writer.write("</tr>");
		this.endTable(writer);
	}

	protected void drawTableRow(Writer writer, List<IExportTemplateItem> templates) throws IOException {
		// 内容按左坐标排序
		templates.sort(new Comparator<IExportTemplateItem>() {
			@Override
			public int compare(IExportTemplateItem o1, IExportTemplateItem o2) {
				return o1.getItemLeft() - o2.getItemLeft();
			}
		});
		writer.write("<tr>");
		for (IExportTemplateItem item : templates) {
			if (item.getItemVisible() == emYesNo.NO) {
				continue;
			}
			writer.write("<td");
			writer.write(" ");
			writer.write("style=\"");
			writer.write("width:");
			writer.write(String.valueOf(item.getItemWidth() - item.getLineLeft() - item.getLineRight()));
			writer.write("px;");
			writer.write("height:");
			writer.write(String.valueOf(item.getItemHeight() - item.getLineTop() - item.getLineBottom()));
			writer.write("px;");
			writer.write("border:1px solid black;");
			writer.write("\"");
			writer.write(" ");
			writer.write(">");
			this.drawElement(writer, item);
			writer.write("</td>");
		}
		writer.write("</tr>");
	}

	protected void drawPageSeparator(Writer writer, String pageName, int top, int width) throws IOException {
		writer.write("<div");
		writer.write(" ");
		writer.write("id=\"");
		writer.write(String.format("%s_line", pageName));
		writer.write("\"");
		writer.write(" ");
		writer.write("class=\"no_print\"");
		writer.write(" ");
		writer.write("style=\"");
		writer.write("position:absolute;");
		writer.write("border:1px dashed black;");
		writer.write("top:");
		writer.write(String.valueOf(top));
		writer.write("px;");
		writer.write("width:");
		writer.write(String.valueOf(width));
		writer.write("px;");
		writer.write("\"");
		writer.write(" ");
		writer.write(">");
		writer.write("</div>");
	}
}
