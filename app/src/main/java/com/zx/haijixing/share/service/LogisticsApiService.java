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
import retrofit2.http.QueryMap;

/**
 *
 *@作者 zx
 *@创建日期 2019/6/17 14:41
 *@描述 物流接口服务
 */
public interface LogisticsApiService {

    //查看物流
    @GET("api/reception/waybill/queryWayPoint")
    Observable<String> checkLogisticsApi(@QueryMap Map<String, String> params);

    //线路班次
    @FormUrlEncoded
    @POST("api/reception/waybill/line/bkki")
    Observable<HaiBaseData<List<LinesClassEntry>>> linesClassApi(@FieldMap Map<String, String> params);

    //派单
    @FormUrlEncoded
    @POST("api/reception/waybill/pai/waybill")
    Observable<String> allotOrderApi(@FieldMap Map<String, String> params);

    //班次
    @FormUrlEncoded
    @POST("api/reception/waybill/back/bkki")
    Observable<HaiBaseData<List<LinesClassEntry>>> allLinesApi(@FieldMap Map<String, String> params);

    //班次统计
    @FormUrlEncoded
    @POST("api/reception/waybill/back/bkki/count")
    Observable<String> countAllApi(@FieldMap Map<String, String> params);

    //线路管理
    @FormUrlEncoded
    @POST("api/logistics/line/list")
    Observable<HaiBaseData<List<LinesManageEntry>>> linesManageApi(@FieldMap Map<String, String> params);

    //班次维护
    @FormUrlEncoded
    @POST("api/logistics/line/bkki/list")
    Observable<HaiBaseData<List<ClassManageEntry>>> classManageApi(@FieldMap Map<String, String> params);

    //查询线路下的司机
    @FormUrlEncoded
    @POST("api/logistics/line/driver/list")
    Observable<HaiBaseData<List<DriverEntry>>> linesDriverApi(@FieldMap Map<String, String> params);

    //查询司机名下车辆
    @FormUrlEncoded
    @POST("api/logistics/line/driver/car/list")
    Observable<HaiBaseData<List<TruckInfoEntry>>> driverTruckApi(@FieldMap Map<String, String> params);

    //新增班次
    @FormUrlEncoded
    @POST("api/logistics/line/save/bkki")
    Observable<String> addClassApi(@FieldMap Map<String, String> params);

    //修改班次
    @FormUrlEncoded
    @POST("/api/logistics/line/upd/bakki")
    Observable<String> editorClassApi(@FieldMap Map<String, String> params);

    //删除班次
    @FormUrlEncoded
    @POST("api/logistics/line/del/bkki")
    Observable<String> deleteClassApi(@FieldMap Map<String, String> params);

    //品类价格
    @FormUrlEncoded
    @POST("api/logistics/line/getprice/details")
    Observable<HaiBaseData<List<List<String>>>> typePriceApi(@FieldMap Map<String, String> params);


    //小红点
    @GET("api/reception/waybill/tag/num")
    Observable<String> botApi(@QueryMap Map<String, String> params);

}
