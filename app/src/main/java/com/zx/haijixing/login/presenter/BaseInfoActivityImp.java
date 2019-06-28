package com.zx.haijixing.login.presenter;

import com.zx.haijixing.login.contract.IBaseInfoActivityContract;
import com.zx.haijixing.share.base.BasePresenter;

/**
 *
 *@作者 zx
 *@创建日期 2019/6/28 10:25
 *@描述 基本信息
 */
public class BaseInfoActivityImp extends BasePresenter<IBaseInfoActivityContract.BaseInfoView> implements IBaseInfoActivityContract.BaseInfoPresenter {
    @Override
    public void baseInfoMethod(String account, String password, String code) {

    }
}
