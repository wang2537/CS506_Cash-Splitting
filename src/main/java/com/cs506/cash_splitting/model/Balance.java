package com.cs506.cash_splitting.model;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table (name = Balance.TABLE_NAME)




public class Balance {
    public static final String TABLE_NAME = "historydb";


    @Id
    @Column
    private int cid;         // history id

    @Column
    private int destination_id;

    @Column
    private Double amount;

    @Column
    private String time = getStringToday();

    @Column
    private String description = "Transaction";

    public int getCid() { return cid;}

    public void setCid(int cid) {this.cid = cid;}

    public int getDestination_id() {return destination_id;}

    public void setDestination_id(int destination_id) {this.destination_id = destination_id;}

    public Double getAmount() {return amount;}

    public void setAmount(Double amount) {this.amount = amount;}

    public String getTime() {return time;}

    public void setTime(String time) {this.time = time;}

    public String getDescription() {return description;}

    public void setDescription(String description) {this.description = description;}


    public static String getStringToday() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        return dateString;
    }
}

