package com.zx.haijixing.driver.contract;

import com.zx.haijixing.driver.entry.GoodsTypePriceEntry;
import com.zx.haijixing.driver.entry.OrderDetailEntry;
import com.zx.haijixing.share.base.IBaseContract;

import java.util.Map;

/**
 *
 *@作者 zx
 *@创建日期 2019/7/18 20:42
 *@描述 订单详情
 */
public interface OrderDetailContract {
    interface OrderDetailView extends IBaseContract.IBaseView{
        void orderDetailSuccess(OrderDetailEntry orderDetailEntry);
        void goodTypePriceSuccess(GoodsTypePriceEntry goodsTypePriceEntry);
        void changeOrderMethodSuccess(String msg);
        void sureMoneySuccess(String msg);
        void receiveOrderSuccess(String msg);
        void completeSuccess(String msg);
        void surePaySuccess(String msg);
        void changePriceSuccess(String msg);
    }
    interface OrderDetailPresenter extends IBaseContract.IBasePresenter<OrderDetailView>{
        void orderDetailMethod(Map<String,String> params);
        void goodTypePriceMethod(Map<String, String> params);
        void changeOrderMethod(Map<String,String> params);
        void sureMoneyMethod(Map<String,String> params);
        void receiveOrderMethod(Map<String,String> params);
        void completeMethod(Map<String,String> params);
        void surePayMethod(Map<String,String> params);
        void changePriceMethod(Map<String,String> params);
    }
}
