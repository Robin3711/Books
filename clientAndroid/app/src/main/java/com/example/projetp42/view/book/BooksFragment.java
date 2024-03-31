package com.example.projetp42.view.book;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetp42.R;
import com.example.projetp42.databinding.FragmentHomeBinding;
import com.example.projetp42.db.BookRepository;
import com.example.projetp42.model.Book;
import com.example.projetp42.viewmodel.BooksViewModel;

import java.util.ArrayList;

public class BooksFragment extends Fragment {

    private FragmentHomeBinding binding;
    private BooksViewModel bookViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        ArrayList<Book> test = new ArrayList<>();
        test.add(new Book("b", "a", new ArrayList<String>()));
        test.add(new Book("b", "a", new ArrayList<String>()));
        test.add(new Book("b", "a", new ArrayList<String>()));
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        bookViewModel = new ViewModelProvider(this).get(BooksViewModel.class);
        BookRepository bookRepository = new BookRepository();
        RecyclerView recyclerView = root.findViewById(R.id.LivresRecyclerView);
        try {
            BookRepository.BookData bookData = new BookRepository.BookData(null,"togrfvbgf",null);
            bookRepository.findBooks(this.getContext(), bookViewModel,bookData);
        }
        catch (Exception e){
            e.printStackTrace();
        }

        if(bookViewModel.getBooks().getValue() == null){
            bookViewModel.loadBook(test);
        }

        bookViewModel.getBooks().observe(getViewLifecycleOwner(),book -> {
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setAdapter(new bookAdapter(book));
        });

        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}