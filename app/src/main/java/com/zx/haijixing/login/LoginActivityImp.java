package com.zx.haijixing.login;

import com.zx.haijixing.share.base.BasePresenter;


public class LoginActivityImp extends BasePresenter<ILoginActivityContract.LoginView> implements ILoginActivityContract.LoginPresenter {

    @Override
    public void loginMethod(String account, String password) {
        mView.loginSuccess();
    }
}
