package com.zx.haijixing.manager;

import android.view.KeyEvent;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.zx.haijixing.R;
import com.zx.haijixing.custom.CustomBottomBar;
import com.zx.haijixing.driver.fragment.IndexFragment;
import com.zx.haijixing.logistics.fragment.LogisticMoveFragment;
import com.zx.haijixing.logistics.fragment.WayBillFragment;
import com.zx.haijixing.manager.fragment.ManagerCenterFragment;
import com.zx.haijixing.share.OtherConstants;
import com.zx.haijixing.share.PathConstant;
import com.zx.haijixing.share.base.BaseActivity;
import com.zx.haijixing.share.pub.entry.EventBusEntity;
import com.zx.haijixing.util.HaiTool;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import zx.com.skytool.ZxLogUtil;
import zx.com.skytool.ZxStatusBarCompat;
import zx.com.skytool.ZxStringUtil;
import zx.com.skytool.ZxToastUtil;

/**
 *
 *@作者 zx
 *@创建日期 2019/7/17 17:15
 *@描述 管理员端
 */
@Route(path = PathConstant.MANAGER)
public class ManagerActivity extends BaseActivity implements AMapLocationListener {
    @BindView(R.id.manager_Bb_bottomMenu)
    CustomBottomBar bottomMenu;

    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;
    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
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
                .addItem(IndexFragment.class,
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
        initLocateOnce();
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
        return R.layout.activity_manager;
    }

    @Override
    public void setStatusBar() {
        ZxStatusBarCompat.translucentStatusBar(this);
    }

    //初始化定位
    private void initLocateOnce() {
        mlocationClient = new AMapLocationClient(this);
        //设置定位回调监听
        mlocationClient.setLocationListener(this);
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位场景为出行
        mLocationOption.setLocationPurpose(AMapLocationClientOption.AMapLocationPurpose.Transport);
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置是否只定位一次,默认为false
        mLocationOption.setOnceLocation(true);
        //设置是否强制刷新WIFI，默认为强制刷新
        mLocationOption.setWifiActiveScan(true);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);
        //设置定位间隔,单位毫秒,默认为2000ms
        //mLocationOption.setInterval(10*60*1000);
        //给定位客户端对象设置定位参数
        mlocationClient.setLocationOption(mLocationOption);
        //启动定位
        mlocationClient.startLocation();
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null){
            if (aMapLocation.getErrorCode() == 0){
                String city = aMapLocation.getCity();
                EventBusEntity eventBusEntity = new EventBusEntity(OtherConstants.WEATHER_ENTRY);
                eventBusEntity.setMessage(city);
                EventBus.getDefault().post(eventBusEntity);
            }else {
                ZxLogUtil.logError(aMapLocation.getErrorInfo()+"<<<<..>>>>"+aMapLocation.getErrorCode());
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        if (mlocationClient != null){
            mlocationClient.startLocation();
            mlocationClient.onDestroy();
        }
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
