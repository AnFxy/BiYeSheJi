package com.ilikexy.biyesheji.entity;

public class ArticleComments {
    private String authorName;
    private String pictureUid;
    private String comContent;
    private String comTime;
    public ArticleComments(String aname,String picuid,String comcon,String comtime){
        this.authorName = aname;
        this.pictureUid = picuid;
        this.comContent = comcon;
        this.comTime = comtime;
    }
    public String getAuthorName() {
        return authorName;
    }
    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }
    public String getPictureUid() {
        return pictureUid;
    }
    public void setPictureUid(String pictureUid) {
        this.pictureUid = pictureUid;
    }
    public String getComContent() {
        return comContent;
    }
    public void setComContent(String comContent) {
        this.comContent = comContent;
    }
    public String getComTime() {
        return comTime;
    }
    public void setComTime(String comTime) {
        this.comTime = comTime;
    }
}
