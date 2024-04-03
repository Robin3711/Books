package com.example.projetp42.viewmodel.author;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.projetp42.model.Author;
import com.example.projetp42.model.Book;

public class AuthorViewModel extends ViewModel {

    private final MutableLiveData<Author> mText;

    public AuthorViewModel() {
        mText = new MutableLiveData<>();
    }

    public LiveData<Author> getAuthor() {
        return mText;
    }
    public void loadAuthor(Author author){
        this.mText.setValue(author);
    }
}