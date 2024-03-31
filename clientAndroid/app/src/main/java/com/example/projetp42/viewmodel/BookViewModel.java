package com.example.projetp42.viewmodel;

import android.app.Application;
import android.widget.Toast;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.projetp42.model.Book;

import java.util.List;

public class BookViewModel extends AndroidViewModel {

    private final MutableLiveData<Book> book;

    public BookViewModel(Application context) {
        super(context);
        book = new MutableLiveData<>();
    }

    public LiveData<Book> getBook() {
        return book;
    }
    public void loadBook(Book book){
        this.book.setValue(book);
    }
}