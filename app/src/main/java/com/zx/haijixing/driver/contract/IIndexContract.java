package com.zx.haijixing.driver.contract;

import com.zx.haijixing.driver.entry.NewsEntry;
import com.zx.haijixing.share.base.IBaseContract;

import java.util.List;

/**
 *
 *@作者 zx
 *@创建日期 2019/7/10 14:12
 *@描述 首页
 */
public interface IIndexContract {
    interface IndexView extends IBaseContract.IBaseView{
        void newsDataSuccess(List<NewsEntry.NewsData> newsEntries,String base);
    }

    interface IndexPresenter extends IBaseContract.IBasePresenter<IndexView>{
        void newsDataMethod(int page);
    }
}
