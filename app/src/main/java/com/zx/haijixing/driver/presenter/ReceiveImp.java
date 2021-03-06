package com.zx.haijixing.driver.presenter;

import com.allen.library.RxHttpUtils;
import com.allen.library.interceptor.Transformer;
import com.allen.library.observer.StringObserver;
import com.zx.haijixing.driver.contract.OrderContract;
import com.zx.haijixing.driver.entry.OrderTotalEntry;
import com.zx.haijixing.share.base.BasePresenter;
import com.zx.haijixing.share.base.HaiDataObserver;
import com.zx.haijixing.share.service.DriverApiService;
import com.zx.haijixing.share.service.LogisticsApiService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;
/**
 *
 *@作者 zx
 *@创建日期 2019/7/18 11:34
 *@描述 订单列表
 */
public class ReceiveImp extends BasePresenter<OrderContract.OrderView> implements OrderContract.OrderPresenter {
    @Override
    public void orderMethod(Map<String, String> params) {
        RxHttpUtils.createApi(DriverApiService.class)
                .orderLists(params)
                .compose(Transformer.switchSchedulers())
                .subscribe(new HaiDataObserver<OrderTotalEntry>() {
                    @Override
                    protected void onError(String errorMsg) {
                        mView.showFaild(errorMsg);
                    }

                    @Override
                    protected void LoginTimeOut() {
                        mView.jumpToLogin();
                    }

                    @Override
                    protected void onSuccess(OrderTotalEntry data) {
                        mView.orderSuccess(data);
                    }
                });
    }

    @Override
    public void receiveOrderMethod(Map<String, String> params) {
        mView.showLoading();
        RxHttpUtils.createApi(DriverApiService.class)
                .receiveOrders(params)
                .compose(Transformer.switchSchedulers())
                .subscribe(new StringObserver() {
                    @Override
                    protected void onError(String errorMsg) {
                        mView.showFaild(errorMsg);
                    }

                    @Override
                    protected void onSuccess(String data) {
                        mView.hideLoading();
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") == 0){
                                mView.receiveOrderSuccess(jsonObject.getString("msg"));
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
    public void sureMoneyMethod(Map<String, String> params) {
        mView.showLoading();
        RxHttpUtils.createApi(LogisticsApiService.class)
                .sureMoneyApi(params)
                .compose(Transformer.switchSchedulers())
                .subscribe(new StringObserver() {
                    @Override
                    protected void onError(String errorMsg) {
                        mView.showFaild(errorMsg);
                    }

                    @Override
                    protected void onSuccess(String data) {
                        mView.hideLoading();
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") == 0){
                                mView.sureMoneySuccess(jsonObject.getString("msg"));
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
}
