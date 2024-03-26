package com.example.projetp42.ui.model;

import java.util.Date;

public class Comment{
    public int id;
    public User user;
    public Book book;
    public String content;
    public Date createdAt;
    public Date updatedAt;
    public Comment(int id, User user, Book book, String content,Date createdAt, Date updatedAt){
        this.id = id;
        this.user = user;
        this.book = book;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getId() {
        return id;
    }

    public User getUser() {
        return user;
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
