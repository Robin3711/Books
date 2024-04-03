package com.example.projetp42.view.author;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.projetp42.R;
import com.example.projetp42.db.AuthorRepository;
import com.example.projetp42.db.BookRepository;
import com.example.projetp42.viewmodel.author.AuthorViewModel;
import com.example.projetp42.viewmodel.book.BookViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AuthorInfoFragment extends Fragment {
    TextView firstname;
    TextView lastname;
    private int id;
    private AuthorViewModel authorViewModel;

    public AuthorInfoFragment() {
        // Required empty public constructor
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
        return root;
    }
}