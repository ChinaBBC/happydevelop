package com.zx.haijixing.driver.presenter;

import com.allen.library.RxHttpUtils;
import com.allen.library.interceptor.Transformer;
import com.allen.library.observer.CommonObserver;
import com.allen.library.observer.StringObserver;
import com.zx.haijixing.driver.contract.TruckChangeContract;
import com.zx.haijixing.driver.entry.TruckDetailEntry;
import com.zx.haijixing.driver.entry.TruckEntry;
import com.zx.haijixing.share.base.BasePresenter;
import com.zx.haijixing.share.base.HaiDataObserver;
import com.zx.haijixing.share.service.DriverApiService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 *
 *@作者 zx
 *@创建日期 2019/7/23 16:46
 *@描述 车辆信息修改
 */
public class TruckChangeImp extends BasePresenter<TruckChangeContract.TruckChangeView> implements TruckChangeContract.TruckChangePresenter {
    @Override
    public void truckInfoMethod(String id) {
        RxHttpUtils.createApi(DriverApiService.class)
                .queryTruckDetailApi(id)
                .compose(Transformer.switchSchedulers())
                .subscribe(new CommonObserver<TruckDetailEntry>() {
                    @Override
                    protected void onError(String errorMsg) {
                        mView.showFaild(errorMsg);
                    }

                    @Override
                    protected void onSuccess(TruckDetailEntry truckDetailEntry) {
                        if (truckDetailEntry.getCode() == 0){
                            mView.truckInfoSuccess(truckDetailEntry.getData(),truckDetailEntry.getFileHttpWW());
                        }else if (truckDetailEntry.getCode() == 1001){
                            mView.jumpToLogin();
                        }else {
                            mView.showFaild(truckDetailEntry.getMsg());
                        }
                    }
                });
    }

    @Override
    public void driverIdentifyMethod(String token) {
        RxHttpUtils.createApi(DriverApiService.class)
                .queryDriverCardApi(token)
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
                                JSONObject object = jsonObject.getJSONObject("data");
                                mView.driverIdentifySuccess(object.getString("DRIVERIMAGEFRONTS"),object.getString("DRIVERIMAGEBACKS"));
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
    public void changeTimeMethod(Map<String, Object> params) {
        RxHttpUtils.createApi(DriverApiService.class)
                .changeTruckApi(params)
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
                                mView.changeTimeSuccess(jsonObject.getString("msg"));
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
