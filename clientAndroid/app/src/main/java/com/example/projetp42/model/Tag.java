package com.example.projetp42.model;

import java.util.List;

public class Tag {
    public int id;
    public String name;
    public List<Book> books;
    public Tag(int id,String name, List<Book> books){
        this.id = id;
        this.name = name;
        this.books = books;
    }
    
}
