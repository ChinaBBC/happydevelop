package com.zx.haijixing.share.base;

public interface IBaseContract {
    interface IBasePresenter<T extends IBaseContract.IBaseView>{
        void attachView(T view);

        void detachView();
    }
    interface IBaseView {
        //显示进度中
        void showLoading();

        //隐藏进度
        void hideLoading();

        //显示请求成功
        void showSuccess(String message);

        //失败重试
        void showFaild(String message);

        //重试
        void onRetry();

        /**
         * 绑定生命周期
         *
         * @param <T>
         * @return
         */
        //<T> LifecycleTransformer<T> bindToLife();
        /**
         * 跳转到登录页面
         */
        void jumpToLogin();
    }
}
