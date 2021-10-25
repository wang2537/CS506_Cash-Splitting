package com.cs506.cash_splitting.model;

import javax.persistence.*;


@Entity
@Table (name = User.TABLE_NAME)
public class User {
    public static final String TABLE_NAME = "userdb";

    @Id
    @Column
    private int uid;

    @Column
    private String username;

    @Column
    private String firstname;

    @Column
    private String lastname;

    @Column
    private String email = "example@example.com";

    @Column
    private double total_balance = 0;

    @Column
    private double borrowed = 0;

    @Column
    private double lent = 0;

    @Column
    private String default_currency = "USD";

    public int getUid() {return uid;}

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getBorrowed() {
        return borrowed;
    }

    public void setBorrowed(double borrowed) {
        this.borrowed = borrowed;
    }

    public double getLent() {
        return lent;
    }

    public void setLent(double lent) {
        this.lent = lent;
    }

    public double getTotal_balance() {
        return total_balance;
    }

    public void setTotal_balance(double total_balance) {
        this.total_balance = total_balance;
    }

    public String getDefault_currency() {
        return default_currency;
    }

    public void setDefault_currency(String default_currency) {
        this.default_currency = default_currency;
    }
}
