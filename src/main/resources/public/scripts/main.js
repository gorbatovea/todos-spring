var todosList = document.getElementsByClassName('todos-list')[0];
var counter = document.getElementsByClassName('todos-actionbar_counter')[0];
var container = document.getElementsByClassName('todos-container')[0];

var xhr = new XMLHttpRequest();

var  todosCounter = 0;

function getDataFromServer(url){
    xhr.open('GET', url, false);
    xhr.send();
    var response;
    if (xhr.status != 200){
        alert(xhr.status + ': ' + xhr.statusText);
    } else{
        response = xhr.responseText;
    }
    return JSON.parse(response);
}

function sendDataFromServer(url){
    xhr.open('GET', url, false);
    xhr.send();
    var response;
    if (xhr.status != 200){
        alert(xhr.status + ': ' + xhr.statusText);
    } else{
        response = JSON.parse(xhr.responseText);
    }
    if (response.successfully === true) return true;
    else return false;
}

function init(){
    var url = 'http://localhost:8080/api/get';
    var items = getDataFromServer(url);
    if (items.length === 0) return;
    for(var i = 0; i < items.length; i++){
        addTodoViaDesc(items[i].id, items[i].task, items[i].done);
    }
}

function selectAll(){
    var targets = todosList.getElementsByClassName('todos-list-item');
    for (var i = 0; i < targets.length; i++){
        var id = targets[i].getAttribute('id');
        targets[i].getElementsByClassName('todos-list-item_check')[0].checked = true;
        var itemName = targets[i].getElementsByClassName('todos-list-item_name')[0];
        itemName.style.textDecoration = 'line-through';
        itemName.style.opacity = '0.5';
        while(sendDataFromServer('http://localhost:8080/api/select?id=' + id + '&done=true') === false){
            alert('Cannot select id=' + id);
        }
    }
}

function createItem(id, task, done){
    todosCounter++;
    updateCounter();
    var newTodoItem = document.createElement('div'),
        newTextArea = document.createElement('input'),
        checkBox = document.createElement('div'),
        deleteButton = document.createElement('div');
    if (done === true) {
        newTodoItem.setAttribute('class','todos-list-item __ready');
        newTextArea.style.textDecoration = 'line-through';
        newTextArea.style.opacity = '0.5';
    } else {
        newTodoItem.setAttribute('class','todos-list-item');
    }
    newTodoItem.setAttribute('id', id);
    newTodoItem.appendChild(checkBox);
    newTextArea.setAttribute('class', 'todos-list-item_name');
    newTextArea.setAttribute('type', 'text');
    newTextArea.setAttribute('value', task);

    generateCheckBox(checkBox, done);
    generateDeleteButton(deleteButton);

    newTodoItem.appendChild(newTextArea);
    newTodoItem.appendChild(deleteButton);

    return newTodoItem;

    function generateCheckBox(ctx, done) {
        ctx.setAttribute('class','todos-list-item_check-w');
        var input = document.createElement('input'),
            img = document.createElement('div');
        input.setAttribute('class', 'todos-list-item_check');
        input.setAttribute('type', 'checkbox');
        if (done === true){
            input.setAttribute('checked', done);
        }
        input.setAttribute('aria-label', 'set as done');
        img.setAttribute('class', 'todos-list-item_check-icon');
        ctx.appendChild(input);
        ctx.appendChild(img);
    }
    function generateDeleteButton(ctx) {
        ctx.setAttribute('class','todos-list-item_delete-w');
        ctx.innerHTML =
            '<button class="todos-list-item_delete" aria-label="delete item"></button>\n' +
            '\n';
    }
}

function addTodoViaDesc(id, task, done){
    todosList.appendChild(createItem(id, task, done));
    itemInit(changeSelection, updateTextValue, deleteItem, document.getElementById(id));
}

function addTodoIntoList(event){
    if (event.path[0].value.length == 0) return;
    if (event.which === 13){
        var todo = getDataFromServer('http://localhost:8080/api/add?task=' + event.path[0].value);
        todosList.appendChild(createItem(todo.id, todo.task, todo.done));
        itemInit(changeSelection, updateTextValue, deleteItem, document.getElementById(todo.id));
        event.path[0].value = '';
    }
}

function changeSelection(event){
    var ctx = event.path[2];
    var ctxInputField = ctx.getElementsByClassName('todos-list-item_name')[0];
    if (ctx.getElementsByClassName('todos-list-item_check')[0].checked == true){
        ctx.setAttribute('class', 'todos-list-item __ready');
        ctxInputField.style.textDecoration = 'line-through';
        ctxInputField.style.opacity = '0.5';
        while (sendDataFromServer('http://localhost:8080/api/select?id=' + ctx.getAttribute('id') + '&done=true') === false){
            alert('Cannot revert id=' + ctx.getAttribute('id'));
        };
    }else{
        ctx.setAttribute('class', 'todos-list-item');
        ctxInputField.style.textDecoration = 'none';
        ctxInputField.style.opacity = 1.0;
        while (sendDataFromServer('http://localhost:8080/api/select?id=' + ctx.getAttribute('id') + '&done=false') === false){
            alert('Cannot revert id=' + ctx.getAttribute('id'));
        };
    }
}

function deleteItem(event){
    var targetId = event.path[2].id;
    var itemsList= event.path[3].getElementsByClassName('todos-list-item');
    for(var i = 0; i < itemsList.length; i++){
        if (itemsList[i].id === targetId){
            itemsList[i].parentNode.removeChild(itemsList[i]);
            todosCounter--;
            updateCounter();
            while (sendDataFromServer('http://localhost:8080/api/delete?id=' + targetId) === false){
                alert('Cant delete id=' + targetId);
            }
            return;
        }
    }
}

function updateCounter(){
    var counterText = ' items left';
    counter.childNodes[0].data = todosCounter + counterText;
    if (todosCounter == 0) {
        container.setAttribute('class','todos-container __empty');
    }else if (todosCounter == 1){
        container.setAttribute('class','todos-container');
    }
}

function updateTextValue(event){
     // console.log(event.path);
    while(sendDataFromServer('http://localhost:8080/api/update?id=' +
        event.path[1].getAttribute('id') +
        '&task=' + event.target.value) === false){
        alert('Cannot change task value!');
    }
    event.path[0].setAttribute('value', event.target.value);
}

function main() {
    addInit(selectAll, addTodoIntoList);
    updateCounter();
    init();
}

main();