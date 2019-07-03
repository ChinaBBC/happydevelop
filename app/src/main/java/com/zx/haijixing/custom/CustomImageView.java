package com.zx.haijixing.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.os.Build;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.View;

/**
 *
 *@作者 zx
 *@创建日期 2019/2/18 13:43
 *@描述 自定义圆角imageView
 */
public class CustomImageView extends AppCompatImageView {
    float width,height;

    public CustomImageView(Context context) {
        this(context, null);
    }

    public CustomImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (Build.VERSION.SDK_INT < 18) {
            setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        width = getWidth();
        height = getHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (width > 20 && height > 20) {
            Path path = new Path();
            path.moveTo(20, 0);
            path.lineTo(width - 20, 0);
            path.quadTo(width, 0, width, 20);

            path.lineTo(width, height);
            //path.lineTo(width, height - 20);
            //path.quadTo(width, height, width - 20, height);
            path.lineTo(0, height);
            //path.lineTo(20, height);
            //path.quadTo(0, height, 0, height - 20);

            path.lineTo(0, 20);
            path.quadTo(0, 0, 20, 0);

            canvas.clipPath(path);
        }
        super.onDraw(canvas);
    }

}
