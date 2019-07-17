package com.zx.haijixing.login.presenter;

import com.allen.library.RxHttpUtils;
import com.allen.library.interceptor.Transformer;
import com.allen.library.observer.StringObserver;
import com.zx.haijixing.login.contract.ITruckActivityContract;
import com.zx.haijixing.login.entry.TruckTypeEntry;
import com.zx.haijixing.share.base.BasePresenter;
import com.zx.haijixing.share.base.HaiDataObserver;
import com.zx.haijixing.share.service.LoginApiService;

import java.util.List;
import java.util.Map;

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
                        mView.truckApplySuccess();
                    }
                });
    }
}