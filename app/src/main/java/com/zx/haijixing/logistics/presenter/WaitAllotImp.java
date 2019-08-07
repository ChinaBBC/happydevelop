package com.zx.haijixing.logistics.presenter;

import com.allen.library.RxHttpUtils;
import com.allen.library.interceptor.Transformer;
import com.zx.haijixing.driver.entry.OrderTotalEntry;
import com.zx.haijixing.logistics.contract.WaitAllotContract;
import com.zx.haijixing.share.base.BasePresenter;
import com.zx.haijixing.share.base.HaiDataObserver;
import com.zx.haijixing.share.service.DriverApiService;

import java.util.Map;

/**
 *
 *@作者 zx
 *@创建日期 2019/7/31 20:04
 *@描述 待派单
 */
public class WaitAllotImp extends BasePresenter<WaitAllotContract.WaitAllotView> implements WaitAllotContract.WaitAllotPresenter {
    @Override
    public void waitAllotMethod(Map<String, Object> params) {
        RxHttpUtils.createApi(DriverApiService.class)
                .orderLists(params)
                .compose(Transformer.switchSchedulers())
                .subscribe(new HaiDataObserver<OrderTotalEntry>() {
                    @Override
                    protected void onError(String errorMsg) {
                        mView.showFaild(errorMsg);
                    }

                    @Override
                    protected void LoginTimeOut() {
                        mView.jumpToLogin();
                    }

                    @Override
                    protected void onSuccess(OrderTotalEntry data) {
                        mView.waitAllotSuccess(data);
                    }
                });
    }
}
