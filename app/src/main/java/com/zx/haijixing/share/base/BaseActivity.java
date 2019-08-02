package com.zx.haijixing.share.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zx.haijixing.share.OtherConstants;
import com.zx.haijixing.share.PathConstant;
import com.zx.haijixing.util.CommonDialogFragment;
import com.zx.haijixing.util.HaiDialogUtil;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import zx.com.skytool.ZxStatusBarCompat;
import zx.com.skytool.ZxToastUtil;

/**
 *
 *@作者 zx
 *@创建日期 2019/6/17 14:47
 *@描述 所有activity的父类
 */
public abstract class BaseActivity<T extends IBaseContract.IBasePresenter> extends AppCompatActivity implements IBaseContract.IBaseView {
    @Nullable
    protected T mPresenter;

    @Nullable
    private Unbinder unbinder;

    private CommonDialogFragment commonDialogFragment;
    private MyCloseReceiver myCloseReceiver;
    protected SmartRefreshLayout refreshLayout;


    protected abstract void initView();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ARouter.getInstance().inject(this);
        int layoutId=getLayoutId();
        setContentView(layoutId);
        initInjector();
        unbinder = ButterKnife.bind(this);
        attachView();
        setStatusBar();
        initView();
        registerBroad();
    }

    @Override
    public void showLoading() {
        commonDialogFragment = HaiDialogUtil.showProgress(getSupportFragmentManager());
    }

    @Override
    public void hideLoading() {
        if (commonDialogFragment != null){
            commonDialogFragment.dismissAllowingStateLoss();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        detachView();
        if (myCloseReceiver != null)
            unregisterReceiver(myCloseReceiver);
    }

    private void attachView() {
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
    }

    /**
     * 跳转到登录页面
     */
    @Override
    public void jumpToLogin() {
        ARouter.getInstance().build(PathConstant.ROUTE_LOGIN)
                .navigation();
    }

    @Override
    public void showFaild(String errorMsg) {
        ZxToastUtil.centerToast(errorMsg);
        if (refreshLayout != null){
            refreshLayout.finishLoadMore(false);
            refreshLayout.finishRefresh(false);
        }
        if (commonDialogFragment != null){
            commonDialogFragment.dismissAllowingStateLoss();
        }
    }

    @Override
    public void onRetry() {

    }


    @Override
    public void showSuccess(String successMsg) {
        ZxToastUtil.centerToast(successMsg);

    }

    /**
     * 分离view
     */
    private void detachView() {
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

    public String getHaiString(int id){
        return getResources().getString(id);
    }
    public int getHaiColor(int id){
        return getResources().getColor(id);
    }
    protected abstract void initInjector();

    protected abstract int getLayoutId();


    public void setStatusBar(){
        ZxStatusBarCompat.setStatusBarLightMode(this);
    }

    public void setTitleTopMargin(View view){
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) view.getLayoutParams();
        layoutParams.topMargin = ZxStatusBarCompat.getStatusBarHeight(this)+20;
        view.setLayoutParams(layoutParams);
    }

    private  void registerBroad(){
        myCloseReceiver = new MyCloseReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(OtherConstants.LOGIN_OUT);
        this.registerReceiver(myCloseReceiver,intentFilter);
    }

    class MyCloseReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(OtherConstants.LOGIN_OUT)){
                finish();
            }
        }
    }

}
