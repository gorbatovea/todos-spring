package com.todos.todosspring.web;

import com.todos.todosspring.model.Session;
import com.todos.todosspring.model.Todo;
import com.todos.todosspring.service.SessionService;
import com.todos.todosspring.service.TodoService;
import com.todos.todosspring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

@RestController
@RequestMapping("/api")
public class TodosController {
    @Autowired
    private TodoService todoService;
    @Autowired
    private SessionService sessionService;
    @RequestMapping("/getAll")
    public @ResponseBody Iterable<Todo>
    getTodosList(@CookieValue(value = "UserId", defaultValue = "") String userId,
                 @CookieValue(value = "LoginToken", defaultValue = "") String token) {
        if (sessionService.validate(userId, token)) {
            return todoService.getAll(userId, token);
        }
        return null;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/add")
    public @ResponseBody Todo addTodo(@CookieValue(value = "UserId", defaultValue = "") String userId,
                                      @CookieValue(value = "LoginToken", defaultValue = "") String token,
                                      @RequestBody String body) {
        if (sessionService.validate(userId, token))
            return todoService.addItem(userId, body);
        return null;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/delete")
    public @ResponseBody Todo deleteTodo(@CookieValue(value = "UserId", defaultValue = "") String userId,
                                         @CookieValue(value = "LoginToken", defaultValue = "") String token,
                                         @RequestBody String body) {
        if (sessionService.validate(userId, token))
            return todoService.deleteItem(userId, body);
        return null;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/update")
    public @ResponseBody boolean updateTodo(@CookieValue(value = "UserId", defaultValue = "") String userId,
                                            @CookieValue(value = "LoginToken", defaultValue = "") String token,
                                            @RequestBody String body) {
        return sessionService.validate(userId, token) && todoService.updateTask(userId, body);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/select")
    public @ResponseBody Todo revert(@CookieValue(value = "UserId", defaultValue = "") String userId,
                                     @CookieValue(value = "LoginToken", defaultValue = "") String token,
                                     @RequestBody String body) {
        if (sessionService.validate(userId, token)) {
            return todoService.updateSelection(userId, body);
        }
        return null;
    }

    @RequestMapping(value = "/dropAll")
    public @ResponseBody boolean dropAll(){
        return todoService.deleteAll();
    }
}
