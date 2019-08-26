package com.test.mycardview;

import android.graphics.Color;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSeekBar;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.TextView;

import com.github.mycardview.view.CustomCardView;

import java.util.List;

public class Main2Activity extends AppCompatActivity {
    CustomCardView ccv;
    AppCompatSeekBar sbStartColor;
    AppCompatSeekBar sbEndColor;

    int startColor;
    int endColor;
    private int alphaStart;
    private int alphaEnd;

    TextView tvStartColor;
    TextView tvEndColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        tvStartColor = findViewById(R.id.tvStartColor);
        tvEndColor = findViewById(R.id.tvEndColor);
        ccv = findViewById(R.id.ccv);
        startColor=ContextCompat.getColor(this,R.color.startcolor);
        endColor=ContextCompat.getColor(this,R.color.endcolor);

        alphaStart = Color.alpha(startColor);
        alphaEnd = Color.alpha(endColor);
        initView();

        int a1=Color.parseColor("#05CCCCCC");
        int a2=Color.parseColor("#08CCCCCC");
        int a3=Color.parseColor("#0aCCCCCC");
        int a4=Color.parseColor("#0dCCCCCC");
        int a5=Color.parseColor("#0fCCCCCC");

        Log.e("=====","====a1="+Color.alpha(a1));
        Log.e("=====","====a2="+Color.alpha(a2));
        Log.e("=====","====a3="+Color.alpha(a3));
        Log.e("=====","====a4="+Color.alpha(a4));
        Log.e("=====","====a5="+Color.alpha(a5));

    }
    private int[] getRGB(int color){
        int red = (color & 0xff0000) >> 16;
        int green = (color & 0x00ff00) >> 8;
        int blue = (color & 0x0000ff);
        return new int[]{red,green,blue};
    }

    private void initView() {
        sbStartColor = findViewById(R.id.sbStartColor);
        sbStartColor.setProgress(alphaStart);
        tvStartColor.setText("开始颜色"+alphaStart+"");
        sbStartColor.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int argb = Color.argb(progress, getRGB(startColor)[0], getRGB(startColor)[1], getRGB(startColor)[2]);
                ccv.setShadowStartColor(argb);
                ccv.setShadowCenterColor(argb);
                ccv.complete();
                tvStartColor.setText("开始颜色"+progress);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        sbEndColor = findViewById(R.id.sbEndColor);
        sbEndColor.setProgress(alphaEnd);
        tvEndColor.setText("结束颜色"+alphaEnd+"");
        sbEndColor.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int argb = Color.argb(progress, getRGB(endColor)[0], getRGB(endColor)[1], getRGB(endColor)[2]);
                ccv.setShadowEndColor(argb);
                ccv.complete();
                tvEndColor.setText("结束颜色"+progress);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
