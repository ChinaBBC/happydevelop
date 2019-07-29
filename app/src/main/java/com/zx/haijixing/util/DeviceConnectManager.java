package com.zx.haijixing.util;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.tools.io.BluetoothPort;
import com.tools.io.PortManager;
import com.zx.haijixing.HaiApplication;
import com.zx.haijixing.R;

import java.io.IOException;
import java.util.Vector;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import zx.com.skytool.ZxLogUtil;

/**
 * Created by Administrator
 *
 * @author 猿史森林
 *         Time 2017/8/2
 */
public class DeviceConnectManager {

    public PortManager mPort;

    private static final String TAG = DeviceConnectManager.class.getSimpleName();

    private String macAddress;


    private static DeviceConnectManager deviceConnectManager = new DeviceConnectManager();

    private boolean isOpenPort;
    /**
     * ESC查询打印机实时状态指令
     */
    private byte[] esc = {0x10, 0x04, 0x02};

    /**
     * ESC查询打印机实时状态 缺纸状态
     */
    private static final int ESC_STATE_PAPER_ERR = 0x20;

    /**
     * ESC指令查询打印机实时状态 打印机开盖状态
     */
    private static final int ESC_STATE_COVER_OPEN = 0x04;

    /**
     * ESC指令查询打印机实时状态 打印机报错状态
     */
    private static final int ESC_STATE_ERR_OCCURS = 0x40;

    /**
     * TSC查询打印机状态指令
     */
    private byte[] tsc = {0x1b, '!', '?'};

    /**
     * TSC指令查询打印机实时状态 打印机缺纸状态
     */
    private static final int TSC_STATE_PAPER_ERR = 0x04;

    /**
     * TSC指令查询打印机实时状态 打印机开盖状态
     */
    private static final int TSC_STATE_COVER_OPEN = 0x01;

    /**
     * TSC指令查询打印机实时状态 打印机出错状态
     */
    private static final int TSC_STATE_ERR_OCCURS = 0x80;

    private byte[] cpcl={0x1b,0x68};

    /**
     * CPCL指令查询打印机实时状态 打印机缺纸状态
     */
    private static final int CPCL_STATE_PAPER_ERR = 0x01;
    /**
     * CPCL指令查询打印机实时状态 打印机开盖状态
     */
    private static final int CPCL_STATE_COVER_OPEN = 0x02;

    private byte[] sendCommand;
    /**
     * 判断打印机所使用指令是否是ESC指令
     */
    private PrinterCommand currentPrinterCommand;
    public static final byte FLAG = 0x10;
    private static final int READ_DATA = 10000;
    private static final String READ_DATA_CNT = "read_data_cnt";
    private static final String READ_BUFFER_ARRAY = "read_buffer_array";
    public static final String ACTION_CONN_STATE = "action_connect_state";
    public static final String ACTION_QUERY_PRINTER_STATE = "action_query_printer_state";
    public static final String STATE = "state";
    public static final int CONN_STATE_DISCONNECT = 0x90;
    public static final int CONN_STATE_CONNECTING = CONN_STATE_DISCONNECT << 1;
    public static final int CONN_STATE_FAILED = CONN_STATE_DISCONNECT << 2;
    public static final int CONN_STATE_CONNECTED = CONN_STATE_DISCONNECT << 3;
    public PrinterReader reader;

    public static DeviceConnectManager getDeviceConnectManagers() {
        return deviceConnectManager;
    }

    public DeviceConnectManager setMacAddress(String macAddress) {
        this.macAddress = macAddress;
        return this;
    }

    /**
     * 打开端口
     *
     * @return
     */
    public void openPort() {
        deviceConnectManager.isOpenPort = false;
        sendStateBroadcast(CONN_STATE_CONNECTING);
        mPort = new BluetoothPort(macAddress);
        isOpenPort = deviceConnectManager.mPort.openPort();
        ZxLogUtil.logError("<mPort>"+mPort+"<isOpenPort>"+isOpenPort);
        //端口打开成功后，检查连接打印机所使用的打印机指令ESC、TSC
        if (isOpenPort) {
            queryCommand();
        } else {
            if (this.mPort != null) {
                    this.mPort=null;
            }
            sendStateBroadcast(CONN_STATE_FAILED);
        }
    }

    /**
     * 查询当前连接打印机所使用打印机指令（ESC（EscCommand.java）、TSC（LabelCommand.java））
     */
    private void queryCommand() {
        //开启读取打印机返回数据线程
        reader = new PrinterReader();
        reader.start(); //读取数据线程
        //查询打印机所使用指令
        queryPrinterCommand(); //
    }

    /**
     * 获取端口打开状态（true 打开，false 未打开）
     *
     * @return
     */
    public boolean getConnState() {
        return isOpenPort;
    }

