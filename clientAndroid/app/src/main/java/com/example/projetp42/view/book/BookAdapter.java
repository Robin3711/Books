package com.example.projetp42.view.book;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetp42.R;
import com.example.projetp42.model.Book;
import com.example.projetp42.view.ItemClickListener;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookViewHolder>{

    public List<Book> books;
    private ItemClickListener clickListener;
    public BookAdapter(List<Book> listBook){
        this.books = listBook;
    }
    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.book_view_holder, parent, false);

        return new BookViewHolder(view,clickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        String title = "Error";
        Integer id = -1;
        try {
            title = books.get(position).getTitle();
            id = books.get(position).getId();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        holder.getTitle().setText(title);
        holder.setId(id);
    }

    @Override
    public int getItemCount() {
        if (books == null)
            return 0;
        return books.size();
    }
    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }
}
