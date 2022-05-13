package com.example.vento30.ui.search_users;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SearchUsersViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SearchUsersViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is search users fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}

