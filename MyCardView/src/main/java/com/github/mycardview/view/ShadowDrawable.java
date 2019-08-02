package com.github.mycardview.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

/***
 *   created by android on 2019/7/31
 */
public class ShadowDrawable extends Drawable implements CardViewInter {
    private Paint paint;
    private boolean needDraw=true;
    private Rect drawableRect;

    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        needDraw=true;
    }

    public ShadowDrawable(ColorStateList backgroundColor) {
        paint=new Paint(Paint.ANTI_ALIAS_FLAG| Paint.DITHER_FLAG);
    }
    @Override
    public void draw( Canvas canvas) {
        if(needDraw){
            buildBound(getBounds());
            needDraw=false;
        }
    }

    private void buildBound(Rect bounds) {

    }

    @Override
    public void setAlpha(int alpha) {
        paint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        paint.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    @Override
    public void setBgColor(int bgColor) {

    }

    @Override
    public void setShadowRadius(float shadowRadius) {

    }

    @Override
    public void setShadowRadiusLeft(float shadowRadiusLeft) {

    }

    @Override
    public void setShadowRadiusTop(float shadowRadiusTop) {

    }

    @Override
    public void setShadowRadiusRight(float shadowRadiusRight) {

    }

    @Override
    public void setShadowRadiusBottom(float shadowRadiusBottom) {

    }

    @Override
    public void setShadowAlpha(float shadowAlpha) {

    }

    @Override
    public void setShadowWidth(float shadowWidth) {

    }

    @Override
    public void setShadowOffsetX(float shadowOffsetX) {

    }

    @Override
    public void setShadowOffsetY(float shadowOffsetY) {

    }

    @Override
    public void setShadowStartColor(int shadowStartColor) {

    }

    @Override
    public void setShadowCenterColor(int shadowCenterColor) {

    }

    @Override
    public void setShadowEndColor(int shadowEndColor) {

    }

    @Override
    public void setShadowStartColorWeight(float shadowStartColorWeight) {

    }

    @Override
    public void setShadowCenterColorWeight(float shadowCenterColorWeight) {

    }

    @Override
    public void setShadowEndColorWeight(float shadowEndColorWeight) {

    }
}
