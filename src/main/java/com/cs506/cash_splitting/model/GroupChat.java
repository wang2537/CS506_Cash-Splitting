package com.cs506.cash_splitting.model;

import javax.persistence.*;

@Entity
@Table (name = GroupChat.TABLE_NAME)
public class GroupChat {

    public static final String TABLE_NAME = "group_chatdb";

    @Id
    @Column
    private int gcid;

    @Column
    private int gid;

    @Column
    private int uid;

    @Column
    private String content = "Empty message";

    @Column
    private String sendtime = "2021-11-13 13:38:28";


    public int getGid() {
        return gid;
    }

    public void setGid(int gid) {
        this.gid = gid;
    }

    public int getGcid() {
        return gcid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getUid() {
        return uid;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setGcid(int gcid) {
        this.gcid = gcid;
    }

    public String getSendtime() {
        return sendtime;
    }

    public void setSendtime(String sendtime) {
        this.sendtime = sendtime;
    }
}
