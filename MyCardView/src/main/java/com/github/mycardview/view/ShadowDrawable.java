package com.github.mycardview.view;

import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;

/***
 *   created by android on 2019/7/31
 */
public class ShadowDrawable extends Drawable {
    private Paint paint;

    public ShadowDrawable(ColorStateList backgroundColor) {
        paint=new Paint(Paint.ANTI_ALIAS_FLAG| Paint.DITHER_FLAG);
    }
    @Override
    public void draw( Canvas canvas) {

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
}
