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
    private int id;
    private BookViewModel bookViewModel;
    private  RatingViewModel ratingViewModel;

    TextView avgRating ;

    private ListView tags;

    private static final String TAG = "BookInfoFragment"; // Log tag for debugging

    public BookInfoFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences prefs = getActivity().getPreferences(Context.MODE_PRIVATE);
        id = prefs.getInt("id", -1);
    }

    @SuppressLint("SetTextI18n")
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        View root = inflater.inflate(R.layout.fragment_book_info, container, false);
        title = root.findViewById(R.id.info_title);
        author = root.findViewById(R.id.info_author);
        publication_year = root.findViewById(R.id.info_publication_year);
        tags = root.findViewById(R.id.info_tags);
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
        Log.d(TAG, "Book ID: " + id);
        bookViewModel.getBook().observe(getViewLifecycleOwner(), book -> {
            try {

                title.setText(bookViewModel.getBook().getValue().getTitle());
                //author.setText(Integer.toString(bookViewModel.getBook().getValue().getAuthorID()));
                author.setText(bookViewModel.getBook().getValue().author);
                author.setTag(bookViewModel.getBook().getValue().authorID);
                publication_year.setText(Integer.toString(bookViewModel.getBook().getValue().getPublication_year()));//

                ArrayList<String> tagsList = new ArrayList<String>();
                for(int i = 0; i < bookViewModel.getBook().getValue().tags.size(); i++) {
                    tagsList.add(bookViewModel.getBook().getValue().tags.get(i).name);
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, tagsList);
                tags.setAdapter(adapter);

                if(tags.getCount() == 0) {
                    tags.setTag(0, "No tags");
                    Log.d(TAG, "No tags");
                }
                Log.d(TAG, "Book loaded successfully.");

            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // suppression
        FloatingActionButton fab = root.findViewById(R.id.info_book_delete_button);
        fab.setOnClickListener(view -> {
            bookRepository.deleteBook(this.getContext(), id);
            Navigation.findNavController(view).navigate(R.id.action_bookInfoFragment_to_fragment_books);
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

