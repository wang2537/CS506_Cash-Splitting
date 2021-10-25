package com.cs506.cash_splitting.model;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table (name = Repair.TABLE_NAME)
public class Repair {

    public static final String TABLE_NAME = "repairdb";

    //TODO: specify foregin key relations, "one-to-one", "one-to-many" in spring boot annotation

    @Id
    @Column
    private int mid;         // maintenance id

    @Column
    private int uid;

    @Column
    private String description = "x broke";

    @Column
    private int pid;

    @Column
    private String status = "processing";

    @Column
    private String create_time = getStringToday();

    @Column
    private String modify_time = getStringToday();

    @Column
    private int priority;


    public static String getStringToday() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    public int getUid() {return uid;}

    public void setUid() {this.uid = uid;}

    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getModify_time() {
        return modify_time;
    }

    public void setModify_time(String modify_time) {
        this.modify_time = modify_time;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
