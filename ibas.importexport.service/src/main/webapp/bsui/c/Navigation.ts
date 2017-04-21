/**
 * @license
 * Copyright color-coding studio. All Rights Reserved.
 *
 * Use of this source code is governed by an Apache License, Version 2.0
 * that can be found in the LICENSE file at http://www.apache.org/licenses/LICENSE-2.0
 */

import * as ibas from "ibas/index";
import * as dataexporttemplateApps from "../../bsapp/dataexporttemplate/index";
import * as dataexporttemplateViews from "./dataexporttemplate/index";

/**
 * 视图导航
 */
export default class Navigation extends ibas.ViewNavigation {

    /** 
     * 创建实例
     * @param id 应用id
     */
    protected newView(id: string): ibas.IView {
        let view: ibas.IView = null;
        switch (id) {
            case dataexporttemplateApps.DataExportTemplateListApp.APPLICATION_ID:
                view = new dataexporttemplateViews.DataExportTemplateListView();
                break;
            case dataexporttemplateApps.DataExportTemplateChooseApp.APPLICATION_ID:
                view = new dataexporttemplateViews.DataExportTemplateChooseView();
                break;
            case dataexporttemplateApps.DataExportTemplateViewApp.APPLICATION_ID:
                view = new dataexporttemplateViews.DataExportTemplateViewView();
                break;
            case dataexporttemplateApps.DataExportTemplateEditApp.APPLICATION_ID:
                view = new dataexporttemplateViews.DataExportTemplateEditView();
                break;
            default:
                break;
        }
        return view;
    }
}
