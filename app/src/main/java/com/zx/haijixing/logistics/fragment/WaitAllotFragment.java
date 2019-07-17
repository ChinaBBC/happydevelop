package com.zx.haijixing.logistics.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.zx.haijixing.R;
import com.zx.haijixing.driver.adapter.ReceiveAdapter;
import com.zx.haijixing.logistics.adapter.AllotAdapter;
import com.zx.haijixing.share.PathConstant;
import com.zx.haijixing.share.base.BaseFragment;
import com.zx.haijixing.util.HaiDialogUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 *
 *@作者 zx
 *@创建日期 2019/7/16 10:59
 *@描述 待派单
 */
public class WaitAllotFragment extends BaseFragment {
    @BindView(R.id.fragment_receive_data)
    RecyclerView rvData;
    @BindView(R.id.fragment_receive_lay1)
    ViewStub viewStub;
    private AllotViewHolder allotViewHolder;
    private AllotAdapter allotAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_receive;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(View view) {

        View inflate = viewStub.inflate();
        allotViewHolder = new AllotViewHolder(inflate);
        allotViewHolder.receive.setText(getHaiString(R.string.sure_allot));

        rvData.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        allotAdapter = new AllotAdapter();
        allotAdapter.setOnClickListener(this::onViewClicked);
        rvData.setAdapter(allotAdapter);
    }

    @OnClick()
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.allot_data_sure_allot:
                ARouter.getInstance().build(PathConstant.ALLOT).navigation();
                break;
            case R.id.allot_data_change_order:
                HaiDialogUtil.showReceive(getFragmentManager(),this::onViewClicked,"");
                break;
        }
    }

    class AllotViewHolder{
        @BindView(R.id.fragment_receive_selectAll)
        ImageView selectAll;
        @BindView(R.id.fragment_receive_word1)
        TextView word1;
        @BindView(R.id.fragment_receive_total)
        TextView total;
        @BindView(R.id.fragment_receive_receive)
        TextView receive;
        public AllotViewHolder(View view) {
            ButterKnife.bind(this,view);
        }

        @OnClick({R.id.fragment_receive_selectAll,R.id.fragment_receive_word1,R.id.fragment_receive_receive})
        public void onViewClicked(View view) {
            switch (view.getId()) {
                case R.id.fragment_receive_selectAll:
                case R.id.fragment_receive_word1:
                    break;
                case R.id.fragment_receive_receive:
                    ARouter.getInstance().build(PathConstant.ALLOT).navigation();
                    break;
            }
        }
    }
}
