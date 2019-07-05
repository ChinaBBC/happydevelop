package com.zx.haijixing.driver.fragment;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.zx.haijixing.R;
import com.zx.haijixing.driver.adapter.IndexAdapter;
import com.zx.haijixing.share.RoutePathConstant;
import com.zx.haijixing.share.base.BaseFragment;
import com.zx.haijixing.util.CommonDialogFragment;
import com.zx.haijixing.util.HaiDialogUtil;

import butterknife.BindView;
import butterknife.OnClick;
/**
 *
 *@作者 zx
 *@创建日期 2019/7/3 10:12
 *@描述 首页
 */
public class IndexFragment extends BaseFragment{


    @BindView(R.id.index_one)
    LinearLayout one;
    @BindView(R.id.index_order_number)
    EditText orderNumber;
    @BindView(R.id.index_search)
    TextView search;
    @BindView(R.id.index_scan_code)
    ImageView scanCode;
    @BindView(R.id.index_rv_body)
    RecyclerView rvBody;
    private IndexAdapter indexAdapter;
    private CommonDialogFragment showClock;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_index;
    }

    @Override
    protected void initInjector() {
        ARouter.getInstance().inject(this);
    }

    @Override
    protected void initView(View view) {

        setTitleTopMargin(one,0);
        setTitleTopMargin(scanCode,20);

        rvBody.setLayoutManager(new LinearLayoutManager(this.getContext(),LinearLayoutManager.VERTICAL,false));
        indexAdapter = new IndexAdapter();
        rvBody.setAdapter(indexAdapter);
        indexAdapter.setClickListener(this::onViewClicked);

    }

    @OnClick({R.id.index_search, R.id.index_scan_code})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.index_search:
                ARouter.getInstance().build(RoutePathConstant.DRIVER_SEARCH).navigation();
                break;
            case R.id.index_scan_code:
                break;
            case R.id.index_header_clock:
                showClock = HaiDialogUtil.showClock(getFragmentManager(), this::onViewClicked);
                break;
            case R.id.dialog_clock_yes:
                showClock.dismissAllowingStateLoss();
                break;
            case R.id.dialog_clock_no:
                showClock.dismissAllowingStateLoss();
                break;
            case R.id.index_header_notify:
                ARouter.getInstance().build(RoutePathConstant.DRIVER_NOTIFY).navigation();
                break;
            case R.id.index_header_services:
                ARouter.getInstance().build(RoutePathConstant.DRIVER_SERVICES).navigation();
                break;
        }
    }

}
