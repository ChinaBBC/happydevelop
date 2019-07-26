package com.zx.haijixing.driver.contract;

import com.zx.haijixing.driver.entry.TruckDetailEntry;
import com.zx.haijixing.share.base.IBaseContract;

import java.util.Map;

/**
 *
 *@作者 zx
 *@创建日期 2019/7/23 16:42
 *@描述 车辆修改
 */
public interface TruckChangeContract {
    interface TruckChangeView extends IBaseContract.IBaseView{
        void truckInfoSuccess(TruckDetailEntry.TruckInfoBean truckInfoBean,String basePath);
        void driverIdentifySuccess(String identifyA,String identifyB);
        void changeTimeSuccess(String msg);
    }

    interface TruckChangePresenter extends IBaseContract.IBasePresenter<TruckChangeView>{
        void truckInfoMethod(String id);
        void driverIdentifyMethod(String token);
        void changeTimeMethod(Map<String,Object> params);
    }
}
