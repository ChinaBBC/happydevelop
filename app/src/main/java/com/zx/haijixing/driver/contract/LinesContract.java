package com.zx.haijixing.driver.contract;

import com.zx.haijixing.driver.entry.DriverClassEntry;
import com.zx.haijixing.share.base.IBaseContract;

import java.util.List;
import java.util.Map;

/**
 *
 *@作者 zx
 *@创建日期 2019/7/23 9:23
 *@描述 车辆及路线
 */
public interface LinesContract {
    interface LinesView extends IBaseContract.IBaseView{
        void linesSuccess(List<DriverClassEntry> driverClassEntries);
    }

    interface LinesPresenter extends IBaseContract.IBasePresenter<LinesView>{
        void linesMethod(Map<String, String> params);
    }
}
