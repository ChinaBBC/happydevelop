package com.zx.haijixing.logistics.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.zx.haijixing.R;
import com.zx.haijixing.logistics.adapter.LinesManageAdapter;
import com.zx.haijixing.share.PathConstant;
import com.zx.haijixing.share.base.BaseFragment;

import butterknife.BindView;

/**
 *
 *@作者 zx
 *@创建日期 2019/7/16 14:23
 *@描述 路线管理
 */
public class LinesManageFragment extends BaseFragment implements View.OnClickListener {
    @BindView(R.id.fragment_cancel_data)
    RecyclerView lineData;
    private LinesManageAdapter linesManageAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_cancel;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(View view) {
        lineData.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        linesManageAdapter = new LinesManageAdapter();
        linesManageAdapter.setOnClickListener(this::onClick);
        lineData.setAdapter(linesManageAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.lines_manage_manage:
                ARouter.getInstance().build(PathConstant.CLASSES_MANAGE).navigation();
                break;
        }
    }
}
