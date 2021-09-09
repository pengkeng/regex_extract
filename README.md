# regex_extract
一个提取正则表达式的程序，利用AST语法树，遍历每一个节点。

支持直接赋值和变量赋值两种方式的提取。


## 运行方式

创建一个RegexExtractor对象，对象初始化参数为 目标路径和结果保存路径。

目标路径可以是文件也可以是文件夹。

## Example Run
```java
   File rootPath = new File("Test.java");
   File resultFile = new File("Test.json");
   java.RegexExtractor extractor = new java.RegexExtractor(rootPath, resultFile);
   extractor.extractToFile();
```



