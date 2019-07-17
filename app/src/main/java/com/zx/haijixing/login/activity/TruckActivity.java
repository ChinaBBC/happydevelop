package com.zx.haijixing.login.activity;

import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bigkoo.pickerview.view.TimePickerView;
import com.zx.haijixing.R;
import com.zx.haijixing.login.contract.ITruckActivityContract;
import com.zx.haijixing.login.entry.TruckTypeEntry;
import com.zx.haijixing.login.presenter.TruckActivityImp;
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
import zx.com.skytool.ZxStringUtil;
import zx.com.skytool.ZxToastUtil;

/**
 * @作者 zx
 * @创建日期 2019/7/9 11:22
 * @描述 车辆详细信息
 */
@Route(path = PathConstant.TRUCK)
public class TruckActivity extends BaseActivity<TruckActivityImp> implements ITruckActivityContract.TruckView,HaiDialogUtil.TruckResultListener {

    @BindView(R.id.common_title_back)
    ImageView back;
    @BindView(R.id.common_title_title)
    TextView title;
    @BindView(R.id.truck_number)
    EditText number;
    @BindView(R.id.truck_type)
    TextView type;
    @BindView(R.id.truck_type_area)
    ConstraintLayout typeArea;
    @BindView(R.id.truck_weight)
    TextView weight;
    @BindView(R.id.truck_cure)
    TextView cure;
    @BindView(R.id.truck_cure_area)
    ConstraintLayout cureArea;

    @BindView(R.id.truck_drive_word)
    TextView driveWord;
    @BindView(R.id.truck_line_drive)
    View lineDrive;
    @BindView(R.id.truck_cure_word)
    TextView cureWord;
    @BindView(R.id.truck_line_cure)
    View lineCure;
    @BindView(R.id.truck_truck_word)
    TextView truckWord;
    @BindView(R.id.truck_line_truck)
    View lineTruck;
    @BindView(R.id.truck_other_word)
    TextView otherWord;
    @BindView(R.id.truck_line_other)
    View lineOther;

    @BindView(R.id.truck_licenseA)
    ImageView driveA;
    @BindView(R.id.truck_graphA)
    ImageView driveGraphA;
    @BindView(R.id.truck_wordA)
    TextView driveWordA;
    @BindView(R.id.truck_con2)
    ConstraintLayout driveAreaA;
    @BindView(R.id.truck_licenseB)
    ImageView driveB;
    @BindView(R.id.truck_graphB)
    ImageView driveGraphB;
    @BindView(R.id.truck_wordB)
    TextView driveWordB;
    @BindView(R.id.truck_con3)
    ConstraintLayout driveAreaB;
    @BindView(R.id.truck_cure_img)
    ImageView cureImg;
    @BindView(R.id.truck_cure_take)
    ImageView cureTake;
    @BindView(R.id.truck_word5)
    TextView cureWord5;
    @BindView(R.id.truck_con4)
    ConstraintLayout cureCon4;
    @BindView(R.id.truck_foreword_img)
    ImageView forewordImg;
    @BindView(R.id.truck_foreword_take)
    ImageView forewordTake;
    @BindView(R.id.truck_word6)
    TextView forewordWord6;
    @BindView(R.id.truck_con5)
    ConstraintLayout forewordCon5;
    @BindView(R.id.truck_left_img)
    ImageView leftImg;
    @BindView(R.id.truck_left_take)
    ImageView leftTake;
    @BindView(R.id.truck_word7)
    TextView leftWord7;
    @BindView(R.id.truck_con6)
    ConstraintLayout leftCon6;
    @BindView(R.id.truck_right_img)
    ImageView rightImg;
    @BindView(R.id.truck_right_take)
    ImageView rightightTake;
    @BindView(R.id.truck_word8)
    TextView rightWord8;
    @BindView(R.id.truck_con7)
    ConstraintLayout rightCon7;
    @BindView(R.id.truck_other_img)
    ImageView otherImg;
    @BindView(R.id.truck_other_take)
    ImageView otherTake;
    @BindView(R.id.truck_word9)
    TextView otherWord9;
    @BindView(R.id.truck_con8)
    ConstraintLayout otherCon8;
    @BindView(R.id.truck_add)
    Button add;
    @BindView(R.id.truck_next)
    Button next;

    @Autowired(name = "driverId")
    public String driverId;

