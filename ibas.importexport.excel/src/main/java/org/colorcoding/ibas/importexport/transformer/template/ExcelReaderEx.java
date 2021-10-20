package org.colorcoding.ibas.importexport.transformer.template;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackageAccess;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.util.CellAddress;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.util.SAXHelper;
import org.apache.poi.xssf.eventusermodel.ReadOnlySharedStringsTable;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler.SheetContentsHandler;
import org.apache.poi.xssf.usermodel.XSSFComment;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.colorcoding.ibas.bobas.data.DataConvert;
import org.colorcoding.ibas.bobas.data.DateTime;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

/**
 * Excel文件读取者（xml事件驱动模式）
 * 
 * @author Niuren.Zhu
 *
 */
public class ExcelReaderEx extends FileReader implements SheetContentsHandler {

	@Override
	public void read(File file) throws ReadFileException, IOException {
		if (this.getTemplate() == null) {
			return;
		}
		try (OPCPackage pkg = OPCPackage.open(file.getPath(), PackageAccess.READ)) {
			ReadOnlySharedStringsTable strings = new ReadOnlySharedStringsTable(pkg);
			XSSFReader xssfReader = new XSSFReader(pkg);
			XSSFReader.SheetIterator sheetIterator = (XSSFReader.SheetIterator) xssfReader.getSheetsData();
			if (sheetIterator.hasNext()) {
				try (InputStream stream = sheetIterator.next()) {
					InputSource sheetSource = new InputSource(stream);
					try {
						XMLReader sheetParser = SAXHelper.newXMLReader();
						ContentHandler handler = new XSSFSheetXMLHandler(xssfReader.getStylesTable(),
								sheetIterator.getSheetComments(), strings, this, new DataFormatter(), false);
						sheetParser.setContentHandler(handler);
						sheetParser.parse(sheetSource);
					} catch (ParserConfigurationException e) {
						throw new RuntimeException("SAX parser appears to be broken - " + e.getMessage());
					}
				}
			}
		} catch (Exception e) {
			throw new ReadFileException(e);
		}
	}

	private int currentRow = -1;
	private int currentCol = -1;
	private List<Cell> currentCells;

	private class Cell {
		public String value;
		public String comment;
	}

	@Override
	public void startRow(int rowNum) {
		currentRow = rowNum;
		currentCol = -1;
		if (this.currentCells == null) {
			this.currentCells = new ArrayList<>();
		}
		if (this.currentCells.size() > 0) {
			this.currentCells.clear();
		}
	}

