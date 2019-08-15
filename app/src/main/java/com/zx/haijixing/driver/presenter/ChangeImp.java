package com.zx.haijixing.driver.presenter;

import com.allen.library.RxHttpUtils;
import com.allen.library.interceptor.Transformer;
import com.allen.library.observer.CommonObserver;
import com.allen.library.observer.StringObserver;
import com.zx.haijixing.BuildConfig;
import com.zx.haijixing.driver.contract.ChangeContract;
import com.zx.haijixing.share.OtherConstants;
import com.zx.haijixing.share.base.BasePresenter;
import com.zx.haijixing.share.service.LoginApiService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import zx.com.skytool.ZxLogUtil;

/**
 *
 *@作者 zx
 *@创建日期 2019/7/23 11:46
 *@描述 修改信息
 */
public class ChangeImp extends BasePresenter<ChangeContract.ChangeView> implements ChangeContract.ChangePresenter {
    @Override
    public void changeMethod(String path) {
        List<String> list = new ArrayList<>();
        list.add(path);
        Map<String,Object> params = new HashMap<>();
        params.put("files","path");
        mView.showLoading();
        RxHttpUtils.uploadImagesWithParams(BuildConfig.homeUrl+OtherConstants.UPLOAD_PATH,"files",params,list)
                .compose(Transformer.<ResponseBody>switchSchedulers())
                .subscribe(new CommonObserver<ResponseBody>() {
                    @Override
                    protected void onError(String errorMsg) {
                        mView.showFaild(errorMsg);
                    }

                    @Override
                    protected void onSuccess(ResponseBody responseBody) {
                        mView.hideLoading();
                        try {
                            String string = responseBody.string().trim();
                            ZxLogUtil.logError("<<<<<<<<<<<<<<"+ string);
                            JSONObject jsonObject = new JSONObject(string);
                            if (jsonObject.getInt("code") == 0){
                                mView.changeSuccess(jsonObject.getString("fileName"),jsonObject.getString("fastdfsProfix"));
                            }else if (jsonObject.getInt("code") == 1001){
                                mView.jumpToLogin();
                            }else {
                                mView.showFaild(jsonObject.getString("msg"));
                            }
                        }catch (IOException e){
                            ZxLogUtil.logError("<<<<<<IOException<<<<<<<<"+e.getMessage());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    public void changeHeadImgMethod(Map<String, String> params) {
        RxHttpUtils.createApi(LoginApiService.class)
                .changeUserHead(params)
                .compose(Transformer.<String>switchSchedulers())
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
                                mView.changeHeadSuccess(jsonObject.getString("msg"));
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