    private List<TruckTypeEntry> typeEntryList = new ArrayList<>();
    private CommonDialogFragment showTruck;
    private  int temp = 0;
    private String typeId = null;
    private TimePickerView pvTime;
    private Map<String,String> params = new HashMap<>();
    private String drivingA;
    private String drivingB;
    private String cureDan;
    private String truckFor;
    private String truckLef;
    private String truckRig;
    private String otherPic;

    private boolean isAdd = false;


    @Override
    protected void initView() {
        title.setText(getHaiString(R.string.register));
        mPresenter.truckTypeMethod();
        pvTime = HaiTool.initTimePickers(this,cure,null);
    }

    @Override
    protected void initInjector() {
        mPresenter = new TruckActivityImp();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_truck;
    }

    @OnClick({R.id.common_title_back, R.id.truck_type_area, R.id.truck_cure_area, R.id.truck_drive_word, R.id.truck_cure_word, R.id.truck_truck_word, R.id.truck_other_word, R.id.truck_con2, R.id.truck_con3, R.id.truck_con4, R.id.truck_con5, R.id.truck_con6, R.id.truck_con7, R.id.truck_con8, R.id.truck_add, R.id.truck_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.common_title_back:
                finish();
                break;
            case R.id.truck_type_area:
                if (typeEntryList.size()>0){
                    TruckAdapter truckAdapter = new TruckAdapter(typeEntryList);
                    showTruck = HaiDialogUtil.showTruck(getSupportFragmentManager(), truckAdapter, this);
                }else {
                    ZxToastUtil.centerToast(getHaiString(R.string.no_data));
                }

                break;
            case R.id.truck_cure_area:
                pvTime.show();
                break;
            case R.id.truck_drive_word:
                showOrHide(0);
                break;
            case R.id.truck_cure_word:
                showOrHide(1);

                break;
            case R.id.truck_truck_word:
                showOrHide(2);

                break;
            case R.id.truck_other_word:
                showOrHide(3);

                break;
            case R.id.truck_con2:
                break;
            case R.id.truck_con3:
                break;
            case R.id.truck_con4:
                break;
            case R.id.truck_con5:
                break;
            case R.id.truck_con6:
                break;
            case R.id.truck_con7:
                break;
            case R.id.truck_con8:
                break;
            case R.id.truck_add:
                isAdd = true;
                mPresenter.truckApplyMethod(params);
                break;
            case R.id.truck_next:
                isAdd = false;
                ARouter.getInstance().build(PathConstant.CHECKING).navigation();
                break;
        }
    }

