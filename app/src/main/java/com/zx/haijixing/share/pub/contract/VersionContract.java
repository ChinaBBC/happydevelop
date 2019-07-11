package com.zx.haijixing.share.pub.contract;

import com.zx.haijixing.share.base.IBaseContract;
import com.zx.haijixing.share.pub.entry.VersionEntry;

/**
 *
 *@作者 zx
 *@创建日期 2019/7/11 8:51
 *@描述 版本控制
 */
public interface VersionContract {
    interface VersionView extends IBaseContract.IBaseView{
        void versionSuccess(VersionEntry versionEntry);
    }
    interface VersionPresenter extends IBaseContract.IBasePresenter<VersionView>{
        void versionMethod();
    }
}