	@Override
	public void endRow(int rowNum) {
		try {
			if (this.currentCells == null || this.currentCells.isEmpty()) {
				return;
			}
			if (this.currentRow == 0) {
				// class line
				this.resolvingHead(this.currentCells);
			} else if (this.currentRow == 1) {
				// bo line
				this.resolvingObject(this.currentCells);
			} else if (this.currentRow == 2) {
				// property line
				if (this.getTemplate().getObjects() != null && this.getTemplate().getObjects().length > 0) {
					// 根据属性修正对象列长度
					Object object = this.getTemplate().getObjects()[this.getTemplate().getObjects().length - 1];
					object.setEndingColumn(this.currentCells.size() - 1);
				}
				this.resolvingProperty(this.currentCells);
			} else {
				// data line
				this.resolvingData(this.currentCells);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void cell(String cellReference, String formattedValue, XSSFComment comment) {
		if (formattedValue == null) {
			return;
		}
		if (cellReference == null) {
			cellReference = new CellAddress(currentRow, currentCol).formatAsString();
		}
		int thisCol = (new CellReference(cellReference)).getCol();
		Cell cell = new Cell();
		cell.value = formattedValue;
		if (cell.value != null && cell.value.startsWith("\"") && cell.value.endsWith("\"")) {
			cell.value = cell.value.substring(1, cell.value.length() - 1);
		}
		if (comment != null) {
			XSSFRichTextString richText = comment.getString();
			if (richText != null) {
				cell.comment = richText.getString();
			}
		}
		int missedCols = thisCol - this.currentCol - 1;
		this.currentCol = thisCol;
		for (int i = 0; i < missedCols; i++) {
			this.currentCells.add(null);
		}
		this.currentCells.add(cell);
	}

	@Override
	public void headerFooter(String text, boolean isHeader, String tagName) {
	}

	protected void resolvingHead(List<Cell> cells) throws ResolvingException {
		Head head = new Head();
		for (int iCol = 0; iCol < cells.size(); iCol++) {
			Cell cell = cells.get(iCol);
			if (cell == null) {
				continue;
			}
			if (cell.comment == null) {
				continue;
			}
			try {
				if (head.resolvingNotes(cell.comment)) {
					head.setStartingRow(this.currentRow);
					head.setEndingRow(this.currentRow);
					head.setStartingColumn(0);
					this.getTemplate().setHead(head);
					this.getTemplate().setName(this.getTemplate().getHead().getName());
					this.getTemplate().setStartingColumn(this.getTemplate().getHead().getStartingColumn());
					return;
				}
			} catch (Exception e) {
				throw new ResolvingException(e);
			}
			throw new ResolvingException("not found head area.");
		}
	}

	protected void resolvingObject(List<Cell> cells) throws ResolvingException {
		// 解析对象
		for (int iCol = 0; iCol < cells.size(); iCol++) {
			Cell cell = cells.get(iCol);
			if (cell == null) {
				continue;
			}
			if (cell.comment == null) {
				continue;
			}
			// 模板的注释
			try {
				Object object = new Object();
				if (object.resolvingNotes(cell.comment)) {
					object.setStartingRow(this.currentRow);
					object.setEndingRow(this.currentRow);
					object.setStartingColumn(iCol);
					object.setEndingColumn(cells.size() - 1);
					object.setParent(this.getTemplate());
					if (this.getTemplate().getObjects() != null && this.getTemplate().getObjects().length > 0) {
						// 设置上一个对象终止信息
						this.getTemplate().getObjects()[this.getTemplate().getObjects().length - 1]
								.setEndingColumn(iCol - 1);
					}
					this.getTemplate().addObject(object);
				}
			} catch (Exception e) {
				throw new ResolvingException(e);
			}
		}
	}

	protected void resolvingProperty(List<Cell> cells) throws ResolvingException {
		Object object = null;
		// 解析对象
		for (int iCol = 0; iCol < cells.size(); iCol++) {
			Cell cell = cells.get(iCol);
			if (cell == null) {
				continue;
			}
			if (cell.comment == null) {
				continue;
			}
			for (int i = 0; i < this.getTemplate().getObjects().length; i++) {
				Object item = this.getTemplate().getObjects()[i];
				if (i < this.getTemplate().getObjects().length - 1) {
					if (iCol >= item.getStartingColumn() && iCol <= item.getEndingColumn()) {
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
					if (iCol >= item.getStartingColumn()) {
						// 单元格为此对象属性
						object = item;
						// 初始化对象属性
						if (object.getProperties() == null || object.getProperties().length == 0) {
							object.setProperties(new Property[cells.size() - object.getStartingColumn()]);
						}
						break;
					}
				}

			}
			if (object != null) {
				try {
					Property property = new Property();
					if (property.resolvingNotes(cell.comment)) {
						property.setStartingRow(this.currentRow);
						property.setEndingRow(property.getStartingRow());
						property.setStartingColumn(iCol);
						property.setEndingColumn(property.getStartingColumn());
						property.setParent(object);
						object.getProperties()[iCol - object.getStartingColumn()] = property;
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

	protected void resolvingData(List<Cell> cells) throws ResolvingException {
		org.colorcoding.ibas.importexport.transformer.template.Cell[] dataRow = this.getTemplate().getDatas()
				.createRow();
		for (Object object : this.getTemplate().getObjects()) {
			if (object == null) {
				continue;
			}
			for (Property property : object.getProperties()) {
				if (property == null) {
					continue;
				}
				if (property.getEndingColumn() >= cells.size()) {
					// 超出范围
					continue;
				}
				Cell sheetCell = cells.get(property.getStartingColumn());
				if (sheetCell == null) {
					continue;
				}
				if (sheetCell.value == null) {
					continue;
				}
				try {
					org.colorcoding.ibas.importexport.transformer.template.Cell dataCell = null;
					if (property.getBindingClass() == Boolean.class) {
						dataCell = this.createCell(property, this.currentRow, Boolean.valueOf(sheetCell.value));
					} else if (property.getBindingClass() == Integer.class || property.getBindingClass() == Short.class
							|| property.getBindingClass() == Long.class
							|| property.getBindingClass() == BigInteger.class) {
						// 整型，去除小数位
						dataCell = this.createCell(property, this.currentRow, DataConvert
								.convert(property.getBindingClass(), Math.round(Double.valueOf(sheetCell.value))));
					} else if (property.getBindingClass() == DateTime.class) {
						if (DataConvert.isNumeric(sheetCell.value)) {
							dataCell = this.createCell(property, this.currentRow,
									DateTime.valueOf(HSSFDateUtil.getJavaDate(Double.valueOf(sheetCell.value))));
						} else {
							dataCell = this.createCell(property, this.currentRow, DateTime.valueOf(sheetCell.value));
						}
					} else if (property.getBindingClass() == String.class) {
						dataCell = this.createCell(property, this.currentRow, sheetCell.value);
					} else {
						dataCell = this.createCell(property, this.currentRow,
								DataConvert.convert(property.getBindingClass(), sheetCell.value));
					}
					dataRow[property.getStartingColumn()] = dataCell;
				} catch (Exception e) {
					throw new ResolvingException(String.format("read cell [%s,%s]'s data error.", this.currentRow,
							property.getEndingColumn()), e);
				}
			}
		}
	}

	private org.colorcoding.ibas.importexport.transformer.template.Cell createCell(Property property, int row,
			java.lang.Object value) {
		org.colorcoding.ibas.importexport.transformer.template.Cell dataCell = new org.colorcoding.ibas.importexport.transformer.template.Cell();
		dataCell.setParent(property);
		dataCell.setStartingRow(row);
		dataCell.setEndingRow(dataCell.getStartingRow());
		dataCell.setStartingColumn(property.getStartingColumn());
		dataCell.setEndingColumn(dataCell.getStartingColumn());
		dataCell.setValue(value);
		return dataCell;
	}
}
