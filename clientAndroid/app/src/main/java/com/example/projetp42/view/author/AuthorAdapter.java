package com.example.projetp42.view.author;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetp42.R;
import com.example.projetp42.model.Author;
import com.example.projetp42.view.book.ItemClickListener;

import java.util.List;

public class AuthorAdapter extends RecyclerView.Adapter<AuthorViewHolder> {

    private final List<Author> authors;
    private ItemClickListener clickListener;

    public AuthorAdapter(List<Author> authors) {
        this.authors = authors;
    }

    @NonNull
    @Override
    public AuthorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.author_view_holder, parent, false);

        return new AuthorViewHolder(view, clickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull AuthorViewHolder holder, int position) {
        Author author = authors.get(position);
        if (author != null) {
            String name = author.getFirstname() + " " + author.getLastname();
            holder.getName().setText(name);
            holder.setId(author.getId());
        }
    }

    @Override
    public int getItemCount() {
        return authors != null ? authors.size() : 0;
    }

    public void setClickListener(ItemClickListener clickListener) {
        this.clickListener = clickListener;
    }
}
