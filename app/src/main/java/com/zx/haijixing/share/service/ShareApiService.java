package com.zx.haijixing.share.service;

import com.zx.haijixing.driver.entry.BannerEntry;
import com.zx.haijixing.driver.entry.NewsEntry;
import com.zx.haijixing.logistics.entry.CompanyEntry;
import com.zx.haijixing.logistics.entry.DriverEntry;
import com.zx.haijixing.logistics.entry.DriverWordEntry;
import com.zx.haijixing.logistics.entry.FeeStatisticsEntry;
import com.zx.haijixing.logistics.entry.RunTableEntry;
import com.zx.haijixing.share.OtherConstants;
import com.zx.haijixing.share.base.HaiBaseData;
import com.zx.haijixing.share.pub.entry.NotifyEntry;
import com.zx.haijixing.share.pub.entry.VersionEntry;


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
 *@创建日期 2019/6/17 14:42
 *@描述 公用接口服务
 */
public interface ShareApiService {

    //新闻列表
    @GET("api/news/newList")
    Observable<NewsEntry> newsList(@QueryMap Map<String,String> params);

    //新闻详情
    @GET("api/news/selectNewById")
    Observable<HaiBaseData<NewsEntry.NewsData>> newsOne(@QueryMap Map<String,String> params);

    //版本
    @GET("open/version/newVersion")
    Observable<HaiBaseData<VersionEntry>> versionApi();

    //轮播
    @GET("api/banner/allBanners")
    Observable<BannerEntry> bannerApi(@QueryMap Map<String,String> params);

    //消息中心
    @GET("api/notice/page/list")
    Observable<HaiBaseData<List<NotifyEntry>>> notifyApi(@QueryMap Map<String,String> params);


    //二维码运单信息
    @GET("api/logistics/common/orderInfoByQR")
    Observable<String> qrOrderApi(@QueryMap Map<String,String> params);

    //今日数据统计
    @FormUrlEncoded
    @POST("api/logistics/reports/count/day/num")
    Observable<String> todayDataApi(@FieldMap Map<String,String> params);

    //运单统计
    @FormUrlEncoded
    @POST("api/logistics/reports/count/waybill/list")
    Observable<HaiBaseData<FeeStatisticsEntry>> orderStatisticsApi(@FieldMap Map<String,String> params);

    //司机列表
    @FormUrlEncoded
    @POST("api/logistics/reports/driver/list")
    Observable<HaiBaseData<List<DriverEntry>>> driverListApi(@FieldMap Map<String,String> params);

    //运费统计
    @FormUrlEncoded
    @POST("api/logistics/reports/price/waybill/list")
    Observable<HaiBaseData<FeeStatisticsEntry>> feeStatisticsApi(@FieldMap Map<String,String> params);

    //到付运费统计
    @FormUrlEncoded
    @POST("api/logistics/reports/topay/price/waybill/list")
    Observable<HaiBaseData<FeeStatisticsEntry>> feeReceivedStatisticsApi(@FieldMap Map<String,String> params);

    //物流公司
    @GET("api/reception/orderComment/selectCompayByDeptId")
    Observable<HaiBaseData<List<CompanyEntry>>> companyApi(@QueryMap Map<String,String> params);

    //运营总表
    @GET("/api/logistics/reports/selectWaybillOrderCount")
    Observable<HaiBaseData<List<RunTableEntry>>> runTableApi(@QueryMap Map<String,String> params);

    //司机评价
    @GET("api/reception/orderComment/driverCommentScore")
    Observable<HaiBaseData<List<DriverWordEntry>>> driverWordApi(@QueryMap Map<String,String> params);

    //支付选择
    @GET("open/app/setting")
    Observable<String> payWayShowApi();
}
