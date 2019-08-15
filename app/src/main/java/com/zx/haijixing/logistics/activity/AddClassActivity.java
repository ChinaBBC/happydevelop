package com.zx.haijixing.logistics.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.bigkoo.pickerview.view.TimePickerView;
import com.zx.haijixing.R;
import com.zx.haijixing.logistics.adapter.DriverTruckAdapter;
import com.zx.haijixing.logistics.contract.AddClassContract;
import com.zx.haijixing.logistics.entry.DriverEntry;
import com.zx.haijixing.logistics.entry.TruckInfoEntry;
import com.zx.haijixing.logistics.presenter.AddClassImp;
import com.zx.haijixing.share.OtherConstants;
import com.zx.haijixing.share.PathConstant;
import com.zx.haijixing.share.base.BaseActivity;
import com.zx.haijixing.util.CommonDialogFragment;
import com.zx.haijixing.util.HaiDialogUtil;
import com.zx.haijixing.util.HaiTool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import zx.com.skytool.ZxLogUtil;
import zx.com.skytool.ZxSharePreferenceUtil;
import zx.com.skytool.ZxStringUtil;
import zx.com.skytool.ZxToastUtil;

/**
 * @作者 zx
 * @创建日期 2019/8/1 19:01
 * @描述 添加班次
 */
@Route(path = PathConstant.ADD_CLASS)
public class AddClassActivity extends BaseActivity<AddClassImp> implements AddClassContract.AddClassView,HaiDialogUtil.TruckResultListener {

    @BindView(R.id.add_editor_startT)
    TextView startT;
    @BindView(R.id.add_editor_startArea)
    LinearLayout startArea;
    @BindView(R.id.add_editor_endT)
    TextView endT;
    @BindView(R.id.add_editor_endArea)
    LinearLayout endArea;
    @BindView(R.id.add_editor_namePhone)
    TextView namePhone;
    @BindView(R.id.add_editor_namePhoneArea)
    LinearLayout namePhoneArea;
    @BindView(R.id.add_editor_line4)
    View addEditorLine4;
    @BindView(R.id.add_editor_truckNum)
    TextView truckNum;
    @BindView(R.id.add_editor_truckNumArea)
    LinearLayout truckNumArea;
    @BindView(R.id.add_editor_remark)
    EditText remark;
    @BindView(R.id.add_editor_cancel)
    Button cancel;
    @BindView(R.id.add_editor_sure)
    Button sure;

    @Autowired(name = "linesId")
    public String linesId;

    private TimePickerView startPickerView;
    private TimePickerView endPickerView;
    private String token;
    private DriverTruckAdapter truckAdapter;
    private DriverTruckAdapter driverAdapter;
    private List<DriverEntry> driverEntries;
    private List<TruckInfoEntry> truckInfoEntries;
    private CommonDialogFragment showDriverSelect;
    private CommonDialogFragment showTruckSelect;

    private Map<String,String> params = new HashMap<>();

    private int type = OtherConstants.SELECT_DRIVER;
    private int driverIndex = 0;
    private int truckIndex = 0;
    private CommonDialogFragment showAddSure;

    @Override
    protected void initView() {

        ZxSharePreferenceUtil instance = ZxSharePreferenceUtil.getInstance();
        instance.init(this);
        token = (String) instance.getParam("token", "null");

        startPickerView = HaiTool.initTimeHourMin(this, startT);
        endPickerView = HaiTool.initTimeHourMin(this, endT);


        params.put("token",token);
        params.put("lineId",linesId);
        params.put("timestamp",System.currentTimeMillis()+"");
        params.put("sign","");
        params.put("sign",HaiTool.sign(params));
        mPresenter.driverPhoneMethod(params);
    }

    @Override
    protected void initInjector() {
        mPresenter = new AddClassImp();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_class;
    }

    @OnClick({R.id.add_editor_startArea, R.id.add_editor_endArea, R.id.add_editor_namePhoneArea, R.id.add_editor_truckNumArea, R.id.add_editor_cancel, R.id.add_editor_sure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.add_editor_startArea:
                startPickerView.show();
                break;
            case R.id.add_editor_endArea:
                endPickerView.show();
                break;
            case R.id.add_editor_namePhoneArea:
                if (driverEntries != null && driverEntries.size()>0){
                    type = OtherConstants.SELECT_DRIVER;
                    driverIndex = 0;
                    showDriverSelect = HaiDialogUtil.showTruck(getSupportFragmentManager(), driverAdapter, this::truckResult);
                }else {
                    ZxToastUtil.centerToast("获取司机失败，请稍候再试。");
                }
                break;
            case R.id.add_editor_truckNumArea:
                if (truckInfoEntries != null && truckInfoEntries.size()>0){
                    type = OtherConstants.SELECT_TRUCK;
                    truckIndex = 0;
                    showTruckSelect = HaiDialogUtil.showTruck(getSupportFragmentManager(), truckAdapter, this::truckResult);
                }else {
                    ZxToastUtil.centerToast("获取车辆信息失败，请稍候再试。");
                }
                break;
            case R.id.add_editor_cancel:
                finish();
                break;
            case R.id.add_editor_sure:
                checkAndAdd();
                break;
            case R.id.dialog_update_yes:
                showAddSure.dismissAllowingStateLoss();
                params.put("timestamp",System.currentTimeMillis()+"");
                params.put("sign","");
                params.put("sign",HaiTool.sign(params));
                mPresenter.addClassMethod(params);
                break;
            case R.id.dialog_update_no:
                showAddSure.dismissAllowingStateLoss();
                break;
        }
    }

