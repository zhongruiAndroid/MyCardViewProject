//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.test.mycardview.test;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;

class CardViewBaseImpl implements CardViewImpl {
    final RectF mCornerRect = new RectF();

    CardViewBaseImpl() {
    }

    public void initStatic() {
        RoundRectDrawableWithShadow.sRoundRectHelper = new RoundRectDrawableWithShadow.RoundRectHelper() {
            public void drawRoundRect(Canvas canvas, RectF bounds, float cornerRadius, Paint paint) {
                float twoRadius = cornerRadius * 2.0F;
                float innerWidth = bounds.width() - twoRadius - 1.0F;
                float innerHeight = bounds.height() - twoRadius - 1.0F;
                if (cornerRadius >= 1.0F) {
                    float roundedCornerRadius = cornerRadius + 0.5F;
                    CardViewBaseImpl.this.mCornerRect.set(-roundedCornerRadius, -roundedCornerRadius, roundedCornerRadius, roundedCornerRadius);
                    int saved = canvas.save();
                    canvas.translate(bounds.left + roundedCornerRadius, bounds.top + roundedCornerRadius);
                    canvas.drawArc(CardViewBaseImpl.this.mCornerRect, 180.0F, 90.0F, true, paint);
                    canvas.translate(innerWidth, 0.0F);
                    canvas.rotate(90.0F);
                    canvas.drawArc(CardViewBaseImpl.this.mCornerRect, 180.0F, 90.0F, true, paint);
                    canvas.translate(innerHeight, 0.0F);
                    canvas.rotate(90.0F);
                    canvas.drawArc(CardViewBaseImpl.this.mCornerRect, 180.0F, 90.0F, true, paint);
                    canvas.translate(innerWidth, 0.0F);
                    canvas.rotate(90.0F);
                    canvas.drawArc(CardViewBaseImpl.this.mCornerRect, 180.0F, 90.0F, true, paint);
                    canvas.restoreToCount(saved);
                    canvas.drawRect(bounds.left + roundedCornerRadius - 1.0F, bounds.top, bounds.right - roundedCornerRadius + 1.0F, bounds.top + roundedCornerRadius, paint);
                    canvas.drawRect(bounds.left + roundedCornerRadius - 1.0F, bounds.bottom - roundedCornerRadius, bounds.right - roundedCornerRadius + 1.0F, bounds.bottom, paint);
                }

                canvas.drawRect(bounds.left, bounds.top + cornerRadius, bounds.right, bounds.bottom - cornerRadius, paint);
            }
        };
    }

    public void initialize(CardViewDelegate cardView, Context context, ColorStateList backgroundColor, float radius, float elevation, float maxElevation) {
        RoundRectDrawableWithShadow background = this.createBackground(context, backgroundColor, radius, elevation, maxElevation);
        background.setAddPaddingForCorners(cardView.getPreventCornerOverlap());
        cardView.setCardBackground(background);
        this.updatePadding(cardView);
    }

    private RoundRectDrawableWithShadow createBackground(Context context, ColorStateList backgroundColor, float radius, float elevation, float maxElevation) {
        return new RoundRectDrawableWithShadow(context.getResources(), backgroundColor, radius, elevation, maxElevation);
    }

    public void updatePadding(CardViewDelegate cardView) {
        Rect shadowPadding = new Rect();
        this.getShadowBackground(cardView).getMaxShadowAndCornerPadding(shadowPadding);
        cardView.setMinWidthHeightInternal((int)Math.ceil((double)this.getMinWidth(cardView)), (int)Math.ceil((double)this.getMinHeight(cardView)));
        cardView.setShadowPadding(shadowPadding.left, shadowPadding.top, shadowPadding.right, shadowPadding.bottom);
    }

    public void onCompatPaddingChanged(CardViewDelegate cardView) {
    }

    public void onPreventCornerOverlapChanged(CardViewDelegate cardView) {
        this.getShadowBackground(cardView).setAddPaddingForCorners(cardView.getPreventCornerOverlap());
        this.updatePadding(cardView);
    }

    public void setBackgroundColor(CardViewDelegate cardView, @Nullable ColorStateList color) {
        this.getShadowBackground(cardView).setColor(color);
    }

    public ColorStateList getBackgroundColor(CardViewDelegate cardView) {
        return this.getShadowBackground(cardView).getColor();
    }

    public void setRadius(CardViewDelegate cardView, float radius) {
        this.getShadowBackground(cardView).setCornerRadius(radius);
        this.updatePadding(cardView);
    }

    public float getRadius(CardViewDelegate cardView) {
        return this.getShadowBackground(cardView).getCornerRadius();
    }

    public void setElevation(CardViewDelegate cardView, float elevation) {
        this.getShadowBackground(cardView).setShadowSize(elevation);
    }

    public float getElevation(CardViewDelegate cardView) {
        return this.getShadowBackground(cardView).getShadowSize();
    }

    public void setMaxElevation(CardViewDelegate cardView, float maxElevation) {
        this.getShadowBackground(cardView).setMaxShadowSize(maxElevation);
        this.updatePadding(cardView);
    }

    public float getMaxElevation(CardViewDelegate cardView) {
        return this.getShadowBackground(cardView).getMaxShadowSize();
    }

    public float getMinWidth(CardViewDelegate cardView) {
        return this.getShadowBackground(cardView).getMinWidth();
    }

    public float getMinHeight(CardViewDelegate cardView) {
        return this.getShadowBackground(cardView).getMinHeight();
    }

    private RoundRectDrawableWithShadow getShadowBackground(CardViewDelegate cardView) {
        return (RoundRectDrawableWithShadow)cardView.getCardBackground();
    }
}
