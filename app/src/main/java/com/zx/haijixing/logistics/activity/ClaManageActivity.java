package com.zx.haijixing.logistics.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zx.haijixing.R;
import com.zx.haijixing.logistics.adapter.ClaManageAdapter;
import com.zx.haijixing.share.PathConstant;
import com.zx.haijixing.share.base.BaseActivity;
import com.zx.haijixing.util.HaiDialogUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @作者 zx
 * @创建日期 2019/7/16 16:32
 * @描述 班次管理
 */
@Route(path = PathConstant.CLASSES_MANAGE)
public class ClaManageActivity extends BaseActivity{

    @BindView(R.id.common_title_title)
    TextView title;
    @BindView(R.id.cla_data)
    RecyclerView claData;

    @Override
    protected void initView() {
        title.setText(getHaiString(R.string.classes_manage));
        claData.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        ClaManageAdapter adapter = new ClaManageAdapter();
        adapter.setOnClickListener(this::onViewClicked);
        claData.setAdapter(adapter);
    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_cla_manage;
    }

    @OnClick(R.id.common_title_back)
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.common_title_back:
                finish();
                break;
            case R.id.cla_manage_editor:
                HaiDialogUtil.showAddEditor(getSupportFragmentManager(),null,null);
                break;
        }

    }
}
