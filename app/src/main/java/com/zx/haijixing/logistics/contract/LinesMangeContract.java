package com.zx.haijixing.logistics.contract;

import com.zx.haijixing.logistics.entry.LinesManageEntry;
import com.zx.haijixing.share.base.IBaseContract;

import java.util.List;
import java.util.Map;

/**
 *
 *@作者 zx
 *@创建日期 2019/8/1 15:04
 *@描述 线路管理
 */
public interface LinesMangeContract {
    interface LinesManageView extends IBaseContract.IBaseView{
        void linesManageSuccess(List<LinesManageEntry> linesManageEntries);
    }

    interface LinesManagePresenter extends IBaseContract.IBasePresenter<LinesManageView>{
        void linesManageMethod(Map<String,Object> params);
    }
}
