package com.zx.haijixing.driver.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zx.haijixing.R;
import com.zx.haijixing.driver.adapter.SendAdapter;
import com.zx.haijixing.share.base.BaseFragment;
import com.zx.haijixing.util.HaiDialogUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @作者 zx
 * @创建日期 2019/7/5 11:29
 * @描述 待出发
 */
public class SendFragment extends BaseFragment {
    @BindView(R.id.fragment_send_data)
    RecyclerView sendData;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_send;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(View view) {
        sendData.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        sendData.setAdapter(new SendAdapter());
    }


    @OnClick()
    public void onViewClicked(View view) {
        switch (view.getId()) {

        }
    }

    class SendViewHolder {
        @BindView(R.id.fragment_receive_selectAll)
        ImageView selectAll;
        @BindView(R.id.fragment_receive_word1)
        TextView word1;
        @BindView(R.id.fragment_receive_total)
        TextView total;
        @BindView(R.id.fragment_receive_receive)
        TextView receive;

        public SendViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        @OnClick({R.id.fragment_receive_selectAll, R.id.fragment_receive_word1, R.id.fragment_receive_receive})
        public void onViewClicked(View view) {
            switch (view.getId()) {
                case R.id.fragment_receive_selectAll:
                case R.id.fragment_receive_word1:
                    break;
                case R.id.fragment_receive_receive:
                    HaiDialogUtil.showReceive(getFragmentManager(), this::onViewClicked, total.getText().toString());
                    break;
                case R.id.receive_data_sure_receive:
                    break;
            }
        }
    }
    /*class SendViewHolder{
        @BindView(R.id.fragment_send_selectAll)
        ImageView selectAll;
        @BindView(R.id.fragment_send_word1)
        TextView word1;
        @BindView(R.id.fragment_send_total)
        TextView total;
        @BindView(R.id.fragment_send_send)
        TextView send;
        public SendViewHolder(View view) {
            ButterKnife.bind(this,view);
        }
        @OnClick({R.id.fragment_send_selectAll, R.id.fragment_send_word1, R.id.fragment_send_send})
        public void onViewClicked(View view) {
            switch (view.getId()) {
                case R.id.fragment_send_selectAll:
                case R.id.fragment_send_word1:
                    break;
                case R.id.fragment_send_send:
                    HaiDialogUtil.showLocate(getFragmentManager(),this::onViewClicked);
                    break;
            }
        }
    }*/
}
