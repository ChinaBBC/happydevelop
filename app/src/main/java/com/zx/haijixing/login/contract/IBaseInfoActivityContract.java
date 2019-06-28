package com.zx.haijixing.login.contract;

import com.zx.haijixing.share.base.IBaseContract;
/**
 *
 *@作者 zx
 *@创建日期 2019/6/28 10:25
 *@描述 基本信息
 */
public interface IBaseInfoActivityContract {
    interface BaseInfoView extends IBaseContract.IBaseView{
        void baseInfoSuccess();
    }

    interface BaseInfoPresenter extends IBaseContract.IBasePresenter<BaseInfoView>{
        void baseInfoMethod(String account,String password,String code);
    }
}
