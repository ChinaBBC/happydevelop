package com.zx.haijixing.driver.contract;

import com.zx.haijixing.driver.entry.OrderTotalEntry;
import com.zx.haijixing.share.base.IBaseContract;

import java.util.Map;

/**
 *
 *@作者 zx
 *@创建日期 2019/7/19 17:04
 *@描述 已完成
 */
public interface CompleteContract {
    interface CompleteView extends IBaseContract.IBaseView{
        void completeSuccess(OrderTotalEntry orderTotalEntry);
    }
    interface CompletePresenter extends IBaseContract.IBasePresenter<CompleteView>{
        void completeMethod(Map<String,String> params);
    }
}
