@echo off
setlocal EnableDelayedExpansion
echo ***************************************************************************
echo                build_all.bat
echo                     by niuren.zhu
echo                           2017.01.13
echo  说明：
echo     1. 此脚本需要在Node.js command prompt下运行。
echo     2. 编译当前目录及bsui子目录下的tsconfig.json。
echo     3. 参数1，tsc命令的其他参数，如：-w，表示监听文件变化。
echo ****************************************************************************
rem 设置参数变量
rem 工作目录
set WORK_FOLDER=%~dp0
rem 工作目录末尾字符是"\"
if "%WORK_FOLDER:~-1%" neq "\" set WORK_FOLDER=%WORK_FOLDER%\
echo --工作的目录：%WORK_FOLDER%
rem 其他参数
set OPTIONS=%~1
rem 运行命令
set COMMAND=tsc
if "%OPTIONS%" neq "" (
  set COMMAND=start /min !COMMAND! %OPTIONS%
  echo 命令：!COMMAND!
)
rem 映射库
set IBAS_FOLDER=%IBAS_TS_LIB%
if "%IBAS_FOLDER%" equ "" (
rem 没有定义环境变量
  if exist "%WORK_FOLDER%..\..\..\..\..\ibas-typescript\" (
    set IBAS_FOLDER=%WORK_FOLDER%..\..\..\..\..\ibas-typescript\
  )
)

rem 检查并映射库
if "%IBAS_FOLDER%" neq "" (
echo --检查库符号链接
  if not exist %WORK_FOLDER%3rdparty\ibas mklink /d .\3rdparty\ibas %IBAS_FOLDER%ibas\ >NUL
  if not exist %WORK_FOLDER%3rdparty\openui5 mklink /d .\3rdparty\openui5 %IBAS_FOLDER%openui5\ >NUL
  if not exist %WORK_FOLDER%3rdparty\shell mklink /d .\3rdparty\shell %IBAS_FOLDER%shell\ >NUL
)

rem 编译项目配置
set TS_CONFIGS=tsconfig.json
set TS_CONFIGS=%TS_CONFIGS% bsui\c\tsconfig.json
set TS_CONFIGS=%TS_CONFIGS% bsui\m\tsconfig.json

FOR %%l IN (%TS_CONFIGS%) DO (
  set TS_CONFIG=%%l
  echo --开始编译：!TS_CONFIG!
  call !COMMAND! -p !TS_CONFIG!
)
