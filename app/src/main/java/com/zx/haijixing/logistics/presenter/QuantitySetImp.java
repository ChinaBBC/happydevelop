package com.zx.haijixing.logistics.presenter;

import com.allen.library.RxHttpUtils;
import com.allen.library.interceptor.Transformer;
import com.zx.haijixing.logistics.contract.QuantitySetConnect;
import com.zx.haijixing.logistics.entry.ProductEntry;
import com.zx.haijixing.share.base.BasePresenter;
import com.zx.haijixing.share.base.HaiDataObserver;
import com.zx.haijixing.share.service.LogisticsApiService;

import java.util.List;
import java.util.Map;

/**
 *
 *@作者 zx
 *@创建日期 2019/10/29 9:21
 *@描述 批量管理线路
 */
public class QuantitySetImp extends BasePresenter<QuantitySetConnect.QuantityView> implements QuantitySetConnect.QuantityPresenter {

    @Override
    public void loProductMethod(Map<String, String> params) {
        RxHttpUtils.createApi(LogisticsApiService.class)
                .logisticsWaysApi(params)
                .compose(Transformer.switchSchedulers())
                .subscribe(new HaiDataObserver<List<ProductEntry>>() {
                    @Override
                    protected void onError(String errorMsg) {
                        mView.showFaild(errorMsg);
                    }

                    @Override
                    protected void LoginTimeOut() {
                        mView.jumpToLogin();
                    }

                    @Override
                    protected void onSuccess(List<ProductEntry> data) {
                        mView.loProductSuccess(data);
                    }
                });
    }
}
