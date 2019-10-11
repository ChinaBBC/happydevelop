package com.zx.haijixing.driver.contract;

import com.zx.haijixing.driver.entry.OrderTotalEntry;
import com.zx.haijixing.share.base.IBaseContract;

import java.util.Map;

/**
 *
 *@作者 zx
 *@创建日期 2019/7/18 11:30
 *@描述 订单列表
 */
public interface OrderContract {
    interface OrderView extends IBaseContract.IBaseView{
        void orderSuccess(OrderTotalEntry orderTotalEntry);
        void receiveOrderSuccess(String msg);
        void sureMoneySuccess(String msg);
    }

    interface OrderPresenter extends IBaseContract.IBasePresenter<OrderView>{
        void orderMethod(Map<String,String> params);
        void receiveOrderMethod(Map<String,String> params);
        void sureMoneyMethod(Map<String,String> params);
    }
}
