package com.zx.haijixing.driver.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bigkoo.pickerview.view.TimePickerView;
import com.zx.haijixing.R;
import com.zx.haijixing.custom.CustomGraphViewT;
import com.zx.haijixing.share.PathConstant;
import com.zx.haijixing.share.base.BaseActivity;
import com.zx.haijixing.util.HaiTool;

import butterknife.BindView;
import butterknife.OnClick;
import zx.com.skytool.ZxStatusBarCompat;

/**
 * @作者 zx
 * @创建日期 2019/7/8 17:38
 * @描述 统计
 */
@Route(path = PathConstant.STATISTICS)
public class StatisticsActivity extends BaseActivity {

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
    private TimePickerView pvTime;

    @Override
    protected void initView() {
        setTitleTopMargin(back);
        pvTime = HaiTool.initTimePickers(this,startTime,endTime);
    }
    @Override
    protected void initInjector() {

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
                break;
            case R.id.statistics_free:
                break;
            case R.id.statistics_start_time_area:
                pvTime.show();
                break;
            case R.id.statistics_end_time_area:
                break;
            case R.id.statistics_search:
                break;
        }
    }

    @Override
    public void setStatusBar() {
        ZxStatusBarCompat.translucentStatusBar(this,true);
    }
}
