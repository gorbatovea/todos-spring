function addInit(selectAll, addTodoIntoList){
    var inputItem = document.getElementsByClassName('todo-add_put-item')[0];
    var selectAllItem = document.getElementsByClassName('todo-add_select-all')[0];
    selectAllItem.addEventListener('click', selectAll, false);
    inputItem.addEventListener('keyup', addTodoIntoList, false);
}