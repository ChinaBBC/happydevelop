package com.zx.haijixing.driver.contract;

import com.zx.haijixing.driver.entry.BannerEntry;
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
        void newDataBannerSuccess(List<BannerEntry.BannerData> bannerDataList,String bannerStr);
        void workSuccess(String msg);
        void weatherSuccess(String city,String weather,String temp);
    }

    interface IndexPresenter extends IBaseContract.IBasePresenter<IndexView>{
        void newsDataMethod(int page);
        void newsDataBanner();
        void workMethod(String token);
        void weatherMethod(String city);
    }
}
