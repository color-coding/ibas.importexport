package org.colorcoding.ibas.importexport.transformer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.colorcoding.ibas.bobas.data.DateTime;
import org.colorcoding.ibas.bobas.data.emYesNo;
import org.colorcoding.ibas.bobas.i18n.I18N;
import org.colorcoding.ibas.bobas.message.Logger;
import org.colorcoding.ibas.bobas.message.MessageLevel;
import org.colorcoding.ibas.importexport.MyConfiguration;
import org.colorcoding.ibas.importexport.bo.exporttemplate.IExportTemplateItem;
import org.colorcoding.ibas.importexport.data.emDataSourceType;
import org.colorcoding.ibas.importexport.data.emJustificationHorizontal;
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
	protected String templateValue(IExportTemplateItem template) {
		if (template.getSourceType() == emDataSourceType.PATH && template.getItemString() != null) {
			// 路径取值
			String value = template.getItemString();
			if (value.indexOf("[]") > 0) {
				int index = this.paramValue(PARAM_DATA_INDEX, -1);
				if (index < 0) {
					return "";
				}
				Object tmp = this.dataValue(value.replace("[]", String.format("[%s]", index - 1)), null);
				if (tmp == null) {
					return "";
				}
				if (template.getValueFormat() != null && !template.getValueFormat().isEmpty()) {
					if (template.getValueFormat().indexOf("%t") >= 0) {
						// 日期转换
						if (tmp instanceof String) {
							tmp = DateTime.valueOf((String) tmp);
						}
					}
					return String.format(template.getValueFormat(), tmp);
				}
				return String.valueOf(tmp);
			}
		}
		return super.templateValue(template);
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
		try {
			File file = new File(String.format("%s%s.html", this.getWorkFolder(), UUID.randomUUID().toString()));
			OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(file), "utf-8");
			if (!file.getParentFile().exists()) {
				file.mkdirs();
			}
			if (!file.exists()) {
				file.createNewFile();
			}
			this.darwPage(writer);
			writer.flush();
			writer.close();
			this.setOutputData(new File[] { file });
		} catch (IOException e) {
			throw new TransformException(e);
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
			int index = itemString.indexOf("[]");
			if (index < 0) {
				continue;
			}
			size = this.dataValue(String.format("%s.length()", itemString.substring(0, index)), 0);
			this.newParam(PARAM_DATA_SIZE, size);
			this.newParam(PARAM_DATA_INDEX, 1);
			break;
		}
		// 计算页数
		int count = 1;
		int pageHeigh = this.getTemplate().getHeight();
		pageHeigh -= this.getTemplate().getMarginTop();
		pageHeigh -= this.getTemplate().getMarginBottom();
		// 页眉区
		if (!this.getTemplate().getPageHeaders().isEmpty()) {
			pageHeigh -= this.getTemplate().getPageHeaderHeight();
			pageHeigh -= this.getTemplate().getMarginArea();
		}
		// 页脚区
		if (!this.getTemplate().getPageFooters().isEmpty()) {
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
					if (!this.getTemplate().getStartSections().isEmpty()) {
						content -= this.getTemplate().getStartSectionHeight();
						content -= this.getTemplate().getMarginArea();
					}
				}
				// 重复头区
				if (!this.getTemplate().getRepetitionHeaders().isEmpty()) {
					content -= this.getTemplate().getRepetitionHeaderHeight();
				}
				// 重复脚区
				if (!this.getTemplate().getRepetitionFooters().isEmpty()) {
					content -= this.getTemplate().getRepetitionFooterHeight();
				}
			}
			content -= this.getTemplate().getRepetitionHeight();
			if (content <= 0 || content < this.getTemplate().getRepetitionHeight()) {
				// 空间用完，新起页
				this.newParam(String.format(PARAM_TEMPLATE_PAGE_DATA_INDEX, count), i - 1);
				count++;
				content = pageHeigh;
			}
			// 最后数据
			if (i == size) {
				// 结束区
				if (!this.getTemplate().getEndSections().isEmpty()) {
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

		writer.write("<style>");
		writer.write("@media print { .no_print { display:none;}}");
		writer.write("</style>");

		writer.write("</head>");

		writer.write("<body>");

		int pageTop = 0, pageLeft = 0, pageWidth = this.getTemplate().getWidth(),
				pageHeight = this.getTemplate().getHeight();
		for (int page = this.paramValue(PARAM_PAGE_INDEX, 1); page <= this.paramValue(PARAM_PAGE_TOTAL, 1); page++) {
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
			int top = 0;
			this.startDiv(writer, pageName, pageLeft, pageTop, pageWidth, pageHeight);
			if (page > 1) {
				// 页分隔线
				writer.write("<div");
				writer.write(" ");
				writer.write("id=\"");
				writer.write(String.format("%s_line", pageName));
				writer.write("\"");
				writer.write(" ");
				writer.write("class=\"no_print\"");
				writer.write(" ");
				writer.write("style=\"");
				writer.write("border:0.5px dashed black;");
				writer.write("\"");
				writer.write(" ");
				writer.write(">");
				writer.write("</div>");
			}
			// 绘制页眉区域
			if (!this.getTemplate().getPageHeaders().isEmpty()) {
				String areaName = String.format("%s_header", pageName);
				Logger.log(MessageLevel.DEBUG, "transformer: draw area [%s].", areaName);
				this.startDiv(writer, areaName, this.getTemplate().getPageHeaderLeft(),
						this.getTemplate().getPageHeaderTop(), this.getTemplate().getPageHeaderWidth(),
						this.getTemplate().getPageHeaderHeight());
				this.drawArea(writer, this.getTemplate().getPageHeaders());
				this.endDiv(writer);
				top = this.getTemplate().getPageHeaderTop();
				top += this.getTemplate().getPageHeaderHeight();
				top += this.getTemplate().getMarginArea();
			}
			// 绘制开始区域
			if (page == 1 && !this.getTemplate().getStartSections().isEmpty()) {
				// 第一页绘制
				String areaName = String.format("%s_startsection", pageName);
				Logger.log(MessageLevel.DEBUG, "transformer: draw area [%s].", areaName);
				this.startDiv(writer, areaName, this.getTemplate().getStartSectionLeft(),
						this.getTemplate().getStartSectionTop(), this.getTemplate().getStartSectionWidth(),
						this.getTemplate().getStartSectionHeight());
				this.drawArea(writer, this.getTemplate().getStartSections());
				this.endDiv(writer);
				top = this.getTemplate().getStartSectionTop();
				top += this.getTemplate().getStartSectionHeight();
				top += this.getTemplate().getMarginArea();
			}
			// 绘制重复区域
			if (!this.getTemplate().getRepetitions().isEmpty()) {
				String areaName = String.format("%s_repetitions", pageName);
				Logger.log(MessageLevel.DEBUG, "transformer: draw area [%s].", areaName);
				int index = this.paramValue(PARAM_DATA_INDEX, 1), size = this.paramValue(PARAM_DATA_SIZE, 0);
				for (int i = index; i <= size; i++) {
					this.newParam(PARAM_DATA_INDEX, i);
					if (i == index) {
						this.startDiv(writer, areaName, this.getTemplate().getRepetitionHeaderLeft(), top,
								this.getTemplate().getRepetitionHeaderWidth(), -1);
						areaName = String.format("%s_table", pageName);
						Logger.log(MessageLevel.DEBUG, "transformer: draw area [%s].", areaName);
						this.startTable(writer, areaName, this.getTemplate().getRepetitionHeaders());
						top += this.getTemplate().getRepetitionHeaderHeight();
					}
					this.drawTableRow(writer, this.getTemplate().getRepetitions());
					top += this.getTemplate().getRepetitionHeight();
					if (this.paramValue(String.format(PARAM_TEMPLATE_PAGE_DATA_INDEX, page), -1) == i) {
						if (!this.getTemplate().getRepetitionFooters().isEmpty()) {
							this.drawTableRow(writer, this.getTemplate().getRepetitionFooters(),
									this.getTemplate().getRepetitions().size()
											- this.getTemplate().getRepetitionFooters().size() + 1);
							top += this.getTemplate().getRepetitionFooterHeight();
						}
						this.endTable(writer);
						this.endDiv(writer);
						this.newParam(PARAM_DATA_INDEX, ++i);
						break;
					}
					if (i == size) {
						if (!this.getTemplate().getRepetitionFooters().isEmpty()) {
							this.drawTableRow(writer, this.getTemplate().getRepetitionFooters(),
									this.getTemplate().getRepetitions().size()
											- this.getTemplate().getRepetitionFooters().size() + 1);
							top += this.getTemplate().getRepetitionFooterHeight();
						}
						this.endTable(writer);
						this.endDiv(writer);
					}
				}
			}
			// 绘制结束区域
			if (page == this.paramValue(PARAM_PAGE_TOTAL, 1) && !this.getTemplate().getEndSections().isEmpty()) {
				// 最后一页绘制
				String areaName = String.format("%s_endsection", pageName);
				Logger.log(MessageLevel.DEBUG, "transformer: draw area [%s].", areaName);
				this.startDiv(writer, areaName, this.getTemplate().getEndSectionLeft(),
						top + this.getTemplate().getMarginArea(), this.getTemplate().getEndSectionWidth(),
						this.getTemplate().getEndSectionHeight());
				this.drawArea(writer, this.getTemplate().getEndSections());
				this.endDiv(writer);
			}
			// 绘制页脚区域
			if (!this.getTemplate().getPageFooters().isEmpty()) {
				String areaName = String.format("%s_footer", pageName);
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
		writer.write("<div");
		writer.write(" ");
		writer.write("id=\"");
		writer.write(id);
		writer.write("\"");
		writer.write(" ");
		writer.write("style=\"");
		writer.write("position:absolute;");
		writer.write("white-space:nowrap;overflow:hidden;");
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
			this.startDiv(writer, String.format("_%s", item.getItemID()), item.getItemLeft(), item.getItemTop(),
					item.getItemWidth(), item.getItemHeight());
			this.drawElement(writer, item);
			this.endDiv(writer);
		}
	}

	protected void drawElement(Writer writer, IExportTemplateItem template) throws IOException {
		if (template.getItemType() != null) {
			if (template.getItemType().equalsIgnoreCase("IMG")) {
				writer.write("<img");
				writer.write(" ");
				writer.write("id=\"");
				writer.write(template.getItemID());
				writer.write("\"");
				writer.write(" ");
				writer.write("style=\"");
				writer.write("max-width:100%;max-height:100%;");
				writer.write("\"");
				writer.write(" ");
				writer.write("src=\"");
				writer.write(this.templateValue(template));
				writer.write("\"");
				writer.write(" ");
				writer.write(">");
				writer.write("</img>");
				return;
			}
		}
		writer.write("<label");
		writer.write(" ");
		writer.write("id=\"");
		writer.write(template.getItemID());
		writer.write("\"");
		writer.write(" ");
		writer.write("style=\"");
		writer.write("display:block;");
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
		if (template.getJustificationHorizontal() == emJustificationHorizontal.CENTER) {
			writer.write("text-align:center;");
		} else if (template.getJustificationHorizontal() == emJustificationHorizontal.LEFT) {
			writer.write("text-align:left;padding-left:2px;");
		} else if (template.getJustificationHorizontal() == emJustificationHorizontal.RIGHT) {
			writer.write("text-align:right;padding-right:2px;");
		}
		writer.write("\"");
		writer.write(" ");
		writer.write(">");
		writer.write(this.templateValue(template));
		writer.write("</label>");
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
		writer.write("border=\"1px\" solid=\"black\"");
		writer.write(" ");
		writer.write("style=\"");
		writer.write("table-layout:fixed;border-collapse:collapse;");
		writer.write("width:");
		writer.write(String.valueOf(this.getTemplate().getRepetitionHeaderWidth()));
		writer.write("px;");
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
			writer.write("<td");
			writer.write(" ");
			writer.write("style=\"");
			writer.write("width:");
			writer.write(String.valueOf(item.getItemWidth() - item.getLineLeft() - item.getLineRight()));
			writer.write("px;");
			writer.write("height:");
			writer.write(String.valueOf(item.getItemHeight() - item.getLineTop() - item.getLineBottom()));
			writer.write("px;");
			writer.write("white-space:nowrap;overflow:hidden;");
			writer.write("\"");
			writer.write(" ");
			writer.write(">");
			this.drawElement(writer, item);
			writer.write("</td>");
		}
		writer.write("</tr>");
	}

	protected void endTable(Writer writer) throws IOException {
		writer.write("</table>");
	}

	protected void drawTableRow(Writer writer, List<IExportTemplateItem> templates, int colSpan) throws IOException {
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
			writer.write("height:");
			writer.write(String.valueOf(item.getItemHeight() - item.getLineTop() - item.getLineBottom()));
			writer.write("px;");
			// 超出文字，隐藏
			writer.write("white-space:nowrap;overflow:hidden;word-break:keep-all;");
			writer.write("\"");
			if (colSpan > 0) {
				writer.write(" ");
				writer.write("colspan=\"");
				writer.write(String.valueOf(colSpan));
				writer.write("\"");
				colSpan = -1;
			}
			writer.write(" ");
			writer.write(">");
			this.drawElement(writer, item);
			writer.write("</td>");
		}
		writer.write("</tr>");
	}

	protected void drawTableRow(Writer writer, List<IExportTemplateItem> templates) throws IOException {
		this.drawTableRow(writer, templates, 0);
	}

}
