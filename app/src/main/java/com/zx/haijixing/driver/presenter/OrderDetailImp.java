package com.zx.haijixing.driver.presenter;

import com.allen.library.RxHttpUtils;
import com.allen.library.interceptor.Transformer;
import com.allen.library.observer.CommonObserver;
import com.allen.library.observer.StringObserver;
import com.zx.haijixing.driver.contract.OrderDetailContract;
import com.zx.haijixing.driver.entry.GoodsTypePriceEntry;
import com.zx.haijixing.driver.entry.OrderDetailEntry;
import com.zx.haijixing.share.base.BasePresenter;
import com.zx.haijixing.share.base.HaiDataObserver;
import com.zx.haijixing.share.service.DriverApiService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 *
 *@作者 zx
 *@创建日期 2019/7/18 20:46
 *@描述 订单详情
 */
public class OrderDetailImp extends BasePresenter<OrderDetailContract.OrderDetailView> implements OrderDetailContract.OrderDetailPresenter {
    @Override
    public void orderDetailMethod(Map<String,String> params) {
        RxHttpUtils.createApi(DriverApiService.class)
                .orderDetails(params)
                .compose(Transformer.switchSchedulers())
                .subscribe(new HaiDataObserver<OrderDetailEntry>() {
                    @Override
                    protected void onError(String errorMsg) {
                        mView.showFaild(errorMsg);
                    }

                    @Override
                    protected void LoginTimeOut() {
                        mView.jumpToLogin();
                    }

                    @Override
                    protected void onSuccess(OrderDetailEntry data) {
                        mView.orderDetailSuccess(data);
                    }
                });
    }

    @Override
    public void goodTypePriceMethod(Map<String, String> params) {
        RxHttpUtils.createApi(DriverApiService.class)
                .queryTypePriceInfo(params)
                .compose(Transformer.switchSchedulers())
                .subscribe(new CommonObserver<GoodsTypePriceEntry>() {
                    @Override
                    protected void onError(String errorMsg) {
                        mView.showFaild(errorMsg);
                    }

                    @Override
                    protected void onSuccess(GoodsTypePriceEntry goodsTypePriceEntry) {
                        if (goodsTypePriceEntry.getCode() == 0){
                            mView.goodTypePriceSuccess(goodsTypePriceEntry);
                        }else if (goodsTypePriceEntry.getCode() == 1001){
                            mView.jumpToLogin();
                        }else {
                            mView.showFaild(goodsTypePriceEntry.getMsg());
                        }
                    }
                });
    }

    @Override
    public void changeOrderMethod(Map<String, String> params) {
        RxHttpUtils.createApi(DriverApiService.class)
                .changeOrderApi(params)
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
                                mView.changeOrderMethodSuccess(jsonObject.getString("msg"));
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
