package com.mycast.Controllers;

import com.mycast.Models.Movies;
import com.mycast.Services.MovieApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {
    //indicating to Spring that movieApiService is a bean
    @Autowired
    private final MovieApiService movieApiService;

    //constructor required by Spring to user the movieApiService bean
    public HomeController(MovieApiService movieApiService) {
        this.movieApiService = movieApiService;
    }

    //mapping a get route to index page
    @GetMapping("/")
    public String index(Model model){
        Movies topMovies = movieApiService.findMoviesTopRated(); //fetching the movies from the api
        model.addAttribute("movies", topMovies); //declaring the list of movies as a variable to the template's engine
        return "index";
    }
}
