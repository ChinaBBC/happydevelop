package com.zx.haijixing.logistics.contract;

import com.zx.haijixing.logistics.entry.ClassManageEntry;
import com.zx.haijixing.share.base.IBaseContract;

import java.util.List;
import java.util.Map;

/**
 *
 *@作者 zx
 *@创建日期 2019/8/1 15:50
 *@描述 班次管理
 */
public interface ClaManageContract {
    interface ClaManageView extends IBaseContract.IBaseView{
        void claManageSuccess(List<ClassManageEntry> classManageEntries);
        void deleteClassSuccess(String msg);
    }
    interface ClaManagePresenter extends IBaseContract.IBasePresenter<ClaManageView>{
        void claManageMethod(Map<String, String> params);
        void deleteClassMethod(Map<String, String> params);
    }
}
