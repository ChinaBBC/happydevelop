package com.zx.haijixing.share.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.alibaba.android.arouter.launcher.ARouter;
import com.zx.haijixing.R;
import com.zx.haijixing.share.RoutePathConstant;
import com.zx.haijixing.util.CommonDialogFragment;
import com.zx.haijixing.util.HaiDialogUtil;

import butterknife.ButterKnife;
import butterknife.Unbinder;

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
        initView();
    }

    @Override
    public void showLoading() {
        commonDialogFragment = HaiDialogUtil.showProgress(getSupportFragmentManager());
    }

    @Override
    public void hideLoading() {
        commonDialogFragment.dismissAllowingStateLoss();
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
        ARouter.getInstance().build(RoutePathConstant.ROUTE_LOGIN)
                .navigation();
    }

    @Override
    public void showFaild(String errorMsg) {

    }

    @Override
    public void onRetry() {

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
     * 是否显示返回键
     *
     * @return
     */
    protected boolean showHomeAsUp() {
        return false;
    }

    public String getHaiString(int id){
        return getResources().getString(id);
    }
    public int getHaiColor(int id){
        return getResources().getColor(id);
    }
    protected abstract void initInjector();

    protected abstract int getLayoutId();


}
