package com.zx.haijixing.driver.presenter;

import com.allen.library.RxHttpUtils;
import com.allen.library.interceptor.Transformer;
import com.allen.library.observer.StringObserver;
import com.zx.haijixing.driver.contract.NewsActivityContract;
import com.zx.haijixing.driver.entry.NewsEntry;
import com.zx.haijixing.share.base.BasePresenter;
import com.zx.haijixing.share.base.HaiDataObserver;
import com.zx.haijixing.share.service.ShareApiService;

import java.util.Map;


/**
 *
 *@作者 zx
 *@创建日期 2019/7/10 17:11
 *@描述 新闻详情
 */
public class NewsActivityImp extends BasePresenter<NewsActivityContract.NewDetailView> implements NewsActivityContract.NewsDetailPresenter {
    @Override
    public void detailMethod(Map<String, String> params) {
        mView.showLoading();
        RxHttpUtils.createApi(ShareApiService.class)
                .newsOne(params)
                .compose(Transformer.switchSchedulers())
                .subscribe(new HaiDataObserver<NewsEntry.NewsData>() {
                    @Override
                    protected void onError(String errorMsg) {
                        mView.showFaild(errorMsg);
                    }

                    @Override
                    protected void LoginTimeOut() {
                        mView.jumpToLogin();
                    }

                    @Override
                    protected void onSuccess(NewsEntry.NewsData data) {
                        mView.detailSuccess(data.getContent());
                        mView.hideLoading();
                    }
                });
    }

    @Override
    public void orderDetailMethod(Map<String, String> params) {
        RxHttpUtils.createApi(ShareApiService.class)
                .qrOrderApi(params)
                .compose(Transformer.switchSchedulers())
                .subscribe(new StringObserver() {
                    @Override
                    protected void onError(String errorMsg) {
                        mView.showFaild(errorMsg);
                    }

                    @Override
                    protected void onSuccess(String data) {
                        mView.orderDetail(data);
                    }
                });
    }
}
