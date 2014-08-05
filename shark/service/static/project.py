import os,time
from utils import eapp_mkdir,writefile


BASE_DIR = '/Users/wxz/EAppOutDir'
ANDROID_MANIFAST_FILE_NAME = 'AndroidManifest.xml'


def make(app_name, pkg_name):
    path = BASE_DIR + os.sep + time.strftime('%Y%m%d%H%I%M%S') + os.sep + app_name
    eapp_mkdir(path)

    create_manifast(path, pkg_name)

    # create src dir
    src = path + os.sep + 'src'
    pkgDirs = pkg_name.split('.')
    for pkg in pkgDirs:
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


def create_manifast(path, pkg_name):
    content = '''<?xml version="1.0" encoding="utf-8"?>\n''' \
                '''<manifest xmlns:android="http://schemas.android.com/apk/res/android\n''' \
                '''     xmlns:tools="http://schemas.android.com/tools"\n''' \
                '''     package="''' + pkg_name + '''"\n''' \
                '''     android:versionCode="1"\n''' \
                '''     android:versionName="1.0">\n\n''' \
                '''     <uses-sdk tools:node="replace" />\n''' \
                '''     <application\n''' \
                '''         android:icon="@drawable/ic_launcher"\n''' \
                '''         android:label="@string/app_name"\n''' \
                '''         android:theme="@style/AppTheme">\n''' \
                '''     </application>\n''' \
                '''</manifest>'''

    writefile(path + os.sep + ANDROID_MANIFAST_FILE_NAME, content)
