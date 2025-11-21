package com.mycast.Services;

import com.mycast.Models.User;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {

    private User currentUser;

    private final Map<String, User> users = new HashMap<>();

    //Constructor with a placeholder account.
    public UserService(){
        users.put("admin", new User("admin", "123"));
    }

    public User authenticate(String username, String password) {
        //searches for the user credentials and verifies the user password
        User user = users.get(username);
        if (user != null && user.getPassword().equals(password)) {
            //correct password, returning the user data for the session
            return user;
        }
        return null;
    }
}
