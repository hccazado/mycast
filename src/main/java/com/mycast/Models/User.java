package com.mycast.Models;

import java.util.LinkedList;
import java.util.List;
import jakarta.validation.constraints.*;

public class User {
    private String firstName;
    private String lastName;

    //fields are being validate with jakarta.
    //This object is user also with user's registration form

    @Email(message = "Must be a valid email!")
    @NotBlank(message = "Email is required")
    private String email;
    @NotBlank(message = "User name is required")
    private String userName;

    @Size(min = 6, message = "Password must have at least 6 character")
    @NotBlank(message = "Password is required")
    private String password;
    private List<Integer> favoriteMovies = new LinkedList<>(); //a list of favorite movies Ids. Using a hashset to prevent duplicate items.

    public User() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Integer> getFavoriteMovies() {
        return favoriteMovies;
    }
}
