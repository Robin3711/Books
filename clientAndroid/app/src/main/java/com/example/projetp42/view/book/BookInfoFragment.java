package com.example.projetp42.view.book;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.projetp42.R;
import com.example.projetp42.db.BookRepository;
import com.example.projetp42.viewmodel.RatingViewModel;
import com.example.projetp42.viewmodel.book.BookViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;

import java.util.ArrayList;

public class BookInfoFragment extends Fragment {
    TextView title;
    TextView author;
    TextView publication_year;
    TextView avgRating ;
    private ListView tags;
    private ListView comments;
    private int id;
    private BookViewModel bookViewModel;
    private  RatingViewModel ratingViewModel;

    public BookInfoFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences prefs = getActivity().getPreferences(Context.MODE_PRIVATE);
        id = prefs.getInt("id", -1);
    }

    @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables"})
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_book_info, container, false);

        title = root.findViewById(R.id.info_title);
        author = root.findViewById(R.id.info_author);
        publication_year = root.findViewById(R.id.info_publication_year);
        tags = root.findViewById(R.id.info_tags);
        comments = root.findViewById(R.id.info_comments);
        avgRating = root.findViewById(R.id.info_avg_rating);

        ratingViewModel = new ViewModelProvider(this).get(RatingViewModel.class);
        bookViewModel = new ViewModelProvider(this).get(BookViewModel.class);

        BookRepository bookRepository = new BookRepository();

        try {
            bookRepository.getAvgRatingOfBook(id,ratingViewModel);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        bookRepository.findBookById(this.getContext(), bookViewModel, id);

        bookViewModel.getBook().observe(getViewLifecycleOwner(), book -> {
            try {

                title.setText(bookViewModel.getBook().getValue().getTitle());
                author.setText(bookViewModel.getBook().getValue().author);
                publication_year.setText("Année de publication: " +Integer.toString(bookViewModel.getBook().getValue().getPublication_year()));

                if(ratingViewModel.getRating().getValue() == null)
                {
                    avgRating.setText("no rating yet");
                }
                else {
                    avgRating.setText("Average rating: " + ratingViewModel.getRating().getValue());
                }

                author.setTag(bookViewModel.getBook().getValue().authorID);

                ArrayList<String> tagsList = new ArrayList<String>();
                for(int i = 0; i < bookViewModel.getBook().getValue().tags.size(); i++) {
                    tagsList.add(bookViewModel.getBook().getValue().tags.get(i).name);
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, tagsList);
                tags.setAdapter(adapter);

                ArrayList<String> commentsList = new ArrayList<String>();
                for(int i = 0; i < bookViewModel.getBook().getValue().comments.size(); i++) {
                    commentsList.add(bookViewModel.getBook().getValue().comments.get(i).author + " : " + bookViewModel.getBook().getValue().comments.get(i).content);
                }

                ArrayAdapter<String> adapterComments = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, commentsList);
                comments.setAdapter(adapterComments);

                if(comments.getCount() == 0) {
                    commentsList.add("No comments");
                }

                if(tags.getCount() == 0) {
                    tags.setTag(0, "No tags");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        FloatingActionButton fab = root.findViewById(R.id.info_book_delete_button);
        fab.setOnClickListener(view -> {
            bookRepository.deleteBook(this.getContext(), id);
            Navigation.findNavController(view).navigate(R.id.action_bookInfoFragment_to_fragment_books);
        });

        FloatingActionButton fabComment = root.findViewById(R.id.info_book_post_comment_button);
        fabComment.setImageDrawable(getResources().getDrawable(android.R.drawable.ic_btn_speak_now));
        fabComment.setOnClickListener(view -> {
            SharedPreferences prefs = getActivity().getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("BookIdForComment", id);
            editor.apply();
            editor.commit();
            Navigation.findNavController(view).navigate(R.id.action_bookInfoFragment_to_addCommentFragment);
        });

        author.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer authorID = (Integer) author.getTag();
                SharedPreferences prefs = getActivity().getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putInt("id", authorID);
                editor.apply();
                editor.commit();
                Navigation.findNavController(v).navigate(R.id.action_bookInfoFragment_to_authorInfoFragment);
            }
        });

        return root;
    }
}

