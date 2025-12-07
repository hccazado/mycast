package com.mycast.Services;

import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
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

    //return user's favorites list.
    public List<MovieValue> getFavorites(String username) throws ExecutionException, InterruptedException{
        //getting list of favoriteMovies from the user document
        User user = userService.findByUserName(username);
        if (user == null || user.getFavoriteMovies().isEmpty()) {
            return null; // Return an empty list if user or favorites is null/empty
        }
        //returning user's favorites list
        return fetchMovies(user.getFavoriteMovies());
    }

    //fetches movie data from cache or tmdb API (
    private List<MovieValue> fetchMovies(List<Integer> movies) throws ExecutionException, InterruptedException {
        List<MovieValue> favoriteMovies = new ArrayList<>();

        //iterating favoritesList to fetch data
        for(int movieId: movies){
            //trying to fetch from movies cache
            DocumentReference docRef = firestore.collection("movies").document(String.valueOf(movieId));
            DocumentSnapshot snapshot = docRef.get().get();
            MovieValue movie;
            if(snapshot.exists()){
                movie = snapshot.toObject(MovieValue.class);
            }
            else {
                //fetching movie data from TMDB
                movie = movieApiService.findMovieById(movieId);
            }
            favoriteMovies.add(movie);
        }
        return favoriteMovies;
    }

    //adding user's favorite movie
    public boolean addFavorite (String username, int id) throws ExecutionException, InterruptedException {
        //Adding movie to cache collection
        //creating a reference for the movie documento and trying to get it from the cache
        DocumentReference docRef = firestore.collection("movies").document(String.valueOf(id));
        DocumentSnapshot snapshot = docRef.get().get();
        if(!snapshot.exists()){
            //fetching movie data from tmdb
            MovieValue movie = movieApiService.findMovieById(id);
            if (movie != null){
                //updating the new movie information into the document
                docRef.set(movie).get();
            }
        }
        //updating user's favorites list
        return userService.addFavoriteId(username, id);
    }

    //removing user's favorite movie
    public boolean removeFavorite(String username, int id) throws ExecutionException, InterruptedException{
        //updating user's favorites list
        return userService.removeFavoriteId(username, id);
    }
}
