package com.mycast.Controllers;

import com.google.cloud.firestore.Firestore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {

    private final Firestore db;

    @Autowired
    public UserController(Firestore firestore) {
        this.db = firestore;
    }
}
