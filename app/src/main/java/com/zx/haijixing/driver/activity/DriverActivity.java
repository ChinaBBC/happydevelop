package com.zx.haijixing.driver.activity;


import android.view.KeyEvent;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.zx.haijixing.R;
import com.zx.haijixing.custom.CustomBottomBar;
import com.zx.haijixing.driver.contract.DriverContract;
import com.zx.haijixing.driver.fragment.IndexFragment;
import com.zx.haijixing.driver.fragment.MineFragment;
import com.zx.haijixing.driver.fragment.NewsFragment;
import com.zx.haijixing.driver.fragment.OrderFragment;
import com.zx.haijixing.driver.presenter.DriverImp;
import com.zx.haijixing.share.OtherConstants;
import com.zx.haijixing.share.PathConstant;
import com.zx.haijixing.share.base.BaseActivity;
import com.zx.haijixing.share.pub.entry.EventBusEntity;
import com.zx.haijixing.util.HaiTool;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import zx.com.skytool.ZxLogUtil;
import zx.com.skytool.ZxSharePreferenceUtil;
import zx.com.skytool.ZxStatusBarCompat;
import zx.com.skytool.ZxStringUtil;
import zx.com.skytool.ZxToastUtil;

@Route(path = PathConstant.DRIVER_MAIN)
public class DriverActivity extends BaseActivity<DriverImp> implements DriverContract.DriverView,AMapLocationListener {

    @BindView(R.id.driver_Bb_bottomMenu)
    CustomBottomBar driverBbBottomMenu;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;
    private Map<String,String> params = new HashMap<>();

    private String token = null;

    @Override
    protected void initView() {
        ZxSharePreferenceUtil instance = ZxSharePreferenceUtil.getInstance();
        instance.init(this);
        token = (String) instance.getParam("token", "token");
        params.put("token",token);

        EventBus.getDefault().register(this);
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
                        getHaiString(R.string.index),
                        R.mipmap.index_index_before,
                        R.mipmap.index_index_after)
                .addItem(OrderFragment.class,
                        getHaiString(R.string.order_center),
                        R.mipmap.index_order_before,
                        R.mipmap.index_order_after)
                .addItem(NewsFragment.class,
                        getHaiString(R.string.news),
                        R.mipmap.index_news_before,
                        R.mipmap.index_news_after)
                .addItem(MineFragment.class,
                        getHaiString(R.string.mine_center),
                        R.mipmap.index_mine_before,
                        R.mipmap.index_mine_after)
                .build();

        initLocateOnce();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventBusEntity entity){
        if (entity.getMsg()==OtherConstants.EVENT_RECEIVE || entity.getMsg() == OtherConstants.EVENT_PRINT)
            driverBbBottomMenu.switchTo(1);
    }
    @Override
    protected void initInjector() {
        mPresenter = new DriverImp();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_driver;
    }

    @Override
    public void setStatusBar() {
        ZxStatusBarCompat.translucentStatusBar(this);
    }

    @Override
    public void driverSuccess(String msg) {

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
        mLocationOption.setOnceLocation(false);
        //设置是否强制刷新WIFI，默认为强制刷新
        mLocationOption.setWifiActiveScan(true);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(10*60*1000);
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
                //ZxLogUtil.logError("<getLatitude>>>"+aMapLocation.getLatitude()+"<getLatitude>>>"+aMapLocation.getLatitude()+"<token>>>"+token+"<token>>>"+aMapLocation.getCity());
                if (!ZxStringUtil.isEmpty(token)){
                    params.put("lng",aMapLocation.getLongitude()+"");
                    params.put("lat",aMapLocation.getLatitude()+"");
                    params.put("timestamp",System.currentTimeMillis()+"");
                    params.put("sign","");
                    params.put("sign",HaiTool.sign(params));
                    mPresenter.driverMethod(params);
                }
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
