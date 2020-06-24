package com.hsw.motionjava.demo.fabtransformation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hsw.motionjava.Cheese;

import java.util.ArrayList;
import java.util.List;

/**
 * @author heshuai
 * created on: 2020/6/24 5:03 PM
 * description:
 */
public class FabTransformationViewModel extends ViewModel {
    private MutableLiveData<List<Cheese>> listCheese;

    public FabTransformationViewModel() {
        listCheese = new MutableLiveData<>();
        List<Cheese> cheeses = new ArrayList<>();
        for (int i = 0; i < Cheese.IMAGE_LIST.size(); i++) {
            cheeses.add(new Cheese(Cheese.NAMES.get(i),Cheese.IMAGE_LIST.get(i)));
        }
        listCheese.setValue(cheeses);
    }

    public LiveData<List<Cheese>> getListCheese() {
        return listCheese;
    }
}
