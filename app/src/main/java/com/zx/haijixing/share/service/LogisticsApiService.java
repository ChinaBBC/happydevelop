package com.zx.haijixing.share.service;

import com.zx.haijixing.driver.entry.PrintEntry;
import com.zx.haijixing.share.base.HaiBaseData;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 *
 *@作者 zx
 *@创建日期 2019/6/17 14:41
 *@描述 物流接口服务
 */
public interface LogisticsApiService {

    //查看物流
    @GET("reception/order/queryWayPoint")
    Observable<String> checkLogisticsApi(@Query("token") String token, @Query("waybillNo") String waybillNo);
}
