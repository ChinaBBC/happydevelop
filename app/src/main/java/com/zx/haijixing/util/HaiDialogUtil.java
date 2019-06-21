package com.zx.haijixing.util;

import android.app.AlertDialog;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;

import com.zx.haijixing.R;

/**
 *
 *@作者 zx
 *@创建日期 2019/6/21 15:08
 *@描述 弹框
 */
public final class HaiDialogUtil {


    private static final int ROB_RED_THEME = R.style.Hai_AlertDialog;
    //数据加载动画
    public static CommonDialogFragment showProgress(FragmentManager fragmentManager){
        CommonDialogFragment progressDialog = CommonDialogFragment.newInstance(context -> {
            View view = LayoutInflater.from(context).inflate(R.layout.dialog_loading_progress, null);
            AlertDialog.Builder builder = new AlertDialog.Builder(context,ROB_RED_THEME);
            return builder.setView(view).create();
        }, false, null);
        progressDialog.show(fragmentManager, "progress");
        return progressDialog;
    }

}
