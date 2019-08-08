package com.zx.haijixing.logistics.presenter;

import com.allen.library.RxHttpUtils;
import com.allen.library.interceptor.Transformer;
import com.allen.library.observer.StringObserver;
import com.zx.haijixing.logistics.contract.CheckLogisticsContract;
import com.zx.haijixing.share.base.BasePresenter;
import com.zx.haijixing.share.service.DriverApiService;
import com.zx.haijixing.share.service.LogisticsApiService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 *
 *@作者 zx
 *@创建日期 2019/7/22 10:42
 *@描述 查看物流
 */
public class CheckLogisticsImp extends BasePresenter<CheckLogisticsContract.CheckLogisticView> implements CheckLogisticsContract.CheckLogisticsPresenter {
    @Override
    public void checkLogisticsMethod(Map<String, String> params) {
        RxHttpUtils.createApi(LogisticsApiService.class)
                .checkLogisticsApi(params)
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
                                JSONObject data1 = jsonObject.getJSONObject("data");
                                mView.checkLogisticsSuccess(data1.getString("startPoint"),data1.getString("endPoint"),data1.getString("nowPoint"));
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
