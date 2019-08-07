package com.github.mycardview.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.github.mycardview.R;

/***
 *   created by zhongrui on 2019/7/31
 */
public class CustomCardView extends FrameLayout {
    private int bgColor=Color.TRANSPARENT;
    private ColorStateList backgroundColor;
    private float shadowRadiusLeftTop;
    private float shadowRadiusRightTop;
    private float shadowRadiusRightBottom;
    private float shadowRadiusLeftBottom;
    private float shadowAlpha;
    private float shadowWidth;
    private float shadowOffsetLeft;
    private float shadowOffsetTop;
    private float shadowOffsetRight;
    private float shadowOffsetBottom;
    private int shadowStartColor;
    private int shadowCenterColor;
    private int shadowEndColor;
    private float shadowStartColorWeight;
    private float shadowCenterColorWeight;
    private float shadowEndColorWeight;
    private CustomDrawable shadowDrawable;

    public CustomCardView(Context context) {
        super(context);
        init(null, R.attr.CustomCardViewStyle);
    }

    public CustomCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, R.attr.CustomCardViewStyle);
    }

    public CustomCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr);
    }

    private void init(AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.CustomCardView, defStyleAttr, R.style.CustomCardView);
        ColorStateList colorStateList;
        if (typedArray.hasValue(R.styleable.CustomCardView_bgColor)) {
            colorStateList = typedArray.getColorStateList(R.styleable.CustomCardView_bgColor);
        } else {
            TypedArray array = getContext().obtainStyledAttributes(new int[]{android.R.attr.colorBackground});
            colorStateList = ColorStateList.valueOf(array.getColor(0, Color.WHITE));
            array.recycle();
        }

        float shadowRadius = typedArray.getDimension(R.styleable.CustomCardView_shadowRadius, dp2px(10));
        shadowRadiusLeftTop = typedArray.getDimension(R.styleable.CustomCardView_shadowRadiusLeftTop, shadowRadius);
        shadowRadiusRightTop = typedArray.getDimension(R.styleable.CustomCardView_shadowRadiusRightTop, shadowRadius);
        shadowRadiusRightBottom = typedArray.getDimension(R.styleable.CustomCardView_shadowRadiusRightBottom, shadowRadius);
        shadowRadiusLeftBottom = typedArray.getDimension(R.styleable.CustomCardView_shadowRadiusLeftBottom, shadowRadius);
        shadowAlpha = typedArray.getDimension(R.styleable.CustomCardView_shadowAlpha, 1);
        shadowWidth = typedArray.getDimension(R.styleable.CustomCardView_shadowWidth, dp2px(10));
        shadowOffsetLeft = typedArray.getDimension(R.styleable.CustomCardView_shadowOffsetLeft, 0);
        shadowOffsetTop = typedArray.getDimension(R.styleable.CustomCardView_shadowOffsetTop, 0);
        shadowOffsetRight = typedArray.getDimension(R.styleable.CustomCardView_shadowOffsetRight, 0);
        shadowOffsetBottom = typedArray.getDimension(R.styleable.CustomCardView_shadowOffsetBottom, 0);

        shadowStartColor = typedArray.getColor(R.styleable.CustomCardView_shadowStartColor, ContextCompat.getColor(getContext(), R.color.customCardView_default_shadowStartColor));
        shadowCenterColor = typedArray.getColor(R.styleable.CustomCardView_shadowCenterColor, Color.TRANSPARENT);
        shadowEndColor = typedArray.getColor(R.styleable.CustomCardView_shadowEndColor, ContextCompat.getColor(getContext(), R.color.customCardView_default_shadowEndColor));

        shadowStartColorWeight = typedArray.getDimension(R.styleable.CustomCardView_shadowStartColorWeight, 1);
        shadowCenterColorWeight = typedArray.getDimension(R.styleable.CustomCardView_shadowCenterColorWeight, 1);
        shadowEndColorWeight = typedArray.getDimension(R.styleable.CustomCardView_shadowEndColorWeight, 1);

        typedArray.recycle();


        if (shadowAlpha > 1) {
            shadowAlpha = 1;
        } else if (shadowAlpha < 0) {
            shadowAlpha = 0;
        }

        shadowDrawable = new CustomDrawable();
        shadowDrawable.setBackground(colorStateList);
        shadowDrawable.setAlpha((int) (shadowAlpha * 255));

        setBackground(shadowDrawable);

    }

    public class CustomDrawable extends Drawable{
        private Paint bgPaint;
        private Paint cornerShadowPaint;
        private Paint shadowPaint;

        private boolean needComputeRect=true;
        private Rect bgRect;
        private Path cornerLeftTopShadowPath;
        private Path cornerRightTopShadowPath;
        private Path cornerLeftBottomShadowPath;
        private Path cornerRightBottomShadowPath;

        private RadialGradient leftTopGradient;
        private RadialGradient rightTopGradient;
        private RadialGradient rightBottomGradient;
        private RadialGradient leftBottomGradient;

        public CustomDrawable() {
            bgPaint =new Paint(Paint.ANTI_ALIAS_FLAG|Paint.DITHER_FLAG);
            bgPaint.setStyle(Paint.Style.FILL);

            cornerShadowPaint=new Paint(Paint.ANTI_ALIAS_FLAG|Paint.DITHER_FLAG);
            shadowPaint=new Paint(Paint.ANTI_ALIAS_FLAG|Paint.DITHER_FLAG);
        }
        private void setBackground(ColorStateList color){
            backgroundColor=(color==null)?ColorStateList.valueOf(Color.WHITE):color;
            bgPaint.setColor(backgroundColor.getColorForState(getState(),backgroundColor.getDefaultColor()));
        }

        @Override
        public void draw( Canvas canvas) {
            if(needComputeRect){
                computeBgRect();
                needComputeRect=false;
            }
            drawShadow(canvas);
        }

        private void drawShadow(Canvas canvas) {

            //左上
            canvas.save();

            canvas.restore();

            //右上
            canvas.save();

            canvas.restore();


            //左下
            canvas.save();

            canvas.restore();

            //右下
            canvas.save();

            canvas.restore();
        }

        @Override
        public void setAlpha(int alpha) {
            cornerShadowPaint.setAlpha(alpha);
            shadowPaint.setAlpha(alpha);

        }
        @Override
        public void setColorFilter( ColorFilter colorFilter) {
            cornerShadowPaint.setColorFilter(colorFilter);
            shadowPaint.setColorFilter(colorFilter);
        }
        @Override
        public int getOpacity() {
            return PixelFormat.TRANSLUCENT;
        }

        private void computeBgRect() {
            Rect bounds = getBounds();
            bgRect=new Rect(
                    (int)(bounds.left+shadowWidth-shadowOffsetLeft),
                    (int)(bounds.top+shadowWidth- shadowOffsetTop),
                    (int)(bounds.right-shadowWidth+ shadowOffsetRight),
                    (int)(bounds.bottom-shadowWidth+ shadowOffsetBottom)
            );


            setCornerLeftTopShadowPath();
            setCornerRightTopShadowPath();
            setCornerLeftBottomShadowPath();
            setCornerRightBottomShadowPath();

            /*
        mCornerShadowPaint.setShader(new RadialGradient(0, 0, mCornerRadius + mShadowSize,
                new int[]{mShadowStartColor, mShadowStartColor, mShadowEndColor},
                new float[]{0f, startRatio, 1f},
                Shader.TileMode.CLAMP));

        mEdgeShadowPaint.setShader(new LinearGradient(0, -mCornerRadius + mShadowSize, 0,
                -mCornerRadius - mShadowSize,
                new int[]{mShadowStartColor, mShadowStartColor, mShadowEndColor},
                new float[]{0f, .5f, 1f}, Shader.TileMode.CLAMP));*/



            cornerShadowPaint.setShader(leftTopGradient);
            shadowPaint.setShader(null);
        }

        private void setCornerLeftTopShadowPath() {
            if (cornerLeftTopShadowPath == null) {
                cornerLeftTopShadowPath = new Path();
            } else {
                cornerLeftTopShadowPath.reset();
            }
//            cornerLeftTopShadowPath.setFillType(Path.FillType.EVEN_ODD);
            float reallyShadowRadius= Math.max(getShadowRadiusLeftTop(),getShadowWidth());
            cornerLeftTopShadowPath.moveTo(reallyShadowRadius,reallyShadowRadius);
            cornerLeftTopShadowPath.arcTo(new RectF(0,0,reallyShadowRadius*2,reallyShadowRadius*2),180,90);
            cornerLeftTopShadowPath.close();

            int []colors={Color.WHITE};
            if(true){
            }else{

            }
            float b[]={1};
            leftTopGradient=new RadialGradient(reallyShadowRadius,reallyShadowRadius,reallyShadowRadius,colors,b,Shader.TileMode.CLAMP);
            rightTopGradient=new RadialGradient(reallyShadowRadius,reallyShadowRadius,reallyShadowRadius,colors,b,Shader.TileMode.CLAMP);
            rightBottomGradient=new RadialGradient(reallyShadowRadius,reallyShadowRadius,reallyShadowRadius,colors,b,Shader.TileMode.CLAMP);
            leftBottomGradient=new RadialGradient(reallyShadowRadius,reallyShadowRadius,reallyShadowRadius,colors,b,Shader.TileMode.CLAMP);
        }
        private void setCornerRightTopShadowPath() {
            Rect bounds = getBounds();
            if (cornerRightTopShadowPath == null) {
                cornerRightTopShadowPath = new Path();
            } else {
                cornerRightTopShadowPath.reset();
            }
//            cornerRightTopShadowPath.setFillType(Path.FillType.EVEN_ODD);
            float reallyShadowRadius= Math.max(getShadowRadiusRightTop(),getShadowWidth());
            cornerRightTopShadowPath.moveTo((bounds.right-bounds.left)-reallyShadowRadius,reallyShadowRadius);
            cornerRightTopShadowPath.arcTo(new RectF(0,0,reallyShadowRadius*2,reallyShadowRadius*2),0,-90);
            cornerRightTopShadowPath.close();
        }
        private void setCornerRightBottomShadowPath() {
            Rect bounds = getBounds();
            if (cornerRightBottomShadowPath == null) {
                cornerRightBottomShadowPath = new Path();
            } else {
                cornerRightBottomShadowPath.reset();
            }
//            cornerRightBottomShadowPath.setFillType(Path.FillType.EVEN_ODD);
            float reallyShadowRadius= Math.max(getShadowRadiusRightBottom(),getShadowWidth());
            cornerRightBottomShadowPath.moveTo((bounds.right-bounds.left)-reallyShadowRadius,(bounds.bottom-bounds.top)-reallyShadowRadius);
            cornerRightBottomShadowPath.arcTo(new RectF(0,0,reallyShadowRadius*2,reallyShadowRadius*2),0,90);
            cornerRightBottomShadowPath.close();
        }
        private void setCornerLeftBottomShadowPath() {
            Rect bounds = getBounds();
            if (cornerLeftBottomShadowPath == null) {
                cornerLeftBottomShadowPath = new Path();
            } else {
                cornerLeftBottomShadowPath.reset();
            }
//            cornerLeftBottomShadowPath.setFillType(Path.FillType.EVEN_ODD);
            float reallyShadowRadius= Math.max(getShadowRadiusLeftBottom(),getShadowWidth());
            cornerLeftBottomShadowPath.moveTo(reallyShadowRadius,(bounds.bottom-bounds.top)-reallyShadowRadius);
            cornerLeftBottomShadowPath.arcTo(new RectF(0,0,reallyShadowRadius*2,reallyShadowRadius*2),90,90);
            cornerLeftBottomShadowPath.close();
        }
    }


    public int getBgColor() {
        return bgColor;
    }

    public void setBgColor(int bgColor) {
        this.bgColor = bgColor;
//        shadowDrawable.setBgColor(bgColor);
    }



    public void setShadowRadius(float shadowRadius) {
        setShadowRadiusLeftTop(shadowRadius);
        setShadowRadiusRightTop(shadowRadius);
        setShadowRadiusRightBottom(shadowRadius);
        setShadowRadiusLeftBottom(shadowRadius);
//        shadowDrawable.setShadowRadius(shadowRadius);
    }

    public float getShadowRadiusLeftTop() {
        return shadowRadiusLeftTop;
    }

    public void setShadowRadiusLeftTop(float shadowRadiusLeftTop) {
        this.shadowRadiusLeftTop = shadowRadiusLeftTop;
//        shadowDrawable.setShadowRadiusLeftTop(shadowRadiusLeftTop);
    }

    public float getShadowRadiusRightTop() {
        return shadowRadiusRightTop;
    }

    public void setShadowRadiusRightTop(float shadowRadiusRightTop) {
        this.shadowRadiusRightTop = shadowRadiusRightTop;
//        shadowDrawable.setShadowRadiusRightTop(shadowRadiusRightTop);
    }

    public float getShadowRadiusRightBottom() {
        return shadowRadiusRightBottom;
    }

    public void setShadowRadiusRightBottom(float shadowRadiusRightBottom) {
        this.shadowRadiusRightBottom = shadowRadiusRightBottom;
//        shadowDrawable.setShadowRadiusRightBottom(shadowRadiusRightBottom);
    }

    public float getShadowRadiusLeftBottom() {
        return shadowRadiusLeftBottom;
    }

    public void setShadowRadiusLeftBottom(float shadowRadiusLeftBottom) {
        this.shadowRadiusLeftBottom = shadowRadiusLeftBottom;
//        shadowDrawable.setShadowRadiusLeftBottom(shadowRadiusLeftBottom);
    }

    public float getShadowAlpha() {
        return shadowAlpha;
    }

    public void setShadowAlpha(float shadowAlpha) {
        this.shadowAlpha = shadowAlpha;
//        shadowDrawable.setShadowAlpha(shadowAlpha);
    }

    public float getShadowWidth() {
        return shadowWidth;
    }

    public void setShadowWidth(float shadowWidth) {
        this.shadowWidth = shadowWidth;
//        shadowDrawable.setShadowWidth(shadowWidth);
    }

    public float getShadowOffsetLeft() {
        return shadowOffsetLeft;
    }

    public void setShadowOffsetLeft(float shadowOffsetLeft) {
        this.shadowOffsetLeft = shadowOffsetLeft;
//        shadowDrawable.setShadowOffsetLeft(shadowOffsetLeft);
    }

    public float getShadowOffsetTop() {
        return shadowOffsetTop;
    }

    public void setShadowOffsetTop(float shadowOffsetTop) {
        this.shadowOffsetTop = shadowOffsetTop;
//        shadowDrawable.setShadowOffsetTop(shadowOffsetTop);
    }

    public int getShadowStartColor() {
        return shadowStartColor;
    }

    public void setShadowStartColor(int shadowStartColor) {
        this.shadowStartColor = shadowStartColor;
//        shadowDrawable.setShadowStartColor(shadowStartColor);
    }

    public int getShadowCenterColor() {
        return shadowCenterColor;
    }

    public void setShadowCenterColor(int shadowCenterColor) {
        this.shadowCenterColor = shadowCenterColor;
//        shadowDrawable.setShadowCenterColor(shadowCenterColor);
    }

    public int getShadowEndColor() {
        return shadowEndColor;
    }

    public void setShadowEndColor(int shadowEndColor) {
        this.shadowEndColor = shadowEndColor;
//        shadowDrawable.setShadowEndColor(shadowEndColor);
    }

    public float getShadowStartColorWeight() {
        return shadowStartColorWeight;
    }

    public void setShadowStartColorWeight(float shadowStartColorWeight) {
        this.shadowStartColorWeight = shadowStartColorWeight;
//        shadowDrawable.setShadowStartColorWeight(shadowStartColorWeight);
    }

    public float getShadowCenterColorWeight() {
        return shadowCenterColorWeight;
    }

    public void setShadowCenterColorWeight(float shadowCenterColorWeight) {
        this.shadowCenterColorWeight = shadowCenterColorWeight;
//        shadowDrawable.setShadowCenterColorWeight(shadowCenterColorWeight);
    }

    public float getShadowEndColorWeight() {
        return shadowEndColorWeight;
    }

    public void setShadowEndColorWeight(float shadowEndColorWeight) {
        this.shadowEndColorWeight = shadowEndColorWeight;
//        shadowDrawable.setShadowEndColorWeight(shadowEndColorWeight);
    }

    private int dp2px(int value) {
        return (int) (getContext().getResources().getDisplayMetrics().density * value);
    }

}
