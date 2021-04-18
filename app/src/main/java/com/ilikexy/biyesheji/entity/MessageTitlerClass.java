package com.ilikexy.biyesheji.entity;



public class MessageTitlerClass {
    private String mName;//标题名字
    private int mColor;//背景框的颜色
    private int mPositioner;//第几个
    //构造方法
    public MessageTitlerClass(String name,int color,int position){
        mName = name;
        mColor = color;
        mPositioner = position;
    }
    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public int getmColor() {
        return mColor;
    }

    public void setmColor(int mColor) {
        this.mColor = mColor;
    }

    public int getmPositioner() {
        return mPositioner;
    }

    public void setmPositioner(int positioner) {
        this.mPositioner = positioner;
    }
}
