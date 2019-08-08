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
import java.util.Map;

/**
 *
 *@作者 zx
 *@创建日期 2019/7/24 17:26
 *@描述 评价
 */
public class EvaluateImp extends BasePresenter<EvaluateContract.EvaluateView> implements EvaluateContract.EvaluatePresenter {
    @Override
    public void totalEvaluateMethod(Map<String, String> params) {
        RxHttpUtils.createApi(DriverApiService.class)
                .queryTotalEvaluateApi(params)
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
    public void everyEvaluateMethod(Map<String, String> params) {
        RxHttpUtils.createApi(DriverApiService.class)
                .queryEveryEvaluateApi(params)
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
