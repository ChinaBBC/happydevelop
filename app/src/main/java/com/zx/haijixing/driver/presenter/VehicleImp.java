package com.zx.haijixing.driver.presenter;

import com.allen.library.RxHttpUtils;
import com.allen.library.interceptor.Transformer;
import com.zx.haijixing.driver.contract.VehicleContract;
import com.zx.haijixing.driver.entry.TruckEntry;
import com.zx.haijixing.share.base.BasePresenter;
import com.zx.haijixing.share.base.HaiDataObserver;
import com.zx.haijixing.share.service.DriverApiService;

import java.util.List;

/**
 *
 *@作者 zx
 *@创建日期 2019/7/23 16:33
 *@描述 车辆管理
 */
public class VehicleImp extends BasePresenter<VehicleContract.VehicleView> implements VehicleContract.VehiclePresenter {
    @Override
    public void vehicleMethod(String token) {
        RxHttpUtils.createApi(DriverApiService.class)
                .queryTruckApi(token)
                .compose(Transformer.switchSchedulers())
                .subscribe(new HaiDataObserver<List<TruckEntry>>() {
                    @Override
                    protected void onError(String errorMsg) {
                        mView.showFaild(errorMsg);
                    }

                    @Override
                    protected void LoginTimeOut() {
                        mView.jumpToLogin();
                    }

                    @Override
                    protected void onSuccess(List<TruckEntry> data) {
                        mView.vehicleSuccess(data);
                    }
                });
    }
}
