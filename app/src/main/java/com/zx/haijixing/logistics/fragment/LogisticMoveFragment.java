package com.zx.haijixing.logistics.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zx.haijixing.R;
import com.zx.haijixing.logistics.adapter.LogisticsMoveAdapter;
import com.zx.haijixing.share.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @作者 zx
 * @创建日期 2019/7/16 14:23
 * @描述 物流动态
 */
public class LogisticMoveFragment extends BaseFragment {
    @BindView(R.id.logistics_move_img1)
    ImageView logisticsMoveImg1;
    @BindView(R.id.logistics_move_num1)
    TextView num1;
    @BindView(R.id.logistics_move_word1)
    TextView word1;
    @BindView(R.id.logistics_move_num2)
    TextView num2;
    @BindView(R.id.logistics_move_word2)
    TextView word2;
    @BindView(R.id.logistics_move_num3)
    TextView num3;
    @BindView(R.id.logistics_move_word3)
    TextView word3;
    @BindView(R.id.logistics_move_num4)
    TextView num4;
    @BindView(R.id.logistics_move_word4)
    TextView word4;
    @BindView(R.id.logistics_move_title)
    TextView title;
    @BindView(R.id.logistics_move_data)
    RecyclerView logisticsMoveData;

    private int loginType = 1;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_logistics_move;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(View view) {
        if (loginType == 1){
            title.setVisibility(View.VISIBLE);
            setTitleTopMargin(title,0);
        }
        logisticsMoveData.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        logisticsMoveData.setAdapter(new LogisticsMoveAdapter());
    }

    @OnClick({R.id.logistics_move_num1, R.id.logistics_move_word1, R.id.logistics_move_num2, R.id.logistics_move_word2, R.id.logistics_move_num3, R.id.logistics_move_word3, R.id.logistics_move_num4, R.id.logistics_move_word4})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.logistics_move_num1:
            case R.id.logistics_move_word1:

                break;
            case R.id.logistics_move_num2:
            case R.id.logistics_move_word2:

                break;
            case R.id.logistics_move_num3:
            case R.id.logistics_move_word3:

                break;
            case R.id.logistics_move_num4:
            case R.id.logistics_move_word4:

                break;
        }
    }
}
