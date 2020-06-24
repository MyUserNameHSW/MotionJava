package com.hsw.motionjava.demo.dissolve;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hsw.motionjava.Cheese;

/**
 * @author heshuai
 * created on: 2020/6/24 3:18 PM
 * description:
 */
public class DissolveViewModel extends ViewModel {

    private int position = 0;

    private MutableLiveData<Integer> imageResId;

    public DissolveViewModel() {
        imageResId = new MutableLiveData<>();
        imageResId.setValue(Cheese.IMAGE_LIST.get(position));
    }

    public LiveData<Integer> getImageResId() {
        return imageResId;
    }

    public void next() {
        if (position == Cheese.IMAGE_LIST.size()) {
            position = 0;
        }
        imageResId.setValue(Cheese.IMAGE_LIST.get(position));
        position++;
    }
}
