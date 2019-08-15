package com.zx.haijixing.logistics.presenter;

import com.allen.library.RxHttpUtils;
import com.allen.library.interceptor.Transformer;
import com.allen.library.observer.StringObserver;
import com.zx.haijixing.logistics.contract.BotContract;
import com.zx.haijixing.share.base.BasePresenter;
import com.zx.haijixing.share.service.LogisticsApiService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import zx.com.skytool.ZxLogUtil;

/**
 *
 *@作者 zx
 *@创建日期 2019/8/15 16:14
 *@描述 小红点
 */
public class BotImp extends BasePresenter<BotContract.BotView> implements BotContract.BotPresenter {
    @Override
    public void botMethod(Map<String, String> params) {
        RxHttpUtils.createApi(LogisticsApiService.class)
                .botApi(params)
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
                                JSONObject object = jsonObject.getJSONObject("data");
                                mView.botSuccess(object.getInt("dpdNum"),object.getInt("djdNum"),object.getInt("dcfNum"),object.getInt("pszNum"));
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
