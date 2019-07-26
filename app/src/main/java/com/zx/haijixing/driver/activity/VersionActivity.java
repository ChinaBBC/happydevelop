package com.zx.haijixing.driver.activity;

import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
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

import butterknife.BindView;
import butterknife.OnClick;

/**
 *
 *@作者 zx
 *@创建日期 2019/7/8 10:07
 *@描述 版本更新
 */
@Route(path = PathConstant.VERSION)
public class VersionActivity extends BaseActivity<VersionImp> implements VersionContract.VersionView {

    @BindView(R.id.common_title_back)
    ImageView back;
    @BindView(R.id.common_title_title)
    TextView title;
    @BindView(R.id.version_version_name)
    TextView versionName;
    @BindView(R.id.version_intro)
    TextView versionIntro;
    @BindView(R.id.version_version_update_area)
    ConstraintLayout updateArea;
    private CommonDialogFragment showUpdate;
    private VersionEntry versionEntry;

    @Override
    protected void initView() {
        title.setText(getHaiString(R.string.version));
        versionName.setText(getHaiString(R.string.now_version)+HaiTool.packageName(this));
    }

    @Override
    protected void initInjector() {
        ARouter.getInstance().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_version;
    }

    @OnClick({R.id.common_title_back, R.id.version_version_update_area})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.common_title_back:
                finish();
                break;
            case R.id.version_version_update_area:
                break;
            case R.id.dialog_update_no:
                showUpdate.dismissAllowingStateLoss();
                break;
            case R.id.dialog_update_yes:
                ARouter.getInstance().build(PathConstant.APK_ACTIVITY).withString("path",versionEntry.getDownloadUrl()).navigation();
                break;
        }
    }

    @Override
    public void versionSuccess(VersionEntry versionEntry) {
        this.versionEntry = versionEntry;
        if (HaiTool.packageCode(this)<versionEntry.getVersionNum()) {
            showUpdate = HaiDialogUtil.showUpdate(getSupportFragmentManager(),null, this::onViewClicked);
        }
    }
}
