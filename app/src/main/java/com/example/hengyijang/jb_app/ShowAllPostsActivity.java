package com.example.hengyijang.jb_app;

import android.content.Intent;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
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

public class ShowAllPostsActivity extends AppCompatActivity {
    Button toPost;
    TextView postUname,postContent,postCreateTime;
    ListView allPostsList;
    List<Map<String,Object>> list = new ArrayList<Map<String, Object>>();
    Looper looper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_posts);

        toPost = findViewById(R.id.toPost);
        allPostsList = findViewById(R.id.postsList);
        /*postContent = findViewById(R.id.showPostContent);
        postCreateTime = findViewById(R.id.showPostCreateTime);
        postUname = findViewById(R.id.showPostUname);*/
        looper = this.getMainLooper();

        final Intent intent = getIntent();
        try {
            JSONArray jsonArray = new JSONArray(intent.getStringExtra("responseData"));
            for (int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Map<String,Object> map = new HashMap<String, Object>();
                map.put("postContent",jsonObject.get("pContent"));
                map.put("postUname",jsonObject.get("uName"));
                map.put("postCreateTime",jsonObject.get("pCreateTime"));
                list.add(map);
                SimpleAdapter adapter = new SimpleAdapter(
                        ShowAllPostsActivity.this,
                        list,
                        R.layout.all_posts_layout,
                        new String[]{"postContent","postUname","postCreateTime"},
                        new int[]{R.id.showPostContent,R.id.showPostUname,R.id.showPostCreateTime}
                );
                allPostsList.setAdapter(adapter);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        toPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(ShowAllPostsActivity.this,PostActivity.class);
                startActivity(intent);
            }
        });

        allPostsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               String uName = (String)((TextView)view.findViewById(R.id.showPostUname)).getText();
               String pCreateTime = (String)((TextView)view.findViewById(R.id.showPostCreateTime)).getText();
               String pContent = (String)((TextView)view.findViewById(R.id.showPostContent)).getText();
               Intent intent1 = new Intent(ShowAllPostsActivity.this,ShowPostDetailActivity.class);
               intent1.putExtra("uName",uName);
               intent1.putExtra("pCreateTime",pCreateTime);
               intent1.putExtra("pContent",pContent);
               startActivity(intent1);
            }
        });


    }
}
