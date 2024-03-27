package com.example.projetp42.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.projetp42.model.Book;

public class BookViewModel extends ViewModel {

    private final MutableLiveData<Book> book = new MutableLiveData<>();

    public BookViewModel() {
    }

    public LiveData<Book> getBook() {
        return book;
    }
    public void loadBook(Book book){
        this.book.setValue(book);
    }
}