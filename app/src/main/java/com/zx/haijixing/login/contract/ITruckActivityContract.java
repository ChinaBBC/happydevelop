package com.zx.haijixing.login.contract;

import com.zx.haijixing.login.entry.TruckTypeEntry;
import com.zx.haijixing.share.base.IBaseContract;

import java.util.List;
import java.util.Map;

/**
 *
 *@作者 zx
 *@创建日期 2019/7/15 11:54
 *@描述 车辆信息
 */
public interface ITruckActivityContract {
    interface TruckView extends IBaseContract.IBaseView{
        void typeSuccess(List<TruckTypeEntry> typeEntryList);
        void truckApplySuccess();
        void uploadTruckSuccess(String path,int tag);
    }

    interface TruckPresenter extends IBaseContract.IBasePresenter<TruckView>{
        void truckTypeMethod();
        void truckApplyMethod(Map<String,String> params);
        void uploadTruckMethod(String path,int tag);
    }
}
