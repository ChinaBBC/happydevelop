package com.zx.haijixing.login.presenter;

import com.zx.haijixing.login.contract.IForgetActivityContract;
import com.zx.haijixing.share.base.BasePresenter;

/**
 *
 *@作者 zx
 *@创建日期 2019/6/21 15:43
 *@描述 忘记密码presenter
 */
public class ForgetActivityImp extends BasePresenter<IForgetActivityContract.ForgetView> implements IForgetActivityContract.ForgetPresenter {
    @Override
    public void findMethod(String account, String password, String code) {
        mView.findSuccess();
    }
}
