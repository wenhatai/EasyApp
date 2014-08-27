__author__ = 'zhangpengyu'
try:
    import xml.etree.cElementTree as ET
except ImportError:
    import xml.etree.ElementTree as ET
from project.app_package import AppPackage
from project.app_class import  AppClass
from project.gradle import Gradle
from project.manifest import AndroidManifest

class AppModule:

    def __init__(self):
        self.module_xml = ["spanndata.xml"]
        self.app_package = AppPackage()
        self.app_class = AppClass()
        self.app_res = []
        self.app_lib = []
        self.app_gradle = Gradle()
        self.app_manifest = AndroidManifest()
        self.readme = ""

    def parse(self):
        for xml in self.module_xml:
            tree = ET.ElementTree(file='../xml/'+xml)
            root = tree.getroot()
            for child_root in root:
                if child_root == "src":
                    self.parse_src(child_root)
                elif child_root == "res":
                    self.parse_res(child_root)

    def parse_src(self,src):
        for child_src in src:
            if child_src == "package":
                for package in child_src:
                    self.app_package.add_items(package.text)
            elif child_src == "class":
                self.app_class.name = child_src.attrib['package']
                for child_class in child_src:
                    self.app_class.add_items(child_class.text)


    def parse_res(self,res):
        for child_res in res:
            self.app_res.append(child_res.text)

    def parse_lib(self,libs):
        for lib in libs:
            self.app_lib.append(lib)
