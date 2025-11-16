package com.mycast.Services;

import com.mycast.Models.User;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {

    private User currentUser;

    private final Map<String, User> users = new HashMap<>();

    public UserService(){
        users.put("admin", new User("admin", "123"));
    }

    public User authenticate(String username, String password) {
        User user = users.get(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    public User getCurrentUser(){
        return this.currentUser;
    }

    public boolean isSignedId(){
        return currentUser != null;
    }
}
