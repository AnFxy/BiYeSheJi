package com.ilikexy.biyesheji.entity;

public class TiResult {
    public int rightCount;
    public int allCount;
    public int doDays;
    public TiResult(int rightcount,int allcount,int dodays){
        this.rightCount = rightcount;
        this.allCount = allcount;
        this.doDays = dodays;
    }

    public int getRightCount() {
        return rightCount;
    }

    public void setRightCount(int rightCount) {
        this.rightCount = rightCount;
    }

    public int getAllCount() {
        return allCount;
    }

    public void setAllCount(int allCount) {
        this.allCount = allCount;
    }

    public int getDoDays() {
        return doDays;
    }

    public void setDoDays(int doDays) {
        this.doDays = doDays;
    }
}
