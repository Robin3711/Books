package com.example.projetp42.view.author;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetp42.R;
import com.example.projetp42.databinding.FragmentDashboardBinding;
import com.example.projetp42.db.AuthorRepository;
import com.example.projetp42.viewmodel.author.AuthorViewModel;
import com.example.projetp42.viewmodel.author.AuthorsViewModel;

public class AuthorsFragment extends Fragment {

    private FragmentDashboardBinding binding;

    private AuthorsViewModel authorsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        AuthorViewModel authorViewModel = new ViewModelProvider(this).get(AuthorViewModel.class);
        View root = binding.getRoot();
        AuthorRepository authorRepository = new AuthorRepository();
        RecyclerView recyclerView = root.findViewById(R.id.AuthorsRecyclerView);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textDashboard;
        authorViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}