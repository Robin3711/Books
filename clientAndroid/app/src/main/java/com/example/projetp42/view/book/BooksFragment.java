package com.example.projetp42.view.book;

import android.os.Bundle;
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
        BookRepository bookRepository = new BookRepository(bookViewModel);
        RecyclerView recyclerView = root.findViewById(R.id.LivresRecyclerView);
        /*try {
            test = bookRepository.findBooks(this.getContext());
            bookViewModel.loadBook(test);
        }
        catch (Exception e){

        }*/

        bookViewModel.loadBook(test);

        bookViewModel.getBooks().observe(getViewLifecycleOwner(),book -> {
            bookAdapter bookAdapter = new bookAdapter(book);
            recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
            recyclerView.setAdapter(bookAdapter);
        });

        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}