package com.zx.haijixing.logistics.contract;

import com.zx.haijixing.logistics.entry.CompanyEntry;
import com.zx.haijixing.share.base.IBaseContract;

import java.util.List;

/**
 *
 *@作者 zx
 *@创建日期 2019/8/5 15:40
 *@描述 物流端公司中心
 */
public interface CompanyCenterContract {
    interface CompanyCenterView extends IBaseContract.IBaseView{
        void companySuccess(List<CompanyEntry> companyEntries);
    }

    interface CompanyCenterPresenter extends IBaseContract.IBasePresenter<CompanyCenterView>{
        void companyMethod(String token);
    }
}
