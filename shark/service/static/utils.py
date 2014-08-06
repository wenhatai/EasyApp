#encoding=utf-8

import os
import shutil
import re
import sys

reload(sys)
sys.setdefaultencoding('utf-8')


### 创建多层目录
def eapp_mkdir(path):
    path = path.strip()
    path = path.rstrip('\\')

    if not os.path.exists(path):
        os.makedirs(path)
        print path + ' path create success'
    else:
        print path + ' path already exsits'


def write_file(path, content):
    f = open(path, 'w')
    f.write(content)
    f.close()


### 复制文件到目标目录
def mv_file(src, dst):
    if not os.path.exists(dst):
        shutil.copytree(src, dst)
    else:
        print dst + ' dst already exsits'


### 复制文件到目标目录，并替换文本内容
def replace_content(src_path, dst_path, old, new):
    eapp_mkdir(os.path.dirname(dst_path))
    f = open(src_path, "r+")
    open(dst_path, 'w').write(re.sub(old, new, f.read()))
