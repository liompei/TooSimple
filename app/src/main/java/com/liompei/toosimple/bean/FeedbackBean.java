package com.liompei.toosimple.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by BLM on 2016/7/15.
 */
public class FeedbackBean extends BmobObject {

    private String email, content;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
