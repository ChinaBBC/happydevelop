package com.zx.haijixing.logistics.presenter;

import com.allen.library.RxHttpUtils;
import com.allen.library.interceptor.Transformer;
import com.allen.library.observer.StringObserver;
import com.zx.haijixing.logistics.contract.AddClassContract;
import com.zx.haijixing.logistics.entry.DriverEntry;
import com.zx.haijixing.logistics.entry.TruckInfoEntry;
import com.zx.haijixing.share.base.BasePresenter;
import com.zx.haijixing.share.base.HaiDataObserver;
import com.zx.haijixing.share.service.LogisticsApiService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

/**
 *
 *@作者 zx
 *@创建日期 2019/8/1 19:21
 *@描述 添加班次
 */
public class AddClassImp extends BasePresenter<AddClassContract.AddClassView> implements AddClassContract.AddClassPresenter {
    @Override
    public void addClassMethod(Map<String, String> params) {
        RxHttpUtils.createApi(LogisticsApiService.class)
                .addClassApi(params)
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
                                mView.addClassSuccess(jsonObject.getString("msg"));
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
    public void editorClassMethod(Map<String, String> params) {
        RxHttpUtils.createApi(LogisticsApiService.class)
                .editorClassApi(params)
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
                                mView.editorClassSuccess(jsonObject.getString("msg"));
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
    public void driverPhoneMethod(Map<String, String> params) {
        RxHttpUtils.createApi(LogisticsApiService.class)
                .linesDriverApi(params)
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
                        mView.driverPhoneSuccess(data);
                    }
                });
    }

    @Override
    public void truckNumMethod(Map<String, String> params) {
        RxHttpUtils.createApi(LogisticsApiService.class)
                .driverTruckApi(params)
                .compose(Transformer.switchSchedulers())
                .subscribe(new HaiDataObserver<List<TruckInfoEntry>>() {
                    @Override
                    protected void onError(String errorMsg) {
                        mView.showFaild(errorMsg);
                    }

                    @Override
                    protected void LoginTimeOut() {
                        mView.jumpToLogin();
                    }

                    @Override
                    protected void onSuccess(List<TruckInfoEntry> data) {
                        mView.truckNumSuccess(data);
                    }
                });
    }

}
