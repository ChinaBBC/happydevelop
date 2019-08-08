package com.zx.haijixing.driver.activity;

import android.graphics.Point;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bigkoo.pickerview.view.TimePickerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.zx.haijixing.BuildConfig;
import com.zx.haijixing.HaiNativeHelper;
import com.zx.haijixing.R;
import com.zx.haijixing.custom.CustomGraphViewT;
import com.zx.haijixing.driver.entry.MyPoint;
import com.zx.haijixing.logistics.contract.FeeStatisticsContract;
import com.zx.haijixing.logistics.entry.DriverEntry;
import com.zx.haijixing.logistics.entry.FeeStatisticsEntry;
import com.zx.haijixing.logistics.presenter.FeeStatisticsImp;
import com.zx.haijixing.share.PathConstant;
import com.zx.haijixing.share.base.BaseActivity;
import com.zx.haijixing.util.HaiTool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import zx.com.skytool.ZxSharePreferenceUtil;
import zx.com.skytool.ZxStatusBarCompat;

/**
 * @作者 zx
 * @创建日期 2019/7/8 17:38
 * @描述 统计
 */
@Route(path = PathConstant.STATISTICS)
public class StatisticsActivity extends BaseActivity<FeeStatisticsImp> implements FeeStatisticsContract.FeeStatisticsView {

    @BindView(R.id.statistics_back)
    ImageView back;
    @BindView(R.id.statistics_user_head)
    ImageView userHead;
    @BindView(R.id.statistics_user_name)
    TextView userName;
    @BindView(R.id.statistics_user_phone)
    TextView userPhone;
    @BindView(R.id.statistics_month)
    TextView statisticsMonth;
    @BindView(R.id.statistics_day)
    TextView statisticsDay;
    @BindView(R.id.statistics_other)
    TextView statisticsOther;

    @BindView(R.id.statistics_bill)
    TextView statisticsBill;
    @BindView(R.id.statistics_line1)
    TextView statisticsLine1;

    @BindView(R.id.statistics_free)
    TextView statisticsFree;
    @BindView(R.id.statistics_line2)
    TextView statisticsLine2;

    @BindView(R.id.statistics_start_time)
    TextView startTime;
    @BindView(R.id.statistics_start_time_area)
    LinearLayout startTimeArea;
    @BindView(R.id.statistics_end_time)
    TextView endTime;
    @BindView(R.id.statistics_end_time_area)
    LinearLayout endTimeArea;
    @BindView(R.id.statistics_search)
    TextView search;
    @BindView(R.id.statistics_add_all)
    TextView addAll;
    @BindView(R.id.statistics_map)
    CustomGraphViewT statisticsMap;
    private TimePickerView sTime;
    private TimePickerView eTime;

    private Map<String,String> params = new HashMap<>();
    private int tag = 0;

    @Override
    protected void initView() {
        setTitleTopMargin(back);
        ZxSharePreferenceUtil instance = ZxSharePreferenceUtil.getInstance();
        instance.init(this);
        String name = (String)instance.getParam("user_name","null");
        String phone = (String)instance.getParam("user_phone","null");
        String head = (String)instance.getParam("user_head","null");
        String token = (String)instance.getParam("token","null");

        userName.setText(name);
        userPhone.setText(phone);
        RequestOptions options = new RequestOptions().circleCrop().error(R.mipmap.user_head);
        Glide.with(this).load(head).apply(options).into(userHead);
        sTime = HaiTool.initTimePickers(this,startTime,1000);
        eTime = HaiTool.initTimePickers(this,endTime,0);

        params.put("token",token);
        params.put("timestamp",System.currentTimeMillis()+"");
        params.put("sign","");
        params.put("sign",HaiTool.sign(params));

        mPresenter.todayStatisticsMethod(params);
        mPresenter.orderStatisticsMethod(params);
    }
    @Override
    protected void initInjector() {
        mPresenter = new FeeStatisticsImp();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_statistics;
    }

