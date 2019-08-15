package com.zx.haijixing.driver.presenter;

import com.allen.library.RxHttpUtils;
import com.allen.library.interceptor.Transformer;
import com.allen.library.observer.CommonObserver;
import com.allen.library.observer.StringObserver;
import com.zx.haijixing.driver.contract.IIndexContract;
import com.zx.haijixing.driver.entry.BannerEntry;
import com.zx.haijixing.driver.entry.NewsEntry;
import com.zx.haijixing.share.OtherConstants;
import com.zx.haijixing.share.base.BasePresenter;
import com.zx.haijixing.share.base.HaiBaseData;
import com.zx.haijixing.share.base.HaiDataObserver;
import com.zx.haijixing.share.pub.entry.NotifyEntry;
import com.zx.haijixing.share.service.DriverApiService;
import com.zx.haijixing.share.service.ManagerApiService;
import com.zx.haijixing.share.service.ShareApiService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import zx.com.skytool.ZxLogUtil;

/**
 *
 *@作者 zx
 *@创建日期 2019/7/10 14:16
 *@描述 首页
 */
public class IndexImp extends BasePresenter<IIndexContract.IndexView> implements IIndexContract.IndexPresenter {
    @Override
    public void newsDataMethod(Map<String, String> params) {
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
                        if (data.getCode() == 1001){
                            mView.jumpToLogin();
                        }else if (data.getCode() == 0){
                            mView.newsDataSuccess(data.getData(),data.getFileHttpWW());
                        }else {
                            mView.showFaild(data.getMsg());
                        }
                    }
                });
    }

    @Override
    public void newsDataBanner(Map<String, String> params) {
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
                        if (data.getCode() == 1001){
                            mView.jumpToLogin();
                        }else if (data.getCode() == 0){
                            mView.newDataBannerSuccess(data.getData(),data.getFileHttpWW());
                        }else {
                            mView.showFaild(data.getMsg());
                        }
                    }
                });
    }

    @Override
    public void workMethod(Map<String, String> params) {
        RxHttpUtils.createApi(DriverApiService.class)
                .workApi(params)
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
                            }else if (jsonObject.getInt("code") == 1001){
                                mView.jumpToLogin();
                            }else {
                                mView.showFaild(jsonObject.getString("msg"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    public void workStatusMethod(Map<String, String> params) {
        RxHttpUtils.createApi(DriverApiService.class)
                .workStatusApi(params)
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
                                mView.workStatusSuccess(jsonObject.getString("data"));
                            }else if (jsonObject.getInt("code") == 1001){
                                mView.jumpToLogin();
                            }else {
                                mView.showFaild(jsonObject.getString("msg"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    public void weatherMethod(String city) {
        RxHttpUtils.createApi("weather",OtherConstants.WEATHER_API,ManagerApiService.class)
                .weatherApi("v1",city)
                .compose(Transformer.switchSchedulers())
                .subscribe(new StringObserver() {
                    @Override
                    protected void onError(String errorMsg) {

                    }

                    @Override
                    protected void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            String city = jsonObject.getString("city");
                            JSONArray array = jsonObject.getJSONArray("data");
                            String weather = null;
                            String temp = null;
                            if (array.length()>0){
                                JSONObject object = array.getJSONObject(0);
                                weather = object.getString("wea");
                                temp = object.getString("tem1")+"/"+object.getString("tem2");
                            }
                            mView.weatherSuccess(city,weather,temp);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    public void notifyMethod(Map<String, String> params) {
        RxHttpUtils.createApi(ShareApiService.class)
                .notifyApi(params)
                .compose(Transformer.<HaiBaseData<List<NotifyEntry>>>switchSchedulers())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HaiDataObserver<List<NotifyEntry>>() {
                    @Override
                    protected void onError(String errorMsg) {
                        mView.showFaild(errorMsg);
                    }

                    @Override
                    protected void LoginTimeOut() {
                        mView.jumpToLogin();
                    }

                    @Override
                    protected void onSuccess(List<NotifyEntry> data) {
                        mView.notifySuccess(data);
                    }
                });
    }
}
