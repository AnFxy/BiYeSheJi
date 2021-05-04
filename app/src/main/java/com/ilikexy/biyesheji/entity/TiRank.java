package com.ilikexy.biyesheji.entity;

import android.graphics.Bitmap;

public class TiRank {
    private String order;
    private String fakename;
    private String picUid;
    private Bitmap bitmap;
    private int right;
    public TiRank(String corder,String cfakename,String picuid,Bitmap cbitmap,int cright){
        this.order = corder;
        this.fakename = cfakename;
        this.picUid = picuid;
        this.bitmap = cbitmap;
        this.right = cright;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getFakename() {
        return fakename;
    }

    public void setFakename(String fakename) {
        this.fakename = fakename;
    }

    public String getPicUid() {
        return picUid;
    }

    public void setPicUid(String picUid) {
        this.picUid = picUid;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public int getRight() {
        return right;
    }

    public void setRight(int right) {
        this.right = right;
    }
}
