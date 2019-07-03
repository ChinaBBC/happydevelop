package com.zx.haijixing.driver.factory;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;

import com.zx.haijixing.driver.entry.NewsEntry;
import com.zx.haijixing.driver.source.IndexDataSource;

/**
 *
 *@作者 zx
 *@创建日期 2019/7/3 15:08
 *@描述 首页
 */
public class IndexDataSourceFactory extends DataSource.Factory<Long,NewsEntry> {
    private MutableLiveData<IndexDataSource> sourceMutableLiveData = new MutableLiveData<>();

    @Override
    public DataSource<Long, NewsEntry> create() {
        IndexDataSource indexDataSource = new IndexDataSource();
        sourceMutableLiveData.postValue(indexDataSource);
        return indexDataSource;
    }
}
