# encoding=utf-8
from module.base_module import BaseModule
from static.manifest import Activity

__author__ = 'wxz'


class TestModule(BaseModule):

    def __init__(self, path, pkg_name):
        BaseModule.__init__(self, path, pkg_name)

        self.src_files = ['java/com/tencent/easyapp/ui/activity/SampleLoginActivity.java']
        self.res_files = ['res/layout/sample_activity_login.xml']
        self.manifest_permissions = ['android.permission.INTERNET']
        activity = Activity('.ui.activity.SampleLoginActivity')
        activity.set_intent_filter('''<intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>''')
        activity.set_label('@string/app_name')
        self.manifest_activities = [activity]

    @classmethod
    def descriptions(cls):
        return '测试模块'
