package com.example.hengyijang.jb_app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ShowPostDetailActivity extends AppCompatActivity {
    TextView detail_uName,detail_pCreateTime,detail_pContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_post_detail);

        detail_pContent = findViewById(R.id.detail_pContent);
        detail_pCreateTime = findViewById(R.id.detail_pCreateTime);
        detail_uName = findViewById(R.id.detail_uName);

        detail_uName.setText(getIntent().getStringExtra("uName"));
        detail_pCreateTime.setText(getIntent().getStringExtra("pCreateTime"));
        detail_pContent.setText(getIntent().getStringExtra("pContent"));
    }
}
