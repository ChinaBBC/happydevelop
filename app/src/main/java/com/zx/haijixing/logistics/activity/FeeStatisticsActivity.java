package com.zx.haijixing.logistics.activity;

import android.graphics.Point;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bigkoo.pickerview.view.TimePickerView;
import com.zx.haijixing.R;
import com.zx.haijixing.custom.CustomGraphViewT;
import com.zx.haijixing.logistics.contract.FeeStatisticsContract;
import com.zx.haijixing.logistics.entry.FeeStatisticsEntry;
import com.zx.haijixing.logistics.presenter.FeeStatisticsImp;
import com.zx.haijixing.share.OtherConstants;
import com.zx.haijixing.share.PathConstant;
import com.zx.haijixing.share.base.BaseActivity;
import com.zx.haijixing.util.HaiTool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import zx.com.skytool.ZxSharePreferenceUtil;

/**
 * @作者 zx
 * @创建日期 2019/7/17 11:14
 * @描述 运费核算
 */
@Route(path = PathConstant.FEE_STATISTICS)
public class FeeStatisticsActivity extends BaseActivity<FeeStatisticsImp> implements FeeStatisticsContract.FeeStatisticsView {

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

    private TimePickerView startPickerView;
    private TimePickerView endPickerView;

    private Map<String,Object> orderParams = new HashMap<>();
    private Map<String,Object> feeParams = new HashMap<>();
    private Map<String,Object> receivedParams = new HashMap<>();

    @Override
    protected void initView() {
        ZxSharePreferenceUtil instance = ZxSharePreferenceUtil.getInstance();
        instance.init(this);
        String token = (String) instance.getParam("token", "null");
        String loginType = (String) instance.getParam("login_type", "4");

        title.setText(getHaiString(R.string.statistics));
        if (loginType.equals(OtherConstants.LOGIN_MANAGER)){
            arrive.setVisibility(View.GONE);
            line3.setVisibility(View.GONE);
        }
        startPickerView = HaiTool.initTimePickers(this, start, null);
        endPickerView = HaiTool.initTimePickers(this, null, end);

        orderParams.put("token",token);
        feeParams.put("token",token);
        receivedParams.put("token",token);

        mPresenter.todayStatisticsMethod(token);
        mPresenter.feeStatisticsMethod(feeParams);
        mPresenter.orderStatisticsMethod(orderParams);
        mPresenter.receiveStatisticsMethod(receivedParams);
    }

    @Override
    protected void initInjector() {
        mPresenter = new FeeStatisticsImp();
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
                changeStatus(0);
                break;
            case R.id.fee_statistics_free:
                changeStatus(1);
                break;
            case R.id.fee_statistics_arrive:
                changeStatus(2);
                break;
            case R.id.fee_statistics_start:
                startPickerView.show();
                break;
            case R.id.fee_statistics_end:
                endPickerView.show();
                break;
            case R.id.fee_statistics_search:

                break;
        }
    }

    private void changeStatus(int tag){
        switch (tag){
            case 0:
                bills.setTextColor(getHaiColor(R.color.color_703f));
                free.setTextColor(getHaiColor(R.color.color_9999));
                arrive.setTextColor(getHaiColor(R.color.color_9999));
                line1.setVisibility(View.VISIBLE);
                line2.setVisibility(View.INVISIBLE);
                line3.setVisibility(View.INVISIBLE);
                break;
            case 1:
                bills.setTextColor(getHaiColor(R.color.color_9999));
                free.setTextColor(getHaiColor(R.color.color_703f));
                arrive.setTextColor(getHaiColor(R.color.color_9999));
                line1.setVisibility(View.INVISIBLE);
                line2.setVisibility(View.VISIBLE);
                line3.setVisibility(View.INVISIBLE);
                break;
            case 2:
                bills.setTextColor(getHaiColor(R.color.color_9999));
                free.setTextColor(getHaiColor(R.color.color_9999));
                arrive.setTextColor(getHaiColor(R.color.color_703f));
                line1.setVisibility(View.INVISIBLE);
                line2.setVisibility(View.INVISIBLE);
                line3.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public void orderStatisticsSuccess(FeeStatisticsEntry feeStatisticsEntry) {
        List<String> rows = feeStatisticsEntry.getRows();
        List<Point> points = new ArrayList<>();
        List<String> yUnit = new ArrayList<>();
        Point point = null;
        int temp = 0;
        for (int i = 0; i< rows.size();i++){
            int y = Integer.parseInt(rows.get(i));
            point = new Point(i, y);
            points.add(point);
            if (temp<y)
                temp = y;
        }
        int num = (temp-temp%5+5)/5;
        for (int i=1;i<6;i++){
            yUnit.add(i*num+"单");
        }
        statisticsMap.setXUnitValue(1).setYUnitValue(num).setXTextUnits(feeStatisticsEntry.getDays()).setYTextUnits(yUnit).setDateList(points).startDraw();
    }

    @Override
    public void feeStatisticsSuccess(FeeStatisticsEntry feeStatisticsEntry) {

        //statisticsMap.setDateList()
    }

    @Override
    public void receiveStatisticsSuccess(FeeStatisticsEntry feeStatisticsEntry) {

    }

    @Override
    public void todayStatisticsSuccess(String countNum, String totalPrice, String toPayMoney) {
        month.setText(countNum+"单");
        day.setText(totalPrice+"元");
        other.setText(toPayMoney+"元");
    }
}
