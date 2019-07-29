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
import com.zx.haijixing.BuildConfig;
import com.zx.haijixing.HaiNativeHelper;
import com.zx.haijixing.R;
import com.zx.haijixing.custom.CustomGraphViewT;
import com.zx.haijixing.share.PathConstant;
import com.zx.haijixing.share.base.BaseActivity;
import com.zx.haijixing.util.HaiTool;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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

    private void test(){
        Log.i("<<<<<","<BuildConfig.homeUrl>"+BuildConfig.homeUrl+HaiNativeHelper.baseUrl());

        List<String> list1 = new ArrayList<>();
        list1.add("1");
        list1.add("2");
        list1.add("3");
        list1.add("4");
        list1.add("5");
        list1.add("6");
        list1.add("7");
        list1.add("8");
        list1.add("9");
        list1.add("11");
        list1.add("12");
        list1.add("13");
        list1.add("14");
        list1.add("15");
        list1.add("16");
        list1.add("17");

        List<String> list2 = new ArrayList<>();
        list2.add("2千元");
        list2.add("4千元");
        list2.add("6千元");
        list2.add("8千元");
        list2.add("10千元");
        list2.add("12千元");

        List<Point> points = new ArrayList<>();
        points.add(new Point(0,2999));
        points.add(new Point(1,2324));
        points.add(new Point(2,1223));
        points.add(new Point(3,4545));
        points.add(new Point(4,6643));
        points.add(new Point(5,7777));
        points.add(new Point(6,8888));
        points.add(new Point(7,1234));
        points.add(new Point(8,2234));
        points.add(new Point(9,3234));
        points.add(new Point(10,3234));
        points.add(new Point(11,3234));
        points.add(new Point(12,3234));
        points.add(new Point(13,3234));
        points.add(new Point(14,3234));
        points.add(new Point(15,3234));
        points.add(new Point(16,3234));
        points.add(new Point(17,3234));

        ///customGraphView2.setXUnitValue(1).setYUnitValue(2000).setXTextUnits(list1).setYTextUnits(list2).setDateList(points).startDraw();

        LinkedList<Double> yList = new LinkedList<>();
        yList.add(200.203);
        yList.add(400.05);
        yList.add(600.60);
        yList.add(300.08);
        yList.add(400.32);
        yList.add(220.0);
        yList.add(550.0);

        LinkedList<String> xRawData = new LinkedList<>();
        xRawData.add("05-19");
        xRawData.add("05-20");
        xRawData.add("05-21");
        xRawData.add("05-22");
        xRawData.add("05-23");
        xRawData.add("05-24");
        xRawData.add("05-25");
        //customGraphView.setData(yList , xRawData , 10000 , 500);
    }
}
