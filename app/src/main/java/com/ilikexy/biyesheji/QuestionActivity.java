package com.ilikexy.biyesheji;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.ilikexy.biyesheji.baseactivity.BaseActivity;

public class QuestionActivity extends BaseActivity {
    private String mUid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        init();
    }
    //初始化方法
    public void init(){
        mUid = getUidFromIntent();
    }
    //从碎片中获取数据
    public String getUidFromIntent(){
        Intent intent = getIntent();
        String uidd = intent.getStringExtra("uid");
        return uidd;
    }
    //设置从碎片中传递回来数据的intent
    public static void jumpToQuestion(Context context, String uid){
        Intent intent = new Intent(context,QuestionActivity.class);
        intent.putExtra("uid",uid);
        context.startActivity(intent);
    }
}