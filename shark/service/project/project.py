# encoding=utf-8

import os
import time
import shutil

from module.base_module import BaseModule
from strings import Strings
from styles import Styles
from utils import eapp_mkdir, zip_dir, mv_file
from manifest import AndroidManifest
from module.test_module import TestModule
from gradle import Gradle


BASE_DIR = '/Users/wxz/EAppOutDir'
LOAD_BASE_URL = 'http://127.0.0.1:8080/'


# 模块列表
# 新添加的模块要在这里添加一下，页面会自动加载对应模块
MODULE_LIST = {
    'test_module': TestModule,
    'test_module2': TestModule
}


# 创建工程
def make(app_name, pkg_name, check_list):
    zip_file_name = app_name + '-' + time.strftime('%Y%m%d%H%I%M%S')
    base_path = BASE_DIR + os.sep + zip_file_name
    path = base_path + os.sep + app_name
    eapp_mkdir(path)

    create_default_file(path)

    # 构建需要的模块
    manifest = AndroidManifest(path, pkg_name)
    gradle = Gradle(path, pkg_name)
    strings = Strings(path, app_name)
    styles = Styles(path)
    for check_module in check_list:
        module = MODULE_LIST[check_module]
        tmp = module(path, pkg_name)
        tmp.create(manifest, gradle, strings, styles)
    manifest.create()
    gradle.create()
    strings.create()
    styles.create()

    # 打包，返回下载url
    zip_dir(base_path, base_path + '.zip')
    shutil.rmtree(base_path)
    return LOAD_BASE_URL + zip_file_name + '.zip'


# 初始化一些文件目录
def create_default_file(path):
    dst_path = path + '/app/src/main/'
    # create res dir
    res = dst_path + 'res'
    eapp_mkdir(res)
    layout = dst_path + 'layout'
    eapp_mkdir(layout)
    drawable = dst_path + 'drawable'
    eapp_mkdir(drawable)
    values = dst_path + 'values'
    eapp_mkdir(values)
    libs = path + '/app/libs'
    eapp_mkdir(libs)

    # icon
    mv_file(BaseModule.PRO_SRC_PATH + 'app/src/main/res/drawable-hdpi/ic_launcher.png',
            dst_path + 'res/drawable-hdpi/ic_launcher.png')