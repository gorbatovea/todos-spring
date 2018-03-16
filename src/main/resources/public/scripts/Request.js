function request(method, url, callback, timeout, body){
    var connection = new XMLHttpRequest();
    connection.open(method, url, true);
    connection.setRequestHeader('Content-Type', 'application/json;charset=UTF-8');
    connection.onload = function (onloadEvent) {
        if (connection.status === 200) {
            // console.log(url + ' : ' + connection.status);
            var response = JSON.parse(connection.responseText);
            if (callback !== null) callback(response);
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