package com.todos.todosspring.web;

import com.todos.todosspring.model.Response;
import com.todos.todosspring.model.Todo;
import com.todos.todosspring.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TodosController {
    @Autowired
    private TodoService todoService;

    @RequestMapping("/get")
    public @ResponseBody List<Todo> getTodosList(){
        return todoService.getAll();
    }

    @RequestMapping(value = "/add", params = {"task"})
    public @ResponseBody Todo addTodo(@RequestParam(value = "task") String task){
        return todoService.addItem(task);
    }

    @RequestMapping(value = "/delete", params = {"id"})
    public @ResponseBody Response deleteTodo(@RequestParam(value = "id") String id){
        return todoService.deleteItem(id);
    }

    @RequestMapping(value = "/update", params = {"id", "task"})
    public @ResponseBody Response updateTodo(@RequestParam(value = "id") String id,
                                             @RequestParam(value = "task") String task){
        return todoService.updateTask(id, task);
    }

    @RequestMapping(value = "/select", params = {"id", "done"})
    public @ResponseBody Response revert(@RequestParam(value = "id") String id,
                                         @RequestParam(value = "done") boolean done){
        return todoService.updateSelection(id, done);
    }
}
