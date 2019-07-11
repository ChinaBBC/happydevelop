package com.zx.haijixing.driver.presenter;

import com.allen.library.RxHttpUtils;
import com.allen.library.interceptor.Transformer;
import com.allen.library.observer.CommonObserver;
import com.zx.haijixing.driver.contract.NewsFragmentContract;
import com.zx.haijixing.driver.entry.NewsEntry;
import com.zx.haijixing.share.base.BasePresenter;
import com.zx.haijixing.share.service.ShareApiService;

import zx.com.skytool.ZxLogUtil;

/**
 *
 *@作者 zx
 *@创建日期 2019/7/10 18:03
 *@描述 新闻资讯
 */
public class NewsFragmentImp extends BasePresenter<NewsFragmentContract.NewsFragmentView> implements NewsFragmentContract.NewsFragmentPresenter {
    @Override
    public void NewsFragmentMethod(int page) {
        RxHttpUtils.createApi(ShareApiService.class)
                .newsList(page,5)
                .compose(Transformer.<NewsEntry>switchSchedulers())
                .subscribe(new CommonObserver<NewsEntry>() {
                    @Override
                    protected void onError(String errorMsg) {
                        mView.showFaild(errorMsg);
                    }

                    @Override
                    protected void onSuccess(NewsEntry data) {
                        mView.NewsFragmentSuccess(data.getData(),data.getFileHttpWW());
                    }
                });
    }
}
