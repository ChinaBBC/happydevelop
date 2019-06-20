package com.zx.haijixing.share.base;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.alibaba.android.arouter.launcher.ARouter;
import com.allen.library.utils.ToastUtils;
import com.bumptech.glide.Glide;
import com.zx.haijixing.HaiApplication;
import com.zx.haijixing.R;
import com.zx.haijixing.share.ad.ActivityComponent;
import com.zx.haijixing.share.ad.ActivityModule;
import com.zx.haijixing.share.ad.DaggerActivityComponent;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 *
 *@作者 zx
 *@创建日期 2019/6/17 14:47
 *@描述 所有activity的父类
 */
public abstract class BaseActivity<T extends BaseContract.BasePresenter> extends AppCompatActivity implements BaseContract.BaseView {

    @Nullable
    @Inject
    protected T mPresenter;
    protected ActivityComponent mActivityComponent;
    /**
     * LoadingView
     */
    //protected LoadingView mLoadingView;
    @Nullable
    protected Toolbar mToolbar;
    private Unbinder unbinder;

    protected abstract void initView();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActivityComponent();
        ARouter.getInstance().inject(this);
        int layoutId=getLayoutId();
        setContentView(layoutId);
        initInjector();
        unbinder = ButterKnife.bind(this);
        initToolBar();
        attachView();
        initView();
        //if (!NetworkUtils.isConnected()) showNoNet();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        detachView();
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
        ARouter.getInstance().build("/activity/LoginActivity")
                .navigation();
    }

    @Override
    public void showFaild(String errorMsg) {

    }

    @Override
    public void onRetry() {

    }


    @Override
    public void showNoNet() {
        //ToastUtils.showShort(R.string.no_network_connection);
    }
    protected void setToolbarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }
    @Override
    public void showSuccess(String successMsg) {
    }

    /**
     * 分离view
     */
    private void detachView() {
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }
    /**
     * 初始化toolbar
     */
    protected  void initToolBar(){
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar == null) {
            throw new NullPointerException("toolbar can not be null");
        }
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(showHomeAsUp());
        /**toolbar除掉阴影*/
        getSupportActionBar().setElevation(0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mToolbar.setElevation(0);
        }

    }
    /**
     * 是否显示返回键
     *
     * @return
     */
    protected boolean showHomeAsUp() {
        return false;
    }


    protected abstract void initInjector();

    protected abstract int getLayoutId();

    /**
     * 初始化ActivityComponent
     */
    private void initActivityComponent() {

        mActivityComponent= DaggerActivityComponent.builder()
                .applicationComponent(((HaiApplication)getApplication()).getApplicationComponent())
                .activityModule(new ActivityModule(this))
                .build();
    }
}
