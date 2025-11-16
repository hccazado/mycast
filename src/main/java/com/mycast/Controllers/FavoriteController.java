package com.mycast.Controllers;

import com.mycast.Models.MovieValue;
import com.mycast.Models.User;
import com.mycast.Services.MovieApiService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/favorites")
public class FavoriteController {

    @Autowired
    private final MovieApiService movieApiService;

    public FavoriteController(MovieApiService movieApiService) {
        this.movieApiService = movieApiService;
    }

    @GetMapping({"", "/"})
    public String displayFavorites(Model model, RedirectAttributes redirectAttributes, HttpSession session){
        User user = (User) session.getAttribute("user");

        if(user == null){
            redirectAttributes.addFlashAttribute("error", "You must log in first!");
            return "redirect:/login";
        }

        model.addAttribute("movies", user.getFavorites());
        return "favorites";
    }

    @PostMapping("/add")
    public String addFavorite(@RequestParam int id, RedirectAttributes redirectAttributes, HttpSession session) {
        User user = (User) session.getAttribute("user");

        if (user == null){
            redirectAttributes.addFlashAttribute("error","You need to login first!");
            return "redirect:/login";
        }
        MovieValue movie = movieApiService.findMovieById(id); // fetch movie details
        boolean added = user.addFavorite(movie); //adding movie to favorites
        if(added){
            redirectAttributes.addFlashAttribute("message", "Movie added to favorites!");
            return "redirect:/favorites"; // redirect to Favorites page
        }
        else{
            redirectAttributes.addFlashAttribute("message", "Movie already in favorites!");
            return "redirect:/";
        }
    }

    @PostMapping("/remove")
    public String removeFavorite(@RequestParam int id, RedirectAttributes redirectAttributes, HttpSession session){
        User user = (User) session.getAttribute("user");

        if(user == null){
            redirectAttributes.addFlashAttribute("error", "You must log in first!");
            return "redirect:/login";
        }
        boolean removed = user.removeFavotire(id);

        if(removed){
            redirectAttributes.addFlashAttribute("message", "Movie removed from favorites!");
            return "redirect:/favorites";
        }
        else{
            redirectAttributes.addFlashAttribute("error", "Something went wrong!");
            return "redirect:/favorites";
        }
    }
}
