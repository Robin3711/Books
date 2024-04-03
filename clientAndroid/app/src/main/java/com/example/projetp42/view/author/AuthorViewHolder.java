package com.example.projetp42.view.author;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetp42.R;
import com.example.projetp42.view.book.ItemClickListener;

public class AuthorViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    TextView name;
    Integer id;
    ItemClickListener clickListener;
    public AuthorViewHolder(@NonNull View itemView, ItemClickListener clickListener) {
        super(itemView);
        name = (TextView) itemView.findViewById(R.id.name);
        this.clickListener = clickListener;
        itemView.setTag(itemView);
        itemView.setOnClickListener(this);
    }

    public TextView getName() {
        return name;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public void onClick(View v) {
        if (clickListener != null) clickListener.onClick(v, id);
    }
}
