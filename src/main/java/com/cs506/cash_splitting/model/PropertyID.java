package com.cs506.cash_splitting.model;

public class PropertyID {
    private String address;
    private String username;
    private String Firstname;
    private String Lastname;

    public PropertyID(String address, String username, String Firstname, String Lastname) {
        this.address = address;
        this.username = username;
        this.Firstname = Firstname;
        this.Lastname = Lastname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFirstname() {
        return Firstname;
    }

    public void setFirstname(String firstname) {
        Firstname = firstname;
    }

    public String getLastname() {
        return Lastname;
    }

    public void setLastname(String lastname) {
        Lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
