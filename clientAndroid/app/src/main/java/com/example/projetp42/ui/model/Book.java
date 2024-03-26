package com.example.projetp42.ui.model;

import com.example.projetp42.R;

import java.util.List;

public class Book {
    public int id;
    public Author author;
    public int publication_year;
    public String title;
    public List<Tag> tags;
    public List<Comment> comments;
    public List<Rating> ratings;
    public Book(int id, Author author, int publication_year, String title, List<Tag> tags, List<Comment> comments, List<Rating> ratings){
        this.id = id;
        this.author = author;
        this.publication_year = publication_year;
        this.title = title;
        this.tags = tags;
        this.comments = comments;
        this.ratings = ratings;
    }

    public int getId() {
        return id;
    }

    public Author getAuthor() {
        return author;
    }

    public int getPublication_year() {
        return publication_year;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public List<Rating> getRatings() {
        return ratings;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public String getTitle() {
        return title;
    }
}
