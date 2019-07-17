package com.zx.haijixing.logistics.activity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

/**
 * @作者 zx
 * @创建日期 2019/7/17 11:14
 * @描述 运费核算
 */
@Route(path = PathConstant.FEE_STATISTICS)
public class FeeStatisticsActivity extends BaseActivity {

    @BindView(R.id.common_title_title)
    TextView title;
    @BindView(R.id.fee_statistics_month)
    TextView month;
    @BindView(R.id.fee_statistics_day)
    TextView day;
    @BindView(R.id.fee_statistics_other)
    TextView other;
    @BindView(R.id.fee_statistics_bills)
    TextView bills;
    @BindView(R.id.fee_statistics_line1)
    View line1;
    @BindView(R.id.fee_statistics_free)
    TextView free;
    @BindView(R.id.fee_statistics_line2)
    View line2;
    @BindView(R.id.fee_statistics_arrive)
    TextView arrive;
    @BindView(R.id.fee_statistics_line3)
    View line3;
    @BindView(R.id.fee_statistics_start)
    TextView start;
    @BindView(R.id.fee_statistics_end)
    TextView end;
    @BindView(R.id.fee_statistics_input)
    EditText input;
    @BindView(R.id.fee_statistics_search)
    Button search;
    @BindView(R.id.fee_statistics_map)
    CustomGraphViewT statisticsMap;
    private TimePickerView timePickerView;

    private int loginType = 1;
    @Override
    protected void initView() {
        title.setText(getHaiString(R.string.statistics));
        if (loginType == 1){
            arrive.setVisibility(View.GONE);
            line3.setVisibility(View.GONE);
        }
        timePickerView = HaiTool.initTimePickers(this, start, end);
    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_fee_statistics;
    }

    @OnClick({R.id.common_title_back, R.id.fee_statistics_bills, R.id.fee_statistics_free, R.id.fee_statistics_arrive, R.id.fee_statistics_start, R.id.fee_statistics_end, R.id.fee_statistics_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.common_title_back:
                finish();
                break;
            case R.id.fee_statistics_bills:
                break;
            case R.id.fee_statistics_free:
                break;
            case R.id.fee_statistics_arrive:
                break;
            case R.id.fee_statistics_start:
                timePickerView.show();
                break;
            case R.id.fee_statistics_end:
                timePickerView.show();
                break;
            case R.id.fee_statistics_search:
                break;
        }
    }
}
