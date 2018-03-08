function addItem(event){
    if(event.keyCode == 13) {
        var inputForm = document.getElementsByClassName("todo-add_put-item");
        createTodo(inputForm[0]);
    }
}

function selectItem(id) {
    console.log('Selecting item');
    setSelectedToItem(id);
}

function selectALL() {
    setSelectedToAll();
}

function deleteClick(id) {
    deleteItem(id);
}

function deleteCompleted() {
    console.log('Remove completed items');
    removeCompletedItems();
}

function filterAll() {
    setFilter('all');
}

function filterActive() {
    setFilter('active');
}

function filterCompleted() {
    setFilter('completed');
}