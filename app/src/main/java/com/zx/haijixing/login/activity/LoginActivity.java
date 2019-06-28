package com.zx.haijixing.login.activity;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.zx.haijixing.R;
import com.zx.haijixing.login.contract.ILoginActivityContract;
import com.zx.haijixing.login.presenter.LoginActivityImp;
import com.zx.haijixing.share.RoutePathConstant;
import com.zx.haijixing.share.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;
import zx.com.skytool.ZxLogUtil;
import zx.com.skytool.ZxStatusBarCompat;
/**
 *
 *@作者 zx
 *@创建日期 2019/6/28 10:34
 *@描述 登录
 */
@Route(path = RoutePathConstant.ROUTE_LOGIN)
public class LoginActivity extends BaseActivity<LoginActivityImp> implements ILoginActivityContract.LoginView {


    @BindView(R.id.common_title_back)
    ImageView back;
    @BindView(R.id.common_title_title)
    TextView title;
    @BindView(R.id.login_user_name)
    EditText name;
    @BindView(R.id.login_password)
    EditText password;
    @BindView(R.id.login_forget_password)
    TextView forgetPassword;
    @BindView(R.id.login_login)
    Button login;
    @BindView(R.id.login_go_register)
    TextView goRegister;

    @Override
    public void loginSuccess() {
        ZxLogUtil.logVerbose("login success!");
    }

    @Override
    protected void initView() {
        ZxStatusBarCompat.setStatusBarLightMode(this);
        title.setText(getHaiString(R.string.login));
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

    @OnClick({R.id.login_forget_password, R.id.login_login, R.id.login_go_register,R.id.common_title_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login_forget_password:
                ARouter.getInstance().build(RoutePathConstant.ROUTE_FORGET).navigation();
                break;
            case R.id.login_login:
                mPresenter.loginMethod("", "");
                break;
            case R.id.login_go_register:
                ARouter.getInstance().build(RoutePathConstant.BASE_INFO).navigation();
                break;
            case R.id.common_title_back:
                finish();
                break;
        }
    }
}
