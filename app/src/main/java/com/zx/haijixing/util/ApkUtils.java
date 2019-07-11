package com.zx.haijixing.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.util.Log;

import java.io.File;
import java.io.IOException;

import zx.com.skytool.ZxLogUtil;

/**
 *
 *@作者 zx
 *@创建日期 2019/7/11 9:08
 *@描述 app更新
 */
public class ApkUtils {

    public static void installAPk(Context context, String apkFile) {

        File file = new File(apkFile);
        if (file.exists()){
            Intent installAPKIntent = getApkInStallIntent(context, file);
            context.startActivity(installAPKIntent);
        }else {
            ZxLogUtil.logError("apk file not exists");
        }


    }


    public static Intent getApkInStallIntent(Context context, File apkFile) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            Uri uri = FileProvider.getUriForFile(context, context.getPackageName() + ".update.provider", apkFile);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(uri, "application/vnd.android.package-archive");
        } else {
            Uri uri = getApkUri(apkFile);
            intent.setDataAndType(uri, "application/vnd.android.package-archive");
        }
        return intent;
    }


    private static Uri getApkUri(File apkFile) {
        Log.d("apkutil", apkFile.toString());
        //如果没有设置 SDCard 写权限，或者没有 SDCard,apk 文件保存在内存中，需要授予权限才能安装
        try {
            String[] command = {"chmod", "777", apkFile.toString()};
            ProcessBuilder builder = new ProcessBuilder(command);
            builder.start();
        } catch (IOException ignored) {
        }
        Uri uri = Uri.fromFile(apkFile);
        Log.d("apkutil", uri.toString());
        return uri;
    }

}
