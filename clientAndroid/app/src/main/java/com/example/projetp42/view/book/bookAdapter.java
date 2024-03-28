package com.example.projetp42.view.book;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetp42.R;
import com.example.projetp42.model.Book;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class bookAdapter extends RecyclerView.Adapter<bookViewHolder>{

    public List<Book> books;
    public bookAdapter(List<Book> listBook){
        this.books = listBook;
    }
    @NonNull
    @Override
    public bookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.book_view_holder, parent, false);

        return new bookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull bookViewHolder holder, int position) {
        String title = "Error";
        try {
            title = books.get(position).getTitle();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        holder.getTitle().setText(title);
    }

    @Override
    public int getItemCount() {
        return books.size();
    }
}
