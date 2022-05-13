package com.example.vento30.ui.my_messages;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MyMessagesViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MyMessagesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is my messages fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
