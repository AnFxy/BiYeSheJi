package com.ilikexy.biyesheji.entity;

import android.graphics.Bitmap;

public class VideoItem {
    private String videoId;
    private Bitmap bitmap;
    private String time;
    private String title;
    private int boFang;
    private int comments;
    public VideoItem(String video,Bitmap bitmaper,String timer,String titler,int bofang,int comment){
        this.videoId = video;
        this.bitmap = bitmaper;
        this.time = timer;
        this.title = titler;
        this.boFang = bofang;
        this.comments = comment;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getBoFang() {
        return boFang;
    }

    public void setBoFang(int boFang) {
        this.boFang = boFang;
    }

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }
}
