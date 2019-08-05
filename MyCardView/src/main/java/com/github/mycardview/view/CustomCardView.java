package com.github.mycardview.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.Switch;

import com.github.mycardview.R;

/***
 *   created by zhongrui on 2019/7/31
 */
public class CustomCardView extends FrameLayout {
    private int bgColor=Color.TRANSPARENT;
    private ColorStateList backgroundColor;
    private float shadowRadius;
    private float shadowRadiusLeft;
    private float shadowRadiusTop;
    private float shadowRadiusRight;
    private float shadowRadiusBottom;
    private float shadowAlpha;
    private float shadowWidth;
    private float shadowOffsetX;
    private float shadowOffsetY;
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

        shadowRadius = typedArray.getDimension(R.styleable.CustomCardView_shadowRadius, dp2px(10));
        shadowRadiusLeft = typedArray.getDimension(R.styleable.CustomCardView_shadowRadiusLeft, 0);
        shadowRadiusTop = typedArray.getDimension(R.styleable.CustomCardView_shadowRadiusTop, 0);
        shadowRadiusRight = typedArray.getDimension(R.styleable.CustomCardView_shadowRadiusRight, 0);
        shadowRadiusBottom = typedArray.getDimension(R.styleable.CustomCardView_shadowRadiusBottom, 0);
        shadowAlpha = typedArray.getDimension(R.styleable.CustomCardView_shadowAlpha, 1);
        shadowWidth = typedArray.getDimension(R.styleable.CustomCardView_shadowWidth, dp2px(10));
        shadowOffsetX = typedArray.getDimension(R.styleable.CustomCardView_shadowOffsetX, 0);
        shadowOffsetY = typedArray.getDimension(R.styleable.CustomCardView_shadowOffsetY, 0);

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
    }

    public int getBgColor() {
        return bgColor;
    }

    public void setBgColor(int bgColor) {
        this.bgColor = bgColor;
//        shadowDrawable.setBgColor(bgColor);
    }

    public float getShadowRadius() {
        return shadowRadius;
    }

    public void setShadowRadius(float shadowRadius) {
        this.shadowRadius = shadowRadius;
//        shadowDrawable.setShadowRadius(shadowRadius);
    }

    public float getShadowRadiusLeft() {
        return shadowRadiusLeft;
    }

    public void setShadowRadiusLeft(float shadowRadiusLeft) {
        this.shadowRadiusLeft = shadowRadiusLeft;
//        shadowDrawable.setShadowRadiusLeft(shadowRadiusLeft);
    }

    public float getShadowRadiusTop() {
        return shadowRadiusTop;
    }

    public void setShadowRadiusTop(float shadowRadiusTop) {
        this.shadowRadiusTop = shadowRadiusTop;
//        shadowDrawable.setShadowRadiusTop(shadowRadiusTop);
    }

    public float getShadowRadiusRight() {
        return shadowRadiusRight;
    }

    public void setShadowRadiusRight(float shadowRadiusRight) {
        this.shadowRadiusRight = shadowRadiusRight;
//        shadowDrawable.setShadowRadiusRight(shadowRadiusRight);
    }

    public float getShadowRadiusBottom() {
        return shadowRadiusBottom;
    }

    public void setShadowRadiusBottom(float shadowRadiusBottom) {
        this.shadowRadiusBottom = shadowRadiusBottom;
//        shadowDrawable.setShadowRadiusBottom(shadowRadiusBottom);
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

    public float getShadowOffsetX() {
        return shadowOffsetX;
    }

    public void setShadowOffsetX(float shadowOffsetX) {
        this.shadowOffsetX = shadowOffsetX;
//        shadowDrawable.setShadowOffsetX(shadowOffsetX);
    }

    public float getShadowOffsetY() {
        return shadowOffsetY;
    }

    public void setShadowOffsetY(float shadowOffsetY) {
        this.shadowOffsetY = shadowOffsetY;
//        shadowDrawable.setShadowOffsetY(shadowOffsetY);
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

    public class CustomDrawable extends Drawable{
        private Paint paint;
        private boolean needComputeRect=true;
        private Rect drawableRect;

        public CustomDrawable() {
            paint=new Paint(Paint.ANTI_ALIAS_FLAG|Paint.DITHER_FLAG);
        }
        private void setBackground(ColorStateList color){
            backgroundColor=(color==null)?ColorStateList.valueOf(Color.WHITE):color;
            paint.setColor(backgroundColor.getColorForState(getState(),backgroundColor.getDefaultColor()));
        }

        @Override
        public void draw( Canvas canvas) {
            if(needComputeRect){
                computeRect();
                needComputeRect=false;
            }
        }
        @Override
        public void setAlpha(int alpha) {
            paint.setAlpha(alpha);
        }
        @Override
        public void setColorFilter( ColorFilter colorFilter) {
            paint.setColorFilter(colorFilter);
        }
        @Override
        public int getOpacity() {
            return PixelFormat.TRANSLUCENT;
        }
    }

    private void computeRect() {

    }
}
