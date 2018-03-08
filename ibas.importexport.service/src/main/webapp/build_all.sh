#!/bin/sh
echo '****************************************************************************'
echo '              build_all.sh                                                  '
echo '                      by niuren.zhu                                         '
echo '                           2016.06.17                                       '
echo '  说明：                                                                    '
echo '    1. 此脚本需要在Node.js下运行。                                           '
echo '    2. 遍历当前目录下所有子目录，存在tsconfig.json则编译。                     '
echo '    3. 参数1，tsc命令的其他参数，如：-w，表示监听文件变化。                    '
echo '****************************************************************************'
# 设置参数变量
# 工作目录
WORK_FOLDER=$(cd `dirname $0`; pwd)
echo --工作目录：${WORK_FOLDER}
# 其他参数
OPTIONS=$1
COMMOND=tsc

# 映射库
IBAS_FOLDER=${IBAS_TS_LIB}
if [ "${IBAS_FOLDER}" = "" ]
then
  if [ -e "${WORK_FOLDER}/../../../../../ibas-typescript" ]
  then
    IBAS_FOLDER=${WORK_FOLDER}/../../../../../ibas-typescript
  fi
fi
# 检查并映射库
if [ "${IBAS_FOLDER}" != "" ]
then
  if [ ! -e "${WORK_FOLDER}/3rdparty/ibas" ]
  then
    ln -s ${IBAS_FOLDER}/ibas ${WORK_FOLDER}/3rdparty/ibas
  fi
  if [ ! -e "${WORK_FOLDER}/3rdparty/openui5" ]
  then
    ln -s ${IBAS_FOLDER}/openui5 ${WORK_FOLDER}/3rdparty/openui5
  fi
  if [ ! -e "${WORK_FOLDER}/3rdparty/shell" ]
  then
    ln -s ${IBAS_FOLDER}/shell ${WORK_FOLDER}/3rdparty/shell
  fi
fi

# 查询当前目录的tsconfig文件
for TS_CONFIG in `find ${WORK_FOLDER}/ -maxdepth 1 -type f -name "tsconfig*.json"`
do
  echo --开始编译：${TS_CONFIG}
# 运行编译命令
  if [ "${OPTIONS}" != "" ]
  then
# 包括监听参数，后台运行命令
    ${COMMOND} ${OPTIONS} -p ${TS_CONFIG} &
  else
    ${COMMOND} -p ${TS_CONFIG}
  fi
done
