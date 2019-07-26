package com.zx.haijixing.logistics.contract;

import com.zx.haijixing.share.base.IBaseContract;

/**
 *
 *@作者 zx
 *@创建日期 2019/7/22 10:32
 *@描述 查看物流
 */
public interface CheckLogisticsContract {
    interface CheckLogisticView extends IBaseContract.IBaseView{
        void checkLogisticsSuccess(String start,String end,String now);
    }

    interface CheckLogisticsPresenter extends IBaseContract.IBasePresenter<CheckLogisticView>{
        void checkLogisticsMethod(String token,String id);
    }
}
