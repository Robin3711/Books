package com.example.projetp42.view.book;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.projetp42.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class BookInfoFragment extends Fragment {
    TextView title;
    TextView author;
    FloatingActionButton fab;
    private int id;

    public BookInfoFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        title = getActivity().findViewById(R.id.info_title);
        author = getActivity().findViewById(R.id.info_author);
        fab = getActivity().findViewById(R.id.add_book_button);
        SharedPreferences prefs = getActivity().getPreferences(Context.MODE_PRIVATE);
        id = prefs.getInt("id", -1);
        
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_book_info, container, false);
    }
}