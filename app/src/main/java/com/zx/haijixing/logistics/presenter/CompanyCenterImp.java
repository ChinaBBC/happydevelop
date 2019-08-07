package com.zx.haijixing.logistics.presenter;

import com.allen.library.RxHttpUtils;
import com.allen.library.interceptor.Transformer;
import com.zx.haijixing.logistics.contract.CompanyCenterContract;
import com.zx.haijixing.logistics.entry.CompanyEntry;
import com.zx.haijixing.share.base.BasePresenter;
import com.zx.haijixing.share.base.HaiDataObserver;
import com.zx.haijixing.share.service.ShareApiService;

import java.util.List;

/**
 *
 *@作者 zx
 *@创建日期 2019/8/5 15:48
 *@描述 物流公司个人中心
 */
public class CompanyCenterImp extends BasePresenter<CompanyCenterContract.CompanyCenterView> implements CompanyCenterContract.CompanyCenterPresenter {
    @Override
    public void companyMethod(String token) {
        RxHttpUtils.createApi(ShareApiService.class)
                .companyApi(token)
                .compose(Transformer.switchSchedulers())
                .subscribe(new HaiDataObserver<List<CompanyEntry>>() {
                    @Override
                    protected void onError(String errorMsg) {
                        mView.showFaild(errorMsg);
                    }

                    @Override
                    protected void LoginTimeOut() {
                        mView.jumpToLogin();
                    }

                    @Override
                    protected void onSuccess(List<CompanyEntry> data) {
                        mView.companySuccess(data);
                    }
                });
    }
}
