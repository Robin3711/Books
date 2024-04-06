package com.example.projetp42.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public class RatingViewModel extends AndroidViewModel {

    private final MutableLiveData<Float> rating;

    public RatingViewModel(Application context) {
        super(context);
        rating = new MutableLiveData<>();
    }

    public MutableLiveData<Float> getRating() {
        return rating;
    }

    public void loadRating(Float rating) {
        this.rating.setValue(rating);
    }
}
