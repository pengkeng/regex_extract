#!/usr/bin/python
import re

line = r"Cats are smarter than dogs"

matchObj = re.match(r'(.*) \\are (.*?) .*', line, re.M | re.I)

if matchObj:
    print
    "matchObj.group() : ", matchObj.group()
    print
    "matchObj.group(1) : ", matchObj.group(1)
    print
    "matchObj.group(2) : ", matchObj.group(2)
else:
    print
    "No match!!"

line = "Cats are smarter than dogs";

matchObj = re.match(r'dogs', line, re.M | re.I)
if matchObj:
    print("match --> matchObj.group() : ", matchObj.group())
else:
    print("No match!!")

matchObj = re.search(r'dogs', line, re.M | re.I)
if matchObj:
    print("search --> searchObj.group() : ", matchObj.group())
else:
    print("No match!!")


# 将匹配的数字乘以 2
def double(matched):
    value = int(matched.group('value'))
    return str(value * 2)

s = 'A23G4HFD567'
print(re.sub('(?P<value>\d+)', double, s))
pat = "\w\d"
print(re.sub(pat, double, "A23G4HFD567"))
