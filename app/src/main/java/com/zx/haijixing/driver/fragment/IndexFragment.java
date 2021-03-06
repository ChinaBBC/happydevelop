package com.zx.haijixing.driver.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.ethanhua.skeleton.RecyclerViewSkeletonScreen;
import com.ethanhua.skeleton.Skeleton;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.zx.haijixing.R;
import com.zx.haijixing.driver.activity.DriverActivity;
import com.zx.haijixing.driver.adapter.IndexAdapter;
import com.zx.haijixing.driver.contract.IIndexContract;
import com.zx.haijixing.driver.entry.BannerEntry;
import com.zx.haijixing.driver.entry.NewsEntry;
import com.zx.haijixing.driver.presenter.IndexImp;
import com.zx.haijixing.share.OtherConstants;
import com.zx.haijixing.share.PathConstant;
import com.zx.haijixing.share.base.BaseFragment;
import com.zx.haijixing.share.pub.entry.EventBusEntity;
import com.zx.haijixing.share.pub.entry.NotifyEntry;
import com.zx.haijixing.util.CommonDialogFragment;
import com.zx.haijixing.util.HaiDialogUtil;
import com.zx.haijixing.util.HaiTool;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
 *@创建日期 2019/7/3 10:12
 *@描述 首页
 */
public class IndexFragment extends BaseFragment<IndexImp> implements IIndexContract.IndexView,OnRefreshLoadMoreListener {


    @BindView(R.id.index_one)
    LinearLayout one;
    @BindView(R.id.index_order_number)
    EditText orderNumber;
    @BindView(R.id.index_search)
    TextView search;
    @BindView(R.id.index_scan_code)
    ImageView scanCode;
    @BindView(R.id.index_rv_body)
    RecyclerView rvBody;
    @BindView(R.id.index_refresh)
    SmartRefreshLayout refresh;

