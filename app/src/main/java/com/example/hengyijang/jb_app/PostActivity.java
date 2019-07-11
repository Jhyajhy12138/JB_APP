package com.example.hengyijang.jb_app;

import android.content.Intent;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PostActivity extends AppCompatActivity {
    EditText pContent;
    Button faBu,toAllPost;
    Looper looper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        pContent = findViewById(R.id.editText4);
        faBu = findViewById(R.id.btnPublish);
        toAllPost = findViewById(R.id.backToAllPosts);
        looper = this.getMainLooper();

        toAllPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        OkHttpClient okHttpClient = new OkHttpClient();
                        Request request = new Request.Builder().url("http://10.13.31.193:8080/post/showAllPosts").build();
                        Call call = okHttpClient.newCall(request);
                        call.enqueue(new Callback() {
                            @Override
                            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                                System.out.println("Fail");
                            }

                            @Override
                            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                                String responseData = response.body().string();
                                Intent intent = new Intent(PostActivity.this,ShowAllPostsActivity.class);
                                intent.putExtra("responseData",responseData);
                                startActivity(intent);
                            }
                        });
                    }
                }).start();
            }
        });

    }

    public void publish(View view){
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient okHttpClient = new OkHttpClient();
                Request request = new Request.Builder().url("http://10.13.31.193:8080/post/addPost?uId=1&pContent=" + pContent.getText().toString()).build();
                Call call = okHttpClient.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        System.out.println("Response fail");
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        String responseData = response.body().string();
                        if (responseData.equals("success")){
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    OkHttpClient okHttpClient = new OkHttpClient();
                                    Request request = new Request.Builder().url("http://10.13.31.193:8080/post/showAllPosts").build();
                                    Call call = okHttpClient.newCall(request);
                                    call.enqueue(new Callback() {
                                        @Override
                                        public void onFailure(@NotNull Call call, @NotNull IOException e) {
                                            System.out.println("Fail");
                                        }

                                        @Override
                                        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                                            String responseData = response.body().string();
                                            Intent intent = new Intent(PostActivity.this,ShowAllPostsActivity.class);
                                            intent.putExtra("responseData",responseData);
                                            startActivity(intent);
                                        }
                                    });
                                }
                            }).start();
                        }
                    }
                });
            }
        }).start();
    }
}

