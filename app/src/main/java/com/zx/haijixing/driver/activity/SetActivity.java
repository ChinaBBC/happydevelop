package com.zx.haijixing.driver.activity;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.allen.library.RxHttpUtils;
import com.zx.haijixing.R;
import com.zx.haijixing.share.OtherConstants;
import com.zx.haijixing.share.PathConstant;
import com.zx.haijixing.share.base.BaseActivity;
import com.zx.haijixing.util.CommonDialogFragment;
import com.zx.haijixing.util.HaiDialogUtil;
import com.zx.haijixing.util.HaiTool;

import butterknife.BindView;
import butterknife.OnClick;
import zx.com.skytool.ZxCacheUtil;
import zx.com.skytool.ZxSharePreferenceUtil;
import zx.com.skytool.ZxToastUtil;

@Route(path = PathConstant.SET)
public class SetActivity extends BaseActivity {

    @BindView(R.id.common_title_back)
    ImageView back;
    @BindView(R.id.common_title_title)
    TextView title;
    @BindView(R.id.set_check_update_area)
    ConstraintLayout updateArea;
    @BindView(R.id.set_clean_cache_area)
    ConstraintLayout cacheArea;
    @BindView(R.id.set_private_area)
    ConstraintLayout privateArea;
    @BindView(R.id.set_out_login)
    TextView outLogin;
    @BindView(R.id.set_cache_size)
    TextView cacheSize;
    @BindView(R.id.set_version_name)
    TextView versionName;
    private CommonDialogFragment showUpdate;

    @Override
    protected void initView() {
        title.setText(getHaiString(R.string.set));
        versionName.setText(getHaiString(R.string.version)+HaiTool.packageName(this));
        getAllCache();
    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_set;
    }

    @OnClick({R.id.common_title_back, R.id.set_check_update_area, R.id.set_clean_cache_area, R.id.set_private_area, R.id.set_out_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.common_title_back:
                finish();
                break;
            case R.id.set_check_update_area:
                ARouter.getInstance().build(PathConstant.VERSION).navigation();
                break;
            case R.id.set_clean_cache_area:
                boolean b = ZxCacheUtil.clearAllCache(this);
                if (b){
                    ZxToastUtil.centerToast("清除成功");
                    getAllCache();
                }else {
                    ZxToastUtil.centerToast("清除缓存失败");
                }
                break;
            case R.id.set_private_area:
                break;
            case R.id.set_out_login:
                showUpdate = HaiDialogUtil.showUpdate(getSupportFragmentManager(), "是否退出登录？", this::onViewClicked);
                break;
            case R.id.dialog_update_yes:
                loginOut();
                break;
            case R.id.dialog_update_no:
                showUpdate.dismissAllowingStateLoss();
                break;
        }
    }

    private void loginOut(){
        showUpdate.dismissAllowingStateLoss();
        //RxHttpUtils.cancel(OtherConstants.CANCEL_REQUEST);
        RxHttpUtils.cancelAll();
        ZxSharePreferenceUtil instance = ZxSharePreferenceUtil.getInstance();
        instance.init(this);
        instance.setLogin(false);
        Intent intent = new Intent();
        intent.setAction(OtherConstants.LOGIN_OUT);
        sendBroadcast(intent);
        ARouter.getInstance().build(PathConstant.ROUTE_LOGIN).navigation();
    }

    private void getAllCache(){
        String totalCacheSize = null;
        try {
            totalCacheSize = ZxCacheUtil.getTotalCacheSize(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        cacheSize.setText(totalCacheSize);
    }
}
