package com.todos.todosspring.service;

import com.todos.todosspring.model.Todo;
import com.todos.todosspring.model.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TodoService {
    private static int idCounter= 0;
    @Autowired
    TodoRepository todoRepository;

    public Iterable<Todo> getAll() {
        return todoRepository.findAll();
    }

    public Todo addItem(String name) {
        Todo item = new Todo(name, false);
        todoRepository.save(item);
        if (todoRepository.existsById((Integer)item.getId())) return item;
        return null;
    }

    public Todo deleteItem (String id) {
        Optional<Todo> optionalItem = todoRepository.findById(Integer.parseInt(id));
        if (optionalItem.isPresent()) {
            Todo item = optionalItem.get();
            todoRepository.delete(item);
            if (!todoRepository.existsById(Integer.parseInt(id)))
                return item;
        }
        return null;
    }

    public Boolean deleteAll(){
        if (todoRepository.count() > 0){
            todoRepository.deleteAll();
            return true;
        }
        return false;
    }

    public Todo updateSelection(String id, boolean done){
        Optional<Todo> optionalTodo = todoRepository.findById(Integer.parseInt(id));
        if (optionalTodo.isPresent()) {
            Todo item = optionalTodo.get();
            item.setDone(done);
            todoRepository.save(item);
            return item;
        }
        return null;
    }

    public boolean updateTask(String id, String newTask){
        Optional<Todo> optionalItem = todoRepository.findById(Integer.parseInt(id));
        if (optionalItem.isPresent()) {
            Todo item = optionalItem.get();
            item.setTask(newTask);
            todoRepository.save(item);
            return true;
        }
        return false;
    }
}
