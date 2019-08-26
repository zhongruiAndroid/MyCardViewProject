package com.test.mycardview;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.github.mycardview.view.CustomCardView;

import java.util.List;

public class Main2Activity extends AppCompatActivity {
    CustomCardView ccv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ccv=findViewById(R.id.ccv);

        

    }
}