    private void showOrHide(int select){
        switch (select){
            case 0:
                lineDrive.setVisibility(View.VISIBLE);
                lineCure.setVisibility(View.INVISIBLE);
                lineTruck.setVisibility(View.INVISIBLE);
                lineOther.setVisibility(View.INVISIBLE);
                driveWord.setTextColor(getHaiColor(R.color.color_703f));
                cureWord.setTextColor(getHaiColor(R.color.color_6666));
                truckWord.setTextColor(getHaiColor(R.color.color_6666));
                otherWord.setTextColor(getHaiColor(R.color.color_6666));

                cureCon4.setVisibility(View.GONE);
                forewordCon5.setVisibility(View.GONE);
                leftCon6.setVisibility(View.GONE);
                rightCon7.setVisibility(View.GONE);
                otherCon8.setVisibility(View.GONE);
                driveAreaA.setVisibility(View.VISIBLE);
                driveAreaB.setVisibility(View.VISIBLE);
                break;
            case 1:
                lineDrive.setVisibility(View.INVISIBLE);
                lineCure.setVisibility(View.VISIBLE);
                lineTruck.setVisibility(View.INVISIBLE);
                lineOther.setVisibility(View.INVISIBLE);
                driveWord.setTextColor(getHaiColor(R.color.color_6666));
                cureWord.setTextColor(getHaiColor(R.color.color_703f));
                truckWord.setTextColor(getHaiColor(R.color.color_6666));
                otherWord.setTextColor(getHaiColor(R.color.color_6666));

                cureCon4.setVisibility(View.VISIBLE);
                forewordCon5.setVisibility(View.GONE);
                leftCon6.setVisibility(View.GONE);
                rightCon7.setVisibility(View.GONE);
                otherCon8.setVisibility(View.GONE);
                driveAreaA.setVisibility(View.GONE);
                driveAreaB.setVisibility(View.GONE);
                break;
            case 2:
                lineDrive.setVisibility(View.INVISIBLE);
                lineCure.setVisibility(View.INVISIBLE);
                lineTruck.setVisibility(View.VISIBLE);
                lineOther.setVisibility(View.INVISIBLE);
                driveWord.setTextColor(getHaiColor(R.color.color_6666));
                cureWord.setTextColor(getHaiColor(R.color.color_6666));
                truckWord.setTextColor(getHaiColor(R.color.color_703f));
                otherWord.setTextColor(getHaiColor(R.color.color_6666));

                cureCon4.setVisibility(View.GONE);
                forewordCon5.setVisibility(View.VISIBLE);
                leftCon6.setVisibility(View.VISIBLE);
                rightCon7.setVisibility(View.VISIBLE);
                otherCon8.setVisibility(View.GONE);
                driveAreaA.setVisibility(View.GONE);
                driveAreaB.setVisibility(View.GONE);
                break;
            case 3:
                lineDrive.setVisibility(View.INVISIBLE);
                lineCure.setVisibility(View.INVISIBLE);
                lineTruck.setVisibility(View.INVISIBLE);
                lineOther.setVisibility(View.VISIBLE);
                driveWord.setTextColor(getHaiColor(R.color.color_6666));
                cureWord.setTextColor(getHaiColor(R.color.color_6666));
                truckWord.setTextColor(getHaiColor(R.color.color_6666));
                otherWord.setTextColor(getHaiColor(R.color.color_703f));

                cureCon4.setVisibility(View.GONE);
                forewordCon5.setVisibility(View.GONE);
                leftCon6.setVisibility(View.GONE);
                rightCon7.setVisibility(View.GONE);
                otherCon8.setVisibility(View.VISIBLE);
                driveAreaA.setVisibility(View.GONE);
                driveAreaB.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public void typeSuccess(List<TruckTypeEntry> typeEntryList) {
        this.typeEntryList.addAll(typeEntryList);
    }

    @Override
    public void truckApplySuccess() {
        if (isAdd){
            type.setText(getHaiString(R.string.select_type));
            weight.setText("0KG");
            drivingA = null;
            drivingB = null;
            cureDan = null;
            truckFor = null;
            truckLef = null;
            truckRig = null;
            otherPic = null;
            typeId = null;
        }else {
            ARouter.getInstance().build(PathConstant.CHECKING).navigation();
            finish();
        }
    }

    @Override
    public void truckResult(int index) {
        ZxLogUtil.logError("<<<<<<<<<<<"+index);
        if (index>-1){
            temp = index;
        }else if (index == -1){
            TruckTypeEntry truckTypeEntry = typeEntryList.get(temp);
            typeId = truckTypeEntry.getTypeId();
            type.setText(truckTypeEntry.getName());
            weight.setText(truckTypeEntry.getLoad()+"KG");
            showTruck.dismissAllowingStateLoss();
        }else if (index == -2){
            showTruck.dismissAllowingStateLoss();
        }
    }
    //检查输入
    private void checkInput(){
        String truckNum = number.getText().toString().trim();
        String cureTime = cure.getText().toString().trim();
        if (ZxStringUtil.isEmpty(truckNum)){

        }else if (ZxStringUtil.isEmpty(cureTime)){
            ZxToastUtil.centerToast(getHaiString(R.string.input_drive_number));
        }else if (ZxStringUtil.isEmpty(typeId)){
            ZxToastUtil.centerToast(getHaiString(R.string.select_type));
        }else if (ZxStringUtil.isEmpty(drivingA)){
            ZxToastUtil.centerToast(getHaiString(R.string.upload_drive));
        }else if (ZxStringUtil.isEmpty(drivingB)){
            ZxToastUtil.centerToast(getHaiString(R.string.upload_drive));
        }else if (ZxStringUtil.isEmpty(cureDan)){
            ZxToastUtil.centerToast(getHaiString(R.string.upload_cure));
        }else if (ZxStringUtil.isEmpty(truckFor)){
            ZxToastUtil.centerToast(getHaiString(R.string.upload_truck));
        }else if (ZxStringUtil.isEmpty(truckLef)){
            ZxToastUtil.centerToast(getHaiString(R.string.upload_truck));
        }else if (ZxStringUtil.isEmpty(truckRig)){
            ZxToastUtil.centerToast(getHaiString(R.string.upload_truck));
        }else {
            params.put("userId","");
            params.put("caeType",typeId);
            params.put("safeEnd",cureTime);
            params.put("idcard",truckNum);
            params.put("carImgFront",truckFor);
            params.put("carImgLeft",truckLef);
            params.put("carImgRight",truckRig);
            params.put("otherImg",otherPic);
            mPresenter.truckApplyMethod(params);
        }

    }
}
