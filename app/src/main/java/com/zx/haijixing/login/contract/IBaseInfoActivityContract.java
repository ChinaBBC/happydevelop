package com.zx.haijixing.login.contract;

import com.zx.haijixing.share.base.IBaseContract;

import java.util.Map;

/**
 *
 *@作者 zx
 *@创建日期 2019/6/28 10:25
 *@描述 基本信息
 */
public interface IBaseInfoActivityContract {
    interface BaseInfoView extends IBaseContract.IBaseView{
        void baseInfoSuccess(String driverId);
        void baseInfoCodeSuccess();
        void uploadImgSuccess(String path,int tag);
        void updateHeadSuccess(String msg);
    }

    interface BaseInfoPresenter extends IBaseContract.IBasePresenter<BaseInfoView>{
        void baseInfoMethod(Map<String,String> params);
        void baseInfoCodeMethod(String phone);
        void uploadImgMethod(String path,int tag);
        void updateHeadImgMethod(String path);
    }
}
