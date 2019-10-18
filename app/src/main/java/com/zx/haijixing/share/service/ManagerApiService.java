package com.zx.haijixing.share.service;


import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 *
 *@作者 zx
 *@创建日期 2019/6/17 14:42
 *@描述 管理员接口服务
 */
public interface ManagerApiService {
    @GET("api/")
    Observable<String> weatherApi(@Query("version") String version,@Query("cityId") String city,@Query("appid") String id,@Query("appsecret") String secret);
}
