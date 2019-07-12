package com.zx.haijixing.login.contract;

import com.zx.haijixing.login.entry.LoginEntry;
import com.zx.haijixing.share.base.IBaseContract;
/**
 *
 *@作者 zx
 *@创建日期 2019/6/21 15:32
 *@描述 登录协议
 */
public interface ILoginActivityContract {
    interface LoginView extends IBaseContract.IBaseView{
        void loginSuccess(LoginEntry loginEntry);
        void loginCodeSuccess();
    }

    interface LoginPresenter extends IBaseContract.IBasePresenter<LoginView>{
        void loginMethod(String account,String password);
        void loginMethodC(String account,String code);
        void loginCodeMethod(String phone);
    }
}
