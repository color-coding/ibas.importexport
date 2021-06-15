package org.colorcoding.ibas.importexport.transformer.template;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
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
import org.colorcoding.ibas.bobas.data.DateTime;
import org.colorcoding.ibas.bobas.data.Decimal;
import org.colorcoding.ibas.bobas.data.KeyValue;
import org.colorcoding.ibas.importexport.data.DataConvert;

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

	private Workbook workbook;

	protected final Workbook getWorkbook() {
		return workbook;
	}

	protected final void setWorkbook(Workbook workbook) {
		this.workbook = workbook;
	}

	@Override
	public void write(File file) throws WriteFileException, IOException {
		if (file.isFile() && file.exists()) {
			file.delete();
		}
		file.getParentFile().mkdirs();
		file.createNewFile();
		SXSSFWorkbook workBook = null;
		try (OutputStream stream = new FileOutputStream(file)) {
			workBook = new SXSSFWorkbook(this.getCacheRows());
			Sheet sheet = workBook.createSheet(this.getTemplate().getDescription());
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
			if (this.cellStyles != null) {
				this.cellStyles = null;
			}
		}
	}

	protected void writeHead(Sheet sheet) {
		Head head = this.getTemplate().getHead();
		CellRangeAddress range = new CellRangeAddress(head.getStartingRow(), head.getEndingRow(),
				head.getStartingColumn(), head.getEndingColumn());
		if (head.getEndingColumn() > head.getStartingColumn()) {
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
		CellStyle style = this.getWorkbook().createCellStyle();
		style.setFillForegroundColor(COLORS_HEAD);// 背景色
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);// 颜色填充方式
		style.setAlignment(HorizontalAlignment.LEFT);// 水平居中
		style.setVerticalAlignment(VerticalAlignment.CENTER);// 垂直居中
		// 设置字体
		Font font = this.getWorkbook().createFont();
		font.setItalic(true);
		font.setBold(true);
		style.setFont(font);
		cell.setCellStyle(style);
	}

	protected void writeObjects(Sheet sheet) {
		CellRangeAddress range = null;
		Cell cell = null;
		Comment comment = null;
		CellStyle style = null;
		Font font = null;
		CreationHelper creationHelper = this.getWorkbook().getCreationHelper();
		Drawing<?> drawing = sheet.createDrawingPatriarch();
		for (Object object : this.getTemplate().getObjects()) {
			range = new CellRangeAddress(object.getStartingRow(), object.getEndingRow(), object.getStartingColumn(),
					object.getEndingColumn());
			if (object.getEndingColumn() > object.getStartingColumn()) {
				sheet.addMergedRegion(range);
			}
		}
		Row oRow = sheet.createRow(this.getTemplate().getHead().getEndingRow() + 1);
		oRow.setHeight((short) (oRow.getHeight() * 1.4));
		Row pRow = sheet.createRow(oRow.getRowNum() + 1);
		pRow.setHeight((short) (pRow.getHeight() * 1.6));
		// 初始化属性单元格格式
		CellStyle styleProperty = this.getWorkbook().createCellStyle();
		styleProperty.setFillForegroundColor(COLORS_PROPERTY);// 背景色
		styleProperty.setFillPattern(FillPatternType.SOLID_FOREGROUND);// 颜色填充方式
		styleProperty.setAlignment(HorizontalAlignment.CENTER);// 水平居中
		styleProperty.setVerticalAlignment(VerticalAlignment.CENTER);// 垂直居中
		// 设置字体
		font = this.getWorkbook().createFont();
		font.setBold(true);
		styleProperty.setFont(font);
		// 写入信息
		for (Object object : this.getTemplate().getObjects()) {
			cell = oRow.createCell(object.getStartingColumn());
			cell.setCellValue(object.getDescription());
			// 写入绑定信息
			comment = drawing.createCellComment(drawing.createAnchor(0, 0, 0, 0, object.getStartingColumn(),
					object.getStartingRow(), object.getEndingColumn(), object.getEndingRow()));
			comment.setString(creationHelper.createRichTextString(object.bindingNotes()));
			comment.setAuthor(Template.TEMPLATE_NAME);
			cell.setCellComment(comment);
			// 设置单元格样式
			style = this.getWorkbook().createCellStyle();
			style.setFillForegroundColor(COLORS_OBJECT[object.getIndex() % COLORS_OBJECT.length]);// 背景色
			style.setFillPattern(FillPatternType.SOLID_FOREGROUND);// 颜色填充方式
			style.setAlignment(HorizontalAlignment.CENTER);// 水平居中
			style.setVerticalAlignment(VerticalAlignment.CENTER);// 垂直居中
			// 设置字体
			font = this.getWorkbook().createFont();
			font.setBold(true);
			style.setFont(font);
			cell.setCellStyle(style);
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
		for (int iRow = this.getTemplate().getDatas().getStartingRow(); iRow <= this.getTemplate().getDatas()
				.getEndingRow(); iRow++) {
			int index = iRow - this.getTemplate().getDatas().getStartingRow();
			org.colorcoding.ibas.importexport.transformer.template.Cell[] dataRow = this.getTemplate().getDatas()
					.getRows().get(index);
			Row sheetRow = sheet.createRow(iRow);
			for (org.colorcoding.ibas.importexport.transformer.template.Cell dataCell : dataRow) {
				if (dataCell == null || dataCell.getParent() == null) {
					continue;
				}
				Cell sheetCell = sheetRow.createCell(dataCell.getStartingColumn());
				if (dataCell.getValue() != null) {
					if (dataCell.getParent().getBindingClass() == DateTime.class) {
						// 日期类型值
						if (!DateTime.MIN_VALUE.equals(dataCell.getValue())) {
							sheetCell.setCellValue((Date) dataCell.getValue());
						}
					} else if (dataCell.getParent().getBindingClass() == Decimal.class
							|| dataCell.getParent().getBindingClass() == Float.class
							|| dataCell.getParent().getBindingClass() == Double.class
							|| dataCell.getParent().getBindingClass() == BigDecimal.class) {
						// 小数类型
						sheetCell.setCellType(CellType.NUMERIC);
						sheetCell.setCellValue(Double.valueOf(dataCell.getValue().toString()));
					} else if (dataCell.getParent().getBindingClass() == Long.class
							|| dataCell.getParent().getBindingClass() == Integer.class
							|| dataCell.getParent().getBindingClass() == Short.class
							|| dataCell.getParent().getBindingClass() == BigInteger.class) {
						// 数值类型
						sheetCell.setCellType(CellType.NUMERIC);
						sheetCell.setCellValue(Double.valueOf(dataCell.getValue().toString()));
					} else if (dataCell.getParent().getBindingClass().isEnum()) {
						// 枚举类型
						if (this.cellStyles == null || !this.cellStyles.containsKey(dataCell.getParent())) {
							// 此列第一次初始化，设置枚举可选值
							KeyValue[] values = DataConvert.toKeyValues(dataCell.getParent().getBindingClass());
							if (values.length > 0) {
								Function<KeyValue[], String[]> toStrings = new Function<KeyValue[], String[]>() {

									@Override
									public String[] apply(KeyValue[] t) {
										String[] values = new String[t.length];
										for (int i = 0; i < values.length; i++) {
											values[i] = t[i].getKey();
										}
										return values;
									}
								};
								DataValidationHelper dvHelper = sheet.getDataValidationHelper();
								DataValidationConstraint dvConstraint = dvHelper
										.createExplicitListConstraint(toStrings.apply(values));
								CellRangeAddressList regions = new CellRangeAddressList(
										this.getTemplate().getDatas().getStartingRow(),
										this.getTemplate().getDatas().getEndingRow(),
										dataCell.getParent().getStartingColumn(),
										dataCell.getParent().getEndingColumn());
								DataValidation validation = dvHelper.createValidation(dvConstraint, regions);
								sheet.addValidationData(validation);
							}
						}
						sheetCell.setCellValue(dataCell.getValue().toString());
					} else {
						// 字符
						sheetCell.setCellValue(dataCell.getValue().toString());
					}
				}
				// 最后设置单元格格式
				sheetCell.setCellStyle(this.getCellStyle(dataCell.getParent()));
			}
		}
	}

	private Map<Property, CellStyle> cellStyles;

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
				style.setDataFormat(format.getFormat(DateTime.FORMAT_DATE));
			}
			this.cellStyles.put(property, style);
		}
		return this.cellStyles.get(property);
	}

}
