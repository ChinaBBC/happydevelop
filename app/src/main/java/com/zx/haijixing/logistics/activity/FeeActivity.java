package com.zx.haijixing.logistics.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.zx.haijixing.R;
import com.zx.haijixing.logistics.adapter.FeeAdapter;
import com.zx.haijixing.logistics.contract.FeeContract;
import com.zx.haijixing.logistics.presenter.FeeImp;
import com.zx.haijixing.share.PathConstant;
import com.zx.haijixing.share.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zx.com.skytool.ZxSharePreferenceUtil;

/**
 * @作者 zx
 * @创建日期 2019/7/16 16:32
 * @描述 收费标准
 */
@Route(path = PathConstant.FEE)
public class FeeActivity extends BaseActivity<FeeImp> implements FeeContract.FeeView {

    @BindView(R.id.common_title_back)
    ImageView back;
    @BindView(R.id.common_title_title)
    TextView title;
    @BindView(R.id.fee_rv_data)
    RecyclerView feeRvData;

    @Autowired(name = "linesId")
    public String linesId;

    @Override
    protected void initView() {
        ZxSharePreferenceUtil instance = ZxSharePreferenceUtil.getInstance();
        instance.init(this);
        String token = (String) instance.getParam("token", "null");
        title.setText(getHaiString(R.string.type_price));

        mPresenter.feeMethod(token,linesId);
    }

    @Override
    protected void initInjector() {
        mPresenter = new FeeImp();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_fee;
    }

    @OnClick(R.id.common_title_back)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void feeSuccess(List<List<String>> data) {
        int number = 0;
        List<String> all = new ArrayList<>();
        for (int i=0;i<data.size();i++){
            List<String> list = data.get(i);
            number = list.size();
            all.addAll(list);
        }
        FeeAdapter adapter = new FeeAdapter(all);
        adapter.setNumber(number);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,number);
        gridLayoutManager.setOrientation(GridLayout.VERTICAL);
        feeRvData.setLayoutManager(gridLayoutManager);
        feeRvData.setAdapter(adapter);
    }
}
