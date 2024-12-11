package com.pro.huyfood.model;

import java.io.Serializable;

public class Image implements Serializable {
    private String url;

    public Image() {
    }

    public Image(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
