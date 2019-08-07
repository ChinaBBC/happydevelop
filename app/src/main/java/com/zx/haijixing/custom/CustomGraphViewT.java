package com.zx.haijixing.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import com.zx.haijixing.R;
import com.zx.haijixing.driver.entry.MyPoint;

import java.util.ArrayList;
import java.util.List;

/**
 *
 *@作者 zx
 *@创建日期 2019/6/18 16:51
 *@描述 自定义曲线图2
 */
public final class CustomGraphViewT extends View {
    private int defaultBorderColor = Color.BLACK;
    private int defaultBorderWidth = 2;
    private boolean defaultIsShowXBorder = true;
    private boolean defaultIsShowYBorder = false;
    private int defaultTextSize = 40;
    private int defaultTextColor = Color.GRAY;
    private int defaultTextPadding = 35;
    private int defaultSolidColor = Color.parseColor("#33000000");
    private boolean defaultIsShowSolid = true;
    private int defaultCircleRadius = 10;
    private int defaultCircleColor = Color.RED;
    private boolean defaultIsCircleSolid = true;
    private boolean defaultIsShowCircle = true;
    private int defaultBrokenWidth = 5;
    private int defaultBrokenColor = Color.BLACK;
    private int defaultYUnitValue = 2000;
    private int defaultXUnitValue = 1;
    private int mXBorderColor = defaultBorderColor;
    private int mXBorderWidth = defaultBorderWidth;
    private int mYBorderColor = defaultBorderColor;
    private int mYBorderWidth = defaultBorderWidth;
    private boolean mIsShowXBorder = defaultIsShowXBorder;
    private boolean mIsShowYBorder = defaultIsShowYBorder;
    private int mXTextSize = defaultTextSize;
    private int mXTextColor = defaultTextColor;
    private int mXTextPadding = defaultTextPadding;
    private int mYTextSize = defaultTextSize;
    private int mYTextColor = defaultTextColor;
    private int mYTextPadding = defaultTextPadding;
    private int mSolidColor = defaultSolidColor;
    private boolean mIsShowSolid = defaultIsShowSolid;
    private int mCircleRadius = defaultCircleRadius;
    private int mCircleColor = defaultCircleColor;
    private boolean mIsCircleSolid = defaultIsCircleSolid;
    private boolean mIsShowCircle = defaultIsShowCircle;
    private int mBrokenWidth = defaultBrokenWidth;
    private int mBrokenColor = defaultBrokenColor;
    private int mYUnitValue = defaultYUnitValue;
    private int mXUnitValue = defaultXUnitValue;

    private Paint mXBorderLinePaint;
    private Paint mYBorderLinePaint;
    private TextPaint mXTextPaint;
    private TextPaint mYTextPaint;
    private Paint mSolidPaint;
    private Paint mCirclePaint;
    private Paint mBrokenLinePaint;

    private List<String> mXTextUnits = new ArrayList<>();

    private List<String> mYTextUnits = new ArrayList<>();

    private List<MyPoint> mDataLists = new ArrayList<>();

    private int mWidth = 0;
    private int mHeight = 0;
    private float mXTextHeight = 0f;
    private float mYTextHeight = 0f;
    //x轴一大格的宽度
    private float itemXWidth = 0f;
    //y轴一大格的宽度
    private float itemYWidth = 0f;
    //y轴的偏移量
    private float xPadding = 0f;
    //x轴的偏移量
    private float yPadding = 0f;
    //y轴字体最大宽度
    private float yMaxWidth = 0f;
    private  Path mSolidPath;
    private  Path mBrokenPath;


    public CustomGraphViewT(Context context) {
        super(context);
    }

    public CustomGraphViewT(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        //initData();
        initStyle(context,attrs);
        initPaint();
        initPath();
    }

