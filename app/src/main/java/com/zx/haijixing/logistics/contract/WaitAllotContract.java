package com.zx.haijixing.logistics.contract;

import com.zx.haijixing.driver.entry.OrderTotalEntry;
import com.zx.haijixing.share.base.IBaseContract;

import java.util.Map;

/**
 *
 *@作者 zx
 *@创建日期 2019/7/31 20:00
 *@描述 待派单
 */
public interface WaitAllotContract {
    interface WaitAllotView extends IBaseContract.IBaseView{
        void waitAllotSuccess(OrderTotalEntry orderTotalEntry);
    }

    interface WaitAllotPresenter extends IBaseContract.IBasePresenter<WaitAllotView>{
        void waitAllotMethod(Map<String,String> params);
    }
}
