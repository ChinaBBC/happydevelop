package com.zx.haijixing.share.service;

import com.zx.haijixing.driver.entry.NewsEntry;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 *
 *@作者 zx
 *@创建日期 2019/6/17 14:41
 *@描述 司机接口服务
 */
public interface DriverApiService {
    //登录
    @FormUrlEncoded
    @POST("hrsys/meterReader/api/login")
    Observable<String> loginApi(@Field("phone") String phone, @Field("verificationCode") String code);

    //1首页轮播2资讯轮播
    @GET("hrsys/banner/api/bannerList")
    Observable<NewsEntry> bannerApi(@Query("type") String str);//1首页轮播2资讯轮播

    @GET("commons/ios/index/image")
    Observable<String> firstBg();


}
