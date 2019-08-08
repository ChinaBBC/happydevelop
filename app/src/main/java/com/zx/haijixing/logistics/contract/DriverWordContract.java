package com.zx.haijixing.logistics.contract;

import com.zx.haijixing.logistics.entry.DriverWordEntry;
import com.zx.haijixing.share.base.IBaseContract;

import java.util.List;
import java.util.Map;

/**
 *
 *@作者 zx
 *@创建日期 2019/8/5 19:55
 *@描述 司机评价
 */
public interface DriverWordContract {
    interface DriverWordView extends IBaseContract.IBaseView{
        void driverWordSuccess(List<DriverWordEntry> driverWordEntries);
    }

    interface DriverWordPresenter extends IBaseContract.IBasePresenter<DriverWordView>{
        void driverWordMethod(Map<String, String> params);
    }
}
