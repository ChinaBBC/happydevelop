package com.zx.haijixing.login.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.zx.haijixing.R;
import com.zx.haijixing.login.contract.ICarInfoActivityContract;
import com.zx.haijixing.login.presenter.CarInfoActivityImp;
import com.zx.haijixing.share.OtherConstants;
import com.zx.haijixing.share.PathConstant;
import com.zx.haijixing.share.base.BaseActivity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import zx.com.skytool.ZxStringUtil;
import zx.com.skytool.ZxToastUtil;

/**
 * @作者 zx
 * @创建日期 2019/6/28 11:38
 * @描述 驾驶证
 */
@Route(path = PathConstant.CAR_INFO)
public class CarInfoActivity extends BaseActivity<CarInfoActivityImp> implements ICarInfoActivityContract.CarInfoView {

    @BindView(R.id.common_title_back)
    ImageView back;
    @BindView(R.id.common_title_title)
    TextView title;
    @BindView(R.id.car_info_licenseA)
    ImageView licenseA;
    @BindView(R.id.car_info_graphA)
    ImageView graphA;
    @BindView(R.id.car_info_wordA)
    TextView wordA;
    @BindView(R.id.car_info_licenseB)
    ImageView licenseB;
    @BindView(R.id.car_info_graphB)
    ImageView graphB;
    @BindView(R.id.car_info_wordB)
    TextView wordB;
    @BindView(R.id.car_info_next)
    Button next;

    @Autowired(name = "driverId")
    public String driverId;
    @Autowired(name = "name")
    public String realName;
    @Autowired(name = "phone")
    public String phoneNum;

    private String strA ;
    private String strB ;

    @Override
    protected void initView() {
        title.setText(getHaiString(R.string.register));
    }

    @Override
    protected void initInjector() {
        mPresenter = new CarInfoActivityImp();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_car_info;
    }

    @OnClick({R.id.common_title_back, R.id.car_info_graphA, R.id.car_info_graphB, R.id.car_info_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.common_title_back:
                finish();
                break;
            case R.id.car_info_graphA:
                takePic(OtherConstants.UPLOAD_DRIVER_A);
                break;
            case R.id.car_info_graphB:
                takePic(OtherConstants.UPLOAD_DRIVER_B);
                break;
            case R.id.car_info_next:
                //ARouter.getInstance().build(PathConstant.TRUCK).navigation();
                checkUpload();
                break;
        }
    }

    @Override
    public void carInfoSuccess() {
        ARouter.getInstance().build(PathConstant.TRUCK)
                .withString("driverId",driverId)
                .withString("name",realName)
                .withString("phone",phoneNum)
                .navigation();
        finish();
    }

    @Override
    public void showFaild(String errorMsg) {
        super.showFaild(errorMsg);
        graphA.setVisibility(View.VISIBLE);
        graphB.setVisibility(View.VISIBLE);
    }

    @Override
    public void uploadCarInfoSuccess(String path, int tag) {
        switch (tag){
            case OtherConstants.UPLOAD_DRIVER_A:
                strA = path;
                graphA.setVisibility(View.VISIBLE);
                wordA.setText("上传完成");
                break;
            case OtherConstants.UPLOAD_DRIVER_B:
                strB = path;
                graphB.setVisibility(View.VISIBLE);
                wordB.setText("上传完成");
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            List<LocalMedia> uris = PictureSelector.obtainMultipleResult(data);
            String path = uris.get(0).getPath();
            switch (requestCode){
                case OtherConstants.UPLOAD_DRIVER_A:
                    Glide.with(this).load(path).into(licenseA);
                    graphA.setVisibility(View.GONE);
                    wordA.setText("正在上传...");
                    wordA.setTextColor(Color.RED);
                    break;
                case OtherConstants.UPLOAD_DRIVER_B:
                    Glide.with(this).load(path).into(licenseB);
                    graphB.setVisibility(View.GONE);
                    wordB.setText("正在上传...");
                    wordB.setTextColor(Color.RED);
                    break;
            }
            mPresenter.uploadCarInfoMethod(path,requestCode);
        }
    }

    //拍照
    private void takePic(int requestCode){
        PictureSelector.create(this)
                .openCamera(PictureMimeType.ofImage())
                .forResult(requestCode);
    }

    private void checkUpload(){
        if (ZxStringUtil.isEmpty(strA)){
            ZxToastUtil.centerToast(getHaiString(R.string.upload_driving_A));
        }else if (ZxStringUtil.isEmpty(strB)){
            ZxToastUtil.centerToast(getHaiString(R.string.upload_driving_B));
        }else{
            Map<String,String> params = new HashMap<>();
            params.put("userId",driverId);
            params.put("driverCardFront",strA);
            params.put("driverCardBack",strB);
            params.put("driverCardFrontS",strA);//缩略图
            params.put("driverCardBackS",strB);

            mPresenter.carInfoMethod(params);
        }
    }
}
