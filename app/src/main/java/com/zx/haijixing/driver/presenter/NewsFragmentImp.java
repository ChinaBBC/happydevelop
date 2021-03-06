package com.zx.haijixing.driver.presenter;

import com.allen.library.RxHttpUtils;
import com.allen.library.interceptor.Transformer;
import com.allen.library.observer.CommonObserver;
import com.zx.haijixing.driver.contract.NewsFragmentContract;
import com.zx.haijixing.driver.entry.BannerEntry;
import com.zx.haijixing.driver.entry.NewsEntry;
import com.zx.haijixing.share.base.BasePresenter;
import com.zx.haijixing.share.service.ShareApiService;

import java.util.Map;

import zx.com.skytool.ZxLogUtil;

/**
 *
 *@作者 zx
 *@创建日期 2019/7/10 18:03
 *@描述 新闻资讯
 */
public class NewsFragmentImp extends BasePresenter<NewsFragmentContract.NewsFragmentView> implements NewsFragmentContract.NewsFragmentPresenter {
    @Override
    public void newsFragmentMethod(Map<String, String> params) {
        RxHttpUtils.createApi(ShareApiService.class)
                .newsList(params)
                .compose(Transformer.<NewsEntry>switchSchedulers())
                .subscribe(new CommonObserver<NewsEntry>() {
                    @Override
                    protected void onError(String errorMsg) {
                        mView.showFaild(errorMsg);
                    }

                    @Override
                    protected void onSuccess(NewsEntry data) {
                        switch (data.getCode()){
                            case 0:
                                mView.newsFragmentSuccess(data.getData(),data.getFileHttpWW());
                                break;
                            case 1001:
                                mView.jumpToLogin();
                                break;
                            default:
                                mView.showFaild(data.getMsg());
                                break;
                        }
                    }
                });
    }

    @Override
    public void newsFragmentBanner(Map<String, String> params) {
        RxHttpUtils.createApi(ShareApiService.class)
                .bannerApi(params)
                .compose(Transformer.<BannerEntry>switchSchedulers())
                .subscribe(new CommonObserver<BannerEntry>() {
                    @Override
                    protected void onError(String errorMsg) {
                        mView.showFaild(errorMsg);
                    }

                    @Override
                    protected void onSuccess(BannerEntry data) {
                        switch (data.getCode()){
                            case 0:
                                mView.newsFragmentBannerSuccess(data.getData(),data.getFileHttpWW());
                                break;
                            case 1001:
                                mView.jumpToLogin();
                                break;
                            default:
                                mView.showFaild(data.getMsg());
                                break;
                        }

                    }
                });
    }
}
