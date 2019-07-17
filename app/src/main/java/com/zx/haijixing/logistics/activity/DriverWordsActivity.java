package com.zx.haijixing.logistics.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bigkoo.pickerview.view.TimePickerView;
import com.zx.haijixing.R;
import com.zx.haijixing.logistics.adapter.DriverWordAdapter;
import com.zx.haijixing.share.PathConstant;
import com.zx.haijixing.share.base.BaseActivity;
import com.zx.haijixing.util.HaiTool;

import butterknife.BindView;
import butterknife.OnClick;
import zx.com.skytool.ZxStatusBarCompat;

@Route(path = PathConstant.DRIVER_EVALUATE)
public class DriverWordsActivity extends BaseActivity {

    @BindView(R.id.driver_words_back)
    ImageView back;
    @BindView(R.id.driver_words_title)
    TextView title;
    @BindView(R.id.driver_words_start)
    TextView start;
    @BindView(R.id.driver_words_end)
    TextView end;
    @BindView(R.id.driver_words_input)
    EditText input;
    @BindView(R.id.driver_words_search)
    Button search;
    @BindView(R.id.driver_words_data)
    RecyclerView data;
    private TimePickerView timePickerView;

    @Override
    protected void initView() {
        setTitleTopMargin(back);
        setTitleTopMargin(title);
        timePickerView = HaiTool.initTimePickers(this, start, end);
        data.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        data.setAdapter(new DriverWordAdapter());
    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_driver_words;
    }

    @OnClick({R.id.driver_words_back, R.id.driver_words_start, R.id.driver_words_end, R.id.driver_words_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.driver_words_back:
                finish();
                break;
            case R.id.driver_words_start:
                timePickerView.show();
                break;
            case R.id.driver_words_end:
                timePickerView.show();
                break;
            case R.id.driver_words_search:
                break;
        }
    }

    @Override
    public void setStatusBar() {
        ZxStatusBarCompat.translucentStatusBar(this,true);
    }
}
