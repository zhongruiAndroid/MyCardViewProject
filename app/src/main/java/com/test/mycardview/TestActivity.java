package com.test.mycardview;

import android.graphics.Color;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.annotation.IntRange;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatSeekBar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.github.mycardview.ShadowFrameLayout;
import com.github.selectcolordialog.SelectColorDialog;
import com.github.selectcolordialog.SelectColorListener;

public class TestActivity extends AppCompatActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {

    private ShadowFrameLayout ccv;
    private TextView tvStartColor;
    private TextView tvBgColor;
    private AppCompatSeekBar sbStartColor;
    private AppCompatSeekBar sbEndColor;
    private AppCompatSeekBar sbBgRadius;
    private AppCompatSeekBar sbBgRadiusLeftTop;
    private AppCompatSeekBar sbBgRadiusRightTop;
    private AppCompatSeekBar sbBgRadiusLeftBottom;
    private AppCompatSeekBar sbBgRadiusRightBottom;
    private AppCompatSeekBar sbShadowAlpha;
    private AppCompatSeekBar sbShadowWidth;
    private AppCompatSeekBar sbShadowOffsetLeft;
    private AppCompatSeekBar sbShadowOffsetTop;
    private AppCompatSeekBar sbShadowOffsetRight;
    private AppCompatSeekBar sbShadowOffsetBottom;
    private AppCompatSeekBar sbShadowClipInLength;
    private AppCompatSeekBar sbShadowClipOutLength;
    private SelectColorDialog selectColorDialog;
    private AppCompatCheckBox cbChangeLinear;

