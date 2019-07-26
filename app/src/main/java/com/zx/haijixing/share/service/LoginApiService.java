package com.zx.haijixing.share.service;

import com.zx.haijixing.login.entry.LoginEntry;
import com.zx.haijixing.share.base.HaiBaseData;
import com.zx.haijixing.login.entry.TruckTypeEntry;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;

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
    Observable<HaiBaseData<LoginEntry>> loginApiPass(@Field("phone") String phone, @Field("password") String password);

    //登录验证码
    @FormUrlEncoded
    @POST("app/loginBySmsCode")
    Observable<HaiBaseData<LoginEntry>> loginApiCode(@Field("phone") String phone, @Field("smsCode") String code);

    //忘记密码
    @FormUrlEncoded
    @POST("app/loginBySmsCode")
    Observable<String> forgetPass(@Field("phone") String phone, @Field("smsCode") String code,@Field("password") String password);

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

    //选择车辆类型
    @GET("logistics/carType/selectAllCarType")
    Observable<HaiBaseData<List<TruckTypeEntry>>> truckTypeApi();

    ///添加车辆
    @FormUrlEncoded
    @POST("logistics/carApply/addApply")
    Observable<String> truckApplyApi(@FieldMap Map<String,String> params);

    //修改上传头像@Field("token") String token,
    @FormUrlEncoded
    @PUT("driver/updateDriverHeadImageByUserId")
    Observable<String> changeUserHead(@Field("headImg") String headImg);

}
