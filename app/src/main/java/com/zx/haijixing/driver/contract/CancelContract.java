package com.zx.haijixing.driver.contract;

import com.zx.haijixing.driver.entry.OrderTotalEntry;
import com.zx.haijixing.share.base.IBaseContract;

import java.util.Map;

/**
 *
 *@作者 zx
 *@创建日期 2019/7/19 17:04
 *@描述 已取消
 */
public interface CancelContract {
    interface CancelView extends IBaseContract.IBaseView{
        void cancelSuccess(OrderTotalEntry orderTotalEntry);
    }
    interface CancelPresenter extends IBaseContract.IBasePresenter<CancelView>{
        void cancelMethod(Map<String,Object> params);
    }
}
