package com.zx.haijixing.logistics.contract;

import com.zx.haijixing.share.base.IBaseContract;

import java.util.Map;

/**
 *
 *@作者 zx
 *@创建日期 2019/8/15 16:10
 *@描述 红点
 *
 */
public interface BotContract {
    interface BotView extends IBaseContract.IBaseView{
        void botSuccess(int allot,int receive,int send,int sending);
    }

    interface BotPresenter extends IBaseContract.IBasePresenter<BotView>{
        void botMethod(Map<String,String> params);
    }
}
