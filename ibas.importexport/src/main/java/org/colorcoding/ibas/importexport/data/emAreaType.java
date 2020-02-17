package org.colorcoding.ibas.importexport.data;

import org.colorcoding.ibas.bobas.mapping.Value;

/**
 * 区域类型
 * 
 * @author Niuren.Zhu
 *
 */
public enum emAreaType {
	/**
	 * 页眉
	 */
	@Value("PH")
	PAGE_HEADER,
	/**
	 * 开始区
	 */
	@Value("SS")
	START_SECTION,
	/**
	 * 重复区头
	 */
	@Value("RH")
	REPETITION_HEADER,
	/**
	 * 重复区
	 */
	@Value("RE")
	REPETITION,
	/**
	 * 重复区脚
	 */
	@Value("RF")
	REPETITION_FOOTER,
	/**
	 * 结束区
	 */
	@Value("ES")
	END_SECTION,
	/**
	 * 页脚区
	 */
	@Value("PF")
	PAGE_FOOTER,
	/**
	 * 附录
	 */
	@Value("AP")
	APPENDIX,
}
