package com.todos.todosspring.web;

import com.todos.todosspring.service.SessionService;
import com.todos.todosspring.service.UserService;
import com.todos.todosspring.utils.Utils;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
public class RegistrationController {
    final private static String LOGIN_PAGE = "login.html";
    final private static String HOME_PAGE = "todos.html";
    final private static String USER_ID_COOKIE = "UserId";
    final private static String TOKEN_COOKIE = "LoginToken";

    @Autowired
    private UserService userService;

    @GetMapping("/registrate")
    public @ResponseBody boolean registrateUser(@RequestParam("login") String login,
                                                @RequestParam("password") String password){
        return userService.createUser(login, password);
    }
}

