package com.zx.haijixing.driver.presenter;

import com.allen.library.RxHttpUtils;
import com.allen.library.interceptor.Transformer;
import com.allen.library.observer.CommonObserver;
import com.allen.library.observer.StringObserver;
import com.zx.haijixing.BuildConfig;
import com.zx.haijixing.driver.contract.PaperChangeContract;
import com.zx.haijixing.share.base.BasePresenter;
import com.zx.haijixing.share.service.DriverApiService;

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
 *@创建日期 2019/7/24 16:16
 *@描述 修改车辆信息
 */
public class PaperChangeImp extends BasePresenter<PaperChangeContract.PaperChangeView> implements PaperChangeContract.PaperChangePresenter {
    @Override
    public void changeDriverId(String token, String driverA, String driverB) {
        RxHttpUtils.createApi(DriverApiService.class)
                .changeDriveIdentify(token,driverA,driverB)
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
                                mView.changeInfoSuccess(jsonObject.getString("msg"));
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
    public void changeTruckInfo(Map<String,Object> params) {
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
                                mView.changeInfoSuccess(jsonObject.getString("msg"));
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
    public void uploadImg(String path, int tag) {
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
                            JSONObject jsonObject = new JSONObject(responseBody.string());
                            if (jsonObject.getInt("code") == 0){
                                mView.uploadImgSuccess(jsonObject.getString("fileName"),tag);
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
