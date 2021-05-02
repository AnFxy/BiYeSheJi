package com.ilikexy.biyesheji.entity;

public class TiAnswer {
    private String tiUId;//题目的uid
    private String mTitle;
    private String mContentA;
    private String mContentB;
    private String mContentC;
    private String mContentD;
    private String mAnswer;//答案
    public TiAnswer(String ctiuid,String ctitle,String contenta,String contentb,String contentc,String contentd,String canswer){
        this.tiUId = ctiuid;
        this.mTitle = ctitle;
        this.mContentA = contenta;
        this.mContentB = contentb;
        this.mContentC = contentc;
        this.mContentD = contentd;
        this.mAnswer = canswer;
    }

    public String getTiUId() {
        return tiUId;
    }

    public void setTiUId(String tiUId) {
        this.tiUId = tiUId;
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
}
