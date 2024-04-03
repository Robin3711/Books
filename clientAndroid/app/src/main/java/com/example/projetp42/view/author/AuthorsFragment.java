package com.example.projetp42.view.author;

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
import com.example.projetp42.databinding.FragmentAuthorsBinding;
import com.example.projetp42.db.AuthorRepository;
import com.example.projetp42.view.book.ItemClickListener;
import com.example.projetp42.viewmodel.author.AuthorsViewModel;

public class AuthorsFragment extends Fragment implements ItemClickListener {

    private FragmentAuthorsBinding binding;
    private static final String TAG = "AuthorFragment"; // Log tag for debugging

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        binding = FragmentAuthorsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        AuthorsViewModel authorsViewModel = new ViewModelProvider(this).get(AuthorsViewModel.class);

        authorsViewModel.getAuthors().observe(getViewLifecycleOwner(), author -> {
            if (author != null) {
                RecyclerView recyclerView = root.findViewById(R.id.AuthorsRecyclerView);
                AuthorAdapter authorAdapter = new AuthorAdapter(author);
                authorAdapter.setClickListener(this);
                recyclerView.setAdapter(authorAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

                Log.d(TAG, "author != null.");
            }
        });

        AuthorRepository authorRepository = new AuthorRepository();

        try {
            authorRepository.findAuthors(this.getContext(), authorsViewModel);
            Log.d(TAG, "Authors loaded successfully.");
            //Log.d(TAG, "Authors: " + authorsViewModel.getAuthors().getValue().get(0).getFirstname());
            /*ArrayList<Author> authors = new ArrayList<>();
            authors.add(new Author(1,"John","Doe",null));
            authorsViewModel.loadAuthors(authors);*/
        } catch (Exception e) {
            Log.e(TAG, "Error loading authors: " + e.getMessage());
            e.printStackTrace();
        }

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onClick(View view, int id) {
    }
}
