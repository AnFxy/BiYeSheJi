package com.ilikexy.biyesheji.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import com.ilikexy.biyesheji.R;
import com.ilikexy.biyesheji.baseactivity.BaseActivity;
import com.ilikexy.biyesheji.constant.ConstantClass;
import com.ilikexy.biyesheji.entity.VideoItem;

import java.util.ArrayList;
import java.util.List;

public class VideoTestData {

    public static List<VideoItem> getVideoItem(BaseActivity baseActivity){
         String uri1 = "android.resource://"+baseActivity.getPackageName()+"/"+R.raw.video1;
        String uri2 = "android.resource://"+baseActivity.getPackageName()+"/"+R.raw.video2;
         String uri3 = "android.resource://"+baseActivity.getPackageName()+"/"+R.raw.video3;
         String uri4 = "android.resource://"+baseActivity.getPackageName()+"/"+R.raw.video4;
         String uri5 = "android.resource://"+baseActivity.getPackageName()+"/"+R.raw.video5;
         String uri6 = "android.resource://"+baseActivity.getPackageName()+"/"+R.raw.video6;
        Bitmap bitmap1 = BitmapFactory.decodeResource(baseActivity.getResources(), R.drawable.video1);
        Bitmap bitmap2 = BitmapFactory.decodeResource(baseActivity.getResources(), R.drawable.video2);
        Bitmap bitmap3 = BitmapFactory.decodeResource(baseActivity.getResources(), R.drawable.video3);
        Bitmap bitmap4 = BitmapFactory.decodeResource(baseActivity.getResources(), R.drawable.video4);
        Bitmap bitmap5 = BitmapFactory.decodeResource(baseActivity.getResources(), R.drawable.video5);
        Bitmap bitmap6 = BitmapFactory.decodeResource(baseActivity.getResources(), R.drawable.video6);
        VideoItem item1 = new VideoItem(uri1,bitmap1,"3:32","万类霜天竞自由！",
                getShare(baseActivity,"video1"),0);
        VideoItem item2 = new VideoItem(uri2,bitmap2,"1:34","灵魂无处安放，《环保视频》大学生拍摄",
                getShare(baseActivity,"video2"),0);
        VideoItem item3 = new VideoItem(uri3,bitmap3,"1:36","震撼公益短片——《致我们的地球》",
                getShare(baseActivity,"video3"),0);
        VideoItem item4 = new VideoItem(uri4,bitmap4,"1:36","如何进行垃圾分类",
                getShare(baseActivity,"video4"),0);
        VideoItem item5 = new VideoItem(uri5,bitmap5,"3:27","黑色动画短片——《转折点》",
                getShare(baseActivity,"video5"),0);
        VideoItem item6 = new VideoItem(uri6,bitmap6,"3:32","全球变暖，或许我们需要做点什么",
                getShare(baseActivity,"video6"),0);

//        VideoItem item1 = new VideoItem(uri1,bitmap1,"3:32","万类霜天竞自由！",123,0);
//        VideoItem item2 = new VideoItem(uri2,bitmap2,"1:34","灵魂无处安放，《环保视频》大学生拍摄",184,0);
//        VideoItem item3 = new VideoItem(uri3,bitmap3,"1:36","震撼公益短片——《致我们的地球》",275,0);
//        VideoItem item4 = new VideoItem(uri4,bitmap4,"1:36","如何进行垃圾分类",1124,0);
//        VideoItem item5 = new VideoItem(uri5,bitmap5,"3:27","黑色动画短片——《转折点》",98,0);
//        VideoItem item6 = new VideoItem(uri6,bitmap6,"3:32","全球变暖，或许我们需要做点什么",336,0);
        List<VideoItem> lister = new ArrayList<>();
        lister.add(item1);
        lister.add(item2);
        lister.add(item3);
        lister.add(item4);
        lister.add(item5);
        lister.add(item6);
        return lister;
    }
    public static int getShare(BaseActivity baseActivity,String key){
        SharedPreferences sp = baseActivity.getSharedPreferences("video", Context.MODE_PRIVATE);
        int bofang = sp.getInt(key,0);
        return bofang;
    }
}
