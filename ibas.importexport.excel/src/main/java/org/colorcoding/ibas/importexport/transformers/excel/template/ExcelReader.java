package org.colorcoding.ibas.importexport.transformers.excel.template;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.colorcoding.ibas.bobas.data.DataConvert;
import org.colorcoding.ibas.bobas.data.DateTime;

/**
 * Excel文件读取者
 * 
 * @author Niuren.Zhu
 *
 */
public class ExcelReader extends FileReader {

	@Override
	public void read(File file) throws ReadFileException, IOException {
		if (this.getTemplate() == null) {
			return;
		}
		Workbook workbook = null;
		try {
			OPCPackage pkg = OPCPackage.open(file);
			workbook = new XSSFWorkbook(pkg);
			if (workbook.getNumberOfSheets() > 0) {
				Sheet sheet = workbook.getSheetAt(0);
				this.getTemplate().setDescription(sheet.getSheetName());
				this.getTemplate().setStartingRow(sheet.getFirstRowNum());
				this.getTemplate().setEndingRow(sheet.getLastRowNum());
				this.resolvingHead(sheet);
				this.resolvingObject(sheet);
				this.resolvingDatas(sheet);
			}
		} catch (Exception e) {
			throw new ReadFileException(e);
		} finally {
			if (workbook != null) {
				workbook.close();
			}
		}
	}

	protected void resolvingHead(Sheet sheet) throws ResolvingException {
		Head head = new Head();
		for (int iRow = this.getTemplate().getStartingRow(); iRow < this.getTemplate().getEndingRow(); iRow++) {
			Row row = sheet.getRow(iRow);
			if (row == null) {
				continue;
			}
			for (int iCol = row.getFirstCellNum(); iCol < row.getLastCellNum(); iCol++) {
				Cell cell = row.getCell(iCol);
				if (cell == null) {
					continue;
				}
				Comment comment = cell.getCellComment();
				if (comment == null) {
					continue;
				}
				if (Template.TEMPLATE_NAME.equals(comment.getAuthor())) {
					// 模板的注释
					try {
						if (head.resolvingNotes(comment.getString().getString())) {
							head.setStartingRow(cell.getRowIndex());
							head.setEndingRow(head.getStartingRow());
							head.setStartingColumn(cell.getColumnIndex());
							head.setEndingColumn(row.getLastCellNum());
							this.getTemplate().setHead(head);
							this.getTemplate().setName(this.getTemplate().getHead().getName());
							this.getTemplate().setStartingColumn(this.getTemplate().getHead().getStartingColumn());
							this.getTemplate().setEndingColumn(this.getTemplate().getHead().getEndingColumn());
							return;
						}
					} catch (Exception e) {
						throw new ResolvingException(e);
					}
				}
			}
		}
		throw new ResolvingException("not found head area.");
	}

	protected void resolvingObject(Sheet sheet) throws ResolvingException {
		Object object = null;
		int propertRow = -1;
		// 解析对象
		for (int iRow = this.getTemplate().getHead().getEndingRow() + 1; iRow < this.getTemplate()
				.getEndingRow(); iRow++) {
			Row row = sheet.getRow(iRow);
			if (row == null) {
				continue;
			}
			for (int iCol = row.getFirstCellNum(); iCol < row.getLastCellNum(); iCol++) {
				Cell cell = row.getCell(iCol);
				if (cell == null) {
					continue;
				}
				Comment comment = cell.getCellComment();
				if (comment == null) {
					continue;
				}
				if (!Template.TEMPLATE_NAME.equals(comment.getAuthor())) {
					continue;
				}
				// 模板的注释
				try {
					if (object == null) {
						object = new Object();
					}
					if (object.resolvingNotes(comment.getString().getString())) {
						object.setStartingRow(cell.getRowIndex());
						object.setEndingRow(object.getStartingRow());
						object.setStartingColumn(cell.getColumnIndex());
						object.setEndingColumn(row.getLastCellNum());
						object.setParent(this.getTemplate());
						if (this.getTemplate().getObjects() != null && this.getTemplate().getObjects().length > 0) {
							// 设置上一个对象终止信息
							this.getTemplate().getObjects()[this.getTemplate().getObjects().length - 1]
									.setEndingColumn(object.getStartingColumn() - 1);
						}
						this.getTemplate().addObject(object);
						object = null;
						// 已找到对象行，则不再处理对象
						propertRow = iRow + 1;
					}
				} catch (Exception e) {
					throw new ResolvingException(e);
				}
			}
			if (propertRow > 0) {
				// 退出对象解析
				break;
			}
		}
		// 解析对象的属性
		for (int iRow = propertRow; iRow < this.getTemplate().getEndingRow(); iRow++) {
			Row row = sheet.getRow(iRow);
			if (row == null) {
				continue;
			}
			for (int iCol = row.getFirstCellNum(); iCol < row.getLastCellNum(); iCol++) {
				Cell cell = row.getCell(iCol);
				if (cell == null) {
					continue;
				}
				Comment comment = cell.getCellComment();
				if (comment == null) {
					continue;
				}
				if (!Template.TEMPLATE_NAME.equals(comment.getAuthor())) {
					continue;
				}
				object = null;// 当前处理的对象
				for (int i = 0; i < this.getTemplate().getObjects().length; i++) {
					Object item = this.getTemplate().getObjects()[i];
					if (i < this.getTemplate().getObjects().length - 1) {
						if (cell.getColumnIndex() >= item.getStartingColumn()
								&& cell.getColumnIndex() <= item.getEndingColumn()) {
							// 单元格为此对象属性
							object = item;
							// 初始化对象属性
							if (object.getProperties() == null || object.getProperties().length == 0) {
								object.setProperties(
										new Property[object.getEndingColumn() - object.getStartingColumn() + 1]);
							}
							break;
						}
					} else {
						// 最后一个对象，单元格可能超过它
						if (cell.getColumnIndex() >= item.getStartingColumn()) {
							// 单元格为此对象属性
							object = item;
							// 初始化对象属性
							if (object.getProperties() == null || object.getProperties().length == 0) {
								object.setProperties(new Property[row.getLastCellNum() - object.getStartingColumn()]);
							}
							break;
						}
					}

				}
				if (object != null) {
					Property property = new Property();
					try {
						if (property.resolvingNotes(comment.getString().getString())) {
							property.setStartingRow(cell.getRowIndex());
							property.setEndingRow(property.getStartingRow());
							property.setStartingColumn(cell.getColumnIndex());
							property.setEndingColumn(property.getStartingColumn());
							property.setParent(object);
							object.getProperties()[cell.getColumnIndex() - object.getStartingColumn()] = property;
							if (property.getStartingColumn() > object.getEndingColumn()) {
								object.setEndingColumn(property.getStartingColumn());
							}
							if (property.getStartingRow() > object.getEndingRow()) {
								object.setEndingRow(property.getEndingRow());
							}
						}
					} catch (Exception e) {
						throw new ResolvingException(e);
					}
				}
			}
		}
		if (this.getTemplate().getObjects() == null || this.getTemplate().getObjects().length == 0) {
			throw new ResolvingException("not found object area.");
		}
		// 设置坐标
		object = this.getTemplate().getObjects()[this.getTemplate().getObjects().length - 1];
		this.getTemplate().getHead().setEndingColumn(object.getEndingColumn());
		this.getTemplate().setEndingColumn(object.getEndingColumn());
		// 初始化数据区
		this.getTemplate().setDatas(new Data());
		this.getTemplate().getDatas().setStartingRow(object.getEndingRow() + 1);
		this.getTemplate().getDatas().setStartingColumn(this.getTemplate().getStartingColumn());
		this.getTemplate().getDatas().setEndingColumn(object.getEndingColumn());
		this.getTemplate().getDatas().setColumnCount(this.getTemplate().getDatas().getEndingColumn()
				- this.getTemplate().getDatas().getStartingColumn() + 1);
	}

