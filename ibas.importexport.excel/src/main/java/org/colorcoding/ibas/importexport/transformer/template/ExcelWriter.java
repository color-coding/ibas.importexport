package org.colorcoding.ibas.importexport.transformer.template;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.colorcoding.ibas.bobas.common.DateTimes;
import org.colorcoding.ibas.bobas.common.Enums;
import org.colorcoding.ibas.bobas.common.Numbers;
import org.colorcoding.ibas.bobas.common.Strings;
import org.colorcoding.ibas.bobas.data.DateTime;
import org.colorcoding.ibas.bobas.data.KeyValue;

/**
 * excel文件写入
 * 
 * @author Niuren.Zhu
 *
 */
public class ExcelWriter extends FileWriter {

	public static final short COLORS_HEAD = IndexedColors.LIME.getIndex();
	public static final short[] COLORS_OBJECT = new short[] { IndexedColors.TAN.getIndex(),
			IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex(), IndexedColors.LAVENDER.getIndex() };
	public static final short COLORS_PROPERTY = IndexedColors.LIGHT_ORANGE.getIndex();

	/** Excel XLSX最大行索引（0-based） */
	public static final int MAX_ROW_INDEX = 1048575;

	public ExcelWriter() {
		this.setCacheRows(60);
	}

	private int cacheRows;

	public final int getCacheRows() {
		return cacheRows;
	}

	public final void setCacheRows(int cacheRows) {
		this.cacheRows = cacheRows;
	}

	/**
	 * 生成工作表名称
	 *
	 * @param index 工作表索引（0-based，0为首个工作表）
	 * @return 工作表名称（不超过31字符）
	 */
	protected String generateSheetName(int index) {
		String sheetName = this.getTemplate().getDescription() == null ? null
				: this.getTemplate().getDescription().replaceAll("[\\\\/:*?\\[\\]]", "_").trim();
		if (sheetName == null || sheetName.isEmpty()) {
			sheetName = "Sheet";
		}
		if (index > 0) {
			// 后续工作表追加序号后缀，确保名称不超过31字符
			String suffix = "_" + (index + 1);
			if (sheetName.length() + suffix.length() > 31) {
				sheetName = sheetName.substring(0, 31 - suffix.length());
			}
			sheetName = sheetName + suffix;
		} else if (sheetName.length() > 31) {
			sheetName = sheetName.substring(0, 31);
		}
		return sheetName;
	}

	private Workbook workbook;

	protected final Workbook getWorkbook() {
		return workbook;
	}

	protected final void setWorkbook(Workbook workbook) {
		this.workbook = workbook;
	}

	@Override
	@SuppressWarnings("deprecation")
	public void write(File file) throws WriteFileException, IOException {
		if (file.isFile() && file.exists()) {
			if (!file.delete()) {
				throw new WriteFileException(String.format("cannot delete existing file [%s].", file.getPath()));
			}
		}
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		file.createNewFile();
		SXSSFWorkbook workBook = null;
		try (OutputStream stream = new FileOutputStream(file)) {
			workBook = new SXSSFWorkbook(this.getCacheRows());
			String sheetName = this.generateSheetName(0);
			Sheet sheet = workBook.createSheet(sheetName);
			this.setWorkbook(workBook);
			this.writeHead(sheet);
			this.writeObjects(sheet);
			// 冻结头信息
			sheet.createFreezePane(0, this.getTemplate().getDatas().getStartingRow());
			this.writeDatas(sheet);
			workBook.write(stream);
		} catch (Exception e) {
			throw new WriteFileException(e);
		} finally {
			if (workBook != null) {
				workBook.close();
				workBook.dispose();
				this.setWorkbook(null);
			}
			this.clearStyleCache();
		}
	}

