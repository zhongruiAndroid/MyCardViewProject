package com.test.mycardview;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Outline;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mycardview.MyCardView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private MyCardView mcv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mcv=findViewById(R.id.mcv);
        Button btList=findViewById(R.id.btList);
        btList.setOnClickListener(this);

        LinearLayout llContent=findViewById(R.id.llContent);

        for (int i = 0; i < tag.length; i++) {
            Button button=new Button(this);
            button.setAllCaps(false);
            button.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));
            button.setText(tagName[i]);
            final int finalI = i;
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    jumpPermission(tag[finalI]);
                }
            });
            llContent.addView(button);
        }



        mcv.setBackground(ContextCompat.getDrawable(this,R.color.app_hint_color));

        TextView textView=null;
        textView.getBackground();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btList:
                Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse("scheme://android.myapp.com/myapp/detail.htm?apkName=com.xunmeng.pinduoduo"));
                startActivity(intent);
//                goList();
//                jumpPermission(tag[0]);
            break;
        }
    }
    String []tag={
            "com.test.mycardview",
            "com.UCMobile",
            "com.speedsoftware.rootexplorer",
            "com.tencent.mobileqq",
            "com.eg.android.AlipayGphone",
            "com.tencent.mm",
            "com.sohu.inputmethod.sogou",
            "com.android.calendar",
            "com.cyjh.mobileanjian",
            "com.chaozh.iReaderFree15",
            "cn.wps.moffice_eng"
    };
    String jumpPage="com.huawei.systemmanager";
    String []tagName={
            "demo",
            "UC",
            "rootexplorer",
            "QQ",
            "Alipay",
            "微信",
            "sogou",
            "日历",
            "按键精灵",
            "掌阅阅读器",
            "WPS"
    };
    private void jumpPermission( String packageName) {
//        String a="com.test.mycardview";
//        String a="com.UCMobile";
//        String a="com.speedsoftware.rootexplorer";
//        String a="com.tencent.mobileqq";
//        String a="com.eg.android.AlipayGphone";
//        String a="com.tencent.mm";
//        String a="com.sohu.inputmethod.sogou";
//        String a="com.android.calendar";
//        String a="com.cyjh.mobileanjian";
//        String a="com.chaozh.iReaderFree15";
//        String a="cn.wps.moffice_eng";
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromParts("package", packageName, null);
        intent.setData(uri);
        startActivity(intent);
    }

    private void goList() {
        //adb shell dumpsys activity activities
        startActivity(new Intent(this,Main2Activity.class));
    }
}
