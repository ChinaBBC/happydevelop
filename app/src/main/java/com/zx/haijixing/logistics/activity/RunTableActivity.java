package com.zx.haijixing.logistics.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bigkoo.pickerview.view.TimePickerView;
import com.zx.haijixing.R;
import com.zx.haijixing.logistics.adapter.RunTableAdapter;
import com.zx.haijixing.share.PathConstant;
import com.zx.haijixing.share.base.BaseActivity;
import com.zx.haijixing.util.HaiTool;

import butterknife.BindView;
import butterknife.OnClick;
import zx.com.skytool.ZxStatusBarCompat;

/**
 *
 *@作者 zx
 *@创建日期 2019/7/16 21:22
 *@描述 运营总表
 */
@Route(path = PathConstant.RUN_TABLE)
public class RunTableActivity extends BaseActivity {

    @BindView(R.id.run_table_back)
    ImageView back;
    @BindView(R.id.run_table_title)
    TextView title;
    @BindView(R.id.run_table_start)
    TextView start;
    @BindView(R.id.run_table_end)
    TextView end;
    @BindView(R.id.run_table_word5)
    TextView word5;
    @BindView(R.id.run_table_companyL)
    TextView company;
    @BindView(R.id.run_table_img4)
    ImageView img4;
    @BindView(R.id.run_table_search)
    Button search;
    @BindView(R.id.run_table_data)
    RecyclerView data;
    private TimePickerView timePickerView;

    private int loginType = 1;
    @Override
    protected void initView() {
        setTitleTopMargin(back);
        setTitleTopMargin(title);
        if (loginType == 1){
            word5.setVisibility(View.VISIBLE);
            company.setVisibility(View.VISIBLE);
            img4.setVisibility(View.VISIBLE);
        }
        data.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        data.setAdapter(new RunTableAdapter());
        timePickerView = HaiTool.initTimePickers(this, start, end);
    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_run_table;
    }

    @OnClick({R.id.run_table_back, R.id.run_table_start, R.id.run_table_end, R.id.run_table_search,R.id.run_table_companyL})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.run_table_back:
                finish();
                break;
            case R.id.run_table_start:
                timePickerView.show();
                break;
            case R.id.run_table_end:
                timePickerView.show();
                break;
            case R.id.run_table_search:
                break;
            case R.id.run_table_companyL:
                break;
        }
    }

    @Override
    public void setStatusBar() {
        ZxStatusBarCompat.translucentStatusBar(this,true);
    }
}
