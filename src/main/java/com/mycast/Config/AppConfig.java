package com.mycast.Config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestClient;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

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

    @Bean
    public FirebaseApp firebaseApp(@Value("${google.cloud.firestore.credentials.location}") Resource serviceAccountResource ) throws IOException {

        //reading data from
        InputStream serviceAccount = serviceAccountResource.getInputStream();

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

        // Avoid duplicate initialization
        List<FirebaseApp> apps = FirebaseApp.getApps();
        if (apps == null || apps.isEmpty() || apps.stream().noneMatch(app -> app.getName().equals(FirebaseApp.DEFAULT_APP_NAME))) {
            return FirebaseApp.initializeApp(options);
        }

        return FirebaseApp.getInstance();
    }

    @Bean
    public Firestore firestore(FirebaseApp firebaseApp) {
        // Uses the correctly initialized FirebaseApp
        return FirestoreClient.getFirestore(firebaseApp);
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder(10);
    }
}
