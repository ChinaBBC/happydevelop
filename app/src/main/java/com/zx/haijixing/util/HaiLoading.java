package com.zx.haijixing.util;

import android.support.v4.app.FragmentManager;

import com.allen.library.interfaces.ILoadingView;

public class HaiLoading implements ILoadingView {
    private CommonDialogFragment loading;
    private FragmentManager fragmentManager;

    public HaiLoading(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    @Override
    public void showLoadingView() {
        this.loading = HaiDialogUtil.showProgress(fragmentManager);
    }

    @Override
    public void hideLoadingView() {
        if (loading != null)
            loading.dismissAllowingStateLoss();
    }
}