	protected void writeHead(Sheet sheet) {
		Head head = this.getTemplate().getHead();
		if (head.getEndingColumn() > head.getStartingColumn()) {
			CellRangeAddress range = new CellRangeAddress(head.getStartingRow(), head.getEndingRow(),
					head.getStartingColumn(), head.getEndingColumn());
			sheet.addMergedRegion(range);
		}
		Row row = sheet.createRow(head.getStartingRow());
		row.setHeight((short) (row.getHeight() * 2));
		Cell cell = row.createCell(head.getStartingColumn());
		cell.setCellValue(head.getDescription());
		// 写入绑定信息
		CreationHelper creationHelper = this.getWorkbook().getCreationHelper();
		Drawing<?> drawing = sheet.createDrawingPatriarch();
		Comment comment = drawing.createCellComment(drawing.createAnchor(0, 0, 0, 0, head.getStartingColumn(),
				head.getStartingRow(), head.getEndingColumn(), head.getEndingRow()));
		comment.setString(creationHelper.createRichTextString(head.bindingNotes()));
		comment.setAuthor(Template.TEMPLATE_NAME);
		cell.setCellComment(comment);
		// 设置单元格样式
		cell.setCellStyle(this.getHeadStyle());
	}

	protected void writeObjects(Sheet sheet) {
		CellRangeAddress range = null;
		Cell cell = null;
		Comment comment = null;
		CreationHelper creationHelper = this.getWorkbook().getCreationHelper();
		Drawing<?> drawing = sheet.createDrawingPatriarch();
		Object[] objects = this.getTemplate().getObjects();
		for (Object object : objects) {
			if (object.getEndingColumn() > object.getStartingColumn()) {
				range = new CellRangeAddress(object.getStartingRow(), object.getEndingRow(),
						object.getStartingColumn(), object.getEndingColumn());
				sheet.addMergedRegion(range);
			}
		}
		Row oRow = sheet.createRow(this.getTemplate().getHead().getEndingRow() + 1);
		oRow.setHeight((short) (oRow.getHeight() * 1.4));
		Row pRow = sheet.createRow(oRow.getRowNum() + 1);
		pRow.setHeight((short) (pRow.getHeight() * 1.6));
		CellStyle styleProperty = this.getPropertyStyle();
		// 写入信息
		for (Object object : objects) {
			if (object.getProperties().length == 0) {
				// 无属性的子对象不输出列
				continue;
			}
			cell = oRow.createCell(object.getStartingColumn());
			cell.setCellValue(object.getDescription());
			// 写入绑定信息
			comment = drawing.createCellComment(drawing.createAnchor(0, 0, 0, 0, object.getStartingColumn(),
					object.getStartingRow(), object.getEndingColumn(), object.getEndingRow()));
			comment.setString(creationHelper.createRichTextString(object.bindingNotes()));
			comment.setAuthor(Template.TEMPLATE_NAME);
			cell.setCellComment(comment);
			// 设置单元格样式
			cell.setCellStyle(this.getObjectStyle(object.getIndex()));
			// 输出属性
			for (Property property : object.getProperties()) {
				cell = pRow.createCell(property.getStartingColumn());
				cell.setCellValue(property.getDescription());
				// 写入绑定信息
				comment = drawing.createCellComment(drawing.createAnchor(0, 0, 0, 0, property.getStartingColumn(),
						property.getStartingRow(), property.getEndingColumn(), property.getEndingRow()));
				comment.setString(creationHelper.createRichTextString(property.bindingNotes()));
				comment.setAuthor(Template.TEMPLATE_NAME);
				cell.setCellComment(comment);
				// 设置单元格样式
				cell.setCellStyle(styleProperty);
			}
		}
	}

