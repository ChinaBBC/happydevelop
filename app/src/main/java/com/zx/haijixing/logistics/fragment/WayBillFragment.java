package com.zx.haijixing.logistics.fragment;

import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.zx.haijixing.R;
import com.zx.haijixing.custom.CustomBottomBar;
import com.zx.haijixing.driver.fragment.CancelFragment;
import com.zx.haijixing.driver.fragment.CompleteFragment;
import com.zx.haijixing.driver.fragment.ReceivedFragment;
import com.zx.haijixing.driver.fragment.SendFragment;
import com.zx.haijixing.driver.fragment.SendingFragment;
import com.zx.haijixing.share.OtherConstants;
import com.zx.haijixing.share.PathConstant;
import com.zx.haijixing.share.base.BaseFragment;
import com.zx.haijixing.share.pub.entry.EventBusEntity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

/**
 *
 *@作者 zx
 *@创建日期 2019/7/16 10:17
 *@描述 运单管理
 */
public class WayBillFragment extends BaseFragment {
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

        EventBus.getDefault().register(this);
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
                .addItem(WaitAllotFragment.class,
                        getHaiString(R.string.wait_allot_order),
                        R.mipmap.lo_a_a,
                        R.mipmap.lo_a_b)
                .addItem(ReceivedFragment.class,
                        getHaiString(R.string.wait_receive),
                        R.mipmap.lo_b_a,
                        R.mipmap.lo_b_b)
                .addItem(SendFragment.class,
                        getHaiString(R.string.wait_send),
                        R.mipmap.lo_c_a,
                        R.mipmap.lo_c_b)
                .addItem(SendingFragment.class,
                        getHaiString(R.string.sending),
                        R.mipmap.lo_d_a,
                        R.mipmap.lo_d_b)
                .addItem(CompleteFragment.class,
                        getHaiString(R.string.complete),
                        R.mipmap.lo_e_a,
                        R.mipmap.lo_e_b)
                .addItem(CancelFragment.class,
                        getHaiString(R.string.has_cancel),
                        R.mipmap.lo_f_a,
                        R.mipmap.lo_f_b)
                .build();
    }

    @OnClick({R.id.order_search, R.id.order_check_all})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.order_search:
                ARouter.getInstance().build(PathConstant.PRINT).navigation();
                break;
            case R.id.order_check_all:
                ARouter.getInstance().build(PathConstant.DRIVER_ORDER_DETAIL).navigation();
                break;

        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventBusEntity entity){
        if (entity.getMsg() == OtherConstants.EVENT_RECEIVE){
            bottomBar.switchTo(0);
        }else if (entity.getMsg() == OtherConstants.EVENT_PRINT){
            bottomBar.switchTo(2);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
