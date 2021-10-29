package com.cs506.cash_splitting.model;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@IdClass(GroupId.class)
@Table (name = Group.TABLE_NAME)
public class Group {

    public static final String TABLE_NAME = "groupdb";

    @Id
    @Column
    private int gid;
                                // composite primary key.
    @Id
    @Column
    private int uid;

    @Column
    private String groupname = "Untitled";

    @Column
    private String status = "valid";

    public static String getStringToday() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    public int getUid() {return uid;}

    public void setUid(int uid) {this.uid = uid;}

    public String getGroupname() { return groupname;}

    public void setGroupname(String groupname) { this.groupname = groupname;}

    public int getGid() { return gid;}

    public void setGid(int gid) {this.gid = gid;}

    public String getStatus() {return status;}

    public void setStatus(String status) {this.status = status;}
}
