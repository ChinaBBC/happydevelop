package com.zx.haijixing.login.contract;

import com.zx.haijixing.share.base.IBaseContract;

/**
 *
 *@作者 zx
 *@创建日期 2019/6/28 11:46
 *@描述 后台审核
 */
public interface ICheckingActivityContract {
    interface CheckingView extends IBaseContract.IBaseView{
        void checkingSuccess();
    }

    interface CheckingPresenter extends IBaseContract.IBasePresenter<CheckingView>{
        void checkingMethod(String param);
    }
}
