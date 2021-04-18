package com.ilikexy.biyesheji.zidingyiview;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ilikexy.biyesheji.R;
import com.ilikexy.biyesheji.constant.ValueAnimator;

public class DialogContainer {
    public static Dialog dialog;
    public static void showDialog(Context context, int c_dialogid, int kongjianid){
        dialog = new Dialog(context, R.style.JumpDialog);//指明Dialog容器弹出的动画风格
        //根据layout文件绘制出加载动画的视图
        LinearLayout linear = (LinearLayout) LayoutInflater.from(context).inflate(c_dialogid,null);
        LoadDialog loadDialog = (LoadDialog)linear.findViewById(kongjianid);//绑定id
        ValueAnimator.catoonShow1(loadDialog,0,8,8000);
        dialog.setContentView(linear);//将视图加入容器
        Window dialogWindow = dialog.getWindow();//获得窗口
        dialogWindow.setGravity(Gravity.CENTER);//放置在中心
        WindowManager.LayoutParams lp = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        lp.x = 0; // 新位置X坐标
        lp.y = 0; // 新位置Y坐标
        lp.width = (int)context.getResources().getDisplayMetrics().widthPixels; // 宽度
        linear.measure(0, 0);
        lp.height = linear.getMeasuredHeight();
        lp.alpha = 0.7f; // 透明度
        dialogWindow.setAttributes(lp);
        dialog.setCancelable(false);
        dialog.show();
    }
    public static void showPictureDialog(final Context context, int c_dialogid, final String zhaoxiang_action, final String dcim_action){
        dialog = new Dialog(context, R.style.JumpDialog);//指明Dialog容器弹出的动画风格
        //根据layout文件绘制出加载动画的视图
        LinearLayout linear = (LinearLayout) LayoutInflater.from(context).inflate(c_dialogid,null);
        TextView kongjian1 = (TextView)linear.findViewById(R.id.text_camera);
        TextView kongjian2 = (TextView)linear.findViewById(R.id.text_dcim);
        TextView kongjian3 = (TextView)linear.findViewById(R.id.text_cancel);
        kongjian1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //发送打开相机通知
                Intent intent = new Intent(zhaoxiang_action);
                context.sendBroadcast(intent);
                deleteDialog();
            }
        });
        kongjian2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(dcim_action);
                context.sendBroadcast(intent1);
                deleteDialog();
            }
        });
        kongjian3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteDialog();
            }
        });
        dialog.setContentView(linear);//将视图加入容器
        Window dialogWindow = dialog.getWindow();//获得窗口
        dialogWindow.setGravity(Gravity.BOTTOM);//放置在底部
        WindowManager.LayoutParams lp = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        lp.x = 0; // 新位置X坐标
        lp.y = 0; // 新位置Y坐标
        lp.width = (int)context.getResources().getDisplayMetrics().widthPixels; // 宽度
        linear.measure(0, 0);
        lp.height = linear.getMeasuredHeight();
        lp.alpha = 1; // 透明度
        dialogWindow.setAttributes(lp);
        dialog.setCancelable(false);
        dialog.show();
    }


    public static void deleteDialog(){
        if (dialog!=null){
            dialog.cancel();
        }
    }
}
