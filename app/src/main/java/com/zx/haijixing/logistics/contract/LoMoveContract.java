package com.zx.haijixing.logistics.contract;

import com.zx.haijixing.logistics.entry.LinesClassEntry;
import com.zx.haijixing.share.base.IBaseContract;

import java.util.List;
import java.util.Map;

/**
 *
 *@作者 zx
 *@创建日期 2019/8/1 14:11
 *@描述 物流动态
 */
public interface LoMoveContract {
    interface LoMoveView extends IBaseContract.IBaseView{
        void loMoveSuccess(List<LinesClassEntry> linesClassEntries);
        void countAllSuccess(String today,String waitSend,String sending,String complete);
    }

    interface LoMovePresenter extends IBaseContract.IBasePresenter<LoMoveView>{
        void loMoveMethod(Map<String,Object> params);
        void countAllMethod(String token);
    }
}
