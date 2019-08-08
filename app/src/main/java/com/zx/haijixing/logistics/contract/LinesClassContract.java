package com.zx.haijixing.logistics.contract;

import com.zx.haijixing.logistics.entry.LinesClassEntry;
import com.zx.haijixing.share.base.IBaseContract;

import java.util.List;
import java.util.Map;

/**
 *
 *@作者 zx
 *@创建日期 2019/8/1 10:03
 *@描述 派单线路班次
 */
public interface LinesClassContract {
    interface LinesClassView extends IBaseContract.IBaseView{
        void linesClassSuccess(List<LinesClassEntry> linesClassEntries);
        void allotOrderSuccess(String msg);
    }

    interface LinesClassPresenter extends IBaseContract.IBasePresenter<LinesClassView>{
        void linesClassMethod(Map<String,String> params);
        void allotOrderMethod(Map<String,String> params);
    }
}
