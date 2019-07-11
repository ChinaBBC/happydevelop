package com.zx.haijixing.login.presenter;

import com.allen.library.RxHttpUtils;
import com.allen.library.interceptor.Transformer;
import com.allen.library.observer.StringObserver;
import com.zx.haijixing.login.contract.IBaseInfoActivityContract;
import com.zx.haijixing.share.base.BasePresenter;
import com.zx.haijixing.share.service.LoginApiService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import zx.com.skytool.ZxLogUtil;

/**
 *
 *@作者 zx
 *@创建日期 2019/6/28 10:25
 *@描述 基本信息
 */
public class BaseInfoActivityImp extends BasePresenter<IBaseInfoActivityContract.BaseInfoView> implements IBaseInfoActivityContract.BaseInfoPresenter {
    @Override
    public void baseInfoMethod(Map<String,String> params) {
        RxHttpUtils.createApi(LoginApiService.class)
                .registerBase(params)
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
                                mView.baseInfoSuccess(jsonObject.getString("data"));
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
    public void baseInfoCodeMethod(String phone) {
        RxHttpUtils.createApi(LoginApiService.class)
                .sendRegisterCode(phone,2)
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
                                mView.baseInfoCodeSuccess();
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
