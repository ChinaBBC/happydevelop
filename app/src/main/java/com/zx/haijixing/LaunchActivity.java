package com.zx.haijixing;

import android.graphics.Point;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.ethanhua.skeleton.RecyclerViewSkeletonScreen;
import com.ethanhua.skeleton.Skeleton;

import com.zx.haijixing.share.RoutePathConstant;
import com.zx.haijixing.share.dao.HaiDao;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


/**
 *
 *@作者 zx
 *@创建日期 2019/6/14 12:00
 *@描述 启动页面
 */
public class LaunchActivity extends AppCompatActivity {


    private HaiDao dao;
    private RecyclerViewSkeletonScreen skeletonScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
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
