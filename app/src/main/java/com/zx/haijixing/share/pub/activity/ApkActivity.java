package com.zx.haijixing.share.pub.activity;

import android.app.Dialog;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.zx.haijixing.R;
import com.zx.haijixing.share.PathConstant;
import com.zx.haijixing.share.base.BaseActivity;
import com.zx.haijixing.share.pub.contract.ApkContract;
import com.zx.haijixing.share.pub.imp.ApkImp;
import com.zx.haijixing.util.ApkUtils;

import butterknife.BindView;
import butterknife.OnClick;
import zx.com.skytool.ZxToastUtil;
/**
 *
 *@作者 zx
 *@创建日期 2019/7/11 10:17
 *@描述 版本更新
 */
@Route(path = PathConstant.APK_ACTIVITY)
public class ApkActivity extends BaseActivity<ApkImp> implements ApkContract.ApkView {

    @BindView(R.id.common_title_back)
    ImageView back;
    @BindView(R.id.common_title_title)
    TextView title;
    @BindView(R.id.apk_update_content)
    TextView content;
    @BindView(R.id.apk_update)
    Button update;

    @Autowired(name = "path")
    public String url;

    private Dialog mDialog1;
    private ProgressBar mProgressBar;
    private TextView mPrecent;

    @Override
    protected void initView() {
        title.setText(getHaiString(R.string.version_update));
    }

    @Override
    protected void initInjector() {
        mPresenter = new ApkImp();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_apk;
    }

    @OnClick({R.id.common_title_back, R.id.apk_update})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.common_title_back:
                finish();
                break;
            case R.id.apk_update:
                show();
                mPresenter.apkMethod(url);
                break;
        }
    }

    @Override
    public void apkSuccess(String apkFile) {
        mDialog1.dismiss();
        ApkUtils.installAPk(this, apkFile);
    }

    @Override
    public void apkProgress(float progress) {
        mProgressBar.setProgress((int) progress);
        mPrecent.setText( progress+"%");
    }

    private void show(){
        View view = this.getLayoutInflater().inflate(R.layout.dialog_down_progress, null);
        //显示进度的对话框
        mDialog1 = new Dialog(this, R.style.Theme_AppCompat_Dialog_Alert);
        mDialog1.setCancelable(false);
        mDialog1.setContentView(view);
        mProgressBar = mDialog1.findViewById(R.id.dialog_down_pp_myPro);
        mPrecent = mDialog1.findViewById(R.id.dialog_down_tv_percent);
        mDialog1.show();
    }

    @Override
    public void showFaild(String errorMsg) {
        mDialog1.dismiss();
        ZxToastUtil.centerToast(errorMsg);
    }
}
