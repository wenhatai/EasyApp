# encoding=utf-8
from module.base_module import BaseModule
from static.manifest import Activity

__author__ = 'wxz'


class TestModule(BaseModule):

    def __init__(self, path, pkg_name):
        BaseModule.__init__(self, path, pkg_name)

        self.src_files = ['app/src/main/java/com/tencent/easyapp/ui/activity/SampleLoginActivity.java']
        self.res_files = ['app/src/main/res/layout/sample_activity_login.xml']
        self.manifest_permissions = ['android.permission.INTERNET']

        # 构造这个activity嫌麻烦的话可以直接丢在下面的app_elems里面
        activity = Activity('.ui.activity.SampleLoginActivity')
        activity.set_intent_filter('''<intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>''')
        activity.set_label('@string/app_name')
        self.manifest_activities = [activity]

        # application的元素都可以丢在这里面，service神马的
        self.manifest_app_elems = ['''      <activity
            android:name=".ui.activity.SampleLoginActivity"
            android:label="@string/app_name">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>''']

        self.gradle_java_src_dirs = ["src/main/java"]
        self.gradle_res_src_dirs = ["src/main/assets"]
        self.gradle_dependencies = ["'com.android.support:support-v4:+'",
                                    "'com.android.support:recyclerview-v7:+'"]

    # 网页上的模块说明
    @classmethod
    def descriptions(cls):
        return '测试模块'
