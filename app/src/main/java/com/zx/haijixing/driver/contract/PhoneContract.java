package com.zx.haijixing.driver.contract;

import com.zx.haijixing.share.base.IBaseContract;

import java.util.Map;

/**
 *
 *@作者 zx
 *@创建日期 2019/7/23 14:16
 *@描述 修改手机号
 */
public interface PhoneContract {
    interface PhoneView extends IBaseContract.IBaseView{
        void oldPhoneSuccess(String msg);
        void newPhoneSuccess(String msg);
        void codeSuccess(String msg);
    }

    interface PhonePresenter extends IBaseContract.IBasePresenter<PhoneView>{
        void oldPhoneMethod(String phone,String code);
        void newPhoneMethod(Map<String,Object> params);
        void codeMethod(String phone,int type);
    }
}
