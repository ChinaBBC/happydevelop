package com.zx.haijixing.login.activity;


import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.zx.haijixing.R;
import com.zx.haijixing.login.contract.ILoginActivityContract;
import com.zx.haijixing.login.presenter.LoginActivityImp;
import com.zx.haijixing.share.RoutePathConstant;
import com.zx.haijixing.share.base.BaseActivity;

import zx.com.skytool.ZxLogUtil;

@Route(path = RoutePathConstant.ROUTE_LOGIN)
public class LoginActivity extends BaseActivity<LoginActivityImp> implements ILoginActivityContract.LoginView {


    @Override
    public void loginSuccess() {
        ZxLogUtil.logVerbose("login success!");
    }

    @Override
    protected void initView() {
        mPresenter.loginMethod("","");
    }

    @Override
    protected void initInjector() {
        mPresenter = new LoginActivityImp();
        ARouter.getInstance().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

}
