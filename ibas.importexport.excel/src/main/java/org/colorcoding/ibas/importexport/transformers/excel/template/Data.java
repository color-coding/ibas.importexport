package org.colorcoding.ibas.importexport.transformers.excel.template;

import java.util.ArrayList;
import java.util.List;

/**
 * 模板-数据区，属性值
 * 
 * @author Niuren.Zhu
 *
 */
public class Data extends Area<Template> {

	public static final String DEFAULT_NAME = "DATA_AREA";

	public Data() {
		this.setName(DEFAULT_NAME);
	}

	@Override
	public int getIndex() {
		if (this.getParent() instanceof Template) {
			Template parent = (Template) this.getParent();
			if (parent.getDatas() == this) {
				return 0;
			}
		}
		return -1;
	}

	private List<Cell[]> rows;

	public final List<Cell[]> getRows() {
		if (this.rows == null) {
			this.rows = new ArrayList<>();
		}
		return rows;
	}

	public Cell[] getRow(int index) {
		return this.getRows().get(index);
	}

	public Iterator<Cell[]> getRowIterator() {
		return new Iterator<Cell[]>() {

			int index = 0;

			@Override
			public boolean hasNext() {
				if (this.index >= 0 && this.index < Data.this.getRows().size()) {
					return true;
				}
				return false;
			}

			@Override
			public Cell[] next() {
				Cell[] row = Data.this.getRows().get(this.index);
				this.index += 1;
				return row;
			}

			@Override
			public void back() {
				if (this.index > 0) {
					this.index -= 1;
				}
			}

		};
	}

	final void setRows(List<Cell[]> rows) {
		this.rows = rows;
	}

	private int columnCount;

	public int getColumnCount() {
		return this.columnCount;
	}

	final void setColumnCount(int columnCount) {
		this.columnCount = columnCount;
	}

	public Cell[] createRow() {
		Cell[] row = new Cell[this.getColumnCount()];
		this.getRows().add(row);
		this.setEndingRow(this.getStartingRow() - 1 + this.getRows().size());
		return row;
	}

	@Override
	public String toString() {
		return String.format("{data: %s}", super.toString());
	}
}
