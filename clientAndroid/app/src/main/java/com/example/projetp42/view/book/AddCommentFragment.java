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

public class AddCommentFragment extends Fragment {


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
        View root = inflater.inflate(R.layout.fragment_add_comment, container, false);

        EditText comment = root.findViewById(R.id.comment_text);
        EditText author = root.findViewById(R.id.comment_author);
        Button submit = root.findViewById(R.id.comment_button);

        submit.setOnClickListener(v -> {
            BookRepository bookRepository = new BookRepository();
            Comment newComment = new Comment(0,author.getText().toString(), bookId, comment.getText().toString(), null, null);
            bookRepository.addComment(this.getContext(), newComment);
            Toast.makeText(getContext(), "Comment added", Toast.LENGTH_SHORT).show();
            NavController navController = Navigation.findNavController(v);
            navController.popBackStack();
        });

        return root;
    }
}