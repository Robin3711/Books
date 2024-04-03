package com.example.projetp42.view.book;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.projetp42.R;
import com.example.projetp42.db.BookRepository;
import com.example.projetp42.model.Book;


public class AddBookFragment extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_book, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        EditText title = (EditText) getView().findViewById(R.id.title);
        EditText publication_year = (EditText) getView().findViewById(R.id.publication_year);
        title.setText("Title");
        publication_year.setText("Publication year");

        Button addBookButton = (Button) getView().findViewById(R.id.add_book);

        addBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Book book = new Book(0,"auteur", Integer.parseInt(publication_year.getText().toString()), title.getText().toString(), null, null, null);
                BookRepository bookRepository = new BookRepository();
                BookRepository.AddBookCallback addBookCallback = new BookRepository.AddBookCallback() {
                    @Override
                    public void onSuccess(int bookId) {
                        Navigation.findNavController(view).navigate(R.id.action_addBookFragment_to_navigation_home);
                    }

                    @Override
                    public void onFailure(String errorMessage) {
                        System.out.println(errorMessage);
                    }

                };

                bookRepository.addBook(getContext(), book,addBookCallback);
            }
        });

        super.onViewCreated(view, savedInstanceState);
    }
}