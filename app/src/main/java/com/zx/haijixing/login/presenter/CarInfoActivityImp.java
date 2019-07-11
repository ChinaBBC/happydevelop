package com.zx.haijixing.login.presenter;

import com.allen.library.RxHttpUtils;
import com.allen.library.interceptor.Transformer;
import com.allen.library.observer.StringObserver;
import com.zx.haijixing.login.contract.ICarInfoActivityContract;
import com.zx.haijixing.share.base.BasePresenter;
import com.zx.haijixing.share.service.LoginApiService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 *
 *@作者 zx
 *@创建日期 2019/6/28 11:45
 *@描述 车辆信息
 */
public class CarInfoActivityImp extends BasePresenter<ICarInfoActivityContract.CarInfoView> implements ICarInfoActivityContract.CarInfoPresenter {
    @Override
    public void carInfoMethod(Map<String,String> params) {
        RxHttpUtils.createApi(LoginApiService.class)
                .drivingCard(params)
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
                                mView.carInfoSuccess();
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
