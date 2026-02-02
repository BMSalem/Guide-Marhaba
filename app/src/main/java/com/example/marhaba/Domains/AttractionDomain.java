package com.example.marhaba.Domains;


import androidx.annotation.Nullable;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.firestore.GeoPoint;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttractionDomain implements Serializable {
    private long id;
    private String name;
    private Map<String, Double> location;
    private double score;
    private double price;
    private String imgUrl;
    private String description;
    private String close;
    private String city;
    public AttractionDomain(long id, String name, GeoPoint location, double score, double price, String imgUrl, String description, String close, String city) {
        this.id = id;
        this.name = name;
        this.location = new HashMap<>();
        this.location.put("latitude", location.getLatitude());
        this.location.put("longitude", location.getLongitude());
        this.score = score;
        this.price = price;
        this.imgUrl = imgUrl;
        this.description = description;
        this.close = close;
        this.city = city;
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

    public Map<String, Double> getLocation() {
        return location;
    }

    public void setLocation(Map<String, Double> location) {
        this.location = location;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getClose() {
        return close;
    }

    public void setClose(String close) {
        this.close = close;
    }

    public String getCity() {return city;}

    public void setCity(String city) {this.city = city;}

    @Override
    public boolean equals(Object obj) {
        return getId() == ((AttractionDomain)obj).getId() && getCity().equals(((AttractionDomain)obj).getCity());
    }
}
