function todoFactory(dist, id, value, done) {
    var todo = document.createElement('div');
    todo.setAttribute('class','todos-list-item');
    if (done === true) {
        todo.classList.add('__ready');
    }
    todo.setAttribute('id', id);
    //checkbox node
    todo.appendChild(new function () {
        var checkArea = document.createElement('div'),
            input = document.createElement('input'),
            img = document.createElement('div');
        checkArea.setAttribute('class','todos-list-item_check-w');
        input.setAttribute('class', 'todos-list-item_check');
        input.setAttribute('type', 'checkbox');
        if (done === true){
            input.setAttribute('checked', done);
        }
        input.setAttribute('aria-label', 'set as done');
        img.setAttribute('class', 'todos-list-item_check-icon');
        checkArea.appendChild(input);
        checkArea.appendChild(img);
        return checkArea;
    });
    //text area node
    todo.appendChild(new function () {
        var textArea = document.createElement('input');
        textArea.setAttribute('class', 'todos-list-item_name');
        textArea.setAttribute('type', 'text');
        textArea.setAttribute('value', value);
        if (done){
            textArea.style.textDecoration = 'line-through';
            textArea.style.opacity = '0.5';
        }
        return textArea;
    });
    //delete button node
    todo.appendChild(new function () {
        var deleteArea = document.createElement('div');
        deleteArea.setAttribute('class', 'todos-list-item_delete-w');
        deleteArea.appendChild(new function () {
            var button = document.createElement('button');
            button.setAttribute('class', 'todos-list-item_delete');
            button.setAttribute('aria-label', 'delete item');
            return button;
        });
        return deleteArea;
    });
    dist.appendChild(todo);
    todo.getElementsByClassName('todos-list-item_check')[0]
        .addEventListener('click', function (clickEvent) {
            var item = clickEvent.target.closest('.todos-list-item');
            if (clickEvent.target.checked === true){
                if (!item.classList.contains('__ready')) {
                    item.classList.add('__ready');
                    item.children[1].style.textDecoration = 'line-through';
                    item.children[1].style.opacity = '0.5';
                }
            } else {
                if (item.classList.contains('__ready')) {
                    item.classList.remove('__ready');
                    item.children[1].style.textDecoration = 'none';
                    item.children[1].style.opacity = '1';
                }
            }
            filtrate();
            request('POST', API_SELECT,
                function (response) {
                    if (response === '') {
                        console.error('Response is empty');
                        return;
                    }
                }, TIMEOUT, JSON.stringify({
                    id: item.getAttribute('id'),
                    task: null,
                    done: clickEvent.target.checked
                }));
        });
    todo.getElementsByClassName('todos-list-item_name')[0]
        .addEventListener('focusout', function (focusoutEvent) {
            request('POST', API_UPDATE, null, TIMEOUT, JSON.stringify({
                id: focusoutEvent.target.closest('.todos-list-item').getAttribute('id'),
                task: focusoutEvent.target.value,
                done: null
            }));
        });
    todo.getElementsByClassName('todos-list-item_delete')[0]
        .addEventListener('click', function (clickEvent) {
            var parent = clickEvent.target.closest('.todos-list-item');
            dist.removeChild(parent);
            updateCounter(-1);
            request('POST', API_DELETE,
                function (response) {
                    if (response === '') {
                        console.error('Response is empty');
                        return;
                    }
                }, TIMEOUT, JSON.stringify({
                    id: parent.getAttribute('id'),
                    task: null,
                    done: null
                }));
        });
}