# encoding=utf-8
import os
from utils import write_file


#as 创建AndroidManifest
class AndroidManifest:

    FILE_NAME = 'AndroidManifest.xml'

    def __init__(self, path, pkg_name):
        self.path = path
        self.pkg_name = pkg_name
        self.application = ''
        # 权限
        self.permissions = []
        # activity
        self.activities = []

    def set_application(self, application):
        self.application = application

    def add_permisson(self, permisson):
        self.permissions.append(permisson)

    def add_activity(self, activity):
        self.activities.append(activity)

    def create(self):
        content = '''<?xml version="1.0" encoding="utf-8"?>\n''' \
                '''<manifest xmlns:android="http://schemas.android.com/apk/res/android\n''' \
                '''     xmlns:tools="http://schemas.android.com/tools"\n''' \
                '''     package="''' + self.pkg_name + '''"\n''' \
                '''     android:versionCode="1"\n''' \
                '''     android:versionName="1.0">\n\n''' \
                '''     <uses-sdk tools:node="replace" />\n''' \
                '''     <application\n''' \
                '''         android:icon="@drawable/ic_launcher"\n''' \
                '''         android:label="@string/app_name"\n'''

        # 设置自定义 application
        if self.application.strip():
            content += '''android:name="''' + self.application + '\"'

        content += '''         android:theme="@style/AppTheme">\n'''

        # activity
        for activity in self.activities:
            content += activity.parse()

        content += '''     </application>\n'''

        # permission
        for permission in self.permissions:
            content += '    <uses-permission android:name=\"' + permission + '\"/>\n'

        content += '''</manifest>'''
        write_file(self.path + os.sep + AndroidManifest.FILE_NAME, content)


#as 构造manifast里面的activity
class Activity:
    def __init__(self, activity):
        self.activity = activity
        self.launch_mode = ''
        self.label = ''
        self.intent_filter = ''

    def set_label(self, label):
        self.label = label

    def set_launchmode(self, launch_mode):
        self.launch_mode = launch_mode

    def set_intent_filter(self, intent_filter):
        self.intent_filter = intent_filter

    def parse(self):
        content = '''       <activity android:name="''' + self.activity + '\"\n'

        if self.label.strip():
            content += '            android:label=\"' + self.label + '\"\n'

        if self.launch_mode.strip():
            content += '            android:launchMode=\"' + self.launch_mode + '\"\n'

        if self.intent_filter.strip():
            content += '            >\n'
            content += '            ' + self.intent_filter + '\n'
            content += '        </activity>\n'
        else:
            content += '            />\n'

        return content

