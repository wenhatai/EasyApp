# encoding=utf-8

__author__ = 'zhangpengyu'
from local import APP_SOURCE_PATH
from utils import mv_file


class AppRes:
    # path是要复制的文件地址
    def __init__(self, path):
        self.path = path
        self.items = []

    def add_items(self, item):
        self.items.append(item)

    def create(self):
        for item in self.items:
            mv_file(APP_SOURCE_PATH + "app/src/main/res/" + item,
                    self.path + item)