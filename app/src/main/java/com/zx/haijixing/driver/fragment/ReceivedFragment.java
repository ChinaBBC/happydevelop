package com.zx.haijixing.driver.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zx.haijixing.R;
import com.zx.haijixing.driver.adapter.ReceiveAdapter;
import com.zx.haijixing.share.base.BaseFragment;
import com.zx.haijixing.util.HaiDialogUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @作者 zx
 * @创建日期 2019/7/5 11:28
 * @描述 待接单
 */
public class ReceivedFragment extends BaseFragment {
    @BindView(R.id.fragment_receive_selectAll)
    ImageView selectAll;
    @BindView(R.id.fragment_receive_word1)
    TextView word1;
    @BindView(R.id.fragment_receive_total)
    TextView total;
    @BindView(R.id.fragment_receive_receive)
    TextView receive;
    @BindView(R.id.fragment_receive_data)
    RecyclerView rvData;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_receive;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(View view) {
        rvData.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        rvData.setAdapter(new ReceiveAdapter());
    }

    @OnClick({R.id.fragment_receive_selectAll, R.id.fragment_receive_word1,R.id.fragment_receive_receive})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fragment_receive_selectAll:
            case R.id.fragment_receive_word1:
                break;
            case R.id.fragment_receive_receive:
                HaiDialogUtil.showReceive(getFragmentManager(),this::onViewClicked,total.getText().toString());
                break;
        }
    }
}
