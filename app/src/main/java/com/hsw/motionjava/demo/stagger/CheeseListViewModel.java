package com.hsw.motionjava.demo.stagger;

import android.os.Handler;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelStore;

import com.hsw.motionjava.Cheese;

import java.util.ArrayList;
import java.util.List;

/**
 * @author heshuai
 * created on: 2020/6/28 11:09 AM
 * description:
 */
public class CheeseListViewModel extends ViewModel {

    private MutableLiveData<List<Cheese>> cheese = new MutableLiveData<>();

    public CheeseListViewModel() {
        refresh();
    }

    public void refresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                cheese.setValue(Cheese.getCheeseList());
            }
        }, 300);
    }

    public void clear() {
        cheese.setValue(new ArrayList<Cheese>());
    }

    public MutableLiveData<List<Cheese>> getCheese() {
        return cheese;
    }
}
