#!/bin/sh
echo '****************************************************************************'
echo '              build_all.sh                                                  '
echo '                      by niuren.zhu                                         '
echo '                           2016.06.17                                       '
echo '  说明：                                                                    '
echo '    1. 此脚本需要在Node.js下运行。                                          '
echo '    2. 遍历当前目录下所有子目录，存在tsconfig.json则编译。                  '
echo '    3. 参数1，编译的目录，“./”表示当前。                                    '
echo '    4. 参数2，tsc命令的其他参数，如：-w，表示监听文件变化。                 '
echo '****************************************************************************'
# 设置参数变量
# 启动目录
STARTUP_FOLDER=$(cd `dirname $0`; pwd)
# 工作目录默认第一个参数
WORK_FOLDER=$1
# 修正相对目录为启动目录
if [ "${WORK_FOLDER}" = "./" ]
then
  WORK_FOLDER=${STARTUP_FOLDER}
fi
# 未提供工作目录，则取启动目录
if [ "${WORK_FOLDER}" = "" ]
then
  WORK_FOLDER=${STARTUP_FOLDER}
fi
echo --工作的目录：${WORK_FOLDER}
# 其他参数
OPTIONS=$2
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
fi

# 遍历当前目录存在tsconfig.json则执行tsc
for folder in `find ${WORK_FOLDER} -type f -name tsconfig.json`
do
  folder=${folder%\/*}
  echo --开始编译：${folder}
# 运行编译命令
  if [ "${OPTIONS}" != "" ]
  then
# 包括监听参数，后台运行命令
    ${COMMOND} ${OPTIONS} -p ${folder} &
  else
    ${COMMOND} -p ${folder}
  fi
done
