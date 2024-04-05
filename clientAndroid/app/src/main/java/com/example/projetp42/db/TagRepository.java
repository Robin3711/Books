package com.example.projetp42.db;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.projetp42.model.Tag;
import com.example.projetp42.viewmodel.TagsViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TagRepository {

    private static final String BASE_URL = "http://10.0.2.2:3000/";

    public void getAllTags(Context context, TagsViewModel tagsViewModel) {
        String url = BASE_URL + "tags";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, response -> {
            ArrayList<Tag> tags = new ArrayList<>();
            for (int i = 0; i < response.length(); i++) {
                try {
                    JSONObject jsonObject = response.getJSONObject(i);
                    Tag tag = new Tag(jsonObject.getInt("id"), jsonObject.getString("name"),null);
                    tags.add(tag);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            tagsViewModel.setTags(tags);
        }, error -> Log.e("Volley", "Error: " + error.getMessage()));

        VolleyRequestQueue.getInstance(context).add(jsonArrayRequest);
    }
}
