package com.test.mycardview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.os.Build;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

public class YanHuaImageView extends AppCompatImageView {
    public Path clipPath;
    public Paint clipPaint;
    private Path tempPath;

    private int topLeftRadius;
    private int topRightRadius;
    private int bottomLeftRadius;
    private int bottomRightRadius;

    public YanHuaImageView(Context context) {
        super(context);
        init(null);
    }

    public YanHuaImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public YanHuaImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        initPaint();

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.YanHuaImageView);

        int clipRadius = (int) typedArray.getDimension(R.styleable.YanHuaImageView_radius, 0);
        if (clipRadius > 0) {
            topLeftRadius = clipRadius;
            topRightRadius = clipRadius;
            bottomLeftRadius = clipRadius;
            bottomRightRadius = clipRadius;
        } else {
            topLeftRadius = (int) typedArray.getDimension(R.styleable.YanHuaImageView_topLeftRadius, 0);
            topRightRadius = (int) typedArray.getDimension(R.styleable.YanHuaImageView_topRightRadius, 0);
            bottomLeftRadius = (int) typedArray.getDimension(R.styleable.YanHuaImageView_bottomLeftRadius, 0);
            bottomRightRadius = (int) typedArray.getDimension(R.styleable.YanHuaImageView_bottomRightRadius, 0);
        }


        typedArray.recycle();

    }

    private void initPaint() {
        clipPath = new Path();
        clipPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        clipPaint.setStyle(Paint.Style.FILL);
        clipPaint.setColor(Color.WHITE);
        if(isInEditMode()|| Build.VERSION.SDK_INT<=Build.VERSION_CODES.O_MR1){
            clipPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        }else{
            clipPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (clipPath != null) {
            clipPath.reset();
        }
        int left = getPaddingLeft();
        int top = getPaddingTop();
        int right = getPaddingRight();
        int bottom = getPaddingBottom();

        float[] radius = new float[8];
        radius[0] = topLeftRadius;
        radius[1] = topLeftRadius;
        radius[2] = topRightRadius;
        radius[3] = topRightRadius;
        radius[4] = bottomRightRadius;
        radius[5] = bottomRightRadius;
        radius[6] = bottomLeftRadius;
        radius[7] = bottomLeftRadius;

        RectF rectF = new RectF(left, top, w - right, h - bottom);
        clipPath.addRoundRect(rectF, radius, Path.Direction.CW);
        clipPath.moveTo(0, 0);
        clipPath.moveTo(w, h);
        if(isInEditMode()||Build.VERSION.SDK_INT<=Build.VERSION_CODES.O_MR1) {

        }else{
            //Build.VERSION.SDK_INT>=28
            if (tempPath == null) {
                tempPath = new Path();
            } else {
                tempPath.reset();
            }
            tempPath.addRect(new RectF(0, 0, w, h), Path.Direction.CW);
            clipPath.op(tempPath, Path.Op.XOR);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int saveLayerCount = canvas.saveLayer(new RectF(0, 0, canvas.getWidth(), canvas.getHeight()), null, Canvas.ALL_SAVE_FLAG);
        super.onDraw(canvas);
        //通过setXfermode裁剪出需要显示部分区域
        canvas.drawPath(clipPath, clipPaint);
        canvas.restoreToCount(saveLayerCount);
    }
}
