/**
 *
 * Author: Peng Qiancheng
 *
 */

"use strict";

var babylon = require("babylon");
var traverse = require("babel-traverse");
var fs = require("fs");

const root = "/Users/pqc/Desktop/browserslist-main";
const filename = "browserslist.json";
var regexs = [];

readDirSync(root);
for (let i = 0; i < regexs.length; i++) {
    for(let j = 0;j<regexs[i].regexps.length;j++){
        console.log(regexs[i].regexps[j].pattern)
    }
}
fs.writeFileSync(filename, JSON.stringify(regexs));

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
                console.log(file);
                var result = getReList(file);
                if (result.regexps.length > 0) {
                    console.log(result);
                    regexs.push(result);
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
        bail_couldNotParse(sourceF);
    }

    var allStaticRegexps = [];

    try {
        traverse.default(ast, {
            enter(path) {
                var node = path.node;
                try {
                    var regexpObj;

                    if (node.type === 'RegExpLiteral') {
                        regexpObj = {
                            pattern: node.pattern,
                            flags: node.flags,
                            line: node.loc.start.line
                        };
                    } else if (node.type === 'NewExpression' &&
                        node.callee.type === 'Identifier' && node.callee.name === 'RegExp') {
                        var pattern = (node['arguments'][0].type === 'StringLiteral') ?
                            node['arguments'][0].value : 'DYNAMIC-PATTERN';

                        var flags = '';
                        if (2 <= node['arguments'].length) {
                            flags = (node['arguments'][1].type === 'StringLiteral') ?
                                node['arguments'][1].value : 'DYNAMIC-FLAGS';
                        }

                        regexpObj = {
                            pattern: pattern,
                            flags: flags,
                            line: node.loc.start.line
                        };
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

