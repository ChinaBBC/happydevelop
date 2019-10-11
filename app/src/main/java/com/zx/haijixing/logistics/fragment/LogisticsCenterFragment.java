package com.zx.haijixing.logistics.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zx.haijixing.R;
import com.zx.haijixing.custom.CustomBottomBar;
import com.zx.haijixing.driver.fragment.CancelFragment;
import com.zx.haijixing.driver.fragment.CompleteFragment;
import com.zx.haijixing.driver.fragment.ReceivedFragment;
import com.zx.haijixing.driver.fragment.SendFragment;
import com.zx.haijixing.driver.fragment.SendingFragment;
import com.zx.haijixing.share.OtherConstants;
import com.zx.haijixing.share.base.BaseFragment;
import com.zx.haijixing.share.pub.entry.EventBusEntity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import zx.com.skytool.ZxSharePreferenceUtil;

/**
 * @作者 zx
 * @创建日期 2019/7/16 10:18
 * @描述 物流中心
 */
public class LogisticsCenterFragment extends BaseFragment {
    @BindView(R.id.logistics_center_title)
    View title;
    @BindView(R.id.logistics_center_bottom)
    CustomBottomBar bottom;
    private String loginType;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_logistics_center;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(View view) {
        ZxSharePreferenceUtil instance = ZxSharePreferenceUtil.getInstance();
        instance.init(getContext());
        loginType = (String) instance.getParam("login_type", "4");
        //setTitleTopMargin(title,0);
        EventBus.getDefault().register(this);
        int titleSize = getResources().getDimensionPixelSize(R.dimen.sp_14);
        //初始化底部菜单栏
        bottom.setContainer(R.id.logistics_center_replace)
                .setTitleBeforeAndAfterColor("#666666", "#30703f")
                .setTitleSize(titleSize)
                .setTitleIconMargin(0)
                .setIconHeight(0)
                .setIconWidth(0)
                .addItem(LogisticMoveFragment.class,
                        getHaiString(R.string.logistics_move),
                        R.mipmap.logistics_center_a,
                        R.mipmap.logistics_center_b)
                .addItem(LinesManageFragment.class,
                        getHaiString(R.string.lines_manage),
                        R.mipmap.lines_manage_a,
                        R.mipmap.lines_manage_b)
                .build();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventBusEntity entity){
        if (entity.getMsg()==OtherConstants.EVENT_LOGISTICS && loginType.equals(OtherConstants.LOGIN_LOGISTICS))
            bottom.switchTo(1);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
