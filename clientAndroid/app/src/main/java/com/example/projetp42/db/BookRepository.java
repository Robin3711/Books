package com.example.projetp42.db;

import android.content.Context;
import android.util.Log;

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

    public void findBooks(Context context, BooksViewModel booksViewModel) {
        String url = BASE_URL + "books";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url,
                response -> {
                    try {
                        String array = response.toString(); 
                        JSONArray jsonArray = new JSONArray(array);
                        booksViewModel.loadBook(JsonToBooks(jsonArray));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    Log.e("findBooks", "Error fetching books", error);

                    if (error.networkResponse != null && error.networkResponse.data != null) {
                        try {
                            String errorResponse = new String(error.networkResponse.data, "UTF-8");
                            Log.e("findBooks", "Error response: " + errorResponse);
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                    ArrayList<Book> books = new ArrayList<>();
                    books.add(new Book("Error", "Error", new ArrayList<>()));
                    booksViewModel.loadBook(books);
                });

        VolleyRequestQueue.getInstance(context).add(jsonObjectRequest);
    }


    public void findBooks(BookData bookData) {

    }

    class BookData {
        private String title;
        private String author;
        private String tag;
    }

    private ArrayList<Book> JsonToBooks(JSONArray json) throws JSONException {
        ArrayList<Book> books = new ArrayList<>();
       // json = json.getJSONArray(0).getJSONArray(0);
        /*for (int i = 0; i < json.length(); i++) {
            JSONObject bookJson = json.getJSONObject(i);
            int id = bookJson.getInt("id");
            String title = bookJson.getString("title");

            books.add(new Book(id, title));
        }*/
        return books;
    }


}
