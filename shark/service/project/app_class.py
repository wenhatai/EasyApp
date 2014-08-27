# encoding=utf-8

__author__ = 'zhangpengyu'
from local import APP_SOURCE_PATH, SOURCE_PKG_NAME
from utils import mv_file


class AppClass:
    # path是要复制的文件地址
    def __init__(self, path):
        self.items = []
        self.name = ""
        self.path = path

    def add_items(self, item):
        self.items.append(self.name + item)

    def create(self):
        for item in self.items:
            mv_file(APP_SOURCE_PATH + "app/src/main/java/" + SOURCE_PKG_NAME + item,
                    self.path + item)
