package com.zx.haijixing.driver.contract;

import com.zx.haijixing.share.base.IBaseContract;

import java.util.Map;

/**
 *
 *@作者 zx
 *@创建日期 2019/8/6 10:42
 *@描述 全部服务
 */
public interface ServicesContract {
    interface ServicesView extends IBaseContract.IBaseView{
        void clockSuccess(String msg);
        void clockStatusSuccess(String msg);
    }

    interface ServicesPresenter extends IBaseContract.IBasePresenter<ServicesView>{
        void clockMethod(Map<String, String> params);
        void clockStatusMethod(Map<String, String> params);
    }
}
