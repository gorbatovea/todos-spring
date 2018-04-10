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

    public Iterable<Todo> getAll(String userId, String token) {
        ArrayList<Todo> list = new ArrayList<>();
        for (Todo item:
             todoRepository.findAll()) {
            if (item.getUserId().equals(userId)) list.add(item);
        }
        list.sort((Comparator) (o1, o2) -> {
            if (((Todo) o1).getId() > ((Todo) o2).getId()) return 1;
            else if (((Todo) o1).getId() < ((Todo) o2).getId()) return -1;
            else return 0;
        });
        return list;
    }

    public Todo addItem(String userId, String body) {
        Todo obj = new Gson().fromJson(body, Todo.class);
        Todo item = new Todo(obj.getTask(), false, userId);
        todoRepository.save(item);
        if (todoRepository.existsById(item.getId())) return item;
        return null;
    }

    public Todo deleteItem (String userId, String body) {
        Todo obj = new Gson().fromJson(body, Todo.class);
        Optional<Todo> optionalItem = todoRepository.findById(obj.getId());
        if (optionalItem.isPresent()) {
            Todo item = optionalItem.get();
            if (item.getUserId().equals(userId)) todoRepository.delete(item);
            else return null;
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

    public Todo updateSelection(String userId, String body){
        Todo obj = new Gson().fromJson(body, Todo.class);
        Optional<Todo> optionalTodo = todoRepository.findById(obj.getId());
        if (optionalTodo.isPresent()) {
            Todo item = optionalTodo.get();
            if (item.getUserId().equals(userId)) item.setDone(obj.isDone());
            else return null;
            todoRepository.save(item);
            return item;
        }
        return null;
    }

    public boolean updateTask(String userId, String body){
        Todo obj = new Gson().fromJson(body, Todo.class);
        Optional<Todo> optionalItem = todoRepository.findById(obj.getId());
        if (optionalItem.isPresent()) {
            Todo item = optionalItem.get();
            if (item.getUserId().equals(userId)) item.setTask(obj.getTask());
            else return false;
            todoRepository.save(item);
            return true;
        }
        return false;
    }
}
