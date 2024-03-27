package com.example.projetp42.view.book;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetp42.R;
import com.example.projetp42.databinding.FragmentHomeBinding;
import com.example.projetp42.db.BookRepository;
import com.example.projetp42.db.VolleyRequestQueue;
import com.example.projetp42.viewmodel.BookViewModel;

public class BookFragment extends Fragment {

    private FragmentHomeBinding binding;
    private BookViewModel bookViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        BookViewModel bookViewModel =
                new ViewModelProvider(this).get(BookViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        bookAdapter bookAdapter = new bookAdapter();
        RecyclerView recyclerView = root.findViewById(R.id.LivresRecyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        recyclerView.setAdapter(bookAdapter);
        bookViewModel.getBook().observe(getViewLifecycleOwner(),book -> {
            if(book != null){
                ((TextView)root.findViewById(R.id.title)).setText(book.getTitle());
            }
        });
        BookRepository bookRepository = new BookRepository(bookViewModel);
        try {
            bookRepository.findBooks(this.getContext());
        }
        catch (Exception e){

        }
        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}