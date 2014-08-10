from static.utils import write_file

__author__ = 'wxz'


class Strings:
    def __init__(self, path, app_name):
        self.strings = []
        self.path = path
        self.app_name = app_name

    def add_string(self, string):
        self.strings.append(string)

    def create(self):
        content = '''<?xml version="1.0" encoding="utf-8"?>
<resources>\n'''
        content += '''  <string name="app_name">''' + self.app_name + ''''</string>\n'''
        for string in self.strings:
            content += '    ' + string
        content += '''</resources>'''
        write_file(self.path + '/app/src/main/res/values/strings.xml', content)
