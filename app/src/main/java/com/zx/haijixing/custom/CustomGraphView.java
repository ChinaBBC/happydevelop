package com.zx.haijixing.custom;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;
import com.zx.haijixing.R;
import java.util.LinkedList;

import zx.com.skytool.ZxLogUtil;

/**
 *
 *@作者 zx
 *@创建日期 2019/6/18 15:30
 *@描述 自定义曲线图
 */
public final class CustomGraphView extends SurfaceView implements SurfaceHolder.Callback {
    private Context mContext;
    private Paint mPaint;
    private Resources res;
    private DisplayMetrics dm;

    private int canvasHeight;
    private int canvasWidth;
    private int bHeight = 0;
    private int bWidth;
    private boolean isMeasure = true;
    private boolean canScrollRight = true;
    private boolean canScrollLeft = true;

    //y轴最大值
    private int maxValue;
    //y轴间隔值
    private int averageValue;
    private int marginTop = 20;
    private int marginBottom = 80;

    //曲线上的总点数
    private Point[] mPoints;
    //纵坐标值
    private LinkedList<Double> yRawData = new LinkedList<>();
    //横坐标值
    private LinkedList<String> xRawData = new LinkedList<>();
    //根据间隔计算出的每个X的值
    private LinkedList<Integer> xList = new LinkedList<>();

   /* private LinkedList<String> xPreData = new LinkedList<>();
    private LinkedList<Double> yPreData = new LinkedList<>();

    private LinkedList<String> xLastData = new LinkedList<>();
    private LinkedList<Double> yLastData = new LinkedList<>();*/
    private int spacingHeight = 1;

    private SurfaceHolder holder;
    private int lastX = 0;
    private int offSet = 0;
    private Rect mRect;

    private int xAverageValue = 0;


    public CustomGraphView(Context context) {
        this(context , null);
    }

    public CustomGraphView(Context context , AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initView();
    }

    private void initView() {
        this.res = mContext.getResources();
        this.mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(dm);

        /*xPreData.add("05-18");
        xPreData.add("05-17");
        xPreData.add("05-16");
        xPreData.add("05-15");
        xPreData.add("05-14");
        xPreData.add("05-13");

        yPreData.add(4000.53);
        yPreData.add(3000.45);
        yPreData.add(6000.78);
        yPreData.add(5000.21);
        yPreData.add(2000.34);
        yPreData.add(6000.32);

        xLastData.add("05-26");
        xLastData.add("05-27");
        xLastData.add("05-28");
        xLastData.add("05-29");
        xLastData.add("05-30");
        xLastData.add("05-31");

        yLastData.add(2000.35);
        yLastData.add(5000.43);
        yLastData.add(6000.23);
        yLastData.add(7000.33);
        yLastData.add(3000.45);
        yLastData.add(2000.45);*/

        holder = this.getHolder();
        holder.addCallback(this);
        ZxLogUtil.logError("<<<<init draw view");
    }

    @Override
    protected void onSizeChanged(int w , int h , int oldW , int oldH) {
        if (isMeasure) {
            this.canvasHeight = getHeight();
            this.canvasWidth = getWidth();
            if (bHeight == 0) {
                bHeight = canvasHeight - marginBottom;
            }
            bWidth = dip2px(30);
            xAverageValue = (canvasWidth - bWidth) / 7;
            isMeasure = false;
        }
    }

    private void drawView() {
        Canvas canvas = holder.lockCanvas();
        canvas.drawColor(Color.WHITE);
        mPaint.setColor(res.getColor(R.color.colorPrimary));
        drawAllXLine(canvas);
        mRect = new Rect(bWidth - 3, marginTop - 5 ,
                bWidth + (canvasWidth - bWidth) / yRawData.size() * (yRawData.size() - 1) + 3, bHeight + marginTop + marginBottom);
        //锁定画图区域
        canvas.clipRect(mRect);
        drawAllYLine(canvas);

        mPoints = getPoints();

        mPaint.setColor(res.getColor(R.color.colorAccent));
        mPaint.setStrokeWidth(dip2px(2.5f));
        mPaint.setStyle(Paint.Style.STROKE);
        drawLine(canvas);

        mPaint.setStyle(Paint.Style.FILL);
        for (int i = 0 ; i < mPoints.length ; i++) {
            canvas.drawCircle(mPoints[i].x , mPoints[i].y , 15 , mPaint);
            drawText(yRawData.get(i)+"",mPoints[i].x-15,mPoints[i].y-30,canvas);
        }

        holder.unlockCanvasAndPost(canvas);

    }

