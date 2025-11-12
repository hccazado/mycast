package com.mycast.controllers;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedList;
import java.util.List;

@RestController
public class Movies {

    public void getMovies(RestTemplate restTemplate) throws Exception{
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-API-Key", "your_api_key_here");
        List<Movies> movies = new LinkedList<>();
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                "\"https://api.themoviedb.org/3/find/id=934433\"",
                HttpMethod.GET,
                entity,
                String.class
        );

        System.out.println(response);

    }
}
