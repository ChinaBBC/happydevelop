package com.zx.haijixing.driver.contract;

import com.zx.haijixing.share.base.IBaseContract;

import java.util.Map;

/**
 *
 *@作者 zx
 *@创建日期 2019/7/24 16:08
 *@描述 修改车辆信息
 */
public interface PaperChangeContract {
    interface PaperChangeView extends IBaseContract.IBaseView{
        void changeInfoSuccess(String msg);
        void uploadImgSuccess(String path,int tag);
    }

    interface PaperChangePresenter extends IBaseContract.IBasePresenter<PaperChangeView>{
        void changeDriverId(Map<String, String> params);
        void changeTruckInfo(Map<String,String> params);
        void uploadImg(String path,int tag);
    }
}
