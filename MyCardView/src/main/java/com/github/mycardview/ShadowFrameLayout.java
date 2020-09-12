package com.github.mycardview;

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.FloatRange;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/***
 *   created by zhongrui on 2019/7/31
 */
public class ShadowFrameLayout extends FrameLayout {
    private float bgRadiusLeftTop;
    private float bgRadiusRightTop;
    private float bgRadiusRightBottom;
    private float bgRadiusLeftBottom;

    private ColorStateList backgroundColor;
    private float shadowAlpha;
    private float shadowWidth;
    private float shadowOffsetLeft;
    private float shadowOffsetTop;
    private float shadowOffsetRight;
    private float shadowOffsetBottom;
    private int shadowStartColor;


    private int shadowEndColor;
    private float shadowClipOutLength;
    private float shadowClipInLength;
    private boolean onlyLinear = false;

    private float controlPointFirstY = 0.8f;

    private float controlPointSecondY = 1f;

    private CustomDrawable shadowDrawable;

    public ShadowFrameLayout(Context context) {
        this(context, null);
    }

    public ShadowFrameLayout(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.ShadowFrameLayoutStyle);
    }

    public ShadowFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr);
    }

    private void init(AttributeSet attrs, int defStyleAttr) {

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ShadowFrameLayout, defStyleAttr, R.style.ShadowLayout);

        ColorStateList colorStateList;
        if (typedArray.hasValue(R.styleable.ShadowFrameLayout_bgColor)) {
            colorStateList = typedArray.getColorStateList(R.styleable.ShadowFrameLayout_bgColor);
        } else {
            TypedArray array = getContext().obtainStyledAttributes(new int[]{android.R.attr.colorBackground});
            colorStateList = ColorStateList.valueOf(array.getColor(0, Color.WHITE));
            array.recycle();
        }


        float bgRadius = typedArray.getDimension(R.styleable.ShadowFrameLayout_bgRadius, dp2px(5));
        bgRadiusLeftTop = typedArray.getDimension(R.styleable.ShadowFrameLayout_bgRadiusLeftTop, bgRadius);
        bgRadiusRightTop = typedArray.getDimension(R.styleable.ShadowFrameLayout_bgRadiusRightTop, bgRadius);
        bgRadiusRightBottom = typedArray.getDimension(R.styleable.ShadowFrameLayout_bgRadiusRightBottom, bgRadius);
        bgRadiusLeftBottom = typedArray.getDimension(R.styleable.ShadowFrameLayout_bgRadiusLeftBottom, bgRadius);

        shadowAlpha = typedArray.getFloat(R.styleable.ShadowFrameLayout_shadowAlpha, 1);
        shadowWidth = typedArray.getDimension(R.styleable.ShadowFrameLayout_shadowWidth, R.dimen.customCardView_default_shadowWidth);
        shadowWidth = (int) (shadowWidth + 0.5f);
        shadowOffsetLeft = typedArray.getDimension(R.styleable.ShadowFrameLayout_shadowOffsetLeft, 0);
        shadowOffsetTop = typedArray.getDimension(R.styleable.ShadowFrameLayout_shadowOffsetTop, 0);
        shadowOffsetRight = typedArray.getDimension(R.styleable.ShadowFrameLayout_shadowOffsetRight, 0);
        shadowOffsetBottom = typedArray.getDimension(R.styleable.ShadowFrameLayout_shadowOffsetBottom, 0);

        shadowStartColor = typedArray.getColor(R.styleable.ShadowFrameLayout_shadowStartColor, ContextCompat.getColor(getContext(), R.color.customCardView_default_shadowStartColor));
        shadowEndColor = typedArray.getColor(R.styleable.ShadowFrameLayout_shadowEndColor, ContextCompat.getColor(getContext(), R.color.customCardView_default_shadowEndColor));


        onlyLinear = typedArray.getBoolean(R.styleable.ShadowFrameLayout_onlyLinear, false);
        shadowClipOutLength = typedArray.getDimension(R.styleable.ShadowFrameLayout_shadowClipOutLength, 0);
        shadowClipOutLength = (int) (shadowClipOutLength + 0.5f);
        shadowClipInLength = typedArray.getDimension(R.styleable.ShadowFrameLayout_shadowClipInLength, 0);
        shadowClipInLength = (int) (shadowClipInLength + 0.5f);
        controlPointFirstY= typedArray.getFloat(R.styleable.ShadowFrameLayout_controlPointFirstY,R.fraction.customCardView_default_controlPointFirstY);
        controlPointSecondY = typedArray.getFloat(R.styleable.ShadowFrameLayout_controlPointSecondY,R.fraction.customCardView_default_controlPointSecondY);


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

        setPadding(getPaddingLeft(), getPaddingTop(), getPaddingRight(), getPaddingBottom());
    }

    @Override
    public void setPadding(int left, int top, int right, int bottom) {
        left = (int) (left + getShadowWidth() - getShadowOffsetLeft());
        top = (int) (top + getShadowWidth() - getShadowOffsetTop());
        right = (int) (right + getShadowWidth() - getShadowOffsetRight());
        bottom = (int) (bottom + getShadowWidth() - getShadowOffsetBottom());
        super.setPadding(left, top, right, bottom);
    }

    @Override
    public void setPaddingRelative(int start, int top, int end, int bottom) {
        start = (int) (start + getShadowWidth() - getShadowOffsetLeft());
        top = (int) (top + getShadowWidth() - getShadowOffsetTop());
        end = (int) (end + getShadowWidth() - getShadowOffsetRight());
        bottom = (int) (bottom + getShadowWidth() - getShadowOffsetBottom());
        super.setPaddingRelative(start, top, end, bottom);
    }

    @Override
    public int getPaddingBottom() {
        return (int) (super.getPaddingBottom() - getShadowWidth() + getShadowOffsetBottom());
    }

    @Override
    public int getPaddingTop() {
        return (int) (super.getPaddingTop() - getShadowWidth() + getShadowOffsetTop());
    }

    @Override
    public int getPaddingLeft() {
        return (int) (super.getPaddingLeft() - getShadowWidth() + getShadowOffsetLeft());
    }

    @Override
    public int getPaddingRight() {
        return (int) (super.getPaddingRight() - getShadowWidth() + getShadowOffsetRight());
    }

    @Override
    public int getPaddingStart() {
        if (getLayoutDirection() == LAYOUT_DIRECTION_RTL) {
            return getPaddingRight();
        } else {
            return getPaddingLeft();
        }
    }

    @Override
    public int getPaddingEnd() {
        if (getLayoutDirection() == LAYOUT_DIRECTION_RTL) {
            return getPaddingLeft();
        } else {
            return getPaddingRight();
        }
    }

    public class CustomDrawable extends Drawable {
        private Paint bgPaint;
        private Paint cornerShadowPaint;
        private Paint shadowPaint;

        private boolean needComputeRect = true;
        private boolean needComputeShadow = true;
        /*需要绘制背景的矩阵*/
        private RectF bgRect;
        /*背景宽高*/
        private float contentWidth;
        private float contentHeight;

        /*顶部和底部阴影path*/
        private Path horizontalPath;
        /*左边和右边阴影path*/
        private Path verticalPath;
        /*圆角阴影path*/
        private Path cornerShadowPath;

        /*背景path*/
        private Path bgPath;

        /*圆角渐变*/
        private RadialGradient cornerGradient;
        /*顶部水平渐变*/
        private LinearGradient horizontalLinearGradient;
        /*左边垂直渐变*/
        private LinearGradient verticalLinearGradient;
        /*底部水平渐变*/
        private LinearGradient horizontalBottomLinearGradient;
        /*右边垂直渐变*/
        private LinearGradient verticalRightLinearGradient;
        private ArgbEvaluator argbEvaluator;

        public CustomDrawable() {
            bgPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
            bgPaint.setStyle(Paint.Style.FILL);

            cornerShadowPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
            shadowPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        }

        private void setBackground(ColorStateList color) {
            backgroundColor = (color == null) ? ColorStateList.valueOf(Color.WHITE) : color;
            bgPaint.setColor(backgroundColor.getColorForState(getState(), backgroundColor.getDefaultColor()));
        }

        public void setNeedComputeRect(boolean needComputeRect) {
            this.needComputeRect = needComputeRect;
        }

        public void setNeedComputeShadow(boolean needComputeShadow) {
            this.needComputeShadow = needComputeShadow;
        }

        @Override
        public void draw(Canvas canvas) {
            if (needComputeRect) {
                computeBgRect();
                needComputeRect = false;
            }
            if (needComputeShadow) {
                computeShadow();
                needComputeShadow = false;
            }
            /*绘制阴影*/
            drawShadow(canvas);

            /*绘制背景*/
            drawContentBg(canvas);
        }


        private void drawContentBg(Canvas canvas) {
            canvas.drawPath(bgPath, bgPaint);
        }

        private void drawShadow(Canvas canvas) {


            //开始绘制四个角
            canvas.save();

            //左上
            canvas.drawPath(cornerShadowPath, cornerShadowPaint);

            //右上
            /*将左上角旋转90，Y轴反向平移之后绘制*/
            canvas.rotate(90, getShadowWidth(), getShadowWidth());
            canvas.translate(0, -contentWidth);
            canvas.drawPath(cornerShadowPath, cornerShadowPaint);

            //右下
            /*继续旋转90，Y轴反向平移之后绘制*/
            canvas.rotate(90, getShadowWidth(), getShadowWidth());
            canvas.translate(0, -contentHeight);
            canvas.drawPath(cornerShadowPath, cornerShadowPaint);

            //左下
            /*继续旋转90，Y轴反向平移之后绘制*/
            canvas.rotate(90, getShadowWidth(), getShadowWidth());
            canvas.translate(0, -contentWidth);
            canvas.drawPath(cornerShadowPath, cornerShadowPaint);

            canvas.restore();


            //开始绘制4条边
            canvas.save();
            shadowPaint.setShader(horizontalLinearGradient);
            canvas.drawPath(horizontalPath, shadowPaint);
            canvas.rotate(180,getWidth()/2,getHeight()/2);
//            canvas.translate(0, contentHeight + getShadowWidth()-shadowClipInLength);
            /*顶部和底部的颜色渐变方向相反，需要重新设置shader*/
//            shadowPaint.setShader(null);
//            shadowPaint.setColor(Color.RED);
//            shadowPaint.setShader(horizontalBottomLinearGradient);
            canvas.drawPath(horizontalPath, shadowPaint);
            canvas.restore();

            canvas.save();
            shadowPaint.setShader(verticalLinearGradient);
            canvas.drawPath(verticalPath, shadowPaint);
            canvas.rotate(180,getWidth()/2,getHeight()/2);
//            shadowPaint.setShader(verticalRightLinearGradient);
            canvas.drawPath(verticalPath, shadowPaint);
            canvas.restore();


        }

        @Override
        public void setAlpha(int alpha) {
            cornerShadowPaint.setAlpha(alpha);
            shadowPaint.setAlpha(alpha);

        }

        @Override
        public void setColorFilter(ColorFilter colorFilter) {
            cornerShadowPaint.setColorFilter(colorFilter);
            shadowPaint.setColorFilter(colorFilter);
        }

        @Override
        public int getOpacity() {
            return PixelFormat.TRANSLUCENT;
        }


        private void computeBgRect() {
            Rect bounds = getBounds();
            bgRect = new RectF(
                    (bounds.left + shadowWidth - shadowOffsetLeft),
                    (bounds.top + shadowWidth - shadowOffsetTop),
                    (bounds.right - shadowWidth + shadowOffsetRight),
                    (bounds.bottom - shadowWidth + shadowOffsetBottom)
            );
            if (bgPath == null) {
                bgPath = new Path();
            } else {
                bgPath.reset();
            }
            float[] floats = {
                    bgRadiusLeftTop, bgRadiusLeftTop,
                    bgRadiusRightTop, bgRadiusRightTop,
                    bgRadiusRightBottom, bgRadiusRightBottom,
                    bgRadiusLeftBottom, bgRadiusLeftBottom,
            };
            //因为如果加了圆角，需要扩大背景绘制面积，不然直角就会漏出来
            float max = Math.max(Math.max(bgRadiusLeftTop, bgRadiusRightTop), Math.max(bgRadiusRightBottom, bgRadiusLeftBottom));
            max = (float) (max - max * Math.cos(Math.toRadians(45))) + 1;
            //设置往外偏移量
            bgRect.inset(-max, -max);
            //新的圆角矩阵需要包含默认的背景矩阵，防止直角漏出来
            bgPath.addRoundRect(bgRect, floats, Path.Direction.CW);

            contentWidth = (int) (bounds.width() - shadowWidth * 2);
            contentHeight = (int) (bounds.height() - shadowWidth * 2);

        }

        private void computeShadow() {
            setCornerShadowPath();

            cornerShadowPaint.setShader(cornerGradient);
            shadowPaint.setShader(horizontalLinearGradient);
        }

        private float getFractionByBezier(float fraction) {
            return (float) (Math.pow((1 - fraction), 3) *0 + 3 * controlPointFirstY * fraction * Math.pow((1 - fraction), 2) + 3 * controlPointSecondY * Math.pow(fraction, 2) * (1 - fraction) + 1 * Math.pow(fraction, 3));
        }

        private void setCornerShadowPath() {
            if (cornerShadowPath == null) {
                cornerShadowPath = new Path();
            } else {
                cornerShadowPath.reset();
            }

            //重合部分处理方式
//            cornerShadowPath.setFillType(Path.FillType.EVEN_ODD);

            float reallyShadowRadius = getShadowWidth() + getShadowClipInLength();
            cornerShadowPath.moveTo(reallyShadowRadius, reallyShadowRadius);
            cornerShadowPath.arcTo(new RectF(0, 0, reallyShadowRadius * 2, reallyShadowRadius * 2), 180, 90);
            cornerShadowPath.close();


            int[] radiusColors;
            float[] scaleLength;
            if (!onlyLinear) {
                if (argbEvaluator == null) {
                    argbEvaluator = new ArgbEvaluator();
                }
                radiusColors = new int[]{
                        getShadowStartColor(),
                        (int) argbEvaluator.evaluate(getFractionByBezier(0.2f) , getShadowStartColor(), getShadowEndColor()),
                        (int) argbEvaluator.evaluate(getFractionByBezier(0.4f) , getShadowStartColor(), getShadowEndColor()),
                        (int) argbEvaluator.evaluate(getFractionByBezier(0.6f) , getShadowStartColor(), getShadowEndColor()),
                        (int) argbEvaluator.evaluate(getFractionByBezier(0.8f) , getShadowStartColor(), getShadowEndColor()),
                        getShadowEndColor(),
                };
                scaleLength = new float[]{0f, 0.2f, 0.4f, 0.6f, 0.8f, 1};
            } else {
                radiusColors = new int[]{getShadowStartColor(), getShadowEndColor()};
                scaleLength = new float[]{0f, 1};
            }

            //如果渲染器起始位置向里调整，horizontalPath,verticalPath则需要偏移，cornerLeftTopShadowPath圆心也需要偏移
            cornerGradient = new RadialGradient(reallyShadowRadius, reallyShadowRadius, reallyShadowRadius + shadowClipOutLength, radiusColors, scaleLength, Shader.TileMode.CLAMP);

            /*设置顶部阴影渐变位置*/
            horizontalLinearGradient = new LinearGradient(0, getShadowWidth() + shadowClipInLength, 0, 0 - shadowClipOutLength, radiusColors, scaleLength, Shader.TileMode.CLAMP);
            /*设置左边阴影渐变位置*/
            verticalLinearGradient = new LinearGradient(getShadowWidth() + shadowClipInLength, 0, 0 - shadowClipOutLength, 0, radiusColors, scaleLength, Shader.TileMode.CLAMP);
            /*设置底部阴影渐变位置*/
            horizontalBottomLinearGradient = new LinearGradient(0, 0 - shadowClipInLength, 0, getShadowWidth() + shadowClipOutLength, radiusColors, scaleLength, Shader.TileMode.CLAMP);
            /*设置右边阴影渐变位置*/
            verticalRightLinearGradient = new LinearGradient(0 - shadowClipInLength, 0, getShadowWidth() + shadowClipOutLength, 0, radiusColors, scaleLength, Shader.TileMode.CLAMP);

            if (horizontalPath == null) {
                horizontalPath = new Path();
            } else {
                horizontalPath.reset();
            }
            if (isInEditMode()) {
                horizontalPath.moveTo(getShadowWidth()+shadowClipInLength, 0);
                horizontalPath.lineTo(contentWidth + getShadowWidth()-shadowClipInLength , 0);
                horizontalPath.lineTo(contentWidth + getShadowWidth()-shadowClipInLength , getShadowWidth()+shadowClipInLength);
                horizontalPath.lineTo(getShadowWidth()+shadowClipInLength , getShadowWidth()+shadowClipInLength);
                horizontalPath.close();
            } else {
                horizontalPath.addRect(new RectF(getShadowWidth()+shadowClipInLength , 0, contentWidth + getShadowWidth()-shadowClipInLength , getShadowWidth()+shadowClipInLength), Path.Direction.CW);
            }


            if (verticalPath == null) {
                verticalPath = new Path();
            } else {
                verticalPath.reset();
            }
            if (isInEditMode()) {
                verticalPath.moveTo(0, getShadowWidth() + shadowClipInLength);
                verticalPath.lineTo(getShadowWidth()+shadowClipInLength, getShadowWidth() + shadowClipInLength);
                verticalPath.lineTo(getShadowWidth()+shadowClipInLength, getShadowWidth() + contentHeight - shadowClipInLength);
                verticalPath.lineTo(0, getShadowWidth() + contentHeight - shadowClipInLength);
                verticalPath.close();
            } else {
                verticalPath.addRect(new RectF(0, getShadowWidth() + shadowClipInLength, getShadowWidth()+shadowClipInLength, getShadowWidth() + contentHeight - shadowClipInLength), Path.Direction.CW);
            }
        }

    }


    public void setBgColor(@ColorInt int bgColor) {
        ColorStateList colorStateList = ColorStateList.valueOf(bgColor);
        if (this.backgroundColor != colorStateList) {
            this.backgroundColor = colorStateList;

            shadowDrawable.setBackground(backgroundColor);
            this.invalidateDrawable(shadowDrawable);

            setBackground(shadowDrawable);
        }
    }

    public void setBgRadius(float bgRadius) {
        setBgRadiusLeftTop(bgRadius);
        setBgRadiusRightTop(bgRadius);
        setBgRadiusRightBottom(bgRadius);
        setBgRadiusLeftBottom(bgRadius);
    }

    public float getBgRadiusLeftTop() {
        return bgRadiusLeftTop;
    }

    public void setBgRadiusLeftTop(float bgRadiusLeftTop) {
        if (bgRadiusLeftTop < 0) {
            bgRadiusLeftTop = 0;
        }
        if (this.bgRadiusLeftTop != bgRadiusLeftTop) {
            this.bgRadiusLeftTop = bgRadiusLeftTop;
            computeBgRect(true);
        }
    }

    public float getBgRadiusRightTop() {
        return bgRadiusRightTop;
    }

    public void setBgRadiusRightTop(float bgRadiusRightTop) {
        if (bgRadiusRightTop < 0) {
            bgRadiusRightTop = 0;
        }
        if (this.bgRadiusRightTop != bgRadiusRightTop) {
            this.bgRadiusRightTop = bgRadiusRightTop;
            computeBgRect(true);
        }
    }

    public float getBgRadiusRightBottom() {
        return bgRadiusRightBottom;
    }

    public void setBgRadiusRightBottom(float bgRadiusRightBottom) {
        if (bgRadiusRightBottom < 0) {
            bgRadiusRightBottom = 0;
        }
        if (this.bgRadiusRightBottom != bgRadiusRightBottom) {
            this.bgRadiusRightBottom = bgRadiusRightBottom;
            computeBgRect(true);
        }
    }

    public float getBgRadiusLeftBottom() {
        return bgRadiusLeftBottom;
    }

    public void setBgRadiusLeftBottom(float bgRadiusLeftBottom) {
        if (bgRadiusLeftBottom < 0) {
            bgRadiusLeftBottom = 0;
        }
        if (this.bgRadiusLeftBottom != bgRadiusLeftBottom) {
            this.bgRadiusLeftBottom = bgRadiusLeftBottom;
            computeBgRect(true);
        }
    }

   /* public ColorStateList getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(ColorStateList backgroundColor) {
        this.backgroundColor = backgroundColor;
    }*/

    public float getShadowAlpha() {
        return shadowAlpha;
    }

    public void setShadowAlpha(@FloatRange(from = 0, to = 1f) float shadowAlpha) {
        if (shadowAlpha < 0) {
            shadowAlpha = 0;
        }
        if (shadowAlpha > 1) {
            shadowAlpha = 1;
        }
        if (this.shadowAlpha != shadowAlpha) {
            this.shadowAlpha = shadowAlpha;
            shadowDrawable.setAlpha((int) (shadowAlpha * 255));
            this.invalidateDrawable(shadowDrawable);
//            computeShadow(true);
        }
    }

    public float getShadowWidth() {
        if (shadowWidth <= 0) {
            shadowWidth = 1;
        }
        return shadowWidth;
    }

    public void setShadowWidth(float shadowWidth) {
        shadowWidth = (int) (shadowWidth + 0.5f);
        if (shadowWidth < 0) {
            shadowWidth = 1;
        }
        if (this.shadowWidth != shadowWidth) {
            this.shadowWidth = shadowWidth;
            computeShadowAndBg(true);
        }
    }

    public float getShadowOffsetLeft() {
        return shadowOffsetLeft;
    }

    public void setShadowOffsetLeft(float shadowOffsetLeft) {
        if (shadowOffsetLeft < 0) {
            shadowOffsetLeft = 0;
        }
        if (this.shadowOffsetLeft != shadowOffsetLeft) {
            this.shadowOffsetLeft = shadowOffsetLeft;
            setPadding(getPaddingLeft(), getPaddingTop(), getPaddingRight(), getPaddingBottom());
            computeShadowAndBg(true);
        }
    }

    public float getShadowOffsetTop() {
        return shadowOffsetTop;
    }

    public void setShadowOffsetTop(float shadowOffsetTop) {
        if (shadowOffsetTop < 0) {
            shadowOffsetTop = 0;
        }
        if (this.shadowOffsetTop != shadowOffsetTop) {
            this.shadowOffsetTop = shadowOffsetTop;
            setPadding(getPaddingLeft(), getPaddingTop(), getPaddingRight(), getPaddingBottom());
            computeShadowAndBg(true);
        }
    }

    public float getShadowOffsetRight() {
        return shadowOffsetRight;
    }

    public void setShadowOffsetRight(float shadowOffsetRight) {
        if (shadowOffsetRight < 0) {
            shadowOffsetRight = 0;
        }
        if (this.shadowOffsetRight != shadowOffsetRight) {
            this.shadowOffsetRight = shadowOffsetRight;
            setPadding(getPaddingLeft(), getPaddingTop(), getPaddingRight(), getPaddingBottom());
            computeShadowAndBg(true);
        }
    }

    public float getShadowOffsetBottom() {
        return shadowOffsetBottom;
    }

    public void setShadowOffsetBottom(float shadowOffsetBottom) {
        if (shadowOffsetBottom < 0) {
            shadowOffsetBottom = 0;
        }
        if (this.shadowOffsetBottom != shadowOffsetBottom) {
            this.shadowOffsetBottom = shadowOffsetBottom;
            setPadding(getPaddingLeft(), getPaddingTop(), getPaddingRight(), getPaddingBottom());
            computeShadowAndBg(true);
        }
    }

    public int getShadowStartColor() {
        return shadowStartColor;
    }

    public void setShadowStartColor(int shadowStartColor) {
        if (this.shadowStartColor != shadowStartColor) {
            this.shadowStartColor = shadowStartColor;
            computeShadow(true);
        }
    }

    public int getShadowEndColor() {
        return shadowEndColor;
    }

    public void setShadowEndColor(int shadowEndColor) {
        if (this.shadowEndColor != shadowEndColor) {
            this.shadowEndColor = shadowEndColor;
            computeShadow(true);
        }
    }

    public boolean isOnlyLinear() {
        return onlyLinear;
    }

    public void setOnlyLinear(boolean onlyLinear) {
        if(this.onlyLinear!=onlyLinear){
            this.onlyLinear = onlyLinear;
            computeShadow(true);
        }
    }

    public float getControlPointFirstY() {
        return controlPointFirstY;
    }

    public void setControlPointFirstY(float controlPointFirstY) {
        if(controlPointFirstY<0){
            controlPointFirstY=0;
        }else if(controlPointFirstY>1){
            controlPointFirstY=1;
        }
        if(this.controlPointFirstY!=controlPointFirstY){
            this.controlPointFirstY = controlPointFirstY;
            computeShadow(true);
        }
    }

    public float getControlPointSecondY() {
        return controlPointSecondY;
    }

    public void setControlPointSecondY(float controlPointSecondY) {
        if(controlPointSecondY<0){
            controlPointSecondY=0;
        }else if(controlPointSecondY>1){
            controlPointSecondY=1;
        }
        if(this.controlPointSecondY!=controlPointSecondY){
            this.controlPointSecondY = controlPointSecondY;
            computeShadow(true);
        }
    }

    public float getShadowClipOutLength() {
        return shadowClipOutLength;
    }

    public void setShadowClipOutLength(float shadowClipOutLength) {
        if (this.shadowClipOutLength != shadowClipOutLength) {
            this.shadowClipOutLength = shadowClipOutLength;
            computeShadow(true);
        }
    }

    public float getShadowClipInLength() {
        if (shadowClipInLength < 0) {
            shadowClipInLength = 0;
        }
        return shadowClipInLength;
    }

    public void setShadowClipInLength(float shadowClipInLength) {
        if (shadowClipInLength < 0) {
            shadowClipInLength = 0;
        }
        if (this.shadowClipInLength != shadowClipInLength) {
            this.shadowClipInLength = shadowClipInLength;
            computeShadowAndBg(true);
        }
    }

    private int dp2px(int value) {
        return (int) (getContext().getResources().getDisplayMetrics().density * value);
    }

    private void computeBgRect(boolean needComputeRect) {
        shadowDrawable.setNeedComputeRect(needComputeRect);
        this.invalidateDrawable(shadowDrawable);
    }

    private void computeShadow(boolean needComputeShadow) {
        shadowDrawable.setNeedComputeShadow(needComputeShadow);
        this.invalidateDrawable(shadowDrawable);
    }

    private void computeShadowAndBg(boolean needCompute) {
        shadowDrawable.setNeedComputeRect(needCompute);
        shadowDrawable.setNeedComputeShadow(needCompute);
        this.invalidateDrawable(shadowDrawable);
    }
}
