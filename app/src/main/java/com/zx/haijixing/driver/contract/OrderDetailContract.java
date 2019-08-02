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
    }
    interface OrderDetailPresenter extends IBaseContract.IBasePresenter<OrderDetailView>{
        void orderDetailMethod(Map<String,Object> params);
        void goodTypePriceMethod(String token,String lineId);
        void changeOrderMethod(Map<String,Object> params);
    }
}
