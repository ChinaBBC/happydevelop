package com.zx.haijixing.driver.contract;

import com.zx.haijixing.driver.entry.PrintEntry;
import com.zx.haijixing.share.base.IBaseContract;

import java.util.Map;

/**
 *
 *@作者 zx
 *@创建日期 2019/7/19 11:45
 *@描述 打单
 */
public interface PrintOrderContract {
    interface PrintOrderView extends IBaseContract.IBaseView{
        void printOrderSuccess(PrintEntry printEntry);
        void printStatusSuccess(String msg);
    }

    interface PrintOrderPresenter extends IBaseContract.IBasePresenter<PrintOrderView>{
        void printOrderMethod(String token,String id);
        void printStatusMethod(Map<String,Object> params);
    }
}
