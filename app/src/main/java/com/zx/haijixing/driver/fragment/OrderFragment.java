package com.zx.haijixing.driver.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.zx.haijixing.R;
import com.zx.haijixing.driver.adapter.OrderAdapter;
import com.zx.haijixing.share.RoutePathConstant;
import com.zx.haijixing.share.base.BaseFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 订单中心
 *
 * @author ZX
 * @time 2019/7/3 21:47
 **/

public class OrderFragment extends BaseFragment {

    @BindView(R.id.order_input)
    EditText orderInput;
    @BindView(R.id.order_search)
    TextView orderSearch;
    @BindView(R.id.order_total_orders)
    TextView orderTotalOrders;
    @BindView(R.id.order_check_all)
    TextView orderCheckAll;
    @BindView(R.id.order_wait_receive)
    ImageView orderWaitReceive;
    @BindView(R.id.order_word1)
    TextView orderWord1;
    @BindView(R.id.order_wait_send)
    ImageView orderWaitSend;
    @BindView(R.id.order_word2)
    TextView orderWord2;
    @BindView(R.id.order_is_sending)
    ImageView orderIsSending;
    @BindView(R.id.order_word3)
    TextView orderWord3;
    @BindView(R.id.order_has_completed)
    ImageView orderHasCompleted;
    @BindView(R.id.order_word4)
    TextView orderWord4;
    @BindView(R.id.order_has_cancel)
    ImageView orderHasCancel;
    @BindView(R.id.order_word5)
    TextView orderWord5;
    @BindView(R.id.order_rv_data)
    RecyclerView orderRvData;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_order;
    }

    @Override
    protected void initInjector() {
        ARouter.getInstance().inject(this);
    }

    @Override
    protected void initView(View view) {
        orderRvData.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        orderRvData.setAdapter(new OrderAdapter());
    }

    @OnClick({R.id.order_search, R.id.order_check_all, R.id.order_wait_receive, R.id.order_word1, R.id.order_wait_send, R.id.order_word2, R.id.order_is_sending, R.id.order_word3, R.id.order_has_completed, R.id.order_word4, R.id.order_has_cancel, R.id.order_word5})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.order_search:

                break;
            case R.id.order_check_all:
                ARouter.getInstance().build(RoutePathConstant.DRIVER_ORDER_DETAIL).navigation();
                break;
            case R.id.order_wait_receive:
            case R.id.order_word1:

                break;
            case R.id.order_wait_send:
            case R.id.order_word2:

                break;
            case R.id.order_is_sending:
            case R.id.order_word3:

                break;
            case R.id.order_has_completed:
            case R.id.order_word4:

                break;
            case R.id.order_has_cancel:
            case R.id.order_word5:

                break;
        }
    }
}
