package com.liompei.toosimple.bean;

import cn.bmob.v3.BmobObject;

/**
 * 新闻内容  新闻内容id,标题,内容,新闻列表id,发送者用户名,发送者邮箱
 * Created by BLM on 2016/7/12.
 */
public class NewsBean extends BmobObject {

    private String title, content, senderid, senderusername, senderemail;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSenderid() {
        return senderid;
    }

    public void setSenderid(String senderid) {
        this.senderid = senderid;
    }

    public String getSenderusername() {
        return senderusername;
    }

    public void setSenderusername(String senderusername) {
        this.senderusername = senderusername;
    }

    public String getSenderemail() {
        return senderemail;
    }

    public void setSenderemail(String senderemail) {
        this.senderemail = senderemail;
    }
}
