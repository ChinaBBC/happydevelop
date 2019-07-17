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
 * @创建日期 2019/7/9 17:41
 * @描述 车辆信息修改
 */
@Route(path = PathConstant.TRUCK_CHANGE)
public class TruckChangeActivity extends BaseActivity {

    @BindView(R.id.common_title_back)
    ImageView back;
    @BindView(R.id.common_title_title)
    TextView title;
    @BindView(R.id.truck_change_cure)
    TextView cureTime;
    @BindView(R.id.truck_change_cure_area)
    ConstraintLayout cureArea;
    @BindView(R.id.truck_change_driving)
    ConstraintLayout driving;
    @BindView(R.id.truck_change_drive)
    ConstraintLayout drive;
    @BindView(R.id.truck_change_ensure)
    ConstraintLayout ensure;
    @BindView(R.id.truck_change_truck)
    ConstraintLayout truck;
    @BindView(R.id.truck_change_other)
    ConstraintLayout other;

    @Override
    protected void initView() {
        title.setText(getHaiString(R.string.truck_manager));
    }

    @Override
    protected void initInjector() {
        ARouter.getInstance().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_truck_change;
    }

    @OnClick({R.id.common_title_back, R.id.truck_change_cure_area, R.id.truck_change_driving, R.id.truck_change_drive, R.id.truck_change_ensure, R.id.truck_change_truck, R.id.truck_change_other})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.common_title_back:
                finish();
                break;
            case R.id.truck_change_cure_area:

                break;
            case R.id.truck_change_driving:
                ARouter.getInstance().build(PathConstant.PAPERS)
                        .withString("title",getHaiString(R.string.driving))
                        .navigation();
                break;
            case R.id.truck_change_drive:
                ARouter.getInstance().build(PathConstant.PAPERS)
                        .withString("title",getHaiString(R.string.truck_drive))
                        .navigation();
                break;
            case R.id.truck_change_ensure:
                ARouter.getInstance().build(PathConstant.PAPERS)
                        .withString("title",getHaiString(R.string.truck_cure_id))
                        .navigation();
                break;
            case R.id.truck_change_truck:
                ARouter.getInstance().build(PathConstant.PAPERS)
                        .withString("title",getHaiString(R.string.truck))
                        .navigation();
                break;
            case R.id.truck_change_other:
                ARouter.getInstance().build(PathConstant.PAPERS)
                        .withString("title",getHaiString(R.string.other))
                        .navigation();
                break;
        }
    }
}
