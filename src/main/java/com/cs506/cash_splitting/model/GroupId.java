package com.cs506.cash_splitting.model;

import java.io.Serializable;

public class GroupId implements Serializable {
    private int gid;

    private int uid;

    public GroupId(){
    }

    public GroupId(int gid, int uid) {
        this.gid = gid;
        this.uid = uid;
    }
}
