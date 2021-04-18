package com.ilikexy.biyesheji.entity;

import android.graphics.Bitmap;

public class ArticleList {
    private String articleUId;
    private String articleTitle;
    private String authorName;
    private String articleTime;
    private String articleComcounts;
    private String articleType;
    private String articlePicUid;
    private Bitmap articlePicture;

    public ArticleList(String uid, String title, String name, String time,
                       String comcounts, String type, String picuid, Bitmap bitmap){
        this.articleUId = uid;
        this.articleTitle = title;
        this.authorName = name;
        this.articleTime = time;
        this.articleComcounts = comcounts;
        this.articleType = type;
        this.articlePicUid = picuid;
        this.articlePicture = bitmap;
    }

    public String getArticleComcounts() {
        return articleComcounts;
    }
    public void setArticleComcounts(String articleComcounts) {
        this.articleComcounts = articleComcounts;
    }
    public String getArticleType() {
        return articleType;
    }
    public void setArticleType(String articleType) {
        this.articleType = articleType;
    }
    public String getArticlePicUid() {
        return articlePicUid;
    }
    public void setArticlePicUid(String articlePicUid) {
        this.articlePicUid = articlePicUid;
    }
    public String getArticleUId() {
        return articleUId;
    }
    public void setArticleUId(String articleUId) {
        this.articleUId = articleUId;
    }
    public String getArticleTitle() {
        return articleTitle;
    }
    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }
    public String getAuthorName() {
        return authorName;
    }
    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }
    public String getArticleTime() {
        return articleTime;
    }
    public void setArticleTime(String articleTime) {
        this.articleTime = articleTime;
    }
    public Bitmap getArticlePicture() {
        return articlePicture;
    }
    public void setArticlePicture(Bitmap articlePicture) {
        this.articlePicture = articlePicture;
    }
}
