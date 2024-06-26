package com.example.projetp42.view.author;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.projetp42.R;
import com.example.projetp42.db.AuthorRepository;
import com.example.projetp42.model.Author;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AddAuthorFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_add_author, container, false);
        EditText firstname = root.findViewById(R.id.editTextAddFirstname);
        EditText lastname = root.findViewById(R.id.editTextAddLastname);
        Button addAuthor = root.findViewById(R.id.addAuthorButton);

        addAuthor.setOnClickListener(v -> {
            Author author = new Author(0, firstname.getText().toString(), lastname.getText().toString(), null);
            AuthorRepository authorRepository = new AuthorRepository();
            AuthorRepository.AddAuthorCallback addAuthorCallback = new AuthorRepository.AddAuthorCallback() {
                @Override
                public void onSuccess(int authorId) {
                    Toast.makeText(getContext(), "Author added successfully", Toast.LENGTH_LONG).show();
                    Navigation.findNavController(root).navigate(R.id.action_addAuthorFragment_to_authorFragment);
                }

                @Override
                public void onFailure(String errorMessage) {
                    Toast.makeText(getContext(), "try again pls", Toast.LENGTH_LONG).show();
                }
            };
            authorRepository.addAuthor(getContext(), author, addAuthorCallback);
        });


        return root;
    }
}