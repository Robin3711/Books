package com.example.projetp42.model;

import java.util.ArrayList;
import java.util.List;

public class Book {
    public int id;
    public int authorID;
    public int publication_year;
    public String title;
    public List<Tag> tags;
    public List<Comment> comments;
    public List<Rating> ratings;

    public String author;
    public Book(int id,int authorID, String author, int publication_year, String title, List<Tag> tags, List<Comment> comments, List<Rating> ratings){
        this.id = id;
        this.author = author;
        this.authorID = authorID;
        this.publication_year = publication_year;
        this.title = title;
        this.tags = tags;
        this.comments = comments;
        this.ratings = ratings;
    }

    public Book(int id, String title)
    {
        this.id = id;
        this.title = title;
    }

    public Book(String title, String author, List<String> tags){
        this.title = title;
        this.authorID = 0;
        this.tags = new ArrayList<Tag>();
        for (String tag : tags) {
            this.tags.add(new Tag(0,tag,null));
        }
    }

    public int getId() {
        return id;
    }

    public int getAuthorID() {
        return authorID;
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

