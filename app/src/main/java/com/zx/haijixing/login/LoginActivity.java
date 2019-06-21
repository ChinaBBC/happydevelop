package com.zx.haijixing.login;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.zx.haijixing.R;
import com.zx.haijixing.share.base.BaseActivity;
@Route(path = "/login/LoginActivity")
public class LoginActivity extends BaseActivity<LoginActivityImp> implements ILoginActivityContract.LoginView {


    @Override
    public void loginSuccess() {

    }

    @Override
    protected void initView() {
        mPresenter = new LoginActivityImp();
        mPresenter.loginMethod("","");
    }

    @Override
    protected void initInjector() {
        ARouter.getInstance().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }
}
