package com.todos.todosspring.service;

import com.todos.todosspring.model.Response;
import com.todos.todosspring.model.Todo;
//import com.todos.todosspring.model.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TodoService {
    private static int idCounter= 0;
//    @Autowired
//    TodoRepository<Todo> todoRepository;

//    static{
//        items.add(new Todo(Integer.toString(idCounter++), "Make a breakfast", true));
//        items.add(new Todo(Integer.toString(idCounter++), "Make a lunch", false));
//        items.add(new Todo(Integer.toString(idCounter++), "Make a dinner", false));
//    }

    public List<Todo> getAll() {
//        return items;
        return null;
    }

    public Todo addItem(String name){
//        Todo item = new Todo(name, false);
//        todoRepository.save(item);
//        if (todoRepository.existsById((long)item.getId())) return item;
        return null;
    }

    public Response deleteItem (String id){
//        todoRepository.deleteById(Long.parseLong(id));
//        if (!todoRepository.existsById(Long.parseLong(id)))
//            return new Response(true, "OK! Deleted element with id=" + id);
//        else
//            return new Response(false, "ERROR! Can't delete element with id=" + id);
        return null;
    }

    public Response updateSelection(String id, boolean done){
//        Todo item = new Todo();
//        item.setId(Integer.parseInt(id));
//        item.setDone(done);
//        todoRepository.save(item);
//        for (Todo current : items) {
//            if (current.getId().equals(id)) {
//                current.setDone(done);
//                return new Response(true, "OK! Selection for id=" +
//                        current.getId() +
//                        " setted to " +
//                        current.isDone());
//            }
//        }
//        return new Response(false, "ERROR! Selection for id=" + id);
        return null;
    }

    public Response updateTask(String id, String newTask){
//        for (Todo current : items) {
//            if (current.getId().equals(id)){
//                current.setTask(newTask);
//                return new Response(true, "OK! Task updated for id=" + id);
//            }
//        }
//        return new Response(false, "ERROR! Can't update task for id=" + id);
        return null;
    }
}
