package com.zx.haijixing.driver.presenter;

import com.allen.library.RxHttpUtils;
import com.allen.library.interceptor.Transformer;
import com.zx.haijixing.driver.contract.CancelContract;
import com.zx.haijixing.driver.entry.OrderTotalEntry;
import com.zx.haijixing.share.base.BasePresenter;
import com.zx.haijixing.share.base.HaiDataObserver;
import com.zx.haijixing.share.service.DriverApiService;

import java.util.Map;
/**
 *
 *@作者 zx
 *@创建日期 2019/7/19 17:08
 *@描述 已取消
 */
public class CancelImp extends BasePresenter<CancelContract.CancelView> implements CancelContract.CancelPresenter {
    @Override
    public void cancelMethod(Map<String, Object> params) {
        RxHttpUtils.createApi(DriverApiService.class)
                .orderLists(params)
                .compose(Transformer.switchSchedulers())
                .subscribe(new HaiDataObserver<OrderTotalEntry>() {
                    @Override
                    protected void onError(String errorMsg) {
                        mView.showFaild(errorMsg);
                    }

                    @Override
                    protected void onSuccess(OrderTotalEntry data) {
                        mView.cancelSuccess(data);
                    }
                });
    }
}
