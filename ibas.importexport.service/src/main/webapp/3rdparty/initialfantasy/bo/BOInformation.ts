/**
 * @license
 * Copyright Color-Coding Studio. All Rights Reserved.
 *
 * Use of this source code is governed by an Apache License, Version 2.0
 * that can be found in the LICENSE file at http://www.apache.org/licenses/LICENSE-2.0
 */
namespace initialfantasy {
    export namespace bo {
        /** 业务对象信息 */
        export interface IBOInformation extends ibas.IBusinessObject {
            /** 编码 */
            code: string;
            /** 名称 */
            name: string;
            /** 描述 */
            description: string;
            /** 映射（表） */
            mapped: string;
            /** 对象类型 */
            objectType: string;
            /** 业务对象属性信息集合 */
            boPropertyInformations: IBOPropertyInformations;
        }
        /** 业务对象属性信息 */
        export interface IBOPropertyInformation extends ibas.IBusinessObject {
            /** 编码 */
            code: string;
            /** 属性名称 */
            property: string;
            /** 映射（字段） */
            mapped: string;
            /** 描述 */
            description: string;
            /** 数据类型 */
            dataType: string;
            /** 编辑类型 */
            editType: string;
            /** 编辑大小 */
            editSize: number;
            /** 检索的 */
            searched: ibas.emYesNo;
            /** 系统的 */
            systemed: ibas.emYesNo;
            /** 可编辑 */
            editable: ibas.emYesNo;
            /** 业务对象属性值集合 */
            boPropertyValues: IBOPropertyValues;
        }

        /** 业务对象属性信息 集合 */
        export interface IBOPropertyInformations extends ibas.IBusinessObjects<IBOPropertyInformation> {

            /** 创建并添加子项 */
            create(): IBOPropertyInformation;
        }
        /** 业务对象属性信息 */
        export interface IBOPropertyValue extends ibas.IBusinessObject {
            /** 编码 */
            code: string;
            /** 属性名称 */
            property: string;
            /** 值 */
            value: string;
            /** 描述 */
            description: string;
        }
        /** 业务对象属性信息 集合 */
        export interface IBOPropertyValues extends ibas.IBusinessObjects<IBOPropertyValue> {
            /** 创建并添加子项 */
            create(): IBOPropertyValue;
        }
    }
}
