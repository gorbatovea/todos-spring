package com.todos.todosspring.service;

import com.google.gson.Gson;
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

    public Todo addItem(String body) {
        Todo obj = new Gson().fromJson(body, Todo.class);
        Todo item = new Todo(obj.getTask(), false);
        todoRepository.save(item);
        if (todoRepository.existsById((Integer)item.getId())) return item;
        return null;
    }

    public Todo deleteItem (String body) {
        Todo obj = new Gson().fromJson(body, Todo.class);
        Optional<Todo> optionalItem = todoRepository.findById(obj.getId());
        if (optionalItem.isPresent()) {
            Todo item = optionalItem.get();
            todoRepository.delete(item);
            if (!todoRepository.existsById(obj.getId()))
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

    public Todo updateSelection(String body){
        Todo obj = new Gson().fromJson(body, Todo.class);
        Optional<Todo> optionalTodo = todoRepository.findById(obj.getId());
        if (optionalTodo.isPresent()) {
            Todo item = optionalTodo.get();
            item.setDone(obj.isDone());
            todoRepository.save(item);
            return item;
        }
        return null;
    }

    public boolean updateTask(String body){
        Todo obj = new Gson().fromJson(body, Todo.class);
        Optional<Todo> optionalItem = todoRepository.findById(obj.getId());
        if (optionalItem.isPresent()) {
            Todo item = optionalItem.get();
            item.setTask(obj.getTask());
            todoRepository.save(item);
            return true;
        }
        return false;
    }
}
