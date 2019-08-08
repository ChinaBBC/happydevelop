package com.zx.haijixing.driver.contract;

import com.zx.haijixing.driver.entry.DriverClassEntry;
import com.zx.haijixing.driver.entry.OrderTotalEntry;
import com.zx.haijixing.share.base.IBaseContract;

import java.util.List;
import java.util.Map;

/**
 *
 *@作者 zx
 *@创建日期 2019/7/19 10:40
 *@描述 待出发
 */
public interface SendContract {
    interface SendView extends IBaseContract.IBaseView{
        void sendSuccess(OrderTotalEntry orderTotalEntry);
        void departSuccess(String msg);
        void driverClassSuccess(List<DriverClassEntry> driverClassEntries);
    }

    interface SendPresenter extends IBaseContract.IBasePresenter<SendView>{
        void sendMethod(Map<String,String> params);
        void departMethod(Map<String,String> params);
        void driverClassMethod(Map<String, String> params);
    }
}
