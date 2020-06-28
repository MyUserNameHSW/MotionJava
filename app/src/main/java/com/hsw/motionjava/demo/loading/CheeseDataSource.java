package com.hsw.motionjava.demo.loading;

import android.os.SystemClock;

import androidx.annotation.NonNull;
import androidx.paging.DataSource;
import androidx.paging.PositionalDataSource;

import com.hsw.motionjava.Cheese;

/**
 * @author heshuai
 * created on: 2020/6/28 11:42 AM
 * description:
 */
public class CheeseDataSource extends PositionalDataSource<Cheese> {

    static class Factory extends DataSource.Factory<Integer, Cheese> {
        @NonNull
        @Override
        public DataSource<Integer, Cheese> create() {
            return new CheeseDataSource();
        }
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams params, @NonNull LoadInitialCallback<Cheese> callback) {
        SystemClock.sleep(3000L);
        callback.onResult(Cheese.getCheeseList().subList(
                params.requestedStartPosition,
                params.requestedStartPosition + params.requestedLoadSize
                ),
                params.requestedStartPosition,
                Cheese.getCheeseList().size());
    }

    @Override
    public void loadRange(@NonNull LoadRangeParams params, @NonNull LoadRangeCallback<Cheese> callback) {
        SystemClock.sleep(3000L);
        callback.onResult(
                Cheese.getCheeseList().subList(
                        params.startPosition, params.startPosition + params.loadSize
                )
        );
    }
}
