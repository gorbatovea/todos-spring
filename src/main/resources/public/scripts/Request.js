function request(method, url, callback, timeout, body){
    var HTTPConnection = new XMLHttpRequest();
    HTTPConnection.open(method, url, true);
    HTTPConnection.onload = function (onloadEvent) {
        if (HTTPConnection.status === 200) {
            console.log(url + ' : ' + HTTPConnection.status);
            var response = JSON.parse(HTTPConnection.responseText);
            if (callback !== null) callback(response);
        } else{
            console.error(url + ' : ' + HTTPConnection.status);
            callback(null);
        }
    };
    HTTPConnection.ontimeout = function (ontimeoutEvent) {
        console.error('Connection timeout');
    };
    HTTPConnection.timeout = timeout;
    HTTPConnection.send(body);
}

// function generateJSON(id, name, done){
//     var result = [];
//     result['id'] = id;
//     result['name'] = name;
//     result['done'] = done;
//     return JSON.stringify(result);
// }
//
// console.log(generateJSON(1, 'html', false));