	protected void writeDatas(Sheet sheet) {
		int startingRow = this.getTemplate().getDatas().getStartingRow();
		int currentRow = startingRow;
		int sheetIndex = 0;
		// 跟踪所有工作表及其数据行范围，用于设置枚举验证
		List<Sheet> sheets = new ArrayList<>();
		List<int[]> sheetRanges = new ArrayList<>();
		sheets.add(sheet);
		int sheetStartRow = currentRow;
		for (org.colorcoding.ibas.importexport.transformer.template.Cell[] dataRow : this.getTemplate().getDatas()
				.getRows()) {
			if (currentRow > MAX_ROW_INDEX) {
				// 记录当前工作表行范围
				sheetRanges.add(new int[] { sheetStartRow, currentRow - 1 });
				// 超出单张工作表最大行数，创建新工作表
				sheetIndex++;
				String sheetName = this.generateSheetName(sheetIndex);
				sheet = this.getWorkbook().createSheet(sheetName);
				this.writeHead(sheet);
				this.writeObjects(sheet);
				sheet.createFreezePane(0, startingRow);
				currentRow = startingRow;
				sheetStartRow = currentRow;
				sheets.add(sheet);
			}
			Row sheetRow = sheet.createRow(currentRow++);
			for (org.colorcoding.ibas.importexport.transformer.template.Cell dataCell : dataRow) {
				this.writeDataCell(sheet, sheetRow, dataCell);
			}
		}
		// 记录最后一张工作表行范围
		sheetRanges.add(new int[] { sheetStartRow, currentRow - 1 });
		// 设置枚举类型列的数据验证（所有工作表）
		Object[] objects = this.getTemplate().getObjects();
		for (int i = 0; i < sheets.size(); i++) {
			Sheet s = sheets.get(i);
			int[] range = sheetRanges.get(i);
			if (range[1] >= range[0]) {
				for (Object object : objects) {
					for (Property property : object.getProperties()) {
						if (property.getBindingClass() != null && property.getBindingClass().isEnum()) {
							this.setupEnumValidation(s, property, range[0], range[1]);
						}
					}
				}
			}
		}
	}

	/**
	 * 写入单个数据单元格
	 *
	 * @param sheet    工作表
	 * @param sheetRow 行
	 * @param dataCell 数据单元格
	 */
	protected void writeDataCell(Sheet sheet, Row sheetRow,
			org.colorcoding.ibas.importexport.transformer.template.Cell dataCell) {
		if (dataCell == null || dataCell.getParent() == null) {
			return;
		}
		Cell sheetCell = sheetRow.createCell(dataCell.getStartingColumn());
		if (dataCell.getValue() != null) {
			if (dataCell.getParent().getBindingClass() == DateTime.class) {
				// 日期类型值
				if (!DateTimes.VALUE_MIN.equals(dataCell.getValue())) {
					if (dataCell.getValue() instanceof Date) {
						sheetCell.setCellValue((Date) dataCell.getValue());
					} else if (dataCell.getValue() instanceof String
							&& !dataCell.getValue().equals(Strings.VALUE_EMPTY)) {
						sheetCell.setCellValue(DateTimes.valueOf((String) dataCell.getValue()));
					}
				}
			} else if (dataCell.getParent().getBindingClass() == Float.class
					|| dataCell.getParent().getBindingClass() == Double.class
					|| dataCell.getParent().getBindingClass() == BigDecimal.class) {
				// 小数类型
				sheetCell.setCellValue(Numbers.toDouble(dataCell.getValue()));
			} else if (dataCell.getParent().getBindingClass() == Long.class
					|| dataCell.getParent().getBindingClass() == Integer.class
					|| dataCell.getParent().getBindingClass() == Short.class
					|| dataCell.getParent().getBindingClass() == BigInteger.class) {
				// 数值类型
				sheetCell.setCellValue(Numbers.toDouble(dataCell.getValue()));
			} else if (dataCell.getParent().getBindingClass().isEnum()) {
				// 枚举类型
				sheetCell.setCellValue(dataCell.getValue().toString());
			} else {
				// 字符
				sheetCell.setCellValue(dataCell.getValue().toString());
			}
		}
		// 最后设置单元格格式
		sheetCell.setCellStyle(this.getCellStyle(dataCell.getParent()));
	}

