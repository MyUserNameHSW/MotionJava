package com.hsw.motionjava.demo.oscillation;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hsw.motionjava.Cheese;

import java.util.List;

/**
 * @author heshuai
 * created on: 2020/6/28 3:55 PM
 * description:
 */
public class OscillationViewModel extends ViewModel {
    private MutableLiveData<List<Cheese>> cheese;

    public OscillationViewModel() {
        cheese = new MutableLiveData<>(Cheese.getCheeseList());
    }

    public MutableLiveData<List<Cheese>> getCheese() {
        return cheese;
    }
}
