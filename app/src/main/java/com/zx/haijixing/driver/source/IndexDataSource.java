package com.zx.haijixing.driver.source;

import android.arch.paging.ItemKeyedDataSource;
import android.support.annotation.NonNull;

import com.zx.haijixing.driver.entry.NewsEntry;

/**
 *
 *@作者 zx
 *@创建日期 2019/7/3 15:04
 *@描述 首页
 */
public class IndexDataSource extends ItemKeyedDataSource<Long,NewsEntry> {
    @Override
    public void loadInitial(@NonNull LoadInitialParams<Long> params, @NonNull LoadInitialCallback<NewsEntry> callback) {

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Long> params, @NonNull LoadCallback<NewsEntry> callback) {

    }

    @Override
    public void loadBefore(@NonNull LoadParams<Long> params, @NonNull LoadCallback<NewsEntry> callback) {

    }

    @NonNull
    @Override
    public Long getKey(@NonNull NewsEntry item) {
        return item.getId();
    }
}
