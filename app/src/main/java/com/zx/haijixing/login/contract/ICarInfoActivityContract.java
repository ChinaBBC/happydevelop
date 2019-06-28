package com.zx.haijixing.login.contract;

import com.zx.haijixing.share.base.IBaseContract;

/**
 *
 *@作者 zx
 *@创建日期 2019/6/28 11:45
 *@描述 车辆信息
 */
public interface ICarInfoActivityContract {
    interface CarInfoView extends IBaseContract.IBaseView{
        void carInfoSuccess();
    }

    interface CarInfoPresenter extends IBaseContract.IBasePresenter<CarInfoView>{
        void carInfoMethod(String param);
    }
}