    /**
     * 关闭端口
     */
    public void closePort() {
        if (this.mPort != null) {
            reader.canceled();
           boolean b= this.mPort.closePort();
            if(b) {
                this.mPort=null;
                isOpenPort = false;
                currentPrinterCommand = null;
            }
        }
        sendStateBroadcast(CONN_STATE_DISCONNECT);
    }

    /**
     * 获取当前打印机指令
     *
     * @return PrinterCommand
     */
    public PrinterCommand getCurrentPrinterCommand() {
        return deviceConnectManager.currentPrinterCommand;
    }


    public void sendDataImmediately(final Vector<Byte> data) {
        if (this.mPort == null) {
            return;
        }
        try {
          //  Log.e(TAG, "data -> " + new String(com.gprinter.command.GpUtils.convertVectorByteTobytes(data), "gb2312"));
            this.mPort.writeDataImmediately(data, 0, data.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int readDataImmediately(byte[] buffer) throws IOException {
        return this.mPort.readData(buffer);
    }

    /**
     * 查询打印机当前使用的指令（TSC、ESC）
     */
    private void queryPrinterCommand() {

        //线程池添加任务
        ThreadPool.getInstantiation().addTask(() -> {
            //发送ESC查询打印机状态指令
            sendCommand = esc;
            Vector<Byte> data = new Vector<>(esc.length);
            for (int i = 0; i < esc.length; i++) {
                data.add(esc[i]);
            }
            sendDataImmediately(data); //发送esc数据
            //开启计时器，隔2000毫秒没有没返回值时发送TSC查询打印机状态指令
            final ThreadFactoryBuilder threadFactoryBuilder = new ThreadFactoryBuilder("Timer");
            final ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(1, threadFactoryBuilder);
            scheduledExecutorService.schedule(threadFactoryBuilder.newThread(() -> {
                if (currentPrinterCommand == null || currentPrinterCommand != PrinterCommand.ESC) {
                    Log.e(TAG, Thread.currentThread().getName());
                    //发送TSC查询打印机状态指令
                    sendCommand = tsc;
                    Vector<Byte> data1 = new Vector<>(tsc.length);
                    for (int i = 0; i < tsc.length; i++) {
                        data1.add(tsc[i]);
                    }
                    sendDataImmediately(data1);
                    //开启计时器，隔2000毫秒没有没返回值时发送CPCL查询打印机状态指令
                    scheduledExecutorService.schedule(threadFactoryBuilder.newThread(() -> {
                        if (currentPrinterCommand == null||(currentPrinterCommand != PrinterCommand.ESC&&currentPrinterCommand != PrinterCommand.TSC)) {
                            Log.e(TAG,Thread.currentThread().getName());
                            //发送CPCL查询打印机状态指令
                            sendCommand=cpcl;
                            Vector<Byte> data11 =new Vector<Byte>(cpcl.length);
                            for (int i=0;i<cpcl.length;i++){
                                data11.add(cpcl[i]);
                            }
                            sendDataImmediately(data11);
                            //开启计时器，隔2000毫秒打印机没有响应者停止读取打印机数据线程并且关闭端口
                            scheduledExecutorService.schedule(threadFactoryBuilder.newThread(() -> {
                                if(currentPrinterCommand==null){
                                    if (reader != null) {
                                        reader.canceled();
                                        mPort.closePort();
                                        isOpenPort = false;
                                        mPort=null;
                                        sendStateBroadcast(CONN_STATE_FAILED);
                                    }
                                }
                            }),2000,TimeUnit.MILLISECONDS);
                        }
                    }), 2000, TimeUnit.MILLISECONDS);
                }
            }), 2000, TimeUnit.MILLISECONDS);
        });
    }

    public class PrinterReader extends Thread {
        private boolean isRun = false;

        private byte[] buffer = new byte[100];

        public PrinterReader() {
            isRun = true;
        }

        @Override
        public void run() {
            try {
                while (isRun) {
                    //读取打印机返回信息
                    int len = readDataImmediately(buffer);
                    if (len > 0) {
                        Message message = Message.obtain();
                        message.what = READ_DATA;
                        Bundle bundle = new Bundle();
                        bundle.putInt(READ_DATA_CNT, len); //数据长度
                        bundle.putByteArray(READ_BUFFER_ARRAY, buffer); //数据
                        message.setData(bundle);
                        mHandler.sendMessage(message);
                    }
                }
            } catch (Exception e) {
                if (deviceConnectManager != null) {
                    closePort();
                }
            }
        }

        public void canceled() {
            isRun = false;
        }
    }
    public enum PrinterCommand {
        /**
         * ESC指令
         */
        ESC,
        /**
         * TSC指令
         */
        TSC,
        /**
         * CPCL指令
         */
        CPCL
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case READ_DATA:
                    int cnt = msg.getData().getInt(READ_DATA_CNT); //数据长度 >0;
                    byte[] buffer = msg.getData().getByteArray(READ_BUFFER_ARRAY);  //数据
                    //这里只对查询状态返回值做处理，其它返回值可参考编程手册来解析
                    if (buffer == null) {
                        return;
                    }
                    int result = judgeResponseType(buffer[0]); //数据右移
                    String status = HaiApplication.getInstance().getString(R.string.str_printer_conn_normal);
                    ZxLogUtil.logError("<<sendCommand>>>"+sendCommand+"<currentPrinterCommand>"+currentPrinterCommand);
                    if (sendCommand == esc) {
                        //设置当前打印机模式为ESC模式
                        if (currentPrinterCommand == null) {
                            currentPrinterCommand = PrinterCommand.ESC;
                            sendStateBroadcast(CONN_STATE_CONNECTED);
                        } else {//查询打印机状态
                            if (result == 0) {//打印机状态查询
                                Intent intent = new Intent(ACTION_QUERY_PRINTER_STATE);
                                HaiApplication.getInstance().sendBroadcast(intent);
                            } else if (result == 1) {//查询打印机实时状态
                                if ((buffer[0] & ESC_STATE_PAPER_ERR) > 0) {
                                    status += " "+HaiApplication.getInstance().getString(R.string.str_printer_out_of_paper);
                                }
                                if ((buffer[0] & ESC_STATE_COVER_OPEN) > 0) {
                                    status += " "+HaiApplication.getInstance().getString(R.string.str_printer_open_cover);
                                }
                                if ((buffer[0] & ESC_STATE_ERR_OCCURS) > 0) {
                                    status += " "+HaiApplication.getInstance().getString(R.string.str_printer_error);
                                }
                                System.out.println(HaiApplication.getInstance().getString(R.string.str_state) + status);
                                //String mode=HaiApplication.getInstance().getString(R.string.str_printer_printmode_esc);
                                //ZxToastUtil.centerToast(mode+" "+status);
                            }
                        }
                    } else if (sendCommand == tsc) {
                        //设置当前打印机模式为TSC模式
                        if (currentPrinterCommand == null) {
                            currentPrinterCommand = PrinterCommand.TSC;
                            sendStateBroadcast(CONN_STATE_CONNECTED);
                        } else {
                            if (cnt == 1) {//查询打印机实时状态
                                if ((buffer[0] & TSC_STATE_PAPER_ERR) > 0) {//缺纸
                                    status += " "+HaiApplication.getInstance().getString(R.string.str_printer_out_of_paper);
                                }
                                if ((buffer[0] & TSC_STATE_COVER_OPEN) > 0) {//开盖
                                    status += " "+HaiApplication.getInstance().getString(R.string.str_printer_open_cover);
                                }
                                if ((buffer[0] & TSC_STATE_ERR_OCCURS) > 0) {//打印机报错
                                    status += " "+HaiApplication.getInstance().getString(R.string.str_printer_error);
                                }
                                System.out.println(HaiApplication.getInstance().getString(R.string.str_state) + status);
                                //String mode=HaiApplication.getInstance().getString(R.string.str_printer_printmode_tsc);
                                //Utils.toast(HaiApplication.getInstance(), mode+" "+status);
                            } else {//打印机状态查询
                                Intent intent = new Intent(ACTION_QUERY_PRINTER_STATE);
                                HaiApplication.getInstance().sendBroadcast(intent);
                            }
                        }
                    }else if(sendCommand==cpcl){
                        if (currentPrinterCommand == null) {
                            currentPrinterCommand = PrinterCommand.CPCL;
                            sendStateBroadcast(CONN_STATE_CONNECTED);
                        }else {
                            if (cnt == 1) {
                                System.out.println(HaiApplication.getInstance().getString(R.string.str_state) + status);
                                if ((buffer[0] ==CPCL_STATE_PAPER_ERR)) {//缺纸
                                    status += " "+HaiApplication.getInstance().getString(R.string.str_printer_out_of_paper);
                                }
                                if ((buffer[0] ==CPCL_STATE_COVER_OPEN)) {//开盖
                                    status += " "+HaiApplication.getInstance().getString(R.string.str_printer_open_cover);
                                }
                                //String mode=HaiApplication.getInstance().getString(R.string.str_printer_printmode_cpcl);
                                //Utils.toast(HaiApplication.getInstance(), mode+" "+status);
                            } else {//打印机状态查询
                                Intent intent = new Intent(ACTION_QUERY_PRINTER_STATE);
                                HaiApplication.getInstance().sendBroadcast(intent);
                            }
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    };

    private void sendStateBroadcast(int state) {
        Intent intent = new Intent(ACTION_CONN_STATE);
        intent.putExtra(STATE, state);
        HaiApplication.getInstance().sendBroadcast(intent);
    }

    /**
     * 判断是实时状态（10 04 02）还是查询状态（1D 72 01）
     */
    private int judgeResponseType(byte r) {
        return (byte) ((r & FLAG) >> 4);
    }


}