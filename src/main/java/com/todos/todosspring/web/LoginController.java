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
public class LoginController {
    final private static String LOGIN_PAGE = "login.html";
    final private static String HOME_PAGE = "todos.html";
    final private static String USER_ID_COOKIE = "UserId";
    final private static String TOKEN_COOKIE = "LoginToken";
    
    @Autowired
    private SessionService sessionService;
    @Autowired
    private UserService userService;

    @GetMapping(value = "/")
    public String getPage(@CookieValue(value = "UserId", defaultValue = "") String userId,
                            @CookieValue(value = "LoginToken", defaultValue = "") String sessionId) {
        if (sessionId.equals("")) {
            return LOGIN_PAGE;
        } else {
            if(sessionService.isIdFormat(userId, 10)){
                if (!sessionService.validate(userId, sessionId)) {
                    return LOGIN_PAGE;
                } else {
                    return HOME_PAGE;
                }
            } else {
                return LOGIN_PAGE;
            }
        }
    }

    @GetMapping("/login")
    public String getLoginPage(@CookieValue(value = "UserId", defaultValue = "") String userId,
                               @CookieValue(value = "LoginToken", defaultValue = "") String sessionId) {
        return getPage(userId, sessionId);
    }

    @GetMapping("/home")
    public String getHomePage(){
        return LOGIN_PAGE;
    }

    @PostMapping("/home")
    public String getHomePage(@RequestBody String body, HttpServletResponse httpServletResponse) {
        Pair<String, String>[] formData = Utils.parseFormUrlEncode(body);
        if (formData == null) return LOGIN_PAGE;
        if (formData.length != 2) return LOGIN_PAGE;
        String login = formData[0].getValue(), password = formData[1].getValue();
        Integer userId = userService.getUserId(login, password);
        if (userId == null) return LOGIN_PAGE;
        else {
            Cookie userIdCookie = new Cookie(USER_ID_COOKIE, Integer.toString(userId)),
                    loginTokenCookie = new Cookie(TOKEN_COOKIE, sessionService.createSession(userId));
            userIdCookie.setHttpOnly(true);
            loginTokenCookie.setHttpOnly(true);
            httpServletResponse.addCookie(userIdCookie);
            httpServletResponse.addCookie(loginTokenCookie);
            return HOME_PAGE;
        }
    }
}