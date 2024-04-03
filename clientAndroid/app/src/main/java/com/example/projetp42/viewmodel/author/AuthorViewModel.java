package com.example.projetp42.viewmodel.author;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AuthorViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public AuthorViewModel() {
        mText = new MutableLiveData<>();
    }

    public LiveData<String> getText() {
        return mText;
    }
}