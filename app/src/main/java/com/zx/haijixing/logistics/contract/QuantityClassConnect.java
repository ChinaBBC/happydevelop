package com.zx.haijixing.logistics.contract;

import com.zx.haijixing.share.base.IBaseContract;

import java.util.Map;

/**
 *
 *@作者 zx
 *@创建日期 2019/10/29 14:23
 *@描述 批量管理班次
 */
public interface QuantityClassConnect {
    interface QuantityClassView extends IBaseContract.IBaseView{
        void quClassSuccess();
    }

    interface QuantityClassPresenter extends IBaseContract.IBasePresenter<QuantityClassView>{
        void quClassMethod(Map<String,String> params);
    }
}
