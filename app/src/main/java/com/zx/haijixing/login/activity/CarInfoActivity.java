package com.zx.haijixing.login.activity;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.zx.haijixing.R;
import com.zx.haijixing.login.contract.ICarInfoActivityContract;
import com.zx.haijixing.login.presenter.CarInfoActivityImp;
import com.zx.haijixing.share.RoutePathConstant;
import com.zx.haijixing.share.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;
import zx.com.skytool.ZxStatusBarCompat;

/**
 * @作者 zx
 * @创建日期 2019/6/28 11:38
 * @描述 车辆信息
 */
@Route(path = RoutePathConstant.CAR_INFO)
public class CarInfoActivity extends BaseActivity<CarInfoActivityImp> implements ICarInfoActivityContract.CarInfoView {

    @BindView(R.id.common_title_back)
    ImageView back;
    @BindView(R.id.common_title_title)
    TextView title;
    @BindView(R.id.car_info_licenseA)
    ImageView licenseA;
    @BindView(R.id.car_info_graphA)
    ImageView graphA;
    @BindView(R.id.car_info_wordA)
    TextView wordA;
    @BindView(R.id.car_info_licenseB)
    ImageView licenseB;
    @BindView(R.id.car_info_graphB)
    ImageView graphB;
    @BindView(R.id.car_info_wordB)
    TextView wordB;
    @BindView(R.id.car_info_next)
    Button next;

    @Override
    protected void initView() {
        ZxStatusBarCompat.setStatusBarLightMode(this);
        title.setText(getHaiString(R.string.register));
    }

    @Override
    protected void initInjector() {
        ARouter.getInstance().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_car_info;
    }

    @OnClick({R.id.common_title_back, R.id.car_info_graphA, R.id.car_info_graphB, R.id.car_info_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.common_title_back:
                finish();
                break;
            case R.id.car_info_graphA:
                break;
            case R.id.car_info_graphB:
                break;
            case R.id.car_info_next:
                ARouter.getInstance().build(RoutePathConstant.CHECKING).navigation();
                break;
        }
    }

    @Override
    public void carInfoSuccess() {

    }
}
