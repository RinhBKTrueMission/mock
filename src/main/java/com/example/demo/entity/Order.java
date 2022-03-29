package com.example.demo.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Orders")
public class Order {


    @Id
    @Column(name = "ID", length = 50)
    private String id;

    @Column(name = "Order_Date", nullable = false)
    private Date orderDate;

    @Column(name = "Order_Num", nullable = false)
    private int orderNum;

    @Column(name = "Amount", nullable = false)
    private double amount;

    @Column(name = "Customer_Name", length = 255, nullable = false)
    private String customerName;

    @Column(name = "Customer_Address", length = 255, nullable = false)
    private String customerAddress;

    @Column(name = "Customer_Email", length = 128, nullable = false)
    private String customerEmail;

    @Column(name = "Customer_Phone", length = 128, nullable = false)
    private String customerPhone;

    public Order(String id, Date orderDate, int orderNum, double amount, String customerName, String customerAddress, String customerEmail, String customerPhone) {
        this.id = id;
        this.orderDate = orderDate;
        this.orderNum = orderNum;
        this.amount = amount;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerEmail = customerEmail;
        this.customerPhone = customerPhone;
    }

    public Order() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

}