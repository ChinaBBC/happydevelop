package com.zx.haijixing.login.activity;


import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
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
import butterknife.ButterKnife;
import butterknife.OnClick;
import zx.com.skytool.ZxCountDownTimerUtil;
import zx.com.skytool.ZxLogUtil;
import zx.com.skytool.ZxStatusBarCompat;

/**
 * @作者 zx
 * @创建日期 2019/6/28 10:34
 * @描述 登录
 */
@Route(path = RoutePathConstant.ROUTE_LOGIN)
public class LoginActivity extends BaseActivity<LoginActivityImp> implements ILoginActivityContract.LoginView {


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
    @BindView(R.id.login_pass_image)
    ImageView passImage;
    @BindView(R.id.login_code_image)
    ImageView codeImage;
    @BindView(R.id.login_password_login)
    TextView passwordLogin;
    @BindView(R.id.login_code_login)
    TextView codeLogin;
    @BindView(R.id.login_password_login_area)
    ConstraintLayout passwordLoginArea;
    @BindView(R.id.login_phone_phone)
    EditText phonePhone;
    @BindView(R.id.login_phone_send_code)
    TextView sendCode;
    @BindView(R.id.login_code_code)
    EditText codeCode;
    @BindView(R.id.login_code_login_area)
    ConstraintLayout codeLoginArea;

    private ZxCountDownTimerUtil<TextView> zxCountDownTimerUtil;

    @Override
    public void loginSuccess() {
        ZxLogUtil.logVerbose("login success!");
    }

    @Override
    protected void initView() {

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

    @OnClick({R.id.login_forget_password, R.id.login_login, R.id.login_go_register,R.id.login_password_login, R.id.login_code_login, R.id.login_phone_send_code})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login_forget_password:
                ARouter.getInstance().build(RoutePathConstant.ROUTE_FORGET).navigation();
                break;
            case R.id.login_login:
                ARouter.getInstance().build(RoutePathConstant.DRIVER_MAIN).navigation();

                //mPresenter.loginMethod("", "");
                break;
            case R.id.login_go_register:
                ARouter.getInstance().build(RoutePathConstant.BASE_INFO).navigation();
                break;
            case R.id.login_password_login:
                codeLoginArea.setVisibility(View.GONE);
                codeImage.setVisibility(View.GONE);
                passwordLoginArea.setVisibility(View.VISIBLE);
                passImage.setVisibility(View.VISIBLE);
                passwordLogin.setTextColor(getHaiColor(R.color.color_3333));
                codeLogin.setTextColor(getHaiColor(R.color.color_9999));
                break;
            case R.id.login_code_login:
                codeLoginArea.setVisibility(View.VISIBLE);
                codeImage.setVisibility(View.VISIBLE);
                passwordLoginArea.setVisibility(View.GONE);
                passImage.setVisibility(View.GONE);
                passwordLogin.setTextColor(getHaiColor(R.color.color_9999));
                codeLogin.setTextColor(getHaiColor(R.color.color_3333));
                break;
            case R.id.login_phone_send_code:
                sendMethod();
                break;
        }
    }

    //发送验证码
    private void sendMethod() {
        zxCountDownTimerUtil = new ZxCountDownTimerUtil<>(sendCode);
        zxCountDownTimerUtil.setCommonStyle(0);
        zxCountDownTimerUtil.setRunningStyle(0);
        zxCountDownTimerUtil.setTimeColor(getHaiColor(R.color.color_3333));
        zxCountDownTimerUtil.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (zxCountDownTimerUtil != null && zxCountDownTimerUtil.getRunStatus())
            zxCountDownTimerUtil.cancel();
    }

    @Override
    public void setStatusBar() {
        ZxStatusBarCompat.translucentStatusBar(this,true);
    }
}
