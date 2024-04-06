package com.example.projetp42.db;

import android.content.Context;
import android.util.Log;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.projetp42.model.Book;
import com.example.projetp42.model.Comment;
import com.example.projetp42.model.Rating;
import com.example.projetp42.viewmodel.RatingViewModel;
import com.example.projetp42.viewmodel.book.BooksViewModel;
import com.example.projetp42.viewmodel.book.BookViewModel;
import com.example.projetp42.model.Tag;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;

public class BookRepository {

    private static final String BASE_URL = "http://10.0.2.2:3000/";

    public BookRepository() {

    }

    public void findBooks(Context context, BooksViewModel booksViewModel,BookData bookData, String order) {
        String url = BASE_URL + "books";

        boolean firstParam = true;

        if(bookData != null){
            url += "?";
            if(bookData.title != null){
                url += "title=" + bookData.title;
                firstParam = false;
            }
            if(bookData.author != null){
                if (!firstParam){
                    url += "&";
                }
                firstParam = false;
                url += "author=" + bookData.author;
            }
            if (bookData.tag != null) {
                if(!firstParam) {
                    url += "&";
                }
                firstParam = false;
                url += "tag=" + bookData.tag;
            }
        }
        if(order != null){
            if(!firstParam){
                url += "&";
            }
                url += "order=" + order;
        }

        Log.d("URL", url);

        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(url,
                response -> {
                    try {
                        ArrayList<Book> books = JsonToBooks(response);
                        if(books.size() == 0){
                            books.add(new Book("No books found for those filters", "Error", new ArrayList<>()));
                        }
                        booksViewModel.loadBooks(books);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    ArrayList<Book> books = new ArrayList<>();
                    books.add(new Book(error.getMessage(), "Error", new ArrayList<>()));
                    booksViewModel.loadBooks(books);
                });

        VolleyRequestQueue.getInstance(context).add(jsonObjectRequest);
    }
    public void findBooksByAuthor(Context context, BooksViewModel booksViewModel,int id){
        String url = BASE_URL + "authors/" + id+"/books";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url,
                response -> {
                    try {
                        ArrayList<Book> books = JsonToBooks(response);
                        booksViewModel.loadBooks(books);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    ArrayList<Book> books = new ArrayList<>();
                    books.add(new Book(error.getMessage(), "Error", new ArrayList<>()));
                    booksViewModel.loadBooks(books);
                });

        VolleyRequestQueue.getInstance(context).add(jsonArrayRequest);
    }


    private ArrayList<Book> JsonToBooks(JSONArray json) throws JSONException {
        ArrayList<Book> books = new ArrayList<>();
        for (int i = 0; i < json.length(); i++) {
            JSONObject bookJson = json.getJSONObject(i);
            int id = bookJson.getInt("id");
            String title = bookJson.getString("title");

            books.add(new Book(id, title));
        }
        return books;
    }

    public void deleteBook(Context context, int id) {
        String url = BASE_URL + "books/" + id;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.DELETE, url, null,
                response -> {
                    Log.d("DELETE", "Book deleted");
                },
                error -> {
                    Log.d("DELETE", "Error deleting book: " + error.getMessage());
                });

        VolleyRequestQueue.getInstance(context).add(jsonObjectRequest);
    }

    public static class BookData {
        private String title;
        private String author;
        private String tag;

        public BookData(String title, String author, String tag) {
            this.title = title;
            this.author = author;
            this.tag = tag;
        }
    }

    public void findBookById(Context context, BookViewModel bookViewModel, int id) {
        String url = BASE_URL + "books/" + id+"?include=all";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url,
                response -> {
                    try {
                        Book book = JsonToBook(response);
                        bookViewModel.loadBook(book);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    Book book = new Book(error.getMessage(), "Error", new ArrayList<>());
                    bookViewModel.loadBook(book);
                });

        VolleyRequestQueue.getInstance(context).add(jsonObjectRequest);
    }

    private Book JsonToBook(JSONObject json) throws JSONException {
        return new Book(json.getInt("id"),
                json.getInt("authorId"),
                json.getJSONObject("author").getString("firstname") + " " + json.getJSONObject("author").getString("lastname"),
                json.getInt("publication_year"),
                json.getString("title"),
                JsonToTags(json.getJSONArray("tags")),
                JsonToComments(json.getJSONArray("comments")),
                //new ArrayList<>(),
                new ArrayList<>());
    }

