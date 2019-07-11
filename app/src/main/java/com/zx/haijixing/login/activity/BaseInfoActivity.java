package com.zx.haijixing.login.activity;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.zx.haijixing.R;
import com.zx.haijixing.login.contract.IBaseInfoActivityContract;
import com.zx.haijixing.login.presenter.BaseInfoActivityImp;
import com.zx.haijixing.share.RoutePathConstant;
import com.zx.haijixing.share.base.BaseActivity;
import com.zx.haijixing.util.HaiTool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import zx.com.skytool.ZxCacheUtil;
import zx.com.skytool.ZxCountDownTimerUtil;
import zx.com.skytool.ZxLogUtil;
import zx.com.skytool.ZxStatusBarCompat;
import zx.com.skytool.ZxStringUtil;
import zx.com.skytool.ZxToastUtil;

/**
 *
 *@作者 zx
 *@创建日期 2019/6/28 10:28
 *@描述 基本信息
 */
@Route(path = RoutePathConstant.BASE_INFO)
public class BaseInfoActivity extends BaseActivity<BaseInfoActivityImp> implements IBaseInfoActivityContract.BaseInfoView {

    @BindView(R.id.common_title_back)
    ImageView back;
    @BindView(R.id.common_title_title)
    TextView title;
    @BindView(R.id.base_info_head)
    ImageView head;
    @BindView(R.id.base_info_name)
    EditText name;
    @BindView(R.id.base_info_identify)
    EditText identify;
    @BindView(R.id.base_info_phone)
    EditText phone;
    @BindView(R.id.base_info_code)
    EditText code;
    @BindView(R.id.base_info_password)
    EditText password;
    @BindView(R.id.base_info_sure_password)
    EditText surePassword;
    @BindView(R.id.base_info_agree)
    ImageView agree;
    @BindView(R.id.base_info_protocol)
    TextView protocol;
    @BindView(R.id.base_info_next)
    Button next;
    @BindView(R.id.base_info_send)
    TextView send;
    @BindView(R.id.base_info_bank_address)
    EditText bankAddress;
    @BindView(R.id.base_info_bank_number)
    EditText bankNumber;

    private final static int HEAD_REQUEST = 1001;

    private boolean isRead = false;
    private boolean hasRead = false;
    private ZxCountDownTimerUtil<TextView> zxCountDownTimerUtil;
    private String headPath = null;

    @Override
    protected void initView() {
        title.setText(getHaiString(R.string.register));
    }

    @Override
    protected void initInjector() {
        mPresenter = new BaseInfoActivityImp();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_base_info;
    }

    @OnClick({R.id.common_title_back, R.id.base_info_head, R.id.base_info_agree, R.id.base_info_protocol,R.id.base_info_send,R.id.base_info_next,R.id.base_info_read})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.common_title_back:
                finish();
                break;
            case R.id.base_info_head:
                takePic();
                break;
            case R.id.base_info_read:
            case R.id.base_info_agree:
                if (hasRead){
                    if (isRead){
                        agree.setImageResource(R.mipmap.register_disagree);
                        isRead = false;
                    }else {
                        agree.setImageResource(R.mipmap.register_agree);
                        isRead = true;
                    }
                }else {
                    ZxToastUtil.centerToast(getHaiString(R.string.please_read_first));
                }
                break;
            case R.id.base_info_protocol:
                hasRead = true;
                agree.setImageResource(R.mipmap.register_agree);
                break;
            case R.id.base_info_send:
                String trim = phone.getText().toString().trim();
                if (!ZxStringUtil.isEmpty(trim)){
                    sendMethod();
                    mPresenter.baseInfoCodeMethod(trim);
                }else {
                    ZxToastUtil.centerToast(getHaiString(R.string.please_input_phone));
                }
                break;
            case R.id.base_info_next:
                checkInput();
                break;
        }
    }

    @Override
    public void baseInfoSuccess(String driverId) {
        ARouter.getInstance().build(RoutePathConstant.CAR_INFO).withString("driverId",driverId).navigation();
    }

    @Override
    public void baseInfoCodeSuccess() {
        ZxToastUtil.centerToast(getHaiString(R.string.send_success));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (zxCountDownTimerUtil != null && zxCountDownTimerUtil.getRunStatus())
            zxCountDownTimerUtil.cancel();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == HEAD_REQUEST){
            List<LocalMedia> uris = PictureSelector.obtainMultipleResult(data);
            String path = uris.get(0).getPath();
            RequestOptions options = new RequestOptions().circleCrop();
            Glide.with(this).load(path).apply(options).into(head);
            headPath = path;
        }
    }

    private void takePic(){
        PictureSelector.create(this)
                .openCamera(PictureMimeType.ofImage())
                .forResult(HEAD_REQUEST);
    }
    //发送验证码
    private void sendMethod() {
        zxCountDownTimerUtil = new ZxCountDownTimerUtil<>(send);
        zxCountDownTimerUtil.setCommonStyle(0);
        zxCountDownTimerUtil.setRunningStyle(0);
        zxCountDownTimerUtil.setTimeColor(getHaiColor(R.color.color_703f));
        zxCountDownTimerUtil.start();
    }

    private void checkInput(){
        String realName = name.getText().toString().trim();
        String identifyCar = identify.getText().toString().trim();
        String bankCar = bankNumber.getText().toString().trim();
        String bankAd = bankAddress.getText().toString().trim();
        String phone = this.phone.getText().toString().trim();
        String code = this.code.getText().toString().trim();
        String password = this.password.getText().toString().trim();
        String surePassword = this.surePassword.getText().toString().trim();

        if (!ZxStringUtil.isEmpty(headPath)){
            ZxToastUtil.centerToast(getHaiString(R.string.please_upload_head));
        }else if (ZxStringUtil.isEmpty(realName)){
            ZxToastUtil.centerToast(getHaiString(R.string.please_input_name));
        }else if (ZxStringUtil.isEmpty(identifyCar)){
            ZxToastUtil.centerToast(getHaiString(R.string.please_input_identify));

        }else if (ZxStringUtil.isEmpty(bankCar)){
            ZxToastUtil.centerToast(getHaiString(R.string.please_input_bank_number));

        }else if (ZxStringUtil.isEmpty(bankAd)){
            ZxToastUtil.centerToast(getHaiString(R.string.please_input_bank_address));

        }else if (ZxStringUtil.isEmpty(phone)){
            ZxToastUtil.centerToast(getHaiString(R.string.please_input_phone));

        }else if (ZxStringUtil.isEmpty(code)){
            ZxToastUtil.centerToast(getHaiString(R.string.please_input_code));

        }else if (ZxStringUtil.isEmpty(password)){
            ZxToastUtil.centerToast(getHaiString(R.string.please_input_password));

        }else if (ZxStringUtil.isEmpty(surePassword)){
            ZxToastUtil.centerToast(getHaiString(R.string.sure_password));

        }else if (!password.equals(surePassword)){
            ZxToastUtil.centerToast(getHaiString(R.string.password_not_equal));

        }else if (!isRead){
            ZxToastUtil.centerToast(getHaiString(R.string.please_read_and_agree));

        }else {
            Map<String,String> params = new HashMap<>();
            params.put("name",realName);
            params.put("idcard",identifyCar);
            params.put("bankCard",bankCar);
            params.put("bankName",bankAd);
            params.put("phone",phone);
            params.put("vcode",code);
            params.put("loginKey",HaiTool.md5Method(password));
            //params.put("head",headPath);

            mPresenter.baseInfoMethod(params);

        }
    }


}
