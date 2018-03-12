function itemInit(changeSelection, updateTextValue, deleteItem, target){
     target.getElementsByClassName('todos-list-item_check')[0].addEventListener('change', changeSelection, false);
     target.getElementsByClassName('todos-list-item_name')[0].addEventListener('focusout', updateTextValue, false);
     target.getElementsByClassName('todos-list-item_delete')[0].addEventListener('click', deleteItem, false);
}