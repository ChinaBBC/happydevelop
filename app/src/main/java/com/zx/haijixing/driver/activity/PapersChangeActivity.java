package com.zx.haijixing.driver.activity;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.zx.haijixing.R;
import com.zx.haijixing.driver.contract.PaperChangeContract;
import com.zx.haijixing.driver.presenter.PaperChangeImp;
import com.zx.haijixing.share.OtherConstants;
import com.zx.haijixing.share.PathConstant;
import com.zx.haijixing.share.base.BaseActivity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import zx.com.skytool.ZxSharePreferenceUtil;
import zx.com.skytool.ZxStringUtil;
import zx.com.skytool.ZxToastUtil;

/**
 *
 *@作者 zx
 *@创建日期 2019/7/10 10:47
 *@描述 修改证件
 */
@Route(path = PathConstant.PAPERS_CHANGE)
public class PapersChangeActivity extends BaseActivity<PaperChangeImp> implements PaperChangeContract.PaperChangeView {

    @BindView(R.id.papers_change_back)
    ImageView back;
    @BindView(R.id.papers_change_title)
    TextView title;
    @BindView(R.id.papers_change_cancel)
    TextView cancel;

    @BindView(R.id.papers_change_one)
    ImageView one;
    @BindView(R.id.papers_change_one_word)
    TextView oneWord;
    @BindView(R.id.papers_change_con2)
    ConstraintLayout con2;

    @BindView(R.id.papers_change_two)
    ImageView two;
    @BindView(R.id.papers_change_two_word)
    TextView twoWord;
    @BindView(R.id.papers_change_con3)
    ConstraintLayout con3;

    @BindView(R.id.papers_change_three)
    ImageView three;
    @BindView(R.id.papers_change_three_word)
    TextView threeWord;
    @BindView(R.id.papers_change_con4)
    ConstraintLayout con4;

    @BindView(R.id.papers_change_save)
    Button save;

    @Autowired(name = "title")
    public String strTitle;
    @Autowired(name = "one")
    public String oneStr;
    @Autowired(name = "two")
    public String twoStr;
    @Autowired(name = "three")
    public String threeStr;
    @Autowired(name = "tag")
    public int tag;
    @Autowired(name = "truckId")
    public String truckId;

    private String strA;
    private String strB;
    private String drivingA;
    private String drivingB;
    private String cureDan;
    private String truckFor;
    private String truckLef;
    private String truckRig;
    private String otherPic;
    private String token;


    @Override
    protected void initView() {
        title.setText("修改"+strTitle);
        ZxSharePreferenceUtil instance = ZxSharePreferenceUtil.getInstance();
        instance.init(this);
        token = (String) instance.getParam("token","token");
        RequestOptions options = new RequestOptions().error(R.mipmap.login_title_bg);
        if (ZxStringUtil.isEmpty(threeStr)){
            con4.setVisibility(View.GONE);
        }else {
            Glide.with(this).load(threeStr).apply(options).into(three);
        }
        if (ZxStringUtil.isEmpty(twoStr)){
            con3.setVisibility(View.GONE);
        }else {
            Glide.with(this).load(twoStr).apply(options).into(two);
        }
        if (ZxStringUtil.isEmpty(oneStr)){
            ZxToastUtil.centerToast("暂时没有图片");
            con2.setVisibility(View.GONE);
        }else {
            Glide.with(this).load(oneStr).apply(options).into(one);
        }
    }

    @Override
    protected void initInjector() {
        mPresenter = new PaperChangeImp();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_papers_change;
    }

    @OnClick({R.id.papers_change_back, R.id.papers_change_cancel, R.id.papers_change_con2, R.id.papers_change_con3, R.id.papers_change_con4, R.id.papers_change_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.papers_change_back:
                finish();
                break;
            case R.id.papers_change_cancel:
                finish();
                break;
            case R.id.papers_change_con2:
                switch (tag){
                    case OtherConstants.CHANGE_DRIVER_ID:
                        takePic(OtherConstants.UPLOAD_DRIVER_A);
                        break;
                    case OtherConstants.CHANGE_TRUCK_ID:
                        takePic(OtherConstants.UPLOAD_DRIVING_A);
                        break;
                    case OtherConstants.CHANGE_TRUCK_IMG:
                        takePic(OtherConstants.UPLOAD_TRUCK_A);
                        break;
                    case OtherConstants.CHANGE_CURE_ID:
                        takePic(OtherConstants.UPLOAD_CURE);
                        break;
                    case OtherConstants.CHANGE_OTHER:
                        takePic(OtherConstants.UPLOAD_OTHER);
                        break;
                }
                break;
            case R.id.papers_change_con3:
                switch (tag){
                    case OtherConstants.CHANGE_DRIVER_ID:
                        takePic(OtherConstants.UPLOAD_DRIVER_B);
                        break;
                    case OtherConstants.CHANGE_TRUCK_ID:
                        takePic(OtherConstants.UPLOAD_DRIVING_B);
                        break;
                    case OtherConstants.CHANGE_TRUCK_IMG:
                        takePic(OtherConstants.UPLOAD_TRUCK_B);
                        break;
                }
                break;
            case R.id.papers_change_con4:
                takePic(OtherConstants.UPLOAD_TRUCK_C);
                break;
            case R.id.papers_change_save:
                Map<String,Object> params = new HashMap<>();
                params.put("token",token);
                params.put("applyId",truckId);
                switch (tag){
                    case OtherConstants.CHANGE_DRIVER_ID:
                        if (ZxStringUtil.isEmpty(strA) || ZxStringUtil.isEmpty(strB)){
                            ZxToastUtil.centerToast("请上传新驾驶证");
                        }else {
                            mPresenter.changeDriverId(token,strA,strB);
                        }
                        break;
                    case OtherConstants.CHANGE_TRUCK_ID:
                        if (ZxStringUtil.isEmpty(drivingA) || ZxStringUtil.isEmpty(drivingB)){
                            ZxToastUtil.centerToast("请上传新行驶证");
                        }else {
                            params.put("drivingFront",drivingA);
                            params.put("drivingBack",drivingB);
                            mPresenter.changeTruckInfo(params);
                        }
                        break;
                    case OtherConstants.CHANGE_TRUCK_IMG:
                        if (ZxStringUtil.isEmpty(truckFor) || ZxStringUtil.isEmpty(truckLef)|| ZxStringUtil.isEmpty(truckRig)){
                            ZxToastUtil.centerToast("请上传车照片");
                        }else {
                            params.put("carImageFront",truckFor);
                            params.put("carImageLeft",truckLef);
                            params.put("carImageRight",truckRig);
                            mPresenter.changeTruckInfo(params);
                        }
                        break;
                    case OtherConstants.CHANGE_CURE_ID:
                        if (ZxStringUtil.isEmpty(cureDan)){
                            ZxToastUtil.centerToast("请上传保单");
                        }else {
                            params.put("safeImage",cureDan);
                            mPresenter.changeTruckInfo(params);
                        }
                        break;
                    case OtherConstants.CHANGE_OTHER:
                        if (ZxStringUtil.isEmpty(otherPic)){
                            ZxToastUtil.centerToast("请上传其他信息");
                        }else {
                            params.put("annex",otherPic);
                            mPresenter.changeTruckInfo(params);
                        }
                        break;
                }
                break;
        }
    }

