package com.zx.haijixing.driver.activity;

import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bigkoo.pickerview.view.TimePickerView;
import com.zx.haijixing.R;
import com.zx.haijixing.driver.contract.TruckChangeContract;
import com.zx.haijixing.driver.entry.TruckDetailEntry;
import com.zx.haijixing.driver.presenter.TruckChangeImp;
import com.zx.haijixing.share.OtherConstants;
import com.zx.haijixing.share.PathConstant;
import com.zx.haijixing.share.base.BaseActivity;
import com.zx.haijixing.util.HaiTool;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import zx.com.skytool.ZxSharePreferenceUtil;
import zx.com.skytool.ZxStringUtil;
import zx.com.skytool.ZxToastUtil;

/**
 * @作者 zx
 * @创建日期 2019/7/9 17:41
 * @描述 车辆信息修改
 */
@Route(path = PathConstant.TRUCK_CHANGE)
public class TruckChangeActivity extends BaseActivity<TruckChangeImp> implements TruckChangeContract.TruckChangeView {

    @BindView(R.id.common_title_back)
    ImageView back;
    @BindView(R.id.common_title_title)
    TextView title;
    @BindView(R.id.truck_change_cure)
    TextView cureTime;
    @BindView(R.id.truck_change_type)
    TextView type;
    @BindView(R.id.truck_change_weight)
    TextView weight;
    @BindView(R.id.truck_change_save)
    Button save;

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

    @Autowired(name = "truckId")
    public String truckId;
    @Autowired(name = "truckName")
    public String truckName;
    private TimePickerView timePickerView;
    private TruckDetailEntry.TruckInfoBean truckInfoBean = null;
    private String path = null;

    private String liA = null;
    private String liB = null;
    private String token;
    private String time = "null";

    @Override
    protected void initView() {

        ZxSharePreferenceUtil instance = ZxSharePreferenceUtil.getInstance();
        instance.init(this);
        token = (String) instance.getParam("token", "null");
        title.setText(truckName);
        mPresenter.truckInfoMethod(truckId);
        mPresenter.driverIdentifyMethod(token);

        timePickerView = HaiTool.initTimePickers(this, cureTime, null);
        time = cureTime.getText().toString().trim();
    }

    @Override
    protected void initInjector() {
        mPresenter = new TruckChangeImp();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_truck_change;
    }

    @OnClick({R.id.common_title_back, R.id.truck_change_cure_area, R.id.truck_change_driving, R.id.truck_change_drive, R.id.truck_change_ensure, R.id.truck_change_truck, R.id.truck_change_other,R.id.truck_change_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.common_title_back:
                finish();
               /* String value = cureTime.getText().toString().trim();
                if (time.equals(value)){

                }else {
                    ZxToastUtil.centerToast("您有修改的数据未保存");
                }*/
                break;
            case R.id.truck_change_cure_area:
                timePickerView.show();
                break;
            case R.id.truck_change_driving:
                if (!ZxStringUtil.isEmpty(liA) && !ZxStringUtil.isEmpty(liB)){
                    ARouter.getInstance().build(PathConstant.PAPERS)
                            .withString("title",getHaiString(R.string.driving))
                            .withString("one",liA)
                            .withString("two",liB)
                            .withString("three",null)
                            .withString("truckId",truckId)
                            .withInt("tag",OtherConstants.CHANGE_DRIVER_ID)
                            .navigation();
                }else {
                    ZxToastUtil.centerToast("请稍后再试");
                }
                break;
            case R.id.truck_change_drive:
                if (truckInfoBean != null)
                    if (truckInfoBean != null){
                        goPapers(getHaiString(R.string.truck_drive),path+truckInfoBean.getDrivingFront(),path+truckInfoBean.getDrivingBack(),null,OtherConstants.CHANGE_TRUCK_ID);
                    }else {
                        ZxToastUtil.centerToast("请稍后再试");
                    }
                break;
            case R.id.truck_change_ensure:
                if (truckInfoBean != null){
                    goPapers(getHaiString(R.string.truck_cure_id),path+truckInfoBean.getSafeImage(),null,null,OtherConstants.CHANGE_CURE_ID);
                }else {
                    ZxToastUtil.centerToast("请稍后再试");
                }
                break;
            case R.id.truck_change_truck:
                if (truckInfoBean != null){
                    goPapers(getHaiString(R.string.truck),path+truckInfoBean.getCarImageFront(),path+truckInfoBean.getCarImageLeft(),path+truckInfoBean.getCarImageRight(),OtherConstants.CHANGE_TRUCK_IMG);
                }else {
                    ZxToastUtil.centerToast("请稍后再试");
                }
                break;
            case R.id.truck_change_other:
                if (truckInfoBean != null){
                    goPapers(getHaiString(R.string.other),path+truckInfoBean.getAnnex(),null,null,OtherConstants.CHANGE_OTHER);
                }else {
                    ZxToastUtil.centerToast("请稍后再试");
                }
                break;
            case R.id.truck_change_save:
                String value2 = cureTime.getText().toString().trim();
                if (time.equals(value2)){
                    ZxToastUtil.centerToast("您还未修改数据");
                }else {
                    Map<String,Object> params = new HashMap<>();
                    params.put("token",token);
                    params.put("applyId",truckId);
                    params.put("safeEnd", value2);
                    mPresenter.changeTimeMethod(params);
                }
                break;
        }
    }

    private void goPapers(String title,String one,String two,String three,int tag){
        ARouter.getInstance().build(PathConstant.PAPERS)
                .withString("title",title)
                .withString("one",one)
                .withString("two",two)
                .withString("three",three)
                .withInt("tag",tag)
                .withString("truckId",truckId)
                .navigation();
    }
    @Override
    public void truckInfoSuccess(TruckDetailEntry.TruckInfoBean truckInfoBean, String basePath) {
        this.truckInfoBean = truckInfoBean;
        this.path = basePath;
        cureTime.setText(truckInfoBean.getSafeEnd());
        type.setText(truckInfoBean.getCaeType());
        weight.setText(truckInfoBean.getCarLoad()+"KG");
    }

    @Override
    public void driverIdentifySuccess(String identifyA, String identifyB) {
        this.liA = identifyA;
        this.liB = identifyB;
    }

    @Override
    public void changeTimeSuccess(String msg) {
        ZxToastUtil.centerToast(msg);
        finish();
    }
}
