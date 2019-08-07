package com.zx.haijixing.login.presenter;

import com.allen.library.RxHttpUtils;
import com.allen.library.interceptor.Transformer;
import com.allen.library.observer.CommonObserver;
import com.allen.library.observer.StringObserver;
import com.zx.haijixing.BuildConfig;
import com.zx.haijixing.login.contract.ITruckActivityContract;
import com.zx.haijixing.login.entry.TruckTypeEntry;
import com.zx.haijixing.share.base.BasePresenter;
import com.zx.haijixing.share.base.HaiDataObserver;
import com.zx.haijixing.share.service.LoginApiService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;

/**
 *
 *@作者 zx
 *@创建日期 2019/7/15 13:37
 *@描述 车辆信息
 */
public class TruckActivityImp extends BasePresenter<ITruckActivityContract.TruckView> implements ITruckActivityContract.TruckPresenter {
    @Override
    public void truckTypeMethod() {
        RxHttpUtils.createApi(LoginApiService.class)
                .truckTypeApi()
                .compose(Transformer.switchSchedulers())
                .subscribe(new HaiDataObserver<List<TruckTypeEntry>>() {
                    @Override
                    protected void onError(String errorMsg) {
                        mView.showFaild(errorMsg);
                    }

                    @Override
                    protected void LoginTimeOut() {

                    }

                    @Override
                    protected void onSuccess(List<TruckTypeEntry> data) {
                        mView.typeSuccess(data);
                    }
                });
    }

    @Override
    public void truckApplyMethod(Map<String, String> params) {
        RxHttpUtils.createApi(LoginApiService.class)
                .truckApplyApi(params)
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
                                mView.truckApplySuccess();
                            }else {
                                mView.showFaild(jsonObject.getString("msg"));
                            }
                        }catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    public void uploadTruckMethod(String path, int tag) {
        List<String> list = new ArrayList<>();
        list.add(path);
        Map<String,Object> params = new HashMap<>();
        params.put("files","path");
        RxHttpUtils.uploadImagesWithParams(BuildConfig.homeUrl+"upload/files/uploadImages","files",params,list)
                .compose(Transformer.<ResponseBody>switchSchedulers())
                .subscribe(new CommonObserver<ResponseBody>() {
                    @Override
                    protected void onError(String errorMsg) {
                        mView.showFaild(errorMsg);
                    }

                    @Override
                    protected void onSuccess(ResponseBody responseBody) {
                        try {
                            String string = responseBody.string().trim();
                            JSONObject jsonObject = new JSONObject(string);
                            if (jsonObject.getInt("code") == 0){
                                mView.uploadTruckSuccess(jsonObject.getString("fileName"),tag);
                            }else {
                                mView.showFaild(jsonObject.getString("msg"));
                            }
                        }catch (IOException e){

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
