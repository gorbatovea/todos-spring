package com.todos.todosspring.web;

import com.todos.todosspring.model.Todo;
import com.todos.todosspring.service.SessionService;
import com.todos.todosspring.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.todos.todosspring.utils.Utils.*;

@RestController
@RequestMapping("/api")
public class TodosController {
    @Autowired
    private TodoService todoService;
    @Autowired
    private SessionService sessionService;
    @RequestMapping("/getAll")
    public @ResponseBody Iterable<Todo>
    getTodosList(@CookieValue(value = USER_ID_COOKIE, defaultValue = EMPTY_STRING) String userId,
                 @CookieValue(value = LOGIN_TOKEN_COOKIE, defaultValue = EMPTY_STRING) String token) {
        if (sessionService.validate(userId, token)) {
            return todoService.getAll(userId, token);
        }
        return null;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/add")
    public @ResponseBody Todo addTodo(@CookieValue(value = USER_ID_COOKIE, defaultValue = EMPTY_STRING) String userId,
                                      @CookieValue(value = LOGIN_TOKEN_COOKIE, defaultValue = EMPTY_STRING) String token,
                                      @RequestBody String body) {
        if (sessionService.validate(userId, token))
            return todoService.addItem(userId, body);
        return null;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/delete")
    public @ResponseBody Todo deleteTodo(@CookieValue(value = USER_ID_COOKIE, defaultValue = EMPTY_STRING) String userId,
                                         @CookieValue(value = LOGIN_TOKEN_COOKIE, defaultValue = EMPTY_STRING) String token,
                                         @RequestBody String body) {
        if (sessionService.validate(userId, token))
            return todoService.deleteItem(userId, body);
        return null;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/update")
    public @ResponseBody boolean updateTodo(@CookieValue(value = USER_ID_COOKIE, defaultValue = EMPTY_STRING) String userId,
                                            @CookieValue(value = LOGIN_TOKEN_COOKIE, defaultValue = EMPTY_STRING) String token,
                                            @RequestBody String body) {
        return sessionService.validate(userId, token) && todoService.updateTask(userId, body);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/select")
    public @ResponseBody Todo revert(@CookieValue(value = USER_ID_COOKIE, defaultValue = EMPTY_STRING) String userId,
                                     @CookieValue(value = LOGIN_TOKEN_COOKIE, defaultValue = EMPTY_STRING) String token,
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
