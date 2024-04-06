package com.example.projetp42.view.book;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.projetp42.R;
import com.example.projetp42.db.BookRepository;
import com.example.projetp42.model.Comment;
import com.example.projetp42.model.Rating;

public class addRatingFragment extends Fragment {


    int bookId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences prefs = getActivity().getPreferences(Context.MODE_PRIVATE);
        bookId = prefs.getInt("BookId", -1);
        if (bookId == -1) {
            Toast.makeText(getContext(), "cannot get book, please restart", Toast.LENGTH_SHORT).show();
            throw new IllegalArgumentException("bookId is not set");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_add_rating, container, false);

        EditText comment = root.findViewById(R.id.rating_value);
        EditText author = root.findViewById(R.id.rating_author);
        Button submit = root.findViewById(R.id.rating_button);

        submit.setOnClickListener(v -> {
            BookRepository bookRepository = new BookRepository();
            Rating newRating = new Rating(0, Integer.parseInt(comment.getText().toString()), author.getText().toString(), bookId);
            bookRepository.addRating(this.getContext(), newRating);
            Toast.makeText(getContext(), "Rating added", Toast.LENGTH_SHORT).show();
            NavController navController = Navigation.findNavController(v);
            navController.popBackStack();
        });

        return root;
    }
}