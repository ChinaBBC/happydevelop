package com.zx.haijixing.driver.activity;

import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.zx.haijixing.R;
import com.zx.haijixing.driver.contract.ServicesContract;
import com.zx.haijixing.driver.presenter.ServicesImp;
import com.zx.haijixing.share.OtherConstants;
import com.zx.haijixing.share.PathConstant;
import com.zx.haijixing.share.base.BaseActivity;
import com.zx.haijixing.share.pub.entry.EventBusEntity;
import com.zx.haijixing.util.CommonDialogFragment;
import com.zx.haijixing.util.HaiDialogUtil;
import com.zx.haijixing.util.HaiTool;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import zx.com.skytool.ZxLogUtil;
import zx.com.skytool.ZxToastUtil;

/**
 * @作者 zx
 * @创建日期 2019/7/3 17:06
 * @描述 全部服务
 */
@Route(path = PathConstant.DRIVER_SERVICES)
public class ServicesActivity extends BaseActivity<ServicesImp> implements ServicesContract.ServicesView {

    @BindView(R.id.common_title_back)
    ImageView back;
    @BindView(R.id.common_title_title)
    TextView title;
    @BindView(R.id.services_word1)
    TextView word1;
    @BindView(R.id.services_word2)
    TextView word2;
    @BindView(R.id.services_word3)
    TextView word3;
    @BindView(R.id.services_receive)
    ConstraintLayout servicesReceive;
    @BindView(R.id.services_print)
    ConstraintLayout servicesPrint;
    @BindView(R.id.services_clock)
    ConstraintLayout servicesClock;

    @Autowired(name = "loginType")
    public String loginType;
    @Autowired(name = "token")
    public String token;
    private CommonDialogFragment showClock;

    @Override
    protected void initView() {
        title.setText(getString(R.string.all_service));
        if (!OtherConstants.LOGIN_DRIVER.equals(loginType)){
            word1.setText("待派单");
            word2.setText("待出发");
            word3.setText(OtherConstants.LOGIN_LOGISTICS.equals(loginType)?"班次管理":"物流中心");
        }
    }
    @Override
    protected void initInjector() {
        mPresenter = new ServicesImp();
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
                EventBus.getDefault().post(new EventBusEntity(OtherConstants.EVENT_RECEIVE));
                finish();
                break;
            case R.id.services_print:
                EventBus.getDefault().post(new EventBusEntity(OtherConstants.EVENT_PRINT));
                finish();
                break;
            case R.id.services_clock:
                if (loginType.equals(OtherConstants.LOGIN_DRIVER)){
                    Map<String,String> param = new HashMap<>();
                    param.put("token",token);
                    param.put("timestamp",System.currentTimeMillis()+"");
                    param.put("sign","");
                    param.put("sign",HaiTool.sign(param));
                    mPresenter.clockStatusMethod(param);
                }else {
                    EventBus.getDefault().post(new EventBusEntity(OtherConstants.EVENT_LOGISTICS));
                    finish();
                }
                break;
            case R.id.dialog_clock_yes:
                Map<String,String> params = new HashMap<>();
                params.put("token",token);
                params.put("timestamp",System.currentTimeMillis()+"");
                params.put("sign","");
                params.put("sign",HaiTool.sign(params));
                mPresenter.clockMethod(params);
                break;
            case R.id.dialog_clock_no:
                showClock.dismissAllowingStateLoss();
                break;
        }
    }

    @Override
    public void clockSuccess(String msg) {
        ZxToastUtil.centerToast(msg);
        showClock.dismissAllowingStateLoss();
    }

    @Override
    public void clockStatusSuccess(String msg) {
        if ("1".equals(msg)){
            showClock = HaiDialogUtil.showClock(getSupportFragmentManager(),"是否下班？", this::onViewClicked);
        }else {
            showClock = HaiDialogUtil.showClock(getSupportFragmentManager(),null, this::onViewClicked);
        }
    }
}
