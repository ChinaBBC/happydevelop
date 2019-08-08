package com.zx.haijixing.logistics.activity;

import android.graphics.Point;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.bigkoo.pickerview.view.TimePickerView;
import com.zx.haijixing.R;
import com.zx.haijixing.custom.CustomGraphViewT;
import com.zx.haijixing.driver.entry.MyPoint;
import com.zx.haijixing.logistics.adapter.CompanyWheel;
import com.zx.haijixing.logistics.adapter.DriverTruckAdapter;
import com.zx.haijixing.logistics.contract.FeeStatisticsContract;
import com.zx.haijixing.logistics.entry.CompanyEntry;
import com.zx.haijixing.logistics.entry.DriverEntry;
import com.zx.haijixing.logistics.entry.FeeStatisticsEntry;
import com.zx.haijixing.logistics.presenter.FeeStatisticsImp;
import com.zx.haijixing.share.OtherConstants;
import com.zx.haijixing.share.PathConstant;
import com.zx.haijixing.share.base.BaseActivity;
import com.zx.haijixing.util.CommonDialogFragment;
import com.zx.haijixing.util.HaiDialogUtil;
import com.zx.haijixing.util.HaiTool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import zx.com.skytool.ZxLogUtil;
import zx.com.skytool.ZxSharePreferenceUtil;
import zx.com.skytool.ZxToastUtil;

/**
 * @作者 zx
 * @创建日期 2019/7/17 11:14
 * @描述 运费核算
 */
@Route(path = PathConstant.FEE_STATISTICS)
public class FeeStatisticsActivity extends BaseActivity<FeeStatisticsImp> implements FeeStatisticsContract.FeeStatisticsView,HaiDialogUtil.TruckResultListener {

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
    @BindView(R.id.fee_statistics_add_all)
    TextView addAll;
    @BindView(R.id.fee_statistics_namePhone)
    TextView namePhone;
    @BindView(R.id.fee_statistics_word6)
    TextView word6;
    @BindView(R.id.fee_statistics_search)
    Button search;
    @BindView(R.id.fee_statistics_map)
    CustomGraphViewT statisticsMap;

    @Autowired(name = "companys")
    public ArrayList<CompanyEntry> companys;

    private TimePickerView startPickerView;
    private TimePickerView endPickerView;

    private Map<String,String> params = new HashMap<>();
    private int tag = 0;
    private DriverTruckAdapter driverAdapter;
    private List<DriverEntry> driverEntries = new ArrayList<>();
    private CommonDialogFragment showDriver;
    private int driverIndex = 0;
    private String loginType;

    private ArrayList<CompanyEntry> companyEntries = new ArrayList<>();

    @Override
    protected void initView() {
        ZxSharePreferenceUtil instance = ZxSharePreferenceUtil.getInstance();
        instance.init(this);
        String token = (String) instance.getParam("token", "null");
        loginType = (String) instance.getParam("login_type", "4");

        title.setText(getHaiString(R.string.statistics));
        startPickerView = HaiTool.initTimePickers(this, start, 1111);
        endPickerView = HaiTool.initTimePickers(this, end, 0);

        params.put("token",token);
        params.put("timestamp",System.currentTimeMillis()+"");
        params.put("sign","");
        params.put("sign",HaiTool.sign(params));
        if (loginType.equals(OtherConstants.LOGIN_MANAGER)){
            arrive.setVisibility(View.GONE);
            line3.setVisibility(View.GONE);
            word6.setText("物流公司:");
            companyEntries.add(new CompanyEntry("","","全部"));
            companyEntries.addAll(companys);
        }else {
            mPresenter.searchDriverMethod(params);
        }

        mPresenter.todayStatisticsMethod(params);
        mPresenter.orderStatisticsMethod(params);
    }

    @Override
    protected void initInjector() {
        mPresenter = new FeeStatisticsImp();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_fee_statistics;
    }

