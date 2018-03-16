package com.todos.todosspring.web;

import com.todos.todosspring.model.Todo;
import com.todos.todosspring.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class TodosController {
    @Autowired
    private TodoService todoService;

    @RequestMapping("/getAll")
    public @ResponseBody Iterable<Todo> getTodosList(){
        return todoService.getAll();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/add")
    public @ResponseBody Todo addTodo(@RequestBody String body){
        return todoService.addItem(body);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/delete")
    public @ResponseBody Todo deleteTodo(@RequestBody String body){
        return todoService.deleteItem(body);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/update")
    public @ResponseBody boolean updateTodo(@RequestBody String body){
        return todoService.updateTask(body);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/select")
    public @ResponseBody Todo revert(@RequestBody String body){
        return todoService.updateSelection(body);
    }

    @RequestMapping(value = "/dropAll")
    public @ResponseBody boolean revert(){
        return todoService.deleteAll();
    }
}
