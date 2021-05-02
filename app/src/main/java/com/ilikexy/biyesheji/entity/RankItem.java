package com.ilikexy.biyesheji.entity;

import android.graphics.Bitmap;

public class RankItem {
    private String orderNumber;
    private Bitmap mBitmaper;//用户头像
    private String mPicUid;//用户头像ID
    private String fakeName;//用户昵称
    private String rightNumber;//答对题数
    public RankItem(String corderNumber,Bitmap cmBitmaper,String cpictureuid,String cfakename,String crightNumber){
        this.orderNumber = corderNumber;
        this.mBitmaper = cmBitmaper;
        this.mPicUid = cpictureuid;
        this.fakeName = cfakename;
        this.rightNumber = crightNumber;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Bitmap getmBitmaper() {
        return mBitmaper;
    }

    public void setmBitmaper(Bitmap mBitmaper) {
        this.mBitmaper = mBitmaper;
    }

    public String getmPicUid() {
        return mPicUid;
    }

    public void setmPicUid(String mPicUid) {
        this.mPicUid = mPicUid;
    }

    public String getFakeName() {
        return fakeName;
    }

    public void setFakeName(String fakeName) {
        this.fakeName = fakeName;
    }

    public String getRightNumber() {
        return rightNumber;
    }

    public void setRightNumber(String rightNumber) {
        this.rightNumber = rightNumber;
    }
}
