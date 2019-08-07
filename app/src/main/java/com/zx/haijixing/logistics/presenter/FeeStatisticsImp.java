package com.zx.haijixing.logistics.presenter;

import com.allen.library.RxHttpUtils;
import com.allen.library.interceptor.Transformer;
import com.allen.library.observer.StringObserver;
import com.zx.haijixing.logistics.contract.FeeStatisticsContract;
import com.zx.haijixing.logistics.entry.DriverEntry;
import com.zx.haijixing.logistics.entry.FeeStatisticsEntry;
import com.zx.haijixing.share.base.BasePresenter;
import com.zx.haijixing.share.base.HaiDataObserver;
import com.zx.haijixing.share.service.LogisticsApiService;
import com.zx.haijixing.share.service.ShareApiService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

/**
 *
 *@作者 zx
 *@创建日期 2019/8/2 16:30
 *@描述 运费运单统计
 */
public class FeeStatisticsImp extends BasePresenter<FeeStatisticsContract.FeeStatisticsView> implements FeeStatisticsContract.FeeStatisticsPresenter {

    @Override
    public void orderStatisticsMethod(Map<String, Object> params) {
        RxHttpUtils.createApi(ShareApiService.class)
                .orderStatisticsApi(params)
                .compose(Transformer.switchSchedulers())
                .subscribe(new HaiDataObserver<FeeStatisticsEntry>() {
                    @Override
                    protected void onError(String errorMsg) {
                        mView.showFaild(errorMsg);
                    }

                    @Override
                    protected void LoginTimeOut() {
                        mView.jumpToLogin();
                    }

                    @Override
                    protected void onSuccess(FeeStatisticsEntry data) {
                        mView.orderStatisticsSuccess(data);
                    }
                });
    }

    @Override
    public void feeStatisticsMethod(Map<String, Object> params) {
        RxHttpUtils.createApi(ShareApiService.class)
                .feeStatisticsApi(params)
                .compose(Transformer.switchSchedulers())
                .subscribe(new HaiDataObserver<FeeStatisticsEntry>() {
                    @Override
                    protected void onError(String errorMsg) {
                        mView.showFaild(errorMsg);
                    }

                    @Override
                    protected void LoginTimeOut() {
                        mView.jumpToLogin();
                    }

                    @Override
                    protected void onSuccess(FeeStatisticsEntry data) {
                        mView.feeStatisticsSuccess(data);
                    }
                });
    }

    @Override
    public void todayStatisticsMethod(String token) {
        RxHttpUtils.createApi(ShareApiService.class)
                .todayDataApi(token)
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
                                JSONObject data1 = jsonObject.getJSONObject("data");
                                mView.todayStatisticsSuccess(data1.getString("countNum"),data1.getString("totalPrice"),data1.getString("toPayMoney"));
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
    public void receiveStatisticsMethod(Map<String,Object> params) {
        RxHttpUtils.createApi(ShareApiService.class)
                .feeReceivedStatisticsApi(params)
                .compose(Transformer.switchSchedulers())
                .subscribe(new HaiDataObserver<FeeStatisticsEntry>() {
                    @Override
                    protected void onError(String errorMsg) {
                        mView.showFaild(errorMsg);
                    }

                    @Override
                    protected void LoginTimeOut() {
                        mView.jumpToLogin();
                    }

                    @Override
                    protected void onSuccess(FeeStatisticsEntry data) {
                        mView.receiveStatisticsSuccess(data);
                    }
                });
    }

    @Override
    public void searchDriverMethod(String token) {
        RxHttpUtils.createApi(ShareApiService.class)
                .driverListApi(token)
                .compose(Transformer.switchSchedulers())
                .subscribe(new HaiDataObserver<List<DriverEntry>>() {
                    @Override
                    protected void onError(String errorMsg) {
                        mView.showFaild(errorMsg);
                    }

                    @Override
                    protected void LoginTimeOut() {
                        mView.jumpToLogin();
                    }

                    @Override
                    protected void onSuccess(List<DriverEntry> data) {
                        mView.searchDriverSuccess(data);
                    }
                });
    }
}
