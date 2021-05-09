package com.ilikexy.biyesheji.entity;

public class TiRecord {
    private int mCounts;
    private int mRight;
    private String mTime;
    private String mUsetime;
    public TiRecord(int count,int right,String time,String usetime){
        this.mCounts = count;
        this.mRight = right;
        this.mTime = time;
        this.mUsetime = usetime;
    }
    public int getmCounts() {
        return mCounts;
    }
    public void setmCounts(int mCounts) {
        this.mCounts = mCounts;
    }
    public int getmRight() {
        return mRight;
    }
    public void setmRight(int mRight) {
        this.mRight = mRight;
    }
    public String getmTime() {
        return mTime;
    }
    public void setmTime(String mTime) {
        this.mTime = mTime;
    }
    public String getmUsetime() {
        return mUsetime;
    }
    public void setmUsetime(String mUsetime) {
        this.mUsetime = mUsetime;
    }



}
