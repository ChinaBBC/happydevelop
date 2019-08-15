package com.zx.haijixing.driver.contract;

import com.zx.haijixing.share.base.IBaseContract;

import java.util.Map;

/**
 *
 *@作者 zx
 *@创建日期 2019/8/14 17:42
 *@描述 我的
 */
public interface MineContract {
    interface MineView extends IBaseContract.IBaseView{
        void servicePhoneSuccess(String phone);
    }

    interface MinePresenter extends IBaseContract.IBasePresenter<MineView>{
        void servicePhoneMethod(Map<String,String> params);
    }
}
