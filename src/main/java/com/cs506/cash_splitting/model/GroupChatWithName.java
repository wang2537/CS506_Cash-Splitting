package com.cs506.cash_splitting.model;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GroupChatWithName {

    public static final String TABLE_NAME = "group_chatdb";

    private int gcid;

    private int gid;

    private int uid;

    private String username;

    private String content = "Empty message";

    private String sendtime = getStringToday();

    public GroupChatWithName(GroupChat groupChat){
        this.content = groupChat.getContent();
        this.gcid = groupChat.getGcid();
        this.gid = groupChat.getGid();
        this.uid = groupChat.getUid();
        this.sendtime = groupChat.getSendtime();
        this.username = " ";

    }

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

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSendtime() {
        return sendtime;
    }

    public String getUsername() {
        return username;
    }

    public void setSendtime(String sendtime) {
        this.sendtime = sendtime;
    }


    public static String getStringToday() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(currentTime);
    }


}

