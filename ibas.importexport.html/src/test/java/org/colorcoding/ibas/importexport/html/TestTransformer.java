package org.colorcoding.ibas.importexport.html;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.colorcoding.ibas.bobas.common.Criteria;
import org.colorcoding.ibas.bobas.common.ICondition;
import org.colorcoding.ibas.bobas.common.ICriteria;
import org.colorcoding.ibas.bobas.common.IOperationResult;
import org.colorcoding.ibas.bobas.common.ISort;
import org.colorcoding.ibas.bobas.common.SortType;
import org.colorcoding.ibas.bobas.data.emYesNo;
import org.colorcoding.ibas.bobas.organization.OrganizationFactory;
import org.colorcoding.ibas.importexport.MyConfiguration;
import org.colorcoding.ibas.importexport.bo.exporttemplate.ExportTemplate;
import org.colorcoding.ibas.importexport.bo.exporttemplate.IExportTemplate;
import org.colorcoding.ibas.importexport.repository.BORepositoryImportExport;
import org.colorcoding.ibas.importexport.transformer.TransformerHtml;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.InvalidJsonException;
import com.jayway.jsonpath.JsonPath;

import junit.framework.TestCase;

public class TestTransformer extends TestCase {

	public void testTransform() throws Exception {
		ICriteria criteria = new Criteria();
		criteria.setResultCount(1);
		ICondition condition = criteria.getConditions().create();
		condition.setAlias(ExportTemplate.PROPERTY_ACTIVATED.getName());
		condition.setValue(emYesNo.YES);
		condition = criteria.getConditions().create();
		condition.setAlias(ExportTemplate.PROPERTY_BOCODE.getName());
		condition.setValue(MyConfiguration.applyVariables("${Company}_SL_SALESQUOTE"));
		ISort sort = criteria.getSorts().create();
		sort.setAlias(ExportTemplate.PROPERTY_OBJECTKEY.getName());
		sort.setSortType(SortType.DESCENDING);
		try (BORepositoryImportExport boRepository = new BORepositoryImportExport()) {
			boRepository.setUserToken(OrganizationFactory.SYSTEM_USER.getToken());
			IOperationResult<IExportTemplate> opRstl = boRepository.fetchExportTemplate(criteria);
			if (opRstl.getError() != null) {
				throw opRstl.getError();
			}

			TransformerHtml transformer = new TransformerHtml();
			transformer.setExportTemplate(opRstl.getResultObjects().firstOrDefault());
			transformer.setInputData(new FileInputStream(
					String.format("%s%stest_salesquote.json", MyConfiguration.getWorkFolder(), File.separator)));
			transformer.setWorkFolder(MyConfiguration.getWorkFolder());
			transformer.transform();
			for (File item : transformer.getOutputData()) {
				File file = new File(String.format("%s%sout.html", item.getParentFile().getPath(), File.separator));
				file.delete();
				item.renameTo(file);
				System.out.println(String.format("out: %s", file.getPath()));
			}
		}
	}

	public void testJsonPath() throws InvalidJsonException, FileNotFoundException {
		DocumentContext context = JsonPath.parse(new FileInputStream(
				String.format("%s%stest_salesquote.json", MyConfiguration.getWorkFolder(), File.separator)));
		Integer count = context.read("$.length()");
		for (int i = 0; i < count; i++) {
			System.out.println(String.format("data %s/%s", i, count));
			System.out.println((String) context.read(String.format("$[%s].CustomerName", i)));
			Integer subCount = context.read(String.format("$[%s].SalesQuoteItems.length()", i));
			for (int j = 0; j < subCount; j++) {
				System.out.println(
						(String) context.read(String.format("$[%s].SalesQuoteItems[%s].ItemDescription", i, j)));
			}
		}
	}
}
