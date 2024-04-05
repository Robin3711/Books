package com.example.projetp42.view.book;

import static java.lang.Integer.parseInt;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.projetp42.R;
import com.example.projetp42.db.AuthorRepository;
import com.example.projetp42.db.BookRepository;
import com.example.projetp42.db.TagRepository;
import com.example.projetp42.model.Author;
import com.example.projetp42.model.Book;
import com.example.projetp42.model.Tag;
import com.example.projetp42.viewmodel.TagsViewModel;
import com.example.projetp42.viewmodel.author.AuthorsViewModel;
import com.example.projetp42.viewmodel.book.BookViewModel;

import java.util.ArrayList;
import java.util.List;


public class AddBookFragment extends Fragment {

    public int id;
    private View root;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences prefs = getActivity().getPreferences(Context.MODE_PRIVATE);
        id = prefs.getInt("idAuthorFromAuthorInfo", -1);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_add_book, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        EditText title = view.findViewById(R.id.title);
        EditText publication_year = view.findViewById(R.id.publication_year);

        Spinner authorSpinner = view.findViewById(R.id.auteurSpinner);

        AuthorsViewModel authorsViewModel = new ViewModelProvider(this).get(AuthorsViewModel.class);
        AuthorRepository authorRepository = new AuthorRepository();

        try {
            authorRepository.findAuthors(this.getContext(), authorsViewModel);
            Log.d("AuthorsFragment", "Authors loaded successfully.");
        } catch (Exception e) {
            Log.e("AuthorsFragment", "Error loading authors: " + e.getMessage());
            e.printStackTrace();
        }

            authorsViewModel.getAuthors().observe(getViewLifecycleOwner(), author -> {
                ArrayAdapter<Author> adapter = new ArrayAdapter<Author>(getContext(), android.R.layout.simple_spinner_dropdown_item, author) {
                    @NonNull
                    @Override
                    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                        TextView textView = (TextView) super.getView(position, convertView, parent);
                        textView.setText(getItem(position).firstname + " " + getItem(position).lastname);
                        return textView;
                    }

                    @Override
                    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                        TextView textView = (TextView) super.getDropDownView(position, convertView, parent);
                        textView.setText(getItem(position).firstname + " " + getItem(position).lastname);
                        return textView;
                    }
                };
                authorSpinner.setAdapter(adapter);
                int indexToSelect = -1;
                for (int i = 0; i < author.size() && indexToSelect ==-1 && id != -1; i++) {
                    if (author.get(i).id == id) {
                        indexToSelect = i;
                    }
                }
                if (indexToSelect != -1) {
                    authorSpinner.setSelection(indexToSelect);
                }

            });


        Spinner tagSpinner = view.findViewById(R.id.tagSpinner);

        TagsViewModel tagsViewModel = new ViewModelProvider(this).get(TagsViewModel.class);
        TagRepository tagRepository = new TagRepository();

        try {
            tagRepository.getAllTags(this.getContext(), tagsViewModel);
            Log.d("TagsFragment", "Tags loaded successfully.");
        } catch (Exception e) {
            Log.e("TagsFragment", "Error loading tags: " + e.getMessage());
            e.printStackTrace();
        }

        tagsViewModel.getTags().observe(getViewLifecycleOwner(), tags -> {
            ArrayAdapter<Tag> adapter = new ArrayAdapter<Tag>(getContext(), android.R.layout.simple_spinner_dropdown_item, tags) {
                @NonNull
                @Override
                public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                    TextView textView = (TextView) super.getView(position, convertView, parent);
                    textView.setText(getItem(position).name);
                    return textView;
                }

                @Override
                public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                    TextView textView = (TextView) super.getDropDownView(position, convertView, parent);
                    textView.setText(getItem(position).name);
                    return textView;
                }
            };
            tagSpinner.setAdapter(adapter);
        });


    Button addBookButton = (Button) getView().findViewById(R.id.add_book);

        addBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Author author1 =(Author) authorSpinner.getSelectedItem();
                ArrayList<Tag> tags = new ArrayList<>();
                tags.add((Tag) tagSpinner.getSelectedItem());
                Book book = new Book(0,author1.id,author1.firstname + " "+ author1.lastname, parseInt(publication_year.getText().toString()), title.getText().toString(), tags, null, null);
                BookRepository bookRepository = new BookRepository();
                BookRepository.AddBookCallback addBookCallback = new BookRepository.AddBookCallback() {
                    @Override
                    public void onSuccess(int bookId) {
                        NavController navController = Navigation.findNavController(v);
                        navController.popBackStack();
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
