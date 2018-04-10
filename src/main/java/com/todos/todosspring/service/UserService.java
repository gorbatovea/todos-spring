package com.todos.todosspring.service;

import com.todos.todosspring.model.User;
import com.todos.todosspring.model.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.ByteBuffer;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public boolean createUser(String login, String password){
        if (contains(login, password)) return false;
        String hash = generateHashCode(password);
        User newUser = new User(login, hash);
        userRepository.save(newUser);
        return userRepository.existsById(newUser.getId());
    }

    public Integer getUserId(String login, String password) {
        String hash = generateHashCode(password);
        for (User user:
                userRepository.findAll()) {
            if (user.getLogin().equals(login) && user.getHash().equals(hash)){
                return user.getId();
            }
        }
        return null;
    }

    public boolean contains(String login, String password) {
        String hash = generateHashCode(password);
        for (User user:
             userRepository.findAll()) {
            if (user.getLogin().equals(login) && user.getHash().equals(hash)) return true;
        }
        return false;
    }

    private static String generateHashCode(String value){
        byte[] bytes = value.getBytes();
        Integer hash = 0;
        for (byte item:
             bytes) {
            hash += Byte.valueOf(item).hashCode();
        }
        return Integer.toString(Integer.toString(hash.hashCode()).hashCode());
    }
}
