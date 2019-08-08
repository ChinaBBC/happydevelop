package com.zx.haijixing.driver.activity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.tools.command.CpclCommand;
import com.tools.command.EscCommand;
import com.tools.command.LabelCommand;
import com.zx.haijixing.R;
import com.zx.haijixing.driver.adapter.BluetoothDataAdapter;
import com.zx.haijixing.driver.adapter.PrintAdapter;
import com.zx.haijixing.driver.contract.PrintOrderContract;
import com.zx.haijixing.driver.entry.PrintEntry;
import com.zx.haijixing.driver.entry.SelectEntry;
import com.zx.haijixing.driver.presenter.PrintImp;
import com.zx.haijixing.share.PathConstant;
import com.zx.haijixing.share.base.BaseActivity;
import com.zx.haijixing.util.CommonDialogFragment;
import com.zx.haijixing.util.DeviceConnectManager;
import com.zx.haijixing.util.HaiDialogUtil;
import com.zx.haijixing.util.HaiTool;
import com.zx.haijixing.util.ThreadPool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import zx.com.skytool.ZxLogUtil;
import zx.com.skytool.ZxSharePreferenceUtil;
import zx.com.skytool.ZxStringUtil;
import zx.com.skytool.ZxToastUtil;

import static com.zx.haijixing.util.DeviceConnectManager.CONN_STATE_FAILED;

@Route(path = PathConstant.PRINT)
public class PrintActivity extends BaseActivity<PrintImp> implements AdapterView.OnItemClickListener,HaiDialogUtil.TruckResultListener,PrintOrderContract.PrintOrderView,PrintAdapter.TotalPrintListener {

    @BindView(R.id.common_title_back)
    ImageView back;
    @BindView(R.id.common_title_title)
    TextView title;
    @BindView(R.id.print_selectAll)
    ImageView selectAll;
    @BindView(R.id.print_word1)
    TextView word1;
    @BindView(R.id.print_total)
    TextView total;
    @BindView(R.id.print_print)
    TextView print;
    @BindView(R.id.print_data)
    RecyclerView printData;

    @Autowired(name = "waybillId")
    String waybillId;
    @Autowired(name = "printStatus")
    String printStatus;

    private final static int SEARCH_CODE = 0x123;
    private BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

    private List<BluetoothDevice> mBlueList = new ArrayList<>();
    private BluetoothDataAdapter adapter = null;

    private ThreadPool threadPool;
    private CommonDialogFragment showBluetooth;
    private PrintAdapter printAdapter;
    private String token;
    private PrintEntry printEntry;
    private boolean isSelectAll = false;

    private List<SelectEntry> selectEntries = new ArrayList<>();
    private int totalNum = 0;
    private int headSelect = 1;


    @Override
    protected void initView() {
        title.setText(getHaiString(R.string.print_detail));
        printData.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        printAdapter = new PrintAdapter(this::totalResult);
        printData.setAdapter(printAdapter);

        ZxSharePreferenceUtil instance = ZxSharePreferenceUtil.getInstance();
        instance.init(this);
        token = (String) instance.getParam("token","null");

        Map<String,String> params = new HashMap<>();
        params.put("token",token);
        params.put("waybillId",waybillId);
        params.put("timestamp",System.currentTimeMillis()+"");
        params.put("sign","");
        params.put("sign",HaiTool.sign(params));
        mPresenter.printOrderMethod(params);
        adapter = new BluetoothDataAdapter(mBlueList);
    }

    @Override
    protected void initInjector() {
        mPresenter = new PrintImp();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_print;
    }

