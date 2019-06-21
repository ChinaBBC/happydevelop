package com.zx.haijixing.login.presenter;

import com.zx.haijixing.login.contract.ILoginActivityContract;
import com.zx.haijixing.share.base.BasePresenter;

/**
 *
 *@作者 zx
 *@创建日期 2019/6/21 15:43
 *@描述 登录presenter
 */
public class LoginActivityImp extends BasePresenter<ILoginActivityContract.LoginView> implements ILoginActivityContract.LoginPresenter {

    @Override
    public void loginMethod(String account, String password) {
        mView.showLoading();
        mView.loginSuccess();
    }
}
