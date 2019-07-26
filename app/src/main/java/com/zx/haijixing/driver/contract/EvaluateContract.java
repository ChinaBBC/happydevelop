package com.zx.haijixing.driver.contract;

import com.zx.haijixing.driver.entry.EveryEvaluateEntry;
import com.zx.haijixing.driver.entry.TotalEvaluateEntry;
import com.zx.haijixing.share.base.IBaseContract;

import java.util.List;

/**
 *
 *@作者 zx
 *@创建日期 2019/7/24 17:23
 *@描述 评价
 */
public interface EvaluateContract {
    interface EvaluateView extends IBaseContract.IBaseView{
        void totalEvaluateSuccess(TotalEvaluateEntry totalEvaluateEntry);
        void everyEvaluateSuccess(List<EveryEvaluateEntry> everyEvaluateEntries);
    }

    interface EvaluatePresenter extends IBaseContract.IBasePresenter<EvaluateView>{
        void totalEvaluateMethod(String token);
        void everyEvaluateMethod(String token,int page);
    }

}
