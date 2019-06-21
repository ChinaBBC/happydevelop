package com.zx.haijixing.login;

import com.zx.haijixing.share.base.IBaseContract;

public interface ILoginActivityContract {
    interface LoginView extends IBaseContract.IBaseView{
        void loginSuccess();
    }

    interface LoginPresenter extends IBaseContract.IBasePresenter<LoginView>{
        void loginMethod(String account,String password);
    }
}
