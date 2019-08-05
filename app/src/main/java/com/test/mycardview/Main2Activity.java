package com.test.mycardview;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.List;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        TextView tv=findViewById(R.id.tv);
       /* timeCountDown.startForMillis(10000, 500, 200, new TimeCountDown.TimeCallback() {
            @Override
            public void onNext(long timeSecond) {
                Log.e("=====","====="+timeSecond);
            }
            @Override
            public void onComplete() {
                Log.e("=====","=====onComplete");
            }
        });*/
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

        Uri data = getIntent().getData();
        StringBuilder stringBuilder=new StringBuilder();
        if (data != null) {
            String scheme = data.getScheme();
            stringBuilder.append("scheme:"+scheme+"\n");

            String host = data.getHost();
            stringBuilder.append("host:"+host+"\n");

            int port = data.getPort();
            stringBuilder.append("port:"+port+"\n");

            String path = data.getPath();
            stringBuilder.append("path:"+path+"\n");

            List<String> pathSegments = data.getPathSegments();
            for (int i = 0; i < pathSegments.size(); i++) {
                String s = pathSegments.get(i);
                stringBuilder.append("pathSegments"+i+":"+s+"\n");
            }

            String query = data.getQuery();
            String paramFirst=data.getQueryParameter("paramFirst");
            stringBuilder.append("paramFirst:"+paramFirst+"\n");

            String paramSecond=data.getQueryParameter("paramSecond");
            stringBuilder.append("paramSecond:"+paramSecond+"\n");

        }

        tv.setText(stringBuilder.toString());
    }
}
