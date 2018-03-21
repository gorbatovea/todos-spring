package com.todos.todosspring.service;

import com.google.gson.Gson;
import com.todos.todosspring.model.Todo;
import com.todos.todosspring.model.TodoRepository;
import javafx.collections.transformation.SortedList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Optional;

@Service
public class TodoService {
    private static int idCounter= 0;
    @Autowired
    TodoRepository todoRepository;

    public Iterable<Todo> getAll() {
        ArrayList<Todo> list = new ArrayList<>();
        for (Todo item:
             todoRepository.findAll()) {
            list.add(item);
        }
        list.sort(new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                if (((Todo) o1).getId() > ((Todo) o2).getId()) return 1;
                else if (((Todo) o1).getId() < ((Todo) o2).getId()) return -1;
                else return 0;
            }
        });
        return list;
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
