package com.example.projetp42.db;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.projetp42.model.Author;
import com.example.projetp42.viewmodel.author.AuthorsViewModel;

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

    public ArrayList<Author> jsonToAuthors( JSONArray json) {
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

    public void addAuthor(Context context, Author author, AddAuthorCallback callback) {
        String url = BASE_URL + "authors";

        JSONObject json = new JSONObject();
        try {
            json.put("firstname", author.firstname);
            json.put("lastname", author.lastname);
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
                        int authorId = response.getInt("id");
                        if (callback != null) {
                            callback.onSuccess(authorId);
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
                        callback.onFailure("Error adding author: " + error.getMessage());
                    }
                });

        VolleyRequestQueue.getInstance(context).add(jsonObjectRequest);
    }

    public interface AddAuthorCallback {
        void onSuccess(int authorId);
        void onFailure(String errorMessage);
    }

}