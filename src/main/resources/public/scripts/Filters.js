function filtrate(){
    switch(activeFilter){
        case filters[0  ] : {
            for (var i = 0; i < todoList.children.length; i++) {
                console.log('SHOW ALL');
                todoList.children[i].style.display = 'flex';
            }
            break;
        }
        case filters[2] : {
            for (var i = 0; i < todoList.children.length; i++) {
                if (todoList.children[i].classList.contains('__ready')) {
                    todoList.children[i].style.display = 'flex';
                }
                else {
                    todoList.children[i].style.display = 'none';
                }
            }
            break;
        }
        case filters[1] : {
            for (var i = 0; i < todoList.children.length; i++) {
                if (!todoList.children[i].classList.contains('__ready')) {
                    todoList.children[i].style.display = 'flex';
                }
                else {
                    todoList.children[i].style.display = 'none';
                }
            }
            break;
        }
    }
}