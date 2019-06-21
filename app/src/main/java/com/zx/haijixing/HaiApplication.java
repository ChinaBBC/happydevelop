package com.zx.haijixing;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.multidex.MultiDex;

import com.alibaba.android.arouter.launcher.ARouter;
import com.allen.library.RxHttpUtils;
import com.allen.library.config.OkHttpConfig;
import com.allen.library.cookie.store.SPCookieStore;
import com.tencent.bugly.crashreport.CrashReport;

import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;
import zx.com.skytool.BackgroundTasks;
import zx.com.skytool.SkyTool;

/**
 *
 *@作者 zx
 *@创建日期 2019/6/14 10:46
 *@描述 
 */
public class HaiApplication extends Application {
    public static Context haiApp;
    private Map<String,Object> headerMaps = new HashMap<>();
    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
        haiApp = getApplicationContext();
        SkyTool.init(this);
        BackgroundTasks.initInstance();
        configBugly();

        intARouter();
    }


    private void intARouter() {
        // 这两行必须写在init之前，否则这些配置在init过程中将无效
        if (BuildConfig.DEBUG){
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this); // 尽可能早，推荐在Application中初始化
    }

    private void configBugly() {
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(haiApp);
        strategy.setAppChannel(Build.MODEL);
        try {
            PackageInfo packageInfo = haiApp.getPackageManager().getPackageInfo(haiApp.getPackageName(), 0);
            strategy.setAppVersion(packageInfo.versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        CrashReport.initCrashReport(haiApp,BuildConfig.buglyId,true,strategy);
        configRxUtil();
    }

    public static Context getInstance(){
        if (haiApp == null){
            haiApp = new HaiApplication();
        }
        return haiApp;
    }
    //配置Rx工具
    private void configRxUtil(){
        OkHttpClient okHttpClient = new OkHttpConfig
                .Builder(this)
                //全局的请求头信息
                //.setHeaders(headerMaps)
                //开启缓存策略(默认false)
                //1、在有网络的时候，先去读缓存，缓存时间到了，再去访问网络获取数据；
                //2、在没有网络的时候，去读缓存中的数据。
                .setCache(true)
                //全局持久话cookie,保存到内存（new MemoryCookieStore()）或者保存到本地（new SPCookieStore(this)）
                //不设置的话，默认不对cookie做处理
                .setCookieType(new SPCookieStore(this))
                //可以添加自己的拦截器(比如使用自己熟悉三方的缓存库等等)
                //.setAddInterceptor(null)
                //全局ssl证书认证
                //1、信任所有证书,不安全有风险（默认信任所有证书）
                //.setSslSocketFactory()
                //2、使用预埋证书，校验服务端证书（自签名证书）
                //.setSslSocketFactory(cerInputStream)
                //3、使用bks证书和密码管理客户端证书（双向认证），使用预埋证书，校验服务端证书（自签名证书）
                //.setSslSocketFactory(bksInputStream,"123456",cerInputStream)
                //全局超时配置
                .setReadTimeout(10)
                //全局超时配置
                .setWriteTimeout(10)
                //全局超时配置
                .setConnectTimeout(10)
                //全局是否打开请求log日志
                .setDebug(true)
                .build();

        RxHttpUtils
                .getInstance()
                .init(this)
                .config()
                //使用自定义factory的用法
                //.setCallAdapterFactory(RxJava2CallAdapterFactory.create())
                //.setConverterFactory(ScalarsConverterFactory.create(),GsonConverterFactory.create(GsonAdapter.buildGson()))
                //配置全局baseUrl
                .setBaseUrl(BuildConfig.homeUrl)
                //开启全局配置
                .setOkClient(okHttpClient);

    }
}
