# encoding=utf-8
import os

from project.utils import mvfile_replacecontent, mv_file
from project.constant import APP_SOURCE_PATH

#as 构造各个模块的基类
#   移动文件，添加manifest属性，添加string等
class BaseModule:

    PRO_SRC_PATH = APP_SOURCE_PATH
    PRO_PKG_NAME = 'com.tencent.easyapp'
    PRO_SRC_PATH_PRE = 'com/tencent/easyapp'

    def __init__(self, path, pkg_name):
        # 需要移动的java文件
        self.src_files = []
        # 资源文件
        self.res_files = []
        self.manifest_activities = []
        self.manifest_app_elems = []
        self.manifest_permissions = []
        self.gradle_dependencies = []
        self.gradle_java_src_dirs = []
        self.gradle_res_src_dirs = []
        self.res_strings = []
        self.res_styles = []
        self.lib_files = []

        self.path = path + os.sep
        self.pkg_name = pkg_name

    def create(self, manifest, gradle, strings, styles):
        # 移动java文件
        pkg_path = self.pkg_name.replace('.', os.sep)
        for src_file in self.src_files:
            # 替换包目录
            dst_file = src_file
            if src_file.find(BaseModule.PRO_SRC_PATH_PRE):
                dst_file = src_file.replace(BaseModule.PRO_SRC_PATH_PRE, pkg_path)
            mvfile_replacecontent(BaseModule.PRO_SRC_PATH + src_file, self.path + dst_file,
                            BaseModule.PRO_PKG_NAME, self.pkg_name)

        # 资源文件
        for res_file in self.res_files:
            mvfile_replacecontent(BaseModule.PRO_SRC_PATH + res_file, self.path + res_file,
                            BaseModule.PRO_PKG_NAME, self.pkg_name)

        # manifest构造
        manifest.add_permisson(self.manifest_permissions)
        manifest.add_activity(self.manifest_activities)
        manifest.add_app_elems(self.manifest_app_elems)

        # gradle构造
        gradle.add_dependency(self.gradle_dependencies)
        gradle.add_java_src(self.gradle_java_src_dirs)
        gradle.add_res_src(self.gradle_res_src_dirs)

        # values
        strings.add_string(self.res_strings)
        styles.add_style(self.res_styles)

        for lib in self.lib_files:
            mv_file(BaseModule.PRO_SRC_PATH + 'app/libs/' + lib,
                    self.path + '/app/libs/' + lib)

    # 模块说明，用于页面上的展示说明
    @classmethod
    def descriptions(cls):
        pass
