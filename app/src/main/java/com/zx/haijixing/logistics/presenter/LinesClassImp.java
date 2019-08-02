package com.zx.haijixing.logistics.presenter;

import com.allen.library.RxHttpUtils;
import com.allen.library.interceptor.Transformer;
import com.allen.library.observer.StringObserver;
import com.zx.haijixing.logistics.contract.LinesClassContract;
import com.zx.haijixing.logistics.entry.LinesClassEntry;
import com.zx.haijixing.share.base.BasePresenter;
import com.zx.haijixing.share.base.HaiDataObserver;
import com.zx.haijixing.share.service.DriverApiService;
import com.zx.haijixing.share.service.LogisticsApiService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;
/**
 *
 *@作者 zx
 *@创建日期 2019/8/1 10:39
 *@描述 派单线路班次
 */
public class LinesClassImp extends BasePresenter<LinesClassContract.LinesClassView> implements LinesClassContract.LinesClassPresenter {
    @Override
    public void linesClassMethod(Map<String, Object> params) {
        RxHttpUtils.createApi(LogisticsApiService.class)
                .linesClassApi(params)
                .compose(Transformer.switchSchedulers())
                .subscribe(new HaiDataObserver<List<LinesClassEntry>>() {
                    @Override
                    protected void onError(String errorMsg) {
                        mView.showFaild(errorMsg);
                    }

                    @Override
                    protected void onSuccess(List<LinesClassEntry> data) {
                        mView.linesClassSuccess(data);
                    }
                });
    }

    @Override
    public void allotOrderMethod(Map<String, Object> params) {
        RxHttpUtils.createApi(LogisticsApiService.class)
                .allotOrderApi(params)
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
                                mView.allotOrderSuccess(jsonObject.getString("msg"));
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
