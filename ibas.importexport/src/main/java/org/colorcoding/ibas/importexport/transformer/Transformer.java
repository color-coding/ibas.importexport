package org.colorcoding.ibas.importexport.transformer;

import java.util.ArrayList;
import java.util.List;

import org.colorcoding.ibas.bobas.i18n.I18N;

/**
 * 转换者
 * 
 * @author Niuren.Zhu
 *
 */
public abstract class Transformer<IN, OUT> implements ITransformer<IN, OUT> {

	private List<IN> inputData;

	protected List<IN> getInputData() {
		return this.inputData;
	}

	/**
	 * 设置输入数据
	 *
	 * @param data
	 */
	public void setInputData(IN[] data) {
		if (data == null) {
			return;
		}
		List<IN> tmps = new ArrayList<>(data.length);
		for (IN in : data) {
			tmps.add(in);
		}
		this.setInputData(tmps);
	}

	@Override
	public void setInputData(List<IN> data) {
		if (data == null) {
			return;
		}
		List<IN> tmps = new ArrayList<>(data.size());
		tmps.addAll(data);
		this.inputData = tmps;
		this.outputData = null;
	}

	@Override
	public void addInputData(IN data) {
		if (data == null) {
			return;
		}
		if (this.inputData == null) {
			this.inputData = new ArrayList<>();
		}
		this.inputData.add(data);
		// 添加新数据时使输出失效
		this.outputData = null;
	}

	/**
	 * 添加输入数据
	 *
	 * @param data
	 */
	public void addInputData(IN[] data) {
		if (data == null) {
			return;
		}
		if (this.inputData == null) {
			this.inputData = new ArrayList<>();
		}
		for (IN in : data) {
			this.inputData.add(in);
		}
		// 添加新数据时使输出失效
		this.outputData = null;
	}

	/**
	 * 添加输入数据
	 *
	 * @param data
	 */
	public void addInputData(List<IN> data) {
		if (data == null) {
			return;
		}
		if (this.inputData == null) {
			this.inputData = new ArrayList<>();
		}
		this.inputData.addAll(data);
		// 添加新数据时使输出失效
		this.outputData = null;
	}

	private List<OUT> outputData;

	protected void setOutputData(List<OUT> data) {
		if (data == null) {
			return;
		}
		List<OUT> tmps = new ArrayList<>(data.size());
		tmps.addAll(data);
		this.outputData = tmps;
	}

	protected void setOutputData(OUT[] data) {
		if (data == null) {
			return;
		}
		List<OUT> tmps = new ArrayList<>(data.length);
		for (OUT out : data) {
			tmps.add(out);
		}
		this.outputData = tmps;
	}

	@Override
	public List<OUT> getOutputData() throws TransformException {
		if (this.outputData == null) {
			throw new TransformException(I18N.prop("msg_ie_no_output_data"));
		}
		return this.outputData;
	}

}
