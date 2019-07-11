package com.zx.haijixing.driver.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.zx.haijixing.R;
import com.zx.haijixing.custom.CustomGraphViewT;
import com.zx.haijixing.share.RoutePathConstant;
import com.zx.haijixing.share.base.BaseActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zx.com.skytool.ZxStatusBarCompat;

/**
 * @作者 zx
 * @创建日期 2019/7/8 17:38
 * @描述 统计
 */
@Route(path = RoutePathConstant.STATISTICS)
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
        initTimePicker1();
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

    private void initTimePicker1() {//选择出生年月日
        //控制时间范围(如果不设置范围，则使用默认时间1900-2100年，此段代码可注释)
        //因为系统Calendar的月份是从0-11的,所以如果是调用Calendar的set方法来设置时间,月份的范围也要是从0-11
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        startTime.setText(getTime(curDate));
        endTime.setText(getTime(curDate));

        SimpleDateFormat formatter_year = new SimpleDateFormat("yyyy ");
        String year_str = formatter_year.format(curDate);
        int year_int = (int) Double.parseDouble(year_str);


        SimpleDateFormat formatter_mouth = new SimpleDateFormat("MM ");
        String mouth_str = formatter_mouth.format(curDate);
        int mouth_int = (int) Double.parseDouble(mouth_str);

        SimpleDateFormat formatter_day = new SimpleDateFormat("dd ");
        String day_str = formatter_day.format(curDate);
        int day_int = (int) Double.parseDouble(day_str);


        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();
        startDate.set(1900, 0, 1);
        Calendar endDate = Calendar.getInstance();
        endDate.set(year_int, mouth_int - 1, day_int);

        //时间选择器
        pvTime = new TimePickerBuilder(this, (date, v) -> startTime.setText(getTime(date)))
                .setType(new boolean[]{true, true, true, false, false, false}) //年月日时分秒 的显示与否，不设置则默认全部显示
                .setLabel("年", "月", "日", "", "", "")//默认设置为年月日时分秒
                .isCenterLabel(false)
                .setDividerColor(getHaiColor(R.color.color_6666))
                .setTextColorCenter(getHaiColor(R.color.color_3333))//设置选中项的颜色
                .setTextColorOut(getHaiColor(R.color.color_9999))//设置没有被选中项的颜色
                .setDate(selectedDate)
                .setLineSpacingMultiplier(1.2f)
                .setTextXOffset(-10, 0,10, 0, 0, 0)//设置X轴倾斜角度[ -90 , 90°]
                .setRangDate(startDate, endDate)
//                .setBackgroundId(0x00FFFFFF) //设置外部遮罩颜色
                .setDecorView(null)
                .build();
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }
}
