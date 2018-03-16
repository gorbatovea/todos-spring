document.addEventListener('DOMContentLoaded', function () {
    //context vars init
    todoContainer = document.getElementsByClassName('todos-container')[0];
    todoList = document.getElementsByClassName('todos-list')[0];
    selectAll = document.getElementsByClassName('todo-add_select-all-w')[0];
    inputField = document.getElementsByClassName('todo-add_put-item')[0];
    counterField = document.getElementsByClassName('todos-actionbar_counter')[0];
    filters = document.getElementsByClassName('filter');
    activeFilter = document.getElementsByClassName('filter __active')[0];
    //listener for 'select all' button
    document.getElementsByClassName('todo-add_select-all')[0].addEventListener('click',
        function (clickEvent) {
            var items = todoList.children;
            for(var i = 0; i < items.length; i++) {
                items[i].classList.add('__ready');
                items[i].getElementsByClassName('todos-list-item_check')[0].checked=true;
                items[i].getElementsByClassName('todos-list-item_name')[0].style.textDecoration = 'line-through';
                items[i].getElementsByClassName('todos-list-item_name')[0].style.opacity = '0.5';
                request('POST', API_SELECT,
                function (response) {
                    if ((response === null)) {
                        console.error('Response is empty');
                        return;
                    }
                }, TIMEOUT, JSON.stringify({
                        id: items[i].getAttribute('id'),
                        task: null,
                        done: true
                    }));
            }
        });
    //listener for 'input field'
    document.getElementsByClassName('todo-add_put-item')[0].addEventListener('keypress',
        function(keyPressEvent){
        var addingKeyNumber = 13;
        if ((keyPressEvent.which === addingKeyNumber) && (inputField.value !== '')){
            request('POST', API_ADD, function (response) {
                todoFactory(todoList, response.id, response.task, response.done);
                updateCounter(1);
                if (activeFilter === filters[2]){
                    activeFilter.classList.remove('__active');
                    activeFilter = filters[1];
                    activeFilter.classList.add('__active');
                    filtrate();
                }
            }, TIMEOUT, JSON.stringify({
                id: null,
                task: inputField.value,
                done: null
            }));
            inputField.value = '';
        }
    });
    //fetching all stored items
    request('GET', API_GETALL, function (response) {
        for(var i = 0; i < response.length; i++) {
            todoFactory(todoList, response[i].id, response[i].task, response[i].done);
            updateCounter(1);
        }
    }, TIMEOUT, null);
    //listeners for 'filters'
    filters[0].addEventListener('click',
        function (clickEvent) {
            activeFilter.classList.remove('__active');
            activeFilter = clickEvent.target;
            activeFilter.classList.add('__active');
            filtrate();
        });
    filters[1].addEventListener('click', 
        function (clickEvent) {
            activeFilter.classList.remove('__active');
            activeFilter = clickEvent.target;
            activeFilter.classList.add('__active');
            filtrate();
        });
    filters[2].addEventListener('click', 
        function (clickEvent) {
            for(var i = 0; i < filters.length; i++){
                activeFilter.classList.remove('__active');
                activeFilter = clickEvent.target;
                activeFilter.classList.add('__active');
                filtrate();
            }
        });
    //listener for 'delete' button
    document.getElementsByClassName('todos-actionbar_delete-ready')[0].addEventListener('click',
        function (clickEvent) {
        var i = 0;
        while(true){
            var item = todoList.children[i];
            if (item === undefined) break;
            if (item.getAttribute('class') === 'todos-list-item __ready') {
                request('POST', API_DELETE,
                    function (response) {
                        if (response === '') {
                            console.error('Response is empty');
                            return;
                        }
                    }, TIMEOUT, JSON.stringify({
                        id: item.getAttribute('id'),
                        task: null,
                        done: null
                    }));
                todoList.removeChild(todoList.children[i]);
                updateCounter(-1);
            } else {
                i++;
            }
        }
    });
});