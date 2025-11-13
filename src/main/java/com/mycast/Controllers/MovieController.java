package com.mycast.Controllers;

import com.mycast.Models.MovieValue;
import com.mycast.Models.Movies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.mycast.Services.MovieApiService;


@Controller
@RequestMapping("/movie")
public class MovieController {
    @Autowired
    private final MovieApiService movieApiService;

    public MovieController(MovieApiService service){
        this.movieApiService = service;
    }

    @GetMapping("/{id}")
    public String movieDetails(@PathVariable int id, Model model){
        MovieValue movie = movieApiService.findMovieById(id);
        model.addAttribute("movie", movie);
        return "movieDetails";
    }
}
