package com.zx.haijixing.driver.contract;

import com.zx.haijixing.driver.entry.TruckEntry;
import com.zx.haijixing.share.base.IBaseContract;

import java.util.List;

/**
 *
 *@作者 zx
 *@创建日期 2019/7/23 16:29
 *@描述 车辆管理
 */
public interface VehicleContract {
    interface VehicleView extends IBaseContract.IBaseView{
        void vehicleSuccess(List<TruckEntry> truckEntries);
    }

    interface VehiclePresenter extends IBaseContract.IBasePresenter<VehicleView>{
        void vehicleMethod(String token);
    }
}
