package com.mycast.Controllers;

import com.mycast.Models.MovieValue;
import com.mycast.Models.Movies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.mycast.Services.MovieApiService;

@RestController
@RequestMapping("/movies")
public class MoviesController {
    @Autowired
    private final MovieApiService movieApiService;

    public MoviesController(MovieApiService service){
        this.movieApiService = service;
    }

    @GetMapping("/")
    public Movies findMoviesByGenre(@RequestParam String genre){
        return movieApiService.findMoviesByGenre(genre);
    }

    @GetMapping("/{id}")
    public MovieValue findMovieById(@PathVariable int id){
        return movieApiService.findMovieById(id);
    }
}
