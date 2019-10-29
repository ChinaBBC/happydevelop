package com.zx.haijixing.logistics.contract;

import com.zx.haijixing.logistics.entry.ProductEntry;
import com.zx.haijixing.share.base.IBaseContract;

import java.util.List;
import java.util.Map;

/**
 *
 *@作者 zx
 *@创建日期 2019/10/29 9:18
 *@描述 批量管理线路
 */
public interface QuantitySetConnect {
    interface QuantityView extends IBaseContract.IBaseView{
        void loProductSuccess(List<ProductEntry> data);
    }

    interface QuantityPresenter extends IBaseContract.IBasePresenter<QuantityView>{
        void loProductMethod(Map<String,String> params);
    }
}
