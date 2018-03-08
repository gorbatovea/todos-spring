var todosContainer = document.getElementsByClassName('todos-container');
var todosList = document.getElementsByClassName('todos-list');
var todosActionbar = document.getElementsByClassName('todos-actionbar');

if (todosList !== 'undefined'){
    console.log("Todos list is ready");
}

function resetInputForm(form) {
    form.value = '';
}

function pushItemToView(newTodoItem) {
    todosList[0].insertBefore(newTodoItem, todosList[0].firstChild.nextSibling);
    if (isEmpty) {
        todosContainer[0].setAttribute('class','todos-container');
        isEmpty = false;
    }
    todosActionbar[0].childNodes[1].childNodes[0].data = itemsCounter + ' items left';
    changeViewByfilter(currentFilter);
}

function removeItemFromView(itemToDelete) {
    todosList[0].removeChild(itemToDelete);
    if (itemsCounter === 0) {
        todosContainer[0].setAttribute('class', todosContainer[0].getAttribute('class') + ' __empty');
        isEmpty = true;
        currentFilter = 'all';
    }
    todosActionbar[0].childNodes[1].childNodes[0].data = itemsCounter + ' items left';
}

function pushSelectedToItem(item) {
    if(item.getAttribute('class') === 'todos-list-item'){
        item.setAttribute('class', 'todos-list-item __ready');
        item.childNodes[0].childNodes[0].checked = true;
    }else{
        item.setAttribute('class', 'todos-list-item');
        item.childNodes[0].childNodes[0].checked = false;
    }
    changeViewByfilter(currentFilter);
}

function pushSelectortToItem(item, itemClass) {
    item.childNodes[0].childNodes[0].checked = true;
    item.setAttribute('class',itemClass);
    changeViewByfilter(currentFilter);
}

function changeViewByfilter(filter) {
    console.log(todosActionbar[0].childNodes[3].childNodes[1]);
    if (filter === 'all') {
        console.log('All filter');
        todosActionbar[0].childNodes[3].childNodes[1].setAttribute('class', 'todos-actionbar_filter-all __active');
        todosActionbar[0].childNodes[3].childNodes[3].setAttribute('class', 'todos-actionbar_filter-active');
        todosActionbar[0].childNodes[3].childNodes[5].setAttribute('class', 'todos-actionbar_filter-ready');
        for (var i = 0, len = todoItemsList.length; i < len; i++) {
            todoItemsList[i].style.display = 'flex';
        }
    }
    if (filter === 'active') {
        console.log('Active filter');
        todosActionbar[0].childNodes[3].childNodes[1].setAttribute('class', 'todos-actionbar_filter-all');
        todosActionbar[0].childNodes[3].childNodes[3].setAttribute('class', 'todos-actionbar_filter-active __active');
        todosActionbar[0].childNodes[3].childNodes[5].setAttribute('class', 'todos-actionbar_filter-ready');
        for (var i = 0, len = todoItemsList.length; i < len; i++) {
            if (todoItemsList[i].getAttribute('class') === 'todos-list-item __ready') {
                todoItemsList[i].style.display = 'none';
            } else {
                todoItemsList[i].style.display = 'flex';
            }

        }
    }
    if (filter === 'completed') {
        console.log('Completed filter');
        todosActionbar[0].childNodes[3].childNodes[1].setAttribute('class', 'todos-actionbar_filter-all');
        todosActionbar[0].childNodes[3].childNodes[3].setAttribute('class', 'todos-actionbar_filter-active');
        todosActionbar[0].childNodes[3].childNodes[5].setAttribute('class', 'todos-actionbar_filter-ready __active');
        for (var i = 0, len = todoItemsList.length; i < len; i++) {
            if (todoItemsList[i].getAttribute('class') === 'todos-list-item __ready') {
                todoItemsList[i].style.display = 'flex';
            } else {
                todoItemsList[i].style.display = 'none';
            }
        }
    }
}