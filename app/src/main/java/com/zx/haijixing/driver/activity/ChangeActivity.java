package com.zx.haijixing.driver.activity;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.view.View;
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
import com.zx.haijixing.driver.contract.ChangeContract;
import com.zx.haijixing.driver.presenter.ChangeImp;
import com.zx.haijixing.share.OtherConstants;
import com.zx.haijixing.share.PathConstant;
import com.zx.haijixing.share.base.BaseActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import zx.com.skytool.ZxSharePreferenceUtil;
import zx.com.skytool.ZxToastUtil;

/**
 *
 *@作者 zx
 *@创建日期 2019/7/9 15:40
 *@描述 资料修改
 */
@Route(path = PathConstant.CHANGE)
public class ChangeActivity extends BaseActivity<ChangeImp> implements ChangeContract.ChangeView {

    @BindView(R.id.common_title_back)
    ImageView back;
    @BindView(R.id.common_title_title)
    TextView title;
    @BindView(R.id.change_header)
    ImageView header;
    @BindView(R.id.change_head_area)
    ConstraintLayout headArea;
    @BindView(R.id.change_name)
    TextView name;
    @BindView(R.id.change_phone_area)
    ConstraintLayout phoneArea;
    @BindView(R.id.change_password_area)
    ConstraintLayout passwordArea;
    @BindView(R.id.change_truck_area)
    ConstraintLayout truckArea;

    @Override
    protected void initView() {
        title.setText(getHaiString(R.string.change));
        ZxSharePreferenceUtil instance = ZxSharePreferenceUtil.getInstance();
        instance.init(this);

        String head = (String)instance.getParam("user_head","null");
        String name = (String)instance.getParam("user_name","null");

        this.name.setText(name);
        RequestOptions options = new RequestOptions().circleCrop().error(R.mipmap.user_head);
        Glide.with(this).load(head).apply(options).into(header);
    }

    @Override
    protected void initInjector() {
        mPresenter = new ChangeImp();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_change;
    }

    @OnClick({R.id.common_title_back, R.id.change_head_area, R.id.change_phone_area, R.id.change_password_area, R.id.change_truck_area})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.common_title_back:
                finish();
                break;
            case R.id.change_head_area:
                takePic();
                break;
            case R.id.change_phone_area:
                ARouter.getInstance().build(PathConstant.PHONE).navigation();
                break;
            case R.id.change_password_area:
                ARouter.getInstance().build(PathConstant.PASSWORD).navigation();
                break;
            case R.id.change_truck_area:
                ARouter.getInstance().build(PathConstant.VEHICLE).navigation();
                break;
        }
    }
    private void takePic(){
        PictureSelector.create(this)
                .openCamera(PictureMimeType.ofImage())
                .forResult(OtherConstants.UPLOAD_HEAD);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == OtherConstants.UPLOAD_HEAD){
            List<LocalMedia> uris = PictureSelector.obtainMultipleResult(data);
            String path = uris.get(0).getPath();
            mPresenter.changeMethod(path);
        }
    }

    @Override
    public void changeSuccess(String head) {
        mPresenter.changeHeadImgMethod(head);
        RequestOptions options = new RequestOptions().circleCrop();
        Glide.with(this).load(head).apply(options).into(header);
    }

    @Override
    public void changeHeadSuccess(String msg) {
        ZxToastUtil.centerToast(msg);
    }
}
