package com.github.mycardview.view;

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
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.github.mycardview.R;

/***
 *   created by zhongrui on 2019/7/31
 */
public class CustomCardView extends FrameLayout {
    // TODO: 2019/8/22
    private int bgColor=Color.TRANSPARENT;
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
    private CustomDrawable shadowDrawable;

    public CustomCardView(Context context) {
        this(context,null);
    }
    public CustomCardView(Context context, AttributeSet attrs) {
        this(context, attrs,R.attr.CustomCardViewStyle);
    }
    public CustomCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr);
    }

    private void init(AttributeSet attrs, int defStyleAttr ) {

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.CustomCardView, defStyleAttr,R.style.CustomCardView);

        ColorStateList colorStateList;
        if (typedArray.hasValue(R.styleable.CustomCardView_bgColor)) {
            colorStateList = typedArray.getColorStateList(R.styleable.CustomCardView_bgColor);
        } else {
            TypedArray array = getContext().obtainStyledAttributes(new int[]{android.R.attr.colorBackground});
            colorStateList = ColorStateList.valueOf(array.getColor(0, Color.WHITE));
            array.recycle();
        }

        float bgRadius = typedArray.getDimension(R.styleable.CustomCardView_bgRadius, 0);
        bgRadiusLeftTop = typedArray.getDimension(R.styleable.CustomCardView_bgRadiusLeftTop, bgRadius);
        bgRadiusRightTop = typedArray.getDimension(R.styleable.CustomCardView_bgRadiusRightTop,bgRadius);
        bgRadiusRightBottom = typedArray.getDimension(R.styleable.CustomCardView_bgRadiusRightBottom, bgRadius);
        bgRadiusLeftBottom = typedArray.getDimension(R.styleable.CustomCardView_bgRadiusLeftBottom,bgRadius);

        shadowAlpha = typedArray.getFloat(R.styleable.CustomCardView_shadowAlpha, 1);
        shadowWidth = typedArray.getDimension(R.styleable.CustomCardView_shadowWidth, dp2px(10));
        shadowOffsetLeft = typedArray.getDimension(R.styleable.CustomCardView_shadowOffsetLeft, 0);
        shadowOffsetTop = typedArray.getDimension(R.styleable.CustomCardView_shadowOffsetTop, 0);
        shadowOffsetRight = typedArray.getDimension(R.styleable.CustomCardView_shadowOffsetRight, 0);
        shadowOffsetBottom = typedArray.getDimension(R.styleable.CustomCardView_shadowOffsetBottom, 0);

        shadowStartColor = typedArray.getColor(R.styleable.CustomCardView_shadowStartColor, ContextCompat.getColor(getContext(), R.color.customCardView_default_shadowStartColor));
        shadowEndColor = typedArray.getColor(R.styleable.CustomCardView_shadowEndColor, ContextCompat.getColor(getContext(), R.color.customCardView_default_shadowEndColor));


        shadowClipOutLength = typedArray.getDimension(R.styleable.CustomCardView_shadowClipOutLength, 0);
        shadowClipInLength = typedArray.getDimension(R.styleable.CustomCardView_shadowClipInLength, 0);

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

        setPadding(getPaddingLeft(),getPaddingTop(),getPaddingRight(),getPaddingBottom());
    }

    @Override
    public void setPadding(int left, int top, int right, int bottom) {
        left= (int) (left+getShadowWidth()-getShadowOffsetLeft());
        top= (int) (top+getShadowWidth()-getShadowOffsetTop());
        right= (int) (right+getShadowWidth()-getShadowOffsetRight());
        bottom= (int) (bottom+getShadowWidth()-getShadowOffsetBottom());
        super.setPadding(left, top, right, bottom);
    }

    @Override
    public void setPaddingRelative(int start, int top, int end, int bottom) {
        start= (int) (start+getShadowWidth()-getShadowOffsetLeft());
        top= (int) (top+getShadowWidth()-getShadowOffsetTop());
        end= (int) (end+getShadowWidth()-getShadowOffsetRight());
        bottom= (int) (bottom+getShadowWidth()-getShadowOffsetBottom());
        super.setPaddingRelative(start, top, end, bottom);
    }

    public class CustomDrawable extends Drawable{
        private Paint bgPaint;
        private Paint cornerShadowPaint;
        private Paint shadowPaint;

        private boolean needComputeRect=true;
        /*需要绘制背景的矩阵*/
        private RectF bgRect;
        /*背景宽高*/
        private int contentWidth;
        private int contentHeight;

        /*顶部和底部阴影path*/
        private Path horizontalPath;
        /*左边和右边阴影path*/
        private Path   verticalPath;
        /*圆角阴影path*/
        private Path cornerShadowPath;

        /*背景path*/
        private Path bgPath;

        /*圆角渐变*/
        private RadialGradient cornerGradient;
        /*顶部水平渐变*/
        private LinearGradient   horizontalLinearGradient;
        /*左边垂直渐变*/
        private LinearGradient   verticalLinearGradient;
        /*底部水平渐变*/
        private LinearGradient   horizontalBottomLinearGradient;
        /*右边垂直渐变*/
        private LinearGradient verticalRightLinearGradient;

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
            /*绘制阴影*/
            drawShadow(canvas);

            /*绘制背景*/
            drawContentBg(canvas);
        }


        private void drawContentBg(Canvas canvas) {
            canvas.drawPath(bgPath,bgPaint);
        }

        private void drawShadow(Canvas canvas) {


            //开始绘制四个角
            canvas.save();

            //左上
            canvas.drawPath(cornerShadowPath,cornerShadowPaint);

            //右上
            /*将左上角旋转90，Y轴反向平移之后绘制*/
            canvas.rotate(90,getShadowWidth(),getShadowWidth());
            canvas.translate(0,-contentWidth);
            canvas.drawPath(cornerShadowPath,cornerShadowPaint);

            //右下
            /*继续旋转90，Y轴反向平移之后绘制*/
            canvas.rotate(90,getShadowWidth(),getShadowWidth());
            canvas.translate(0,-contentHeight);
            canvas.drawPath(cornerShadowPath,cornerShadowPaint);

            //左下
            /*继续旋转90，Y轴反向平移之后绘制*/
            canvas.rotate(90,getShadowWidth(),getShadowWidth());
            canvas.translate(0,-contentWidth);
            canvas.drawPath(cornerShadowPath,cornerShadowPaint);

            canvas.restore();




            //开始绘制4条边
            canvas.save();
            shadowPaint.setShader(horizontalLinearGradient);
            canvas.drawPath(horizontalPath,shadowPaint);
            canvas.translate(0,contentHeight+getShadowWidth());
            /*顶部和底部的颜色渐变方向相反，需要重新设置shader*/
            shadowPaint.setShader(horizontalBottomLinearGradient);
            canvas.drawPath(horizontalPath,shadowPaint);
            canvas.restore();

            canvas.save();
            shadowPaint.setShader(verticalLinearGradient);
            canvas.drawPath(verticalPath,shadowPaint);
            canvas.translate(contentWidth+getShadowWidth(),0);
            /*左边和右边的颜色渐变方向相反，需要重新设置shader*/
            shadowPaint.setShader(verticalRightLinearGradient);
            canvas.drawPath(verticalPath,shadowPaint);
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
            bgRect=new RectF(
                     (bounds.left+shadowWidth-shadowOffsetLeft),
                     (bounds.top+shadowWidth- shadowOffsetTop),
                     (bounds.right-shadowWidth+ shadowOffsetRight),
                     (bounds.bottom-shadowWidth+ shadowOffsetBottom)
            );
            if (bgPath == null) {
                bgPath=new Path();
            }else{
                bgPath.reset();
            }
            float[] floats = {
                    bgRadiusLeftTop,bgRadiusLeftTop,
                    bgRadiusRightTop,bgRadiusRightTop,
                    bgRadiusRightBottom,bgRadiusRightBottom,
                    bgRadiusLeftBottom,bgRadiusLeftBottom,
            };
            //因为如果加了圆角，需要扩大背景绘制面积，不然直角就会漏出来
            float max = Math.max(Math.max(bgRadiusLeftTop, bgRadiusRightTop), Math.max(bgRadiusRightBottom, bgRadiusLeftBottom));
            max= (float) (max-max*Math.cos(Math.toRadians(45)))+1;
            //设置往外偏移量
            bgRect.inset(-max,-max);
            //新的圆角矩阵需要包含默认的背景矩阵，防止直角漏出来
            bgPath.addRoundRect(bgRect,floats,Path.Direction.CW);

            contentWidth= (int) (bounds.width()-shadowWidth*2);
            contentHeight= (int) (bounds.height()-shadowWidth*2);

            setCornerShadowPath();


            cornerShadowPaint.setShader(cornerGradient);
            shadowPaint.setShader(horizontalLinearGradient);
        }

        private void setCornerShadowPath() {
            if (cornerShadowPath == null) {
                cornerShadowPath = new Path();
            } else {
                cornerShadowPath.reset();
            }

            //重合部分处理方式
//            cornerShadowPath.setFillType(Path.FillType.EVEN_ODD);

            float reallyShadowRadius=getShadowWidth()+shadowClipInLength;
            cornerShadowPath.moveTo(reallyShadowRadius,reallyShadowRadius);
            cornerShadowPath.arcTo(new RectF(0,0,reallyShadowRadius*2,reallyShadowRadius*2),180,90);
            cornerShadowPath.close();



            int []radiusColors={getShadowStartColor(),getShadowEndColor()};
            int []lineColors={getShadowStartColor(),getShadowEndColor()};
            float scaleLength[]={0f,1};

            //如果渲染器起始位置向里调整，horizontalPath,verticalPath则需要偏移，cornerLeftTopShadowPath圆心也需要偏移
            cornerGradient =new RadialGradient(reallyShadowRadius,reallyShadowRadius,reallyShadowRadius+shadowClipOutLength,radiusColors,scaleLength,Shader.TileMode.CLAMP);

            /*设置顶部阴影渐变位置*/
            horizontalLinearGradient=new LinearGradient(0,getShadowWidth()+ shadowClipInLength,0,0-shadowClipOutLength,lineColors,scaleLength,Shader.TileMode.CLAMP);
            /*设置左边阴影渐变位置*/
            verticalLinearGradient=new LinearGradient(getShadowWidth()+ shadowClipInLength,0,0-shadowClipOutLength,0,lineColors,scaleLength,Shader.TileMode.CLAMP);
            /*设置底部阴影渐变位置*/
            horizontalBottomLinearGradient =new LinearGradient(0,0- shadowClipInLength,0,getShadowWidth()+shadowClipOutLength,radiusColors,scaleLength,Shader.TileMode.CLAMP);
            /*设置右边阴影渐变位置*/
            verticalRightLinearGradient    =new LinearGradient(0-shadowClipInLength,0,getShadowWidth()+shadowClipOutLength,0,radiusColors,scaleLength,Shader.TileMode.CLAMP);

            if (horizontalPath == null) {
                horizontalPath=new Path();
            }else{
                horizontalPath.reset();
            }
            if(isInEditMode()){
                horizontalPath.moveTo(getShadowWidth()+shadowClipInLength,0);
                horizontalPath.lineTo(contentWidth+getShadowWidth()-shadowClipInLength,0);
                horizontalPath.lineTo(contentWidth+getShadowWidth()-shadowClipInLength,getShadowWidth());
                horizontalPath.lineTo(getShadowWidth()+shadowClipInLength,getShadowWidth());
                horizontalPath.close();
            }else{
                horizontalPath.addRect(new RectF(getShadowWidth()+shadowClipInLength,0,contentWidth+getShadowWidth()-shadowClipInLength,getShadowWidth()),Path.Direction.CW);
            }


            if (verticalPath == null) {
                verticalPath=new Path();
            }else{
                verticalPath.reset();
            }
            if(isInEditMode()){
                verticalPath.moveTo(0,getShadowWidth()+shadowClipInLength);
                verticalPath.lineTo(getShadowWidth(),getShadowWidth()+shadowClipInLength);
                verticalPath.lineTo(getShadowWidth(),getShadowWidth()+contentHeight-shadowClipInLength);
                verticalPath.lineTo(0,getShadowWidth()+contentHeight-shadowClipInLength);
                verticalPath.close();
            }else{
                verticalPath.addRect(new RectF(0,getShadowWidth()+shadowClipInLength,getShadowWidth(),getShadowWidth()+contentHeight-shadowClipInLength),Path.Direction.CW);
            }
        }

    }


    public int getBgColor() {
        return bgColor;
    }

    public void setBgColor(int bgColor) {
        this.bgColor = bgColor;
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
        this.bgRadiusLeftTop = bgRadiusLeftTop;
    }

    public float getBgRadiusRightTop() {
        return bgRadiusRightTop;
    }

    public void setBgRadiusRightTop(float bgRadiusRightTop) {
        this.bgRadiusRightTop = bgRadiusRightTop;
    }

    public float getBgRadiusRightBottom() {
        return bgRadiusRightBottom;
    }

    public void setBgRadiusRightBottom(float bgRadiusRightBottom) {
        this.bgRadiusRightBottom = bgRadiusRightBottom;
    }

    public float getBgRadiusLeftBottom() {
        return bgRadiusLeftBottom;
    }

    public void setBgRadiusLeftBottom(float bgRadiusLeftBottom) {
        this.bgRadiusLeftBottom = bgRadiusLeftBottom;
    }

    public ColorStateList getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(ColorStateList backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public float getShadowAlpha() {
        return shadowAlpha;
    }

    public void setShadowAlpha(float shadowAlpha) {
        this.shadowAlpha = shadowAlpha;
    }

    public float getShadowWidth() {
        return shadowWidth;
    }

    public void setShadowWidth(float shadowWidth) {
        this.shadowWidth = shadowWidth;
    }

    public float getShadowOffsetLeft() {
        return shadowOffsetLeft;
    }

    public void setShadowOffsetLeft(float shadowOffsetLeft) {
        this.shadowOffsetLeft = shadowOffsetLeft;
    }

    public float getShadowOffsetTop() {
        return shadowOffsetTop;
    }

    public void setShadowOffsetTop(float shadowOffsetTop) {
        this.shadowOffsetTop = shadowOffsetTop;
    }

    public float getShadowOffsetRight() {
        return shadowOffsetRight;
    }

    public void setShadowOffsetRight(float shadowOffsetRight) {
        this.shadowOffsetRight = shadowOffsetRight;
    }

    public float getShadowOffsetBottom() {
        return shadowOffsetBottom;
    }

    public void setShadowOffsetBottom(float shadowOffsetBottom) {
        this.shadowOffsetBottom = shadowOffsetBottom;
    }

    public int getShadowStartColor() {
        return shadowStartColor;
    }

    public void setShadowStartColor(int shadowStartColor) {
        this.shadowStartColor = shadowStartColor;
    }

    public int getShadowEndColor() {
        return shadowEndColor;
    }

    public void setShadowEndColor(int shadowEndColor) {
        this.shadowEndColor = shadowEndColor;
    }

    public float getShadowClipOutLength() {
        return shadowClipOutLength;
    }

    public void setShadowClipOutLength(float shadowClipOutLength) {
        this.shadowClipOutLength = shadowClipOutLength;
    }

    public float getShadowClipInLength() {
        return shadowClipInLength;
    }

    public void setShadowClipInLength(float shadowClipInLength) {
        this.shadowClipInLength = shadowClipInLength;
    }

    private int dp2px(int value) {
        return (int) (getContext().getResources().getDisplayMetrics().density * value);
    }

}
