package com.example.projetp42.db;

import android.content.Context;

import com.example.projetp42.R;
import com.example.projetp42.model.Book;
import com.example.projetp42.viewmodel.BookViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class BookRepository {

    private static final String BASE_URL = "";

    private BookViewModel bookViewModel;

    public BookRepository(BookViewModel bookViewModel) {
        this.bookViewModel = bookViewModel;
    }

    public void findBooks(Context context) throws IOException, JSONException {
        InputStream inputStream = context.getResources().openRawResource(R.raw.data);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder sb = new StringBuilder();
        String line;
        while((line = reader.readLine()) != null ) {
            sb.append( line );
            sb.append( '\n' );
        }
        JSONArray json = new JSONArray(sb.toString());
    }

    public void findBooks(BookData bookData) {

    }

    class BookData {
        private String title;
        private String author;
        private String tag;
    }

    private List<Book> JsonToBooks(JSONArray json) throws JSONException {
        ArrayList<Book> books = new ArrayList<>();
        for (int i = 0; i < json.length(); i++) {
            JSONObject bookJson = json.getJSONObject(i);
            String author = bookJson.getString("author");
            String title = bookJson.getString("title");
            JSONArray tagsJson = bookJson.getJSONArray("tags");

            List<String> tags = new ArrayList<>();
            for (int j = 0; j < tagsJson.length(); j++) {
                tags.add(tagsJson.getString(j));
            }

            books.add(new Book(author, title, tags));
        }

    }
}
