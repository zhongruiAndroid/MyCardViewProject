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
    private int shadowCenterColor;
    private int shadowEndColor;
    private float shadowStartColorWeight;
    private float shadowCenterColorWeight;
    private float shadowEndColorWeight;
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

        // TODO: 2019/8/22 get set
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
        shadowCenterColor = typedArray.getColor(R.styleable.CustomCardView_shadowCenterColor, Color.TRANSPARENT);
        shadowEndColor = typedArray.getColor(R.styleable.CustomCardView_shadowEndColor, ContextCompat.getColor(getContext(), R.color.customCardView_default_shadowEndColor));

        shadowStartColorWeight = typedArray.getFloat(R.styleable.CustomCardView_shadowStartColorWeight, 1);
        shadowCenterColorWeight = typedArray.getFloat(R.styleable.CustomCardView_shadowCenterColorWeight, 1);
        shadowEndColorWeight = typedArray.getFloat(R.styleable.CustomCardView_shadowEndColorWeight, 1);

        shadowClipOutLength = typedArray.getDimension(R.styleable.CustomCardView_shadowOutClipLength, 0);
        shadowClipInLength = typedArray.getDimension(R.styleable.CustomCardView_shadowInClipLength, 0);

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
        /*需要绘制内容的矩阵*/
        private RectF bgRect;
        private int contentWidth;
        private int contentHeight;

        private Path horizontalPath;
        private Path   verticalPath;

        private Path cornerLeftTopShadowPath;
        private Path bgPath;
//        private Path cornerRightTopShadowPath;
//        private Path cornerLeftBottomShadowPath;
//        private Path cornerRightBottomShadowPath;

        private RadialGradient leftTopGradient;
        private LinearGradient   horizontalLinearGradient;
        private LinearGradient   verticalLinearGradient;
        private LinearGradient   horizontalBottomLinearGradient;
        private LinearGradient verticalRightLinearGradient;
