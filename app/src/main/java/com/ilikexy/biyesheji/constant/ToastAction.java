package com.ilikexy.biyesheji.constant;

import android.content.Context;
import android.widget.Toast;

public class ToastAction {
    public static void startToast(Context context, String content){
            Toast.makeText(context,content,Toast.LENGTH_SHORT).show();
    }
}
