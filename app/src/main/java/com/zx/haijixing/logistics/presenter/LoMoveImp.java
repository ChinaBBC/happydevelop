package com.zx.haijixing.logistics.presenter;

import com.allen.library.RxHttpUtils;
import com.allen.library.interceptor.Transformer;
import com.allen.library.observer.StringObserver;
import com.zx.haijixing.logistics.contract.LoMoveContract;
import com.zx.haijixing.logistics.entry.LinesClassEntry;
import com.zx.haijixing.share.base.BasePresenter;
import com.zx.haijixing.share.base.HaiDataObserver;
import com.zx.haijixing.share.service.LogisticsApiService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;
/**
 *
 *@作者 zx
 *@创建日期 2019/8/1 14:17
 *@描述 物流动态
 */
public class LoMoveImp extends BasePresenter<LoMoveContract.LoMoveView> implements LoMoveContract.LoMovePresenter {
    @Override
    public void loMoveMethod(Map<String, String> params) {
        RxHttpUtils.createApi(LogisticsApiService.class)
                .allLinesApi(params)
                .compose(Transformer.switchSchedulers())
                .subscribe(new HaiDataObserver<List<LinesClassEntry>>() {
                    @Override
                    protected void onError(String errorMsg) {
                        mView.showFaild(errorMsg);
                    }

                    @Override
                    protected void LoginTimeOut() {
                        mView.jumpToLogin();
                    }

                    @Override
                    protected void onSuccess(List<LinesClassEntry> data) {
                        mView.loMoveSuccess(data);
                    }
                });
    }

    @Override
    public void countAllMethod(Map<String, String> params) {
        RxHttpUtils.createApi(LogisticsApiService.class)
                .countAllApi(params)
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
                                JSONObject data1 = jsonObject.getJSONObject("data");
                                mView.countAllSuccess(data1.getString("allCount"),data1.getString("toStartCount"),
                                        data1.getString("deliveryCount"),data1.getString("countDone"));
                            }else if (jsonObject.getInt("code") == 1001){
                                mView.jumpToLogin();
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