//        private RadialGradient rightTopGradient;
//        private RadialGradient rightBottomGradient;
//        private RadialGradient leftBottomGradient;

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

            drawContentBg(canvas);
        }


        private void drawContentBg(Canvas canvas) {
            canvas.drawPath(bgPath,bgPaint);
        }

        private void drawShadow(Canvas canvas) {


            //开始绘制四个角
            canvas.save();

            //左上
            canvas.drawPath(cornerLeftTopShadowPath,cornerShadowPaint);

            //右上
            /*将左上角旋转90，Y轴反向平移之后绘制*/
            canvas.rotate(90,getShadowWidth(),getShadowWidth());
            canvas.translate(0,-contentWidth);
            canvas.drawPath(cornerLeftTopShadowPath,cornerShadowPaint);

            //右下
            /*继续旋转90，Y轴反向平移之后绘制*/
            canvas.rotate(90,getShadowWidth(),getShadowWidth());
            canvas.translate(0,-contentHeight);
            canvas.drawPath(cornerLeftTopShadowPath,cornerShadowPaint);

            //左下
            /*继续旋转90，Y轴反向平移之后绘制*/
            canvas.rotate(90,getShadowWidth(),getShadowWidth());
            canvas.translate(0,-contentWidth);
            canvas.drawPath(cornerLeftTopShadowPath,cornerShadowPaint);

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
            bgRect.inset(-max,-max);
            bgPath.addRoundRect(bgRect,floats,Path.Direction.CW);

            contentWidth= (int) (bounds.width()-shadowWidth*2);
            contentHeight= (int) (bounds.height()-shadowWidth*2);

            setCornerLeftTopShadowPath();
//            setCornerRightTopShadowPath();
//            setCornerLeftBottomShadowPath();
//            setCornerRightBottomShadowPath();


            cornerShadowPaint.setShader(leftTopGradient);
            shadowPaint.setShader(horizontalLinearGradient);
        }

        private void setCornerLeftTopShadowPath() {
            if (cornerLeftTopShadowPath == null) {
                cornerLeftTopShadowPath = new Path();
            } else {
                cornerLeftTopShadowPath.reset();
            }

            //设置shadowClipOutLength shadowClipInLength时，去掉圆角和边的重合部分
            cornerLeftTopShadowPath.setFillType(Path.FillType.EVEN_ODD);
            cornerLeftTopShadowPath.addRect(new RectF(getShadowWidth(),0,getShadowWidth()+shadowClipInLength,getShadowWidth()+shadowClipInLength),Path.Direction.CW);
            // TODO: 2019/8/23  
            cornerLeftTopShadowPath.addRect(new RectF(0,getShadowWidth(),getShadowWidth()+shadowClipInLength,getShadowWidth()+shadowClipInLength),Path.Direction.CW);

            float reallyShadowRadius=getShadowWidth()+shadowClipInLength;
            cornerLeftTopShadowPath.moveTo(reallyShadowRadius,reallyShadowRadius);
            cornerLeftTopShadowPath.arcTo(new RectF(0,0,reallyShadowRadius*2,reallyShadowRadius*2),180,90);
            cornerLeftTopShadowPath.close();

            // TODO: 2019/8/22

            int []radiusColors={getShadowStartColor(),getShadowCenterColor(),getShadowEndColor()};
            int []lineColors={getShadowStartColor(),getShadowCenterColor(),getShadowEndColor()};
            float scaleLength[]={0f,0.5f,1};
            if(getShadowCenterColor()==Color.TRANSPARENT){
                 radiusColors=new int[]{getShadowStartColor(),getShadowEndColor()};
                 lineColors  =new int[]{getShadowStartColor(),getShadowEndColor()};
                 scaleLength =new float[]{0f,1};
            }
            leftTopGradient=new RadialGradient(reallyShadowRadius,reallyShadowRadius,reallyShadowRadius+shadowClipOutLength,radiusColors,scaleLength,Shader.TileMode.CLAMP);
//            rightTopGradient=new RadialGradient(reallyShadowRadius,reallyShadowRadius,reallyShadowRadius,radiusColors,scaleLength,Shader.TileMode.CLAMP);
//            rightBottomGradient=new RadialGradient(reallyShadowRadius,reallyShadowRadius,reallyShadowRadius,radiusColors,scaleLength,Shader.TileMode.CLAMP);
//            leftBottomGradient=new RadialGradient(reallyShadowRadius,reallyShadowRadius,reallyShadowRadius,radiusColors,scaleLength,Shader.TileMode.CLAMP);


            horizontalLinearGradient=new LinearGradient(0,getShadowWidth()+ shadowClipInLength,0,0-shadowClipOutLength,lineColors,scaleLength,Shader.TileMode.CLAMP);
            verticalLinearGradient=new LinearGradient(getShadowWidth()+ shadowClipInLength,0,0-shadowClipOutLength,0,lineColors,scaleLength,Shader.TileMode.CLAMP);

            horizontalBottomLinearGradient =new LinearGradient(0,0- shadowClipInLength,0,getShadowWidth()+shadowClipOutLength,radiusColors,scaleLength,Shader.TileMode.CLAMP);
            verticalRightLinearGradient    =new LinearGradient(0-shadowClipInLength,0,getShadowWidth()+shadowClipOutLength,0,radiusColors,scaleLength,Shader.TileMode.CLAMP);

            if (horizontalPath == null) {
                horizontalPath=new Path();
            }else{
                horizontalPath.reset();
            }
            if(isInEditMode()){
                horizontalPath.moveTo(getShadowWidth(),0);
                horizontalPath.lineTo(contentWidth+getShadowWidth(),0);
                horizontalPath.lineTo(contentWidth+getShadowWidth(),getShadowWidth());
                horizontalPath.lineTo(getShadowWidth(),getShadowWidth());
                horizontalPath.close();
            }else{
                horizontalPath.addRect(new RectF(getShadowWidth(),0,contentWidth+getShadowWidth(),getShadowWidth()),Path.Direction.CW);
            }


            if (verticalPath == null) {
                verticalPath=new Path();
            }else{
                verticalPath.reset();
            }
            if(isInEditMode()){
                verticalPath.moveTo(0,getShadowWidth());
                verticalPath.lineTo(getShadowWidth(),getShadowWidth());
                verticalPath.lineTo(getShadowWidth(),getShadowWidth()+contentHeight);
                verticalPath.lineTo(0,getShadowWidth()+contentHeight);
                verticalPath.close();
            }else{
                verticalPath.addRect(new RectF(0,getShadowWidth(),getShadowWidth(),getShadowWidth()+contentHeight),Path.Direction.CW);
            }

        }
        /*private void setCornerRightTopShadowPath() {
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
        }*/
    }


    public int getBgColor() {
        return bgColor;
    }

    public void setBgColor(int bgColor) {
        this.bgColor = bgColor;
//        shadowDrawable.setBgColor(bgColor);
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
