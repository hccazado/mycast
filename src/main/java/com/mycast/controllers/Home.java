package com.mycast.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Home {
    @GetMapping("/{name}")
    public String helloName(@PathVariable("name") String name){
        Movies moviesController = new Movies();
        moviesController.getMovies();
        return String.format("Hello from Spring app, %s!", name);
    }
}
