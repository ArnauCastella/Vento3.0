package com.example.vento30.ui.my_timeline;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MyTimelineViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MyTimelineViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is my timeline fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
