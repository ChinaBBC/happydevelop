package com.zx.haijixing.driver.fragment;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zx.haijixing.R;
import com.zx.haijixing.share.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 个人中心
 *
 * @author ZX
 * @time 2019/7/7 10:09
 **/

public class MineFragment extends BaseFragment {
    @BindView(R.id.mine_count_area)
    ConstraintLayout mineCountArea;
    @BindView(R.id.mine_word_area)
    ConstraintLayout mineWordArea;
    @BindView(R.id.mine_shifts_area)
    ConstraintLayout mineShiftsArea;
    @BindView(R.id.mine_change_area)
    ConstraintLayout mineChangeArea;
    @BindView(R.id.mine_customer_area)
    ConstraintLayout mineCustomerArea;
    @BindView(R.id.mine_set_area)
    ConstraintLayout mineSetArea;
    @BindView(R.id.mine_customer_phone)
    TextView customerPhone;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected void initView(View view) {

    }

    @OnClick({R.id.mine_count_area, R.id.mine_word_area, R.id.mine_shifts_area, R.id.mine_change_area, R.id.mine_customer_area, R.id.mine_set_area})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mine_count_area:
                break;
            case R.id.mine_word_area:
                break;
            case R.id.mine_shifts_area:
                break;
            case R.id.mine_change_area:
                break;
            case R.id.mine_customer_area:
                break;
            case R.id.mine_set_area:
                break;
        }
    }
}
