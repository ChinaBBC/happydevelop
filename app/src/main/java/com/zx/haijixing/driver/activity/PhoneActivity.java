package com.zx.haijixing.driver.activity;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import butterknife.ButterKnife;
import butterknife.OnClick;
import zx.com.skytool.ZxCountDownTimerUtil;
import zx.com.skytool.ZxSharePreferenceUtil;
import zx.com.skytool.ZxStringUtil;
import zx.com.skytool.ZxToastUtil;

/**
 * @作者 zx
 * @创建日期 2019/7/9 15:49
 * @描述 修改手机号
 */
@Route(path = PathConstant.PHONE)
public class PhoneActivity extends BaseActivity<PhoneImp> implements PhoneContract.PhoneView {

    @BindView(R.id.common_title_back)
    ImageView back;
    @BindView(R.id.common_title_title)
    TextView title;
    @BindView(R.id.phone_old_phone)
    TextView oldPhone;
    @BindView(R.id.phone_send_old)
    TextView sendOld;
    @BindView(R.id.phone_lay_old)
    LinearLayout layOld;
    @BindView(R.id.phone_new_phone)
    EditText newPhone;
    @BindView(R.id.phone_send_new)
    TextView sendNew;
    @BindView(R.id.phone_lay_new)
    LinearLayout layNew;
    @BindView(R.id.phone_line1)
    TextView phoneLine1;
    @BindView(R.id.phone_old_code)
    EditText oldCode;
    @BindView(R.id.phone_new_code)
    EditText newCode;
    @BindView(R.id.phone_next)
    Button next;
    @BindView(R.id.phone_sure)
    Button sure;
    @BindView(R.id.phone_pass)
    EditText phonePass;
    @BindView(R.id.phone_re_pass)
    EditText phoneRePass;
    @BindView(R.id.phone_con_new)
    ConstraintLayout phoneConNew;
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
    }

    @Override
    protected void initInjector() {
        mPresenter = new PhoneImp();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_phone;
    }

    @OnClick({R.id.common_title_back, R.id.phone_send_old, R.id.phone_send_new, R.id.phone_next, R.id.phone_sure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.common_title_back:
                finish();
                break;
            case R.id.phone_send_old:
                sendMethod(sendOld);
                mPresenter.codeMethod(phone,4);
                break;
            case R.id.phone_send_new:
                String myNews = newPhone.getText().toString().trim();
                if (!ZxStringUtil.isEmpty(myNews) && HaiTool.checkPhone(myNews)){
                    sendMethod(sendNew);
                    mPresenter.codeMethod(myNews,5);
                }else {
                    ZxToastUtil.centerToast("请输入正确的手机号！");
                }
                break;
            case R.id.phone_next:
                String myOldCode = oldCode.getText().toString().trim();
                if (!ZxStringUtil.isEmpty(myOldCode)){
                    Map<String,String> phoneMap = new HashMap<>();
                    phoneMap.put("token",token);
                    phoneMap.put("vcode",myOldCode);
                    phoneMap.put("phone",phone);
                    phoneMap.put("timestamp",System.currentTimeMillis()+"");
                    phoneMap.put("sign","");
                    phoneMap.put("sign",HaiTool.sign(phoneMap));
                    mPresenter.oldPhoneMethod(phoneMap);
                }else {
                    ZxToastUtil.centerToast("请输入验证码！");
                }
                break;
            case R.id.phone_sure:
                checkInput();
                break;
        }
    }

    //发送验证码
    private void sendMethod(TextView view) {
        zxCountDownTimerUtil = new ZxCountDownTimerUtil<>(view);
        zxCountDownTimerUtil.setCommonStyle(0);
        zxCountDownTimerUtil.setRunningStyle(0);
        zxCountDownTimerUtil.setTimeColor(getHaiColor(R.color.color_3333));
        zxCountDownTimerUtil.start();
    }

    private void checkInput(){
        String newP = newPhone.getText().toString().trim();
        String newC = newCode.getText().toString().trim();
        String pass = phonePass.getText().toString().trim();
        String rePass = phoneRePass.getText().toString().trim();
        if (ZxStringUtil.isEmpty(newP)){
            ZxToastUtil.centerToast("请输入手机号！");
        }else if (ZxStringUtil.isEmpty(newC)){
            ZxToastUtil.centerToast("请输入验证码！");
        }else if (ZxStringUtil.isEmpty(pass)){
            ZxToastUtil.centerToast("请输入密码！");
        }else if (ZxStringUtil.isEmpty(rePass)){
            ZxToastUtil.centerToast("请确认密码！");
        }else if (!pass.equals(rePass)){
            ZxToastUtil.centerToast("密码输入不一致！");
        }else {

            Map<String,String> param = new HashMap<>();
            param.put("phone",newP);
            param.put("vcode",newC);
            param.put("loginKey",HaiTool.md5Method(pass));
            param.put("token",token);
            param.put("type",5+"");
            param.put("timestamp",System.currentTimeMillis()+"");
            param.put("sign","");
            param.put("sign",HaiTool.sign(param));
            mPresenter.newPhoneMethod(param);
        }
    }

    private void show() {
        layOld.setVisibility(View.GONE);
        next.setVisibility(View.GONE);
        oldCode.setVisibility(View.GONE);

        layNew.setVisibility(View.VISIBLE);
        sure.setVisibility(View.VISIBLE);
        phoneConNew.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (zxCountDownTimerUtil != null && zxCountDownTimerUtil.getRunStatus())
            zxCountDownTimerUtil.cancel();
    }

    @Override
    public void oldPhoneSuccess(String msg) {
        if (zxCountDownTimerUtil != null && zxCountDownTimerUtil.getRunStatus())
            zxCountDownTimerUtil.cancel();
        show();
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
