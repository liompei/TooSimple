package com.liompei.toosimple.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by BLM on 2016/7/14.
 */
public class PicCommentBean extends BmobObject {
    private String id, name, content, email;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
