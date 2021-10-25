package com.cs506.cash_splitting.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = Property.TABLE_NAME)
public class Property {

    public static final String TABLE_NAME = "propertydb";

    @Id
    @Column
    private int pid;

    @Column
    private String address;

    @Column
    private int rid;

    @Column
    private double monthly_rent;

    @Column
    private String type;

    @Column
    private String pname = "Lucky";

    public void setRid(int rid) {
        this.rid = rid;
    }

    public int getRid() {
        return rid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getPid() {
        return pid;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getMonthly_rent() {
        return monthly_rent;
    }

    public void setMonthly_rate(double monthly_rent) {
        this.monthly_rent = monthly_rent;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
