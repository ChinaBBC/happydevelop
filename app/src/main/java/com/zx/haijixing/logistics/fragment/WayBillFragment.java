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
import com.zx.haijixing.logistics.contract.BotContract;
import com.zx.haijixing.logistics.presenter.BotImp;
import com.zx.haijixing.share.OtherConstants;
import com.zx.haijixing.share.PathConstant;
import com.zx.haijixing.share.base.BaseFragment;
import com.zx.haijixing.share.pub.entry.EventBusEntity;
import com.zx.haijixing.util.HaiTool;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import zx.com.skytool.ZxLogUtil;
import zx.com.skytool.ZxSharePreferenceUtil;
import zx.com.skytool.ZxStringUtil;
import zx.com.skytool.ZxToastUtil;

/**
 *
 *@作者 zx
 *@创建日期 2019/7/16 10:17
 *@描述 运单管理
 */
public class WayBillFragment extends BaseFragment<BotImp> implements BotContract.BotView {
    @BindView(R.id.order_input)
    EditText orderInput;
    @BindView(R.id.order_search)
    TextView orderSearch;
    @BindView(R.id.order_total_orders)
    TextView orderTotalOrders;
    @BindView(R.id.order_check_all)
    TextView orderCheckAll;
    @BindView(R.id.order_tv_receiveBot)
    TextView receiveBot;
    @BindView(R.id.order_tv_sendBot)
    TextView sendBot;
    @BindView(R.id.order_tv_sendingBot)
    TextView sendingBot;
    @BindView(R.id.order_tv_allotBot)
    TextView allotBot;

    @BindView(R.id.order_rp_lay)
    FrameLayout replace;
    @BindView(R.id.order_one)
    LinearLayout title;
    @BindView(R.id.order_bottom_bar)
    CustomBottomBar bottomBar;

    private Map<String,String> params = new HashMap<>();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_order;
    }

    @Override
    protected void initData() {
        mPresenter = new BotImp();
    }

    @Override
    protected void initView(View view) {
        setTitleTopMargin(title,0);

        ZxSharePreferenceUtil instance = ZxSharePreferenceUtil.getInstance();
        instance.init(getContext());
        String token = (String) instance.getParam("token", "null");

        params.put("token",token);
        params.put("timestamp",System.currentTimeMillis()+"");
        params.put("sign","");
        params.put("sign",HaiTool.sign(params));
        mPresenter.botMethod(params);

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
                String trim = orderInput.getText().toString().trim();
                if (ZxStringUtil.isEmpty(trim)){
                    ZxToastUtil.centerToast("请输入查询内容");
                }else {
                    ARouter.getInstance().build(PathConstant.DRIVER_SEARCH)
                            .withString("content",trim)
                            .navigation();
                }
                break;
            case R.id.order_check_all:
                ARouter.getInstance().build(PathConstant.DRIVER_ORDER).navigation();
                break;

        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventBusEntity entity){
        switch (entity.getMsg()){
            case OtherConstants.EVENT_RECEIVE:
                bottomBar.switchTo(0);
                break;
            case OtherConstants.EVENT_PRINT:
                bottomBar.switchTo(2);
                break;
            case OtherConstants.RED_BOT:
                params.put("timestamp",System.currentTimeMillis()+"");
                params.put("sign","");
                params.put("sign",HaiTool.sign(params));
                mPresenter.botMethod(params);
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void botSuccess(int allot, int receive, int send, int sending) {
        allotBot.setVisibility(allot == 0 ?View.INVISIBLE:View.VISIBLE);
        receiveBot.setVisibility(receive == 0 ?View.INVISIBLE:View.VISIBLE);
        sendBot.setVisibility(send == 0 ?View.INVISIBLE:View.VISIBLE);
        sendingBot.setVisibility(sending == 0 ?View.INVISIBLE:View.VISIBLE);

        allotBot.setText(allot+"");
        receiveBot.setText(receive+"");
        sendBot.setText(send+"");
        sendingBot.setText(sending+"");
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden && !HaiTool.isFastClick()){
            params.put("timestamp",System.currentTimeMillis()+"");
            params.put("sign","");
            params.put("sign",HaiTool.sign(params));
            mPresenter.botMethod(params);
        }
    }
}
