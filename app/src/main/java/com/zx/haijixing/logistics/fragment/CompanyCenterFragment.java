package com.zx.haijixing.logistics.fragment;

import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.zx.haijixing.R;
import com.zx.haijixing.share.PathConstant;
import com.zx.haijixing.share.base.BaseFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @作者 zx
 * @创建日期 2019/7/16 10:19
 * @描述 公司中心
 */
public class CompanyCenterFragment extends BaseFragment {
    @BindView(R.id.company_center_vip)
    TextView vip;
    @BindView(R.id.company_center_name)
    TextView name;
    @BindView(R.id.company_center_company)
    TextView company;
    @BindView(R.id.company_center_fee)
    ConstraintLayout fee;
    @BindView(R.id.company_center_table)
    ConstraintLayout table;
    @BindView(R.id.company_center_driver_words)
    ConstraintLayout words;
    @BindView(R.id.company_center_setting)
    ConstraintLayout setting;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_company_center;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(View view) {

    }

    @OnClick({R.id.company_center_fee, R.id.company_center_table, R.id.company_center_driver_words, R.id.company_center_setting})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.company_center_fee:
                ARouter.getInstance().build(PathConstant.FEE_STATISTICS).navigation();
                break;
            case R.id.company_center_table:
                ARouter.getInstance().build(PathConstant.RUN_TABLE).navigation();
                break;
            case R.id.company_center_driver_words:
                ARouter.getInstance().build(PathConstant.DRIVER_EVALUATE).navigation();
                break;
            case R.id.company_center_setting:
                ARouter.getInstance().build(PathConstant.SET).navigation();
                break;
        }
    }
}
