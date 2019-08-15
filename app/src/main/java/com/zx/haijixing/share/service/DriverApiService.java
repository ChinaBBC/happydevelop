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
    @GET("api/reception/waybill/params/list")
    Observable<HaiBaseData<OrderTotalEntry>> orderLists(@QueryMap Map<String, String> params);

    //接单
    @FormUrlEncoded
    @POST("api/reception/waybill/receipt")
    Observable<String> receiveOrders(@FieldMap Map<String, String> params);

    //订单详情
    @GET("api/reception/waybill/details")
    Observable<HaiBaseData<OrderDetailEntry>> orderDetails(@QueryMap Map<String, String> params);

    //出发
    @FormUrlEncoded
    @POST("api/reception/waybill/batchdepart")
    Observable<String> sendApi(@FieldMap Map<String, String> params);

    //完成
    @FormUrlEncoded
    @POST("api/reception/waybill/done")
    Observable<String> completeApi(@FieldMap Map<String, String> params);

    //收款
    @FormUrlEncoded
    @POST("api/reception/waybill/makeprice")
    Observable<String> receiveMoneyAPi(@FieldMap Map<String, String> params);

    //订单打印
    @GET("api/reception/waybill/dadan")
    Observable<HaiBaseData<PrintEntry>> printOrderApi(@QueryMap Map<String, String> params);

    //改变打单状态
    @FormUrlEncoded
    @POST("api/reception/waybill/upd/dadanflag")
    Observable<String> changePrint(@FieldMap Map<String, String> params);

    //司机班次信息、车辆及路线
    @FormUrlEncoded
    @POST("api/reception/waybill/driver/bkki")
    Observable<HaiBaseData<List<DriverClassEntry>>> driverClass(@FieldMap Map<String, String> params);

    //修改运单价格
    @FormUrlEncoded
    @POST("api/reception/waybill/upd/realprice")
    Observable<String> changePrice(@FieldMap Map<String, String> params);


    //位置上传
    @FormUrlEncoded
    @POST("api/reception/waybill/insertWayPoint")
    Observable<String> uploadLocate(@FieldMap Map<String, String> params);

    //上班打卡
    @FormUrlEncoded
    @POST("api/user/info/in/click")
    Observable<String> workApi(@FieldMap Map<String, String> params);

    //检查打卡状态
    @FormUrlEncoded
    @POST("api/user/info/in/click/status")
    Observable<String> workStatusApi(@FieldMap Map<String, String> params);

    //修改手机号验证
    @FormUrlEncoded
    @POST("api/driver/editPhoneCheckVcode")
    Observable<String> phoneOldApi(@FieldMap Map<String, String> params);

    //修改手机号或者修改密码
    @FormUrlEncoded
    @PUT("api/driver/confirmEdit")
    Observable<String> phoneOrPassApi(@FieldMap Map<String, String> params);

    //查询司机名下车辆
    @GET("api/car/carMaster/selectAllCarByUserId")
    Observable<HaiBaseData<List<TruckEntry>>> queryTruckApi(@QueryMap Map<String, String> params);

    //查询车辆详细信息
    @GET("api/car/carMaster/selectCarMasterVoById")
    Observable<TruckDetailEntry> queryTruckDetailApi(@QueryMap Map<String, String> params);

    //查询司机驾驶证
    @GET("api/driver/queryMyDriverImage")
    Observable<String> queryDriverCardApi(@QueryMap Map<String, String> params);

    //修改驾驶证
    @FormUrlEncoded
    @PUT("api/driver/updateDriverImageByUserId")
    Observable<String> changeDriveIdentify(@FieldMap Map<String, String> params);


    //根据线路查询品类价格规格
    @GET("api/reception/waybill/linemaster")
    Observable<GoodsTypePriceEntry> queryTypePriceInfo(@QueryMap Map<String, String> params);

    //修改订单
    @FormUrlEncoded
    @POST("api/reception/waybill/upd")
    Observable<String> changeOrderApi(@FieldMap Map<String, String> params);

    //司机总评价
    @GET("api/reception/orderComment/selectComment")
    Observable<HaiBaseData<TotalEvaluateEntry>> queryTotalEvaluateApi(@QueryMap Map<String, String> params);

    //司机运单评价
    @GET("api/reception/orderComment/selectCommentByUserId")
    Observable<HaiBaseData<List<EveryEvaluateEntry>>> queryEveryEvaluateApi(@QueryMap Map<String, String> params);

    //修改车辆信息
    @FormUrlEncoded
    @POST("api/logistics/carApply/updateApply")
    Observable<String> changeTruckApi(@FieldMap Map<String, String> params);

    //客服电话/
    @GET("api/logistics/common/service/phone")
    Observable<String> servicePhoneApi(@QueryMap Map<String, String> params);

}
