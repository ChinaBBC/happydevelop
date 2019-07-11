package com.zx.haijixing.share.pub.imp;

import com.allen.library.RxHttpUtils;
import com.allen.library.interceptor.Transformer;
import com.zx.haijixing.share.base.BasePresenter;
import com.zx.haijixing.share.base.HaiBaseData;
import com.zx.haijixing.share.base.HaiDataObserver;
import com.zx.haijixing.share.pub.contract.VersionContract;
import com.zx.haijixing.share.pub.entry.VersionEntry;
import com.zx.haijixing.share.service.ShareApiService;

/**
 *
 *@作者 zx
 *@创建日期 2019/7/11 8:54
 *@描述 版本控制
 */
public class VersionImp extends BasePresenter<VersionContract.VersionView> implements VersionContract.VersionPresenter {
    @Override
    public void versionMethod() {
        RxHttpUtils.createApi(ShareApiService.class)
                .versionApi()
                .compose(Transformer.<HaiBaseData<VersionEntry>>switchSchedulers())
                .subscribe(new HaiDataObserver<VersionEntry>() {
                    @Override
                    protected void onError(String errorMsg) {
                        mView.showFaild(errorMsg);
                    }

                    @Override
                    protected void onSuccess(VersionEntry data) {
                        mView.versionSuccess(data);
                    }
                });
    }
}
