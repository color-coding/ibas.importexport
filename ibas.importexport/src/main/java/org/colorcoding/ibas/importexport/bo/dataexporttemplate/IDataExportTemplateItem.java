package org.colorcoding.ibas.importexport.bo.dataexporttemplate;

import org.colorcoding.ibas.bobas.bo.*;
import org.colorcoding.ibas.bobas.data.*;
import org.colorcoding.ibas.importexport.data.*;

/**
* 数据导出模板-项 接口
* 
*/
public interface IDataExportTemplateItem extends IBOSimpleLine {

    /**
    * 获取-编号
    * 
    * @return 值
    */
    Integer getObjectKey();

    /**
    * 设置-编号
    * 
    * @param value 值
    */
    void setObjectKey(Integer value);


    /**
    * 获取-类型
    * 
    * @return 值
    */
    String getObjectCode();

    /**
    * 设置-类型
    * 
    * @param value 值
    */
    void setObjectCode(String value);


    /**
    * 获取-行号
    * 
    * @return 值
    */
    Integer getLineId();

    /**
    * 设置-行号
    * 
    * @param value 值
    */
    void setLineId(Integer value);


    /**
    * 获取-数据源
    * 
    * @return 值
    */
    String getDataSource();

    /**
    * 设置-数据源
    * 
    * @param value 值
    */
    void setDataSource(String value);


    /**
    * 获取-实例号（版本）
    * 
    * @return 值
    */
    Integer getLogInst();

    /**
    * 设置-实例号（版本）
    * 
    * @param value 值
    */
    void setLogInst(Integer value);


    /**
    * 获取-创建日期
    * 
    * @return 值
    */
    DateTime getCreateDate();

    /**
    * 设置-创建日期
    * 
    * @param value 值
    */
    void setCreateDate(DateTime value);


    /**
    * 获取-创建时间
    * 
    * @return 值
    */
    Short getCreateTime();

    /**
    * 设置-创建时间
    * 
    * @param value 值
    */
    void setCreateTime(Short value);


    /**
    * 获取-修改日期
    * 
    * @return 值
    */
    DateTime getUpdateDate();

    /**
    * 设置-修改日期
    * 
    * @param value 值
    */
    void setUpdateDate(DateTime value);


    /**
    * 获取-修改时间
    * 
    * @return 值
    */
    Short getUpdateTime();

    /**
    * 设置-修改时间
    * 
    * @param value 值
    */
    void setUpdateTime(Short value);


    /**
    * 获取-创建用户
    * 
    * @return 值
    */
    Integer getCreateUserSign();

    /**
    * 设置-创建用户
    * 
    * @param value 值
    */
    void setCreateUserSign(Integer value);


    /**
    * 获取-修改用户
    * 
    * @return 值
    */
    Integer getUpdateUserSign();

    /**
    * 设置-修改用户
    * 
    * @param value 值
    */
    void setUpdateUserSign(Integer value);


    /**
    * 获取-创建动作标识
    * 
    * @return 值
    */
    String getCreateActionId();

    /**
    * 设置-创建动作标识
    * 
    * @param value 值
    */
    void setCreateActionId(String value);


    /**
    * 获取-更新动作标识
    * 
    * @return 值
    */
    String getUpdateActionId();

    /**
    * 设置-更新动作标识
    * 
    * @param value 值
    */
    void setUpdateActionId(String value);


    /**
    * 获取-参考1
    * 
    * @return 值
    */
    String getReference1();

    /**
    * 设置-参考1
    * 
    * @param value 值
    */
    void setReference1(String value);


    /**
    * 获取-参考2
    * 
    * @return 值
    */
    String getReference2();

    /**
    * 设置-参考2
    * 
    * @param value 值
    */
    void setReference2(String value);


    /**
    * 获取-区域类型
    * 
    * @return 值
    */
    emAreaType getAreaType();

    /**
    * 设置-区域类型
    * 
    * @param value 值
    */
    void setAreaType(emAreaType value);


    /**
    * 获取-项标识
    * 
    * @return 值
    */
    String getItemUID();

    /**
    * 设置-项标识
    * 
    * @param value 值
    */
    void setItemUID(String value);


    /**
    * 获取-项左坐标
    * 
    * @return 值
    */
    Integer getItemLeft();

    /**
    * 设置-项左坐标
    * 
    * @param value 值
    */
    void setItemLeft(Integer value);


    /**
    * 获取-项上坐标
    * 
    * @return 值
    */
    Integer getItemTop();

    /**
    * 设置-项上坐标
    * 
    * @param value 值
    */
    void setItemTop(Integer value);


    /**
    * 获取-数据源
    * 
    * @return 值
    */
    emDataSourceType getSourceType();

    /**
    * 设置-数据源
    * 
    * @param value 值
    */
    void setSourceType(emDataSourceType value);


    /**
    * 获取-项字符串
    * 
    * @return 值
    */
    String getItemString();

    /**
    * 设置-项字符串
    * 
    * @param value 值
    */
    void setItemString(String value);


    /**
    * 获取-项是否可见
    * 
    * @return 值
    */
    emYesNo getItemVisible();

    /**
    * 设置-项是否可见
    * 
    * @param value 值
    */
    void setItemVisible(emYesNo value);


    /**
    * 获取-项宽度
    * 
    * @return 值
    */
    Integer getWidth();

    /**
    * 设置-项宽度
    * 
    * @param value 值
    */
    void setWidth(Integer value);


    /**
    * 获取-项高度
    * 
    * @return 值
    */
    Integer getHeight();

    /**
    * 设置-项高度
    * 
    * @param value 值
    */
    void setHeight(Integer value);


    /**
    * 获取-左边距
    * 
    * @return 值
    */
    Integer getLeftMargin();

