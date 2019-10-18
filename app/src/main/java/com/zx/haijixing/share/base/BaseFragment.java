package com.zx.haijixing.share.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.launcher.ARouter;
import com.allen.library.RxHttpUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zx.haijixing.share.OtherConstants;
import com.zx.haijixing.share.PathConstant;
import com.zx.haijixing.util.CommonDialogFragment;
import com.zx.haijixing.util.HaiDialogUtil;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import zx.com.skytool.ZxSharePreferenceUtil;
import zx.com.skytool.ZxStatusBarCompat;
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
    private CommonDialogFragment commonDialogFragment;
    protected SmartRefreshLayout refreshLayout;

    protected abstract int getLayoutId();

    protected abstract void initData();

    protected abstract void initView(View view);

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ARouter.getInstance().inject(this);
        initData();

        attachView();

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
        commonDialogFragment = HaiDialogUtil.showProgress(getFragmentManager());
    }

    @Override
    public void hideLoading() {
        if (commonDialogFragment != null){
            commonDialogFragment.dismissAllowingStateLoss();
        }
    }

    @Override
    public void showSuccess(String successMsg) {
        ZxToastUtil.centerToast(successMsg);
    }

    @Override
    public void showFaild(String errorMsg) {
        ZxToastUtil.centerToast(errorMsg);
        if (refreshLayout != null){
            refreshLayout.finishRefresh(false);
            refreshLayout.finishLoadMore(false);
        }
        if (commonDialogFragment != null){
            commonDialogFragment.dismissAllowingStateLoss();
        }
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
        if (commonDialogFragment != null){
            commonDialogFragment.dismissAllowingStateLoss();
        }
        RxHttpUtils.cancel(OtherConstants.CANCEL_REQUEST);
        ZxSharePreferenceUtil instance = ZxSharePreferenceUtil.getInstance();
        instance.init(getContext());
        instance.setLogin(false);
        Intent intent = new Intent();
        intent.setAction(OtherConstants.LOGIN_OUT);
        getContext().sendBroadcast(intent);
        ARouter.getInstance().build(PathConstant.ROUTE_LOGIN).navigation();
    }

    //获取string
    public String getHaiString(int id){
        return getResources().getString(id);
    }

    public void setTitleTopMargin(View view,int margin){
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) view.getLayoutParams();
        layoutParams.topMargin = ZxStatusBarCompat.getStatusBarHeight(getContext())+20+margin;
        view.setLayoutParams(layoutParams);
    }
}
