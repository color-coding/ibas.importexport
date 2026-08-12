package org.colorcoding.ibas.importexport.test.transformer;

import java.util.ArrayList;
import java.util.List;

import org.colorcoding.ibas.importexport.transformer.TransformException;
import org.colorcoding.ibas.importexport.transformer.Transformer;

/**
 * 转换者-输入输出生命周期 测试。
 *
 * <p>覆盖：TF-T01 ~ TF-T05</p>
 * <p>核心：{@link Transformer} 的输入管理、输出失效、空值容错</p>
 */
public class TestTransformerLifecycle extends AbstractTransformerTestCase {

	/** 测试桩：字符串转换器，将输入加方括号后作为输出 */
	private static class StringTransformer extends Transformer<String, String> {
		int transformCount = 0;

		@Override
		public void transform() throws TransformException {
			this.transformCount++;
			if (this.getInputData() == null || this.getInputData().isEmpty()) {
				return;
			}
			List<String> out = new ArrayList<>();
			for (String s : this.getInputData()) {
				out.add("[" + s + "]");
			}
			this.setOutputData(out);
		}
	}

	// ==================================================================
	// TF-T01: setInputData 后未 transform，getOutputData 抛异常
	// ==================================================================

	public void testTF_T01_GetOutputBeforeTransform_Throws() throws Exception {
		StringTransformer transformer = new StringTransformer();
		List<String> input = new ArrayList<>();
		input.add("a");
		transformer.setInputData(input);

		try {
			transformer.getOutputData();
			fail("Expected TransformException when getting output before transform.");
		} catch (TransformException e) {
			// expected: outputData is null
		}
	}

	// ==================================================================
	// TF-T02: addInputData 使输出失效，需重新 transform
	// ==================================================================

	public void testTF_T02_AddInputInvalidatesOutput() throws Exception {
		StringTransformer transformer = new StringTransformer();
		transformer.addInputData("a");
		transformer.addInputData("b");
		transformer.transform();
		assertEquals("Output size before add.", 2, transformer.getOutputData().size());

		// 添加新数据，输出应失效
		transformer.addInputData("c");
		try {
			transformer.getOutputData();
			fail("Expected TransformException after addInputData invalidated output.");
		} catch (TransformException e) {
			// expected: output invalidated
		}

		// 重新转换后可用
		transformer.transform();
		assertEquals("Output size after re-transform.", 3, transformer.getOutputData().size());
		assertEquals("Transform called twice.", 2, transformer.transformCount);
	}

	// ==================================================================
	// TF-T03: 空值输入被忽略（setInputData(null) / addInputData(null)）
	// ==================================================================

	public void testTF_T03_NullInputIgnored() throws Exception {
		StringTransformer transformer = new StringTransformer();

		// null 列表被忽略
		transformer.setInputData((List<String>) null);
		transformer.transform();
		try {
			transformer.getOutputData();
			fail("Expected TransformException when input was null.");
		} catch (TransformException e) {
			// expected: no input -> no output
		}

		// null 单项被忽略
		transformer.addInputData((String) null);
		transformer.transform();
		try {
			transformer.getOutputData();
			fail("Expected TransformException when only null was added.");
		} catch (TransformException e) {
			// expected: null ignored -> no output
		}
	}

	// ==================================================================
	// TF-T04: 数组变体 setInputData(IN[]) / addInputData(IN[])
	// ==================================================================

	public void testTF_T04_ArrayVariants() throws Exception {
		StringTransformer transformer = new StringTransformer();

		// setInputData(IN[])
		transformer.setInputData(new String[] { "x", "y" });
		transformer.transform();
		assertEquals("setInputData(array) size.", 2, transformer.getOutputData().size());
		assertEquals("First element wrapped.", "[x]", transformer.getOutputData().get(0));

		// addInputData(IN[]) 追加
		transformer.addInputData(new String[] { "z" });
		transformer.transform();
		assertEquals("After addInputData(array) size.", 3, transformer.getOutputData().size());
	}

	// ==================================================================
	// TF-T05: addInputData(List) 追加，多次调用累积
	// ==================================================================

	public void testTF_T05_ListAddAccumulates() throws Exception {
		StringTransformer transformer = new StringTransformer();

		List<String> batch1 = new ArrayList<>();
		batch1.add("a");
		batch1.add("b");
		transformer.addInputData(batch1);

		List<String> batch2 = new ArrayList<>();
		batch2.add("c");
		transformer.addInputData(batch2);

		transformer.transform();
		assertEquals("Accumulated size.", 3, transformer.getOutputData().size());
		assertEquals("Last element.", "[c]", transformer.getOutputData().get(2));

		// null 列表被忽略，不影响已有
		transformer.addInputData((List<String>) null);
		transformer.transform();
		assertEquals("After null list add.", 3, transformer.getOutputData().size());
	}
}