    @OnClick({R.id.common_title_back, R.id.print_selectAll, R.id.print_word1, R.id.print_print})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.common_title_back:
                finish();
                break;
            case R.id.print_selectAll:
            case R.id.print_word1:
                selectOrCancel();
                break;
            case R.id.print_print:
                if (totalNum>0){
                    show();
                }else {
                    ZxToastUtil.centerToast("请选择需要打印的运单");
                }
                break;
        }
    }

    //全选或者取消全选
    private void selectOrCancel() {
        if (printEntry != null){
            if (isSelectAll){
                isSelectAll = false;
                selectAll.setImageResource(R.mipmap.select_no);
                word1.setText(getHaiString(R.string.select_all));
                total.setText("共:0单");
                printAdapter.setSelectTag(0);
                printAdapter.setSelectHead(1);
                totalNum = 0;
                headSelect = 1;
            }else {
                isSelectAll = true;
                selectAll.setImageResource(R.mipmap.select_yes_solid);
                word1.setText(getHaiString(R.string.cancel));
                int  size = Integer.parseInt(ZxStringUtil.isEmpty(printEntry.getTotalNum())?"0":printEntry.getTotalNum());
                totalNum = size+1;
                headSelect = 0;
                total.setText("共:"+(size+1)+"单");
                printAdapter.setSelectTag(size+1);
                printAdapter.setSelectHead(0);
            }
            for (int i=0;i<selectEntries.size();i++){
                selectEntries.get(i).setSelect(isSelectAll);
            }
            printAdapter.notifyDataSetChanged();
        }else {
            ZxToastUtil.centerToast("暂无数据");
        }
    }

    /**
     * 判断蓝牙是否开启
     */
    private void initBluetooth() {
        // 判断手机是否支持蓝牙
        if (mBluetoothAdapter == null) {
            ZxToastUtil.centerToast(getHaiString(R.string.not_support));
        }
        // 判断是否打开蓝牙
        if (!mBluetoothAdapter.isEnabled()) {
            //弹出对话框提示用户是后打开
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent,SEARCH_CODE);
        } else {
            // 不做提示，强行打开
            mBluetoothAdapter.enable();
        }
        startDiscovery();
    }

    /**
     * 注册异步搜索蓝牙设备和蓝牙连接状态的广播
     */
    private void startDiscovery() {
        // 找到设备的广播
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        // 注册广播
        registerReceiver(receiver, filter);
        // 搜索完成的广播
        IntentFilter filter1 = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        // 注册广播
        registerReceiver(receiver, filter1);
        //连接状态的广播
        IntentFilter filter2 = new IntentFilter( DeviceConnectManager.ACTION_CONN_STATE );
        //注册广播
        registerReceiver( receiver, filter2 );

        startScanBluth();
    }

    /**
     * 广播接收器
     */
    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // 收到的广播类型
            String action = intent.getAction();
            switch (action){
                case BluetoothDevice.ACTION_FOUND:
                    // 从intent中获取设备
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    // 没否配对
                    if (!mBlueList.contains(device) && !ZxStringUtil.isEmpty(device.getName())) {
                        mBlueList.add(device);
                        adapter.notifyDataSetChanged();
                    }
                    break;
                case BluetoothAdapter.ACTION_DISCOVERY_FINISHED:
                    // 搜索完成关闭进度条
                    ZxLogUtil.logError("onReceive: 搜索完成");
                    break;
                case DeviceConnectManager.ACTION_CONN_STATE:
                    int state = intent.getIntExtra( DeviceConnectManager.STATE, -1 );
                    switch ( state ) {
                        case DeviceConnectManager.CONN_STATE_DISCONNECT:
                            ZxToastUtil.centerToast("连接断开，请重试");
                            break;
                        case DeviceConnectManager.CONN_STATE_CONNECTING:
                            ZxToastUtil.centerToast("正在连接，请稍候");
                            break;
                        case DeviceConnectManager.CONN_STATE_CONNECTED:
                            ZxToastUtil.centerToast("连接成功，正在打印，请稍候");
                            showBluetooth.dismissAllowingStateLoss();
                            printXml();
                            break;
                        case CONN_STATE_FAILED:
                            ZxToastUtil.centerToast("连接失败，请重试");
                            break;
                        default:
                            break;
                    }
                    break;
            }
        }
    };

    private void show(){
        showBluetooth = HaiDialogUtil.showBluetooth(getSupportFragmentManager(), adapter, PrintActivity.this::truckResult, PrintActivity.this::onItemClick);
    }
    /**
     * 搜索蓝牙的方法
     */
    private void startScanBluth() {
        // 判断是否在搜索,如果在搜索，就取消搜索
        if (mBluetoothAdapter.isDiscovering()) {
            mBluetoothAdapter.cancelDiscovery();
        }
        // 开始搜索
        mBluetoothAdapter.startDiscovery();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (receiver != null) {
            //取消注册,防止内存泄露（onDestroy被回调代不代表Activity被回收？：具体回收看系统，由GC回收，同时广播会注册到系统
            //管理的ams中，即使activity被回收，reciver也不会被回收，所以一定要取消注册），
            try {
                unregisterReceiver(receiver);
            }catch (Exception e){
                ZxLogUtil.logError(",<<<<<<未注册");
            }
        }
        if ( threadPool != null ) {
            threadPool.stopThreadPool();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==SEARCH_CODE){
            startDiscovery();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        showBluetooth.dismissAllowingStateLoss();
        print.setClickable(false);
        connectPort(mBlueList.get(position).getAddress());
    }

    /**
     * 重新连接回收上次连接的对象，避免内存泄漏
     */
    private void closePort(){
        DeviceConnectManager.getDeviceConnectManagers().closePort();
    }

    private void connectPort(String address){
        ZxLogUtil.logError("<<<<address>>"+address);
        closePort();
        /* 打开端口 */
        threadPool = ThreadPool.getInstantiation();
        threadPool.addTask(() -> {
                    ZxLogUtil.logError("<<<<address>>"+address);
                    DeviceConnectManager.getDeviceConnectManagers().setMacAddress(address).openPort();
                }
               );
    }

    /**
     * 打印XML
     */
    public void printXml() {
        Map<String,String> params = new HashMap<>();
        params.put("token",token);
        params.put("waybillId",waybillId);
        params.put("dadanFlag",ZxStringUtil.isEmpty(printStatus)?"0":printStatus);
        params.put("timestamp",System.currentTimeMillis()+"");
        params.put("sign","");
        params.put("sign",HaiTool.sign(params));
        mPresenter.printStatusMethod(params);

        threadPool = ThreadPool.getInstantiation();
        for (int i=headSelect;i<totalNum+headSelect;i++){
            final Bitmap bitmap = HaiTool.shotRecyclerView(printData,i);
            threadPool.addTask(() -> {
                if ( DeviceConnectManager.getDeviceConnectManagers() == null || !DeviceConnectManager.getDeviceConnectManagers().getConnState() ) {
                    ZxToastUtil.centerToast("连接断开了。。。");
                    return;
                }

                if ( DeviceConnectManager.getDeviceConnectManagers().getCurrentPrinterCommand() == DeviceConnectManager.PrinterCommand.CPCL ) {
                    ZxLogUtil.logError("<<<<<CPCL<");
                    CpclCommand cpcl = new CpclCommand();
                    cpcl.addInitializePrinter( 1500, 1 );
                    cpcl.addCGraphics( 0, 0, (80 - 10) * 8, bitmap );
                    cpcl.addPrint();
                    DeviceConnectManager.getDeviceConnectManagers().sendDataImmediately( cpcl.getCommand() );
                } else if ( DeviceConnectManager.getDeviceConnectManagers().getCurrentPrinterCommand() == DeviceConnectManager.PrinterCommand.TSC ) {
                    ZxLogUtil.logError("<<<<<TSC<"+bitmap.getWidth()+"<>"+bitmap.getHeight());
                    LabelCommand labelCommand = new LabelCommand();

                    labelCommand.addSize( 76, 50 );
                    labelCommand.addGap(3);
                    labelCommand.addCls();
                    labelCommand.addBitmap( 0, 0, (80 - 10) * 8, bitmap );
                    labelCommand.addPrint( 1 );
                    DeviceConnectManager.getDeviceConnectManagers().sendDataImmediately( labelCommand.getCommand() );
                }else if ( DeviceConnectManager.getDeviceConnectManagers().getCurrentPrinterCommand() == DeviceConnectManager.PrinterCommand.ESC ) {
                    ZxLogUtil.logError("<<<<<ESC<");
                    EscCommand esc = new EscCommand();
                    esc.addInitializePrinter();
                    esc.addInitializePrinter();
                    esc.addRastBitImage( bitmap, (80 - 10) * 8, 0 );
                    esc.addPrintAndLineFeed();
                    esc.addPrintAndLineFeed();
                    esc.addPrintAndLineFeed();
                    DeviceConnectManager.getDeviceConnectManagers().sendDataImmediately(esc.getCommand());
                }
            });
        }

    }


    @Override
    public void truckResult(int index) {
        showBluetooth.dismissAllowingStateLoss();
    }

    @Override
    public void printOrderSuccess(PrintEntry printEntry) {
        initBluetooth();
        this.printEntry = printEntry;
        int  size = Integer.parseInt(ZxStringUtil.isEmpty(printEntry.getTotalNum())?"0":printEntry.getTotalNum());
        SelectEntry selectEntry = null;
        for (int i=0;i<size;i++){
            selectEntry = new SelectEntry(false);
            selectEntries.add(selectEntry);
        }
        printAdapter.setSelectEntries(selectEntries);
        printAdapter.setPrintEntry(printEntry);
        printAdapter.notifyDataSetChanged();
    }

    @Override
    public void printStatusSuccess(String msg) {
        print.setClickable(true);
    }

    @Override
    public void showFaild(String errorMsg) {
        super.showFaild(errorMsg);
        print.setClickable(true);
    }

    @Override
    public void totalResult(int total,int head) {
        ZxLogUtil.logError("<<"+total+"<<"+head);
        totalNum = total;
        headSelect = head;
        this.total.setText("共:"+total+"单");
        if (total == 0){
            isSelectAll = false;
            selectAll.setImageResource(R.mipmap.select_no);
            word1.setText(getHaiString(R.string.select_all));
        }else{
            int  size = Integer.parseInt(ZxStringUtil.isEmpty(printEntry.getTotalNum())?"0":printEntry.getTotalNum());
            if (total == size+1){
                isSelectAll = true;
                selectAll.setImageResource(R.mipmap.select_yes_solid);
                word1.setText(getHaiString(R.string.cancel));
            }

        }
    }
}
