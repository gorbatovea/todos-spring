function request(method, url, callback, timeout, body){
    var connection = new XMLHttpRequest();
    connection.open(method, url, true);
    connection.setRequestHeader('Content-Type', 'application/json;charset=UTF-8');
    connection.onload = function () {
        if (connection.status === 200) {
            if (callback !== null) callback(connection.responseText);
        } else{
            console.error(url + ' : ' + connection.status);
            callback(null);
        }
    };
    connection.ontimeout = function (ontimeoutEvent) {
        console.error('Connection timeout');
    };
    connection.timeout = timeout;
    connection.send(body);
}