    private void takePic(int request){
        PictureSelector.create(this)
                .openCamera(PictureMimeType.ofImage())
                .forResult(request);
    }

    @Override
    public void showFaild(String errorMsg) {
        super.showFaild(errorMsg);
        con2.setClickable(true);
        con3.setClickable(true);
        con4.setClickable(true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            List<LocalMedia> uris = PictureSelector.obtainMultipleResult(data);
            String path = uris.get(0).getPath();
            switch (requestCode){
                case OtherConstants.UPLOAD_DRIVER_A:
                    strA = path;
                    Glide.with(this).load(path).into(one);
                    oneWord.setText("上传中...");
                    con2.setClickable(false);
                    break;
                case OtherConstants.UPLOAD_DRIVER_B:
                    strB = path;
                    Glide.with(this).load(path).into(two);
                    twoWord.setText("上传中...");
                    con3.setClickable(false);
                    break;
                case OtherConstants.UPLOAD_DRIVING_A:
                    Glide.with(this).load(path).into(one);
                    oneWord.setText("上传中...");
                    con2.setClickable(false);
                    break;
                case OtherConstants.UPLOAD_DRIVING_B:
                    Glide.with(this).load(path).into(two);
                    twoWord.setText("上传中...");
                    con3.setClickable(false);
                    break;
                case OtherConstants.UPLOAD_CURE:
                    Glide.with(this).load(path).into(one);
                    oneWord.setText("上传中...");
                    con2.setClickable(false);
                    break;
                case OtherConstants.UPLOAD_TRUCK_A:
                    Glide.with(this).load(path).into(one);
                    oneWord.setText("上传中...");
                    con2.setClickable(false);
                    break;
                case OtherConstants.UPLOAD_TRUCK_B:
                    Glide.with(this).load(path).into(two);
                    twoWord.setText("上传中...");
                    con3.setClickable(false);
                    break;
                case OtherConstants.UPLOAD_TRUCK_C:
                    Glide.with(this).load(path).into(three);
                    threeWord.setText("上传中...");
                    con4.setClickable(false);
                    break;
                case OtherConstants.UPLOAD_OTHER:
                    Glide.with(this).load(path).into(one);
                    oneWord.setText("上传中...");
                    con2.setClickable(false);
                    break;
            }
            mPresenter.uploadImg(path,requestCode);
        }

    }

    @Override
    public void changeInfoSuccess(String msg) {
        ZxToastUtil.centerToast(msg);
        finish();
    }

    @Override
    public void uploadImgSuccess(String path, int tag) {
        switch (tag){
            case OtherConstants.UPLOAD_DRIVER_A:
                strA = path;
                oneWord.setText("重新上传");
                con2.setClickable(true);
                break;
            case OtherConstants.UPLOAD_DRIVER_B:
                strB = path;
                twoWord.setText("重新上传");
                con3.setClickable(true);
                break;
            case OtherConstants.UPLOAD_DRIVING_A:
                drivingA = path;
                oneWord.setText("重新上传");
                con2.setClickable(true);
                break;
            case OtherConstants.UPLOAD_DRIVING_B:
                drivingB = path;
                twoWord.setText("重新上传");
                con3.setClickable(true);
                break;
            case OtherConstants.UPLOAD_CURE:
                cureDan = path;
                oneWord.setText("重新上传");
                con2.setClickable(true);
                break;
            case OtherConstants.UPLOAD_TRUCK_A:
                truckFor = path;
                oneWord.setText("重新上传");
                con2.setClickable(true);
                break;
            case OtherConstants.UPLOAD_TRUCK_B:
                truckLef = path;
                twoWord.setText("重新上传");
                con3.setClickable(true);
                break;
            case OtherConstants.UPLOAD_TRUCK_C:
                truckRig = path;
                threeWord.setText("重新上传");
                con4.setClickable(true);
                break;
            case OtherConstants.UPLOAD_OTHER:
                otherPic = path;
                oneWord.setText("重新上传");
                con2.setClickable(true);
                break;
        }
    }
}
