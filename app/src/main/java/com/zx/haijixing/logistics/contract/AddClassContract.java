package com.zx.haijixing.logistics.contract;

import com.zx.haijixing.logistics.entry.DriverEntry;
import com.zx.haijixing.logistics.entry.TruckInfoEntry;
import com.zx.haijixing.share.base.IBaseContract;

import java.util.List;
import java.util.Map;

/**
 *
 *@作者 zx
 *@创建日期 2019/8/1 19:10
 *@描述 添加班次
 */
public interface AddClassContract {
    interface AddClassView extends IBaseContract.IBaseView{
        void addClassSuccess(String msg);
        void editorClassSuccess(String msg);
        void driverPhoneSuccess(List<DriverEntry> driverEntries);
        void truckNumSuccess(List<TruckInfoEntry> truckInfoEntries);
    }

    interface AddClassPresenter extends IBaseContract.IBasePresenter<AddClassView>{
        void addClassMethod(Map<String,String> params);
        void editorClassMethod(Map<String,String> params);
        void driverPhoneMethod(Map<String, String> params);
        void truckNumMethod(Map<String, String> params);
    }
}
