package com.zx.haijixing.share.service;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 *
 *@作者 zx
 *@创建日期 2019/7/11 10:31
 *@描述 登录接口服务
 */
public interface LoginApiService {
    //登录密码
    @FormUrlEncoded
    @POST("app/loginByPassword")
    Observable<String> loginApiPass(@Field("phone") String phone, @Field("password") String password);

    //登录验证码
    @FormUrlEncoded
    @POST("app/loginBySmsCode")
    Observable<String> loginApiCode(@Field("phone") String phone, @Field("smsCode") String code);

    //注册基本信息
    @FormUrlEncoded
    @POST("driver/normalInfo")
    Observable<String> registerBase(@FieldMap Map<String,String> params);

    //发送验证码
    @FormUrlEncoded
    @POST("app/appSendSms")
    Observable<String> sendRegisterCode(@Field("phone") String phone, @Field("type") int type);

    //注册基本信息
    @FormUrlEncoded
    @POST("driver/driverCard")
    Observable<String> drivingCard(@FieldMap Map<String,String> params);

}
