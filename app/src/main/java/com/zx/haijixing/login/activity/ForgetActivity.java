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
import com.zx.haijixing.login.contract.IForgetActivityContract;
import com.zx.haijixing.login.presenter.ForgetActivityImp;
import com.zx.haijixing.share.RoutePathConstant;
import com.zx.haijixing.share.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;
import zx.com.skytool.ZxCountDownTimerUtil;
import zx.com.skytool.ZxStatusBarCompat;
/**
 *
 *@作者 zx
 *@创建日期 2019/6/28 10:38
 *@描述 忘记密码
 */
@Route(path = RoutePathConstant.ROUTE_FORGET)
public class ForgetActivity extends BaseActivity<ForgetActivityImp> implements IForgetActivityContract.ForgetView {

    @BindView(R.id.forget_back)
    ImageView back;
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
    }

    @Override
    protected void initInjector() {
        ARouter.getInstance().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_forget;
    }

    @Override
    public void findSuccess() {

    }

    @OnClick({R.id.forget_back, R.id.forget_send_code, R.id.forget_sure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.forget_back:
                finish();
                break;
            case R.id.forget_send_code:
                sendMethod();
                break;
            case R.id.forget_sure:
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
