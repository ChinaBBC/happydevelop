package com.zx.haijixing.driver.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zx.haijixing.R;
import com.zx.haijixing.driver.adapter.EvaluateAdapter;
import com.zx.haijixing.share.PathConstant;
import com.zx.haijixing.share.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @作者 zx
 * @创建日期 2019/7/8 11:11
 * @描述 评价
 */
@Route(path = PathConstant.EVALUATE)
public class EvaluateActivity extends BaseActivity {

    @BindView(R.id.common_title_back)
    ImageView back;
    @BindView(R.id.common_title_title)
    TextView title;
   /* @BindView(R.id.evaluate_user_head)
    ImageView userHead;
    @BindView(R.id.evaluate_user_name)
    TextView userName;
    @BindView(R.id.evaluate_all_score)
    TextView allScore;
    @BindView(R.id.evaluate_service_manner)
    TextView serviceManner;
    @BindView(R.id.evaluate_arrive_time)
    TextView arriveTime;
    @BindView(R.id.evaluate_goods_intact)
    TextView goodsIntact;*/
    @BindView(R.id.evaluate_data)
    RecyclerView evaluateData;

    @Override
    protected void initView() {
        title.setText(getHaiString(R.string.evaluate));
        evaluateData.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        evaluateData.setAdapter(new EvaluateAdapter());
    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_evaluate;
    }

    @OnClick(R.id.common_title_back)
    public void onViewClicked() {
        finish();
    }
}
