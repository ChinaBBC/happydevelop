package com.zx.haijixing.share.service;

import com.zx.haijixing.driver.entry.DriverClassEntry;
import com.zx.haijixing.driver.entry.EveryEvaluateEntry;
import com.zx.haijixing.driver.entry.GoodsTypePriceEntry;
import com.zx.haijixing.driver.entry.OrderDetailEntry;
import com.zx.haijixing.driver.entry.OrderTotalEntry;
import com.zx.haijixing.driver.entry.PrintEntry;
import com.zx.haijixing.driver.entry.TotalEvaluateEntry;
import com.zx.haijixing.driver.entry.TruckDetailEntry;
import com.zx.haijixing.driver.entry.TruckEntry;
import com.zx.haijixing.share.OtherConstants;
import com.zx.haijixing.share.base.HaiBaseData;
import com.zx.haijixing.share.base.HaiDataObserver;

import java.util.List;
import java.util.Map;
import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 *
 *@作者 zx
 *@创建日期 2019/6/17 14:41
 *@描述 司机接口服务
 */
public interface DriverApiService {

    //订单列表
    @GET("reception/waybill/params/list")
    Observable<HaiBaseData<OrderTotalEntry>> orderLists(@QueryMap Map<String, Object> params);

    //接单
    @FormUrlEncoded
    @POST("reception/waybill/receipt")
    Observable<String> receiveOrders(@FieldMap Map<String, Object> params);

    //订单详情
    @GET("reception/waybill/details")
    Observable<HaiBaseData<OrderDetailEntry>> orderDetails(@QueryMap Map<String, Object> params);

    //出发
    @FormUrlEncoded
    @POST("reception/waybill/batchdepart")
    Observable<String> sendApi(@FieldMap Map<String, Object> params);

    //完成
    @FormUrlEncoded
    @POST("reception/waybill/done")
    Observable<String> completeApi(@FieldMap Map<String, Object> params);

    //收款
    @FormUrlEncoded
    @POST("reception/waybill/makeprice")
    Observable<String> receiveMoneyAPi(@FieldMap Map<String, Object> params);

    //订单打印
    @GET("reception/waybill/dadan")
    Observable<HaiBaseData<PrintEntry>> printOrderApi(@Query("token") String token, @Query("waybillId") String waybillId);

    //改变打单状态
    @FormUrlEncoded
    @POST("reception/waybill/upd/dadanflag")
    Observable<String> changePrint(@FieldMap Map<String, Object> params);

    //司机班次信息、车辆及路线
    @FormUrlEncoded
    @POST("reception/waybill/driver/bkki")
    Observable<HaiBaseData<List<DriverClassEntry>>> driverClass(@Field("token") String token);

    //修改运单价格
    @FormUrlEncoded
    @POST("reception/waybill/upd/realprice")
    Observable<String> changePrice(@FieldMap Map<String, Object> params);


    //位置上传
    @FormUrlEncoded
    @POST("reception/order/insertWayPoint")
    Observable<String> uploadLocate(@Field("token") String token,@Field("lng") String lng,@Field("lat") String lat);

    //上班打卡
    @FormUrlEncoded
    @POST("user/info/in/click")
    Observable<String> workApi(@Field("token") String token);

    //修改手机号验证
    @FormUrlEncoded
    @POST("driver/editPhoneCheckVcode")
    Observable<String> phoneOldApi(@Field("phone") String phone,@Field("vcode") String code);

    //修改手机号或者修改密码
    @FormUrlEncoded
    @PUT("driver/confirmEdit")
    Observable<String> phoneOrPassApi(@FieldMap Map<String, Object> params);

    //查询司机名下车辆
    @GET("car/carMaster/selectAllCarByUserId")
    Observable<HaiBaseData<List<TruckEntry>>> queryTruckApi(@Query("token") String token);

    //查询车辆详细信息
    @GET("car/carMaster/selectCarMasterVoById")
    Observable<TruckDetailEntry> queryTruckDetailApi(@Query("carId") String carId);

    //查询司机驾驶证
    @GET("driver/queryMyDriverImage")
    Observable<String> queryDriverCardApi(@Query("token") String token);

    //修改驾驶证
    @FormUrlEncoded
    @PUT("driver/updateDriverImageByUserId")
    Observable<String> changeDriveIdentify(@Field("token") String token,@Field("driverCardFront") String driverCardFront,@Field("driverCardBack") String driverCardBack);


    //根据线路查询品类价格规格
    @GET("reception/waybill/linemaster")
    Observable<GoodsTypePriceEntry> queryTypePriceInfo(@Query("token") String token, @Query("lineMasterId") String lineMasterId);

    //修改订单
    @FormUrlEncoded
    @POST("reception/waybill/upd")
    Observable<String> changeOrderApi(@FieldMap Map<String, Object> params);

    //司机总评价
    @GET("reception/orderComment/selectComment")
    Observable<HaiBaseData<TotalEvaluateEntry>> queryTotalEvaluateApi(@Query("token") String token);

    //司机运单评价
    @GET("reception/orderComment/selectCommentByUserId")
    Observable<HaiBaseData<List<EveryEvaluateEntry>>> queryEveryEvaluateApi(@Query("token") String token, @Query(OtherConstants.PAGE) int pageNum, @Query(OtherConstants.SIZE) int pageSize);

    //修改车辆信息
    @FormUrlEncoded
    @POST("logistics/carApply/updateApply")
    Observable<String> changeTruckApi(@FieldMap Map<String, Object> params);

}
