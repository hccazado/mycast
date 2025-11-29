package com.mycast.Services;

import com.google.cloud.firestore.Firestore;
import com.mycast.Models.MovieValue;
import com.mycast.Models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;

@Service
public class FavoriteService {
    private final Firestore firestore;
    private final UserService userService;
    private final MovieApiService movieApiService;

    @Autowired
    public FavoriteService(Firestore firestore, UserService userService, MovieApiService movieApiService) {
        this.firestore = firestore;
        this.userService = userService;
        this.movieApiService = movieApiService;
    }

    public List<MovieValue> getFavorites(String username) throws ExecutionException, InterruptedException{
        //getting list of favoriteMovies from the user document
        User user = userService.findByUserName(username);
        if (user == null || user.getFavoriteMovies().isEmpty()) {
            return null; // Return an empty list if user or favorites are null/empty
        }
        return fetchMovies(user.getFavoriteMovies());
    }

    private List<MovieValue> fetchMovies(List<Integer> movies){
        List<MovieValue> favoriteMovies = new ArrayList<>();

        for(int movieId: movies){
            MovieValue movie = movieApiService.findMovieById(movieId);
            favoriteMovies.add(movie);
        }
        return favoriteMovies;
    }
    //adding user's favorite movie
    public boolean addFavorite (String username, int id) throws ExecutionException, InterruptedException {
        //updating user's favorites list
        return userService.addFavoriteId(username, id);
    }

    //removing user's favorite movie
    public boolean removeFavorite(String username, int id) throws ExecutionException, InterruptedException{
        //updating user's favorites list
        return userService.removeFavoriteId(username, id);
    }
}
