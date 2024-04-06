package com.example.projetp42.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.projetp42.model.Comment;

public class CommentViewModel extends AndroidViewModel {

    private final MutableLiveData<Comment> comment;
    public CommentViewModel(@NonNull Application application) {
        super(application);
        comment = new MutableLiveData<>();
    }

    public MutableLiveData<Comment> getComment() {
        return comment;
    }

    public void loadComment(Comment comment) {
        this.comment.setValue(comment);
    }
}
