package com.zx.haijixing.driver.contract;

import com.zx.haijixing.share.base.IBaseContract;
/**
 *
 *@作者 zx
 *@创建日期 2019/7/10 17:09
 *@描述 新闻详情
 */
public interface NewsActivityContract {
    interface NewDetailView extends IBaseContract.IBaseView{
        void detailSuccess(String data);
    }
    interface NewsDetailPresenter extends IBaseContract.IBasePresenter<NewDetailView>{
        void detailMethod(String newId);
    }
}
