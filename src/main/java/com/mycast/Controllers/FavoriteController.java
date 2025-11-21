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
@RequestMapping("/favorites") //mapping a get route to favorite's page
public class FavoriteController {
    //indicating to Spring that movieApiService is a bean
    @Autowired
    private final MovieApiService movieApiService;

    //constructor required by Spring to user the movieApiService bean
    public FavoriteController(MovieApiService movieApiService) {
        this.movieApiService = movieApiService;
    }

    //mapping Get route for displaying the favorites page
    @GetMapping({"", "/"})
    public String displayFavorites(Model model, RedirectAttributes redirectAttributes, HttpSession session){
        User user = (User) session.getAttribute("user"); //fetching the user from session

        //redirecting the user to the login page if there's no active session
        if(user == null){
            redirectAttributes.addFlashAttribute("error", "You must log in first!");
            return "redirect:/login";
        }
        //returning user's favorite movies to the template
        model.addAttribute("movies", user.getFavorites());
        return "favorites";
    }

    //mapping Post route for adding a movie to user's favorites
    @PostMapping("/add")
    public String addFavorite(@RequestParam int id, RedirectAttributes redirectAttributes, HttpSession session) {
        //checks if the user has an active session and call the responsible method from the user's model to
        //add the movie to its favorite list.
        User user = (User) session.getAttribute("user");

        //there is no user session. redirecting the user to the login page.
        if (user == null){
            redirectAttributes.addFlashAttribute("error","You need to login first!");
            return "redirect:/login";
        }
        MovieValue movie = movieApiService.findMovieById(id); // fetch movie details
        boolean added = user.addFavorite(movie); //adding movie to favorites

        //redirecting the user to the favorite's page with appropriated message
        if(added){
            redirectAttributes.addFlashAttribute("message", "Movie added to favorites!");
            return "redirect:/favorites";
        }
        else{
            redirectAttributes.addFlashAttribute("message", "Movie already in favorites!");
            return "redirect:/";
        }
    }

    //mapping Post route for removing a movie from favorites
    @PostMapping("/remove")
    public String removeFavorite(@RequestParam int id, RedirectAttributes redirectAttributes, HttpSession session){
        //checks if the user has an active session, and calls the responsible method from the user's model to
        //remove the movie from its favorite list.
        User user = (User) session.getAttribute("user");

        //there is no user session. redirecting the user to the login page.
        if(user == null){
            redirectAttributes.addFlashAttribute("error", "You must log in first!");
            return "redirect:/login";
        }

        boolean removed = user.removeFavotire(id);//removing movie from favorites

        //redirecting the user to the favorite's page with appropriated message
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
