package com.example.javafx_project.entities;



import java.io.Serializable;


public class Supplier implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String name;
    private String CIN;
    private String address;
    private Integer phoneNumber;
    //export to services
    //write from database
    //check input type from int or date ??
    //date enters as date or string ??
    //save and update in one method(check and save or update)


    public Supplier() {
    }

    public Supplier(Integer id, String name, String CIN, String address, Integer phoneNumber) {
        this.id = id;
        this.name = name;
        this.CIN = CIN;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCIN() {
        return CIN;
    }

    public void setCIN(String CIN) {
        this.CIN = CIN;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Integer phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "Supplier{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", CIN='" + CIN + '\'' +
                ", address='" + address + '\'' +
                ", phoneNumber=" + phoneNumber +
                '}';
    }
}
