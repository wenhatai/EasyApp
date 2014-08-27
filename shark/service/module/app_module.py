__author__ = 'zhangpengyu'
try:
    import xml.etree.cElementTree as ET
except ImportError:
    import xml.etree.ElementTree as ET
from project.app_package import AppPackage
from project.app_class import AppClass
from project.gradle import Gradle
from project.manifest import AndroidManifest,Activity


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
            tree = ET.ElementTree(file='../xml/' + xml)
            root = tree.getroot()
            for child_root in root:
                if child_root == "src":
                    self.parse_src(child_root)
                elif child_root == "res":
                    self.parse_res(child_root)
                elif child_root == "libs":
                    self.parse_lib(child_root)
                elif child_root == "manifest":
                    self.parse_manifest(child_root)
                elif child_root == "gradle":
                    self.parse_gradle(child_root)



    def parse_src(self, src):
        for child_src in src:
            if child_src == "package":
                for package in child_src:
                    self.app_package.add_items(package.text)
            elif child_src == "class":
                self.app_class.name = child_src.attrib['package']
                for child_class in child_src:
                    self.app_class.add_items(child_class.text)

    def parse_res(self, res):
        for child_res in res:
            self.app_res.append(child_res.text)

    def parse_lib(self, libs):
        for lib in libs:
            self.app_lib.append(lib.text)

    def parse_manifest(self,manifest):
        for child_mainfest in manifest:
            if child_mainfest == "permission":
                for permission in child_mainfest:
                    self.app_manifest.add_permisson(permission.text)
            elif child_mainfest == "activity":
                for app_activity in child_mainfest:
                    activity = Activity(app_activity.attrib['name'])
                    activity.set_label(app_activity.attrib['label'])
                    activity.set_launchmode(app_activity.attrib['launch_mode'])
                    activity.set_intent_filter(app_activity.attrib['intent_filter'])
                    self.app_manifest.add_activity(activity)
            else:
                for app_elems in child_mainfest:
                    self.app_manifest.add_app_elems(app_elems.text)

    def parse_gradle(self,gradle):
        for child_gradle in gradle:
            if child_gradle == "dependency":
                for dependency in child_gradle:
                    self.app_gradle.add_dependency(dependency.text)

