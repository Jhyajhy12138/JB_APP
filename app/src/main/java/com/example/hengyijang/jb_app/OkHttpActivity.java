package com.example.hengyijang.jb_app;

import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkHttpActivity extends AppCompatActivity {
    Button btnGetAsyn;
    Button btnGetSyn;
    EditText cname,cpassword;
    Looper looper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ok_http);

        btnGetAsyn = findViewById(R.id.btnGetAsyn);
        btnGetSyn = findViewById(R.id.btnGetSyn);
        cname = findViewById(R.id.editText);
        cpassword = findViewById(R.id.editText2);
        looper =this.getMainLooper();
    }

    //异步GET请求
    public void getAsyn(View view){
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                //get
//                Request request = new Request.Builder().url("http://10.13.31.193:8080/post/loginAli?cname="+cname.getText().toString()+"&cpassword="+cpassword.getText().toString()).build();
//                Request request = new Request.Builder().url("http://10.13.31.193:8080/post/addPost?cname="+cname.getText().toString()+"&cpassword="+cpassword.getText().toString()).build();
                Request request = new Request.Builder().url("http://10.13.31.193:8080/post/showAllPosts").build();

                //post
                /*RequestBody body = new FormBody.Builder().add("cname","admin").add("cpassword","123").build();
                Request request = new Request.Builder().post(body).url("http://10.13.31.193:8080/post/login").build();*/

                Call call = client.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        System.out.println("Fail");
                    }
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String responseData =response.body().string();
                        System.out.println(responseData);
                        showResponse(responseData);
                    }
                });
            }
        }).start();
    }


    //同步GET请求
    public void getSyn(View view){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder().url("http://www.baidu.com").build();
                    Response response = client.newCall(request).execute();
                    String responseData =response.body().string();
                    showResponse(responseData);
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
        ).start();
    }

    public void showResponse(String resposeData){
        //主线程中创建handler后会默认创建一个looper对象。但是子线程不会，需要手动创建。
        //启用Looper
        Looper.prepare();
        Toast.makeText(OkHttpActivity.this,resposeData,Toast.LENGTH_SHORT).show();
        Looper.loop();
    }
}