    @OnClick({R.id.common_title_back, R.id.fee_statistics_bills, R.id.fee_statistics_free,R.id.fee_statistics_input,
            R.id.fee_statistics_arrive, R.id.fee_statistics_start, R.id.fee_statistics_end, R.id.fee_statistics_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.common_title_back:
                finish();
                break;
            case R.id.fee_statistics_bills:
                tag = 0;
                changeStatus(0);
                break;
            case R.id.fee_statistics_free:
                tag = 1;
                changeStatus(1);
                break;
            case R.id.fee_statistics_arrive:
                tag = 2;
                changeStatus(2);
                break;
            case R.id.fee_statistics_start:
                startPickerView.show();
                break;
            case R.id.fee_statistics_end:
                endPickerView.show();
                break;
            case R.id.fee_statistics_search:
                checkAndSearch();
                break;
            case R.id.fee_statistics_input:
                driverIndex = 0;
                if (loginType.equals(OtherConstants.LOGIN_MANAGER)){
                    CompanyWheel companyWheel = new CompanyWheel(companyEntries);
                    showDriver = HaiDialogUtil.showTruck(getSupportFragmentManager(), companyWheel, this::truckResult);
                }else {
                    if (driverAdapter != null){
                        showDriver = HaiDialogUtil.showTruck(getSupportFragmentManager(), driverAdapter, this::truckResult);
                    }else {
                        ZxToastUtil.centerToast("请稍候再试!");
                    }
                }
                break;
        }
    }

    private void checkAndSearch(){
        String sTime = start.getText().toString().trim();
        String eTime = end.getText().toString().trim();
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
            case 2:
                mPresenter.receiveStatisticsMethod(params);
                break;
        }
    }

    private void changeStatus(int tag){
        params.put("timestamp",System.currentTimeMillis()+"");
        params.put("sign","");
        params.put("sign",HaiTool.sign(params));
        switch (tag){
            case 0:
                bills.setTextColor(getHaiColor(R.color.color_703f));
                free.setTextColor(getHaiColor(R.color.color_9999));
                arrive.setTextColor(getHaiColor(R.color.color_9999));
                line1.setVisibility(View.VISIBLE);
                line2.setVisibility(View.INVISIBLE);
                line3.setVisibility(View.INVISIBLE);
                mPresenter.orderStatisticsMethod(params);
                break;
            case 1:
                bills.setTextColor(getHaiColor(R.color.color_9999));
                free.setTextColor(getHaiColor(R.color.color_703f));
                arrive.setTextColor(getHaiColor(R.color.color_9999));
                line1.setVisibility(View.INVISIBLE);
                line2.setVisibility(View.VISIBLE);
                line3.setVisibility(View.INVISIBLE);
                mPresenter.feeStatisticsMethod(params);
                break;
            case 2:
                bills.setTextColor(getHaiColor(R.color.color_9999));
                free.setTextColor(getHaiColor(R.color.color_9999));
                arrive.setTextColor(getHaiColor(R.color.color_703f));
                line1.setVisibility(View.INVISIBLE);
                line2.setVisibility(View.INVISIBLE);
                line3.setVisibility(View.VISIBLE);
                mPresenter.receiveStatisticsMethod(params);
                break;
        }
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
        setData(feeStatisticsEntry,"元");
    }

    @Override
    public void todayStatisticsSuccess(String countNum, String totalPrice, String toPayMoney) {
        month.setText(countNum+"单");
        day.setText(totalPrice+"元");
        other.setText(toPayMoney+"元");
    }

    @Override
    public void searchDriverSuccess(List<DriverEntry> driverEntries) {
        this.driverEntries.add(new DriverEntry(null,"全部"));
        this.driverEntries.addAll(driverEntries);
        driverAdapter = new DriverTruckAdapter(this.driverEntries, null, OtherConstants.SELECT_DRIVER);
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

    @Override
    public void truckResult(int index) {
        if (index == -1){
            showDriver.dismissAllowingStateLoss();
            if (loginType.equals(OtherConstants.LOGIN_MANAGER)){
                CompanyEntry companyEntry = companyEntries.get(driverIndex);
                params.put("lgsId", companyEntry.getLgsId());
                namePhone.setText(companyEntry.getcName());
            }else {
                DriverEntry driverEntry = driverEntries.get(driverIndex);
                params.put("driverId", driverEntry.getDriverId());
                namePhone.setText(driverEntry.getDriverName());
            }
        }else if (index == -2){
           showDriver.dismissAllowingStateLoss();
        }else {
            driverIndex = index;
        }
    }
}
