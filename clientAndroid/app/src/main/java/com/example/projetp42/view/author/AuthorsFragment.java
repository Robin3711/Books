package com.example.projetp42.view.author;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetp42.R;
import com.example.projetp42.databinding.FragmentAuthorsBinding;
import com.example.projetp42.db.AuthorRepository;
import com.example.projetp42.view.ItemClickListener;
import com.example.projetp42.viewmodel.author.AuthorsViewModel;

public class AuthorsFragment extends Fragment implements ItemClickListener {

    private FragmentAuthorsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAuthorsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        AuthorsViewModel authorsViewModel = new ViewModelProvider(this).get(AuthorsViewModel.class);

        authorsViewModel.getAuthors().observe(getViewLifecycleOwner(), author -> {
            if (author != null) {
                RecyclerView recyclerView = root.findViewById(R.id.AuthorsRecyclerView);
                AuthorsAdapter authorAdapter = new AuthorsAdapter(author);
                authorAdapter.setClickListener(this);
                recyclerView.setAdapter(authorAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

            }
        });

        AuthorRepository authorRepository = new AuthorRepository();

        try {
            authorRepository.findAuthors(this.getContext(), authorsViewModel);
        } catch (Exception e) {
            Toast.makeText(this.getContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

        binding.fabAddAuthor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_authorFragment_to_addAuthorFragment);
            }
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
        SharedPreferences prefs = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("idAuthor", id);
        editor.apply();
        editor.commit();
        Navigation.findNavController(view).navigate(R.id.action_authorFragment_to_authorInfoFragment);

    }
}
