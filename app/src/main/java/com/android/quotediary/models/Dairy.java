package com.android.quotediary.models;

import java.util.Date;

public class Dairy {
    String content = "Today is sooo";
    Date date = new Date();

    public Dairy(String content, Date date) {
        this.content = content;
        this.date = date;
    }

    public Dairy() {
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
