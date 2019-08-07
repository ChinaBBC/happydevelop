package com.zx.haijixing.logistics.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.bigkoo.pickerview.view.TimePickerView;
import com.zx.haijixing.R;
import com.zx.haijixing.logistics.adapter.CompanyWheel;
import com.zx.haijixing.logistics.adapter.RunTableAdapter;
import com.zx.haijixing.logistics.contract.RunTableContact;
import com.zx.haijixing.logistics.entry.CompanyEntry;
import com.zx.haijixing.logistics.entry.RunTableEntry;
import com.zx.haijixing.logistics.presenter.RunTableImp;
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
import zx.com.skytool.ZxSharePreferenceUtil;
import zx.com.skytool.ZxStatusBarCompat;
import zx.com.skytool.ZxToastUtil;

/**
 *
 *@作者 zx
 *@创建日期 2019/7/16 21:22
 *@描述 运营总表
 */
@Route(path = PathConstant.RUN_TABLE)
public class RunTableActivity extends BaseActivity<RunTableImp> implements RunTableContact.RunTableView,HaiDialogUtil.TruckResultListener {

    @BindView(R.id.run_table_back)
    ImageView back;
    @BindView(R.id.run_table_title)
    TextView title;
    @BindView(R.id.run_table_start)
    TextView start;
    @BindView(R.id.run_table_end)
    TextView end;
    @BindView(R.id.run_table_word5)
    TextView word5;
    @BindView(R.id.run_table_companyL)
    TextView company;
    @BindView(R.id.run_table_all_bill)
    TextView bill;
    @BindView(R.id.run_table_all_fee)
    TextView fee;
    @BindView(R.id.run_table_all_brokerage)
    TextView brokerage;
    @BindView(R.id.run_table_img4)
    ImageView img4;
    @BindView(R.id.run_table_search)
    Button search;
    @BindView(R.id.run_table_data)
    RecyclerView data;

    @Autowired(name = "companys")
    public ArrayList<CompanyEntry> companys;

    private TimePickerView startPickerView;
    private TimePickerView endPickerView;
    private RunTableAdapter runTableAdapter;
    private List<RunTableEntry> runTableEntries = new ArrayList<>();
    private Map<String, Object> params = new HashMap<>();
    private CommonDialogFragment showCompany;
    private int companyIndex = 0;

    @Override
    protected void initView() {

        ZxSharePreferenceUtil instance = ZxSharePreferenceUtil.getInstance();
        instance.init(this);
        //String token = (String) instance.getParam("token", "null");
        String loginType = (String) instance.getParam("login_type", "4");

        title.setText(getHaiString(R.string.statistics));
        if (loginType.equals(OtherConstants.LOGIN_MANAGER)){
            word5.setVisibility(View.VISIBLE);
            company.setVisibility(View.VISIBLE);
            img4.setVisibility(View.VISIBLE);
        }
        startPickerView = HaiTool.initTimePickers(this, start, 1111);
        endPickerView = HaiTool.initTimePickers(this, end, 0);
        setTitleTopMargin(back);
        setTitleTopMargin(title);

        data.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        runTableAdapter = new RunTableAdapter(runTableEntries);
        data.setAdapter(runTableAdapter);

        CompanyEntry companyEntry = companys.get(0);
        params.put("compayId", companyEntry.getLgsId());
        company.setText(companyEntry.getcName());
        mPresenter.runTableMethod(params);
        //@Query("compayId") String compayId, @Query("startTime") String startTime, @Query("endTime") String endTime
    }

    @Override
    protected void initInjector() {
        mPresenter = new RunTableImp();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_run_table;
    }

    @OnClick({R.id.run_table_back, R.id.run_table_start, R.id.run_table_end, R.id.run_table_search,R.id.run_table_companyL})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.run_table_back:
                finish();
                break;
            case R.id.run_table_start:
                startPickerView.show();
                break;
            case R.id.run_table_end:
                endPickerView.show();
                break;
            case R.id.run_table_search:
                search();
                break;
            case R.id.run_table_companyL:
                companyIndex = 0;
                CompanyWheel companyWheel = new CompanyWheel(companys);
                showCompany = HaiDialogUtil.showTruck(getSupportFragmentManager(), companyWheel, this::truckResult);
                break;
        }
    }

    private void search(){
        String starts = this.start.getText().toString().trim();
        String ends = end.getText().toString().trim();

        params.put("startTime",starts);
        params.put("endTime",ends);

        mPresenter.runTableMethod(params);
    }
    @Override
    public void setStatusBar() {
        ZxStatusBarCompat.translucentStatusBar(this,true);
    }

    @Override
    public void runTableSuccess(List<RunTableEntry> runTableEntries) {
        if (runTableEntries.size()==0){
            this.runTableEntries.clear();
            runTableAdapter.notifyDataSetChanged();
            ZxToastUtil.centerToast("此物流公司暂无信息");
        }else {
            this.runTableEntries.clear();
            this.runTableEntries.addAll(runTableEntries);
            runTableAdapter.notifyDataSetChanged();

            int orderNum = 0;
            double real = 0;
            double salary = 0;
            for (int i=0;i<runTableEntries.size();i++){
                RunTableEntry runTableEntry = runTableEntries.get(i);
                int a = Integer.parseInt(runTableEntry.getWaybillCount());
                double b = Double.parseDouble(runTableEntry.getSumrealPrice());
                double c = Double.parseDouble(runTableEntry.getCommission());
                orderNum += a;
                real += b;
                salary += c;
            }
            bill.setText(orderNum+"单");
            fee.setText(real+"元");
            brokerage.setText(salary+"元");
        }
    }

    @Override
    public void truckResult(int index) {
        if (index == -1){
            showCompany.dismissAllowingStateLoss();
            CompanyEntry driverEntry = companys.get(companyIndex);
            params.put("compayId", driverEntry.getLgsId());
            company.setText(driverEntry.getcName());
            mPresenter.runTableMethod(params);
        }else if (index == -2){
            showCompany.dismissAllowingStateLoss();
        }else {
            companyIndex = index;
        }
    }
}
