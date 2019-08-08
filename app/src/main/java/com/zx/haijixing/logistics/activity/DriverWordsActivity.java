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
import com.zx.haijixing.logistics.contract.DriverWordContract;
import com.zx.haijixing.logistics.entry.DriverWordEntry;
import com.zx.haijixing.logistics.presenter.DriverWordImp;
import com.zx.haijixing.share.PathConstant;
import com.zx.haijixing.share.base.BaseActivity;
import com.zx.haijixing.util.HaiTool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import zx.com.skytool.ZxSharePreferenceUtil;
import zx.com.skytool.ZxStatusBarCompat;
import zx.com.skytool.ZxToastUtil;

@Route(path = PathConstant.DRIVER_EVALUATE)
public class DriverWordsActivity extends BaseActivity<DriverWordImp> implements DriverWordContract.DriverWordView {

    @BindView(R.id.driver_words_back)
    ImageView back;
    @BindView(R.id.driver_words_title)
    TextView title;
    @BindView(R.id.driver_words_input)
    EditText input;
    @BindView(R.id.driver_words_search)
    Button search;
    @BindView(R.id.driver_words_data)
    RecyclerView data;
    private DriverWordAdapter driverWordAdapter;
    private List<DriverWordEntry> driverWordEntries = new ArrayList<>();
    private  Map<String,String> params = new HashMap<>();
    @Override
    protected void initView() {
        setTitleTopMargin(back);
        setTitleTopMargin(title);
        ZxSharePreferenceUtil instance = ZxSharePreferenceUtil.getInstance();
        instance.init(this);
        String token = (String) instance.getParam("token", "null");
        data.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        driverWordAdapter = new DriverWordAdapter(driverWordEntries);
        data.setAdapter(driverWordAdapter);

        params.put("token",token);
        params.put("driverName","");
        params.put("timestamp",System.currentTimeMillis()+"");
        params.put("sign","");
        params.put("sign",HaiTool.sign(params));
        mPresenter.driverWordMethod(params);
    }

    @Override
    protected void initInjector() {
        mPresenter = new DriverWordImp();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_driver_words;
    }

    @OnClick({R.id.driver_words_back, R.id.driver_words_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.driver_words_back:
                finish();
                break;
            case R.id.driver_words_search:
                String input = this.input.getText().toString().trim();
                params.put("driverName",input);
                params.put("timestamp",System.currentTimeMillis()+"");
                params.put("sign","");
                params.put("sign",HaiTool.sign(params));
                mPresenter.driverWordMethod(params);
                break;
        }
    }

    @Override
    public void setStatusBar() {
        ZxStatusBarCompat.translucentStatusBar(this,true);
    }

    @Override
    public void driverWordSuccess(List<DriverWordEntry> driverWordEntries) {
        if (driverWordEntries.size() == 0){
            ZxToastUtil.centerToast("暂无此司机的评价");
        }else {
            this.driverWordEntries.clear();
            this.driverWordEntries.addAll(driverWordEntries);
            driverWordAdapter.notifyDataSetChanged();
        }
    }
}
