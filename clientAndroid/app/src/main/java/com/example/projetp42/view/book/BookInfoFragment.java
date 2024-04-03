package com.example.projetp42.view.book;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.projetp42.R;
import com.example.projetp42.db.BookRepository;
import com.example.projetp42.viewmodel.book.BookViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class BookInfoFragment extends Fragment {
    TextView title;
    TextView author;
    TextView publication_year;
    FloatingActionButton fab;
    private int id;
    private BookViewModel bookViewModel;

    public BookInfoFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //fab = getActivity().findViewById(R.id.add_book_button);
        SharedPreferences prefs = getActivity().getPreferences(Context.MODE_PRIVATE);
        id = prefs.getInt("id", -1);

    }

    @SuppressLint("SetTextI18n")
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_book_info, container, false);
        title = root.findViewById(R.id.info_title);
        author = root.findViewById(R.id.info_author);
        publication_year = root.findViewById(R.id.info_publication_year);

        bookViewModel = new ViewModelProvider(this).get(BookViewModel.class);
        BookRepository bookRepository = new BookRepository();
        bookRepository.findBookById(this.getContext(), bookViewModel, id);
        bookViewModel.getBook().observe(getViewLifecycleOwner(), book -> {
            try {

                title.setText(bookViewModel.getBook().getValue().getTitle());
                //author.setText(Integer.toString(bookViewModel.getBook().getValue().getAuthorID()));
                author.setText(bookViewModel.getBook().getValue().author);
                publication_year.setText(Integer.toString(bookViewModel.getBook().getValue().getPublication_year()));
                publication_year.setText(bookViewModel.getBook().getValue().getPublication_year());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        return root;
    }
}