    private AppCompatSeekBar sbShadowGradient1;
    private AppCompatSeekBar sbShadowGradient2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        initView();
        initData();
    }

    private int startColor;
    private int bgColor;

    private int startColorAlpha;
    private int endColorAlpha;

    public static int argb(
            @IntRange(from = 0, to = 255) int alpha,
            @IntRange(from = 0, to = 255) int red,
            @IntRange(from = 0, to = 255) int green,
            @IntRange(from = 0, to = 255) int blue) {
        return (alpha << 24) | (red << 16) | (green << 8) | blue;
    }

    float ratio = 1;

    float ratio2 = 255;

    private void initView() {

        ccv = findViewById(R.id.ccv);
        sbShadowGradient1 = findViewById(R.id.sbShadowGradient1);
        sbShadowGradient1.setOnSeekBarChangeListener(this);

        sbShadowGradient2 = findViewById(R.id.sbShadowGradient2);
        sbShadowGradient2.setOnSeekBarChangeListener(this);

        cbChangeLinear = findViewById(R.id.cbChangeLinear);
        ccv.setOnlyLinear(cbChangeLinear.isChecked());
        cbChangeLinear.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ccv.setOnlyLinear(cbChangeLinear.isChecked());
            }
        });

        tvStartColor = findViewById(R.id.tvStartColor);
        tvStartColor.setOnClickListener(this);


        tvBgColor = findViewById(R.id.tvBgColor);
        tvBgColor.setOnClickListener(this);

        sbStartColor = findViewById(R.id.sbStartColor);
        sbStartColor.setOnSeekBarChangeListener(this);

        sbEndColor = findViewById(R.id.sbEndColor);
        sbEndColor.setOnSeekBarChangeListener(this);

        sbBgRadius = findViewById(R.id.sbBgRadius);
        sbBgRadius.setOnSeekBarChangeListener(this);

        sbBgRadiusLeftTop = findViewById(R.id.sbBgRadiusLeftTop);
        sbBgRadiusLeftTop.setOnSeekBarChangeListener(this);

        sbBgRadiusRightTop = findViewById(R.id.sbBgRadiusRightTop);
        sbBgRadiusRightTop.setOnSeekBarChangeListener(this);

        sbBgRadiusLeftBottom = findViewById(R.id.sbBgRadiusLeftBottom);
        sbBgRadiusLeftBottom.setOnSeekBarChangeListener(this);

        sbBgRadiusRightBottom = findViewById(R.id.sbBgRadiusRightBottom);
        sbBgRadiusRightBottom.setOnSeekBarChangeListener(this);

        sbShadowAlpha = findViewById(R.id.sbShadowAlpha);
        sbShadowAlpha.setOnSeekBarChangeListener(this);

        sbShadowWidth = findViewById(R.id.sbShadowWidth);
        sbShadowWidth.setOnSeekBarChangeListener(this);

        sbShadowOffsetLeft = findViewById(R.id.sbShadowOffsetLeft);
        sbShadowOffsetLeft.setOnSeekBarChangeListener(this);

        sbShadowOffsetTop = findViewById(R.id.sbShadowOffsetTop);
        sbShadowOffsetTop.setOnSeekBarChangeListener(this);

        sbShadowOffsetRight = findViewById(R.id.sbShadowOffsetRight);
        sbShadowOffsetRight.setOnSeekBarChangeListener(this);

        sbShadowOffsetBottom = findViewById(R.id.sbShadowOffsetBottom);
        sbShadowOffsetBottom.setOnSeekBarChangeListener(this);

        sbShadowClipInLength = findViewById(R.id.sbShadowClipInLength);
        sbShadowClipInLength.setOnSeekBarChangeListener(this);

        sbShadowClipOutLength = findViewById(R.id.sbShadowClipOutLength);
        sbShadowClipOutLength.setOnSeekBarChangeListener(this);

    }

    private void initData() {
        startColor = ActivityCompat.getColor(this, R.color.black);
        bgColor = Color.WHITE;

        startColorAlpha = 35;
        endColorAlpha = 0;

        sbStartColor.setProgress(startColorAlpha);
        sbEndColor.setProgress(endColorAlpha);

        tvStartColor.setBackgroundColor(startColor);
        tvBgColor.setBackgroundColor(bgColor);

        ccv.setShadowWidth(dp2px(13));

        sbShadowWidth.setProgress(13);

        ccv.setBgRadius(dp2px(5));

        sbBgRadius.setProgress(5);
        sbBgRadiusLeftTop.setProgress(5);
        sbBgRadiusLeftBottom.setProgress(5);
        sbBgRadiusRightTop.setProgress(5);
        sbBgRadiusRightBottom.setProgress(5);

        sbShadowAlpha.setProgress(100);

        sbShadowGradient1.setProgress((int) (ccv.getControlPointFirstY() * 10));
        sbShadowGradient2.setProgress((int) (ccv.getControlPointSecondY() * 10));
    }

    public PointF test(float fraction, PointF start, PointF control1, PointF control2, PointF endPoint) {
        PointF pointF = new PointF();
        pointF.x = (float) (Math.pow((1 - fraction), 3) * start.x + 3 * control1.x * fraction * Math.pow((1 - fraction), 2) + 3 * control2.x * Math.pow(fraction, 2) * (1 - fraction) + endPoint.x * Math.pow(fraction, 3));
        pointF.y = (float) (Math.pow((1 - fraction), 3) * start.y + 3 * control1.y * fraction * Math.pow((1 - fraction), 2) + 3 * control2.y * Math.pow(fraction, 2) * (1 - fraction) + endPoint.y * Math.pow(fraction, 3));
        return pointF;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvStartColor:
            case R.id.tvBgColor:
                showSelectColorDialog(v.getId());
                break;
        }
    }

    private void showSelectColorDialog(final int viewId) {
        selectColorDialog = new SelectColorDialog(this);
        selectColorDialog.setListener(new SelectColorListener() {
            @Override
            public void selectColor(int color) {
                switch (viewId) {
                    case R.id.tvStartColor:
                        startColor = color;
                        ccv.setShadowStartColor(getColorByAlpha(startColor, startColorAlpha));
                        tvStartColor.setBackgroundColor(getColorByAlpha(startColor, startColorAlpha));
                        break;
                    case R.id.tvBgColor:
                        bgColor = color;
                        ccv.setBgColor(bgColor);
                        tvBgColor.setBackgroundColor(bgColor);
                        break;
                }
            }
        });
        selectColorDialog.show();
    }

    private int[] getRGB(int color) {
        int red = (color & 0xff0000) >> 16;
        int green = (color & 0x00ff00) >> 8;
        int blue = (color & 0x0000ff);
        return new int[]{red, green, blue};
    }

    private int dp2px(int value) {
        return (int) (getResources().getDisplayMetrics().density * value);
    }

    private int getColorByAlpha(int color, int alpha) {
        if (color == 0) {
            return Color.TRANSPARENT;
        }
        return Color.argb(alpha, getRGB(color)[0], getRGB(color)[1], getRGB(color)[2]);
    }


    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        Log.i("=====", "=====progress:=" + progress);
        switch (seekBar.getId()) {
            case R.id.sbStartColor:
                startColorAlpha = progress;
                ccv.setShadowStartColor(getColorByAlpha(startColor, progress));
                break;
            case R.id.sbEndColor:
                endColorAlpha = progress;
                ccv.setShadowEndColor(getColorByAlpha(startColor, progress));
                break;
            case R.id.sbBgRadius:
                ccv.setBgRadius(dp2px(progress));
                break;
            case R.id.sbBgRadiusLeftTop:
                ccv.setBgRadiusLeftTop(dp2px(progress));
                break;
            case R.id.sbBgRadiusRightTop:
                ccv.setBgRadiusRightTop(dp2px(progress));
                break;
            case R.id.sbBgRadiusLeftBottom:
                ccv.setBgRadiusLeftBottom(dp2px(progress));
                break;
            case R.id.sbBgRadiusRightBottom:
                ccv.setBgRadiusRightBottom(dp2px(progress));
                break;
            case R.id.sbShadowAlpha:
                ccv.setShadowAlpha(progress * 1f / 100);
                break;
            case R.id.sbShadowWidth:
                ccv.setShadowWidth(dp2px(progress));
                break;
            case R.id.sbShadowOffsetLeft:
                ccv.setShadowOffsetLeft(dp2px(progress));
                break;
            case R.id.sbShadowOffsetTop:
                ccv.setShadowOffsetTop(dp2px(progress));
                break;
            case R.id.sbShadowOffsetRight:
                ccv.setShadowOffsetRight(dp2px(progress));
                break;
            case R.id.sbShadowOffsetBottom:
                ccv.setShadowOffsetBottom(dp2px(progress));
                break;
            case R.id.sbShadowClipInLength:
                ccv.setShadowClipInLength(dp2px(progress));
                break;
            case R.id.sbShadowClipOutLength:
                ccv.setShadowClipOutLength(dp2px(progress));
                break;
            case R.id.sbShadowGradient1:
                float ratio1 = progress * 1f / sbShadowGradient1.getMax();
                ccv.setControlPointFirstY(ratio1);
                break;
            case R.id.sbShadowGradient2:
                float ratio2 = progress * 1f / sbShadowGradient2.getMax();
                ccv.setControlPointSecondY(ratio2);
                break;
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
