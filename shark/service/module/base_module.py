# encoding=utf-8
import os
from static.utils import replace_content


#as 构造各个模块的基类
#   移动文件，添加manifest属性，添加string等
class BaseModule:

    PRO_SRC_PATH = '/Users/wxz/ModleProject/wolf/app/src/main/'
    PRO_PKG_NAME = 'com.tencent.easyapp'
    PRO_SRC_PATH_PRE = 'com/tencent/easyapp'

    def __init__(self, path, pkg_name):
        # 需要移动的java文件
        self.src_files = []
        # 资源文件
        self.res_files = []
        self.manifest_activities = []
        self.manifest_permissions = []

        self.path = path + os.sep
        self.pkg_name = pkg_name

    def create(self, manifest):
        # 移动java文件
        pkg_path = self.pkg_name.replace('.', os.sep)
        for src_file in self.src_files:
            # 替换包目录
            dst_file = src_file
            if src_file.find(BaseModule.PRO_SRC_PATH_PRE):
                dst_file = src_file.replace(BaseModule.PRO_SRC_PATH_PRE, pkg_path)
            replace_content(BaseModule.PRO_SRC_PATH + src_file, self.path + dst_file,
                            BaseModule.PRO_PKG_NAME, self.pkg_name)

        # 资源文件
        for res_file in self.res_files:
            replace_content(BaseModule.PRO_SRC_PATH + res_file, self.path + res_file,
                            BaseModule.PRO_PKG_NAME, self.pkg_name)

        for permission in self.manifest_permissions:
            manifest.add_permisson(permission)

        for activity in self.manifest_activities:
            manifest.add_activity(activity)

    # append string到strings.xml
    def appand_string(self):
        pass

    # 模块说明，用于页面上的展示说明
    @classmethod
    def descriptions(cls):
        pass
