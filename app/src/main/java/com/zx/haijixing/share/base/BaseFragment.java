package com.zx.haijixing.share.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.launcher.ARouter;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import zx.com.skytool.ZxToastUtil;

/**
 *
 *@作者 zx
 *@创建日期 2019/6/17 14:49
 *@描述 所有fragment的父类
 */
public abstract class BaseFragment<T extends IBaseContract.IBasePresenter> extends Fragment implements IBaseContract.IBaseView {

    private static final String STATE_SAVE_IS_HIDDEN = "STATE_SAVE_IS_HIDDEN";
    @Nullable
    protected T mPresenter;
    private Unbinder unbinder;
    private View mRootView, mErrorView, mEmptyView;

    protected abstract int getLayoutId();

    protected abstract void initInjector();

    protected abstract void initView(View view);

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ARouter.getInstance().inject(this);
        initInjector();
        attachView();
        //if (!NetworkUtils.isConnected()) showNoNet();
        if (savedInstanceState != null) {
            boolean isSupportHidden = savedInstanceState.getBoolean(STATE_SAVE_IS_HIDDEN);
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            if (isSupportHidden) {
                ft.hide(this);
            } else {
                ft.show(this);
            }
            ft.commit();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(STATE_SAVE_IS_HIDDEN, isHidden());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        inflaterView(inflater, container);
        unbinder = ButterKnife.bind(this, mRootView);
        initView(mRootView);
        return mRootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        detachView();
    }

    @Override
    public void showLoading() {
        ZxToastUtil.centerToast("showLoading");
    }

    @Override
    public void hideLoading() {
        ZxToastUtil.centerToast("hideLoading");
    }

    @Override
    public void showSuccess(String successMsg) {
        ZxToastUtil.centerToast(successMsg);
    }

    @Override
    public void showFaild(String errorMsg) {
        ZxToastUtil.centerToast(errorMsg);
    }

    @Override
    public void showNoNet() {
        //ZxToastUtil.centerToast(R.string.no_network_connection);
    }

    @Override
    public void onRetry() {
        ZxToastUtil.centerToast("onRetry");
    }

    /**
     * 贴上view
     */
    private void attachView() {
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
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
     * 设置View
     *
     * @param inflater
     * @param container
     */
    private void inflaterView(LayoutInflater inflater, @Nullable ViewGroup container) {
        if (mRootView == null) {
            mRootView = inflater.inflate(getLayoutId(), container, false);
        }
    }
    @Override
    public void jumpToLogin() {
        ARouter.getInstance().build("/activity/LoginActivity")
                .navigation();
    }
}
