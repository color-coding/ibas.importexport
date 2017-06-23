说明：
  1. 此处用来放置第三方类库。
  2. ibas-typescript框架需要以下目录，其中openui5不存在则加载sap官方发布。
     a. ./ibas：下载地址（https://github.com/color-coding/ibas-typescript/ibas/）
     b. ./openui5： 下载地址（https://github.com/color-coding/ibas-typescript/openui5/）
     c. ./openui5/resources: 下载地址（http://openui5.org/download.html）

  * 以上建议通过操作系统文件链接指令映射，不要拷贝物理文件。
  * 链接指令示例：
      windows: mklink /d ibas ..\..\..\..\..\..\ibas-typescript\ibas
               mklink /d openui5 ..\..\..\..\..\..\ibas-typescript\openui5
      linux： ln -s ../../../../../../ibas-typescript/ibas ibas
              ln -s ../../../../../../ibas-typescript/openui5 openui5