    //绘制折线图
    private void drawLine(Canvas canvas) {
        Point startP = null;
        Point endP = null;
        for (int i = 0 ; i < mPoints.length - 1; i++) {
            startP = mPoints[i];
            endP = mPoints[i + 1];
            canvas.drawLine(startP.x , startP.y , endP.x , endP.y , mPaint);
        }
    }

    //绘制所有的纵向分割线
    private void drawAllYLine(Canvas canvas) {
        for (int i = 0 ; i < yRawData.size() ; i++) {
            if (i == 0) {
                canvas.drawLine(bWidth, marginTop , bWidth, bHeight + marginTop , mPaint);
            }
            if (i == yRawData.size() - 1) {
                canvas.drawLine(bWidth + xAverageValue * i, marginTop , bWidth + xAverageValue * i , bHeight + marginTop , mPaint);
            }
            xList.add(bWidth + xAverageValue * i);
            canvas.drawLine(bWidth + xAverageValue * i + offSet, marginTop , bWidth + xAverageValue * i + offSet , bHeight + marginTop , mPaint);
            drawText(xRawData.get(i) , bWidth + xAverageValue * i - 30 + offSet, bHeight + dip2px(26) , canvas);

        }
    }

    //绘制所有的横向分割线
    private void drawAllXLine(Canvas canvas) {
        for (int i = 0 ; i < spacingHeight + 1 ; i++) {
            canvas.drawLine(bWidth , bHeight - (bHeight / spacingHeight) * i + marginTop ,
                    bWidth + xAverageValue * (yRawData.size() - 1) , bHeight - (bHeight / spacingHeight) * i + marginTop , mPaint);
            drawText(String.valueOf(averageValue * i) , bWidth / 2 , bHeight - (bHeight / spacingHeight) * i + marginTop, canvas);
        }
    }

    //绘制坐标值
    private void drawText(String text , int x , int y , Canvas canvas) {
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setTextSize(dip2px(12));
        p.setColor(res.getColor(R.color.color_4d));
        p.setTextAlign(Paint.Align.LEFT);
        canvas.drawText(text , x , y , p);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        //new Thread(this).start();
        Log.d("OOK" , "Created");
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        Log.d("OOK" , "Changed");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        int rawX = (int) event.getX();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                lastX = rawX;
                break;
            case MotionEvent.ACTION_MOVE:
                int offsetX = rawX - lastX;
                offSet = offSet + offsetX;
                if (offSet > xAverageValue && canScrollRight) {
                    offSet = offSet % xAverageValue;
                    canScrollLeft = true;
                }


                if (offSet < -xAverageValue && canScrollLeft) {
                    offSet = offSet % xAverageValue;
                    canScrollRight = true;
                }
                lastX = rawX;
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }

    private Point[] getPoints() {
        Point[] points = new Point[yRawData.size()];
        for (int i = 0 ; i < yRawData.size() ; i++) {
            int ph = bHeight - (int)(bHeight * (yRawData.get(i) / maxValue));

            points[i] = new Point(xList.get(i) + offSet , ph + marginTop);
        }
        return points;
    }

    public void setData(LinkedList<Double> yRawData , LinkedList<String> xRawData , int maxValue , int averageValue) {
        Canvas canvas = holder.lockCanvas();
        canvas.drawColor(Color.TRANSPARENT,PorterDuff.Mode.CLEAR);
        holder.unlockCanvasAndPost(canvas);
        this.maxValue = maxValue;
        this.averageValue = averageValue;
        this.mPoints = new Point[yRawData.size()];
        this.yRawData = yRawData;
        this.xRawData = xRawData;
        this.spacingHeight = maxValue / averageValue;
        drawView();
    }

    private int dip2px(float dpValue) {
        return (int) (dpValue * dm.density + 0.5f);
    }

}
