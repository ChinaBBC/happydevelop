package com.zx.haijixing.share.pub.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.zx.haijixing.R;
import com.zx.haijixing.share.OtherConstants;
import com.zx.haijixing.share.PathConstant;
import com.zx.haijixing.share.base.BaseActivity;
import com.zx.haijixing.share.pub.contract.VersionContract;
import com.zx.haijixing.share.pub.entry.VersionEntry;
import com.zx.haijixing.share.pub.imp.VersionImp;
import com.zx.haijixing.util.CommonDialogFragment;
import com.zx.haijixing.util.HaiDialogUtil;
import com.zx.haijixing.util.HaiTool;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import zx.com.skytool.ZxSharePreferenceUtil;


/**
 * @作者 zx
 * @创建日期 2019/6/14 12:00
 * @描述 启动页面
 */
public class LaunchActivity extends BaseActivity<VersionImp> implements VersionContract.VersionView {


    @BindView(R.id.sample_text)
    TextView jump;

    private CommonDialogFragment showUpdate;
    private VersionEntry versionEntry;
    private StartTimer startTimer;
    private CommonDialogFragment open;
    private int tag = 0;//0更新，1权限

    @Override
    protected void initView() {
        permission();
    }

    @Override
    protected void initInjector() {
        mPresenter = new VersionImp();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_launch;
    }

    @Override
    public void versionSuccess(VersionEntry versionEntry) {
        jump.setVisibility(View.VISIBLE);
        this.versionEntry = versionEntry;
        if (HaiTool.packageCode(this) < versionEntry.getVersionNum()) {
            tag = 0;
            showUpdate = HaiDialogUtil.showUpdate(getSupportFragmentManager(), null, this::onViewClicked);
        }else {
            startTimer = new StartTimer(3000, 1000);
            startTimer.start();
        }
    }

    @Override
    public void payWayShowSuccess(String online, String offline) {
        ZxSharePreferenceUtil instance = ZxSharePreferenceUtil.getInstance();
        instance.init(this);
        instance.saveParam("online",online);
        instance.saveParam("offline",offline);
    }

    @OnClick(R.id.sample_text)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.dialog_update_no:
                if (tag == 1){
                    open.dismissAllowingStateLoss();
                    finish();
                }else {
                    showUpdate.dismissAllowingStateLoss();
                    startTimer = new StartTimer(3000, 1000);
                    startTimer.start();
                }
                break;
            case R.id.dialog_update_yes:
                if (tag == 0){
                    ARouter.getInstance().build(PathConstant.APK_ACTIVITY)
                            .withString("path", versionEntry.getDownloadUrl())
                            .withString("content",versionEntry.getRemark())
                            .navigation();
                }else {
                    //引导用户到设置中去进行设置
                    Intent intent = new Intent();
                    intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                    intent.setData(Uri.fromParts("package", getPackageName(), null));
                    startActivity(intent);
                    finish();
                }
                break;
            case R.id.sample_text:
                checkLogin();
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case OtherConstants.PERMISSION_REQUEST:
                for (int i=0;i<grantResults.length;i++){
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        mPresenter.versionMethod();
                    } else {
                        tag = 1;
                        open = HaiDialogUtil.showUpdate(getSupportFragmentManager(), "请开启权限", this::onViewClicked);
                    }
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
    private void permission() {
        mPresenter.payWayShowMethod();
        final List<String> permissionsList = new ArrayList<>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(LaunchActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) ||
                    ActivityCompat.shouldShowRequestPermissionRationale(LaunchActivity.this, Manifest.permission.CAMERA) ||
                    ActivityCompat.shouldShowRequestPermissionRationale(LaunchActivity.this, Manifest.permission.CALL_PHONE) ||
                    ActivityCompat.shouldShowRequestPermissionRationale(LaunchActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) ||
                    ActivityCompat.shouldShowRequestPermissionRationale(LaunchActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) ||
                    ActivityCompat.shouldShowRequestPermissionRationale(LaunchActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                tag = 1;
                open = HaiDialogUtil.showUpdate(getSupportFragmentManager(), "请开启权限", this::onViewClicked);
            } else {
                if ((checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED))
                    permissionsList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                if ((checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED))
                    permissionsList.add(Manifest.permission.READ_EXTERNAL_STORAGE);
                if ((checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED))
                    permissionsList.add(Manifest.permission.ACCESS_COARSE_LOCATION);
                if ((checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED))
                    permissionsList.add(Manifest.permission.CAMERA);
                if ((checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED))
                    permissionsList.add(Manifest.permission.ACCESS_FINE_LOCATION);
                if ((checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED))
                    permissionsList.add(Manifest.permission.CALL_PHONE);
                if (permissionsList.size() == 0) {
                    mPresenter.versionMethod();
                } else {
                    requestPermissions(permissionsList.toArray(new String[permissionsList.size()]), OtherConstants.PERMISSION_REQUEST);
                }
            }
        } else {
            mPresenter.versionMethod();
        }

    }

    //检查登录
    private void checkLogin(){
        if (startTimer != null && startTimer.getRunStatus()) {
            startTimer.cancel();
        }
        ZxSharePreferenceUtil instance = ZxSharePreferenceUtil.getInstance();
        instance.init(this);
        String loginType = (String) instance.getParam("login_type", "4");

        if (instance.isLogin()){
            switch (loginType){
                case OtherConstants.LOGIN_DRIVER:
                    ARouter.getInstance().build(PathConstant.DRIVER_MAIN).navigation();
                    break;
                case OtherConstants.LOGIN_LOGISTICS:
                    ARouter.getInstance().build(PathConstant.LOGISTICS).navigation();
                    break;
                case OtherConstants.LOGIN_MANAGER:
                    ARouter.getInstance().build(PathConstant.MANAGER).navigation();
                    break;
            }
            finish();
        }else {
            ARouter.getInstance().build(PathConstant.ROUTE_LOGIN).navigation();
            finish();
        }
    }


    //倒计时内部类实现
    class StartTimer extends CountDownTimer {

        private boolean isRunning = true;

        /**
         * @param millisInFuture    结束时间
         * @param countDownInterval 计时间隔
         */
        public StartTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        public boolean getRunStatus() {
            return isRunning;
        }

        @Override
        public void onTick(long millisUntilFinished) {
            isRunning = true;
        }

        @Override
        public void onFinish() {
            isRunning = false;
            checkLogin();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (startTimer!= null && startTimer.isRunning)
            startTimer.cancel();
    }
}
