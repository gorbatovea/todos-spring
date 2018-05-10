package com.todos.todosspring.web;

import com.todos.todosspring.service.UserService;
import com.todos.todosspring.utils.Utils;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static com.todos.todosspring.utils.Utils.*;

@Controller
public class RegistrationController {
    @Autowired
    private UserService userService;

    @GetMapping("/registration")
    public String registrationPage() {
        return REGISTRATION_PAGE;
    }

    @PostMapping("/submit")
    public String registrateUser(@RequestBody String body){
        Pair<String, String>[] formData = Utils.parseFormUrlEncode(body);
        if (formData == null) return REGISTRATION_PAGE;
        if (formData.length != 2) return REGISTRATION_PAGE;
        String login = formData[0].getValue(), password = formData[1].getValue();
        if (userService.createUser(login, password)) {
            return LOGIN_PAGE;
        } else {
            return REGISTRATION_PAGE;
        }
    }
}

