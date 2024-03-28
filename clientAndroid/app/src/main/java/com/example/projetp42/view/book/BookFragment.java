package com.example.projetp42.view.book;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetp42.R;
import com.example.projetp42.databinding.FragmentHomeBinding;
import com.example.projetp42.db.BookRepository;
import com.example.projetp42.db.VolleyRequestQueue;
import com.example.projetp42.viewmodel.BooksViewModel;

public class BookFragment extends Fragment {

    private FragmentHomeBinding binding;
    private BooksViewModel bookViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        bookViewModel = new ViewModelProvider(this).get(BooksViewModel.class);
        BookRepository bookRepository = new BookRepository(bookViewModel);
        RecyclerView recyclerView = root.findViewById(R.id.LivresRecyclerView);
        try {
            bookViewModel.loadBook(bookRepository.findBooks(this.getContext()));
        }
        catch (Exception e){

        }
        bookViewModel.getBooks().observe(getViewLifecycleOwner(),book -> {
            bookAdapter bookAdapter = new bookAdapter(book);
            recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
            recyclerView.setAdapter(bookAdapter);
        });
        Toast.makeText(this.getContext(), bookViewModel.getBooks().getValue().size(), Toast.LENGTH_LONG).show();

        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}