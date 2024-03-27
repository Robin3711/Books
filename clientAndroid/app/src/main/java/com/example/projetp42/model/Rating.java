package com.example.projetp42.model;

public class Rating {
    public int id;
    public int value;
    public String author;
    public Book book;
    public Rating(int id, int value, String author, Book book){
        this.id = id;
        this.value = value;
        this.author = author;
        this.book = book;
    }
}