	/**
	 * 设置枚举类型列的数据验证
	 *
	 * @param sheet    工作表
	 * @param property 属性
	 * @param startRow 起始行
	 * @param endRow   结束行
	 */
	protected void setupEnumValidation(Sheet sheet, Property property, int startRow, int endRow) {
		KeyValue[] values = Enums.toKeyValues(property.getBindingClass());
		if (values.length > 0) {
			String[] enumKeys = new String[values.length];
			for (int i = 0; i < values.length; i++) {
				enumKeys[i] = values[i].getKey();
			}
			DataValidationHelper dvHelper = sheet.getDataValidationHelper();
			DataValidationConstraint dvConstraint = dvHelper.createExplicitListConstraint(enumKeys);
			CellRangeAddressList regions = new CellRangeAddressList(startRow, endRow,
					property.getStartingColumn(), property.getEndingColumn());
			DataValidation validation = dvHelper.createValidation(dvConstraint, regions);
			sheet.addValidationData(validation);
		}
	}

	private Map<Property, CellStyle> cellStyles;
	/** 缓存-表头样式 */
	private CellStyle headStyle;
	/** 缓存-对象头样式（按颜色索引） */
	private CellStyle[] objectStyles;
	/** 缓存-属性头样式 */
	private CellStyle propertyStyle;

	protected CellStyle getCellStyle(Property property) {
		if (this.cellStyles == null) {
			this.cellStyles = new HashMap<>();
		}
		if (!this.cellStyles.containsKey(property)) {
			CellStyle style = this.getWorkbook().createCellStyle();
			style.setFillForegroundColor(COLORS_OBJECT[property.getParent().getIndex() % COLORS_OBJECT.length]);// 背景色
			style.setFillPattern(FillPatternType.SOLID_FOREGROUND);// 颜色填充方式
			if (property.getBindingClass() == DateTime.class) {
				// 日期类型设置格式
				DataFormat format = this.getWorkbook().createDataFormat();
				style.setDataFormat(format.getFormat(DateTimes.FORMAT_DATE));
			}
			this.cellStyles.put(property, style);
		}
		return this.cellStyles.get(property);
	}

	/**
	 * 获取表头样式（缓存）
	 */
	protected CellStyle getHeadStyle() {
		if (this.headStyle == null) {
			CellStyle style = this.getWorkbook().createCellStyle();
			style.setFillForegroundColor(COLORS_HEAD);
			style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			style.setAlignment(HorizontalAlignment.LEFT);
			style.setVerticalAlignment(VerticalAlignment.CENTER);
			Font font = this.getWorkbook().createFont();
			font.setItalic(true);
			font.setBold(true);
			style.setFont(font);
			this.headStyle = style;
		}
		return this.headStyle;
	}

	/**
	 * 获取对象头样式（按颜色索引缓存，最多 COLORS_OBJECT.length 种）
	 */
	protected CellStyle getObjectStyle(int objectIndex) {
		int colorIndex = objectIndex % COLORS_OBJECT.length;
		if (this.objectStyles == null) {
			this.objectStyles = new CellStyle[COLORS_OBJECT.length];
		}
		if (this.objectStyles[colorIndex] == null) {
			CellStyle style = this.getWorkbook().createCellStyle();
			style.setFillForegroundColor(COLORS_OBJECT[colorIndex]);
			style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			style.setAlignment(HorizontalAlignment.CENTER);
			style.setVerticalAlignment(VerticalAlignment.CENTER);
			Font font = this.getWorkbook().createFont();
			font.setBold(true);
			style.setFont(font);
			this.objectStyles[colorIndex] = style;
		}
		return this.objectStyles[colorIndex];
	}

	/**
	 * 获取属性头样式（缓存）
	 */
	protected CellStyle getPropertyStyle() {
		if (this.propertyStyle == null) {
			CellStyle style = this.getWorkbook().createCellStyle();
			style.setFillForegroundColor(COLORS_PROPERTY);
			style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			style.setAlignment(HorizontalAlignment.CENTER);
			style.setVerticalAlignment(VerticalAlignment.CENTER);
			Font font = this.getWorkbook().createFont();
			font.setBold(true);
			style.setFont(font);
			this.propertyStyle = style;
		}
		return this.propertyStyle;
	}

	/**
	 * 清理样式缓存，释放资源
	 */
	private void clearStyleCache() {
		if (this.cellStyles != null) {
			this.cellStyles = null;
		}
		this.headStyle = null;
		this.objectStyles = null;
		this.propertyStyle = null;
	}

}
