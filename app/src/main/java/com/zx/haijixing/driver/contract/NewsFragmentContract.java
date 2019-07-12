package com.zx.haijixing.driver.contract;

import com.zx.haijixing.driver.entry.BannerEntry;
import com.zx.haijixing.driver.entry.NewsEntry;
import com.zx.haijixing.share.base.IBaseContract;

import java.util.List;

/**
 *
 *@作者 zx
 *@创建日期 2019/7/10 18:00
 *@描述 新闻资讯
 */
public interface NewsFragmentContract {
    interface NewsFragmentView extends IBaseContract.IBaseView{
        void newsFragmentSuccess(List<NewsEntry.NewsData> newsEntries, String base);
        void newsFragmentBannerSuccess(List<BannerEntry.BannerData> bannerDataList,String bannerStr);
    }

    interface NewsFragmentPresenter extends IBaseContract.IBasePresenter<NewsFragmentView>{
        void newsFragmentMethod(int page);
        void newsFragmentBanner();
    }
}
