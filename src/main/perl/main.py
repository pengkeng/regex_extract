import json
import os
import subprocess
import sys

path = "D:/pqc/perl/unique_extract"
files = os.listdir(path)
s = "D:\\pqc\\perl\\unique_result\\"
for i in range(0, len(files)):
    file = path + "/" + files[i]
    shells = ["perl", "extract-regexps.pl", file, s + str(i + 1) + ".json"]
    ret = subprocess.run(shells, shell=True, stdout=subprocess.PIPE, stderr=subprocess.PIPE, encoding="utf-8")
    if ret.returncode == 0:
        print(file)
        print(str(i) + "success:")
    else:
        print(str(i) + "error:")
