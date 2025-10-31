package org.colorcoding.ibas.importexport.transformer;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.colorcoding.ibas.bobas.common.DateTimes;
import org.colorcoding.ibas.bobas.common.Decimals;
import org.colorcoding.ibas.bobas.common.IOperationResult;
import org.colorcoding.ibas.bobas.common.OperationResult;
import org.colorcoding.ibas.bobas.data.IDataTable;
import org.colorcoding.ibas.bobas.db.DbTransaction;
import org.colorcoding.ibas.bobas.db.SqlStatement;
import org.colorcoding.ibas.bobas.message.Logger;
import org.colorcoding.ibas.bobas.message.MessageLevel;
import org.colorcoding.ibas.bobas.repository.BORepositoryService;
import org.colorcoding.ibas.importexport.data.DataConvert;

public abstract class TemplateTransformer extends Transformer<InputStream, File> implements ITemplateTransformer {

	private String template;

	public final String getTemplate() {
		return template;
	}

	public final void setTemplate(String template) {
		this.template = template;
	}

	private String workFolder;

	public void setWorkFolder(String folder) {
		this.workFolder = folder;
	}

	public String getWorkFolder() {
		if (this.workFolder != null && !this.workFolder.isEmpty()) {
			if (!this.workFolder.endsWith(File.separator)) {
				this.workFolder += File.separator;
			}
		}
		return this.workFolder;
	}

	private Map<String, Object> params;

	/**
	 * 新变量值
	 * 
	 * @param name  变量名
	 * @param value 值
	 */
	protected <T> void newParam(String name, T value) {
		if (this.params == null) {
			this.params = new HashMap<>();
		}
		this.params.put(name, value);
		Logger.log(MessageLevel.DEBUG, "transformer: new param [%s], value [%s].", name, value);
	}

	/**
	 * 获取变量值
	 * 
	 * @param name     名称
	 * @param defaults 未取到的默认值
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected <T> T paramValue(String name, T defaults) {
		if (this.params == null) {
			return defaults;
		}
		Object value = this.params.get(name);
		if (value == null) {
			return defaults;
		}
		return (T) value;
	}

	/**
	 * 格式化值
	 * 
	 * @param value  值
	 * @param format 格式
	 * @return
	 */
	protected String formatValue(Object value, String format) {
		if (value == null) {
			return "";
		}
		if (format == null || format.isEmpty()) {
			return String.valueOf(value);
		}
		int pIndex, sIndex;
		// String.format 转换
		pIndex = format.indexOf("%");
		if (pIndex >= 0) {
			// 日期类型转换
			sIndex = format.indexOf("t");
			if (sIndex > 0 && sIndex > pIndex) {
				if (value instanceof String) {
					value = DateTimes.valueOf((String) value);
				} else if (value instanceof Long) {
					value = DateTimes.valueOf((Long) value);
				} else if (value instanceof Integer) {
					value = DateTimes.valueOf(Long.valueOf(value.toString()));
				}
			}
			// 整数类型转换
			sIndex = format.indexOf("d");
			if (sIndex > 0 && sIndex > pIndex) {
				value = Long.valueOf(value.toString());
			}
			// 小数类型转换
			sIndex = format.indexOf("f");
			if (sIndex > 0 && sIndex > pIndex) {
				value = Double.valueOf(value.toString());
			}
			// 人民币转换
			sIndex = format.indexOf("￥");
			if (sIndex > 0 && sIndex > pIndex) {
				value = DataConvert.toChineseYuan(Decimals.valueOf(value.toString()));
				format = format.replace("￥", "s");
			}
			// 字符串格式化
			return String.format(format, value);
		}
		return String.valueOf(value);
	}

	/**
	 * 获取语句值
	 * 
	 * @param query    语句
	 * @param defaults 未取到的默认值
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected <T> T queryValue(String query, T defaults) {
		if (query == null || query.isEmpty()) {
			return defaults;
		}
		try (BORepositoryQuery boRepository = new BORepositoryQuery()) {
			IOperationResult<IDataTable> operationResult = boRepository.query(new SqlStatement(query));
			if (operationResult.getError() != null) {
				throw operationResult.getError();
			}
			IDataTable table = operationResult.getResultObjects().firstOrDefault();
			if (table != null) {
				return (T) table.getRows().get(0).getValue(0);
			}
		} catch (Exception e) {
			Logger.log(MessageLevel.WARN, e);
		}
		return defaults;
	}

	protected abstract <T> T dataValue(String name, T defaults);

	private class BORepositoryQuery extends BORepositoryService {

		public IOperationResult<IDataTable> query(SqlStatement sqlStatement) {
			try {
				if (this.getTransaction() == null) {
					this.connect();
				}
				if (this.getTransaction() instanceof DbTransaction) {
					DbTransaction transaction = (DbTransaction) this.getTransaction();
					return new OperationResult<IDataTable>().addResultObjects(transaction.fetch(sqlStatement));
				} else {
					return new OperationResult<>();
				}
			} catch (Exception e) {
				return new OperationResult<>(e);
			}
		}

	}
}
