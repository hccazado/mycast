package com.mycast.Services;

import com.mycast.Models.MovieValue;
import com.mycast.Models.Movies;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class MovieApiService {

    private final RestClient restClient;

    public MovieApiService(RestClient restClient) {
        this.restClient = restClient;
    }

    public Movies findMoviesByGenre(String genre){
        return restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/discover/movie")
                        .queryParam("with-genres", genre)
                        .build())
                .retrieve()
                .body(Movies.class);
    }

    public MovieValue findMovieById(int id){
        return restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/movie/{id}")
                        .build(id))
                .retrieve()
                .body(MovieValue.class);
    }
}
