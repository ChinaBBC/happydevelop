package com.zx.haijixing.driver.presenter;

import com.allen.library.RxHttpUtils;
import com.allen.library.interceptor.Transformer;
import com.zx.haijixing.driver.contract.CompleteContract;
import com.zx.haijixing.driver.entry.OrderTotalEntry;
import com.zx.haijixing.share.base.BasePresenter;
import com.zx.haijixing.share.base.HaiDataObserver;
import com.zx.haijixing.share.service.DriverApiService;

import java.util.Map;
/**
 *
 *@作者 zx
 *@创建日期 2019/7/19 17:08
 *@描述 已完成
 */
public class CompleteImp extends BasePresenter<CompleteContract.CompleteView> implements CompleteContract.CompletePresenter {
    @Override
    public void completeMethod(Map<String, String> params) {
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
                        mView.completeSuccess(data);
                    }
                });
    }
}
