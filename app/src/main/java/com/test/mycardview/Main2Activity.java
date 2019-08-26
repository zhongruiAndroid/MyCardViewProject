package com.test.mycardview;

import android.net.Uri;
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
    AppCompatSeekBar endStartColor;

    int startColor;
    int endColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ccv = findViewById(R.id.ccv);

        initView();

    }

    private void initView() {
        sbStartColor = findViewById(R.id.sbStartColor);
        sbStartColor.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        endStartColor = findViewById(R.id.endStartColor);
        endStartColor.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

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
