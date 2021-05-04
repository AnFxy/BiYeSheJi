package com.ilikexy.biyesheji.entity;

public class TiWrongSend {
    private String tiUid;
    private String wrongChoose;
    public TiWrongSend(String tiuid,String wrongchoose){
        this.tiUid = tiuid;
        this.wrongChoose = wrongchoose;
    }

    public String getTiUid() {
        return tiUid;
    }

    public void setTiUid(String tiUid) {
        this.tiUid = tiUid;
    }

    public String getWrongChoose() {
        return wrongChoose;
    }

    public void setWrongChoose(String wrongChoose) {
        this.wrongChoose = wrongChoose;
    }
}
