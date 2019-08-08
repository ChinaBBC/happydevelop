package com.zx.haijixing.driver.presenter;

import com.allen.library.RxHttpUtils;
import com.allen.library.interceptor.Transformer;
import com.zx.haijixing.driver.contract.LinesContract;
import com.zx.haijixing.driver.entry.DriverClassEntry;
import com.zx.haijixing.share.base.BasePresenter;
import com.zx.haijixing.share.base.HaiDataObserver;
import com.zx.haijixing.share.service.DriverApiService;

import java.util.List;
import java.util.Map;

/**
 *
 *@作者 zx
 *@创建日期 2019/7/23 9:25
 *@描述 车辆及路线
 */
public class LineImp extends BasePresenter<LinesContract.LinesView> implements LinesContract.LinesPresenter {

    @Override
    public void linesMethod(Map<String, String> params) {
        RxHttpUtils.createApi(DriverApiService.class)
                .driverClass(params)
                .compose(Transformer.switchSchedulers())
                .subscribe(new HaiDataObserver<List<DriverClassEntry>>() {
                    @Override
                    protected void onError(String errorMsg) {
                        mView.showFaild(errorMsg);
                    }

                    @Override
                    protected void LoginTimeOut() {
                        mView.jumpToLogin();
                    }

                    @Override
                    protected void onSuccess(List<DriverClassEntry> data) {
                        mView.linesSuccess(data);
                    }
                });
    }
}
