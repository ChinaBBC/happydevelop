package com.zx.haijixing.login.activity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zx.haijixing.R;
import com.zx.haijixing.login.contract.IForgetActivityContract;
import com.zx.haijixing.login.presenter.ForgetActivityImp;
import com.zx.haijixing.share.PathConstant;
import com.zx.haijixing.share.base.BaseActivity;
import com.zx.haijixing.util.HaiTool;

import butterknife.BindView;
import butterknife.OnClick;
import zx.com.skytool.ZxCountDownTimerUtil;
import zx.com.skytool.ZxStatusBarCompat;
import zx.com.skytool.ZxStringUtil;
import zx.com.skytool.ZxToastUtil;

/**
 *
 *@作者 zx
 *@创建日期 2019/6/28 10:38
 *@描述 忘记密码
 */
@Route(path = PathConstant.ROUTE_FORGET)
public class ForgetActivity extends BaseActivity<ForgetActivityImp> implements IForgetActivityContract.ForgetView {

    @BindView(R.id.forget_back)
    ImageView back;
    @BindView(R.id.forget_title)
    TextView title;
    @BindView(R.id.forget_user_name)
    EditText name;
    @BindView(R.id.forget_send_code)
    TextView send;
    @BindView(R.id.forget_user_code)
    EditText code;
    @BindView(R.id.forget_password)
    EditText password;
    @BindView(R.id.forget_sure_password)
    EditText surePassword;
    @BindView(R.id.forget_sure)
    Button sure;
    private ZxCountDownTimerUtil<TextView> zxCountDownTimerUtil;

    @Override
    protected void initView() {
       setTitleTopMargin(back);
       setTitleTopMargin(title);
    }

    @Override
    protected void initInjector() {
        mPresenter = new ForgetActivityImp();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_forget;
    }

    @Override
    public void findSuccess() {
        ZxToastUtil.centerToast(getHaiString(R.string.change_success));
        finish();
    }

    @Override
    public void findCodeSuccess() {
        ZxToastUtil.centerToast(getHaiString(R.string.send_success));
    }

    @OnClick({R.id.forget_back, R.id.forget_send_code, R.id.forget_sure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.forget_back:
                finish();
                break;
            case R.id.forget_send_code:
                String phone = this.name.getText().toString().trim();
                if (ZxStringUtil.isEmpty(phone)){
                    ZxToastUtil.centerToast(getHaiString(R.string.please_input_phone));
                }else if (!HaiTool.checkPhone(phone)){
                    ZxToastUtil.centerToast(getHaiString(R.string.phone_error));
                } else {
                    sendMethod();
                    mPresenter.findCodeMethod(phone);
                }
                break;
            case R.id.forget_sure:
                checkInput();
                break;
        }
    }
    //发送验证码
    private void sendMethod() {
        zxCountDownTimerUtil = new ZxCountDownTimerUtil<>(send);
        zxCountDownTimerUtil.setCommonStyle(0);
        zxCountDownTimerUtil.setRunningStyle(0);
        zxCountDownTimerUtil.setTimeColor(getHaiColor(R.color.color_3333));
        zxCountDownTimerUtil.start();
    }

    private void checkInput(){
        String phone = this.name.getText().toString().trim();
        String code = this.code.getText().toString().trim();
        String password = this.password.getText().toString().trim();
        String surePassword = this.surePassword.getText().toString().trim();

        if (ZxStringUtil.isEmpty(phone)){
            ZxToastUtil.centerToast(getHaiString(R.string.please_input_phone));
        }else if (ZxStringUtil.isEmpty(code)){
            ZxToastUtil.centerToast(getHaiString(R.string.please_input_code));
        }else if (ZxStringUtil.isEmpty(password)){
            ZxToastUtil.centerToast(getHaiString(R.string.please_input_password));
        }else if (ZxStringUtil.isEmpty(surePassword)){
            ZxToastUtil.centerToast(getHaiString(R.string.sure_password));
        }else if (!password.equals(surePassword)){
            ZxToastUtil.centerToast(getHaiString(R.string.password_not_equal));
        }else if (!HaiTool.checkPhone(phone)){
            ZxToastUtil.centerToast(getHaiString(R.string.phone_error));
        } else {
            mPresenter.findMethod(phone,password,code);
        }

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
