package com.mycast.Services;

import com.mycast.Models.MovieValue;
import com.mycast.Models.Movies;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class MovieApiService {
    //class responsible for acting as a Service to query TMDB API
    private final RestClient restClient;

    //initializing the class with a new instance of restClient
    public MovieApiService(RestClient restClient) {
        this.restClient = restClient;
    }

    //fetches movies by genre
    public Movies findMoviesByGenre(String genre){
        return restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/discover/movie")
                        .queryParam("with-genres", genre)
                        .build())
                .retrieve()
                .body(Movies.class);
    }

    //fetches a movie by id
    public MovieValue findMovieById(int id){
        return restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/movie/{id}")
                        .build(id))
                .retrieve()
                .body(MovieValue.class);
    }

    //fetches the most popular movies
    public Movies findMoviesPopular(){
        return restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/movie/popular")
                        .build())
                .retrieve()
                .body(Movies.class);
    }

    //fetches the top-rated movies
    public Movies findMoviesTopRated(){
        return restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/movie/top_rated")
                        .build())
                .retrieve()
                .body(Movies.class);
    }
}
