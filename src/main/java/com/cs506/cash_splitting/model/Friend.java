package com.cs506.cash_splitting.model;
import javax.persistence.*;

@Entity
@IdClass(FriendId.class)
@Table (name = Friend.TABLE_NAME)
public class Friend{
    public static final String TABLE_NAME = "frienddb";

    @Id
    @Column
    private int friend_id;

    @Id
    @Column
    private int uid;

    @Column
    private String status = "valid";

    public Friend() {
    }

    public Friend(int friend_id, int uid) {
        this.friend_id = friend_id;
        this.uid = uid;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public void setFriend_id(int friend_id) {
        this.friend_id = friend_id;
    }

    public int getFriend_id() {
        return friend_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
