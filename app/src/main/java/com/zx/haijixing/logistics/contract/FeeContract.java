package com.zx.haijixing.logistics.contract;

import com.zx.haijixing.share.base.IBaseContract;

import java.util.List;
import java.util.Map;

/**
 *
 *@作者 zx
 *@创建日期 2019/8/2 14:07
 *@描述 品类价格
 */
public interface FeeContract {
    interface FeeView extends IBaseContract.IBaseView{
        void feeSuccess(List<List<String>> data);
    }

    interface FeePresenter extends IBaseContract.IBasePresenter<FeeView>{
        void feeMethod(Map<String, String> params);
    }
}
