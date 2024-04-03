package com.example.projetp42.db;

import android.content.Context;

import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.projetp42.model.Author;
import com.example.projetp42.model.Book;
import com.example.projetp42.viewmodel.author.AuthorViewModel;
import com.example.projetp42.viewmodel.author.AuthorsViewModel;
import com.example.projetp42.viewmodel.book.BookViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AuthorRepository {
    private static final String BASE_URL = "http://10.0.2.2:3000/";

    public AuthorRepository() {
    }

    public void findAuthors(Context context, AuthorsViewModel authorsViewModel) {
        String url = BASE_URL + "authors";

        JsonArrayRequest request = new JsonArrayRequest(url,
                response -> {
                    ArrayList<Author> authors = jsonToAuthors(response);
                    authorsViewModel.loadAuthors(authors);
                },
                error -> {
                    ArrayList<Author> authors = new ArrayList<Author>();
                    authors.add(new Author(0,"error", error.getMessage(),null));
                    authorsViewModel.loadAuthors(authors);
        });
        VolleyRequestQueue.getInstance(context).add(request);
    }

    public ArrayList<Author> jsonToAuthors(JSONArray json) {
        ArrayList<Author> authors = new ArrayList<>();
        try {
            for (int i = 0; i < json.length(); i++) {
                Author author = new Author(0, null, null, null);
                author.setId(json.getJSONObject(i).getInt("id"));
                author.setFirstname(json.getJSONObject(i).getString("firstname"));
                author.setLastname(json.getJSONObject(i).getString("lastname"));
                authors.add(author);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return authors;
    }
    public void findAuthorById(Context context, AuthorViewModel authorViewModel, int id) {
        String url = BASE_URL + "books/" + id+"?include=author";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url,
                response -> {
                    try {
                        Author author = jsonToAuthor(response);
                        authorViewModel.loadAuthor(author);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    Author author = new Author(-1, error.getMessage(), "Error");
                    authorViewModel.loadAuthor(author);
                });

        VolleyRequestQueue.getInstance(context).add(jsonObjectRequest);
    }
    private Author jsonToAuthor(JSONObject json) throws JSONException {
        return new Author(json.getInt("id"),
                json.getString("firstname"),
                json.getString("lastname"));
    }
}