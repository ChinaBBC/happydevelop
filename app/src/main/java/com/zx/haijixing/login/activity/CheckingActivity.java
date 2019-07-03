package com.zx.haijixing.login.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.zx.haijixing.R;
import com.zx.haijixing.login.contract.ICheckingActivityContract;
import com.zx.haijixing.login.presenter.CheckingActivityImp;
import com.zx.haijixing.share.RoutePathConstant;
import com.zx.haijixing.share.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;
import zx.com.skytool.ZxStatusBarCompat;

/**
 * @作者 zx
 * @创建日期 2019/6/28 11:43
 * @描述 后台审核
 */
@Route(path = RoutePathConstant.CHECKING)
public class CheckingActivity extends BaseActivity<CheckingActivityImp> implements ICheckingActivityContract.CheckingView {

    @BindView(R.id.common_title_back)
    ImageView back;
    @BindView(R.id.common_title_title)
    TextView title;
    @BindView(R.id.checking_wait)
    TextView wait;
    @BindView(R.id.checking_is_checking)
    TextView isChecking;
    @BindView(R.id.checking_bg)
    ImageView checkingBg;
    @BindView(R.id.checking_sure)
    Button sure;

    @Override
    protected void initView() {
        title.setText(getHaiString(R.string.register));
    }

    @Override
    protected void initInjector() {
        ARouter.getInstance().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_checking;
    }

    @Override
    public void checkingSuccess() {

    }

    @OnClick({R.id.common_title_back, R.id.checking_sure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.common_title_back:
                finish();
                break;
            case R.id.checking_sure:
                break;
        }
    }
}
