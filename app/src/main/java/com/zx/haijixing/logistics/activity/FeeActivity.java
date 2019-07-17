package com.zx.haijixing.logistics.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zx.haijixing.R;
import com.zx.haijixing.share.PathConstant;

/**
 *
 *@作者 zx
 *@创建日期 2019/7/16 16:32
 *@描述 收费标准
 */
@Route(path = PathConstant.FEE)
public class FeeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fee);
    }
}
