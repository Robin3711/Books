package com.example.projetp42.view.author;

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
import com.example.projetp42.databinding.FragmentDashboardBinding;
import com.example.projetp42.db.AuthorRepository;
import com.example.projetp42.view.book.ItemClickListener;
import com.example.projetp42.viewmodel.author.AuthorsViewModel;

public class AuthorFragment extends Fragment implements ItemClickListener {

    private FragmentDashboardBinding binding;

    private AuthorsViewModel authorsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        authorsViewModel = new ViewModelProvider(this).get(AuthorsViewModel.class);
        AuthorRepository authorRepository = new AuthorRepository();

        try {
            authorRepository.findAuthors(this.getContext(), authorsViewModel);
        }
        catch (Exception e){
            e.printStackTrace();
        }


        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        RecyclerView recyclerView = root.findViewById(R.id.AuthorsRecyclerView);
        authorsViewModel.getAuthors().observe(getViewLifecycleOwner(), author -> {
            AuthorAdapter authorAdapter = new AuthorAdapter(author);
            authorAdapter.setClickListener(this);
            recyclerView.setAdapter(authorAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
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