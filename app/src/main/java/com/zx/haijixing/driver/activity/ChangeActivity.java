package com.zx.haijixing.driver.activity;

import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.zx.haijixing.R;
import com.zx.haijixing.share.RoutePathConstant;
import com.zx.haijixing.share.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;
/**
 *
 *@作者 zx
 *@创建日期 2019/7/9 15:40
 *@描述 资料修改
 */
@Route(path = RoutePathConstant.CHANGE)
public class ChangeActivity extends BaseActivity {

    @BindView(R.id.common_title_back)
    ImageView back;
    @BindView(R.id.common_title_title)
    TextView title;
    @BindView(R.id.change_header)
    ImageView header;
    @BindView(R.id.change_head_area)
    ConstraintLayout headArea;
    @BindView(R.id.change_name)
    TextView name;
    @BindView(R.id.change_phone_area)
    ConstraintLayout phoneArea;
    @BindView(R.id.change_password_area)
    ConstraintLayout passwordArea;
    @BindView(R.id.change_truck_area)
    ConstraintLayout truckArea;

    @Override
    protected void initView() {
        title.setText(getHaiString(R.string.change));
    }

    @Override
    protected void initInjector() {
        ARouter.getInstance().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_change;
    }

    @OnClick({R.id.common_title_back, R.id.change_head_area, R.id.change_phone_area, R.id.change_password_area, R.id.change_truck_area})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.common_title_back:
                finish();
                break;
            case R.id.change_head_area:
                break;
            case R.id.change_phone_area:
                ARouter.getInstance().build(RoutePathConstant.PHONE).navigation();
                break;
            case R.id.change_password_area:
                ARouter.getInstance().build(RoutePathConstant.PASSWORD).navigation();
                break;
            case R.id.change_truck_area:
                ARouter.getInstance().build(RoutePathConstant.VEHICLE).navigation();
                break;
        }
    }
}
