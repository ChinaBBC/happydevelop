package com.zx.haijixing.login.activity;


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
import com.zx.haijixing.login.entry.LoginEntry;
import com.zx.haijixing.login.presenter.LoginActivityImp;
import com.zx.haijixing.share.RoutePathConstant;
import com.zx.haijixing.share.base.BaseActivity;
import com.zx.haijixing.util.HaiTool;

import butterknife.BindView;
import butterknife.OnClick;
import zx.com.skytool.ZxCountDownTimerUtil;
import zx.com.skytool.ZxLogUtil;
import zx.com.skytool.ZxStatusBarCompat;
import zx.com.skytool.ZxStringUtil;
import zx.com.skytool.ZxToastUtil;

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
    private int loginType = 1;

    @Override
    public void loginSuccess(LoginEntry loginEntry) {
        ARouter.getInstance().build(RoutePathConstant.DRIVER_MAIN).navigation();
        finish();
    }

    @Override
    public void loginCodeSuccess() {
        ZxToastUtil.centerToast(getHaiString(R.string.send_success));
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initInjector() {
        mPresenter = new LoginActivityImp();
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
                if (loginType == 1){
                    checkInputC();
                }else {
                    checkInputP();
                }
                break;
            case R.id.login_go_register:
                ARouter.getInstance().build(RoutePathConstant.BASE_INFO).navigation();
                break;
            case R.id.login_password_login:
                loginType = 2;
                codeLoginArea.setVisibility(View.GONE);
                codeImage.setVisibility(View.GONE);
                passwordLoginArea.setVisibility(View.VISIBLE);
                passImage.setVisibility(View.VISIBLE);
                passwordLogin.setTextColor(getHaiColor(R.color.color_3333));
                codeLogin.setTextColor(getHaiColor(R.color.color_9999));
                break;
            case R.id.login_code_login:
                loginType = 1;
                codeLoginArea.setVisibility(View.VISIBLE);
                codeImage.setVisibility(View.VISIBLE);
                passwordLoginArea.setVisibility(View.GONE);
                passImage.setVisibility(View.GONE);
                passwordLogin.setTextColor(getHaiColor(R.color.color_9999));
                codeLogin.setTextColor(getHaiColor(R.color.color_3333));
                break;
            case R.id.login_phone_send_code:
                String trim = phonePhone.getText().toString().trim();
                if (ZxStringUtil.isEmpty(trim)){
                    ZxToastUtil.centerToast(getHaiString(R.string.please_input_phone));
                }else if (!HaiTool.checkPhone(trim)){
                    ZxToastUtil.centerToast(getHaiString(R.string.phone_error));
                }
                else {
                    sendMethod();
                    mPresenter.loginCodeMethod(trim);
                }
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

    private void checkInputP(){
        String phone = name.getText().toString().trim();
        String password = this.password.getText().toString().trim();
        if (ZxStringUtil.isEmpty(phone) || ZxStringUtil.isEmpty(password)){
            ZxToastUtil.centerToast(getHaiString(R.string.please_input_phone_pass));
        }/*else if (!HaiTool.checkPhone(phone)){
            ZxToastUtil.centerToast(getHaiString(R.string.phone_error));
        }*/ else {
            mPresenter.loginMethod(phone,HaiTool.md5Method(password));
        }
    }
    private void checkInputC(){
        String phone = this.phonePhone.getText().toString().trim();
        String code = this.codeCode.getText().toString().trim();
        if (ZxStringUtil.isEmpty(phone) || ZxStringUtil.isEmpty(code)){
            ZxToastUtil.centerToast(getHaiString(R.string.please_input_phone_code));
        }else if (!HaiTool.checkPhone(phone)){
            ZxToastUtil.centerToast(getHaiString(R.string.phone_error));
        } else {
            mPresenter.loginMethodC(phone,code);
        }
    }
}
