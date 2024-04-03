package com.example.projetp42.model;

import java.util.List;

public class Author {
    public int id;
    public String firstname;
    public String lastname;
    public List<Book> books;    // books ??
    public Author(int id, String firstname, String lastname, List<Book> books){
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.books = books;
    }

    public int getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }
}
