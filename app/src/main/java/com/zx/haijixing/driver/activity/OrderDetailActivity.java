package com.zx.haijixing.driver.activity;

import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zx.haijixing.R;
import com.zx.haijixing.share.PathConstant;
import com.zx.haijixing.share.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;
import zx.com.skytool.ZxStatusBarCompat;

/**
 * @作者 zx
 * @创建日期 2019/7/4 14:41
 * @描述 订单详情
 */
@Route(path = PathConstant.DRIVER_ORDER_DETAIL)
public class OrderDetailActivity extends BaseActivity {

    @BindView(R.id.order_detail_back)
    ImageView back;
    @BindView(R.id.order_detail_title)
    TextView title;
    @BindView(R.id.order_detail_start)
    TextView start;
    @BindView(R.id.order_detail_sTime)
    TextView sTime;
    @BindView(R.id.order_detail_duration)
    TextView duration;
    @BindView(R.id.order_detail_end)
    TextView end;
    @BindView(R.id.order_detail_eTime)
    TextView eTime;
    @BindView(R.id.order_detail_number)
    TextView number;
    @BindView(R.id.order_detail_cTime)
    TextView cTime;
    @BindView(R.id.order_detail_send_man)
    TextView sendMan;
    @BindView(R.id.order_detail_send_phone)
    TextView sendPhone;
    @BindView(R.id.order_detail_send_locate)
    TextView sendLocate;
    @BindView(R.id.order_detail_receive_man)
    TextView receiveMan;
    @BindView(R.id.order_detail_receive_phone)
    TextView receivePhone;
    @BindView(R.id.order_detail_receive_locate)
    TextView receiveLocate;

    /*@BindView(R.id.order_detail_wType)
    TextView wType;
    @BindView(R.id.order_detail_wWeight)
    TextView wWeight;
    @BindView(R.id.order_detail_wFreight)
    TextView wFreight;
    @BindView(R.id.order_detail_wTotal)
    TextView wTotal;
    @BindView(R.id.order_detail_total_info)
    TextView totalInfo;
    @BindView(R.id.order_detail_rTime)
    TextView rTime;
    @BindView(R.id.order_detail_pay_way)
    TextView payWay;
    @BindView(R.id.order_detail_vip_service)
    TextView vipService;
    @BindView(R.id.order_detail_logistics_way)
    TextView logisticsWay;
    @BindView(R.id.order_detail_logistics_company)
    TextView logisticsCompany;
    @BindView(R.id.order_detail_driver_info)
    TextView driverInfo;
    @BindView(R.id.order_detail_car_number)
    TextView carNumber;
    @BindView(R.id.order_detail_remark)
    TextView remark;*/

    @BindView(R.id.stub_normal_dan)
    ViewStub stubNormalDan;
    @BindView(R.id.stub_change_dan)
    ViewStub stubChangeDan;
    @BindView(R.id.stub_normal_down)
    ViewStub stubNormalDown;
    @BindView(R.id.stub_normal_down_b)
    ViewStub stubNormalDownB;
    @BindView(R.id.stub_has_complete)
    ViewStub stubHasComplete;


    @Override
    protected void initView() {

        int m = 1;
        switch (m){
            case 0:
                View inflateNormalDan = stubNormalDan.inflate();
                View inflateNormalDownB = stubNormalDownB.inflate();

                break;
            case 1:
                View inflateChangeDan = stubChangeDan.inflate();
                View inflateNormalDown = stubNormalDown.inflate();
                break;
            case 2:
                View inflateNormalDan1 = stubNormalDan.inflate();
                break;
            case 3:
                stubNormalDan.inflate();
                stubNormalDownB.inflate();
                stubHasComplete.inflate();
                break;
            case 4:
                break;
        }
    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_detail;
    }

    @OnClick(R.id.order_detail_back)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void setStatusBar() {
        ZxStatusBarCompat.translucentStatusBar(this, true);
    }

}
