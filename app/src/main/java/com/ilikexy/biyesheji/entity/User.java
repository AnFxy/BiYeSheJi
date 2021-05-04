package com.ilikexy.biyesheji.entity;

public class User {
    private String usename;
    private String password;
    private String fakename;
    private String picuid;
    private String sex;
    private String school;
    private String classer;
    public User(String a,String b,String c,String d,String e,String f,String g){
        this.usename = a;
        this.password = b;
        this.fakename = c;
        this.picuid = d;
        this.sex = e;
        this.school = f;
        this.classer = g;
    }
    public String getUsename() {
        return usename;
    }
    public void setUsename(String usename) {
        this.usename = usename;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getFakename() {
        return fakename;
    }
    public void setFakename(String fakename) {
        this.fakename = fakename;
    }
    public String getPicuid() {
        return picuid;
    }
    public void setPicuid(String picuid) {
        this.picuid = picuid;
    }
    public String getSex() {
        return sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
    public String getClasser() {
        return classer;
    }
    public void setClasser(String classer) {
        this.classer = classer;
    }
    public String getSchool() {
        return school;
    }
    public void setSchool(String school) {
        this.school = school;
    }


}

