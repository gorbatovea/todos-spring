package com.todos.todosspring.web;

import com.todos.todosspring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static com.todos.todosspring.utils.Utils.*;

@Controller
public class RegistrationController {
    @Autowired
    private UserService userService;

    @GetMapping("/registrate")
    public @ResponseBody boolean registrateUser(@RequestParam(LOGIN_PARAM) String login,
                                                @RequestParam(PASSWORD_PARAM) String password){
        return userService.createUser(login, password);
    }
}

