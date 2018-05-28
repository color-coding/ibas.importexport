package org.colorcoding.ibas.importexport.transformer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import org.colorcoding.ibas.bobas.data.emYesNo;
import org.colorcoding.ibas.bobas.i18n.I18N;
import org.colorcoding.ibas.importexport.bo.exporttemplate.IExportTemplateItem;

/**
 * 业务对象转换html文件
 * 
 * @author Niuren.Zhu
 *
 */
@TransformerInfo(name = "TO_FILE_HTML", template = true)
public class TransformerHtml extends TemplateTransformer {

	@Override
	public void transform() throws TransformException {
		if (this.getTemplate() == null) {
			throw new TransformException(I18N.prop("msg_ie_no_template"));
		}
		if (this.getInputData() == null) {
			throw new TransformException(I18N.prop("msg_ie_no_input_data"));
		}
		try {
			File file = new File(String.format("%s%s.html", this.getWorkFolder(), UUID.randomUUID().toString()));
			OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(file), "utf-8");
			if (!file.getParentFile().exists()) {
				file.mkdirs();
			}
			if (!file.exists()) {
				file.createNewFile();
			}
			this.darwPage(writer);
			writer.flush();
			writer.close();
			this.setOutputData(new File[] { file });
		} catch (IOException e) {
			throw new TransformException(e);
		}
	}

	protected void darwPage(Writer writer) throws IOException {
		writer.write("<!DOCTYPE HTML>");
		writer.write("<html>");

		writer.write("<head>");
		writer.write(String.format("<title>%s</title>", this.getTemplate().getName()));
		writer.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />");
		writer.write("<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">");
		writer.write("<meta charset=\"utf-8\" />");
		writer.write("</head>");

		writer.write("<body>");
		int page = 1;
		this.startPage(writer, page);
		// 绘制开始区域
		this.startDiv(writer, String.format("page_%s_startsection", page));
		this.drawArea(writer, this.getTemplate().getStartSections());
		this.endDiv(writer);
		// 绘制重复区域
		this.startTable(writer, String.format("page_%s_repetitionheaders", page),
				this.getTemplate().getRepetitionHeaders());
		this.endTable(writer);
		// 绘制结束区域
		this.startDiv(writer, String.format("page_%s_endsection", page));
		this.drawArea(writer, this.getTemplate().getEndSections());
		this.endDiv(writer);
		page = this.endPage(writer, page);
		writer.write("</body>");

		writer.write("</html>");
	}

	protected void startPage(Writer writer, int page) throws IOException {
		String id = String.format("page_%s", page);
		this.startDiv(writer, id);
		// 页眉
		this.startDiv(writer, String.format("%s_header", id));
		this.drawArea(writer, this.getTemplate().getPageHeaders());
		this.endDiv(writer);
	}

	protected int endPage(Writer writer, int page) throws IOException {
		// 页脚
		String id = String.format("page_%s", page);
		this.startDiv(writer, String.format("%s_footer", id));
		this.drawArea(writer, this.getTemplate().getPageFooters());
		this.endDiv(writer);
		this.endDiv(writer);
		return page++;
	}

	protected void startDiv(Writer writer, String id) throws IOException {
		this.startDiv(writer, id, 0, 0, 0, 0);
	}

	protected void startDiv(Writer writer, String id, int top, int right, int botton, int left) throws IOException {
		writer.write("<div");
		writer.write(" ");
		writer.write("id=\"");
		writer.write(id);
		writer.write("\"");
		writer.write(" ");
		if (top + right + botton + left > 0) {
			writer.write("style=\"");
			writer.write("margin:");
			writer.write(top);
			writer.write("px");
			writer.write(",");
			writer.write(right);
			writer.write("px");
			writer.write(",");
			writer.write(botton);
			writer.write("px");
			writer.write(",");
			writer.write(left);
			writer.write("px");
			writer.write("\"");
			writer.write(" ");
		}
		writer.write(">");
	}

	protected void endDiv(Writer writer) throws IOException {
		writer.write("</div>");
	}

	protected void drawArea(Writer writer, List<IExportTemplateItem> templates) throws IOException {
		if (templates.isEmpty()) {
			return;
		}
		// 内容按左坐标排序
		templates.sort(new Comparator<IExportTemplateItem>() {
			@Override
			public int compare(IExportTemplateItem o1, IExportTemplateItem o2) {
				return o1.getItemLeft() - o2.getItemLeft();
			}
		});
		for (IExportTemplateItem item : templates) {
			if (item.getItemVisible() == emYesNo.NO) {
				continue;
			}
			this.startDiv(writer, item.getItemID());
			this.drawElement(writer, item);
			this.endDiv(writer);
		}
	}

	protected void drawElement(Writer writer, IExportTemplateItem template) throws IOException {
		String eleType = this.elementType(template.getItemType());
		writer.write("<");
		writer.write(eleType);
		writer.write(" ");
		writer.write("id=\"");
		writer.write(template.getItemID());
		writer.write("\"");
		writer.write(" ");
		writer.write(">");
		writer.write(this.elementValue(template));
		writer.write("</");
		writer.write(eleType);
		writer.write(">");
	}

	protected void startTable(Writer writer, String id, List<IExportTemplateItem> templates) throws IOException {
		writer.write("<");
		writer.write("table");
		writer.write(" ");
		writer.write("id=\"");
		writer.write(id);
		writer.write("\"");
		writer.write(" ");
		writer.write(">");
		// 绘制列名
		// 内容按左坐标排序
		templates.sort(new Comparator<IExportTemplateItem>() {
			@Override
			public int compare(IExportTemplateItem o1, IExportTemplateItem o2) {
				return o1.getItemLeft() - o2.getItemLeft();
			}
		});
		writer.write("<tr>");
		for (IExportTemplateItem item : templates) {
			if (item.getItemVisible() == emYesNo.NO) {
				continue;
			}
			writer.write("<th>");
			writer.write(item.getItemString());
			writer.write("</th>");
		}
		writer.write("</tr>");
	}

	protected void endTable(Writer writer) throws IOException {
		writer.write("</table>");
	}

	protected String elementType(String itemType) {
		if (itemType.equalsIgnoreCase("Picture")) {

		}
		return "label";
	}

	protected String elementValue(IExportTemplateItem template) {
		String value = template.getItemString();
		if (value == null) {
			value = "";
		}
		return value;
	}

}
