package com.zx.haijixing.login.contract;

import com.zx.haijixing.share.base.IBaseContract;

/**
 *
 *@作者 zx
 *@创建日期 2019/8/19 14:52
 *@描述 隐私协议
 */
public interface IProtocolContract {
    interface ProtocolView extends IBaseContract.IBaseView{
        void protocolSuccess(String data);
        void privateSuccess(String data);
    }

    interface ProtocolPresenter extends IBaseContract.IBasePresenter<ProtocolView>{
        void protocolMethod();
        void privateMethod();
    }
}
