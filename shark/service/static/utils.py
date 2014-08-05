import os


def eapp_mkdir(path):
    if not os.path.exists(path):
        os.makedirs(path)


def writefile(path, content):
    file = open(path, 'w')
    file.write(content)
    file.close()

def mvfile(src, dst):
    pass