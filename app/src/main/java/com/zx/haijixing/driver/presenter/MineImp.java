package com.zx.haijixing.driver.presenter;

import com.allen.library.RxHttpUtils;
import com.allen.library.interceptor.Transformer;
import com.allen.library.observer.StringObserver;
import com.zx.haijixing.driver.contract.MineContract;
import com.zx.haijixing.share.base.BasePresenter;
import com.zx.haijixing.share.service.DriverApiService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 *
 *@作者 zx
 *@创建日期 2019/8/14 17:44
 *@描述 我的
 */
public class MineImp extends BasePresenter<MineContract.MineView> implements MineContract.MinePresenter {
    @Override
    public void servicePhoneMethod(Map<String, String> params) {
        RxHttpUtils.createApi(DriverApiService.class)
                .servicePhoneApi(params)
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
                                mView.servicePhoneSuccess(jsonObject.getString("data"));
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
