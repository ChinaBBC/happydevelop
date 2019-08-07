package com.zx.haijixing.logistics.contract;

import com.zx.haijixing.logistics.entry.RunTableEntry;
import com.zx.haijixing.share.base.IBaseContract;

import java.util.List;
import java.util.Map;

/**
 *
 *@作者 zx
 *@创建日期 2019/8/5 17:00
 *@描述 运营总表
 */
public interface RunTableContact {
    interface RunTableView extends IBaseContract.IBaseView{
        void runTableSuccess(List<RunTableEntry> runTableEntries);
    }

    interface RunTablePresenter extends IBaseContract.IBasePresenter<RunTableView>{
        void runTableMethod(Map<String,Object> params);
    }
}
