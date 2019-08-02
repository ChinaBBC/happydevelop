package com.zx.haijixing.logistics.presenter;

import com.allen.library.RxHttpUtils;
import com.allen.library.interceptor.Transformer;
import com.allen.library.observer.StringObserver;
import com.zx.haijixing.logistics.contract.ClaManageContract;
import com.zx.haijixing.logistics.entry.ClassManageEntry;
import com.zx.haijixing.share.base.BasePresenter;
import com.zx.haijixing.share.base.HaiDataObserver;
import com.zx.haijixing.share.service.LogisticsApiService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 *
 *@作者 zx
 *@创建日期 2019/8/1 15:53
 *@描述 班次管理
 */
public class ClassManageImp extends BasePresenter<ClaManageContract.ClaManageView> implements ClaManageContract.ClaManagePresenter {
    @Override
    public void claManageMethod(String token, String linesId) {
        RxHttpUtils.createApi(LogisticsApiService.class)
                .classManageApi(token,linesId)
                .compose(Transformer.switchSchedulers())
                .subscribe(new HaiDataObserver<List<ClassManageEntry>>() {
                    @Override
                    protected void onError(String errorMsg) {
                        mView.showFaild(errorMsg);
                    }

                    @Override
                    protected void onSuccess(List<ClassManageEntry> data) {
                        mView.claManageSuccess(data);
                    }
                });
    }

    @Override
    public void deleteClassMethod(String token, String bkId) {
        RxHttpUtils.createApi(LogisticsApiService.class)
                .deleteClassApi(token,bkId)
                .compose(Transformer.switchSchedulers())
                .subscribe(new StringObserver() {
                    @Override
                    protected void onError(String errorMsg) {
                        mView.showFaild(errorMsg);
                    }

                    @Override
                    protected void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") == 0){
                                mView.deleteClassSuccess(jsonObject.getString("msg"));
                            }else {
                                mView.showFaild(jsonObject.getString("msg"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
