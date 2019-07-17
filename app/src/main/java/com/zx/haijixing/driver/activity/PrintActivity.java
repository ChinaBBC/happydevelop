package com.zx.haijixing.driver.activity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.location.LocationManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.tools.command.CpclCommand;
import com.tools.command.EscCommand;
import com.tools.command.LabelCommand;
import com.zx.haijixing.R;
import com.zx.haijixing.driver.adapter.BluetoothDataAdapter;
import com.zx.haijixing.driver.adapter.PrintAdapter;
import com.zx.haijixing.share.PathConstant;
import com.zx.haijixing.share.base.BaseActivity;
import com.zx.haijixing.util.CommonDialogFragment;
import com.zx.haijixing.util.DeviceConnectManager;
import com.zx.haijixing.util.HaiDialogUtil;
import com.zx.haijixing.util.HaiTool;
import com.zx.haijixing.util.ThreadPool;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import zx.com.skytool.ZxLogUtil;
import zx.com.skytool.ZxStringUtil;
import zx.com.skytool.ZxToastUtil;

@Route(path = PathConstant.PRINT)
public class PrintActivity extends BaseActivity implements AdapterView.OnItemClickListener,HaiDialogUtil.TruckResultListener {

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

    private final static int SEARCH_CODE = 0x123;
    private BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

    private List<BluetoothDevice> mBlueList = new ArrayList<>();
    private BluetoothDataAdapter adapter = null;

    private int id = 0;
    private ThreadPool threadPool;
    private CommonDialogFragment showBluetooth;


    @Override
    protected void initView() {
        title.setText(getHaiString(R.string.print_detail));
        printData.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        printData.setAdapter(new PrintAdapter());
        adapter = new BluetoothDataAdapter(mBlueList);
        initBluetooth();
    }

    @Override
    protected void initInjector() {

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
                printXml();
                break;
            case R.id.print_print:
                show();
                break;
        }
    }


    //gps是否可用(有些设备可能需要定位)
    public static final boolean isGpsEnable(final Context context) {
        LocationManager locationManager
                = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (gps || network) {
            return true;
        }
        return false;
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
     * 注册异步搜索蓝牙设备的广播
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
            // 发现设备的广播
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // 从intent中获取设备
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                // 没否配对
                if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
                    if (!mBlueList.contains(device) && !ZxStringUtil.isEmpty(device.getName())) {
                        mBlueList.add(device);
                        adapter.notifyDataSetChanged();
                    }
                }
                // 搜索完成
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                // 关闭进度条
                ZxLogUtil.logError("onReceive: 搜索完成");
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
            unregisterReceiver(receiver);
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
        connectPort(mBlueList.get(position).getAddress());
    }

    /**
     * 重新连接回收上次连接的对象，避免内存泄漏
     */
    private void closePort(){
        if ( DeviceConnectManager.getDeviceConnectManagers()[id] != null &&DeviceConnectManager.getDeviceConnectManagers()[id].mPort != null ) {
            DeviceConnectManager.getDeviceConnectManagers()[id].reader.canceled();

            DeviceConnectManager.getDeviceConnectManagers()[id].mPort.closePort();
            DeviceConnectManager.getDeviceConnectManagers()[id].mPort = null;
        }
    }

    private void connectPort(String address){
        closePort();
        /* 初始化话DeviceConnFactoryManager */
        new DeviceConnectManager.Build()
                .setId(id)
                /* 设置连接方式 */
                .setConnMethod( DeviceConnectManager.CONN_METHOD.BLUETOOTH )
                /* 设置连接的蓝牙mac地址 */
                .setMacAddress(address)
                .build();
        /* 打开端口 */
        threadPool = ThreadPool.getInstantiation();
        threadPool.addTask(() -> DeviceConnectManager.getDeviceConnectManagers()[id].openPort());
    }

    /**
     * 打印XML
     */
    public void printXml() {
        //View v = LayoutInflater.from(this).inflate(R.layout.item_print_header, null);
        //final Bitmap bitmap = convertViewToBitmap( v );
        final Bitmap bitmap = HaiTool.shotRecyclerView(printData);
        threadPool = ThreadPool.getInstantiation();
        threadPool.addTask(() -> {
            if ( DeviceConnectManager.getDeviceConnectManagers()[id] == null || !DeviceConnectManager.getDeviceConnectManagers()[id].getConnState() ) {
                //mHandler.obtainMessage( CONN_PRINTER ).sendToTarget();
                return;
            }

            if ( DeviceConnectManager.getDeviceConnectManagers()[id].getCurrentPrinterCommand() == DeviceConnectManager.PrinterCommand.CPCL ) {
                CpclCommand cpcl = new CpclCommand();
                cpcl.addInitializePrinter( 1500, 1 );
                cpcl.addCGraphics( 0, 0, (80 - 10) * 8, bitmap );
                cpcl.addPrint();
                DeviceConnectManager.getDeviceConnectManagers()[id].sendDataImmediately( cpcl.getCommand() );
            } else if ( DeviceConnectManager.getDeviceConnectManagers()[id].getCurrentPrinterCommand() == DeviceConnectManager.PrinterCommand.TSC ) {
                LabelCommand labelCommand = new LabelCommand();
                labelCommand.addSize( 80, 180 );
                labelCommand.addCls();
                labelCommand.addBitmap( 0, 0, (80 - 10) * 8, bitmap );
                labelCommand.addPrint( 1 );
                DeviceConnectManager.getDeviceConnectManagers()[id].sendDataImmediately( labelCommand.getCommand() );
            }else if ( DeviceConnectManager.getDeviceConnectManagers()[id].getCurrentPrinterCommand() == DeviceConnectManager.PrinterCommand.ESC ) {
                EscCommand esc = new EscCommand();
                esc.addInitializePrinter();
                esc.addInitializePrinter();
                esc.addRastBitImage( bitmap, (80 - 10) * 8, 0 );
                esc.addPrintAndLineFeed();
                esc.addPrintAndLineFeed();
                esc.addPrintAndLineFeed();
                DeviceConnectManager.getDeviceConnectManagers()[id].sendDataImmediately(esc.getCommand());
            }
        });
    }


    /**
     * mxl转bitmap图片
     * @param view
     * @return
     */
    public static Bitmap convertViewToBitmap( View view )
    {
        view.measure( View.MeasureSpec.makeMeasureSpec( 0, View.MeasureSpec.UNSPECIFIED ), View.MeasureSpec.makeMeasureSpec( 0, View.MeasureSpec.UNSPECIFIED ) );
        view.layout( 0, 0, view.getMeasuredWidth(), view.getMeasuredHeight() );
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
        return(bitmap);
    }

    @Override
    public void truckResult(int index) {
        showBluetooth.dismissAllowingStateLoss();
    }
}
