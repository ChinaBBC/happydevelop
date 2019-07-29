package com.zx.haijixing.util;

import android.app.AlertDialog;
import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.contrarywind.adapter.WheelAdapter;
import com.contrarywind.listener.OnItemSelectedListener;
import com.contrarywind.view.WheelView;
import com.zx.haijixing.R;
import com.zx.haijixing.driver.adapter.BluetoothDataAdapter;
import com.zx.haijixing.login.activity.TruckAdapter;

import java.util.concurrent.atomic.AtomicInteger;

import zx.com.skytool.ZxStringUtil;

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
        }, false, null);
        receiveDialog.show(fragmentManager, "receive");
        return receiveDialog;
    }
    //支付选择
    public static CommonDialogFragment showPay(FragmentManager fragmentManager, View.OnClickListener clickListener,TruckResultListener truckResultListener){
        CommonDialogFragment payDialog = CommonDialogFragment.newInstance(context -> {
            View view = LayoutInflater.from(context).inflate(R.layout.dialog_pay_tip, null);
            TextView yes = view.findViewById(R.id.dialog_pay_yes);
            TextView no = view.findViewById(R.id.dialog_pay_no);
            ImageView crash = view.findViewById(R.id.dialog_pay_crash);
            TextView crashImg = view.findViewById(R.id.dialog_pay_crash_img);
            ImageView online = view.findViewById(R.id.dialog_pay_online);
            TextView onlineImg = view.findViewById(R.id.dialog_pay_online_img);
            //EditText money = view.findViewById(R.id.dialog_pay_money);
            yes.setOnClickListener(clickListener);
            no.setOnClickListener(clickListener);

            crash.setOnClickListener(v -> {
                crash.setImageResource(R.mipmap.select_yes_solid);
                crashImg.setTextColor(Color.parseColor("#30703f"));
                online.setImageResource(R.mipmap.select_no);
                onlineImg.setTextColor(Color.parseColor("#999999"));
                truckResultListener.truckResult(2);
            });
            crashImg.setOnClickListener(v -> {
                crash.setImageResource(R.mipmap.select_yes_solid);
                crashImg.setTextColor(Color.parseColor("#30703f"));
                online.setImageResource(R.mipmap.select_no);
                onlineImg.setTextColor(Color.parseColor("#999999"));
                truckResultListener.truckResult(2);
            });
            online.setOnClickListener(v -> {
                crash.setImageResource(R.mipmap.select_no);
                crashImg.setTextColor(Color.parseColor("#999999"));
                online.setImageResource(R.mipmap.select_yes_solid);
                onlineImg.setTextColor(Color.parseColor("#30703f"));
                truckResultListener.truckResult(1);
            });
            onlineImg.setOnClickListener(v -> {
                crash.setImageResource(R.mipmap.select_no);
                crashImg.setTextColor(Color.parseColor("#999999"));
                online.setImageResource(R.mipmap.select_yes_solid);
                onlineImg.setTextColor(Color.parseColor("#30703f"));
                truckResultListener.truckResult(1);
            });
            //payMoneyResultListener.payResult(money.getText().toString());
            AlertDialog.Builder builder = new AlertDialog.Builder(context,ROB_RED_THEME);
            return builder.setView(view).create();
        }, false, null);
        payDialog.show(fragmentManager, "pay");
        return payDialog;
    }

    //更新提示以及其他提示
    public static CommonDialogFragment showUpdate(FragmentManager fragmentManager,String contents,View.OnClickListener clickListener){
        CommonDialogFragment updateDialog = CommonDialogFragment.newInstance(context -> {
            View view = LayoutInflater.from(context).inflate(R.layout.dialog_update_tip, null);
            TextView yes = view.findViewById(R.id.dialog_update_yes);
            TextView no = view.findViewById(R.id.dialog_update_no);
            TextView content = view.findViewById(R.id.dialog_update_content);
            if (!ZxStringUtil.isEmpty(contents))
                content.setText(contents);
            yes.setOnClickListener(clickListener);
            no.setOnClickListener(clickListener);
            AlertDialog.Builder builder = new AlertDialog.Builder(context,ROB_RED_THEME);
            return builder.setView(view).create();
        }, false, null);
        updateDialog.show(fragmentManager, "receive");
        return updateDialog;
    }

    //蓝牙选择
    public static CommonDialogFragment showBluetooth(final FragmentManager manager, BluetoothDataAdapter bluetoothAdapter,TruckResultListener truckResultListener, final AdapterView.OnItemClickListener onItemClickListener){
        CommonDialogFragment bluetooth = CommonDialogFragment.newInstance(context -> {
            View view = LayoutInflater.from(context).inflate(R.layout.dialog_bluetooth, null);
            final ListView listView = view.findViewById(R.id.bluetooth_data);
            final ImageView close = view.findViewById(R.id.bluetooth_close);
            final TextView title = view.findViewById(R.id.bluetooth_title);
            listView.setAdapter(bluetoothAdapter);
            listView.setOnItemClickListener(onItemClickListener);
            close.setOnClickListener(v -> truckResultListener.truckResult(-3));
            title.setOnClickListener(v -> truckResultListener.truckResult(-3));
            AlertDialog.Builder builder = new AlertDialog.Builder(context, ROB_RED_THEME);
            return builder.setView(view)
                    .create();
        }, true, true, null);
        bluetooth.show(manager,"blue");
        return bluetooth;
    }

    //车型选择/班次选择
    public static CommonDialogFragment showTruck(FragmentManager fragmentManager, WheelAdapter truckAdapter, TruckResultListener truckResultListener){
        CommonDialogFragment truckDialog = CommonDialogFragment.newInstance(context -> {
            View view = LayoutInflater.from(context).inflate(R.layout.dialog_truck_type, null);
            WheelView wheelView = view.findViewById(R.id.dialog_truck_wheel);
            TextView sure = view.findViewById(R.id.dialog_truck_sure);
            TextView cancel = view.findViewById(R.id.dialog_truck_cancel);
            wheelView.setCyclic(false);
            wheelView.setAdapter(truckAdapter);
            sure.setOnClickListener(v -> truckResultListener.truckResult(-1));
            cancel.setOnClickListener(v -> truckResultListener.truckResult(-2) );
            wheelView.setOnItemSelectedListener(index -> truckResultListener.truckResult(index));
            AlertDialog.Builder builder = new AlertDialog.Builder(context,ROB_RED_THEME);
            return builder.setView(view).create();
        }, false, null);
        truckDialog.show(fragmentManager, "truck");
        return truckDialog;
    }

    //新增修改班次
    public static CommonDialogFragment showAddEditor(FragmentManager fragmentManager, TruckAdapter truckAdapter,TruckResultListener truckResultListener){
        CommonDialogFragment addEditorDialog = CommonDialogFragment.newInstance(context -> {
            View view = LayoutInflater.from(context).inflate(R.layout.dialog_add_editor_classes, null);
            TextView sure = view.findViewById(R.id.add_editor_sure);
            TextView cancel = view.findViewById(R.id.add_editor_cancel);

            //sure.setOnClickListener(v -> truckResultListener.truckResult(-1));
            //cancel.setOnClickListener(v -> truckResultListener.truckResult(-2) );
            AlertDialog.Builder builder = new AlertDialog.Builder(context,ROB_RED_THEME);
            return builder.setView(view).create();
        }, true, null);
        addEditorDialog.show(fragmentManager, "add editor");
        return addEditorDialog;
    }
    //修改运费
    public static CommonDialogFragment showChangeMoney(FragmentManager fragmentManager,PayMoneyResultListener payMoneyResultListener,String money){
        CommonDialogFragment changeMoneyDialog = CommonDialogFragment.newInstance(context -> {
            View view = LayoutInflater.from(context).inflate(R.layout.dialog_change_money, null);
            TextView sure = view.findViewById(R.id.change_money_sure);
            TextView cancel = view.findViewById(R.id.change_money_cancel);
            TextView input = view.findViewById(R.id.change_money_input);
            input.setText(money);

            sure.setOnClickListener(v -> payMoneyResultListener.payResult(input.getText().toString()));
            cancel.setOnClickListener(v -> payMoneyResultListener.payResult("cancel") );
            AlertDialog.Builder builder = new AlertDialog.Builder(context,ROB_RED_THEME);
            return builder.setView(view).create();
        }, false, null);
        changeMoneyDialog.show(fragmentManager, "change price");
        return changeMoneyDialog;
    }
    public interface PayMoneyResultListener{
        void payResult(String result);
    }
    public interface TruckResultListener{
        void truckResult(int index);
    }
}
