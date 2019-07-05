package com.zx.haijixing.driver.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zx.haijixing.R;
import com.zx.haijixing.driver.adapter.CcAdapter;
import com.zx.haijixing.share.base.BaseFragment;

import butterknife.BindView;

/**
 *
 *@作者 zx
 *@创建日期 2019/7/5 11:30
 *@描述 已完成
 */
public class CompleteFragment extends BaseFragment {
    @BindView(R.id.fragment_complete_data)
    RecyclerView completeData;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_complete;
    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected void initView(View view) {
        completeData.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        completeData.setAdapter(new CcAdapter());
    }
}
