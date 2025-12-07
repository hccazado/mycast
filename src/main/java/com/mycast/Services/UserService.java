package com.mycast.Services;

import com.google.api.core.ApiFuture;
import com.google.api.gax.rpc.ApiException;
import com.google.cloud.firestore.*;
import com.mycast.Models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
public class UserService {

    private final Firestore firestore;
    private final PasswordEncoder passwordEncoder;

    //Constructor.
    @Autowired
    public UserService(Firestore firestore, PasswordEncoder passwordEncoder){
        this.firestore = firestore;
        this.passwordEncoder = passwordEncoder;
    }

    public User newUser(User user) {
        try {
            // Defining refence to a blank document in collection "users"
            DocumentReference doc = firestore.collection("users").document();

            //serializing the user object
            ApiFuture<WriteResult> future = doc.set(user);

            // wait for Firestore to return the created document
            future.get();
            // return generated document ID
            return user;

        } catch (InterruptedException e) {
        // Handle interruption (e.g., thread was told to stop waiting)
        Thread.currentThread().interrupt(); // Restore interrupted status
        throw new RuntimeException("Firestore operation interrupted.", e);

        } catch (ExecutionException e) {
            // The operation failed. Check the cause.
            Throwable cause = e.getCause();

            if (cause instanceof ApiException) {
                ApiException apiException = (ApiException) cause;
                // Print the specific status code for detailed debugging
                System.err.println("Firestore API Failure: Status=" + apiException.getStatusCode().getCode().name());
                System.err.println("Details: " + apiException.getMessage());

                // You can rethrow a custom exception based on the status if needed
                if (apiException.getStatusCode().getCode().name().equals("PERMISSION_DENIED")) {
                    throw new SecurityException("Cannot save user. Check Firestore rules or service account permissions.", apiException);
                }
                // For general failures, throw a standard runtime exception
                throw new RuntimeException("Firestore operation failed for user " + user.getUserName(), apiException);

            } else {
                // Handle other execution exceptions (e.g., unexpected internal errors)
                throw new RuntimeException("Unexpected error during Firestore write.", e);
            }
        }
    }

    public User authenticate(String username, String rawPassword) throws ExecutionException, InterruptedException {
        // Firestore query to find a user by username
        var query = firestore.collection("users")
                .whereEqualTo("userName", username)
                .limit(1)
                .get();

        //querying the user
        var querySnapshot = query.get();
        if (querySnapshot.isEmpty()) {
            return null; // user not found
        }
        // instancing a new user object from found document
        User user = querySnapshot.getDocuments().get(0).toObject(User.class);

        //found document with the username, checking user password
        if (passwordEncoder.matches(rawPassword, user.getPassword())) {
            return user; // correct password
        }
        return null; // wrong password
    }

    //Auxiliary method to insert a new movie id to user's favorites list
    public boolean addFavoriteId(String username, int id) throws ExecutionException, InterruptedException {
        //fetching user's document ID
        String documentId = getDocumentId(username);

        if(documentId == null){
            return false;
        }

        try {
            // Use the actual document ID for the update
            firestore.collection("users").document(documentId)
                    .update("favoriteMovies", FieldValue.arrayUnion(id))
                    .get();
            return true;

        } catch (ExecutionException e) {
            // Unpack the exception to check the specific error
            Throwable cause = e.getCause();

            if (cause instanceof ApiException) {
                ApiException apiException = (ApiException) cause;
                System.err.println("🚨 Firestore API Failure (Add Favorite): Status=" + apiException.getStatusCode().getCode().name());
            } else {
                System.err.println("🚨 Unexpected error during Firestore write for user: " + e.getMessage());
            }

            // Return false on any write failure
            return false;

        } catch (InterruptedException e) {
            // Handle thread interruption
            Thread.currentThread().interrupt();
            System.err.println("❌ Firestore operation interrupted.");
            return false;
        }
    }

    //Auxiliary method to remove an id from user's favorites list
    public boolean removeFavoriteId(String username, int id) throws ExecutionException, InterruptedException{
        //fetching user's document ID
        String documentId = getDocumentId(username);

        if(documentId == null){
            return false;
        }

        try {
            // Using user's documentId for the update
            firestore.collection("users").document(documentId)
                    .update("favoriteMovies", FieldValue.arrayRemove(id))
                    .get();
            return true;

        } catch (ExecutionException e) {
            //getting the cause of the exception
            Throwable cause = e.getCause();

            if (cause instanceof ApiException) {
                ApiException apiException = (ApiException) cause;
                System.err.println("Firestore API Failure (Remove Favorite): Status=" + apiException.getStatusCode().getCode().name());
            } else {
                System.err.println("Unexpected error during Firestore write for user: " + e.getMessage());
            }
            // Return false on any write failure
            return false;

        } catch (InterruptedException e) {
            // Handle thread interruption
            Thread.currentThread().interrupt();
            System.err.println("Firestore operation interrupted.");
            return false;
        }
    }

    //Auxiliary method to fetch full user document by its username
    public User findByUserName(String username) throws ExecutionException, InterruptedException{
        ApiFuture<QuerySnapshot> future = firestore.collection("users")
                .whereEqualTo("userName", username)
                .limit(1)
                .get();

        QuerySnapshot querySnapshot = future.get();

        if (querySnapshot.isEmpty()) {
            return null;
        }

        // Getting the first document if more than one
        DocumentSnapshot document = querySnapshot.getDocuments().get(0);

        // Converting the user document to a User object
        User user = document.toObject(User.class);
        return user;
    }

    //returns the document id from the user
    public String getDocumentId(String username) throws ExecutionException, InterruptedException{
        ApiFuture<QuerySnapshot> future = firestore.collection("users")
                .whereEqualTo("userName", username)
                .limit(1)
                .get();

        QuerySnapshot querySnapshot = future.get();

        if (querySnapshot.isEmpty()) {
            return null; // User not found
        }
        // Get the first document and return its ID
        return querySnapshot.getDocuments().get(0).getId();
    }
}
