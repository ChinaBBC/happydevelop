package com.zx.haijixing.logistics.presenter;

import com.allen.library.RxHttpUtils;
import com.allen.library.interceptor.Transformer;
import com.zx.haijixing.logistics.contract.DriverWordContract;
import com.zx.haijixing.logistics.entry.DriverWordEntry;
import com.zx.haijixing.share.base.BasePresenter;
import com.zx.haijixing.share.base.HaiDataObserver;
import com.zx.haijixing.share.service.ShareApiService;

import java.util.List;
import java.util.Map;

/**
 *
 *@作者 zx
 *@创建日期 2019/8/5 19:58
 *@描述 司机评价
 */
public class DriverWordImp extends BasePresenter<DriverWordContract.DriverWordView> implements DriverWordContract.DriverWordPresenter {
    @Override
    public void driverWordMethod(Map<String, String> params) {
        RxHttpUtils.createApi(ShareApiService.class)
                .driverWordApi(params)
                .compose(Transformer.switchSchedulers())
                .subscribe(new HaiDataObserver<List<DriverWordEntry>>() {
                    @Override
                    protected void onError(String errorMsg) {
                        mView.showFaild(errorMsg);
                    }

                    @Override
                    protected void LoginTimeOut() {
                        mView.jumpToLogin();
                    }

                    @Override
                    protected void onSuccess(List<DriverWordEntry> data) {
                        mView.driverWordSuccess(data);
                    }
                });
    }
}
