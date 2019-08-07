package com.zx.haijixing.driver.presenter;

import com.allen.library.RxHttpUtils;
import com.allen.library.interceptor.Transformer;
import com.zx.haijixing.driver.contract.EvaluateContract;
import com.zx.haijixing.driver.entry.EveryEvaluateEntry;
import com.zx.haijixing.driver.entry.TotalEvaluateEntry;
import com.zx.haijixing.share.base.BasePresenter;
import com.zx.haijixing.share.base.HaiDataObserver;
import com.zx.haijixing.share.service.DriverApiService;

import java.util.List;

/**
 *
 *@作者 zx
 *@创建日期 2019/7/24 17:26
 *@描述 评价
 */
public class EvaluateImp extends BasePresenter<EvaluateContract.EvaluateView> implements EvaluateContract.EvaluatePresenter {
    @Override
    public void totalEvaluateMethod(String token) {
        RxHttpUtils.createApi(DriverApiService.class)
                .queryTotalEvaluateApi(token)
                .compose(Transformer.switchSchedulers())
                .subscribe(new HaiDataObserver<TotalEvaluateEntry>() {
                    @Override
                    protected void onError(String errorMsg) {
                        mView.showFaild(errorMsg);
                    }

                    @Override
                    protected void LoginTimeOut() {
                        mView.jumpToLogin();
                    }

                    @Override
                    protected void onSuccess(TotalEvaluateEntry data) {
                        mView.totalEvaluateSuccess(data);
                    }
                });
    }

    @Override
    public void everyEvaluateMethod(String token, int page) {
        RxHttpUtils.createApi(DriverApiService.class)
                .queryEveryEvaluateApi(token,page,5)
                .compose(Transformer.switchSchedulers())
                .subscribe(new HaiDataObserver<List<EveryEvaluateEntry>>() {
                    @Override
                    protected void onError(String errorMsg) {
                        mView.showFaild(errorMsg);
                    }

                    @Override
                    protected void LoginTimeOut() {
                        mView.jumpToLogin();
                    }

                    @Override
                    protected void onSuccess(List<EveryEvaluateEntry> data) {
                        mView.everyEvaluateSuccess(data);
                    }
                });
    }
}
