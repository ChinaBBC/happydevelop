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

    private static final String TEL_REGEX = "^[1][3-9][0-9]{9}$";
    private static final String IDENTIFY_CARD = "^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$";
    private static final String BANK_CARD = "^([1-9]{1})(\\d{14}|\\d{18})$";
    public static boolean checkPhone(String phone){
        return phone.matches(TEL_REGEX);
    }
    public static boolean checkIdentify(String identify){
        return identify.matches(IDENTIFY_CARD);
    }
    public static boolean checkBank(String bank){
        return bank.matches(BANK_CARD);
    }

}
