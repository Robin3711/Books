package com.example.projetp42.view.author;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetp42.R;
import com.example.projetp42.model.Author;
import com.example.projetp42.view.book.ItemClickListener;
import com.example.projetp42.view.book.bookViewHolder;

import java.util.List;

public class AuthorAdapter extends RecyclerView.Adapter<AuthorViewHolder> {

    public List<Author> authors;
    private ItemClickListener clickListener;

    public AuthorAdapter(List<Author> authors) {
        this.authors = authors;
    }
    @NonNull
    @Override
    public AuthorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.book_view_holder, parent, false);

        return new AuthorViewHolder(view,clickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull AuthorViewHolder holder, int position) {
        String name = "Error";
        Integer id = -1;
        try {
            name = authors.get(position).firstname+" "+authors.get(position).lastname;
            id = authors.get(position).getId();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        holder.getName().setText(name);
        holder.setId(id);
    }

    @Override
    public int getItemCount() {
        if(authors == null)
        {
            return  0;
        }
        else
        {
            return authors.size();
        }
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }
}
