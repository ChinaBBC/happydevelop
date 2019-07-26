package com.zx.haijixing.driver.contract;

import com.zx.haijixing.share.base.IBaseContract;

/**
 *
 *@作者 zx
 *@创建日期 2019/7/22 14:02
 *@描述 司机端主页
 */
public interface DriverContract  {
    interface DriverView extends IBaseContract.IBaseView{
        void driverSuccess(String msg);
    }
    interface DriverPresenter extends IBaseContract.IBasePresenter<DriverView>{
        void driverMethod(String token,String latitude,String longitude);
    }
}
