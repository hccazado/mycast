package com.mycast.Models;

import java.util.HashSet;
import java.util.Set;

public class User {
    private String userName;
    private String password;
    private Set<MovieValue> favotires = new HashSet<>(); //a list of favorite movies. Actually as a hashset to prevent duplicate items.

    public User() {
    }

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword(){
        return this.password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public Set<MovieValue> getFavorites(){
        return this.favotires;
    }

    public boolean addFavorite(MovieValue movie) {
        return favotires.add(movie);
    }

    public boolean removeFavotire(int id) {
        //iterates through the user's favorite movies list and remove the movie with same id
        //the list doesn't have duplicate items since it's a HashSet
        boolean removed = favotires.removeIf(movie -> movie.id() == id);
        return removed; //returns operation status
    }
}
