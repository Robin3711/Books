package com.example.projetp42.model;

import java.util.Date;

public class Comment{
    public int id;
    public Book book;
    public String content;
    public Date createdAt;
    public Date updatedAt;
    public String author;   // author du commentaire, pas du livre
    public Comment(int id,String author, Book book, String content,Date createdAt, Date updatedAt){
        this.id = id;

        this.book = book;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.author = author;
    }

    public int getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public Book getBook() {
        return book;
    }

    public String getContent() {
        return content;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }
}
