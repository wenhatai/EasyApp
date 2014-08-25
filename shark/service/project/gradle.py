from module.base_module import BaseModule
from utils import mv_file, mv_folder, write_file

__author__ = 'wxz'


class Gradle:
    def __init__(self, path, apk_name):
        self.path = path
        self.apk_name = apk_name
        self.dependencies = []
        self.java_src_dirs = []
        self.res_src_dirs = []

    def add_dependency(self, dependency):
        self.dependencies += dependency

    def add_java_src(self, java_src):
        self.java_src_dirs += java_src

    def add_res_src(self, res_src):
        self.res_src_dirs += res_src

    def create(self):
        self.create_default()

        content = '''apply plugin: 'com.android.application'

android {
    compileSdkVersion 19
    buildToolsVersion '20.0.0'

    defaultConfig {
        applicationId "'''
        content += self.apk_name
        content += '''"
        minSdkVersion 8
        targetSdkVersion 19
        versionCode 1
        versionName "1.0"
    }
    buildTypes {
        release {
            runProguard false
            proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
        }
    }

    sourceSets{
        main{
            java.srcDirs = ['''
        for java_src in self.java_src_dirs:
            content += "'" + java_src + "',"
        content = content.rstrip(',')
        content += ''']
            resources.srcDirs = ['''
        for res_src in self.res_src_dirs:
            content += "'" + res_src + "',"
        content = content.rstrip(',')
        content += ''']
        }
    }

}

dependencies {
    compile fileTree(dir: 'libs', include: ['*.jar'])'''
        for dependency in self.dependencies:
            content += "\n    compile " + dependency + "\n"
        content += "}"

        write_file(self.path + '/app/build.gradle', content)

    def create_default(self):
        mv_file(BaseModule.PRO_SRC_PATH + 'build.gradle', self.path + '/build.gradle')
        mv_file(BaseModule.PRO_SRC_PATH + 'settings.gradle', self.path + '/settings.gradle')
        mv_file(BaseModule.PRO_SRC_PATH + 'local.properties', self.path + '/local.properties')
        mv_file(BaseModule.PRO_SRC_PATH + 'gradlew', self.path + '/gradlew')
        mv_file(BaseModule.PRO_SRC_PATH + 'gradlew.bat', self.path + '/gradlew.bat')
        mv_file(BaseModule.PRO_SRC_PATH + 'gradle.properties', self.path + '/gradle.properties')
        mv_file(BaseModule.PRO_SRC_PATH + 'app/local.properties', self.path + '/app/local.properties')
        mv_file(BaseModule.PRO_SRC_PATH + 'app/proguard-rules.pro', self.path + '/app/proguard-rules.pro')
        mv_folder(BaseModule.PRO_SRC_PATH + 'gradle', self.path + '/gradle')