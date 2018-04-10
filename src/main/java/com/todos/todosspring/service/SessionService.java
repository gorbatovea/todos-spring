package com.todos.todosspring.service;

import com.todos.todosspring.model.Session;
import com.todos.todosspring.model.SessionRepository;
import com.todos.todosspring.model.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Optional;

@Service
public class SessionService {
    @Autowired
    private SessionRepository sessionRepository;
    private UserService userService = new UserService();

    public String createSession(String login, String password){
        Integer userId = userService.getUserId(login, password);
        if (userId == null) return null;
        else {
            String token = generateToken();
            for (Session session:
                 sessionRepository.findAll()) {
                if (session.getUserId().equals(userId)) sessionRepository.delete(session);
            }
            sessionRepository.save(new Session(userId, token));
            return token;
        }
    }

    public String createSession(Integer userId){
        if (userId == null) return null;
        else {
            String token = generateToken();
            Optional<Session> optionalSession = sessionRepository.findById(userId);
            if (optionalSession.isPresent()){
                Session session = optionalSession.get();
                session.setToken(token);
                sessionRepository.save(session);
            } else {
                sessionRepository.save(new Session(userId, token));
            }
            return token;
        }
    }

    public boolean validate(String userId, String token){
        if (!isIdFormat(userId, 10)) return false;
        for (Session session:
             sessionRepository.findAll()) {
            if (session.getUserId().equals(Integer.parseInt(userId)) && session.getToken().equals(token))
                return true;
        }
        return false;
    }

    public boolean isIdFormat(String id, int radix){
        if(id.isEmpty()) return false;
        for(int i = 0; i < id.length(); i++) {
            if(i == 0 && id.charAt(i) == '-') {
                if(id.length() == 1) return false;
                else continue;
            }
            if(Character.digit(id.charAt(i),radix) < 0) return false;
        }
        return true;
    }

    private String generateToken(){
        SecureRandom random = new SecureRandom();
        byte bytes[] = new byte[20];
        random.nextBytes(bytes);
        return bytes.toString();
    }
}