    private IndexAdapter indexAdapter;
    private CommonDialogFragment showClock;
    private List<NewsEntry.NewsData> newsData = new ArrayList<>();
    private int page = 1;
    private RecyclerViewSkeletonScreen skeletonScreen;
    private boolean isHide = false;
    private String token = null;
    private String loginType;
    private  Map<String,String> params = new HashMap<>();
    private  Map<String,String> paramsBanner = new HashMap<>();
    private Map<String,String> notifyMap = new HashMap<>();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_index;
    }

    @Override
    protected void initData() {
        mPresenter = new IndexImp();
    }

    @Override
    protected void initView(View view) {

        ZxSharePreferenceUtil instance = ZxSharePreferenceUtil.getInstance();
        instance.init(getContext());
        token = (String) instance.getParam("token","null");
        loginType = (String) instance.getParam("login_type", "4");

        setTitleTopMargin(one,0);
        setTitleTopMargin(scanCode,20);

        EventBus.getDefault().register(this);

        rvBody.setLayoutManager(new LinearLayoutManager(this.getContext(),LinearLayoutManager.VERTICAL,false));
        indexAdapter = new IndexAdapter(newsData);
        indexAdapter.setLoginType(loginType);
        rvBody.setAdapter(indexAdapter);
        indexAdapter.setClickListener(this::onViewClicked);

        params.put("token",token);
        params.put(OtherConstants.PAGE, page+"");
        params.put(OtherConstants.SIZE, 5+"");
        params.put("timestamp",System.currentTimeMillis()+"");
        params.put("sign","");
        params.put("sign",HaiTool.sign(params));
        mPresenter.newsDataMethod(params);


        paramsBanner.put("token",token);
        paramsBanner.put("timestamp",System.currentTimeMillis()+"");
        paramsBanner.put("sign","");
        paramsBanner.put("sign",HaiTool.sign(paramsBanner));
        mPresenter.newsDataBanner(paramsBanner);

        refresh.setOnRefreshLoadMoreListener(this);
        skeletonScreen = Skeleton.bind(rvBody).adapter(indexAdapter)
                .shimmer(true)
                .load(R.layout.skeleton_index_data)
                .show();


        notifyMap.put("token",token);
        notifyMap.put(OtherConstants.PAGE,1+"");
        notifyMap.put(OtherConstants.SIZE,3+"");
        notifyMap.put("timestamp",System.currentTimeMillis()+"");
        notifyMap.put("sign",HaiTool.sign(notifyMap));
        mPresenter.notifyMethod(notifyMap);
    }

    @OnClick({R.id.index_search, R.id.index_scan_code})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.index_search:
                String trim = orderNumber.getText().toString().trim();
                if (ZxStringUtil.isEmpty(trim)){
                    ZxToastUtil.centerToast("请输入搜索内容");
                }else {
                    ARouter.getInstance().build(PathConstant.DRIVER_SEARCH)
                            .withString("content",trim)
                            .navigation();
                }
                break;
            case R.id.index_scan_code:
                scanQrCode();
                break;
            case R.id.index_header_clock:
                if (loginType.equals(OtherConstants.LOGIN_DRIVER)){
                    Map<String,String> param = new HashMap<>();
                    param.put("token",token);
                    param.put("timestamp",System.currentTimeMillis()+"");
                    param.put("sign","");
                    param.put("sign",HaiTool.sign(param));
                    mPresenter.workStatusMethod(param);
                }else {
                    EventBus.getDefault().post(new EventBusEntity(OtherConstants.EVENT_LOGISTICS));
                }
                break;
            case R.id.dialog_clock_yes:
                Map<String,String> param = new HashMap<>();
                param.put("token",token);
                param.put("timestamp",System.currentTimeMillis()+"");
                param.put("sign","");
                param.put("sign",HaiTool.sign(param));
                mPresenter.workMethod(param);
                //showClock.dismissAllowingStateLoss();
                break;
            case R.id.dialog_clock_no:
                showClock.dismissAllowingStateLoss();
                break;
            case R.id.index_header_notify:
                ARouter.getInstance().build(PathConstant.DRIVER_NOTIFY).navigation();
                break;
            case R.id.index_header_services:
                ARouter.getInstance().build(PathConstant.DRIVER_SERVICES)
                        .withString("loginType",loginType)
                        .withString("token",token)
                        .navigation();
                break;
            case R.id.index_header_receive:
                EventBus.getDefault().post(new EventBusEntity(OtherConstants.EVENT_RECEIVE));
                break;
            case R.id.index_header_print:
                EventBus.getDefault().post(new EventBusEntity(OtherConstants.EVENT_PRINT));
                break;
        }
    }

    @Override
    public void newsDataSuccess(List<NewsEntry.NewsData> newsEntries,String base) {
        if (!isHide){
            skeletonScreen.hide();
            isHide = true;
        }
        indexAdapter.setBaseStr(base);

        if (newsEntries.size()>0){
            refresh.finishLoadMore(true);
            refresh.finishRefresh(true);
            refresh.setNoMoreData(false);
            newsData.addAll(newsEntries);
            indexAdapter.notifyDataSetChanged();
        }else {
            refresh.finishRefreshWithNoMoreData();
            refresh.finishLoadMoreWithNoMoreData();
        }

    }

    @Override
    public void newDataBannerSuccess(List<BannerEntry.BannerData> bannerDataList,String bannerStr) {
        indexAdapter.setBannerData(bannerDataList,bannerStr);
        indexAdapter.notifyDataSetChanged();
    }

    @Override
    public void workSuccess(String msg) {
        ZxToastUtil.centerToast(msg);
        showClock.dismissAllowingStateLoss();
    }

    @Override
    public void weatherSuccess(String city, String weather, String temp) {
        //ZxLogUtil.logError("<city>"+city+"<weather>"+weather+"<temp>"+temp);
        indexAdapter.setWeather(city,weather,temp);
    }

    @Override
    public void workStatusSuccess(String msg) {
        if ("1".equals(msg)){
            showClock = HaiDialogUtil.showClock(getFragmentManager(),"是否下班？", this::onViewClicked);
        }else {
            showClock = HaiDialogUtil.showClock(getFragmentManager(),null, this::onViewClicked);
        }
    }

    @Override
    public void notifySuccess(List<NotifyEntry> notifyEntries) {
        if (notifyEntries.size()>0){
            indexAdapter.setNotice(notifyEntries.get(0).getTitle());
        }else {
            indexAdapter.setNotice("暂无新消息");
        }
        indexAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        if (page<1000)
            page++;
        params.put(OtherConstants.PAGE, page+"");
        params.put("timestamp",System.currentTimeMillis()+"");
        params.put("sign","");
        params.put("sign",HaiTool.sign(params));
        mPresenter.newsDataMethod(params);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        refresh.setNoMoreData(false);
        page = 1;
        newsData.clear();
        indexAdapter.notifyDataSetChanged();
        params.put(OtherConstants.PAGE, page+"");
        params.put("timestamp",System.currentTimeMillis()+"");
        params.put("sign","");
        params.put("sign",HaiTool.sign(params));
        mPresenter.newsDataMethod(params);
        paramsBanner.put("timestamp",System.currentTimeMillis()+"");
        paramsBanner.put("sign","");
        paramsBanner.put("sign",HaiTool.sign(paramsBanner));
        mPresenter.newsDataBanner(paramsBanner);
        notifyMap.put("timestamp",System.currentTimeMillis()+"");
        notifyMap.put("sign",HaiTool.sign(notifyMap));
        mPresenter.notifyMethod(notifyMap);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == OtherConstants.READ_QR) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    if (result.length() == 12){
                        ARouter.getInstance().build(PathConstant.DRIVER_ORDER_DETAIL)
                                .withString("orderId",result)
                                .withString("detailType",OtherConstants.ORDER_INFO+"")
                                .navigation();
                    }else {
                        ZxToastUtil.centerToast("请扫描正确的二维码");
                    }
                    ZxLogUtil.logError("onActivityResult: " + result);
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    ZxToastUtil.centerToast("识别二维码失败");
                }
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventBusEntity entity){
        if (entity.getMsg() == OtherConstants.WEATHER_ENTRY){
            mPresenter.weatherMethod(entity.getMessage());
        }
    }

    /**
     * 扫描二维码
     */
    private void scanQrCode(){
        Intent intent = new Intent(getActivity(), CaptureActivity.class);
        //第二个参数是请求码，定义的常量随意填写
        startActivityForResult(intent, OtherConstants.READ_QR);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
