<div align="center">

# IBAS ImportExport

**数据导入导出模块**

IBAS 系统的数据导入导出模块，提供业务对象与文件（Excel / JSON / XML / HTML）之间的双向转换，支持基于模板的打印导出和自由格式的数据导入导出。

Data import/export module for the IBAS system — bidirectional conversion between business objects and files (Excel / JSON / XML / HTML), template-based print export and free-form data import/export.

[![License](https://img.shields.io/badge/license-Apache--2.0-blue.svg)](LICENSE)
[![Java](https://img.shields.io/badge/Java-1.8+-orange.svg)](https://www.oracle.com/java/)
[![Maven](https://img.shields.io/badge/Maven-3.x-red.svg)](https://maven.apache.org/)
[![Version](https://img.shields.io/badge/version-0.2.0-green.svg)](pom.xml)
[![PRs Welcome](https://img.shields.io/badge/PRs-welcome-brightgreen.svg)](#-贡献--contributing)

</div>

---

## 📖 目录 | Table of Contents

- [✨ 特性 | Features](#-特性--features)
- [📦 模块结构 | Modules](#-模块结构--modules)
- [🚀 快速开始 | Quick Start](#-快速开始--quick-start)
- [🏗️ 架构 | Architecture](#️-架构--architecture)
- [📋 业务对象 | Business Objects](#-业务对象--business-objects)
- [📚 相关项目 | Related Projects](#-相关项目--related-projects)
- [🤝 贡献 | Contributing](#-贡献--contributing)
- [📄 许可证 | License](#-许可证--license)

---

## ✨ 特性 | Features

- **📊 Excel 导入导出** — 业务对象与 Excel（xlsx）双向转换，模板解析引擎自动映射属性结构
- **🌐 JSON / XML 导入导出** — 基于序列化的文件导入导出
- **🖨️ HTML 打印模板** — 基于导出模板的打印输出，支持分页、重复区域、绝对定位布局
- **🔄 转换器工厂** — 注册制工厂模式，通过 `@TransformerInfo` 注解声明转换器，支持自定义扩展
- **📋 数据更新策略** — 导入时支持跳过（SKIP）、替换（REPLACE）、修改（MODIFY）三种策略
- **📑 导出模板** — 可配置的导出模板，含布局参数、元素项、附录页
- **📝 导出日志** — 导出操作日志记录（Export Record）
- **🔗 REST API** — 提供完整的导入导出 REST 端点

---

## 📦 模块结构 | Modules

| 模块 | 类型 | 说明 |
|------|------|------|
| `ibas.importexport` | JAR | **核心模块** — BO 定义（ExportTemplate / ExportRecord）、转换器框架、数据更新策略 |
| `ibas.importexport.excel` | JAR | **Excel 转换器** — 导入（xlsx→BO）和导出（BO→xlsx），模板解析引擎 |
| `ibas.importexport.html` | JAR | **HTML 转换器** — 基于导出模板的打印输出（BO→HTML），分页/重复区域 |
| `ibas.importexport.service` | WAR | **REST 服务** — Jersey 端点暴露导入导出 API |

### 编译顺序

```
ibas.importexport → ibas.importexport.excel → ibas.importexport.html → ibas.importexport.service
```

---

## 🚀 快速开始 | Quick Start

### 环境要求 | Prerequisites

- **JDK** 1.8+
- **Maven** 3.x
- [ibas-framework](https://github.com/color-coding/ibas-framework)（BOBAS 框架）

### 构建 | Build

```bash
# 克隆仓库
git clone https://github.com/color-coding/ibas.importexport.git
cd ibas.importexport

# 编译全部模块（按 compile_order.txt 顺序）
./compile_packages.sh            # Linux / macOS
compile_packages.bat             # Windows

# 编译单个模块
mvn clean package install -Dmaven.test.skip=true -f ibas.importexport/pom.xml

# 运行测试
mvn test -f ibas.importexport/pom.xml

# 部署
./deploy_packages.sh
```

### Maven 依赖

```xml
<dependency>
    <groupId>org.colorcoding.apps</groupId>
    <artifactId>ibas.importexport</artifactId>
    <version>0.2.0</version>
</dependency>
```

---

## 🏗️ 架构 | Architecture

### 转换器体系

转换器是本模块的核心，采用注册制工厂模式：

```
ITransformer<IN, OUT>          — 转换器接口
  └─ Transformer<IN, OUT>      — 抽象基类
       ├─ FileTransformer      — 文件 → 业务对象（导入）
       │    ├─ JsonTransformer     @TransformerInfo(name="FILE_JSON_TO")
       │    ├─ XmlTransformer      @TransformerInfo(name="FILE_XML_TO")
       │    └─ ExcelTransformer    @TransformerInfo(name="FILE_XLSX_TO")
       └─ TransformerFile       — 业务对象 → 文件（导出）
       │    └─ TransformerExcel    @TransformerInfo(name="TO_FILE_XLSX")
       └─ TemplateTransformer   — 模板驱动导出
            └─ TransformerHtml      @TransformerInfo(name="TO_FILE_HTML")
```

### 数据导入流程

1. 根据文件扩展名通过 `TransformerFactory` 创建对应的 `IFileTransformer`
2. 调用 `transform()` 将文件转为 `List<IBusinessObject>`
3. 根据 `emDataUpdateMethod` 使用不同 `DataUpdater` 处理：
   - **SKIP** — 数据已存在则跳过，不存在则新建
   - **REPLACE** — 删除旧数据再新建
   - **MODIFY** — 基于唯一键/主键匹配，合并修改值

---

## 📋 业务对象 | Business Objects

| 业务对象 | 说明 |
|----------|------|
| `ExportTemplate` / `ExportTemplateItem` / `ExportTemplateAppendix` | 导出模板及元素项 |
| `ExportRecord` | 导出日志记录 |

---

## 📚 相关项目 | Related Projects

| 项目 | 说明 |
|------|------|
| [ibas-framework](https://github.com/color-coding/ibas-framework) | BOBAS 业务对象框架 |
| [ibas.reportanalysis](https://github.com/color-coding/ibas.reportanalysis) | 报表分析模块 |

---

## 🤝 贡献 | Contributing

欢迎提交 Issue 和 Pull Request！

1. Fork 本仓库
2. 创建特性分支（`git checkout -b feature/amazing-feature`）
3. 提交更改（`git commit -m 'Add amazing feature'`）
4. 推送到分支（`git push origin feature/amazing-feature`）
5. 发起 Pull Request

---

## 📄 许可证 | License

本项目基于 [Apache License 2.0](LICENSE) 开源。
---

## 🙏 鸣谢 | Thanks

<div align="center">

**[Color-Coding Studio](http://colorcoding.org/)** · 咔啦工作室

</div>
