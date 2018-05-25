package org.colorcoding.ibas.importexport.html;

import java.io.File;
import java.io.FileInputStream;

import org.colorcoding.ibas.bobas.common.Criteria;
import org.colorcoding.ibas.bobas.common.ICondition;
import org.colorcoding.ibas.bobas.common.ICriteria;
import org.colorcoding.ibas.bobas.common.IOperationResult;
import org.colorcoding.ibas.bobas.data.emYesNo;
import org.colorcoding.ibas.bobas.organization.OrganizationFactory;
import org.colorcoding.ibas.importexport.MyConfiguration;
import org.colorcoding.ibas.importexport.bo.exporttemplate.ExportTemplate;
import org.colorcoding.ibas.importexport.bo.exporttemplate.IExportTemplate;
import org.colorcoding.ibas.importexport.repository.BORepositoryImportExport;
import org.colorcoding.ibas.importexport.transformer.TransformerHtml;

import junit.framework.TestCase;

public class testTransformer extends TestCase {

	public void testTransform() throws Exception {
		ICriteria criteria = new Criteria();
		ICondition condition = criteria.getConditions().create();
		condition.setAlias(ExportTemplate.PROPERTY_ACTIVATED.getName());
		condition.setValue(emYesNo.YES);
		condition = criteria.getConditions().create();
		condition.setAlias(ExportTemplate.PROPERTY_OBJECTKEY.getName());
		condition.setValue(1);
		BORepositoryImportExport boRepository = new BORepositoryImportExport();
		boRepository.setUserToken(OrganizationFactory.SYSTEM_USER.getToken());
		IOperationResult<IExportTemplate> opRstl = boRepository.fetchExportTemplate(criteria);
		if (opRstl.getError() != null) {
			throw opRstl.getError();
		}

		TransformerHtml transformer = new TransformerHtml();
		transformer.setTemplate(opRstl.getResultObjects().firstOrDefault());
		transformer.setInputData(new FileInputStream(
				String.format("%s%stest_salesquote.json", MyConfiguration.getWorkFolder(), File.separator)));
		transformer.setWorkFolder(MyConfiguration.getWorkFolder());
		transformer.transform();
		for (File item : transformer.getOutputData()) {
			System.out.println(String.format("out: %s", item.getPath()));
		}
	}
}
