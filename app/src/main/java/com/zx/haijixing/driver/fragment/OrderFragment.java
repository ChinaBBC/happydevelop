package com.zx.haijixing.driver.fragment;

import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.zx.haijixing.R;
import com.zx.haijixing.custom.CustomBottomBar;
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

    @BindView(R.id.order_rp_lay)
    FrameLayout replace;
    @BindView(R.id.order_one)
    LinearLayout title;
    @BindView(R.id.order_bottom_bar)
    CustomBottomBar bottomBar;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_order;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(View view) {
        setTitleTopMargin(title,0);

        int titleSize = getResources().getDimensionPixelSize(R.dimen.sp_11);
        int titleMar = getResources().getDimensionPixelSize(R.dimen.dp_4);
        int iconSize = getResources().getDimensionPixelSize(R.dimen.dp_20);
        //初始化底部菜单栏
        bottomBar.setContainer(R.id.order_rp_lay)
                .setTitleBeforeAndAfterColor("#666666", "#30703f")
                .setTitleSize(titleSize)
                .setTitleIconMargin(titleMar)
                .setIconHeight(iconSize)
                .setIconWidth(iconSize)
                .addItem(ReceivedFragment.class,
                        getHaiString(R.string.wait_receive),
                        R.mipmap.wait_receive,
                        R.mipmap.wait_receive2)
                .addItem(SendFragment.class,
                        getHaiString(R.string.wait_send),
                        R.mipmap.wait_send,
                        R.mipmap.wait_send2)
                .addItem(SendingFragment.class,
                        getHaiString(R.string.sending),
                        R.mipmap.sending,
                        R.mipmap.sending2)
                .addItem(CompleteFragment.class,
                        getHaiString(R.string.complete),
                        R.mipmap.complete,
                        R.mipmap.complete2)
                .addItem(CancelFragment.class,
                        getHaiString(R.string.has_cancel),
                        R.mipmap.has_cancel,
                        R.mipmap.has_cancel2)
                .build();
    }

    @OnClick({R.id.order_search, R.id.order_check_all})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.order_search:
                ARouter.getInstance().build(RoutePathConstant.PRINT).navigation();
                break;
            case R.id.order_check_all:
                ARouter.getInstance().build(RoutePathConstant.DRIVER_ORDER).navigation();
                break;

        }
    }
}
