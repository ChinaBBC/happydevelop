package com.zx.haijixing.driver.presenter;

import com.allen.library.RxHttpUtils;
import com.allen.library.interceptor.Transformer;
import com.allen.library.observer.StringObserver;
import com.zx.haijixing.driver.contract.SendContract;
import com.zx.haijixing.driver.entry.DriverClassEntry;
import com.zx.haijixing.driver.entry.OrderTotalEntry;
import com.zx.haijixing.share.base.BasePresenter;
import com.zx.haijixing.share.base.HaiDataObserver;
import com.zx.haijixing.share.service.DriverApiService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

/**
 *
 *@作者 zx
 *@创建日期 2019/7/19 10:43
 *@描述 待出发
 */
public class SendImp extends BasePresenter<SendContract.SendView> implements SendContract.SendPresenter {
    @Override
    public void sendMethod(Map<String, String> params) {
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
                        mView.sendSuccess(data);
                    }
                });
    }

    @Override
    public void departMethod(Map<String, String> params) {
        RxHttpUtils.createApi(DriverApiService.class)
                .sendApi(params)
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
                                mView.departSuccess(jsonObject.getString("msg"));
                            }else if (jsonObject.getInt("code") == 1010){
                                mView.departSuccess("1010");
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
    public void driverClassMethod(Map<String, String> params) {
        RxHttpUtils.createApi(DriverApiService.class)
                .driverClass(params)
                .compose(Transformer.switchSchedulers())
                .subscribe(new HaiDataObserver<List<DriverClassEntry>>() {
                    @Override
                    protected void onError(String errorMsg) {
                        mView.showFaild(errorMsg);
                    }

                    @Override
                    protected void LoginTimeOut() {
                        mView.jumpToLogin();
                    }

                    @Override
                    protected void onSuccess(List<DriverClassEntry> data) {
                        mView.driverClassSuccess(data);
                    }
                });
    }
}
