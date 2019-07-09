package com.zx.haijixing.driver.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zx.haijixing.R;
import com.zx.haijixing.share.RoutePathConstant;
import com.zx.haijixing.share.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @作者 zx
 * @创建日期 2019/7/9 15:49
 * @描述 修改手机号
 */
@Route(path = RoutePathConstant.PHONE)
public class PhoneActivity extends BaseActivity {

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

    @Override
    protected void initView() {
        title.setText(getHaiString(R.string.change_phone));
    }

    @Override
    protected void initInjector() {

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
                break;
            case R.id.phone_send_new:
                break;
            case R.id.phone_next:
                show();
                break;
            case R.id.phone_sure:
                break;
        }
    }

    private void show(){
        layOld.setVisibility(View.INVISIBLE);
        next.setVisibility(View.INVISIBLE);
        oldCode.setVisibility(View.INVISIBLE);

        layNew.setVisibility(View.VISIBLE);
        sure.setVisibility(View.VISIBLE);
        newCode.setVisibility(View.VISIBLE);
    }
}
