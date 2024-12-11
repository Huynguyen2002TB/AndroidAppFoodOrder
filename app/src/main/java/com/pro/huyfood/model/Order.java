package com.pro.huyfood.model;

import java.io.Serializable;

public class Order implements Serializable {

    private long id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private int amount;
    private String foods;
    private int payment;
    private boolean completed;

    public Order() {
    }

    public Order(long id, String name, String email, String phone,
                 String address, int amount, String foods, int payment, boolean completed) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.amount = amount;
        this.foods = foods;
        this.payment = payment;
        this.completed = completed;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getFoods() {
        return foods;
    }

    public void setFoods(String foods) {
        this.foods = foods;
    }

    public int getPayment() {
        return payment;
    }

    public void setPayment(int payment) {
        this.payment = payment;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
