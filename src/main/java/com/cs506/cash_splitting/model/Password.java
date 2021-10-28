package com.cs506.cash_splitting.model;

import javax.persistence.*;


@Entity
@Table (name = Password.TABLE_NAME)
public class Password {
    public static final String TABLE_NAME = "passworddb";

    @Id
    @Column
    private int pid;

    @Column
    private int uid;

    @Column
    private String password;

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public int getPid() {
        return pid;
    }

    public int getUid() {
        return uid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }
}

