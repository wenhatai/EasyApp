# encoding=utf-8

import os
import time

from utils import eapp_mkdir, zip_dir
from manifest import AndroidManifest, Activity
from module.test_module import TestModule
import shutil

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

    # 构建需要的模块
    manifest = AndroidManifest(path, pkg_name)
    for check_module in check_list:
        module = MODULE_LIST[check_module]
        tmp = module(path, pkg_name)
        tmp.create(manifest)
    manifest.create()

    # 打包，返回下载url
    zip_dir(base_path, base_path + '.zip')
    shutil.rmtree(base_path)
    return LOAD_BASE_URL + zip_file_name + '.zip'


def make_bak(app_name, pkg_name):
    path = BASE_DIR + os.sep + time.strftime('%Y%m%d%H%I%M%S') + os.sep + app_name
    eapp_mkdir(path)

    manifest = AndroidManifest(path, pkg_name)
    activity = Activity('.ui.activity.SampleLoginActivity')
    activity.set_intent_filter('''<intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>''')
    activity.set_label('@string/app_name')
    manifest.add_activity(activity)
    manifest.add_permisson('android.permission.INTERNET')
    manifest.create()

    # create src dir
    src = path + os.sep + 'src'
    pkg_dirs = pkg_name.split('.')
    for pkg in pkg_dirs:
        src += os.sep
        src += pkg
    eapp_mkdir(src)

    # create res dir
    res = path + os.sep + 'res'
    eapp_mkdir(res)
    layout = res + os.sep + 'layout'
    eapp_mkdir(layout)
    drawable = res + os.sep + 'drawable'
    eapp_mkdir(drawable)
    values = res + os.sep + 'values'
    eapp_mkdir(values)