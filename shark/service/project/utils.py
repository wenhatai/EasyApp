#encoding=utf-8

import os
import shutil
import re
import sys
import zipfile

# 解决读取中文异常
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
    eapp_mkdir(os.path.dirname(path))
    f = open(path, 'w')
    f.write(content)
    f.close()


### 复制文件夹到目标目录
def mv_folder(src, dst):
    if not os.path.exists(dst):
        shutil.copytree(src, dst)
    else:
        print dst + ' dst already exsits'


### 复制文件
def mv_file(src_path, dst_path):
    ##如果文件不存在就不执行复制
    if not os.path.isfile(src_path):
        return
    eapp_mkdir(os.path.dirname(dst_path))
    f = open(src_path, "r+")
    open(dst_path, 'w').write(f.read())


### 复制文件到目标目录，并替换文本内容
def mvfile_replacecontent(src_path, dst_path, old, new):
    eapp_mkdir(os.path.dirname(dst_path))
    f = open(src_path, "r+")
    open(dst_path, 'w').write(re.sub(old, new, f.read()))


# 压缩文件
def zip_file(target, source):
    zip_command = "zip -qr '%s' %s" % (target, source)
    if os.system(zip_command) == 0:
        return True
    else:
        return False


def zip_dir(dirname, zipfilename):
    filelist = []
    if os.path.isfile(dirname):
        filelist.append(dirname)
    else:
        for root, dirs, files in os.walk(dirname):
            for name in files:
                filelist.append(os.path.join(root, name))

    zf = zipfile.ZipFile(zipfilename, "w", zipfile.zlib.DEFLATED)
    for tar in filelist:
        arcname = tar[len(dirname):]
        #print arcname
        zf.write(tar, arcname)
    zf.close()
