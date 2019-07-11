package com.zx.haijixing.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.allen.library.utils.MD5;

/**
 *
 *@作者 zx
 *@创建日期 2019/7/8 9:24
 *@描述 工具类
 */
public final class HaiTool {
    /**
     * 获取应用版本名称
     * @param context
     * @return
     */
    public static String packageName(Context context) {
        PackageManager manager = context.getPackageManager();
        //BaseApplication.getInstance().getPackageManager()
        String name = null;
        try {
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            name = info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return name;
    }
    /**
     * 获取应用版本名称
     * @param context
     * @return
     */
    public static int packageCode(Context context) {
        PackageManager manager = context.getPackageManager();
        int code = 0;
        try {
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            code = info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return code;
    }

    public static String md5Method(String word){
        return  MD5.EncoderByMd5(word);
    }
}
