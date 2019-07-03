package com.zx.haijixing.driver.model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.paging.PagedList;
import android.arch.paging.RxPagedListBuilder;
import android.support.annotation.NonNull;

import com.zx.haijixing.driver.entry.NewsEntry;
import com.zx.haijixing.driver.factory.IndexDataSourceFactory;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;

/**
 *
 *@作者 zx
 *@创建日期 2019/7/3 15:11
 *@描述 首页
 */
public class IndexViewModel extends AndroidViewModel {

    public IndexViewModel(@NonNull Application application) {
        super(application);
    }

    public Flowable<PagedList<NewsEntry>> newsMethod(){
        IndexDataSourceFactory dataSourceFactory = new IndexDataSourceFactory();
        Flowable<PagedList<NewsEntry>> pagedListFlowable = new RxPagedListBuilder<>(dataSourceFactory, 10).buildFlowable(BackpressureStrategy.LATEST);
        return pagedListFlowable;
    }
}
