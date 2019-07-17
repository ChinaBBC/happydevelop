package com.zx.haijixing.logistics.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zx.haijixing.R;
import com.zx.haijixing.share.PathConstant;

/**
 *
 *@作者 zx
 *@创建日期 2019/7/17 16:58
 *@描述 查看物流
 */
@Route(path = PathConstant.CHECK_LOGISTICS)
public class CheckLogisticsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_logistics);
    }
}
