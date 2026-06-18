package org.colorcoding.ibas.importexport.transformer;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.colorcoding.ibas.bobas.common.Bytes;
import org.colorcoding.ibas.bobas.common.DateTimes;
import org.colorcoding.ibas.bobas.common.Strings;
import org.colorcoding.ibas.bobas.common.Urls;
import org.colorcoding.ibas.bobas.data.ArrayList;
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

/**
 * 业务对象转换html文件
 * 
 * @author Niuren.Zhu
 *
 */
@TransformerInfo(name = "TO_FILE_HTML", template = true, printable = true, contentType = "text/html")
public class TransformerHtml extends ExportTemplateTransformer {

	public final static String DISPLAY_ELEMENT_IMAGE = "IMG";
	public final static String DISPLAY_ELEMENT_TEXT = "TEXT";
	public final static String DISPLAY_ELEMENT_PDF = "PDF";

	/**
	 * 获取是否将IMG标签的图片地址转为BASE64数据
	 *
	 * @return true-开启图片BASE64嵌入，false-保留原始地址（默认）
	 */
	public boolean isEmbedImage() {
		return this.paramValue("EmbedImage", false);
	}

	private DocumentContext dataContext;

	private final DocumentContext getDataContext() {
		if (this.dataContext == null) {
			this.dataContext = JsonPath.parse(this.getInputData());
		}
		return dataContext;
	}