    public CustomGraphViewT(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initData() {
        mXTextUnits.add("1");
        mXTextUnits.add("2");
        mXTextUnits.add("3");
        mXTextUnits.add("4");
        mXTextUnits.add("5");
        mXTextUnits.add("6");

        mYTextUnits.add("2000元");
        mYTextUnits.add("4000元");
        mYTextUnits.add("6000元");
        mYTextUnits.add("7000元");
        mYTextUnits.add("8000元");
        mYTextUnits.add("9000元");
    }

    private void initStyle(Context ctx, AttributeSet attributeSet){
        TypedArray typeArray = ctx.obtainStyledAttributes(attributeSet, R.styleable.BrokenLineView);
        mXBorderColor = typeArray.getColor(R.styleable.BrokenLineView_XBorderLineColor,defaultBorderColor);
        mXBorderWidth = typeArray.getDimensionPixelSize(R.styleable.BrokenLineView_XBorderLineWidth,defaultBorderWidth);
        mYBorderColor = typeArray.getColor(R.styleable.BrokenLineView_YBorderLineColor,defaultBorderColor);
        mYBorderWidth = typeArray.getDimensionPixelSize(R.styleable.BrokenLineView_YBorderLineWidth,defaultBorderWidth);
        mIsShowXBorder = typeArray.getBoolean(R.styleable.BrokenLineView_isShowXBorderLine,defaultIsShowXBorder);
        mIsShowYBorder = typeArray.getBoolean(R.styleable.BrokenLineView_isShowYBorderLine,defaultIsShowYBorder);
        mXTextSize = typeArray.getDimensionPixelSize(R.styleable.BrokenLineView_XTextSize,defaultTextSize);
        mXTextColor = typeArray.getColor(R.styleable.BrokenLineView_XTextColor,defaultTextColor);
        mXTextPadding = typeArray.getDimensionPixelSize(R.styleable.BrokenLineView_XTextPadding,defaultTextPadding);
        mYTextSize = typeArray.getDimensionPixelSize(R.styleable.BrokenLineView_YTextSize,defaultTextSize);
        mYTextColor = typeArray.getColor(R.styleable.BrokenLineView_YTextColor,defaultTextColor);
        mYTextPadding = typeArray.getDimensionPixelSize(R.styleable.BrokenLineView_YTextPadding,defaultTextPadding);
        mSolidColor = typeArray.getColor(R.styleable.BrokenLineView_solidColor,defaultSolidColor);
        mIsShowSolid = typeArray.getBoolean(R.styleable.BrokenLineView_isShowSolid,defaultIsShowSolid);
        mCircleRadius = typeArray.getDimensionPixelSize(R.styleable.BrokenLineView_circleRadius,defaultCircleRadius);
        mCircleColor = typeArray.getColor(R.styleable.BrokenLineView_circleColor,defaultCircleColor);
        mIsCircleSolid = typeArray.getBoolean(R.styleable.BrokenLineView_isCircleSolid,defaultIsCircleSolid);
        mIsShowCircle = typeArray.getBoolean(R.styleable.BrokenLineView_isShowCircle,defaultIsShowCircle);
        mBrokenWidth = typeArray.getDimensionPixelSize(R.styleable.BrokenLineView_BrokenLineWidth,defaultBrokenWidth);
        mBrokenColor = typeArray.getColor(R.styleable.BrokenLineView_BrokenLineColor,defaultBrokenColor);
        mXUnitValue = typeArray.getInteger(R.styleable.BrokenLineView_xUnitValue,defaultXUnitValue);
        mYUnitValue = typeArray.getInteger(R.styleable.BrokenLineView_yUnitValue,defaultYUnitValue);

        typeArray.recycle();
    }
    private void initPaint(){
        mXBorderLinePaint = createBasePaint();
        mXBorderLinePaint.setStrokeWidth(mXBorderWidth);
        mXBorderLinePaint.setColor(mXBorderColor);

        mYBorderLinePaint = createBasePaint();
        mYBorderLinePaint.setStrokeWidth(mYBorderWidth);
        mYBorderLinePaint.setColor(mYBorderColor);

        mXTextPaint = createTextPaint();
        mXTextPaint.setColor(mXTextColor);
        mXTextPaint.setTextSize(mXTextSize);

        mYTextPaint = createTextPaint();
        mYTextPaint.setColor(mYTextColor);
        mYTextPaint.setTextSize(mYTextSize);

        mSolidPaint = createBasePaint();
        mSolidPaint.setColor(mSolidColor);
        mSolidPaint.setStyle(Paint.Style.FILL);

        mCirclePaint = createBasePaint();
        mCirclePaint.setStrokeWidth(mCircleRadius/4);

        mBrokenLinePaint = createBasePaint();
        mBrokenLinePaint.setStrokeWidth(mBrokenWidth);
        mBrokenLinePaint.setStyle(Paint.Style.STROKE);
        mBrokenLinePaint.setColor(mBrokenColor);


        mXTextHeight = getTextHeight(mXTextPaint);
        mYTextHeight = getTextHeight(mYTextPaint);
    }
    private void initPath(){
        mSolidPath = new Path();
        mBrokenPath = new Path();
    }
    private Paint createBasePaint(){
        Paint basePaint =new Paint(Paint.ANTI_ALIAS_FLAG);
        basePaint.setAntiAlias(true);
        return basePaint;
    }
    private TextPaint createTextPaint(){
        TextPaint textPaint = new TextPaint();
        textPaint.setAntiAlias(true);
        return textPaint;
    }

    public CustomGraphViewT setXUnitValue(int value){
        this.mXUnitValue = value;
        return this;
    }

    public CustomGraphViewT setYUnitValue(int value){
        this.mYUnitValue = value;
        return this;
    }

    public CustomGraphViewT setXTextUnits(List<String> textUnits){
        mXTextUnits.clear();
        mXTextUnits.addAll(textUnits);
        return this;
    }

    public CustomGraphViewT setYTextUnits(List<String> textUnits){
        mYTextUnits.clear();
        mYTextUnits.addAll(textUnits);
        return this;
    }

    public CustomGraphViewT setDateList(List<MyPoint> dataList){
        mDataLists.clear();
        mDataLists.addAll(dataList);
        return this;
    }

    public void startDraw(){
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        mHeight = MeasureSpec.getSize(heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawXY(canvas);
        drawXText(canvas);
        drawYText(canvas);
        if(mIsShowSolid){
            drawSolid(canvas);
        }
        drawBroken(canvas);
        if(mIsShowCircle){
            drawCircle(canvas);
        }
    }

    private void drawXY(Canvas canvas){
        yMaxWidth = getYTextUnitMaxWidth();
        xPadding = mYBorderWidth/2 + yMaxWidth + mYTextPadding;
        //线/2+文字高度+文字边距
        yPadding = mXBorderWidth/2 + mXTextHeight + mXTextPadding;
        //绘制X轴
        canvas.drawLine(xPadding,mHeight-yPadding,
                mWidth,mHeight-yPadding,mXBorderLinePaint);
        //绘制Y轴
        canvas.drawLine(xPadding,mHeight-yPadding,xPadding,0f,mYBorderLinePaint);
    }
    private void drawXText(Canvas canvas){
        //尾部多出半份0.5f
        itemXWidth = (mWidth - xPadding) / (mXTextUnits.size() -0.5f);
        for (int index=0;index<mXTextUnits.size();index++){
            float xPoint = xPadding + index * itemXWidth;
            String s = mXTextUnits.get(index);
            canvas.drawText(s,xPoint - getTextWidth(mXTextPaint,s)/2,
                    mHeight - mXTextPaint.getFontMetrics().descent,mXTextPaint);
            if(mIsShowYBorder){
                //绘制Y方向格子线
                canvas.drawLine(xPoint,mHeight-yPadding,xPoint,0f,mYBorderLinePaint);
            }
        }
    }
    private void drawYText(Canvas canvas){
        //尾部多出半份0.5f
        itemYWidth = (mHeight - yPadding) / (mYTextUnits.size() + 0.5f);
        for (int index=0;index<mYTextUnits.size();index++){
            float yPoint = mHeight - yPadding - (index + 1) * itemYWidth;
            String s = mYTextUnits.get(index);
            //绘制Y轴标注文字,给点偏移量mYTextHeight/4
            canvas.drawText(s,yMaxWidth - getTextWidth(mYTextPaint,s),
                    yPoint + mYTextHeight/4,mYTextPaint);
            if(mIsShowXBorder){
                //绘制X方向格子线
                canvas.drawLine(xPadding,yPoint,mWidth,yPoint,mXBorderLinePaint);
            }
        }

    }
    private void drawSolid(Canvas canvas){
        float xLittleUnit = itemXWidth/mXUnitValue;
        float yLittleUnit = itemYWidth/mYUnitValue;
        mSolidPath.reset();
        mSolidPath.moveTo(xPadding,mHeight - yPadding);
        for (int index=0;index<mDataLists.size();index++){
            MyPoint it = mDataLists.get(index);
            mSolidPath.lineTo(xPadding + xLittleUnit * it.x , mHeight - yPadding - yLittleUnit * it.y);

        }
        mSolidPath.lineTo(mWidth,mHeight - yPadding);
        mSolidPath.close();
        canvas.drawPath(mSolidPath,mSolidPaint);
    }
    private void drawBroken(Canvas canvas){
        float xLittleUnit = itemXWidth/mXUnitValue;
        float yLittleUnit = itemYWidth/mYUnitValue;
        mBrokenPath.reset();
        for (int index=0;index<mDataLists.size();index++){
            MyPoint point = mDataLists.get(index);
            if(index==0){
                mBrokenPath.moveTo(xPadding + xLittleUnit * point.x , mHeight - yPadding - yLittleUnit * point.y);
            }else{
                mBrokenPath.lineTo(xPadding + xLittleUnit * point.x , mHeight - yPadding - yLittleUnit * point.y);
            }
        }
        canvas.drawPath(mBrokenPath,mBrokenLinePaint);
    }
    private void drawCircle(Canvas canvas){
        float xLittleUnit = itemXWidth/mXUnitValue;
        float yLittleUnit = itemYWidth/mYUnitValue;
        for (int index=0;index<mDataLists.size();index++){
            MyPoint it = mDataLists.get(index);
            if(mIsCircleSolid){
                mCirclePaint.setStyle(Paint.Style.FILL);
                mCirclePaint.setColor(Color.WHITE);
                canvas.drawCircle(xPadding + xLittleUnit * it.x,
                        mHeight - yPadding - yLittleUnit * it.y,mCircleRadius,mCirclePaint);
                mCirclePaint.setStyle(Paint.Style.STROKE);
                mCirclePaint.setColor(mCircleColor);
                canvas.drawCircle(xPadding + xLittleUnit * it.x,
                        mHeight - yPadding - yLittleUnit * it.y,mCircleRadius,mCirclePaint);
            }else{
                mCirclePaint.setStyle(Paint.Style.FILL);
                mCirclePaint.setColor(mCircleColor);
                canvas.drawCircle(xPadding + xLittleUnit * it.x,
                        mHeight - yPadding - yLittleUnit * it.y,mCircleRadius,mCirclePaint);
            }
        }

    }
    private Float getTextHeight(TextPaint textPaint){
        if (textPaint != null)
            return textPaint.getFontMetrics().descent - textPaint.getFontMetrics().ascent;
        return 5f;
    }
    private Float getTextWidth(TextPaint textPaint,String s){
        if (textPaint != null)
            return textPaint.measureText(s);
        return 5f;
    }
    private Float getYTextUnitMaxWidth(){
        String itemStr = "";
        for (int i=0;i<mYTextUnits.size();i++){
            String s = mYTextUnits.get(i);
            if (s.length()>itemStr.length()){
                itemStr = s;
            }
        }
        return getTextWidth(mYTextPaint,itemStr);
    }
}
