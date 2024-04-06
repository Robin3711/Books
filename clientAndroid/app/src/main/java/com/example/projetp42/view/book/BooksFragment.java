package com.example.projetp42.view.book;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetp42.R;
import com.example.projetp42.databinding.FragmentBooksBinding;
import com.example.projetp42.db.BookRepository;
import com.example.projetp42.viewmodel.book.BooksViewModel;

public class BooksFragment extends Fragment implements ItemClickListener{

    private FragmentBooksBinding binding;
    private BooksViewModel booksViewModel;

    private static final String PREF_TITLE = "title";
    private static final String PREF_AUTHOR = "author";
    private static final String PREF_TAG = "tag";
    private static final String PREF_ORDER = "order";

    private EditText titleEditText;
    private EditText authorEditText;
    private EditText tagEditText;
    private Button filterButton;

    private Spinner orderSpinner;

    public View onCreateView(@NonNull LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentBooksBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        booksViewModel = new ViewModelProvider(this).get(BooksViewModel.class);
        BookRepository bookRepository = new BookRepository();
        RecyclerView recyclerView = root.findViewById(R.id.LivresRecyclerView);

        SharedPreferences prefs = getActivity().getPreferences(Context.MODE_PRIVATE);
        String savedTitle = prefs.getString(PREF_TITLE, null);
        String savedAuthor = prefs.getString(PREF_AUTHOR, null);
        String savedTag = prefs.getString(PREF_TAG, null);
        String savedOrder = prefs.getString(PREF_ORDER, "publication_year");

        try {
            BookRepository.BookData bookData = new BookRepository.BookData(savedTitle,savedAuthor,savedTag);
            bookRepository.findBooks(this.getContext(), booksViewModel,bookData,savedOrder);
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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        filterButton = binding.filterButton;
        titleEditText = binding.filterTitleEditText;
        authorEditText = binding.filterAuthorEditText;
        tagEditText = binding.filterTagEditText;
        orderSpinner = binding.orderSpinner;

        titleEditText.setText(getActivity().getPreferences(Context.MODE_PRIVATE).getString(PREF_TITLE, ""));
        authorEditText.setText(getActivity().getPreferences(Context.MODE_PRIVATE).getString(PREF_AUTHOR, ""));
        tagEditText.setText(getActivity().getPreferences(Context.MODE_PRIVATE).getString(PREF_TAG, ""));

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.order_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        orderSpinner.setAdapter(adapter);
        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences prefs = getActivity().getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();

                editor.putString(PREF_TITLE, titleEditText.getText().toString());
                editor.putString(PREF_AUTHOR, authorEditText.getText().toString());
                editor.putString(PREF_TAG, tagEditText.getText().toString());
                editor.putString(PREF_ORDER, orderSpinner.getSelectedItem().toString());
                editor.apply();

                BookRepository.BookData bookData = new BookRepository.BookData(titleEditText.getText().toString(),authorEditText.getText().toString(),tagEditText.getText().toString());
                BookRepository bookRepository = new BookRepository();
                bookRepository.findBooks(getContext(),booksViewModel,bookData,orderSpinner.getSelectedItem().toString());
            }
        });

        // FAB and book creation
        binding.floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_navigation_home_to_addBookFragment);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onClick(View view, int id) {
        // Cliker sur un lien arrive ici et id = book.id
        SharedPreferences prefs = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("id", id);
        editor.apply();
        editor.commit();
        Navigation.findNavController(view).navigate(R.id.action_navigation_home_to_bookInfoFragment);
    }
}