	@SuppressWarnings("unchecked")
	protected <T> T dataValue(String name, T defaults) {
		try {
			Matcher matcher = Pattern.compile(MyConfiguration.VARIABLE_PATTERN).matcher(name);
			while (matcher.find()) {
				String pName = matcher.group(0);
				Object pValue = this.paramValue(pName, null);
				if (pValue != null) {
					name = name.replace(pName, String.valueOf(pValue));
				}
			}
			Object value = null;
			// 重复区数据：使用 DATA_INDEXES + PARAM_DATA_INDEX 定位当前行
			if (this.DATA_INDEXES != null && this.DATA_INDEXES_PATH != null
					&& name.startsWith(this.DATA_INDEXES_PATH)) {
				String suffix = name.substring(this.DATA_INDEXES_PATH.length());
				if (suffix.isEmpty() || suffix.startsWith("[") || suffix.startsWith(".")) {
					int dataIndex = this.paramValue(PARAM_DATA_INDEX, 0);
					if (dataIndex > 0 && dataIndex <= this.DATA_INDEXES.size()) {
						Object element = this.DATA_INDEXES.get(dataIndex - 1);
						// 提取属性链：去除方括号表达式和点号
						String propChain = suffix;
						while (propChain.startsWith("[")) {
							int bracketEnd = -1;
							int depth = 0;
							for (int i = 0; i < propChain.length(); i++) {
								char c = propChain.charAt(i);
								if (c == '[') {
									depth++;
								} else if (c == ']') {
									depth--;
									if (depth == 0) {
										bracketEnd = i;
										break;
									}
								}
							}
							if (bracketEnd >= 0) {
								propChain = propChain.substring(bracketEnd + 1);
							} else {
								break;
							}
						}
						if (propChain.startsWith(".")) {
							propChain = propChain.substring(1);
						}
						if (propChain.isEmpty()) {
							value = element;
						} else {
							value = this.getPropertyValue(element, propChain);
						}
						if (value == null) {
							return defaults;
						}
						Logger.log(MessageLevel.DEBUG, "transformer: data [%s], value [%s].", name, value);
						return (T) value;
					}
				}
			}
			// 非重复区数据：使用原始JsonPath读取
			{
				String arrayPath = this.extractArrayPath(name);
				String propertyPath = this.extractPropertyPath(name);
				if (arrayPath != null && !arrayPath.equals(name)) {
					Object arrayValue = this.getDataContext().read(arrayPath);
					if (arrayValue == null) {
						return defaults;
					}
					if (propertyPath != null && !propertyPath.isEmpty()) {
						if (arrayValue instanceof Iterable) {
							StringBuilder sb = new StringBuilder();
							for (Object item : (Iterable<Object>) arrayValue) {
								if (item instanceof Map) {
									Object propVal = ((Map<String, Object>) item).get(propertyPath);
									if (propVal != null) {
										if (sb.length() > 0) {
											sb.append(",");
										}
										sb.append(String.valueOf(propVal));
									}
								}
							}
							value = sb.length() > 0 ? sb.toString() : null;
						} else if (arrayValue instanceof Map) {
							value = ((Map<String, Object>) arrayValue).get(propertyPath);
						} else {
							value = arrayValue;
						}
					} else {
						value = arrayValue;
					}
				} else {
					value = this.getDataContext().read(name);
				}
			}
			if (value == null) {
				return defaults;
			}
			Logger.log(MessageLevel.DEBUG, "transformer: data [%s], value [%s].", name, value);
			if (value instanceof Iterable) {
				Double total = null;
				for (Object item : (Iterable<Object>) value) {
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

	/**
	 * 从JsonPath表达式中提取数组路径部分。 如 $[0].SalesQuoteItems[?(@.price <
	 * 40)].ItemDescription → $[0].SalesQuoteItems[?(@.price < 40)] 如
	 * $[0].SalesQuoteItems[].ItemDescription → $[0].SalesQuoteItems[*]
	 */
	private String extractArrayPath(String path) {
		if (path == null || path.isEmpty()) {
			return null;
		}
		// 去除末尾的简单属性（不在[]内的.分隔的部分）
		// 从右向左找第一个不在括号内的点号
		int lastDot = -1;
		int bracketDepth = 0;
		for (int i = path.length() - 1; i >= 0; i--) {
			char c = path.charAt(i);
			if (c == ']') {
				bracketDepth++;
			} else if (c == '[') {
				bracketDepth--;
			} else if (c == '.' && bracketDepth == 0) {
				lastDot = i;
				break;
			}
		}
		if (lastDot > 0) {
			String afterDot = path.substring(lastDot + 1);
			// 如果点后面不是数组表达式，则截取数组路径
			if (!afterDot.contains("[") && !afterDot.contains("]")) {
				path = path.substring(0, lastDot);
			}
		}
		// 替换所有空数组标记 [] 为 [*]，以支持JsonPath读取全部元素（含嵌套数组）
		path = path.replace("[]", "[*]");
		return path;
	}

	/**
	 * 从JsonPath数组路径中提取基础路径（去除末尾的方括号表达式）。 如 $[0].SalesQuoteItems[?(@.price < 40)] →
	 * $[0].SalesQuoteItems 如 $[0].SalesQuoteItems[] → $[0].SalesQuoteItems
	 * 保留$[数字]这样的根对象索引。
	 */
	private String extractBaseArrayPath(String path) {
		if (path == null || path.isEmpty()) {
			return null;
		}
		String result = path;
		while (result.endsWith("]")) {
			int depth = 1;
			int i = result.length() - 2;
			while (i >= 0 && depth > 0) {
				char c = result.charAt(i);
				if (c == ']') {
					depth++;
				} else if (c == '[') {
					depth--;
				}
				i--;
			}
			if (depth == 0) {
				String before = result.substring(0, i + 1);
				// 去掉后如果不再包含属性名（如 $[0] → $），不再继续
				if (!before.contains(".")) {
					break;
				}
				result = before;
			} else {
				break;
			}
		}
		return result;
	}

	/**
	 * 从JsonPath表达式中提取最后一个]后面的属性名。 如 $[0].SalesQuoteItems[?(@.price <
	 * 40)].ItemDescription → ItemDescription
	 */
	private String extractPropertyPath(String path) {
		if (path == null || path.isEmpty()) {
			return null;
		}
		// 从右向左找第一个不在括号内的点号
		int lastDot = -1;
		int bracketDepth = 0;
		for (int i = path.length() - 1; i >= 0; i--) {
			char c = path.charAt(i);
			if (c == ']') {
				bracketDepth++;
			} else if (c == '[') {
				bracketDepth--;
			} else if (c == '.' && bracketDepth == 0) {
				lastDot = i;
				break;
			}
		}
		if (lastDot > 0) {
			String afterDot = path.substring(lastDot + 1);
			// 如果点后面不是数组表达式，则是属性名
			if (!afterDot.contains("[") && !afterDot.contains("]")) {
				return afterDot;
			}
		}
		return null;
	}

	/**
	 * 从对象中按属性链获取值。 属性链用.分隔，如 "Item.Name" 表示 element.get("Item").get("Name")
	 */
	@SuppressWarnings("unchecked")
	private Object getPropertyValue(Object obj, String propertyPath) {
		if (obj == null || propertyPath == null || propertyPath.isEmpty()) {
			return null;
		}
		String[] parts = propertyPath.split("\\.");
		Object current = obj;
		for (String part : parts) {
			if (part.isEmpty()) {
				continue;
			}
			if (current instanceof Map) {
				current = ((Map<String, Object>) current).get(part);
			} else {
				return null;
			}
			if (current == null) {
				return null;
			}
		}
		return current;
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
	 * 重复区数据元素列表（存储JsonPath返回的数组元素对象）
	 */
	private List<Object> DATA_INDEXES;

	/**
	 * 重复区数据对应的数组路径
	 */
	private String DATA_INDEXES_PATH;

	/**
	 * 根据JsonPath表达式读取数组数据，返回实际元素列表。 使用DocumentContext.read()读取数组数据，支持过滤表达式如
	 * [?(@.price &lt; 40)]。
	 */
	private List<Object> createDataIndexes(String path) {
		try {
			Object result = this.getDataContext().read(path);
			if (result == null) {
				return new ArrayList<>();
			}
			return ArrayList.create(result);
		} catch (Exception e) {
			Logger.log(MessageLevel.WARN, e);
			return new ArrayList<>();
		}
	}

	/**
	 * 初始化，分析数据构建变量
	 */
	protected void init() throws TransformException {
		// 初始变量值
		this.newParam(PARAM_TIME_NOW, DateTimes.now());
		this.newParam(PARAM_DATA_INDEX, 0);
		// 获取数据长度
		int size = 0;
		for (IExportTemplateItem item : this.getExportTemplate().getRepetitions()) {
			if (item.getSourceType() != emDataSourceType.PATH) {
				continue;
			}
			String itemString = item.getItemString();
			if (itemString == null || itemString.isEmpty()) {
				continue;
			}
			// 提取数组路径部分（去除最后一个]后面的属性路径）
			String arrayPath = this.extractArrayPath(itemString);
			if (arrayPath == null) {
				continue;
			}
			this.DATA_INDEXES = this.createDataIndexes(arrayPath);
			// 计算DATA_INDEXES_PATH：取路径中最后一个数组标记([]或[*])之前的部分
			int lastEmptyBracket = Math.max(itemString.lastIndexOf("[]"), itemString.lastIndexOf("[*]"));
			if (lastEmptyBracket >= 0) {
				this.DATA_INDEXES_PATH = itemString.substring(0, lastEmptyBracket);
			} else {
				this.DATA_INDEXES_PATH = this.extractBaseArrayPath(arrayPath);
			}
			if (this.DATA_INDEXES != null && this.DATA_INDEXES.size() > 0) {
				size = this.DATA_INDEXES.size();
				this.newParam(PARAM_DATA_SIZE, size);
				this.newParam(PARAM_DATA_INDEX, 1);
				break;
			}
		}
		// 计算页数
		int count = 1;
		int pageHeigh = this.getExportTemplate().getHeight();
		pageHeigh -= this.getExportTemplate().getMarginTop();
		pageHeigh -= this.getExportTemplate().getMarginBottom();
		// 页眉区
		if (this.getExportTemplate().getPageHeaderHeight() > 0) {
			pageHeigh -= this.getExportTemplate().getPageHeaderHeight();
			pageHeigh -= this.getExportTemplate().getMarginArea();
		}
		// 页脚区
		if (this.getExportTemplate().getPageFooterHeight() > 0) {
			pageHeigh -= this.getExportTemplate().getMarginArea();
			pageHeigh -= this.getExportTemplate().getPageFooterHeight();
		}
		// 判断是否有空余位置
		if (pageHeigh < 0) {
			throw new TransformException(
					I18N.prop("msg_ie_template_size_not_enough", this.getExportTemplate().getName()));
		}
		int content = pageHeigh;
		for (int i = 1; i <= size; i++) {
			// 新页
			if (content == pageHeigh) {
				// 第一页
				if (count == 1) {
					// 开始区
					if (this.getExportTemplate().getStartSectionHeight() > 0) {
						content -= this.getExportTemplate().getStartSectionHeight();
						content -= this.getExportTemplate().getMarginArea();
					}
				}
				// 重复头区
				if (this.getExportTemplate().getRepetitionHeaderHeight() > 0) {
					content -= this.getExportTemplate().getRepetitionHeaderHeight();
				}
				// 重复脚区
				if (this.getExportTemplate().getRepetitionFooterHeight() > 0) {
					content -= this.getExportTemplate().getRepetitionFooterHeight();
				}
			}
			content -= this.getExportTemplate().getRepetitionHeight();
			if (content <= 0 || content < this.getExportTemplate().getRepetitionHeight()) {
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
				if (this.getExportTemplate().getEndSectionHeight() > 0) {
					content -= this.getExportTemplate().getMarginArea();
					content -= this.getExportTemplate().getEndSectionHeight();
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
		if (!this.getExportTemplate().getAppendixs().isEmpty()) {
			count += this.getExportTemplate().getAppendixs().size();
		}
		// 总页数
		this.newParam(PARAM_PAGE_TOTAL, count);
		this.newParam(PARAM_PAGE_INDEX, 1);
	}

	protected void darwPage(Writer writer) throws IOException {
		writer.write("<!DOCTYPE HTML>");
		writer.write("<html>");

		writer.write("<head>");
		writer.write(String.format("<title>%s</title>", this.getExportTemplate().getName()));
		writer.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />");
		writer.write("<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">");
		writer.write("<meta charset=\"utf-8\" />");
		writer.write("<meta name=\"referrer\" content=\"origin\" />");
		writer.write("<meta name=\"page_template\" content=\"");
		writer.write(this.getExportTemplate().getObjectKey().toString());
		writer.write("\" ");
		writer.write("/>");
		writer.write("<meta name=\"page_width\" content=\"");
		writer.write(this.getExportTemplate().getWidth().toString());
		writer.write("\" ");
		writer.write("/>");
		writer.write("<meta name=\"page_height\" content=\"");
		writer.write(this.getExportTemplate().getHeight().toString());
		writer.write("\" ");
		writer.write("/>");
		int templateDpi = this.getExportTemplate().getDpi() != null && this.getExportTemplate().getDpi() > 0
				? this.getExportTemplate().getDpi()
				: 72;
		writer.write("<meta name=\"page_dpi\" content=\"");
		writer.write(String.valueOf(templateDpi));
		writer.write("\" />");
		writer.write("<style>");
		writer.write("@media print {");
		// 不打印内容
		writer.write(".no_print { display:none;}");
		writer.write(" ");
		// 根据模板pDPI计算物理纸张尺寸
		double pageWidthMm = (double) this.getExportTemplate().getWidth() / templateDpi * 25.4;
		double pageHeightMm = (double) this.getExportTemplate().getHeight() / templateDpi * 25.4;
		writer.write(String.format("@page {size:%.2fmm %.2fmm;margin:0;}", pageWidthMm, pageHeightMm));
		writer.write("}");
		writer.write("</style>");

		writer.write("</head>");

		writer.write("<body>");

		int pageTop = 0, pageLeft = 0, pageWidth = this.getExportTemplate().getWidth(),
				pageHeight = this.getExportTemplate().getHeight();
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
			if (this.getExportTemplate().getPageHeaderHeight() > 0) {
				Logger.log(MessageLevel.DEBUG, "transformer: draw area [%s].", areaName);
				this.startDiv(writer, areaName, this.getExportTemplate().getPageHeaderLeft(),
						this.getExportTemplate().getPageHeaderTop(), this.getExportTemplate().getPageHeaderWidth(),
						this.getExportTemplate().getPageHeaderHeight());
				this.drawArea(writer, this.getExportTemplate().getPageHeaders());
				this.endDiv(writer);
				top += this.getExportTemplate().getPageHeaderHeight();
			}
			// 绘制开始区域
			if (page == 1) {
				// 第一页绘制
				areaName = String.format("%s_startsection", pageName);
				if (this.getExportTemplate().getStartSectionHeight() > 0) {
					Logger.log(MessageLevel.DEBUG, "transformer: draw area [%s].", areaName);
					top += this.getExportTemplate().getMarginArea();
					this.startDiv(writer, areaName, this.getExportTemplate().getStartSectionLeft(), top,
							this.getExportTemplate().getStartSectionWidth(),
							this.getExportTemplate().getStartSectionHeight());
					this.drawArea(writer, this.getExportTemplate().getStartSections());
					this.endDiv(writer);
					top += this.getExportTemplate().getStartSectionHeight();
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
					top += this.getExportTemplate().getMarginArea();
					Logger.log(MessageLevel.DEBUG, "transformer: draw area [%s].", areaName);
					this.startDiv(writer, areaName, this.getExportTemplate().getRepetitionHeaderLeft(), top,
							this.getExportTemplate().getRepetitionHeaderWidth(), -1);
					this.startTable(writer, areaName, this.getExportTemplate().getRepetitionHeaders());
					top += this.getExportTemplate().getRepetitionHeaderHeight();
				}
				// if (this.getMyTemplate().getRepetitionHeight() > 0) {
				// 去除重复限制，以实现结束区顶格
				this.drawTableRow(writer, this.getExportTemplate().getRepetitions());
				top += this.getExportTemplate().getRepetitionHeight();
				// }
				if (this.paramValue(String.format(PARAM_TEMPLATE_PAGE_DATA_INDEX, page), -1) == i) {
					if (this.getExportTemplate().getRepetitionFooterHeight() > 0) {
						this.endTable(writer, this.getExportTemplate().getRepetitionFooters());
						top += this.getExportTemplate().getRepetitionFooterHeight();
					} else {
						this.endTable(writer);
					}
					this.endDiv(writer);
					this.newParam(PARAM_DATA_INDEX, ++i);
					break;
				}
				if (i == size) {
					if (this.getExportTemplate().getRepetitionFooterHeight() > 0) {
						this.endTable(writer, this.getExportTemplate().getRepetitionFooters());
						top += this.getExportTemplate().getRepetitionFooterHeight();
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
				if (this.getExportTemplate().getEndSectionHeight() > 0) {
					Logger.log(MessageLevel.DEBUG, "transformer: draw area [%s].", areaName);
					top += this.getExportTemplate().getMarginArea();
					this.startDiv(writer, areaName, this.getExportTemplate().getEndSectionLeft(), top,
							this.getExportTemplate().getEndSectionWidth(),
							this.getExportTemplate().getEndSectionHeight());
					this.drawArea(writer, this.getExportTemplate().getEndSections());
					this.endDiv(writer);
					top += this.getExportTemplate().getEndSectionHeight();
				}
			}
			// 绘制页脚区域
			areaName = String.format("%s_footer", pageName);
			if (this.getExportTemplate().getPageFooterHeight() > 0) {
				Logger.log(MessageLevel.DEBUG, "transformer: draw area [%s].", areaName);
				top += this.getExportTemplate().getMarginArea();
				this.startDiv(writer, areaName, this.getExportTemplate().getPageFooterLeft(),
						this.getExportTemplate().getPageFooterTop(), this.getExportTemplate().getPageFooterWidth(),
						this.getExportTemplate().getPageFooterHeight());
				this.drawArea(writer, this.getExportTemplate().getPageFooters());
				this.endDiv(writer);
			}
			// 结束-页
			this.endDiv(writer);
		}
		int page = this.paramValue(PARAM_PAGE_INDEX, 0);
		// 绘制附录
		for (int index = 0; index < this.getExportTemplate().getAppendixs().size(); index++) {
			// 设置当前页变量
			page += 1;
			this.newParam(PARAM_PAGE_INDEX, page);
			IExportTemplateAppendix appendix = this.getExportTemplate().getAppendixs().get(index);
			// 开始-页
			String pageName = String.format("page_%s", page), areaName;
			pageTop = (page - 1) * pageHeight;
			this.drawPageSeparator(writer, pageName, pageTop, pageWidth);
			this.startDiv(writer, pageName, pageLeft, pageTop, pageWidth, pageHeight);
			// 绘制页眉区域
			if (appendix.getPageHeader() == emYesNo.YES) {
				areaName = String.format("%s_header", pageName);
				Logger.log(MessageLevel.DEBUG, "transformer: draw area [%s].", areaName);
				this.startDiv(writer, areaName, this.getExportTemplate().getPageHeaderLeft(),
						this.getExportTemplate().getPageHeaderTop(), this.getExportTemplate().getPageHeaderWidth(),
						this.getExportTemplate().getPageHeaderHeight());
				this.drawArea(writer, this.getExportTemplate().getPageHeaders());
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
				this.startDiv(writer, areaName, this.getExportTemplate().getPageFooterLeft(),
						this.getExportTemplate().getPageFooterTop(), this.getExportTemplate().getPageFooterWidth(),
						this.getExportTemplate().getPageFooterHeight());
				this.drawArea(writer, this.getExportTemplate().getPageFooters());
				this.endDiv(writer);
			}
			// 结束-页
			this.endDiv(writer);
		}
		writer.write("</body>");
		// DPI校正：CSS px按96 DPI解析，模板按设计DPI，需缩放校正
		writer.write("<script>");
		writer.write("(function(){");
		writer.write("var d=document.querySelector('meta[name=page_dpi]');");
		writer.write("var dpi=d?parseInt(d.content)||72:72;");
		writer.write("var s=96/dpi;");
		writer.write("if(Math.abs(s-1)<0.01)return;");
		writer.write("document.body.style.zoom=s;");
		writer.write("window.__dpiCorrected=true;");
		writer.write("var st=document.createElement('style');");
		writer.write("st.textContent='@media print{body{zoom:'+s+' !important;}}';");
		writer.write("document.head.appendChild(st);");
		writer.write("})();");
		writer.write("</script>");
		writer.write("</html>");
	}

	protected void startDiv(Writer writer, String id, int left, int top) throws IOException {
		this.startDiv(writer, id, left, top, -1, -1);
	}

	protected void startDiv(Writer writer, String id, int left, int top, int width, int height) throws IOException {
		this.startDiv(writer, id, left, top, width, height, null, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1);
	}

	protected void startDiv(Writer writer, String id, int left, int top, int width, int height, emLineStyle lineStyle,
			int lineLeft, int lineTop, int lineRight, int lineBottom, int borderRed, int borderGreen, int borderBlue,
			int bgRed, int bgGreen, int bgBlue) throws IOException {
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
		if (bgRed > 0 || bgGreen > 0 || bgBlue > 0) {
			writer.write("background-color:rgb(");
			writer.write(String.valueOf(bgRed));
			writer.write(",");
			writer.write(String.valueOf(bgGreen));
			writer.write(",");
			writer.write(String.valueOf(bgBlue));
			writer.write(");");
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
					item.getBorderBlue(), item.getBackgroundRed(), item.getBackgroundGreen(), item.getBackgroundBlue());
			this.drawElement(writer, item);
			this.endDiv(writer);
		}
	}

	/**
	 * 图片下载失败时的默认占位图
	 */
	public final static String IMAGE_BROKEN_BASE64 = "data:image/svg+xml;base64,"
			+ "PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHZlcnNpb249IjEuMSI+Cjwvc3ZnPiA=";

	/**
	 * 下载图片并转为BASE64数据URI。 支持http/https协议的图片地址，将图片内容下载后转为
	 * data:image/xxx;base64,...格式的数据URI。 如果下载失败或地址无效，则返回图片加载失败的占位图BASE64数据。
	 *
	 * @param imageUrl 图片地址
	 * @return BASE64数据URI；下载失败时返回占位图BASE64数据
	 */
	protected String downloadImageAsBase64(String imageUrl) {
		if (imageUrl == null || imageUrl.isEmpty()) {
			return IMAGE_BROKEN_BASE64;
		}
		// 已经是BASE64数据URI，直接返回
		if (imageUrl.startsWith("data:")) {
			return imageUrl;
		}
		// 仅处理http/https协议的地址
		if (!imageUrl.startsWith("http://") && !imageUrl.startsWith("https://")) {
			return IMAGE_BROKEN_BASE64;
		}
		// 替换地址中的token参数值为用户令牌
		String token = this.paramValue("UserToken", Strings.VALUE_EMPTY);
		if (token != null && !token.isEmpty()) {
			imageUrl = imageUrl.replaceAll("([?&])token=([^&]*)",
					"$1token=" + java.util.regex.Matcher.quoteReplacement(token));
		}
		HttpURLConnection connection = null;
		try {
			URL url = new URL(imageUrl);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setConnectTimeout(10000);
			connection.setReadTimeout(10000);
			// 不跟随重定向次数过多
			connection.setInstanceFollowRedirects(true);
			int responseCode = connection.getResponseCode();
			if (responseCode != HttpURLConnection.HTTP_OK) {
				Logger.log(MessageLevel.WARN, "transformer: download image [%s] failed, response code [%s].", imageUrl,
						responseCode);
				return IMAGE_BROKEN_BASE64;
			}
			// 获取Content-Type以判断图片类型
			String contentType = connection.getContentType();
			if (contentType == null) {
				switch (Urls.getFileExtension(imageUrl).toLowerCase()) {
				case "png":
					contentType = "image/png";
					break;
				case "jpg":
				case "jpeg":
					contentType = "image/jpeg";
					break;
				case "gif":
					contentType = "image/gif";
					break;
				case "bmp":
					contentType = "image/bmp";
					break;
				case "webp":
					contentType = "image/webp";
					break;
				case "svg":
					contentType = "image/svg+xml";
					break;
				default:
					break;
				}
			}
			if (contentType == null || !contentType.startsWith("image/")) {
				Logger.log(MessageLevel.WARN, "transformer: download image [%s] content type [%s] is not image.",
						imageUrl, contentType);
				return IMAGE_BROKEN_BASE64;
			}
			// 读取图片数据
			try (InputStream inputStream = connection.getInputStream();
					ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
				int bytesRead;
				byte[] bytes = new byte[4096];
				while ((bytesRead = inputStream.read(bytes)) != -1) {
					outputStream.write(bytes, 0, bytesRead);
				}
				bytes = outputStream.toByteArray();
				Logger.log(MessageLevel.DEBUG, "transformer: image [%s] converted to BASE64, size [%s].", imageUrl,
						bytes.length);
				return Bytes.toBase64String(bytes, contentType);
			}
		} catch (Exception e) {
			Logger.log(MessageLevel.WARN, "transformer: download image [%s] error [%s].", imageUrl, e.getMessage());
			return IMAGE_BROKEN_BASE64;
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
	}

	protected void drawElement(Writer writer, IExportTemplateItem template) throws IOException {
		if (DISPLAY_ELEMENT_PDF.equalsIgnoreCase(template.getItemType())) {
			String pdfSrc = this.templateValue(template);
			writer.write("<iframe");
			writer.write(" ");
			writer.write("id=\"");
			writer.write(template.getItemID());
			writer.write("\"");
			writer.write(" ");
			writer.write("src=\"");
			if (pdfSrc != null && !pdfSrc.isEmpty()) {
				writer.write(pdfSrc);
				if (!pdfSrc.contains("#")) {
					writer.write("#view=FitH,0&toolbar=0&scrollbar=0&navpanes=0");
				}
			}
			writer.write("\"");
			writer.write(" ");
			writer.write("scrolling=\"no\"");
			writer.write(" ");
			writer.write("frameborder=\"0\"");
			writer.write(" ");
			writer.write("style=\"");
			writer.write("width:100%;height:100%;border:none;display:block;overflow:hidden;margin:0;padding:0;");
			writer.write("\"");
			writer.write(" ");
			writer.write(">");
			writer.write("</iframe>");
		} else if (DISPLAY_ELEMENT_IMAGE.equalsIgnoreCase(template.getItemType())) {
			String imgSrc = this.templateValue(template);
			// 若开启BASE64嵌入，则下载图片并转为BASE64数据URI
			if (this.isEmbedImage()) {
				imgSrc = this.downloadImageAsBase64(imgSrc);
			}
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
			writer.write(imgSrc);
			writer.write("\"");
			writer.write(" ");
			if (!this.isEmbedImage()) {
				// 默认模式：onerror使用空白SVG占位
				writer.write("onerror=\"this.src='");
				writer.write(IMAGE_BROKEN_BASE64);
				writer.write("';\"");
			}
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
			if (item.getLineLeft() > 0 || item.getLineTop() > 0 || item.getLineRight() > 0
					|| item.getLineBottom() > 0) {
				writer.write("border-style:");
				writer.write(String.valueOf(item.getLineStyle() != null ? item.getLineStyle() : emLineStyle.SOLID)
						.toLowerCase());
				writer.write(";");
				writer.write("border-left-width:");
				writer.write(String.valueOf(item.getLineLeft()));
				writer.write("px;");
				writer.write("border-top-width:");
				writer.write(String.valueOf(item.getLineTop()));
				writer.write("px;");
				writer.write("border-right-width:");
				writer.write(String.valueOf(item.getLineRight()));
				writer.write("px;");
				writer.write("border-bottom-width:");
				writer.write(String.valueOf(item.getLineBottom()));
				writer.write("px;");
				if (item.getBorderRed() > 0 || item.getBorderGreen() > 0 || item.getBorderBlue() > 0) {
					writer.write("border-color:rgb(");
					writer.write(String.valueOf(item.getBorderRed()));
					writer.write(",");
					writer.write(String.valueOf(item.getBorderGreen()));
					writer.write(",");
					writer.write(String.valueOf(item.getBorderBlue()));
					writer.write(");");
				} else {
					writer.write("border-color:black;");
				}
			} else {
				writer.write("border:1px solid black;");
			}
			if (item.getBackgroundRed() > 0 || item.getBackgroundGreen() > 0 || item.getBackgroundBlue() > 0) {
				writer.write("background-color:rgb(");
				writer.write(String.valueOf(item.getBackgroundRed()));
				writer.write(",");
				writer.write(String.valueOf(item.getBackgroundGreen()));
				writer.write(",");
				writer.write(String.valueOf(item.getBackgroundBlue()));
				writer.write(");");
			}
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
		writer.write(String.valueOf(this.getExportTemplate().getRepetitionFooterHeight()));
		writer.write("px;");
		writer.write("border:1px solid black;");
		writer.write("\"");
		writer.write(" ");
		writer.write("colspan=\"");
		writer.write(String.valueOf(this.getExportTemplate().getRepetitions().size()));
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
			if (item.getLineLeft() > 0 || item.getLineTop() > 0 || item.getLineRight() > 0
					|| item.getLineBottom() > 0) {
				writer.write("border-style:");
				writer.write(String.valueOf(item.getLineStyle() != null ? item.getLineStyle() : emLineStyle.SOLID)
						.toLowerCase());
				writer.write(";");
				writer.write("border-left-width:");
				writer.write(String.valueOf(item.getLineLeft()));
				writer.write("px;");
				writer.write("border-top-width:");
				writer.write(String.valueOf(item.getLineTop()));
				writer.write("px;");
				writer.write("border-right-width:");
				writer.write(String.valueOf(item.getLineRight()));
				writer.write("px;");
				writer.write("border-bottom-width:");
				writer.write(String.valueOf(item.getLineBottom()));
				writer.write("px;");
				if (item.getBorderRed() > 0 || item.getBorderGreen() > 0 || item.getBorderBlue() > 0) {
					writer.write("border-color:rgb(");
					writer.write(String.valueOf(item.getBorderRed()));
					writer.write(",");
					writer.write(String.valueOf(item.getBorderGreen()));
					writer.write(",");
					writer.write(String.valueOf(item.getBorderBlue()));
					writer.write(");");
				} else {
					writer.write("border-color:black;");
				}
			} else {
				writer.write("border:1px solid black;");
			}
			if (item.getBackgroundRed() > 0 || item.getBackgroundGreen() > 0 || item.getBackgroundBlue() > 0) {
				writer.write("background-color:rgb(");
				writer.write(String.valueOf(item.getBackgroundRed()));
				writer.write(",");
				writer.write(String.valueOf(item.getBackgroundGreen()));
				writer.write(",");
				writer.write(String.valueOf(item.getBackgroundBlue()));
				writer.write(");");
			}
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
