package com.example.projetp42.db;

import android.content.Context;
import android.util.Log;

import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.projetp42.model.Book;
import com.example.projetp42.viewmodel.BooksViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class BookRepository {

    private static final String BASE_URL = "http://192.168.122.1:3000/";

    public BookRepository() {

    }

    public void findBooks(Context context, BooksViewModel booksViewModel,BookData bookData) {
        String url = BASE_URL + "books";

        if(bookData != null){
            url += "?";
            if(bookData.title != null){
                url += "title=" + bookData.title;
            }
            if(bookData.author != null){
                if (bookData.title != null){
                    url += "&";
                }
                url += "author=" + bookData.author;
            }
        }

        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(url,
                response -> {
                    try {
                        ArrayList<Book> books = JsonToBooks(response);
                        if(books.size() == 0){
                            books.add(new Book("No books found for those filters", "Error", new ArrayList<>()));
                        }
                        booksViewModel.loadBook(books);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    ArrayList<Book> books = new ArrayList<>();
                    books.add(new Book(error.getMessage(), "Error", new ArrayList<>()));
                    booksViewModel.loadBook(books);
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


}
