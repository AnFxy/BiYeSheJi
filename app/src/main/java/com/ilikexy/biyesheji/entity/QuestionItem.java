package com.ilikexy.biyesheji.entity;

import android.graphics.Bitmap;

public class QuestionItem {
    private String questionUid;
    private String pictureUid;
    private String questionTitle;

    private Bitmap authorPic;
    private String authorName;
    private String questionTime;

    private String questionContent;
    private String questionAgree;
    private String questionAnswer;
    public QuestionItem(String cquestionuid,String cpictureuid,String cquestiontitle,Bitmap cauthorpic,
                        String cauthorname,String cquestiontime,String cquestioncontent,String cquestionagree,
                        String cquestionanswer){
        this.questionUid = cquestionuid;
        this.pictureUid = cpictureuid;
        this.questionTitle = cquestiontitle;

        this.authorPic = cauthorpic;
        this.authorName = cauthorname;
        this.questionTime = cquestiontime;

        this.questionContent = cquestioncontent;
        this.questionAgree = cquestionagree;
        this.questionAnswer = cquestionanswer;
    }
    public String getPictureUid() {
        return pictureUid;
    }

    public void setPictureUid(String pictureUid) {
        this.pictureUid = pictureUid;
    }

    public String getQuestionUid() {
        return questionUid;
    }

    public void setQuestionUid(String questionUid) {
        this.questionUid = questionUid;
    }

    public String getQuestionTitle() {
        return questionTitle;
    }

    public void setQuestionTitle(String questionTitle) {
        this.questionTitle = questionTitle;
    }

    public Bitmap getAuthorPic() {
        return authorPic;
    }

    public void setAuthorPic(Bitmap authorPic) {
        this.authorPic = authorPic;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getQuestionTime() {
        return questionTime;
    }

    public void setQuestionTime(String questionTime) {
        this.questionTime = questionTime;
    }

    public String getQuestionContent() {
        return questionContent;
    }

    public void setQuestionContent(String questionContent) {
        this.questionContent = questionContent;
    }

    public String getQuestionAgree() {
        return questionAgree;
    }

    public void setQuestionAgree(String questionAgree) {
        this.questionAgree = questionAgree;
    }

    public String getQuestionAnswer() {
        return questionAnswer;
    }

    public void setQuestionAnswer(String questionAnswer) {
        this.questionAnswer = questionAnswer;
    }
}
