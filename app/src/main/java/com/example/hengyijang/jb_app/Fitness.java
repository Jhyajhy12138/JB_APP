package com.example.hengyijang.jb_app;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.os.Vibrator;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Fitness extends AppCompatActivity {
    Button start, reset;
    Chronometer cc;
    Vibrator vv;
    boolean isflag = false;
    long tt;
    int num = 0;
    String fTime;
    String ID;
    EditText ed;


    private static String[] PERMISSION = {
            Manifest.permission.VIBRATE
    };

    private static int REQUEST_PERMISSION_CODE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fitness);

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.VIBRATE) !=
                    PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, PERMISSION, REQUEST_PERMISSION_CODE);

            }
        }
        ed = (EditText) findViewById(R.id.editText);
        start = (Button) findViewById(R.id.start);
        reset = (Button) findViewById(R.id.reset);
        cc = (Chronometer) findViewById(R.id.chronometer);
        vv = (Vibrator) this.getSystemService(VIBRATOR_SERVICE);

        cc.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                String time = cc.getText().toString();
                //02:23
                int new_time = Integer.parseInt(time.substring(0, time.indexOf(":"))) * 60
                        + Integer.parseInt(time.substring(time.length() - 2));

            }
        });

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isflag == false) {
                    start.setText("暂停");
                    cc.setBase(SystemClock.elapsedRealtime() - tt);
                    //开始计时时，默认减去休眠的时间
                    //tt = SystemClock.elapsedRealtime() - cc.getBase();
                    //时间复位
                    reset.setEnabled(false);
                    cc.start();
                    isflag = true;
                } else {
                    start.setText("开始");
                    reset.setEnabled(true);
                    fTime = cc.getText().toString();
                    ID = ed.getText().toString();
                    getAsyn();
                    tt = SystemClock.elapsedRealtime() - cc.getBase();
                    cc.stop();
                    isflag = false;
                }

            }
        });


        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset.setEnabled(false);
                //保存当前计时器的计时时间
//                cc.setBase(0);
                cc.setBase(SystemClock.elapsedRealtime());
                tt = 0;
                for (int i = 0; i < 10; i++) {
                    //lv.setText("");
                }
                num = 0;
            }
        });
    }


    public void getAsyn() {
        new Thread(new Runnable() {
            public void run() {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url("http://10.13.31.199:8888/jb/fitness/add.do?fTime=" + fTime + "cId=" + ID).build();
                Call call = client.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                        System.out.println("Fail");
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String responseData = response.body().string();
                    }
                });
            }
        }
        ).start();
    }
}
