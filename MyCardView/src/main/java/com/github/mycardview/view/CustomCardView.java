package com.github.mycardview.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.Switch;

import com.github.mycardview.R;

/***
 *   created by android on 2019/7/31
 */
public class CustomCardView extends FrameLayout {
    private int bgColor;
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

    public CustomCardView(Context context) {
        super(context);
        init(null,R.attr.CustomCardViewStyle);
    }
    public CustomCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs,R.attr.CustomCardViewStyle);
    }
    public CustomCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs,defStyleAttr);
    }

    private void init(AttributeSet attrs,int defStyleAttr) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.CustomCardView,defStyleAttr,R.style.CustomCardView);

        ColorStateList backgroundColor;
        if(typedArray.hasValue(R.styleable.CustomCardView_bgColor)){
            backgroundColor=typedArray.getColorStateList(R.styleable.CustomCardView_bgColor);
        }else{
            TypedArray array = getContext().obtainStyledAttributes(new int[]{android.R.attr.colorBackground});
            backgroundColor =ColorStateList.valueOf(array.getColor(0, Color.WHITE));
            array.recycle();
        }

        shadowRadius=typedArray.getDimension(R.styleable.CustomCardView_shadowRadius,dp2px(10));
        shadowRadiusLeft=typedArray.getDimension(R.styleable.CustomCardView_shadowRadiusLeft,0);
        shadowRadiusTop=typedArray.getDimension(R.styleable.CustomCardView_shadowRadiusTop,0);
        shadowRadiusRight=typedArray.getDimension(R.styleable.CustomCardView_shadowRadiusRight,0);
        shadowRadiusBottom=typedArray.getDimension(R.styleable.CustomCardView_shadowRadiusBottom,0);
        shadowAlpha=typedArray.getDimension(R.styleable.CustomCardView_shadowAlpha,0);
        shadowWidth=typedArray.getDimension(R.styleable.CustomCardView_shadowWidth,0);
        shadowOffsetX=typedArray.getDimension(R.styleable.CustomCardView_shadowOffsetX,0);
        shadowOffsetY=typedArray.getDimension(R.styleable.CustomCardView_shadowOffsetY,0);

        shadowStartColor=typedArray.getColor(R.styleable.CustomCardView_shadowStartColor,ContextCompat.getColor(getContext(),R.color.customCardView_default_shadowStartColor));
        shadowCenterColor=typedArray.getColor(R.styleable.CustomCardView_shadowCenterColor,Color.TRANSPARENT);
        shadowEndColor=typedArray.getColor(R.styleable.CustomCardView_shadowEndColor,ContextCompat.getColor(getContext(),R.color.customCardView_default_shadowEndColor));

        shadowStartColorWeight=typedArray.getDimension(R.styleable.CustomCardView_shadowStartColorWeight,dp2px(10));
        shadowCenterColorWeight=typedArray.getDimension(R.styleable.CustomCardView_shadowCenterColorWeight,dp2px(10));
        shadowEndColorWeight=typedArray.getDimension(R.styleable.CustomCardView_shadowEndColorWeight,dp2px(10));

        typedArray.recycle();


        Drawable drawable=getBGDrawable(backgroundColor);
    }

    private ShadowDrawable getBGDrawable(ColorStateList backgroundColor) {
        ShadowDrawable shadowDrawable=new ShadowDrawable(backgroundColor);
        return shadowDrawable;
    }
    private int dp2px(int value){
        return (int) (getContext().getResources().getDisplayMetrics().density*value);
    }
}
