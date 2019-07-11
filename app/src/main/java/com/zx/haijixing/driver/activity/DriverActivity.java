package com.zx.haijixing.driver.activity;


import com.alibaba.android.arouter.facade.annotation.Route;
import com.zx.haijixing.R;
import com.zx.haijixing.custom.CustomBottomBar;
import com.zx.haijixing.driver.fragment.IndexFragment;
import com.zx.haijixing.driver.fragment.MineFragment;
import com.zx.haijixing.driver.fragment.NewsFragment;
import com.zx.haijixing.driver.fragment.OrderFragment;
import com.zx.haijixing.share.RoutePathConstant;
import com.zx.haijixing.share.base.BaseActivity;

import butterknife.BindView;
import zx.com.skytool.ZxStatusBarCompat;

@Route(path = RoutePathConstant.DRIVER_MAIN)
public class DriverActivity extends BaseActivity {

    @BindView(R.id.driver_Bb_bottomMenu)
    CustomBottomBar driverBbBottomMenu;

    @Override
    protected void initView() {
        int titleSize = getResources().getDimensionPixelSize(R.dimen.sp_11);
        int titleMar = getResources().getDimensionPixelSize(R.dimen.dp_4);
        int iconSize = getResources().getDimensionPixelSize(R.dimen.dp_20);
        //初始化底部菜单栏
        driverBbBottomMenu.setContainer(R.id.driver_fl_fragment)
                .setTitleBeforeAndAfterColor("#999999", "#30703f")
                .setTitleSize(titleSize)
                .setTitleIconMargin(titleMar)
                .setIconHeight(iconSize)
                .setIconWidth(iconSize)
                .addItem(IndexFragment.class,
                        "首页",
                        R.mipmap.index_index_before,
                        R.mipmap.index_index_after)
                .addItem(OrderFragment.class,
                        "订单中心",
                        R.mipmap.index_order_before,
                        R.mipmap.index_order_after)
                .addItem(NewsFragment.class,
                        "新闻资讯",
                        R.mipmap.index_news_before,
                        R.mipmap.index_news_after)
                .addItem(MineFragment.class,
                        "个人中心",
                        R.mipmap.index_mine_before,
                        R.mipmap.index_mine_after)
                .build();
    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_driver;
    }

    @Override
    public void setStatusBar() {
        ZxStatusBarCompat.translucentStatusBar(this);
    }
}
