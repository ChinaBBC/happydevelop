package com.zx.haijixing.driver.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.zx.haijixing.R;
import com.zx.haijixing.driver.adapter.VehicleAdapter;
import com.zx.haijixing.driver.contract.VehicleContract;
import com.zx.haijixing.driver.entry.TruckEntry;
import com.zx.haijixing.driver.presenter.VehicleImp;
import com.zx.haijixing.share.PathConstant;
import com.zx.haijixing.share.base.BaseActivity;
import com.zx.haijixing.util.HaiTool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import zx.com.skytool.ZxSharePreferenceUtil;

/**
 * @作者 zx
 * @创建日期 2019/7/9 17:32
 * @描述 车辆管理
 */
@Route(path = PathConstant.VEHICLE)
public class VehicleActivity extends BaseActivity<VehicleImp> implements VehicleContract.VehicleView {

    @BindView(R.id.common_title_back)
    ImageView back;
    @BindView(R.id.common_title_add)
    ImageView add;
    @BindView(R.id.common_title_title)
    TextView title;
    @BindView(R.id.vehicle_data)
    RecyclerView vehicleData;

    private VehicleAdapter vehicleAdapter;

    @Override
    protected void initView() {
        title.setText(getHaiString(R.string.truck_manager));
        //add.setVisibility(View.VISIBLE);
        vehicleData.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        vehicleAdapter = new VehicleAdapter();
        vehicleData.setAdapter(vehicleAdapter);

        ZxSharePreferenceUtil instance = ZxSharePreferenceUtil.getInstance();
        instance.init(this);
        String token = (String) instance.getParam("token", "null");

        Map<String,String> params = new HashMap<>();
        params.put("token",token);
        params.put("timestamp",System.currentTimeMillis()+"");
        params.put("sign","");
        params.put("sign",HaiTool.sign(params));
        mPresenter.vehicleMethod(params);
    }

    @Override
    protected void initInjector() {
        mPresenter = new VehicleImp();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_vehicle;
    }

    @OnClick({R.id.common_title_back,R.id.common_title_add})
    public void onViewClicked(View view) {
        if (view.getId() == R.id.common_title_add){
            ARouter.getInstance().build(PathConstant.TRUCK).navigation();
        }else {
            finish();
        }
    }

    @Override
    public void vehicleSuccess(List<TruckEntry> truckEntries) {
        vehicleAdapter.setTruckEntries(truckEntries);
        vehicleAdapter.notifyDataSetChanged();
    }
}
