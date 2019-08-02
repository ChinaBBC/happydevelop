package com.zx.haijixing.share.service;

import com.zx.haijixing.logistics.entry.ClassManageEntry;
import com.zx.haijixing.logistics.entry.DriverEntry;
import com.zx.haijixing.logistics.entry.LinesClassEntry;
import com.zx.haijixing.logistics.entry.LinesManageEntry;
import com.zx.haijixing.logistics.entry.TruckInfoEntry;
import com.zx.haijixing.share.base.HaiBaseData;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
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

    //线路班次
    @FormUrlEncoded
    @POST("reception/waybill/line/bkki")
    Observable<HaiBaseData<List<LinesClassEntry>>> linesClassApi(@FieldMap Map<String, Object> params);

    //派单
    @FormUrlEncoded
    @POST("reception/waybill/pai/waybill")
    Observable<String> allotOrderApi(@FieldMap Map<String, Object> params);

    //班次
    @FormUrlEncoded
    @POST("reception/waybill/back/bkki")
    Observable<HaiBaseData<List<LinesClassEntry>>> allLinesApi(@FieldMap Map<String, Object> params);

    //班次统计
    @FormUrlEncoded
    @POST("reception/waybill/back/bkki/count")
    Observable<String> countAllApi(@Field("token") String token);

    //线路管理
    @FormUrlEncoded
    @POST("logistics/line/list")
    Observable<HaiBaseData<List<LinesManageEntry>>> linesManageApi(@FieldMap Map<String, Object> params);

    //班次维护
    @FormUrlEncoded
    @POST("logistics/line/bkki/list")
    Observable<HaiBaseData<List<ClassManageEntry>>> classManageApi(@Field("token") String token, @Field("lineId") String lineId);

    //查询线路下的司机
    @FormUrlEncoded
    @POST("logistics/line/driver/list")
    Observable<HaiBaseData<List<DriverEntry>>> linesDriverApi(@Field("token") String token, @Field("lineId") String lineId);

    //查询司机名下车辆
    @FormUrlEncoded
    @POST("logistics/line/driver/car/list")
    Observable<HaiBaseData<List<TruckInfoEntry>>> driverTruckApi(@Field("token") String token, @Field("driverId") String driverId);

    //新增班次
    @FormUrlEncoded
    @POST("logistics/line/save/bkki")
    Observable<String> addClassApi(@FieldMap Map<String, Object> params);

    //删除班次
    @FormUrlEncoded
    @POST("logistics/line/del/bkki")
    Observable<String> deleteClassApi(@Field("token") String token, @Field("bakkiId") String bakkiId);

    //品类价格
    @FormUrlEncoded
    @POST("logistics/line/getprice/details")
    Observable<HaiBaseData<List<List<String>>>> typePriceApi(@Field("token") String token, @Field("lineId") String lineId);

}
