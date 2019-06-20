package com.zx.haijixing.driver;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.paging.DataSource;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.arch.paging.RxPagedListBuilder;
import android.support.annotation.NonNull;

import com.allen.library.RxHttpUtils;
import com.allen.library.base.BaseObserver;
import com.allen.library.bean.BaseData;
import com.allen.library.interceptor.Transformer;
import com.allen.library.observer.CommonObserver;
import com.zx.haijixing.share.dao.HaiDao;
import com.zx.haijixing.share.dao.HaiDataBase;
import com.zx.haijixing.share.service.DriverApiService;


import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;

public class DriverViewModel extends AndroidViewModel {

    private HaiDao haiDao;
    public DriverViewModel(@NonNull Application application) {
        super(application);
        haiDao = HaiDataBase.getInstance(application).getDao();
    }

    public LiveData<PagedList<DriverInfo>> driver(){
        DataSource.Factory<Integer, DriverInfo> allDriver = haiDao.getAllDriver();
        return new LivePagedListBuilder<>(allDriver,10).build();
        //return new LivePagedListBuilder(allDriver,new PagedList.Config.Builder().setPageSize(10).setEnablePlaceholders(false).setInitialLoadSizeHint(10).build()).build();
    }

    public Flowable<PagedList<DriverInfo>> drivers(){
        DataSource.Factory<Integer, DriverInfo> allDriver = haiDao.getAllDriver();
        //allDriver.map(driverInfo -> driverInfo.getId()+"-"+driverInfo.getName());
        DriverDataSourceFactory dataSourceFactory = new DriverDataSourceFactory();

        Flowable<PagedList<DriverInfo>> pagedListFlowable = new RxPagedListBuilder<>(dataSourceFactory, 10).buildFlowable(BackpressureStrategy.LATEST);
        return pagedListFlowable;
    }
    public void doSomeTest(){
        RxHttpUtils.createApi(DriverApiService.class)
                .bannerApi("")
                .compose(Transformer.<DriverInfo>switchSchedulers())
                .subscribe(new CommonObserver<DriverInfo>() {
                    @Override
                    protected String setTag() {
                        return "tag1";
                    }

                    @Override
                    protected void onError(String errorMsg) {

                    }

                    @Override
                    protected void onSuccess(DriverInfo driverInfoBaseData) {

                    }
                });
    }
}
