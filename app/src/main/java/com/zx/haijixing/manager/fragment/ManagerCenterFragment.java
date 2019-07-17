package com.zx.haijixing.manager.fragment;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.zx.haijixing.R;
import com.zx.haijixing.share.PathConstant;
import com.zx.haijixing.share.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @作者 zx
 * @创建日期 2019/7/17 17:35
 * @描述 管理端个人中心
 */
public class ManagerCenterFragment extends BaseFragment {
    @BindView(R.id.manager_user_head)
    ImageView head;
    @BindView(R.id.manager_user_name_phone)
    TextView namePhone;
    @BindView(R.id.manager_user_content)
    TextView content;
    @BindView(R.id.company_center_fee)
    ConstraintLayout fee;
    @BindView(R.id.company_center_table)
    ConstraintLayout table;
    @BindView(R.id.company_center_driver_words)
    ConstraintLayout driverWords;
    @BindView(R.id.company_center_setting)
    ConstraintLayout setting;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_manager_center;
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
