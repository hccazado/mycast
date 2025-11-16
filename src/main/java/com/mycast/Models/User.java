package com.mycast.Models;

import java.util.HashSet;
import java.util.Set;

public class User {
    private String userName;
    private String password;
    private Set<MovieValue> favotires = new HashSet<>();

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
        boolean removed = favotires.removeIf(movie -> movie.id() == id);
        return removed;
    }
}
