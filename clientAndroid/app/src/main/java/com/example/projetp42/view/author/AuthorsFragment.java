package com.example.projetp42.view.author;

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
import com.example.projetp42.databinding.FragmentDashboardBinding;
import com.example.projetp42.db.AuthorRepository;
import com.example.projetp42.view.book.ItemClickListener;
import com.example.projetp42.viewmodel.author.AuthorViewModel;
import com.example.projetp42.viewmodel.author.AuthorsViewModel;

public class AuthorsFragment extends Fragment implements ItemClickListener {

    private FragmentDashboardBinding binding;

    private AuthorsViewModel authorsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        authorsViewModel = new ViewModelProvider(this).get(AuthorsViewModel.class);
        View root = binding.getRoot();
        AuthorRepository authorRepository = new AuthorRepository();
        RecyclerView recyclerView = root.findViewById(R.id.AuthorsRecyclerView);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        authorsViewModel.getAuthors().observe(getViewLifecycleOwner(), author -> {
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            AuthorAdapter authorAdapter = new AuthorAdapter(author);
            authorAdapter.setClickListener(this);
            recyclerView.setAdapter(authorAdapter);
        });
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