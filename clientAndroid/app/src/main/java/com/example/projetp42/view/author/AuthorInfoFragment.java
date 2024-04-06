package com.example.projetp42.view.author;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.projetp42.R;
import com.example.projetp42.db.AuthorRepository;
import com.example.projetp42.db.BookRepository;
import com.example.projetp42.view.book.BookAdapter;
import com.example.projetp42.view.book.ItemClickListener;
import com.example.projetp42.viewmodel.author.AuthorViewModel;
import com.example.projetp42.viewmodel.book.BookViewModel;
import com.example.projetp42.viewmodel.book.BooksViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AuthorInfoFragment extends Fragment implements ItemClickListener {
    TextView firstname;
    TextView lastname;
    private int id;
    private AuthorViewModel authorViewModel;
    private BooksViewModel booksViewModel;

    public AuthorInfoFragment() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences prefs = getActivity().getPreferences(Context.MODE_PRIVATE);
        id = prefs.getInt("id", -1);

    }
    @SuppressLint("SetTextI18n")
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_author_info, container, false);

        firstname = root.findViewById(R.id.firstname);
        lastname = root.findViewById(R.id.lastname);

        authorViewModel = new ViewModelProvider(this).get(AuthorViewModel.class);

        AuthorRepository authorRepository = new AuthorRepository();
        authorRepository.findAuthorById(this.getContext(), authorViewModel, id);

        authorViewModel.getAuthor().observe(getViewLifecycleOwner(), book -> {
            try {
                firstname.setText(authorViewModel.getAuthor().getValue().getFirstname());
                lastname.setText(authorViewModel.getAuthor().getValue().getLastname());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // suppression
        FloatingActionButton fab = root.findViewById(R.id.info_author_delete_button);
        fab.setOnClickListener(view -> {
            authorRepository.deleteAuthor(this.getContext(), id);
            Navigation.findNavController(view).navigate(R.id.action_authorInfoFragment_to_authorFragment);
        });
        Button button = root.findViewById(R.id.buttonAjoutLivreAuthor);
        button.setOnClickListener(view -> {
            SharedPreferences prefs = getActivity().getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("idAuthorFromAuthorInfo", id);
            editor.apply();
            editor.commit();
            Navigation.findNavController(view).navigate(R.id.action_authorInfoFragment_to_addBookFragment);
        });

        //Recuperation des books
        booksViewModel = new ViewModelProvider(this).get(BooksViewModel.class);
        BookRepository bookRepository = new BookRepository();
        RecyclerView recyclerView = root.findViewById(R.id.AuthorBook);
        try {
            BookRepository.BookData bookData = new BookRepository.BookData(null,null,null);
            bookRepository.findBooksByAuthor(this.getContext(), booksViewModel,id);
        }
        catch (Exception e){
            e.printStackTrace();
        }

        booksViewModel.getBooks().observe(getViewLifecycleOwner(), book -> {
            BookAdapter bookAdapter = new BookAdapter(book);
            recyclerView.setAdapter(bookAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            bookAdapter.setClickListener(this);
        });
        return root;
    }

    @Override
    public void onClick(View view, int id) {
        // Cliker sur un lien arrive ici et id = book.id
        SharedPreferences prefs = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("id", id);
        editor.apply();
        editor.commit();
        Navigation.findNavController(view).navigate(R.id.action_authorInfoFragment_to_bookInfoFragment);
    }
}