package com.zx.haijixing.driver.contract;

import com.zx.haijixing.driver.entry.BannerEntry;
import com.zx.haijixing.driver.entry.NewsEntry;
import com.zx.haijixing.share.base.IBaseContract;
import com.zx.haijixing.share.pub.entry.NotifyEntry;

import java.util.List;
import java.util.Map;

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
        void workStatusSuccess(String msg);
        void notifySuccess(List<NotifyEntry> notifyEntries);
    }

    interface IndexPresenter extends IBaseContract.IBasePresenter<IndexView>{
        void newsDataMethod(Map<String, String> params);
        void newsDataBanner(Map<String, String> params);
        void workMethod(Map<String, String> params);
        void workStatusMethod(Map<String, String> params);
        void weatherMethod(String city);
        void notifyMethod(Map<String, String> params);
    }
}
