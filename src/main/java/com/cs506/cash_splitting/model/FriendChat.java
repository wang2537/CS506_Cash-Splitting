package com.cs506.cash_splitting.model;


import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table (name = FriendChat.TABLE_NAME)
public class FriendChat {
    public static final String TABLE_NAME = "friend_chatdb";

    @Id
    @Column
    private int fcid;

    @Column
    private int source;

    @Column
    private int destination;

    @Column
    private String content = "Empty message";

    @Column
    private String sendtime = getStringToday();


    public int getFcid() {
        return fcid;
    }

    public void setFcid(int fcid) {
        this.fcid = fcid;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public int getDestination() {
        return destination;
    }

    public void setDestination(int destination) {
        this.destination = destination;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSendtime() {
        return sendtime;
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
