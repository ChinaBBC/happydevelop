package com.zx.haijixing.driver.activity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zx.haijixing.R;
import com.zx.haijixing.share.PathConstant;
import com.zx.haijixing.share.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;
/**
 *
 *@作者 zx
 *@创建日期 2019/7/9 16:54
 *@描述 修改密码
 */
@Route(path = PathConstant.PASSWORD)
public class PasswordActivity extends BaseActivity {

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

    @Override
    protected void initView() {
        title.setText(getHaiString(R.string.change_password));
    }

    @Override
    protected void initInjector() {

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
                break;
            case R.id.password_sure:
                break;
        }
    }
}
