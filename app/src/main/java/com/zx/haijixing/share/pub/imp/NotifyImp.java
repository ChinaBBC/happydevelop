package com.zx.haijixing.share.pub.imp;

import com.allen.library.RxHttpUtils;
import com.allen.library.interceptor.Transformer;
import com.zx.haijixing.share.base.BasePresenter;
import com.zx.haijixing.share.base.HaiBaseData;
import com.zx.haijixing.share.base.HaiDataObserver;
import com.zx.haijixing.share.pub.contract.NotifyContract;
import com.zx.haijixing.share.pub.entry.NotifyEntry;
import com.zx.haijixing.share.service.ShareApiService;

import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 *
 *@作者 zx
 *@创建日期 2019/7/22 15:54
 *@描述 消息中心
 */
public class NotifyImp extends BasePresenter<NotifyContract.NotifyView> implements NotifyContract.NotifyPresenter {
    @Override
    public void notifyMethod(Map<String, String> params) {
        RxHttpUtils.createApi(ShareApiService.class)
                .notifyApi(params)
                .compose(Transformer.<HaiBaseData<List<NotifyEntry>>>switchSchedulers())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HaiDataObserver<List<NotifyEntry>>() {
                    @Override
                    protected void onError(String errorMsg) {
                        mView.showFaild(errorMsg);
                    }

                    @Override
                    protected void LoginTimeOut() {
                        mView.jumpToLogin();
                    }

                    @Override
                    protected void onSuccess(List<NotifyEntry> data) {
                        mView.notifySuccess(data);
                    }
                });
    }
}
