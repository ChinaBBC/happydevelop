package com.zx.haijixing.logistics.contract;

import com.zx.haijixing.logistics.entry.DriverEntry;
import com.zx.haijixing.logistics.entry.FeeStatisticsEntry;
import com.zx.haijixing.share.base.IBaseContract;

import java.util.List;
import java.util.Map;

/**
 *
 *@作者 zx
 *@创建日期 2019/8/2 16:15
 *@描述 运费运单统计
 */
public interface FeeStatisticsContract {
    interface FeeStatisticsView extends IBaseContract.IBaseView{
        void orderStatisticsSuccess(FeeStatisticsEntry feeStatisticsEntry);
        void feeStatisticsSuccess(FeeStatisticsEntry feeStatisticsEntry);
        void receiveStatisticsSuccess(FeeStatisticsEntry feeStatisticsEntry);
        void todayStatisticsSuccess(String countNum,String totalPrice,String toPayMoney);
        void searchDriverSuccess(List<DriverEntry> driverEntries);
    }

    interface FeeStatisticsPresenter extends IBaseContract.IBasePresenter<FeeStatisticsView>{
        void orderStatisticsMethod(Map<String,String> params);
        void feeStatisticsMethod(Map<String,String> params);
        void todayStatisticsMethod(Map<String, String> params);
        void receiveStatisticsMethod(Map<String,String> params);
        void searchDriverMethod(Map<String, String> params);
    }
}
