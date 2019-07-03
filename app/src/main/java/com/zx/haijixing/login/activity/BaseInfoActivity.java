package com.zx.haijixing.login.activity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.zx.haijixing.R;
import com.zx.haijixing.login.contract.IBaseInfoActivityContract;
import com.zx.haijixing.login.presenter.BaseInfoActivityImp;
import com.zx.haijixing.share.RoutePathConstant;
import com.zx.haijixing.share.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;
import zx.com.skytool.ZxCacheUtil;
import zx.com.skytool.ZxCountDownTimerUtil;
import zx.com.skytool.ZxStatusBarCompat;
import zx.com.skytool.ZxStringUtil;

/**
 *
 *@作者 zx
 *@创建日期 2019/6/28 10:28
 *@描述 基本信息
 */
@Route(path = RoutePathConstant.BASE_INFO)
public class BaseInfoActivity extends BaseActivity<BaseInfoActivityImp> implements IBaseInfoActivityContract.BaseInfoView {

    @BindView(R.id.common_title_back)
    ImageView back;
    @BindView(R.id.common_title_title)
    TextView title;
    @BindView(R.id.base_info_head)
    ImageView head;
    @BindView(R.id.base_info_name)
    EditText name;
    @BindView(R.id.base_info_identify)
    EditText identify;
    @BindView(R.id.base_info_phone)
    EditText phone;
    @BindView(R.id.base_info_code)
    EditText code;
    @BindView(R.id.base_info_password)
    EditText password;
    @BindView(R.id.base_info_sure_password)
    EditText surePassword;
    @BindView(R.id.base_info_agree)
    ImageView agree;
    @BindView(R.id.base_info_protocol)
    TextView protocol;
    @BindView(R.id.base_info_next)
    Button next;
    @BindView(R.id.base_info_send)
    TextView send;

    private boolean isRead = false;
    private ZxCountDownTimerUtil<TextView> zxCountDownTimerUtil;

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
        return R.layout.activity_base_info;
    }

    @OnClick({R.id.common_title_back, R.id.base_info_head, R.id.base_info_agree, R.id.base_info_protocol,R.id.base_info_send,R.id.base_info_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.common_title_back:
                finish();
                break;
            case R.id.base_info_head:
                break;
            case R.id.base_info_agree:
                if (isRead){
                    agree.setImageResource(R.mipmap.register_disagree);
                    isRead = false;
                }else {
                    agree.setImageResource(R.mipmap.register_agree);
                    isRead = true;
                }
                break;
            case R.id.base_info_protocol:
                break;
            case R.id.base_info_send:
                sendMethod();
                break;
            case R.id.base_info_next:
                ARouter.getInstance().build(RoutePathConstant.CAR_INFO).navigation();
                break;
        }
    }

    @Override
    public void baseInfoSuccess() {

    }

    //发送验证码
    private void sendMethod() {
        zxCountDownTimerUtil = new ZxCountDownTimerUtil<>(send);
        zxCountDownTimerUtil.setCommonStyle(0);
        zxCountDownTimerUtil.setRunningStyle(0);
        zxCountDownTimerUtil.setTimeColor(getHaiColor(R.color.color_703f));
        zxCountDownTimerUtil.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (zxCountDownTimerUtil != null && zxCountDownTimerUtil.getRunStatus())
            zxCountDownTimerUtil.cancel();
    }
}
