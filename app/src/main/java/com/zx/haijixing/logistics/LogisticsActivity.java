package com.zx.haijixing.logistics;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zx.haijixing.R;
import com.zx.haijixing.custom.CustomBottomBar;
import com.zx.haijixing.logistics.fragment.CompanyCenterFragment;
import com.zx.haijixing.logistics.fragment.LogisticsCenterFragment;
import com.zx.haijixing.logistics.fragment.LogisticsIndexFragment;
import com.zx.haijixing.logistics.fragment.WayBillFragment;
import com.zx.haijixing.share.PathConstant;
import com.zx.haijixing.share.base.BaseActivity;
import com.zx.haijixing.share.pub.entry.EventBusEntity;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import zx.com.skytool.ZxStatusBarCompat;

/**
 *
 *@作者 zx
 *@创建日期 2019/7/16 10:00
 *@描述 物流端
 */
@Route(path = PathConstant.LOGISTICS)
public class LogisticsActivity extends BaseActivity {

    @BindView(R.id.logistics_Bb_bottomMenu)
    CustomBottomBar bottomMenu;

    @Override
    protected void initView() {
        int titleSize = getResources().getDimensionPixelSize(R.dimen.sp_11);
        int titleMar = getResources().getDimensionPixelSize(R.dimen.dp_4);
        int iconSize = getResources().getDimensionPixelSize(R.dimen.dp_20);
        //初始化底部菜单栏
        bottomMenu.setContainer(R.id.logistics_fl_fragment)
                .setTitleBeforeAndAfterColor("#999999", "#30703f")
                .setTitleSize(titleSize)
                .setTitleIconMargin(titleMar)
                .setIconHeight(iconSize)
                .setIconWidth(iconSize)
                .addItem(LogisticsIndexFragment.class,
                        getHaiString(R.string.index),
                        R.mipmap.index_index_before,
                        R.mipmap.index_index_after)
                .addItem(WayBillFragment.class,
                        getHaiString(R.string.order_center),
                        R.mipmap.index_order_before,
                        R.mipmap.index_order_after)
                .addItem(LogisticsCenterFragment.class,
                        getHaiString(R.string.logistics_center),
                        R.mipmap.logistics_center_a,
                        R.mipmap.logistics_center_b)
                .addItem(CompanyCenterFragment.class,
                        getHaiString(R.string.company_center),
                        R.mipmap.company_center_a,
                        R.mipmap.company_center_b)
                .build();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventBusEntity entity){
        bottomMenu.switchTo(1);
    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_logistics;
    }

    @Override
    public void setStatusBar() {
        ZxStatusBarCompat.translucentStatusBar(this);
    }
}
