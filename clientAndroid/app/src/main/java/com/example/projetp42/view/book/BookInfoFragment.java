package com.example.projetp42.view.book;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.projetp42.R;
import com.example.projetp42.db.BookRepository;
import com.example.projetp42.viewmodel.BookViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class BookInfoFragment extends Fragment {
    TextView title;
    TextView author;
    TextView publication_year;
    private int id;
    private BookViewModel bookViewModel;
    public BookInfoFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        title = getActivity().findViewById(R.id.info_title);
        author = getActivity().findViewById(R.id.info_author);
        publication_year = getActivity().findViewById(R.id.info_publication_year);
        SharedPreferences prefs = getActivity().getPreferences(Context.MODE_PRIVATE);
        id = prefs.getInt("id", -1);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        BookRepository bookRepository = new BookRepository();
        bookViewModel = new ViewModelProvider(this).get(BookViewModel.class);
        try {
            BookRepository.BookData bookData = new BookRepository.BookData(null,null,null);
            bookRepository.findBookById(this.getContext(),bookViewModel,id);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        bookViewModel.getBook().observe(getViewLifecycleOwner(), book -> {
            title.setText(book.getTitle());
            author.setText(book.getAuthorID());
            publication_year.setText(book.getPublication_year());
        });
        return inflater.inflate(R.layout.fragment_book_info, container, false);
    }
}