package com.zx.haijixing.driver.presenter;

import com.allen.library.RxHttpUtils;
import com.allen.library.interceptor.Transformer;
import com.allen.library.observer.StringObserver;
import com.zx.haijixing.driver.contract.PrintOrderContract;
import com.zx.haijixing.driver.entry.PrintEntry;
import com.zx.haijixing.share.base.BasePresenter;
import com.zx.haijixing.share.base.HaiDataObserver;
import com.zx.haijixing.share.service.DriverApiService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 *
 *@作者 zx
 *@创建日期 2019/7/19 11:49
 *@描述 打单
 */
public class PrintImp extends BasePresenter<PrintOrderContract.PrintOrderView> implements PrintOrderContract.PrintOrderPresenter {
    @Override
    public void printOrderMethod(String token, String id) {
        RxHttpUtils.createApi(DriverApiService.class)
                .printOrderApi(token,id)
                .compose(Transformer.switchSchedulers())
                .subscribe(new HaiDataObserver<PrintEntry>() {
                    @Override
                    protected void onError(String errorMsg) {
                        mView.showFaild(errorMsg);
                    }

                    @Override
                    protected void onSuccess(PrintEntry data) {
                        mView.printOrderSuccess(data);
                    }
                });
    }

    @Override
    public void printStatusMethod(Map<String, Object> params) {
        RxHttpUtils.createApi(DriverApiService.class)
                .changePrint(params)
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
                                mView.printStatusSuccess(jsonObject.getString("msg"));
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
