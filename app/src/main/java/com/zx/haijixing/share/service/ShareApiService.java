package com.zx.haijixing.share.service;

import com.zx.haijixing.driver.entry.BannerEntry;
import com.zx.haijixing.driver.entry.NewsEntry;
import com.zx.haijixing.share.base.HaiBaseData;
import com.zx.haijixing.share.pub.entry.VersionEntry;


import io.reactivex.Observable;
import retrofit2.http.GET;
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
    Observable<NewsEntry> newsList(@Query("pageNum") int num, @Query("pageSize") int size);

    //新闻详情
    @GET("news/selectNewById")
    Observable<HaiBaseData<NewsEntry.NewsData>> newsOne(@Query("newId") String newId);

    //版本
    @GET("version/newVersion")
    Observable<HaiBaseData<VersionEntry>> versionApi();

    //轮播
    @GET("banner/allBanners")
    Observable<BannerEntry> bannerApi();

}
