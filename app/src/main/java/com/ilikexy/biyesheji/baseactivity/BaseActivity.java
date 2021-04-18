package com.ilikexy.biyesheji.baseactivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import com.ilikexy.biyesheji.LoginActivity;
import com.ilikexy.biyesheji.R;
import com.ilikexy.biyesheji.constant.ActivityControler;
import com.jaeger.library.StatusBarUtil;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.colorAccent));
        }//底部导航栏颜色
        ActivityControler.addActivity(this);
        StatusBarUtil.setColor(this, Color.parseColor("#ededed"),0);
        StatusBarUtil.setLightMode(this);
    }
    //常用启动活动的函数
    public static void jump(Context context, Class cls){
        Intent intent = new Intent();
        intent.setClass(context,cls);
        context.startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityControler.deleteActivity(this);
    }
}
