package com.zx.haijixing.share.service;

import com.zx.haijixing.driver.entry.BannerEntry;
import com.zx.haijixing.driver.entry.NewsEntry;
import com.zx.haijixing.logistics.entry.FeeStatisticsEntry;
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

/**
 *
 *@作者 zx
 *@创建日期 2019/6/17 14:42
 *@描述 公用接口服务
 */
public interface ShareApiService {

    //新闻列表
    @GET("news/newList")
    Observable<NewsEntry> newsList(@Query(OtherConstants.PAGE) int num, @Query(OtherConstants.SIZE) int size);

    //新闻详情
    @GET("news/selectNewById")
    Observable<HaiBaseData<NewsEntry.NewsData>> newsOne(@Query("token") String token,@Query("newId") String newId);

    //版本
    @GET("version/newVersion")
    Observable<HaiBaseData<VersionEntry>> versionApi();

    //轮播
    @GET("banner/allBanners")
    Observable<BannerEntry> bannerApi();

    //消息中心
    @GET("notice/page/list")
    Observable<HaiBaseData<List<NotifyEntry>>> notifyApi(@Query("token") String token, @Query(OtherConstants.PAGE) String pageNum, @Query(OtherConstants.SIZE) String pageSize);


    //二维码运单信息
    @GET("logistics/common/orderInfoByQR")
    Observable<String> qrOrderApi(@Query("token") String token, @Query("code") String code);

    //今日数据统计
    @FormUrlEncoded
    @POST("logistics/reports/count/day/num")
    Observable<String> todayDataApi(@Field("token") String token);

    //运单统计
    @FormUrlEncoded
    @POST("logistics/reports/count/waybill/list")
    Observable<HaiBaseData<FeeStatisticsEntry>> orderStatisticsApi(@FieldMap Map<String,Object> params);

    //司机列表
    @FormUrlEncoded
    @POST("logistics/reports/driver/list")
    Observable<String> driverListApi(@Field("token") String token);

    //运费统计
    @FormUrlEncoded
    @POST("logistics/reports/price/waybill/list")
    Observable<HaiBaseData<FeeStatisticsEntry>> feeStatisticsApi(@FieldMap Map<String,Object> params);

    //到付运费统计
    @FormUrlEncoded
    @POST("logistics/reports/topay/price/waybill/list")
    Observable<HaiBaseData<FeeStatisticsEntry>> feeReceivedStatisticsApi(@FieldMap Map<String,Object> params);
}
