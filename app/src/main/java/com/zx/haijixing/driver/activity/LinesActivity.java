package com.zx.haijixing.driver.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zx.haijixing.R;
import com.zx.haijixing.driver.adapter.LinesAdapter;
import com.zx.haijixing.driver.contract.LinesContract;
import com.zx.haijixing.driver.entry.DriverClassEntry;
import com.zx.haijixing.driver.presenter.LineImp;
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

/**
 * @作者 zx
 * @创建日期 2019/7/8 14:08
 * @描述 车辆及路线
 */
@Route(path = PathConstant.LINES)
public class LinesActivity extends BaseActivity<LineImp> implements LinesContract.LinesView {


    @BindView(R.id.lines_data)
    RecyclerView linesData;
    @BindView(R.id.common_title_back)
    ImageView back;
    @BindView(R.id.common_title_title)
    TextView title;
    @BindView(R.id.lines_noData)
    TextView noData;

    private List<DriverClassEntry> driverClassEntries = new ArrayList<>();
    private LinesAdapter linesAdapter;
    private String token;
    private Map<String, String> params = new HashMap<>();

    @Override
    protected void initView() {
        ZxSharePreferenceUtil instance = ZxSharePreferenceUtil.getInstance();
        instance.init(this);
        String token = (String)instance.getParam("token","null");
        title.setText(getHaiString(R.string.car_and_lines));
        linesData.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        linesAdapter = new LinesAdapter(driverClassEntries);
        linesData.setAdapter(linesAdapter);

        params.put("token",token);
        params.put("timestamp",System.currentTimeMillis()+"");
        params.put("sign",HaiTool.sign(params));
        mPresenter.linesMethod(params);
    }

    @Override
    protected void initInjector() {
        mPresenter = new LineImp();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_lines;
    }

    @OnClick(R.id.common_title_back)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void linesSuccess(List<DriverClassEntry> driverClassEntries) {
        this.driverClassEntries.addAll(driverClassEntries);
        linesAdapter.notifyDataSetChanged();
        noData.setVisibility(this.driverClassEntries.size() == 0?View.VISIBLE:View.GONE);
    }
}
