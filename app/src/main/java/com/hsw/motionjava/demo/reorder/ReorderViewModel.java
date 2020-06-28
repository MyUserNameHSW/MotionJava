package com.hsw.motionjava.demo.reorder;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hsw.motionjava.Cheese;

import java.util.List;

/**
 * @author heshuai
 * created on: 2020/6/26 4:19 PM
 * description:
 */
public class ReorderViewModel extends ViewModel {
    private MutableLiveData<List<Cheese>> mutableLiveData;
    public ReorderViewModel() {
        List<Cheese> cheeseList = Cheese.getCheeseList();
        mutableLiveData = new MutableLiveData<>(cheeseList);
    }

    public void move(int from, int to) {
        List<Cheese> list = mutableLiveData.getValue();
        Cheese cheese = list.remove(from);
        list.add(to, cheese);
        getList().setValue(list);
    }

    public MutableLiveData<List<Cheese>> getList() {
        return mutableLiveData;
    }
}
