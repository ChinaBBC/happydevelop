package com.zx.haijixing.logistics.activity;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.AMapNaviListener;
import com.amap.api.navi.model.AMapCalcRouteResult;
import com.amap.api.navi.model.AMapCarInfo;
import com.amap.api.navi.model.AMapLaneInfo;
import com.amap.api.navi.model.AMapModelCross;
import com.amap.api.navi.model.AMapNaviCameraInfo;
import com.amap.api.navi.model.AMapNaviCross;
import com.amap.api.navi.model.AMapNaviInfo;
import com.amap.api.navi.model.AMapNaviLocation;
import com.amap.api.navi.model.AMapNaviPath;
import com.amap.api.navi.model.AMapNaviRouteNotifyData;
import com.amap.api.navi.model.AMapNaviTrafficFacilityInfo;
import com.amap.api.navi.model.AMapServiceAreaInfo;
import com.amap.api.navi.model.AimLessModeCongestionInfo;
import com.amap.api.navi.model.AimLessModeStat;
import com.amap.api.navi.model.NaviInfo;
import com.amap.api.navi.model.NaviLatLng;
import com.amap.api.navi.model.RouteOverlayOptions;
import com.amap.api.navi.view.RouteOverLay;
import com.autonavi.tbt.TrafficFacilityInfo;
import com.zx.haijixing.R;
import com.zx.haijixing.logistics.contract.CheckLogisticsContract;
import com.zx.haijixing.logistics.presenter.CheckLogisticsImp;
import com.zx.haijixing.share.PathConstant;
import com.zx.haijixing.share.base.BaseActivity;
import com.zx.haijixing.util.HaiTool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import zx.com.skytool.ZxLogUtil;
import zx.com.skytool.ZxSharePreferenceUtil;
import zx.com.skytool.ZxStatusBarCompat;
import zx.com.skytool.ZxStringUtil;
import zx.com.skytool.ZxToastUtil;

/**
 *
 *@作者 zx
 *@创建日期 2019/7/17 16:58
 *@描述 查看物流
 */
@Route(path = PathConstant.CHECK_LOGISTICS)
public class CheckLogisticsActivity extends BaseActivity<CheckLogisticsImp> implements AMapNaviListener,CheckLogisticsContract.CheckLogisticView {
    @BindView(R.id.map_map)
    MapView mRouteMapView;
    @BindView(R.id.map_head)
    TextView head;
    @BindView(R.id.map_title)
    ConstraintLayout title;

    @Autowired(name = "waybillNo")
    String waybillNo;

    //起点
    private List<NaviLatLng> startList = new ArrayList<NaviLatLng>();
    ///终点
    private List<NaviLatLng> endList = new ArrayList<NaviLatLng>();
    private List<NaviLatLng> wayList = new ArrayList<NaviLatLng>();
    private AMap mAmap = null;//地图控制器
    private AMapNavi mAMapNavi;


    @Override
    protected void initView() {
        setTitleTopMargin(title);
    }

    @Override
    protected void initInjector() {
        mPresenter = new CheckLogisticsImp();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_check_logistics;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView(savedInstanceState);
    }

    @Override
    public void setStatusBar() {
        ZxStatusBarCompat.translucentStatusBar(this);
    }

