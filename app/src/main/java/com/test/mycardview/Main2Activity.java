package com.test.mycardview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        final TimeCountDown timeCountDown=new TimeCountDown();
        Log.e("=====","=====start");
        timeCountDown.startForMillis(10000, 500, 200, new TimeCountDown.TimeCallback() {
            @Override
            public void onNext(long timeSecond) {
                Log.e("=====","====="+timeSecond);
            }
            @Override
            public void onComplete() {
                Log.e("=====","=====onComplete");
            }
        });
        /*timeCountDown.start(10, new TimeCountDown.TimeCallback() {
            @Override
            public void onNext(long timeSecond) {
                Log.e("=====","====="+timeSecond);
            }
            @Override
            public void onComplete() {
                Log.e("=====","=====onComplete");
            }
        });*/
    }
}