    public void addBook(Context context, Book book, AddBookCallback callback) {
        String url = BASE_URL + "authors/" + book.authorID + "/books";

        JSONObject json = new JSONObject();
        try {
            json.put("title", book.title);
            json.put("publication_year", book.publication_year);
        } catch (JSONException e) {
            e.printStackTrace();
            if (callback != null) {
                callback.onFailure("Error creating JSON object");
            }
            return;
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, json,
                response -> {
                    try {
                        int id = response.getInt("id");
                        book.id = id;
                        if (callback != null) {
                            callback.onSuccess(id);
                        }

                        // Add tags after successfully adding the book
                        if (book.tags != null) {
                            for (Tag tag : book.tags) {
                                String urlTag = BASE_URL + "books/" + book.id + "/tags/" + tag.id;
                                StringRequest stringRequest = new StringRequest(Request.Method.POST, urlTag,
                                        tagResponse -> {
                                            Log.d("TAG", "Tag added to book");
                                        },
                                        error -> {
                                            Log.d("TAG", "Error adding tag to book: " + error.getMessage());
                                        });

                                VolleyRequestQueue.getInstance(context).add(stringRequest);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        if (callback != null) {
                            callback.onFailure("Error parsing server response");
                        }
                    }
                },
                error -> {
                    if (callback != null) {
                        callback.onFailure("Error adding book: " + error.getMessage());
                    }
                });

        VolleyRequestQueue.getInstance(context).add(jsonObjectRequest);
    }

public void addComment(Context context, Comment comment) {
        String url = BASE_URL + "books/" + comment.bookId + "/comments";

        JSONObject json = new JSONObject();
        try {
            json.put("text", comment.content);
            json.put("author", comment.author);
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, json,
                response -> {
                    Log.d("COMMENT", "Comment added");
                },
                error -> {
                    Log.d("COMMENT", "Error adding comment: " + error.getMessage());
                });

        VolleyRequestQueue.getInstance(context).add(jsonObjectRequest);
    }

    public void addRating(Context context, Rating rating) {
        String url = BASE_URL + "books/" + rating.bookId + "/ratings";

        JSONObject json = new JSONObject();
        try {
            json.put("rating", rating.value);
            json.put("author", rating.author);
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, json,
                response -> {
                    Log.d("RATING", "Rating added");
                },
                error -> {
                    Log.d("RATING", "Error adding rating: " + error.getMessage());
                });

        VolleyRequestQueue.getInstance(context).add(jsonObjectRequest);
    }


    public interface AddBookCallback {
        void onSuccess(int bookId);
        void onFailure(String errorMessage);
    }


    public void getAvgRatingOfBook(int bookId, RatingViewModel ratingViewModel) throws JSONException {
        String url = BASE_URL + "books/" + bookId + "/ratings/avg";
        StringRequest jsonObjectRequest = new StringRequest(url,
                response -> {
                    try {
                        DecimalFormat df = new DecimalFormat("#.##");
                        Float rating = Float.parseFloat(response);
                        if (rating == -1.0f) {
                            ratingViewModel.loadRating(-1.0f);
                        } else {
                            ratingViewModel.loadRating(Float.parseFloat(df.format(rating)));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    ratingViewModel.loadRating(-1.0f);
                });

        VolleyRequestQueue.getInstance(null).add(jsonObjectRequest);
    }



    private ArrayList<Tag> JsonToTags(JSONArray json) throws JSONException {
        ArrayList<Tag> tags = new ArrayList<>();
        for (int i = 0; i < json.length(); i++) {
            JSONObject tagJson = json.getJSONObject(i);
            int id = tagJson.getInt("id");
            String name = tagJson.getString("name");

            tags.add(new Tag(id, name, null));
        }
        return tags;
    }

    private ArrayList<Comment> JsonToComments(JSONArray json) throws JSONException {
        ArrayList<Comment> comments = new ArrayList<>();
        for (int i = 0; i < json.length(); i++) {
            JSONObject commentJson = json.getJSONObject(i);
            int id = commentJson.getInt("id");
            String content = commentJson.getString("text");
            //int bookId = commentJson.getInt("bookId");
            String author = commentJson.getString("author");
            //Date createdAt = new Date(commentJson.getLong("createdAt"));
            //Date updatedAt = new Date(commentJson.getLong("updatedAt"));

            comments.add(new Comment(id,author,0, content, null, null));
            Log.d("COMMENT", "Comment added: " + content);
        }
        return comments;
    }

}
