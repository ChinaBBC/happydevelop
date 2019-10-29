package com.zx.haijixing.share.pub.imp;

import com.allen.library.RxHttpUtils;
import com.allen.library.interceptor.Transformer;
import com.allen.library.observer.StringObserver;
import com.zx.haijixing.share.base.BasePresenter;
import com.zx.haijixing.share.base.HaiBaseData;
import com.zx.haijixing.share.base.HaiDataObserver;
import com.zx.haijixing.share.pub.contract.VersionContract;
import com.zx.haijixing.share.pub.entry.VersionEntry;
import com.zx.haijixing.share.service.ShareApiService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import io.reactivex.android.schedulers.AndroidSchedulers;

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
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HaiDataObserver<VersionEntry>() {
                    @Override
                    protected void onError(String errorMsg) {
                        mView.showFaild(errorMsg);
                    }

                    @Override
                    protected void LoginTimeOut() {

                    }

                    @Override
                    protected void onSuccess(VersionEntry data) {
                        mView.versionSuccess(data);
                    }
                });
    }

    @Override
    public void payWayShowMethod() {
        RxHttpUtils.createApi(ShareApiService.class)
                .payWayShowApi()
                .compose(Transformer.<String>switchSchedulers())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new StringObserver() {
                    @Override
                    protected void onError(String errorMsg) {
                        mView.showFaild(errorMsg);
                    }

                    @Override
                    protected void onSuccess(String data) {
                        try {
                            JSONObject object = new JSONObject(data);
                            if (object.getInt("code") == 0){
                                JSONObject jsonObject = object.getJSONObject("data");
                                mView.payWayShowSuccess(jsonObject.getString("onlinePay"),jsonObject.getString("unlinePay"));
                            }else {
                                mView.showFaild(object.getString("msg"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
