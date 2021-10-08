/**
 *
 * Author: Peng Qiancheng
 *
 */

"use strict";

var babylon = require("babylon");
var traverse = require("babel-traverse");
var fs = require("fs");

const root = "D:/pqc/npm/extract";
var func_regex = /(_fnSetObjectDataFn|applyPatches|assign|bodyParser|buildQueryString|classToPlainFromExist|clone|collide|connie|create|deep(Copy|Defaults|FillIn|Merge|MixIn|Set)?|defaultsDeep|del|deparam|depthedLookup|diff|dot-object|extend|expand|handler|immutable|insert|jq_deparam|linuxCmdline|loadPackageDefinition|matrixToAsciiTable|merge|mixin-deep|nestie|override|parse(_str|ComplexParam|QueryString)?|QueryStringToJSON|recursive|set(ByPath|Getter|In|OrGet|ParamValue|Path(Value)?|Value|ter)?|undefsafe|unflatten|zipObjectDeep)/i

for (let i = 1; i <= 91; i++) {
    let path = root + "/" + i
    var regexs = [];
    readDirSync(path);
    let filename = "D:/pqc/npm/fun/" + i + ".json";
    fs.writeFileSync(filename, JSON.stringify(regexs, null, 4));
}

/**
 * 递归遍历文件夹
 *
 * @param path
 */
function readDirSync(path) {
    var pa = fs.readdirSync(path);
    pa.forEach(function (ele, index) {
        var info = fs.statSync(path + "/" + ele);
        if (info.isDirectory()) {
            if (ele.search("test") >= 0 || ele.search("build") >= 0 || ele.search("compile") >= 0) {
                return;
            }
            readDirSync(path + "/" + ele);
        } else {
            var file = path + "/" + ele;
            if (file.endsWith(".js")) {
                var stat = fs.statSync(file)
                if (stat.size < 500 * 1000) {
                    console.log(file);
                    var result = getReList(file);
                    if (result.regexps.length > 0) {
                        console.log(result);
                        regexs.push(result);
                    }
                }
            }
        }
    })
}

/**
 * 获取当前文件的re
 * @param sourceF
 * @returns {{regexps: [], file: *}}
 */
function getReList(sourceF) {

    var source = fs.readFileSync(sourceF, {encoding: 'utf8'});

    var ast = 0;
    if (!ast) {
        try {
            ast = babylon.parse(source, {
                sourceType: "module",
            });
        } catch (e) {
            ast = 0;
        }
    }

    if (!ast) {
        try {
            ast = babylon.parse(source, {
                sourceType: "script",
            });
        } catch (e) {
            ast = 0;
        }
    }

    if (!ast) {
        ast = 0;
    }

    var allStaticRegexps = [];

    try {
        traverse.default(ast, {
            enter(path) {
                var node = path.node;
                try {
                    var regexpObj;
                    if (node.type === 'CallExpression') {
                        let funcName = "";
                        var args = []
                        try {
                            funcName = node.callee.property.name;
                        } catch (e) {
                            funcName = node.callee.name;
                        }
                        if (funcName.search(func_regex) >= 0) {
                            try {
                                node.arguments.forEach((arg) => {
                                    args.push(arg.name);
                                });
                            } catch (e) {

                            }
                            regexpObj = {
                                funcName: funcName,
                                arguments: args.toString(),
                                line: node.loc.start.line
                            };
                        }
                    }
                } catch (e) {
                }

                if (regexpObj) {
                    allStaticRegexps.push(regexpObj);
                }
            }
        });
    } catch (e) {
    }

    var regexpsArray = [];
    allStaticRegexps.forEach((regexp) => {
        regexpsArray.push(regexp);
    });

    return {
        filename: sourceF,
        regexps: regexpsArray
    }
}

