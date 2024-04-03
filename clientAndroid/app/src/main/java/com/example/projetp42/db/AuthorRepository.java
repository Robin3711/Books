package com.example.projetp42.db;

import android.content.Context;

import com.android.volley.toolbox.JsonArrayRequest;
import com.example.projetp42.model.Author;
import com.example.projetp42.viewmodel.author.AuthorsViewModel;

import org.json.JSONArray;
import org.json.JSONException;

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
    }

    public ArrayList<Author> jsonToAuthors(JSONArray json) {
        return null;
    }
}