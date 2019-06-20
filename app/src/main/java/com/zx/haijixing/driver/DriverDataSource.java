package com.zx.haijixing.driver;

import android.arch.paging.ItemKeyedDataSource;
import android.support.annotation.NonNull;
import android.util.Log;

import com.allen.library.RxHttpUtils;
import com.allen.library.interceptor.Transformer;
import com.allen.library.observer.StringObserver;
import com.zx.haijixing.share.service.DriverApiService;



public class DriverDataSource extends ItemKeyedDataSource<Integer,DriverInfo> {
    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<DriverInfo> callback) {

        RxHttpUtils.createApi(DriverApiService.class)
                .firstBg()
                .compose(Transformer.<String>switchSchedulers())
                .subscribe(new StringObserver() {
                    @Override
                    protected void onError(String errorMsg) {

                    }

                    @Override
                    protected void onSuccess(String data) {
                        Log.i("<<<<<driver data","<>"+data);
                        //callback.onResult();
                    }
                });

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<DriverInfo> callback) {
        RxHttpUtils.createApi(DriverApiService.class)
                .firstBg()
                .compose(Transformer.<String>switchSchedulers())
                .subscribe(new StringObserver() {
                    @Override
                    protected void onError(String errorMsg) {

                    }

                    @Override
                    protected void onSuccess(String data) {
                        Log.i("<<<<<driver data","<>"+data);
                    }
                });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<DriverInfo> callback) {

    }

    @NonNull
    @Override
    public Integer getKey(@NonNull DriverInfo item) {
        return item.getId();
    }
}
