package com.zx.haijixing.manager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zx.haijixing.R;
import com.zx.haijixing.custom.CustomBottomBar;
import com.zx.haijixing.logistics.fragment.CompanyCenterFragment;
import com.zx.haijixing.logistics.fragment.LogisticMoveFragment;
import com.zx.haijixing.logistics.fragment.LogisticsCenterFragment;
import com.zx.haijixing.logistics.fragment.LogisticsIndexFragment;
import com.zx.haijixing.logistics.fragment.WayBillFragment;
import com.zx.haijixing.manager.fragment.ManagerCenterFragment;
import com.zx.haijixing.share.PathConstant;
import com.zx.haijixing.share.base.BaseActivity;

import butterknife.BindView;
import zx.com.skytool.ZxStatusBarCompat;

/**
 *
 *@作者 zx
 *@创建日期 2019/7/17 17:15
 *@描述 管理员端
 */
@Route(path = PathConstant.MANAGER)
public class ManagerActivity extends BaseActivity {
    @BindView(R.id.manager_Bb_bottomMenu)
    CustomBottomBar bottomMenu;

    @Override
    protected void initView() {
        int titleSize = getResources().getDimensionPixelSize(R.dimen.sp_11);
        int titleMar = getResources().getDimensionPixelSize(R.dimen.dp_4);
        int iconSize = getResources().getDimensionPixelSize(R.dimen.dp_20);
        //初始化底部菜单栏
        bottomMenu.setContainer(R.id.manager_fl_fragment)
                .setTitleBeforeAndAfterColor("#999999", "#30703f")
                .setTitleSize(titleSize)
                .setTitleIconMargin(titleMar)
                .setIconHeight(iconSize)
                .setIconWidth(iconSize)
                .addItem(LogisticsIndexFragment.class,
                        getHaiString(R.string.index),
                        R.mipmap.index_a_a,
                        R.mipmap.index_a_b)
                .addItem(WayBillFragment.class,
                        getHaiString(R.string.order_center),
                        R.mipmap.index_order_before,
                        R.mipmap.index_order_after)
                .addItem(LogisticMoveFragment.class,
                        getHaiString(R.string.logistics_center),
                        R.mipmap.index_index_before,
                        R.mipmap.index_index_after)
                .addItem(ManagerCenterFragment.class,
                        getHaiString(R.string.mine_center),
                        R.mipmap.index_mine_before,
                        R.mipmap.index_mine_after)
                .build();
    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_manager;
    }

    @Override
    public void setStatusBar() {
        ZxStatusBarCompat.translucentStatusBar(this);
    }

}
