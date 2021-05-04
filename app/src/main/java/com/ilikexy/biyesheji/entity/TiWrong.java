package com.ilikexy.biyesheji.entity;

public class TiWrong {

    private String mTitle;
    private String mContentA;
    private String mContentB;
    private String mContentC;
    private String mContentD;
    private String mAnswer;//答案
    private String mWrong;//错误选项
    public TiWrong(String ctitle,String contenta,String contentb,String contentc,String contentd,String canswer,String wrong){

        this.mTitle = ctitle;
        this.mContentA = contenta;
        this.mContentB = contentb;
        this.mContentC = contentc;
        this.mContentD = contentd;
        this.mAnswer = canswer;
        this.mWrong = wrong;
    }
    public String getmTitle() {
        return mTitle;
    }
    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }
    public String getmContentA() {
        return mContentA;
    }
    public void setmContentA(String mContentA) {
        this.mContentA = mContentA;
    }
    public String getmContentB() {
        return mContentB;
    }
    public void setmContentB(String mContentB) {
        this.mContentB = mContentB;
    }
    public String getmContentC() {
        return mContentC;
    }
    public void setmContentC(String mContentC) {
        this.mContentC = mContentC;
    }
    public String getmContentD() {
        return mContentD;
    }
    public void setmContentD(String mContentD) {
        this.mContentD = mContentD;
    }
    public String getmAnswer() {
        return mAnswer;
    }
    public void setmAnswer(String mAnswer) {
        this.mAnswer = mAnswer;
    }
    public String getmWrong() {
        return mWrong;
    }
    public void setmWrong(String mWrong) {
        this.mWrong = mWrong;
    }

}

