package com.zx.haijixing.share.base;

import android.text.TextUtils;

import com.allen.library.base.BaseObserver;
import com.allen.library.bean.BaseData;
import com.allen.library.utils.ToastUtils;

import io.reactivex.disposables.Disposable;

/**
 *
 *@作者 zx
 *@创建日期 2019/6/20 16:37
 *@描述 数据监听
 */
public abstract class HaiDataObserver<T> extends BaseObserver<HaiBaseData<T>> {
    /**
     * 失败回调
     *
     * @param errorMsg 错误信息
     */
    protected abstract void onError(String errorMsg);

    /**
     * 登录超时
     */
    protected abstract void LoginTimeOut();
    /**
     * 成功回调
     *
     * @param data 结果
     */
    protected abstract void onSuccess(T data);

    @Override
    public void doOnSubscribe(Disposable d) {
    }

    @Override
    public void doOnError(String errorMsg) {
        if (!isHideToast() && !TextUtils.isEmpty(errorMsg)) {
            ToastUtils.showToast(errorMsg);
        }
        onError(errorMsg);
    }

    @Override
    public void doOnNext(HaiBaseData<T> data) {
        //onSuccess(data.getData());
        //可以根据需求对code统一处理
        switch (data.getCode()) {
            case 0:
               onSuccess(data.getData());
                break;
            case 300:
            case 500:
            case 1002:
               onError(data.getMsg());
                break;
            case 1001:
                LoginTimeOut();
                break;
            default:
        }
    }

    @Override
    public void doOnCompleted() {
    }
}
