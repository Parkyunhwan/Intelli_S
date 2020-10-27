package com.login_signup_screendesign_demo.models;

import com.google.gson.annotations.SerializedName;

public class IntellisPost {
    @SerializedName("body")
    private String id;
    private String title;
    private String content;
    private String writer;
    private String bno;
    private String regTime;
    private String updateTime;

    public IntellisPost(String id, String title, String content, String writer, String bno, String regTime, String updateTime) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.bno = bno;
        this.regTime = regTime;
        this.updateTime = updateTime;
    }

    public String getId() {
        return id;
    }
    public String getContent() {
        return content;
    }
    public String getTitle() {
        return title;
    }
    public String getWriter(){return writer; }
    public String getBno(){return bno;}
    public String getRegTime(){return regTime;}
    public String getUpdatetime(){return updateTime;}

}
