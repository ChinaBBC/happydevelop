package com.zx.haijixing.driver.activity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.zx.haijixing.R;
import com.zx.haijixing.driver.contract.PhoneContract;
import com.zx.haijixing.driver.presenter.PhoneImp;
import com.zx.haijixing.share.PathConstant;
import com.zx.haijixing.share.base.BaseActivity;
import com.zx.haijixing.util.HaiTool;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import zx.com.skytool.ZxCountDownTimerUtil;
import zx.com.skytool.ZxSharePreferenceUtil;
import zx.com.skytool.ZxStringUtil;
import zx.com.skytool.ZxToastUtil;

/**
 *
 *@作者 zx
 *@创建日期 2019/7/9 16:54
 *@描述 修改密码
 */
@Route(path = PathConstant.PASSWORD)
public class PasswordActivity extends BaseActivity<PhoneImp> implements PhoneContract.PhoneView {

    @BindView(R.id.common_title_back)
    ImageView back;
    @BindView(R.id.common_title_title)
    TextView title;
    @BindView(R.id.password_old_phone)
    TextView oldPhone;
    @BindView(R.id.password_send_old)
    TextView send;
    @BindView(R.id.password_code)
    EditText code;
    @BindView(R.id.password_pass)
    EditText pass;
    @BindView(R.id.password_re_pass)
    EditText rePass;
    @BindView(R.id.password_sure)
    Button sure;
    private String token;
    private String phone;
    private ZxCountDownTimerUtil<TextView> zxCountDownTimerUtil;

    @Override
    protected void initView() {
        ZxSharePreferenceUtil instance = ZxSharePreferenceUtil.getInstance();
        instance.init(this);
        token = (String) instance.getParam("token","null");
        phone = (String) instance.getParam("user_phone","null");
        title.setText(getHaiString(R.string.change_phone));
        oldPhone.setText(phone.substring(0,3)+"****"+phone.substring(7,11));
        title.setText(getHaiString(R.string.change_password));
    }

    @Override
    protected void initInjector() {
        mPresenter = new PhoneImp();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_password;
    }

    @OnClick({R.id.common_title_back, R.id.password_send_old, R.id.password_sure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.common_title_back:
                finish();
                break;
            case R.id.password_send_old:
                sendMethod();
                String myOld = oldPhone.getText().toString().trim();
                mPresenter.codeMethod(myOld,3);
                break;
            case R.id.password_sure:
                checkInput();
                break;
        }
    }

    private void checkInput(){
        String newC = code.getText().toString().trim();
        String pass = this.pass.getText().toString().trim();
        String rePass = this.rePass.getText().toString().trim();
        if (ZxStringUtil.isEmpty(newC)){
            ZxToastUtil.centerToast("请输入验证码！");
        }else if (ZxStringUtil.isEmpty(pass)){
            ZxToastUtil.centerToast("请输入密码！");
        }else if (ZxStringUtil.isEmpty(rePass)){
            ZxToastUtil.centerToast("请确认密码！");
        }else if (!pass.equals(rePass)){
            ZxToastUtil.centerToast("密码输入不一致！");
        }else {
            Map<String,Object> param = new HashMap<>();
            param.put("phone",phone);
            param.put("vcode",newC);
            param.put("loginKey",HaiTool.md5Method(pass));
            param.put("token",token);
            param.put("type",3);

            mPresenter.newPhoneMethod(param);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (zxCountDownTimerUtil != null && zxCountDownTimerUtil.getRunStatus())
            zxCountDownTimerUtil.cancel();
    }
    @Override
    public void oldPhoneSuccess(String msg) {

    }

    @Override
    public void newPhoneSuccess(String msg) {
        ZxToastUtil.centerToast(msg);
        ZxSharePreferenceUtil instance = ZxSharePreferenceUtil.getInstance();
        instance.init(this);
        instance.setLogin(false);
        ARouter.getInstance().build(PathConstant.ROUTE_LOGIN).navigation();
    }

    @Override
    public void codeSuccess(String msg) {
        ZxToastUtil.centerToast(msg);
    }
}
