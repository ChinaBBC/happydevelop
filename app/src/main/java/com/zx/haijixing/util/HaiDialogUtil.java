package com.zx.haijixing.util;

import android.app.AlertDialog;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

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

    //弹出打卡
    public static CommonDialogFragment showClock(FragmentManager fragmentManager, View.OnClickListener clickListener){
        CommonDialogFragment progressDialog = CommonDialogFragment.newInstance(context -> {
            View view = LayoutInflater.from(context).inflate(R.layout.dialog_clock_in, null);
            TextView yes = view.findViewById(R.id.dialog_clock_yes);
            TextView no = view.findViewById(R.id.dialog_clock_no);
            yes.setOnClickListener(clickListener);
            no.setOnClickListener(clickListener);
            AlertDialog.Builder builder = new AlertDialog.Builder(context,ROB_RED_THEME);
            return builder.setView(view).create();
        }, true, null);
        progressDialog.show(fragmentManager, "clock");
        return progressDialog;
    }
    //使用导航
    public static CommonDialogFragment showLocate(FragmentManager fragmentManager, View.OnClickListener clickListener){
        CommonDialogFragment locateDialog = CommonDialogFragment.newInstance(context -> {
            View view = LayoutInflater.from(context).inflate(R.layout.dialog_locate_tip, null);
            TextView yes = view.findViewById(R.id.dialog_locate_yes);
            TextView no = view.findViewById(R.id.dialog_locate_no);
            yes.setOnClickListener(clickListener);
            no.setOnClickListener(clickListener);
            AlertDialog.Builder builder = new AlertDialog.Builder(context,ROB_RED_THEME);
            return builder.setView(view).create();
        }, true, null);
        locateDialog.show(fragmentManager, "locate");
        return locateDialog;
    }
    //接单提示
    public static CommonDialogFragment showReceive(FragmentManager fragmentManager, View.OnClickListener clickListener,String totals){
        CommonDialogFragment receiveDialog = CommonDialogFragment.newInstance(context -> {
            View view = LayoutInflater.from(context).inflate(R.layout.dialog_receive_tip, null);
            TextView yes = view.findViewById(R.id.dialog_receive_yes);
            TextView no = view.findViewById(R.id.dialog_receive_no);
            TextView total = view.findViewById(R.id.dialog_receive_total);
            total.setText(totals);
            yes.setOnClickListener(clickListener);
            no.setOnClickListener(clickListener);
            AlertDialog.Builder builder = new AlertDialog.Builder(context,ROB_RED_THEME);
            return builder.setView(view).create();
        }, true, null);
        receiveDialog.show(fragmentManager, "receive");
        return receiveDialog;
    }
    //支付选择
    public static CommonDialogFragment showPay(FragmentManager fragmentManager, View.OnClickListener clickListener,PayMoneyResultListener payMoneyResultListener){
        CommonDialogFragment payDialog = CommonDialogFragment.newInstance(context -> {
            View view = LayoutInflater.from(context).inflate(R.layout.dialog_pay_tip, null);
            TextView yes = view.findViewById(R.id.dialog_pay_yes);
            TextView no = view.findViewById(R.id.dialog_pay_no);
            TextView crash = view.findViewById(R.id.dialog_pay_crash);
            TextView crashImg = view.findViewById(R.id.dialog_pay_crash_img);
            TextView online = view.findViewById(R.id.dialog_pay_online);
            TextView onlineImg = view.findViewById(R.id.dialog_pay_online_img);
            EditText money = view.findViewById(R.id.dialog_pay_money);
            yes.setOnClickListener(clickListener);
            no.setOnClickListener(clickListener);
            crash.setOnClickListener(clickListener);
            crashImg.setOnClickListener(clickListener);
            online.setOnClickListener(clickListener);
            onlineImg.setOnClickListener(clickListener);
            payMoneyResultListener.payResult(money.getText().toString());
            AlertDialog.Builder builder = new AlertDialog.Builder(context,ROB_RED_THEME);
            return builder.setView(view).create();
        }, true, null);
        payDialog.show(fragmentManager, "pay");
        return payDialog;
    }

    public interface PayMoneyResultListener{
        void payResult(String result);
    }
}
