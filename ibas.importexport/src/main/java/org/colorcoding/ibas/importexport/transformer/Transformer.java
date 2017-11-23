package org.colorcoding.ibas.importexport.transformer;

import org.colorcoding.ibas.bobas.data.ArrayList;
import org.colorcoding.ibas.bobas.data.List;
import org.colorcoding.ibas.bobas.i18n.I18N;

/**
 * 转换者
 * 
 * @author Niuren.Zhu
 *
 */
public abstract class Transformer<IN, OUT> implements ITransformer<IN, OUT> {

	private IN inputData;

	protected IN getInputData() {
		return this.inputData;
	}

	@Override
	public void setInputData(IN data) {
		this.inputData = data;
		this.outputData = null;
	}

	private List<OUT> outputData;

	protected void setOutputData(java.util.List<OUT> data) {
		if (data == null) {
			return;
		}
		List<OUT> tmps = new ArrayList<>();
		tmps.addAll(data);
		this.setOutputData(tmps);
	}

	protected void setOutputData(OUT[] data) {
		if (data == null) {
			return;
		}
		List<OUT> tmps = new ArrayList<>();
		for (OUT out : data) {
			tmps.add(out);
		}
		this.setOutputData(tmps);
	}

	protected void setOutputData(List<OUT> data) {
		if (data == null) {
			return;
		}
		this.outputData = data;
	}

	@Override
	public List<OUT> getOutputData() throws TransformException {
		if (this.outputData == null) {
			throw new TransformException(I18N.prop("msg_ie_invaild_data"));
		}
		return this.outputData;
	}

}