    /**
    * 设置-左边距
    * 
    * @param value 值
    */
    void setLeftMargin(Integer value);


    /**
    * 获取-右边距
    * 
    * @return 值
    */
    Integer getRightMargin();

    /**
    * 设置-右边距
    * 
    * @param value 值
    */
    void setRightMargin(Integer value);


    /**
    * 获取-上边距
    * 
    * @return 值
    */
    Integer getTopMargin();

    /**
    * 设置-上边距
    * 
    * @param value 值
    */
    void setTopMargin(Integer value);


    /**
    * 获取-下边距
    * 
    * @return 值
    */
    Integer getBottomMargin();

    /**
    * 设置-下边距
    * 
    * @param value 值
    */
    void setBottomMargin(Integer value);


    /**
    * 获取-左线长度
    * 
    * @return 值
    */
    Integer getLeftLine();

    /**
    * 设置-左线长度
    * 
    * @param value 值
    */
    void setLeftLine(Integer value);


    /**
    * 获取-右线长度
    * 
    * @return 值
    */
    Integer getRightLine();

    /**
    * 设置-右线长度
    * 
    * @param value 值
    */
    void setRightLine(Integer value);


    /**
    * 获取-上线长度
    * 
    * @return 值
    */
    Integer getTopLine();

    /**
    * 设置-上线长度
    * 
    * @param value 值
    */
    void setTopLine(Integer value);


    /**
    * 获取-下线长度
    * 
    * @return 值
    */
    Integer getBottomLine();

    /**
    * 设置-下线长度
    * 
    * @param value 值
    */
    void setBottomLine(Integer value);


    /**
    * 获取-字体名称
    * 
    * @return 值
    */
    String getFontName();

    /**
    * 设置-字体名称
    * 
    * @param value 值
    */
    void setFontName(String value);


    /**
    * 获取-字体大小
    * 
    * @return 值
    */
    Integer getFontSize();

    /**
    * 设置-字体大小
    * 
    * @param value 值
    */
    void setFontSize(Integer value);


    /**
    * 获取-文本样式
    * 
    * @return 值
    */
    Integer getTextStyle();

    /**
    * 设置-文本样式
    * 
    * @param value 值
    */
    void setTextStyle(Integer value);


    /**
    * 获取-水平对齐方式
    * 
    * @return 值
    */
    emHorizontalJustification getHorizontalJustification();

    /**
    * 设置-水平对齐方式
    * 
    * @param value 值
    */
    void setHorizontalJustification(emHorizontalJustification value);


    /**
    * 获取-竖直对齐方式
    * 
    * @return 值
    */
    emVerticalJustification getVerticalJustification();

    /**
    * 设置-竖直对齐方式
    * 
    * @param value 值
    */
    void setVerticalJustification(emVerticalJustification value);


    /**
    * 获取-背景色-红
    * 
    * @return 值
    */
    Integer getBackground_Red();

    /**
    * 设置-背景色-红
    * 
    * @param value 值
    */
    void setBackground_Red(Integer value);


    /**
    * 获取-背景色-绿
    * 
    * @return 值
    */
    Integer getBackground_Green();

    /**
    * 设置-背景色-绿
    * 
    * @param value 值
    */
    void setBackground_Green(Integer value);


    /**
    * 获取-背景色-蓝
    * 
    * @return 值
    */
    Integer getBackground_Blue();

    /**
    * 设置-背景色-蓝
    * 
    * @param value 值
    */
    void setBackground_Blue(Integer value);


    /**
    * 获取-前景色-红
    * 
    * @return 值
    */
    Integer getForeground_Red();

    /**
    * 设置-前景色-红
    * 
    * @param value 值
    */
    void setForeground_Red(Integer value);


    /**
    * 获取-前景色-绿
    * 
    * @return 值
    */
    Integer getForeground_Green();

    /**
    * 设置-前景色-绿
    * 
    * @param value 值
    */
    void setForeground_Green(Integer value);


    /**
    * 获取-前景色-蓝
    * 
    * @return 值
    */
    Integer getForeground_Blue();

    /**
    * 设置-前景色-蓝
    * 
    * @param value 值
    */
    void setForeground_Blue(Integer value);


    /**
    * 获取-高亮显示色-红
    * 
    * @return 值
    */
    Integer getMarker_Red();

    /**
    * 设置-高亮显示色-红
    * 
    * @param value 值
    */
    void setMarker_Red(Integer value);


    /**
    * 获取-高亮显示色-绿
    * 
    * @return 值
    */
    Integer getMarker_Green();

    /**
    * 设置-高亮显示色-绿
    * 
    * @param value 值
    */
    void setMarker_Green(Integer value);


    /**
    * 获取-高亮显示色-蓝
    * 
    * @return 值
    */
    Integer getMarker_Blue();

    /**
    * 设置-高亮显示色-蓝
    * 
    * @param value 值
    */
    void setMarker_Blue(Integer value);


    /**
    * 获取-框架色-红
    * 
    * @return 值
    */
    Integer getBorder_Red();

    /**
    * 设置-框架色-红
    * 
    * @param value 值
    */
    void setBorder_Red(Integer value);


    /**
    * 获取-框架色-绿
    * 
    * @return 值
    */
    Integer getBorder_Green();

    /**
    * 设置-框架色-绿
    * 
    * @param value 值
    */
    void setBorder_Green(Integer value);


    /**
    * 获取-框架色-蓝
    * 
    * @return 值
    */
    Integer getBorder_Blue();

    /**
    * 设置-框架色-蓝
    * 
    * @param value 值
    */
    void setBorder_Blue(Integer value);


    /**
    * 获取-显示格式
    * 
    * @return 值
    */
    String getValFormat();

    /**
    * 设置-显示格式
    * 
    * @param value 值
    */
    void setValFormat(String value);



}
