package com.hsw.motionjava.demo.loading;

import android.content.Intent;

import androidx.arch.core.executor.ArchTaskExecutor;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.hsw.motionjava.Cheese;

/**
 * @author heshuai
 * created on: 2020/6/28 11:53 AM
 * description:
 */
public class LoadingViewModel extends ViewModel {
    private LiveData<PagedList<Cheese>> source = null;
    private MediatorLiveData<PagedList<Cheese>> _cheeses;

    public LoadingViewModel() {
        _cheeses = new MediatorLiveData<>();
        refresh();
    }

    public void refresh() {
        if (null != source) {
          _cheeses.removeSource(source);
        }

        source = new LivePagedListBuilder<Integer, Cheese>(new CheeseDataSource.Factory(), 15).
                build();
        _cheeses.addSource(source, new Observer<PagedList<Cheese>>() {
            @Override
            public void onChanged(PagedList<Cheese> cheeses) {
                _cheeses.postValue(cheeses);
            }
        });
    }

    public LiveData<PagedList<Cheese>> getCheeses() {
        return _cheeses;
    }
}
