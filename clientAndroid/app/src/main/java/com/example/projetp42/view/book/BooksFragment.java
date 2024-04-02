package com.example.projetp42.view.book;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetp42.R;
import com.example.projetp42.databinding.FragmentHomeBinding;
import com.example.projetp42.db.BookRepository;
import com.example.projetp42.viewmodel.BooksViewModel;

import java.util.ArrayList;

public class BooksFragment extends Fragment implements ItemClickListener{

    private FragmentHomeBinding binding;
    private BooksViewModel bookViewModel;

    private EditText titleEditText;
    private EditText authorEditText;
    private EditText tagEditText;
    private Button filterButton;

    private Spinner orderSpinner;

    public View onCreateView(@NonNull LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        bookViewModel = new ViewModelProvider(this).get(BooksViewModel.class);
        BookRepository bookRepository = new BookRepository();
        RecyclerView recyclerView = root.findViewById(R.id.LivresRecyclerView);
        try {
            BookRepository.BookData bookData = new BookRepository.BookData(null,null,null);
            bookRepository.findBooks(this.getContext(), bookViewModel,bookData,"publication_year");
        }
        catch (Exception e){
            e.printStackTrace();
        }

        bookViewModel.getBooks().observe(getViewLifecycleOwner(),book -> {
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                BookAdapter bookAdapter = new BookAdapter(book);
                bookAdapter.setClickListener(this);
                recyclerView.setAdapter(bookAdapter);
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

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.order_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        orderSpinner.setAdapter(adapter);
        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BookRepository.BookData bookData = new BookRepository.BookData(titleEditText.getText().toString(),authorEditText.getText().toString(),tagEditText.getText().toString());
                BookRepository bookRepository = new BookRepository();
                bookRepository.findBooks(getContext(),bookViewModel,bookData,orderSpinner.getSelectedItem().toString());
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