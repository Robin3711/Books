package com.example.projetp42.view.book;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetp42.R;

public class bookViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    TextView title;
    Integer id;
    ItemClickListener clickListener;
    public bookViewHolder(@NonNull View itemView, ItemClickListener clickListener) {
        super(itemView);
        title = (TextView) itemView.findViewById(R.id.title);
        this.clickListener = clickListener;
        itemView.setTag(itemView);
        itemView.setOnClickListener(this);
    }

    public TextView getTitle() {
        return title;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public void onClick(View v) {
        if (clickListener != null) clickListener.onClick(v, id);
    }
}
