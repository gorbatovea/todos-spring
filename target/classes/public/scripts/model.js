todoItemsList = Array.from(document.getElementsByClassName('todos-list-item'));
console.log(todoItemsList);
console.log(typeof todoItemsList);

var itemsCounter = 0;
var idCounter = 0;
var isEmpty = true;
var currentFilter = 'all';

function createTodo(input){
    if (input.value.length === 0) return;
    itemsCounter++;
    idCounter++;
    var newTodoItem = document.createElement('div'),
        newTextArea = document.createElement('input'),
        checkBox = document.createElement('div'),
        deleteButton = document.createElement('div');

    generateCheckBox(checkBox);
    generateDeleteButton(deleteButton)

    newTodoItem.setAttribute('class','todos-list-item');
    newTodoItem.setAttribute('id',idCounter);

    newTodoItem.appendChild(checkBox);

    newTextArea.setAttribute('class','todos-list-item_name');
    newTextArea.setAttribute('value', input.value);
    //newTextArea.appendChild(document.createTextNode(input.value));
    newTodoItem.appendChild(newTextArea);

    newTodoItem.appendChild(deleteButton);

    todoItemsList.push(newTodoItem);

    console.log('Adding element ' + input.value);
    pushItemToView(newTodoItem)
    resetInputForm(input);
}
//
function generateCheckBox(ctx) {
    ctx.setAttribute('class','todos-list-item_check-w');
    ctx.innerHTML =
        '<input class="todos-list-item_check" id=' + idCounter + ' type="checkbox" onclick="selectItem(this.id)" aria-label="set as done">\n' +
        '\n' +
        '<div class="todos-list-item_check-icon"></div>\n' +
        '\n';
}

function generateDeleteButton(ctx) {
    ctx.setAttribute('class','todos-list-item_delete-w');
    ctx.innerHTML =
        '<button class="todos-list-item_delete" id=' + idCounter + ' onclick="deleteClick(this.id)" aria-label="delete item"></button>\n' +
        '\n';
}

function deleteItem(id) {
    console.log('Deleting item');
    var deletingItem = document.getElementById(id);
    console.log()
    for(var i = 0, len = todoItemsList.length; i < len; i++){
        if (todoItemsList[i].getAttribute('id') === deletingItem.getAttribute("id")) {
            itemsCounter--;
            removeItemFromView(todoItemsList[i]);
            todoItemsList.splice(i, 1);
            break;
        }
    }
}

function setSelectedToItem(id) {
    console.log('Setting selector');
    var item = document.getElementById(id);
    for(var i = 0, len = todoItemsList.length; i < len; i++) {
        if (todoItemsList[i].getAttribute('id') === id){
            pushSelectedToItem(todoItemsList[i]);
        }
    }
}

function setSelectedToAll() {
    if (itemsCounter === 0) return;

    console.log('Setting selectors');

    for(var i = 0, len = todoItemsList.length; i < len; i++){
        pushSelectortToItem(document.getElementById(todoItemsList[i].getAttribute('id')), 'todos-list-item __ready');
    }
}

function removeCompletedItems(){
    if (itemsCounter === 0) return;
    for(var i = 0, len = todoItemsList.length; i < len; i++){
        if (todoItemsList[i].getAttribute('class') === 'todos-list-item __ready'){
            itemsCounter--;
            console.log(document.getElementById(todoItemsList[i].getAttribute('id')));
            removeItemFromView(document.getElementById(todoItemsList[i].getAttribute('id')));
            todoItemsList.splice(i, 1);
            i--; len--;
        }
    }
}

function setFilter(filter) {
    currentFilter = filter;
    changeViewByfilter(filter);
}