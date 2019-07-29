package com.zx.haijixing.share.pub.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.zx.haijixing.R;
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
import zx.com.skytool.ZxLogUtil;
import zx.com.skytool.ZxSharePreferenceUtil;
import zx.com.skytool.ZxToastUtil;


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
        this.versionEntry = versionEntry;
        if (HaiTool.packageCode(this) < versionEntry.getVersionNum()) {
            showUpdate = HaiDialogUtil.showUpdate(getSupportFragmentManager(), null, this::onViewClicked);
        }else {
            startTimer = new StartTimer(3000, 1000);
            startTimer.start();
        }
    }

    @OnClick(R.id.sample_text)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.dialog_update_no:
                showUpdate.dismissAllowingStateLoss();
                break;
            case R.id.dialog_update_yes:
                ARouter.getInstance().build(PathConstant.APK_ACTIVITY).withString("path", versionEntry.getDownloadUrl()).navigation();
                break;
            case R.id.sample_text:
                checkLogin();
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 220:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mPresenter.versionMethod();
                } else {
                    ZxToastUtil.centerToast("请开启权限");
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
    private void permission() {
        final List<String> permissionsList = new ArrayList<>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(LaunchActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) ||
                    ActivityCompat.shouldShowRequestPermissionRationale(LaunchActivity.this, Manifest.permission.RECORD_AUDIO) ||
                    ActivityCompat.shouldShowRequestPermissionRationale(LaunchActivity.this, Manifest.permission.CAMERA) ||
                    ActivityCompat.shouldShowRequestPermissionRationale(LaunchActivity.this, Manifest.permission.CALL_PHONE) ||
                    ActivityCompat.shouldShowRequestPermissionRationale(LaunchActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) ||
                    ActivityCompat.shouldShowRequestPermissionRationale(LaunchActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) ||
                    ActivityCompat.shouldShowRequestPermissionRationale(LaunchActivity.this, Manifest.permission.WRITE_SETTINGS) ||
                    ActivityCompat.shouldShowRequestPermissionRationale(LaunchActivity.this, Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS) ||
                    ActivityCompat.shouldShowRequestPermissionRationale(LaunchActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            } else {
                if ((checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED))
                    permissionsList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                if ((checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED))
                    permissionsList.add(Manifest.permission.READ_EXTERNAL_STORAGE);
                if ((checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED))
                    permissionsList.add(Manifest.permission.ACCESS_COARSE_LOCATION);
                if ((checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED))
                    permissionsList.add(Manifest.permission.CAMERA);
                if ((checkSelfPermission(Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED))
                    permissionsList.add(Manifest.permission.RECORD_AUDIO);
                if ((checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED))
                    permissionsList.add(Manifest.permission.ACCESS_FINE_LOCATION);
                if ((checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED))
                    permissionsList.add(Manifest.permission.CALL_PHONE);
                if ((checkSelfPermission(Manifest.permission.WRITE_SETTINGS) != PackageManager.PERMISSION_GRANTED))
                    permissionsList.add(Manifest.permission.WRITE_SETTINGS);
                if ((checkSelfPermission(Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS) != PackageManager.PERMISSION_GRANTED))
                    permissionsList.add(Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS);
                if (permissionsList.size() == 0) {
                    mPresenter.versionMethod();
                } else {
                    requestPermissions(permissionsList.toArray(new String[permissionsList.size()]), 220);
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
        if (instance.isLogin()){
            ARouter.getInstance().build(PathConstant.DRIVER_MAIN).navigation();
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
