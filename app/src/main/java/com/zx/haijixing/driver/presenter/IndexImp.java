package com.zx.haijixing.driver.presenter;

import com.allen.library.RxHttpUtils;
import com.allen.library.interceptor.Transformer;
import com.allen.library.observer.CommonObserver;
import com.allen.library.observer.StringObserver;
import com.zx.haijixing.driver.contract.IIndexContract;
import com.zx.haijixing.driver.entry.BannerEntry;
import com.zx.haijixing.driver.entry.NewsEntry;
import com.zx.haijixing.share.base.BasePresenter;
import com.zx.haijixing.share.service.DriverApiService;
import com.zx.haijixing.share.service.ShareApiService;

import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 *@作者 zx
 *@创建日期 2019/7/10 14:16
 *@描述 首页
 */
public class IndexImp extends BasePresenter<IIndexContract.IndexView> implements IIndexContract.IndexPresenter {
    @Override
    public void newsDataMethod(int page) {
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
                        mView.newsDataSuccess(data.getData(),data.getFileHttpWW());
                    }
                });
    }

    @Override
    public void newsDataBanner() {
        RxHttpUtils.createApi(ShareApiService.class)
                .bannerApi()
                .compose(Transformer.<BannerEntry>switchSchedulers())
                .subscribe(new CommonObserver<BannerEntry>() {
                    @Override
                    protected void onError(String errorMsg) {
                        mView.showFaild(errorMsg);
                    }

                    @Override
                    protected void onSuccess(BannerEntry data) {
                        mView.newDataBannerSuccess(data.getData(),data.getFileHttpWW());
                    }
                });
    }

    @Override
    public void workMethod(String token) {
        RxHttpUtils.createApi(DriverApiService.class)
                .workApi(token)
                .compose(Transformer.switchSchedulers())
                .subscribe(new StringObserver() {
                    @Override
                    protected void onError(String errorMsg) {
                        mView.showFaild(errorMsg);
                    }

                    @Override
                    protected void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") == 0){
                                mView.workSuccess(jsonObject.getString("msg"));
                            }else {
                                mView.showFaild(jsonObject.getString("msg"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
