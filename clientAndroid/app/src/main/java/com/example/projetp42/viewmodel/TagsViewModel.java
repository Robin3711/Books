package com.example.projetp42.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.projetp42.model.Tag;

import java.util.ArrayList;
import java.util.List;

public class TagsViewModel extends AndroidViewModel {

    private final MutableLiveData<List<Tag>> tags;

    public TagsViewModel(Application context) {
        super(context);
        tags = new MutableLiveData<>();
    }

    public MutableLiveData<List<Tag>> getTags() {
        return tags;
    }

    public void setTags(ArrayList<Tag> tags) {
        this.tags.setValue(tags);
    }
}