    @OnClick({R.id.statistics_back, R.id.statistics_bill, R.id.statistics_free, R.id.statistics_start_time_area, R.id.statistics_end_time_area, R.id.statistics_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.statistics_back:
                finish();
                break;
            case R.id.statistics_bill:
                tag = 0;
                changeStatus(tag);
                break;
            case R.id.statistics_free:
                tag = 1;
                changeStatus(tag);
                break;
            case R.id.statistics_start_time_area:
                sTime.show();
                break;
            case R.id.statistics_end_time_area:
                eTime.show();
                break;
            case R.id.statistics_search:
                checkAndSearch();
                break;
        }
    }
    private void checkAndSearch(){
        String sTime = startTime.getText().toString().trim();
        String eTime = endTime.getText().toString().trim();
        params.put("startTime",sTime);
        params.put("endTime",eTime);
        params.put("timestamp",System.currentTimeMillis()+"");
        params.put("sign","");
        params.put("sign",HaiTool.sign(params));
        switch (tag){
            case 0:
                mPresenter.orderStatisticsMethod(params);
                break;
            case 1:
                mPresenter.feeStatisticsMethod(params);
                break;
        }
    }

    private void changeStatus(int tag){
        switch (tag){
            case 0:
                statisticsBill.setTextColor(getHaiColor(R.color.color_703f));
                statisticsFree.setTextColor(getHaiColor(R.color.color_9999));
                statisticsLine1.setVisibility(View.VISIBLE);
                statisticsLine2.setVisibility(View.INVISIBLE);
                mPresenter.orderStatisticsMethod(params);
                break;
            case 1:
                statisticsBill.setTextColor(getHaiColor(R.color.color_9999));
                statisticsFree.setTextColor(getHaiColor(R.color.color_703f));
                statisticsLine1.setVisibility(View.INVISIBLE);
                statisticsLine2.setVisibility(View.VISIBLE);
                mPresenter.feeStatisticsMethod(params);
                break;
        }
    }
    @Override
    public void setStatusBar() {
        ZxStatusBarCompat.translucentStatusBar(this,true);
    }

    @Override
    public void orderStatisticsSuccess(FeeStatisticsEntry feeStatisticsEntry) {
        setData(feeStatisticsEntry,"单");
    }

    @Override
    public void feeStatisticsSuccess(FeeStatisticsEntry feeStatisticsEntry) {
        setData(feeStatisticsEntry,"元");
    }

    @Override
    public void receiveStatisticsSuccess(FeeStatisticsEntry feeStatisticsEntry) {

    }

    @Override
    public void todayStatisticsSuccess(String countNum, String totalPrice, String toPayMoney) {
        statisticsMonth.setText(countNum+"单");
        statisticsDay.setText(totalPrice+"元");
        statisticsOther.setText(toPayMoney+"元");
    }

    @Override
    public void searchDriverSuccess(List<DriverEntry> driverEntries) {

    }

    private void setData(FeeStatisticsEntry feeStatisticsEntry,String text){
        List<String> rows = feeStatisticsEntry.getRows();
        List<MyPoint> points = new ArrayList<>();
        List<String> yUnit = new ArrayList<>();
        MyPoint point = null;
        float temp = 0;
        float total = 0;
        for (int i = 0; i< rows.size();i++){
            float y = Float.parseFloat(rows.get(i));
            point = new MyPoint(i, y);
            points.add(point);
            total+=y;
            if (temp<y)
                temp = y;
        }

        int max = new Float(temp).intValue();
        int num = (max-max%5+5)/5;
        for (int i=1;i<6;i++){
            yUnit.add(i*num+"");
        }
        addAll.setText(total+text);
        statisticsMap.setXUnitValue(1).setYUnitValue(num).setXTextUnits(feeStatisticsEntry.getDays()).setYTextUnits(yUnit).setDateList(points).startDraw();
    }
}