    protected void initView(Bundle savedInstanceState) {
        mRouteMapView.onCreate(savedInstanceState);
        if (mAmap == null)
            mAmap = mRouteMapView.getMap();
        mAmap.getUiSettings().setZoomControlsEnabled(false);
        mAmap.setMapType(AMap.MAP_TYPE_NAVI);
        try {
            mAMapNavi = AMapNavi.getInstance(getApplicationContext());
            if (mAMapNavi != null) {
                mAMapNavi.addAMapNaviListener(this);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
        mRouteMapView.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        mRouteMapView.onPause();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mRouteMapView.onSaveInstanceState(outState);
    }

    @OnClick({R.id.map_back})
    public void onViewClicked(View view) {
        finish();
    }
    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        startList.clear();
        endList.clear();
        if (mRouteMapView != null){
            mRouteMapView.onDestroy();
        }
        /**
         * 当前页面只是展示地图，activity销毁后不需要再回调导航的状态
         */
        mAMapNavi.removeAMapNaviListener(this);
        mAMapNavi.destroy();
    }

    @Override
    public void onInitNaviSuccess() {
        ZxToastUtil.centerToast("<<<<<onInitNaviSuccess" );
        ZxSharePreferenceUtil instance = ZxSharePreferenceUtil.getInstance();
        instance.init(this);
        String token = (String)instance.getParam("token", "null");
        Map<String,String> params = new HashMap<>();
        params.put("token",token);
        params.put("waybillNo",waybillNo);
        params.put("timestamp",System.currentTimeMillis()+"");
        params.put("sign","");
        params.put("sign",HaiTool.sign(params));
        mPresenter.checkLogisticsMethod(params);
    }

    @Override
    public void onCalculateRouteSuccess(AMapCalcRouteResult aMapCalcRouteResult) {
        ZxToastUtil.centerToast("<<<<<onCalculateRouteSuccess"+aMapCalcRouteResult.getCalcRouteType());
        AMapNaviPath naviPath = mAMapNavi.getNaviPath();
        drawRoutes(naviPath);
    }

    @Override
    public void onCalculateRouteFailure(AMapCalcRouteResult aMapCalcRouteResult) {
        ZxLogUtil.logError("<<<<<onCalculateRouteFailure"+aMapCalcRouteResult.getErrorDetail());
    }

    @Override
    public void checkLogisticsSuccess(String start, String end, String now) {
        if (!ZxStringUtil.isEmpty(start) && !ZxStringUtil.isEmpty(end) && !ZxStringUtil.isEmpty(now)){
            String[] starts = start.split(",");
            String[] ends = end.split(",");
            String[] nows = now.split(",");
            if (starts.length>1 && ends.length>1 && nows.length>1){
                NaviLatLng startLatlng = new NaviLatLng(Double.parseDouble(starts[0]), Double.parseDouble(starts[1]));
                startList.clear();
                startList.add(startLatlng);
                NaviLatLng endLatlng = new NaviLatLng(Double.parseDouble(ends[0]), Double.parseDouble(ends[1]));
                endList.clear();
                endList.add(endLatlng);

                addMark(Double.parseDouble(nows[0]), Double.parseDouble(nows[1]));
                calculateRoute();
            }

        }
    }

    //车辆位置
    private void addMark(double latitude,double longitude){
        MarkerOptions options = new MarkerOptions();
        options.anchor(0.5f, 1.1f);
        LatLng ll = new LatLng(latitude, longitude);//设置经纬度
        options.position(ll);
        BitmapDescriptor commonDes = BitmapDescriptorFactory.fromResource(R.mipmap.map_car);
        options.icon(commonDes);
        mAmap.addMarker(options);
    }

    //画线
    private void drawRoutes(AMapNaviPath path) {
        RouteOverLay routeOverLay = new RouteOverLay(mAmap, path, this);
        routeOverLay.setTrafficLine(false);
        routeOverLay.setStartPointBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.map_send));
        routeOverLay.setEndPointBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.map_receive));
        routeOverLay.addToMap();

        routeOverLay.zoomToSpan(120);
        RouteOverlayOptions routeOverlayOptions = new RouteOverlayOptions();
        routeOverlayOptions.setArrowColor(Color.BLUE);
        routeOverlayOptions.setLineWidth(40);

        routeOverLay.setRouteOverlayOptions(routeOverlayOptions);
    }


    //计算路径
    public void calculateRoute() {
        int strategyFlag = 0;
        try {
            strategyFlag = mAMapNavi.strategyConvert(true, false, true, false, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (strategyFlag >= 0) {
            AMapCarInfo carInfo = new AMapCarInfo();
            //设置车牌
//                    carInfo.setCarNumber(carNumber);
            //设置车牌是否参与限行算路
//                    carInfo.setRestriction(true);
            mAMapNavi.setCarInfo(carInfo);
            mAMapNavi.calculateDriveRoute(startList, endList, wayList, strategyFlag);
        }
    }



    /**
     * ************************************************** 在算路页面，以下接口全不需要处理，在以后的版本中我们会进行优化***********************************************************************************************
     **/
    @Override
    public void OnUpdateTrafficFacility(AMapNaviTrafficFacilityInfo arg0) {


    }

    @Override
    public void OnUpdateTrafficFacility(TrafficFacilityInfo arg0) {


    }

    @Override
    public void OnUpdateTrafficFacility(AMapNaviTrafficFacilityInfo[] arg0) {


    }

    @Override
    public void hideCross() {


    }

    @Override
    public void hideLaneInfo() {


    }

    @Override
    public void onCalculateRouteSuccess(int[] ints) {

    }

    @Override
    public void notifyParallelRoad(int arg0) {


    }

    @Override
    public void onArriveDestination() {


    }

    @Override
    public void onCalculateRouteFailure(int i) {

    }

    @Override
    public void onArrivedWayPoint(int arg0) {


    }

    @Override
    public void onEndEmulatorNavi() {


    }

    @Override
    public void onGetNavigationText(int arg0, String arg1) {


    }

    @Override
    public void onGetNavigationText(String s) {

    }

    @Override
    public void onGpsOpenStatus(boolean arg0) {


    }

    @Override
    public void onInitNaviFailure() {


    }

    @Override
    public void onLocationChange(AMapNaviLocation arg0) {


    }

    @Override
    public void onNaviInfoUpdate(NaviInfo arg0) {


    }

    @Override
    public void onNaviInfoUpdated(AMapNaviInfo arg0) {


    }

    @Override
    public void updateCameraInfo(AMapNaviCameraInfo[] aMapCameraInfos) {

    }

    @Override
    public void onServiceAreaUpdate(AMapServiceAreaInfo[] amapServiceAreaInfos) {

    }

    @Override
    public void onReCalculateRouteForTrafficJam() {


    }

    @Override
    public void onReCalculateRouteForYaw() {


    }

    @Override
    public void onStartNavi(int arg0) {


    }

    @Override
    public void onTrafficStatusUpdate() {


    }

    @Override
    public void showCross(AMapNaviCross arg0) {


    }

    @Override
    public void showLaneInfo(AMapLaneInfo[] arg0, byte[] arg1, byte[] arg2) {


    }

    @Override
    public void updateAimlessModeCongestionInfo(AimLessModeCongestionInfo arg0) {


    }

    @Override
    public void onPlayRing(int i) {

    }


    @Override
    public void onNaviRouteNotify(AMapNaviRouteNotifyData aMapNaviRouteNotifyData) {

    }

    @Override
    public void updateAimlessModeStatistics(AimLessModeStat arg0) {


    }

    @Override
    public void showModeCross(AMapModelCross aMapModelCross) {

    }

    @Override
    public void hideModeCross() {

    }

    @Override
    public void updateIntervalCameraInfo(AMapNaviCameraInfo aMapNaviCameraInfo, AMapNaviCameraInfo aMapNaviCameraInfo1, int i) {

    }

    @Override
    public void showLaneInfo(AMapLaneInfo aMapLaneInfo) {

    }



}
