package com.ilikexy.biyesheji.entity;

import android.graphics.Bitmap;

public class RecyclerComments {
    private String authorName;
    private Bitmap mBitmap;
    private String comContent;
    private String comTime;
    public RecyclerComments(String aname,Bitmap mbitmap,String comcon,String comtime){
        this.authorName = aname;
        this.mBitmap = mbitmap;
        this.comContent = comcon;
        this.comTime = comtime;
    }
    public String getAuthorName() {
        return authorName;
    }
    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public Bitmap getmBitmap() {
        return mBitmap;
    }

    public void setmBitmap(Bitmap mBitmap) {
        this.mBitmap = mBitmap;
    }

    public String getComContent() {
        return comContent;
    }
    public void setComContent(String comContent) {
        this.comContent = comContent;
    }
    public String getComTime() {
        return comTime;
    }
    public void setComTime(String comTime) {
        this.comTime = comTime;
    }
}
