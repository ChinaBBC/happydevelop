package com.zx.haijixing.share.pub.contract;

import com.zx.haijixing.share.base.IBaseContract;
import com.zx.haijixing.share.pub.entry.NotifyEntry;

import java.util.List;

/**
 *
 *@作者 zx
 *@创建日期 2019/7/22 15:42
 *@描述 消息中心
 */
public interface NotifyContract {
    interface NotifyView extends IBaseContract.IBaseView{
        void notifySuccess(List<NotifyEntry> notifyEntries);
    }

    interface NotifyPresenter extends IBaseContract.IBasePresenter<NotifyView>{
        void notifyMethod(String token,String page,String size);
    }
}
