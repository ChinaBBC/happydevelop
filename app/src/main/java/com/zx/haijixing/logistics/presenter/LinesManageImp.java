package com.zx.haijixing.logistics.presenter;

import com.allen.library.RxHttpUtils;
import com.allen.library.interceptor.Transformer;
import com.zx.haijixing.logistics.contract.LinesMangeContract;
import com.zx.haijixing.logistics.entry.LinesManageEntry;
import com.zx.haijixing.share.base.BasePresenter;
import com.zx.haijixing.share.base.HaiDataObserver;
import com.zx.haijixing.share.service.LogisticsApiService;

import java.util.List;
import java.util.Map;
/**
 *
 *@作者 zx
 *@创建日期 2019/8/1 15:09
 *@描述 线路管理
 */
public class LinesManageImp extends BasePresenter<LinesMangeContract.LinesManageView> implements LinesMangeContract.LinesManagePresenter {
    @Override
    public void linesManageMethod(Map<String, Object> params) {
        RxHttpUtils.createApi(LogisticsApiService.class)
                .linesManageApi(params)
                .compose(Transformer.switchSchedulers())
                .subscribe(new HaiDataObserver<List<LinesManageEntry>>() {
                    @Override
                    protected void onError(String errorMsg) {
                        mView.showFaild(errorMsg);
                    }

                    @Override
                    protected void onSuccess(List<LinesManageEntry> data) {
                        mView.linesManageSuccess(data);
                    }
                });
    }
}
