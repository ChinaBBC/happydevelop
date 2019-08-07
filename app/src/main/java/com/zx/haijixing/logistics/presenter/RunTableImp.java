package com.zx.haijixing.logistics.presenter;

import com.allen.library.RxHttpUtils;
import com.allen.library.interceptor.Transformer;
import com.zx.haijixing.logistics.contract.RunTableContact;
import com.zx.haijixing.logistics.entry.RunTableEntry;
import com.zx.haijixing.share.base.BasePresenter;
import com.zx.haijixing.share.base.HaiDataObserver;
import com.zx.haijixing.share.service.ShareApiService;

import java.util.List;
import java.util.Map;

/**
 *
 *@作者 zx
 *@创建日期 2019/8/5 17:03
 *@描述 运营总表
 */
public class RunTableImp extends BasePresenter<RunTableContact.RunTableView> implements RunTableContact.RunTablePresenter {
    @Override
    public void runTableMethod(Map<String,Object> params) {
        RxHttpUtils.createApi(ShareApiService.class)
                .runTableApi(params)
                .compose(Transformer.switchSchedulers())
                .subscribe(new HaiDataObserver<List<RunTableEntry>>() {
                    @Override
                    protected void onError(String errorMsg) {
                        mView.showFaild(errorMsg);
                    }

                    @Override
                    protected void LoginTimeOut() {
                        mView.jumpToLogin();
                    }

                    @Override
                    protected void onSuccess(List<RunTableEntry> data) {
                        mView.runTableSuccess(data);
                    }
                });
    }
}
