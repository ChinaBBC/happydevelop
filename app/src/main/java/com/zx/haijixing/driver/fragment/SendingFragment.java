package com.zx.haijixing.driver.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zx.haijixing.R;
import com.zx.haijixing.driver.adapter.SendingAdapter;
import com.zx.haijixing.share.base.BaseFragment;
import com.zx.haijixing.util.HaiDialogUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @作者 zx
 * @创建日期 2019/7/5 11:29
 * @描述 配送中
 */
public class SendingFragment extends BaseFragment {
    @BindView(R.id.fragment_sending_data)
    RecyclerView sendingData;
    private SendingAdapter sendingAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_sending;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(View view) {
        sendingData.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        sendingAdapter = new SendingAdapter();
        sendingAdapter.setLoginType(1);
        sendingAdapter.setOnClickListener(this::onViewClicked);
        sendingData.setAdapter(sendingAdapter);

    }

    @OnClick()
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.sending_data_sure_pay:

                break;
            case R.id.sending_data_send_complete:
                HaiDialogUtil.showChangeMoney(getFragmentManager());
                break;
        }
    }

    class SendingViewHolder {
        @BindView(R.id.fragment_sending_selectAll)
        ImageView selectAll;
        @BindView(R.id.fragment_sending_word1)
        TextView word1;
        @BindView(R.id.fragment_sending_total)
        TextView total;
        @BindView(R.id.fragment_sending_sure_pay)
        TextView surePay;
        @BindView(R.id.fragment_sending_complete)
        TextView complete;

        public SendingViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        @OnClick({R.id.fragment_sending_selectAll, R.id.fragment_sending_word1, R.id.fragment_sending_sure_pay, R.id.fragment_sending_complete})
        public void onViewClicked(View view) {
            switch (view.getId()) {
                case R.id.fragment_sending_selectAll:
                case R.id.fragment_sending_word1:
                    break;
                case R.id.fragment_sending_sure_pay:
                    break;
                case R.id.fragment_sending_complete:
                    break;
            }
        }
    }
}
