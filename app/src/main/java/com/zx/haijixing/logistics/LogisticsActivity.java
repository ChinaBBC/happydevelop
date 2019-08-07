package com.zx.haijixing.logistics;

import android.view.KeyEvent;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zx.haijixing.R;
import com.zx.haijixing.custom.CustomBottomBar;
import com.zx.haijixing.driver.fragment.IndexFragment;
import com.zx.haijixing.logistics.fragment.CompanyCenterFragment;
import com.zx.haijixing.logistics.fragment.LogisticsCenterFragment;
import com.zx.haijixing.logistics.fragment.WayBillFragment;
import com.zx.haijixing.share.OtherConstants;
import com.zx.haijixing.share.PathConstant;
import com.zx.haijixing.share.base.BaseActivity;
import com.zx.haijixing.share.pub.entry.EventBusEntity;
import com.zx.haijixing.util.HaiTool;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import zx.com.skytool.ZxStatusBarCompat;
import zx.com.skytool.ZxToastUtil;

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
        EventBus.getDefault().register(this);
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
                .addItem(IndexFragment.class,
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
        if (entity.getMsg()==OtherConstants.EVENT_RECEIVE || entity.getMsg() == OtherConstants.EVENT_PRINT)
            bottomMenu.switchTo(1);
        if (entity.getMsg()==OtherConstants.EVENT_LOGISTICS)
            bottomMenu.switchTo(2);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            if (HaiTool.isFastClick()){
                finish();
            }else {
                ZxToastUtil.centerToast("再按一次退出星配送");
            }
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
}
