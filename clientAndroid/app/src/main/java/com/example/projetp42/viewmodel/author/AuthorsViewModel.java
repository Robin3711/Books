package com.example.projetp42.viewmodel.author;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.projetp42.model.Author;

import java.util.List;

public class AuthorsViewModel extends AndroidViewModel {
    private final MutableLiveData<List<Author>> authors;

    public AuthorsViewModel(Application context) {
        super(context);
        authors = new MutableLiveData<>();
    }

    public LiveData<List<Author>> getAuthors() {
        return authors;
    }
    public void loadAuthors(List<Author> authors){
        this.authors.setValue(authors);
    }
}
