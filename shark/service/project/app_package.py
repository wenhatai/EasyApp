# encoding=utf-8
__author__ = 'zhangpengyu'
from local import APP_SOURCE_PATH, SOURCE_PKG_NAME
from utils import mv_folder


class AppPackage:

    # path是要复制的地址
    def __init__(self, path):
        self.items = []
        self.path = path

    def add_items(self, item):
        self.items.append(item)

    def create(self):
        for item in self.items:
            mv_folder(APP_SOURCE_PATH + "app/src/main/java/" + SOURCE_PKG_NAME + item,
                      self.path + item)
