package com.pro.huyfood.model;

import com.google.gson.Gson;

public class User {

    private String email;
    private String password;
    private boolean isAdmin;

    public User() {
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public String toJSon() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
