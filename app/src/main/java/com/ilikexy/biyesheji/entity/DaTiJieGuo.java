package com.ilikexy.biyesheji.entity;

public class DaTiJieGuo {
    private int allCount;
    private int lvRight;
    public DaTiJieGuo(int allcount,int lvright){
        this.allCount = allcount;
        this.lvRight = lvright;
    }

    public int getAllCount() {
        return allCount;
    }

    public void setAllCount(int allCount) {
        this.allCount = allCount;
    }

    public int getLvRight() {
        return lvRight;
    }

    public void setLvRight(int lvRight) {
        this.lvRight = lvRight;
    }
}
