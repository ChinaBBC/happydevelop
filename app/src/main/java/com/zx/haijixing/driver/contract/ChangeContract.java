package com.zx.haijixing.driver.contract;

import com.zx.haijixing.share.base.IBaseContract;

import java.util.Map;

/**
 *
 *@作者 zx
 *@创建日期 2019/7/23 11:43
 *@描述 修改信息
 */
public interface ChangeContract {
    interface ChangeView extends IBaseContract.IBaseView{
        void changeSuccess(String head,String base);
        void changeHeadSuccess(String msg);
    }

    interface ChangePresenter extends IBaseContract.IBasePresenter<ChangeView>{
        void changeMethod(String path);
        void changeHeadImgMethod(Map<String, String> params);
    }
}
