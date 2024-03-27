package com.example.projetp42.view.book;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetp42.R;

public class bookViewHolder extends RecyclerView.ViewHolder{
    TextView title;
    public bookViewHolder(@NonNull View itemView) {
        super(itemView);
        title = (TextView) itemView.findViewById(R.id.title);
    }

    public TextView getTitle() {
        return title;
    }
}