    private void checkAndAdd() {
        String trim = startT.getText().toString().trim();
        String trim1 = endT.getText().toString().trim();
        String trim2 = truckNum.getText().toString().trim();
        String trim3 = namePhone.getText().toString().trim();
        if (trim.equals(trim1)){
            ZxToastUtil.centerToast("时间不能相同");
        }else if(ZxStringUtil.isEmpty(trim3)){
            ZxToastUtil.centerToast("请选择司机");
        }else if(ZxStringUtil.isEmpty(trim2)){
            ZxToastUtil.centerToast("请选择车牌号");
        }else {
            params.put("startTime", trim);
            params.put("endTime", trim1);
            params.put("remark",remark.getText().toString().trim());
            showAddSure = HaiDialogUtil.showUpdate(getSupportFragmentManager(), "是否确认新增班次？", this::onViewClicked);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ZxLogUtil.logError("<<<<add class onDestroy>>");
    }

    @Override
    public void addClassSuccess(String msg) {
        ZxToastUtil.centerToast(msg);
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void driverPhoneSuccess(List<DriverEntry> driverEntries) {
        if (driverEntries.size()>0){
            this.driverEntries = driverEntries;
            DriverEntry driverEntry = driverEntries.get(driverIndex);
            namePhone.setText(driverEntry.getDriverName());
            Map<String,String> params = new HashMap<>();
            params.put("token",token);
            params.put("driverId",driverEntry.getDriverId());
            params.put("timestamp",System.currentTimeMillis()+"");
            params.put("sign",HaiTool.sign(params));
            mPresenter.truckNumMethod(params);
            this.params.put("driverId", driverEntry.getDriverId());
            driverAdapter = new DriverTruckAdapter(driverEntries, null, OtherConstants.SELECT_DRIVER);
        }else {
            ZxToastUtil.centerToast("此线路下暂无司机");
            namePhone.setText("");
        }

    }

    @Override
    public void truckNumSuccess(List<TruckInfoEntry> truckInfoEntries) {
        if (truckInfoEntries.size()>0){
            this.truckInfoEntries = truckInfoEntries;
            TruckInfoEntry truckInfoEntry = truckInfoEntries.get(truckIndex);
            params.put("carId", truckInfoEntry.getCarId());
            truckNum.setText(truckInfoEntry.getIdcard());
            truckAdapter = new DriverTruckAdapter(null, truckInfoEntries, OtherConstants.SELECT_TRUCK);
        }else {
            ZxToastUtil.centerToast("此司机下暂无车辆");
            truckNum.setText("");
            this.truckInfoEntries.clear();
        }

    }

    @Override
    public void truckResult(int index) {
        if (index == -1){
            if (type == OtherConstants.SELECT_DRIVER){
                showDriverSelect.dismissAllowingStateLoss();
                DriverEntry driverEntry = driverEntries.get(driverIndex);
                Map<String,String> param = new HashMap<>();
                param.put("token",token);
                param.put("driverId",driverEntry.getDriverId());
                param.put("timestamp",System.currentTimeMillis()+"");
                param.put("sign",HaiTool.sign(param));
                mPresenter.truckNumMethod(param);
                params.put("driverId", driverEntry.getDriverId());
                namePhone.setText(driverEntry.getDriverName());
            }else {
                TruckInfoEntry truckInfoEntry = truckInfoEntries.get(truckIndex);
                params.put("carId", truckInfoEntry.getCarId());
                truckNum.setText(truckInfoEntry.getIdcard());
                showTruckSelect.dismissAllowingStateLoss();
            }
        }else if (index == -2){
            if (type == OtherConstants.SELECT_DRIVER){
                showDriverSelect.dismissAllowingStateLoss();
            }else {
                showTruckSelect.dismissAllowingStateLoss();
            }
        }else {
            if (type == OtherConstants.SELECT_DRIVER){
                driverIndex = index;
            }else {
                truckIndex = index;
            }
        }
    }
}
