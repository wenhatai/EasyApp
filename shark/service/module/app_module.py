# encoding=utf-8

__author__ = 'zhangpengyu'
try:
    import xml.etree.cElementTree as ET
except ImportError:
    import xml.etree.ElementTree as ET
import os
from project.app_package import AppPackage
from project.app_class import AppClass
from project.app_res import AppRes
from project.app_libs import AppLibs
from project.gradle import Gradle
from project.local import PROJECT_PATH

from project.manifest import AndroidManifest, Activity

class AppModule:

    # path是文件地址，pkg_name是文件包名
    def __init__(self, path, pkg_name):
        self.pkg_name = pkg_name
        self.path = path
        paths = pkg_name.split(".")
        self.pkg_path = ""
        for path in paths:
            self.pkg_path += path + os.sep
        self.module_xml = ["spanndata.xml"]
        src_path = self.path + "/app/src/main/java/" + self.pkg_path
        res_path = self.path + "/app/src/main/res/"
        lib_path = self.path + "/app/libs/"
        app_path = self.path + "/app/"
        mainfest_path = self.path+"/app/src/main/"
        self.app_package = AppPackage(src_path)
        self.app_class = AppClass(src_path)
        self.app_res = AppRes(res_path)
        self.app_lib = AppLibs(lib_path)
        self.app_gradle = Gradle(app_path)
        self.app_manifest = AndroidManifest(mainfest_path,self.pkg_name)
        self.readme = ""

    def parse(self):
        for xml in self.module_xml:
            tree = ET.ElementTree(file=PROJECT_PATH+"xml/" + xml)
            root = tree.getroot()
            for child_root in root:
                if child_root.tag == "src":
                    self.parse_src(child_root)
                elif child_root.tag == "res":
                    self.parse_res(child_root)
                elif child_root.tag == "libs":
                    self.parse_lib(child_root)
                elif child_root.tag == "manifest":
                    self.parse_manifest(child_root)
                elif child_root.tag == "gradle":
                    self.parse_gradle(child_root)

    def parse_src(self, src):
        for child_src in src:
            if child_src.tag == "package":
                for package in child_src:
                    self.app_package.add_items(package.text)
            elif child_src.tag == "class":
                self.app_class.name = child_src.attrib['package']
                for child_class in child_src:
                    self.app_class.add_items(child_class.text)

    def parse_res(self, res):
        for child_res in res:
            self.app_res.add_items(child_res.text)

    def parse_lib(self, libs):
        for lib in libs:
            self.app_lib.add_items(lib.text)

    def parse_manifest(self, manifest):
        for child_mainfest in manifest:
            if child_mainfest.tag == "permission":
                for permission in child_mainfest:
                    self.app_manifest.add_permisson(permission.text)
            elif child_mainfest.tag == "activity":
                for app_activity in child_mainfest:
                    activity = Activity(app_activity.attrib['name'])
                    activity.set_label(app_activity.attrib['label'])
                    activity.set_launchmode(app_activity.attrib['launch_mode'])
                    activity.set_intent_filter(app_activity.attrib['intent_filter'])
                    self.app_manifest.add_activity(activity)
            else:
                for app_elems in child_mainfest:
                    self.app_manifest.add_app_elems(app_elems.text)

    def parse_gradle(self, gradle):
        for child_gradle in gradle:
            if child_gradle == "dependency":
                for dependency in child_gradle:
                    self.app_gradle.add_dependency(dependency.text)

    def create(self):
        self.parse()
        self.app_package.create()
        self.app_class.create()
        self.app_lib.create()
        self.app_res.create()
        self.app_class.create()
        # self.app_gradle.create()
        # self.app_manifest.create()