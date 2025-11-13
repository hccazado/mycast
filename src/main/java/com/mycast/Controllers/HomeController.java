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
    @Autowired
    private final MovieApiService movieApiService;

    public HomeController(MovieApiService movieApiService) {
        this.movieApiService = movieApiService;
    }

    @GetMapping("/")
    public String index(Model model){
        Movies topMovies = movieApiService.findMoviesTopRated();
        model.addAttribute("movies", topMovies);
        return "index";
    }
}
