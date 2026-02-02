package com.example.marhaba.Domains;

import java.io.Serializable;

public class CategoryDomain implements Serializable {
    private long id;
    private String titles;
    private String catUrl;
    private String type;

    public CategoryDomain(long id, String titles, String catUrl, String type) {
        this.id = id;
        this.titles = titles;
        this.catUrl = catUrl;
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitles() {
        return titles;
    }

    public void setTitles(String titles) {
        this.titles = titles;
    }

    public String getCatUrl() {
        return catUrl;
    }

    public void setCatUrl(String catUrl) {
        this.catUrl = catUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
