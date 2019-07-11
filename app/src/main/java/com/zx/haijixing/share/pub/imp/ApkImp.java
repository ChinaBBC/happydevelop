package com.zx.haijixing.share.pub.imp;

import com.allen.library.RxHttpUtils;
import com.allen.library.download.DownloadObserver;
import com.zx.haijixing.share.base.BasePresenter;
import com.zx.haijixing.share.pub.contract.ApkContract;
/**
 *
 *@作者 zx
 *@创建日期 2019/7/11 9:47
 *@描述 下载app
 */
public class ApkImp extends BasePresenter<ApkContract.ApkView> implements ApkContract.ApkPresenter {

    @Override
    public void apkMethod(String path) {
        RxHttpUtils.downloadFile(path)
                .subscribe(new DownloadObserver("s2f43fda") {
                    @Override
                    protected void onError(String errorMsg) {
                        mView.showFaild(errorMsg);
                    }

                    @Override
                    protected void onSuccess(long bytesRead, long contentLength, float progress, boolean done, String filePath) {
                        mView.apkProgress(progress);
                        if (done)
                            mView.apkSuccess(filePath);
                    }
                });
    }
}
