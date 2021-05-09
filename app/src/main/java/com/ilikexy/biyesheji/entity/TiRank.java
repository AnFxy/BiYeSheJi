package com.ilikexy.biyesheji.entity;

import android.graphics.Bitmap;

public class TiRank {
    private String fakename;
    private String usepicuid;
    private Bitmap bitmap;
    private int right;
    public TiRank(String usepicuid,Bitmap cbitmap,String cfakename,int cright){
        this.fakename = cfakename;
        this.usepicuid = usepicuid;
        this.bitmap = cbitmap;
        this.right = cright;
    }

    public String getFakename() {
        return fakename;
    }

    public void setFakename(String fakename) {
        this.fakename = fakename;
    }

    public String getUsepicuid() {
        return usepicuid;
    }

    public void setUsepicuid(String usepicuid) {
        this.usepicuid = usepicuid;
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
