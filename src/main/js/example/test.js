Promise.timeout = function (timeout, cb) {
    return Promise.race([
        new Promise(cb),
        new Promise(function (resolve, reject) {
            setTimeout(function () {
                reject('Timed out');
            }, timeout);
        })
    ]);
};

function delayedHello(cb) {
    var pattern = /(a*)*abc/;
    var s = Date.now();
    var str = "aaaaaaaaaaaaaaaaaaaaaaaaaaabb";
    var b = pattern.test(str);
    console.log(b);
    console.log(Date.now() - s);
    setTimeout(function () {
        cb('Hello');
    }, 2000);
    var str1 = str.replace(/^[\s\uFEFF\xA0]+|[\s\uFEFF\xA0]+$/g, '');
    var str2 = str.replace(pattern, '');
}


Promise.timeout(1000, delayedHello).then(function (data) {
    console.log(data);
}).catch(function (e) {
    console.log(e);
}); //delayedHello makes it.