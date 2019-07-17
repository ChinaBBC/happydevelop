package com.zx.haijixing.login.activity;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.zx.haijixing.R;
import com.zx.haijixing.login.contract.ICarInfoActivityContract;
import com.zx.haijixing.login.presenter.CarInfoActivityImp;
import com.zx.haijixing.share.PathConstant;
import com.zx.haijixing.share.base.BaseActivity;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import zx.com.skytool.ZxStringUtil;
import zx.com.skytool.ZxToastUtil;

/**
 * @作者 zx
 * @创建日期 2019/6/28 11:38
 * @描述 车辆信息
 */
@Route(path = PathConstant.CAR_INFO)
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

    @Autowired(name = "driverId")
    public String driverId;

    private String strA = "dsdadadas";
    private String strB = "dssddasdas";

    @Override
    protected void initView() {
        title.setText(getHaiString(R.string.register));
    }

    @Override
    protected void initInjector() {
        mPresenter = new CarInfoActivityImp();
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
                ARouter.getInstance().build(PathConstant.TRUCK).navigation();
                //checkUpload();
                break;
        }
    }

    @Override
    public void carInfoSuccess() {
        ARouter.getInstance().build(PathConstant.TRUCK).withString("driverId",driverId).navigation();
        finish();
    }

    private void checkUpload(){
        if (ZxStringUtil.isEmpty(strA)){
            ZxToastUtil.centerToast(getHaiString(R.string.upload_driving_A));
        }else if (ZxStringUtil.isEmpty(strB)){
            ZxToastUtil.centerToast(getHaiString(R.string.upload_driving_B));
        }else{
            Map<String,String> params = new HashMap<>();
            params.put("userId",driverId);
            params.put("driverCardFront",strA);
            params.put("driverCardBack",strB);
            params.put("driverCardFrontS",strA);//缩略图
            params.put("driverCardBackS",strB);

            mPresenter.carInfoMethod(params);
        }
    }
}
