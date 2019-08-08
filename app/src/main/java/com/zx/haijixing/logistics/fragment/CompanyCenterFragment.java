package com.zx.haijixing.logistics.fragment;

import android.support.constraint.ConstraintLayout;
import android.view.View;
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
import butterknife.OnClick;
import zx.com.skytool.ZxLogUtil;
import zx.com.skytool.ZxSharePreferenceUtil;
import zx.com.skytool.ZxStringUtil;
import zx.com.skytool.ZxToastUtil;

/**
 * @作者 zx
 * @创建日期 2019/7/16 10:19
 * @描述 公司中心
 */
public class CompanyCenterFragment extends BaseFragment<CompanyCenterImp> implements CompanyCenterContract.CompanyCenterView {
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

    private ArrayList<CompanyEntry> companyEntries;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_company_center;
    }

    @Override
    protected void initData() {
        mPresenter = new CompanyCenterImp();
    }

    @Override
    protected void initView(View view) {
        ZxSharePreferenceUtil instance = ZxSharePreferenceUtil.getInstance();
        instance.init(getContext());
        String name = (String)instance.getParam("user_name","null");
        String token = (String)instance.getParam("token","null");

        this.name.setText(name);

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
                if (companyEntries == null || companyEntries.size() == 0){
                    ZxToastUtil.centerToast("未查询到您的公司信息");
                }else {
                    ARouter.getInstance().build(PathConstant.RUN_TABLE)
                            .withParcelableArrayList("companys",companyEntries)
                            .navigation();
                }
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
            CompanyEntry companyEntry = companyEntries.get(0);
            this.company.setText(companyEntry.getcName());
            this.companyEntries = (ArrayList<CompanyEntry>) companyEntries;
        }
    }
}
