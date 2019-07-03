package com.zx.haijixing.driver.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zx.haijixing.R;
import com.zx.haijixing.driver.adapter.NotifyAdapter;
import com.zx.haijixing.share.RoutePathConstant;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
/**
 *
 *@作者 zx
 *@创建日期 2019/7/3 16:34
 *@描述 消息中心
 */
@Route(path = RoutePathConstant.DRIVER_NOTIFY)
public class NotifyActivity extends AppCompatActivity {

    @BindView(R.id.common_title_back)
    ImageView back;
    @BindView(R.id.common_title_title)
    TextView title;
    @BindView(R.id.notify_data)
    RecyclerView notifyData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify);
        ButterKnife.bind(this);
        title.setText(getString(R.string.notify_center));

        NotifyAdapter notifyAdapter = new NotifyAdapter();
        notifyData.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        notifyData.setAdapter(notifyAdapter);
    }

    @OnClick(R.id.common_title_back)
    public void onViewClicked() {
        finish();
    }
}