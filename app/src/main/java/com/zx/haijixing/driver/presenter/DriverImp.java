package com.zx.haijixing.driver.presenter;

import com.allen.library.RxHttpUtils;
import com.allen.library.interceptor.Transformer;
import com.allen.library.observer.StringObserver;
import com.zx.haijixing.driver.contract.DriverContract;
import com.zx.haijixing.driver.entry.DriverClassEntry;
import com.zx.haijixing.share.OtherConstants;
import com.zx.haijixing.share.base.BasePresenter;
import com.zx.haijixing.share.base.HaiDataObserver;
import com.zx.haijixing.share.service.DriverApiService;

import java.util.List;
import java.util.Map;

/**
 *
 *@作者 zx
 *@创建日期 2019/7/22 14:04
 *@描述 司机端主页
 */
public class DriverImp extends BasePresenter<DriverContract.DriverView> implements DriverContract.DriverPresenter {

    @Override
    public void driverMethod(Map<String, String> params) {
        RxHttpUtils.createApi(DriverApiService.class)
                .uploadLocate(params)
                .compose(Transformer.switchSchedulers())
                .subscribe(new StringObserver() {
                    @Override
                    protected String setTag() {
                        return OtherConstants.CANCEL_REQUEST;
                    }

                    @Override
                    protected void onError(String errorMsg) {
                        mView.showFaild(errorMsg);
                    }

                    @Override
                    protected void onSuccess(String data) {
                        mView.driverSuccess(data);
                    }
                });
    }
}
