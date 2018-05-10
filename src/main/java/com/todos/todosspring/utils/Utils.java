package com.todos.todosspring.utils;

import javafx.util.Pair;

public class Utils {
    final public static String LOGIN_PAGE = "login.html";
    final public static String HOME_PAGE = "todos.html";
    final public static String USER_ID_COOKIE = "UserId";
    final public static String LOGIN_TOKEN_COOKIE = "LoginToken";
    final public static String EMPTY_STRING = "";
    final public static String LOGIN_PARAM = "login";
    final public static String PASSWORD_PARAM = "password";
    final public static String LOGIN_REDIRECT_PAGE = "login-redirect.html";
    final public static String REGISTRATION_PAGE = "registration.html";
    
    public static Pair<String, String>[] parseFormUrlEncode(String src){
        if (src.length() > 0) {
            String[] parts = src.split("&");
            Pair<String, String>[] dist = new Pair[parts.length];
            for(int i = 0; i < parts.length; i++){
                String[] temp = parts[i].split("=");
                if (temp.length != 2) return null;
                dist[i] = new Pair<>(temp[0], temp[1]);
            }
            return dist;
        }
        return null;
    }
}
