package com.zx.haijixing.logistics.presenter;

import com.allen.library.RxHttpUtils;
import com.allen.library.interceptor.Transformer;
import com.zx.haijixing.logistics.contract.FeeContract;
import com.zx.haijixing.share.base.BasePresenter;
import com.zx.haijixing.share.base.HaiDataObserver;
import com.zx.haijixing.share.service.LogisticsApiService;

import java.util.List;
import java.util.Map;

/**
 *
 *@作者 zx
 *@创建日期 2019/8/2 14:09
 *@描述 品类价格
 */
public class FeeImp extends BasePresenter<FeeContract.FeeView> implements FeeContract.FeePresenter {
    @Override
    public void feeMethod(Map<String, String> params) {
        RxHttpUtils.createApi(LogisticsApiService.class)
                .typePriceApi(params)
                .compose(Transformer.switchSchedulers())
                .subscribe(new HaiDataObserver<List<List<String>>>() {
                    @Override
                    protected void onError(String errorMsg) {
                        mView.showFaild(errorMsg);
                    }

                    @Override
                    protected void LoginTimeOut() {
                        mView.jumpToLogin();
                    }

                    @Override
                    protected void onSuccess(List<List<String>> data) {
                        mView.feeSuccess(data);
                    }
                });
    }
}
