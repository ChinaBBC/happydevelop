package com.zx.haijixing.driver.activity;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.zx.haijixing.R;
import com.zx.haijixing.share.RoutePathConstant;
import com.zx.haijixing.share.base.BaseActivity;
import com.zx.haijixing.util.HaiTool;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zx.com.skytool.ZxCacheUtil;
import zx.com.skytool.ZxStringUtil;

@Route(path = RoutePathConstant.SET)
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

    @Override
    protected void initView() {
        title.setText(getHaiString(R.string.set));
        versionName.setText(getHaiString(R.string.version)+HaiTool.packageName(this));
        String totalCacheSize = null;
        try {
            totalCacheSize = ZxCacheUtil.getTotalCacheSize(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        cacheSize.setText(totalCacheSize);

    }

    @Override
    protected void initInjector() {
        ARouter.getInstance().inject(this);
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
                ARouter.getInstance().build(RoutePathConstant.VERSION).navigation();
                break;
            case R.id.set_clean_cache_area:
                break;
            case R.id.set_private_area:
                break;
            case R.id.set_out_login:
                break;
        }
    }
}
