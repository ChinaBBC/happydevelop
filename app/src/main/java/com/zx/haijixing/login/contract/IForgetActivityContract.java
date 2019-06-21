package com.zx.haijixing.login.contract;

import com.zx.haijixing.share.base.IBaseContract;

/**
 *
 *@作者 zx
 *@创建日期 2019/6/21 15:42
 *@描述 忘记密码协议
 */
public interface IForgetActivityContract {
    interface ForgetView extends IBaseContract.IBaseView{
        void findSuccess();
    }

    interface ForgetPresenter extends IBaseContract.IBasePresenter<ForgetView>{
        void findMethod(String account,String password,String code);
    }
}
