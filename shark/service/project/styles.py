from utils import write_file

__author__ = 'wxz'


class Styles:
    def __init__(self, path):
        self.path = path
        self.styles = []

    def add_style(self, style):
        self.styles += style

    def create(self):
        content = '''<?xml version="1.0" encoding="utf-8"?>
<resources>
    <style name="AppTheme" parent="Theme.Sherlock.Light.DarkActionBar">
        <!-- Customize your theme here. -->
    </style>\n'''
        for style in self.styles:
            content += '    ' + style
        content += '''</resources>'''
        write_file(self.path + '/app/src/main/res/values/styles.xml', content)
