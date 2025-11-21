package com.mycast.Config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestClient;

//Indicating to spring that in this class the framework will find the Beans configuration
@Configuration
public class AppConfig {
    //defining a Bean for RestClient will be used to consume TMDB API
    @Bean
    public RestClient restClient (RestClient.Builder builder,
                                  @Value("${tmdb.api.url}") String url,
                                  @Value("${tmdb.api.token}") String key){
        return builder
                .baseUrl(url)
                .defaultHeader("Authorization", "Bearer "+key)
                .build();
    }
}
