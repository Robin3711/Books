package com.example.projetp42.viewmodel;

import android.app.Application;
import android.widget.Toast;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.projetp42.model.Book;

import java.util.List;

public class BooksViewModel extends AndroidViewModel {

    private final MutableLiveData<List<Book>> books;

    public BooksViewModel(Application context) {
        super(context);
        books = new MutableLiveData<>();
    }

    public LiveData<List<Book>> getBooks() {
        return books;
    }
    public void loadBook(List<Book> books){
        this.books.setValue(books);
    }
}