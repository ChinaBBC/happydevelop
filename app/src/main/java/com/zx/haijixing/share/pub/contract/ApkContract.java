package com.zx.haijixing.share.pub.contract;

import com.zx.haijixing.share.base.IBaseContract;

/**
 *
 *@作者 zx
 *@创建日期 2019/7/11 9:44
 *@描述 下载
 */
public interface ApkContract {
    interface ApkView extends IBaseContract.IBaseView{
        void apkSuccess(String apkFile);
        void apkProgress(float progress);
    }

    interface ApkPresenter extends IBaseContract.IBasePresenter<ApkView>{
        void apkMethod(String path,String destPath);
    }
}
