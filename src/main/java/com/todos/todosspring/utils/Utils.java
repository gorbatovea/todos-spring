package com.todos.todosspring.utils;

import javafx.util.Pair;

public class Utils {
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
