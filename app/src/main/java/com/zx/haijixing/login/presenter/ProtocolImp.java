package com.zx.haijixing.login.presenter;

import com.allen.library.RxHttpUtils;
import com.allen.library.interceptor.Transformer;
import com.allen.library.observer.StringObserver;
import com.zx.haijixing.login.contract.IProtocolContract;
import com.zx.haijixing.share.base.BasePresenter;
import com.zx.haijixing.share.service.LoginApiService;

import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 *@作者 zx
 *@创建日期 2019/8/19 14:54
 *@描述 隐私协议
 */
public class ProtocolImp extends BasePresenter<IProtocolContract.ProtocolView> implements IProtocolContract.ProtocolPresenter {
    @Override
    public void protocolMethod() {
        RxHttpUtils.createApi(LoginApiService.class)
                .protocolApi()
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
                                mView.protocolSuccess(jsonObject.getString("data"));
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
    public void privateMethod() {
        RxHttpUtils.createApi(LoginApiService.class)
                .privateApi()
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
                                mView.privateSuccess(jsonObject.getString("data"));
                            }else {
                                mView.showFaild(jsonObject.getString("msg"));
                            }
                        }catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
