package com.example.projetp42.ui.model;

import java.util.List;

public class User {
    public int id;
    public String email;
    public String username;
    public String password;
    public List<Comment> comments;
    public List<Rating> ratings;
    public User(int id, String email, String username, String password, List<Comment> comments, List<Rating> ratings){
        this.id = id;
        this.email = email;
        this.username = username;
        this.password = password;
        this.comments = comments;
        this.ratings = ratings;
    }
}
