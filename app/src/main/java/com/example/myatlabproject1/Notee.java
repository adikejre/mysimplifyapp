package com.example.myatlabproject1;

import com.google.firebase.Timestamp;

public class Notee {
    private String text;
    private boolean completed;
    private Timestamp created;
    private String userid;

    public Notee() {
    }

    public Notee(String text, boolean completed, Timestamp created, String userid) {
        this.text = text;
        this.completed = completed;
        this.created = created;
        this.userid = userid;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    @Override
    public String toString() {
        return "Notee{" +
                "text='" + text + '\'' +
                ", completed=" + completed +
                ", created=" + created +
                ", userid='" + userid + '\'' +
                '}';
    }
}
