package com.zx.haijixing.driver.activity;

import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.zx.haijixing.R;
import com.zx.haijixing.share.PathConstant;
import com.zx.haijixing.share.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @作者 zx
 * @创建日期 2019/7/3 17:06
 * @描述 全部服务
 */
@Route(path = PathConstant.DRIVER_SERVICES)
public class ServicesActivity extends BaseActivity {

    @BindView(R.id.common_title_back)
    ImageView back;
    @BindView(R.id.common_title_title)
    TextView title;
    @BindView(R.id.services_receive)
    ConstraintLayout servicesReceive;
    @BindView(R.id.services_print)
    ConstraintLayout servicesPrint;
    @BindView(R.id.services_clock)
    ConstraintLayout servicesClock;

    @Override
    protected void initView() {
        title.setText(getString(R.string.all_service));
    }
    @Override
    protected void initInjector() {
        ARouter.getInstance().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_services;
    }

    @OnClick({R.id.common_title_back, R.id.services_receive, R.id.services_print, R.id.services_clock})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.common_title_back:
                finish();
                break;
            case R.id.services_receive:
                break;
            case R.id.services_print:
                break;
            case R.id.services_clock:
                break;
        }
    }
}
