package com.ilikexy.biyesheji.entity;

public class ArticleContent {
    private String articleUId;//文章id
    private String articleTitle;//文章标题
    private String articleContent;//文章内容
    private String authorName;//作者名字
    private String articleTime;//文章时间
    private String articlePancounts;//文章点赞数
    private String articleComcounts;//文章评论数
    private String articleCollectcounts;//文章收藏数
    private String articlePicUid;//文章图片uid
    private String authorPicUid;//作者图片uid
    public ArticleContent(String uid,String title,String con,String name,String time,String pan,String com,String coll,String arpic,String aupic){
        this.articleUId = uid;
        this.articleTitle = uid;
        this.articleContent = con;
        this.authorName = name;
        this.articleTime = time;
        this.articlePancounts = pan;
        this.articleComcounts = com;
        this.articleCollectcounts = coll;
        this.articlePicUid = arpic;
        this.authorPicUid = aupic;
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
    public String getArticleContent() {
        return articleContent;
    }
    public void setArticleContent(String articlecontent) {
        this.articleContent = articlecontent;
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
    public String getArticlePancounts() {
        return articlePancounts;
    }
    public void setArticlePancounts(String articlePancounts) {
        this.articlePancounts = articlePancounts;
    }
    public String getArticleComcounts() {
        return articleComcounts;
    }
    public void setArticleComcounts(String articleComcounts) {
        this.articleComcounts = articleComcounts;
    }
    public String getArticleCollectcounts() {
        return articleCollectcounts;
    }
    public void setArticleCollectcounts(String articleCollectcounts) {
        this.articleCollectcounts = articleCollectcounts;
    }
    public String getArticlePicUid() {
        return articlePicUid;
    }
    public void setArticlePicUid(String articlePicUid) {
        this.articlePicUid = articlePicUid;
    }
    public String getAuthorPicUid() {
        return authorPicUid;
    }
    public void setAuthorPicUid(String authorPicUid) {
        this.authorPicUid = authorPicUid;
    }
}
