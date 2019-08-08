package com.zx.haijixing.manager.fragment;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.zx.haijixing.R;
import com.zx.haijixing.logistics.contract.CompanyCenterContract;
import com.zx.haijixing.logistics.entry.CompanyEntry;
import com.zx.haijixing.logistics.presenter.CompanyCenterImp;
import com.zx.haijixing.share.PathConstant;
import com.zx.haijixing.share.base.BaseFragment;
import com.zx.haijixing.util.HaiTool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import zx.com.skytool.ZxSharePreferenceUtil;

/**
 * @作者 zx
 * @创建日期 2019/7/17 17:35
 * @描述 管理端个人中心
 */
public class ManagerCenterFragment extends BaseFragment<CompanyCenterImp> implements CompanyCenterContract.CompanyCenterView {
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
    private ArrayList<CompanyEntry> companyEntries;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_manager_center;
    }

    @Override
    protected void initData() {
        mPresenter = new CompanyCenterImp();
    }

    @Override
    protected void initView(View view) {
        ZxSharePreferenceUtil instance = ZxSharePreferenceUtil.getInstance();
        instance.init(getContext());
        String token = (String)instance.getParam("token","null");
        String name = (String)instance.getParam("user_name","null");
        String phone = (String)instance.getParam("user_phone","null");
        String head = (String)instance.getParam("user_head","null");

        namePhone.setText(name);
        content.setText(phone);
        RequestOptions options = new RequestOptions().circleCrop().error(R.mipmap.user_head);
        Glide.with(this).load(head).apply(options).into(this.head);

        Map<String,String> params = new HashMap<>();
        params.put("token",token);
        params.put("timestamp",System.currentTimeMillis()+"");
        params.put("sign","");
        params.put("sign",HaiTool.sign(params));
        mPresenter.companyMethod(params);
    }

    @OnClick({R.id.company_center_fee, R.id.company_center_table, R.id.company_center_driver_words, R.id.company_center_setting})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.company_center_fee:
                ARouter.getInstance().build(PathConstant.FEE_STATISTICS)
                        .withParcelableArrayList("companys",companyEntries)
                        .navigation();
                break;
            case R.id.company_center_table:
                ARouter.getInstance().build(PathConstant.RUN_TABLE)
                        .withParcelableArrayList("companys",companyEntries)
                        .navigation();
                break;
            case R.id.company_center_driver_words:
                ARouter.getInstance().build(PathConstant.DRIVER_EVALUATE).navigation();
                break;
            case R.id.company_center_setting:
                ARouter.getInstance().build(PathConstant.SET).navigation();
                break;
        }
    }

    @Override
    public void companySuccess(List<CompanyEntry> companyEntries) {
        if (companyEntries.size()>0){
            this.companyEntries = (ArrayList<CompanyEntry>) companyEntries;
        }
    }
}
