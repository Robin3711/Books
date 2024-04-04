package com.example.projetp42.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public class RatingViewModel extends AndroidViewModel {

    private final MutableLiveData<Integer> rating;

    public RatingViewModel(Application context) {
        super(context);
        rating = new MutableLiveData<>();
    }

    public MutableLiveData<Integer> getRating() {
        return rating;
    }

    public void loadRating(int rating) {
        this.rating.setValue(rating);
    }
}
