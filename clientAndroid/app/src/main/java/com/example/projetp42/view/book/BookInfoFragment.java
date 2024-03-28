package com.example.projetp42.view.book;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.projetp42.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class BookInfoFragment extends Fragment {

    public BookInfoFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView title = getActivity().findViewById(R.id.info_title);
        TextView author = getActivity().findViewById(R.id.info_author);
        FloatingActionButton fab = getActivity().findViewById(R.id.add_book_button);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_book_info, container, false);
    }
}