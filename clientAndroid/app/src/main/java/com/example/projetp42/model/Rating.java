package com.example.projetp42.model;

public class Rating {
    public int id;
    public int value;
    public String author;
    public int bookId;
    public Rating(int id, int value, String author, int bookId){
        this.id = id;
        this.value = value;
        this.author = author;
        this.bookId = bookId;
    }
}
