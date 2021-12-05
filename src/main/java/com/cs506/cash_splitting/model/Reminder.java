package com.cs506.cash_splitting.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = Reminder.TABLE_NAME)
public class Reminder {
    public static final String TABLE_NAME = "reminderdb";

    @Id
    @Column
    private int rid;

    @Column
    private int source;

    @Column
    private int destination;

    @Column
    private String status = "pending";

    @Column
    private String modify_time = FriendApp.getStringToday();

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

    public String getModify_time() {
        return modify_time;
    }

    public void setModify_time(String modify_time) {
        this.modify_time = modify_time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getRid() {
        return this.rid;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }
}
