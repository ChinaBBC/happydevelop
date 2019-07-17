package com.zx.haijixing.driver.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zx.haijixing.R;
import com.zx.haijixing.driver.adapter.LinesAdapter;
import com.zx.haijixing.share.PathConstant;
import com.zx.haijixing.share.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @作者 zx
 * @创建日期 2019/7/8 14:08
 * @描述 车辆及路线
 */
@Route(path = PathConstant.LINES)
public class LinesActivity extends BaseActivity {


    @BindView(R.id.lines_data)
    RecyclerView linesData;
    @BindView(R.id.common_title_back)
    ImageView back;
    @BindView(R.id.common_title_title)
    TextView title;

    @Override
    protected void initView() {
        title.setText(getHaiString(R.string.car_and_lines));
        linesData.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        linesData.setAdapter(new LinesAdapter());
    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_lines;
    }

    @OnClick(R.id.common_title_back)
    public void onViewClicked() {
        finish();
    }
}
