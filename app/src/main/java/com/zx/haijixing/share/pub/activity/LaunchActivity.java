package com.zx.haijixing.share.pub.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.ethanhua.skeleton.RecyclerViewSkeletonScreen;

import com.zx.haijixing.BuildConfig;
import com.zx.haijixing.HaiNativeHelper;
import com.zx.haijixing.R;
import com.zx.haijixing.share.RoutePathConstant;
import com.zx.haijixing.share.base.BaseActivity;
import com.zx.haijixing.share.dao.HaiDao;
import com.zx.haijixing.share.pub.contract.VersionContract;
import com.zx.haijixing.share.pub.entry.VersionEntry;
import com.zx.haijixing.share.pub.imp.VersionImp;
import com.zx.haijixing.util.CommonDialogFragment;
import com.zx.haijixing.util.HaiDialogUtil;
import com.zx.haijixing.util.HaiTool;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import butterknife.OnClick;


/**
 *
 *@作者 zx
 *@创建日期 2019/6/14 12:00
 *@描述 启动页面
 */
public class LaunchActivity extends BaseActivity<VersionImp> implements VersionContract.VersionView {


    private HaiDao dao;
    private RecyclerViewSkeletonScreen skeletonScreen;
    private CommonDialogFragment showUpdate;
    private VersionEntry versionEntry;

    @Override
    protected void initView() {
        permission();
        test();
        mPresenter.versionMethod();
    }

    @Override
    protected void initInjector() {
        mPresenter = new VersionImp();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_launch;
    }

    @Override
    public void versionSuccess(VersionEntry versionEntry) {
        this.versionEntry = versionEntry;
        if (HaiTool.packageCode(this)<versionEntry.getVersionNum()) {
            showUpdate = HaiDialogUtil.showUpdate(getSupportFragmentManager(), this::onViewClicked);
        }
    }

    @OnClick
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.dialog_update_no:
                showUpdate.dismissAllowingStateLoss();
                break;
            case R.id.dialog_update_yes:
                ARouter.getInstance().build(RoutePathConstant.APK_ACTIVITY).withString("path",versionEntry.getDownloadUrl()).navigation();
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void permission(){
        final List<String> permissionsList = new ArrayList<>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(LaunchActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)||
                    ActivityCompat.shouldShowRequestPermissionRationale(LaunchActivity.this, Manifest.permission.RECORD_AUDIO)||
                    ActivityCompat.shouldShowRequestPermissionRationale(LaunchActivity.this, Manifest.permission.CAMERA)||
                    ActivityCompat.shouldShowRequestPermissionRationale(LaunchActivity.this, Manifest.permission.CALL_PHONE)||
                    ActivityCompat.shouldShowRequestPermissionRationale(LaunchActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION)||
                    ActivityCompat.shouldShowRequestPermissionRationale(LaunchActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)||
                    ActivityCompat.shouldShowRequestPermissionRationale(LaunchActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)){
            }else {
                if ((checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED))
                    permissionsList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                if ((checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED))
                    permissionsList.add(Manifest.permission.READ_EXTERNAL_STORAGE);
                if ((checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED))
                    permissionsList.add(Manifest.permission.ACCESS_COARSE_LOCATION);
                if ((checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED))
                    permissionsList.add(Manifest.permission.CAMERA);
                if ((checkSelfPermission(Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED))
                    permissionsList.add(Manifest.permission.RECORD_AUDIO);
                if ((checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED))
                    permissionsList.add(Manifest.permission.ACCESS_FINE_LOCATION);
                if ((checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED))
                    permissionsList.add(Manifest.permission.CALL_PHONE);
                if ((checkSelfPermission(Manifest.permission.WRITE_SETTINGS) != PackageManager.PERMISSION_GRANTED))
                    permissionsList.add(Manifest.permission.WRITE_SETTINGS);
                if ((checkSelfPermission(Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS) != PackageManager.PERMISSION_GRANTED))
                    permissionsList.add(Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS);
                if (permissionsList.size() == 0) {

                } else {
                    requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
                            0);
                }
            }
        } else {

        }

    }
    private void test(){
        Log.i("<<<<<","<BuildConfig.homeUrl>"+BuildConfig.homeUrl+HaiNativeHelper.baseUrl());
        final SwipeRefreshLayout swipe = findViewById(R.id.swipe);
        final TextView sample = findViewById(R.id.sample_text);
        //final CustomGraphView customGraphView = findViewById(R.id.graph);
        //final CustomGraphViewT customGraphView2 = findViewById(R.id.graph2);


        List<String> list1 = new ArrayList<>();
        list1.add("1");
        list1.add("2");
        list1.add("3");
        list1.add("4");
        list1.add("5");
        list1.add("6");
        list1.add("7");
        list1.add("8");
        list1.add("9");
        list1.add("11");
        list1.add("12");
        list1.add("13");
        list1.add("14");
        list1.add("15");
        list1.add("16");
        list1.add("17");

        List<String> list2 = new ArrayList<>();
        list2.add("2千元");
        list2.add("4千元");
        list2.add("6千元");
        list2.add("8千元");
        list2.add("10千元");
        list2.add("12千元");

        List<Point> points = new ArrayList<>();
        points.add(new Point(0,2999));
        points.add(new Point(1,2324));
        points.add(new Point(2,1223));
        points.add(new Point(3,4545));
        points.add(new Point(4,6643));
        points.add(new Point(5,7777));
        points.add(new Point(6,8888));
        points.add(new Point(7,1234));
        points.add(new Point(8,2234));
        points.add(new Point(9,3234));
        points.add(new Point(10,3234));
        points.add(new Point(11,3234));
        points.add(new Point(12,3234));
        points.add(new Point(13,3234));
        points.add(new Point(14,3234));
        points.add(new Point(15,3234));
        points.add(new Point(16,3234));
        points.add(new Point(17,3234));

        /*customGraphView2.setXUnitValue(1)
                .setYUnitValue(2000)
                .setXTextUnits(list1)
                .setYTextUnits(list2)
                .setDateList(points)
                .startDraw();*/

        LinkedList<Double> yList = new LinkedList<>();
        yList.add(200.203);
        yList.add(400.05);
        yList.add(600.60);
        yList.add(300.08);
        yList.add(400.32);
        yList.add(220.0);
        yList.add(550.0);

        LinkedList<String> xRawData = new LinkedList<>();
        xRawData.add("05-19");
        xRawData.add("05-20");
        xRawData.add("05-21");
        xRawData.add("05-22");
        xRawData.add("05-23");
        xRawData.add("05-24");
        xRawData.add("05-25");
        ARouter.getInstance().inject(this);

        //customGraphView.setData(yList , xRawData , 10000 , 500);
        sample.setOnClickListener(v -> {
            ARouter.getInstance().build(RoutePathConstant.ROUTE_LOGIN).navigation();
        });

        final RecyclerView recyclerView = findViewById(R.id.my_data);

        swipe.setOnRefreshListener(() -> {

        });
    }
}
