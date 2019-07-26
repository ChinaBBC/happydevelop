package com.zx.haijixing.driver.contract;

import com.zx.haijixing.driver.entry.OrderTotalEntry;
import com.zx.haijixing.share.base.IBaseContract;

import java.util.Map;
/**
 *
 *@作者 zx
 *@创建日期 2019/7/19 10:48
 *@描述 配送中
 */
public interface SendingContract {
    interface SendingView extends IBaseContract.IBaseView{
        void sendingSuccess(OrderTotalEntry orderTotalEntry);
        void completeSuccess(String msg);
        void surePaySuccess(String msg);
        void changePriceSuccess(String msg);
    }

    interface SendingPresenter extends IBaseContract.IBasePresenter<SendingView>{
        void sendingMethod(Map<String,Object> params);
        void completeMethod(Map<String,Object> params);
        void surePayMethod(Map<String,Object> params);
        void changePriceMethod(Map<String,Object> params);
    }
}
