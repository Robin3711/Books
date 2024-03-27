package com.example.projetp42.ui.model;

public class Rating {
    public int id;
    public int value;
    public User user;
    public Book book;
    public Rating(int id, int value, User user, Book book){
        this.id = id;
        this.value = value;
        this.user = user;
        this.book = book;
    }
}
