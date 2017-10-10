package org.colorcoding.ibas.importexport.transformer;

import java.util.List;

import org.colorcoding.ibas.bobas.i18n.I18N;
import org.colorcoding.ibas.bobas.util.ArrayList;
import org.colorcoding.ibas.bobas.util.Collection;

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

	private Collection<OUT> outputData;

	protected void setOutputData(List<OUT> data) {
		if (data == null) {
			return;
		}
		Collection<OUT> tmps = new ArrayList<>();
		tmps.addAll(data);
		this.setOutputData(tmps);
	}

	protected void setOutputData(Collection<OUT> data) {
		if (data == null) {
			return;
		}
		this.outputData = data;
	}

	@Override
	public Collection<OUT> getOutputData() throws TransformException {
		if (this.outputData == null) {
			throw new TransformException(I18N.prop("msg_importexport_invaild_data"));
		}
		return this.outputData;
	}

}
