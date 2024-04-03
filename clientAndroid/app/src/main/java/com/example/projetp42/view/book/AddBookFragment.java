package com.example.projetp42.view.book;

import static java.lang.Integer.parseInt;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.projetp42.R;
import com.example.projetp42.db.BookRepository;
import com.example.projetp42.model.Book;
import com.example.projetp42.viewmodel.author.AuthorsViewModel;
import com.example.projetp42.viewmodel.book.BookViewModel;


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
        EditText title = view.findViewById(R.id.title);
        EditText publication_year = view.findViewById(R.id.publication_year);

        /*Spinner spinner = view.findViewById(R.id.author); //on voulait recuperer la list des auteurs

        AuthorsViewModel authorsViewModel = new ViewModelProvider(this).get(AuthorsViewModel.class);
        authorsViewModel.getAuthors().observe(getViewLifecycleOwner(), authors -> {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            for (int i = 0; i < authors.size(); i++) {
                adapter.add(authors.get(i).firstname + " " + authors.get(i).lastname);
            }
            spinner.setAdapter(adapter);
        }); */

    EditText author = view.findViewById(R.id.author);

    Button addBookButton = (Button) getView().findViewById(R.id.add_book);

        addBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Book book = new Book(0,parseInt(author.getText().toString()),"author", parseInt(publication_year.getText().toString()), title.getText().toString(), null, null, null);
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