	private org.colorcoding.ibas.importexport.transformers.excel.template.Cell createCell(Property property, int row,
			java.lang.Object value) {
		org.colorcoding.ibas.importexport.transformers.excel.template.Cell dataCell = new org.colorcoding.ibas.importexport.transformers.excel.template.Cell();
		dataCell.setParent(property);
		dataCell.setStartingRow(row);
		dataCell.setEndingRow(dataCell.getStartingRow());
		dataCell.setStartingColumn(property.getStartingColumn());
		dataCell.setEndingColumn(dataCell.getStartingColumn());
		dataCell.setValue(value);
		return dataCell;
	}

	protected void resolvingDatas(Sheet sheet) throws ResolvingException {
		for (int iRow = this.getTemplate().getDatas().getStartingRow(); iRow <= this.getTemplate()
				.getEndingRow(); iRow++) {
			Row sheetRow = sheet.getRow(iRow);
			if (sheetRow == null) {
				continue;
			}
			org.colorcoding.ibas.importexport.transformers.excel.template.Cell[] dataRow = this.getTemplate().getDatas()
					.createRow();
			for (Object object : this.getTemplate().getObjects()) {
				for (Property property : object.getProperties()) {
					Cell sheetCell = sheetRow.getCell(property.getStartingColumn());
					if (sheetCell == null) {
						continue;
					}
					org.colorcoding.ibas.importexport.transformers.excel.template.Cell dataCell = null;
					try {
						if (sheetCell.getCellTypeEnum() == CellType.BOOLEAN) {
							if (property.getBindingClass() == Boolean.class) {
								dataCell = this.createCell(property, sheetRow.getRowNum(),
										sheetCell.getBooleanCellValue());
							}
						} else if (sheetCell.getCellTypeEnum() == CellType.NUMERIC) {
							if (property.getBindingClass() == Integer.class || property.getBindingClass() == Short.class
									|| property.getBindingClass() == Long.class
									|| property.getBindingClass() == BigInteger.class) {
								// 整型，去除小数位
								dataCell = this.createCell(property, sheetRow.getRowNum(), DataConvert.convert(
										property.getBindingClass(), Math.round(sheetCell.getNumericCellValue())));
							} else if (property.getBindingClass() == DateTime.class) {
								dataCell = this.createCell(property, sheetRow.getRowNum(),
										DataConvert.convert(property.getBindingClass(), sheetCell.getDateCellValue()));
							} else {
								dataCell = this.createCell(property, sheetRow.getRowNum(), DataConvert
										.convert(property.getBindingClass(), sheetCell.getNumericCellValue()));
							}
						} else if (sheetCell.getCellTypeEnum() == CellType.STRING) {
							String value = sheetCell.getStringCellValue();
							if (value != null && !value.isEmpty()) {
								if (property.getBindingClass() == DateTime.class) {
									dataCell = this.createCell(property, sheetRow.getRowNum(), DateTime.valueOf(value));
								} else {
									dataCell = this.createCell(property, sheetRow.getRowNum(),
											DataConvert.convert(property.getBindingClass(), value));
								}
							}
						}
						dataRow[dataCell.getStartingColumn()] = dataCell;
					} catch (Exception e) {
						throw new ResolvingException(String.format("read cell [%s,%s]'s data error.",
								sheetCell.getRowIndex(), sheetCell.getColumnIndex()), e);
					}
				}
			}
		}
	}
}
