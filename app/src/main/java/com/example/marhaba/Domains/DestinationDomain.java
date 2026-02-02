package com.example.marhaba.Domains;


import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.firestore.GeoPoint;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DestinationDomain implements Serializable {
    private long id;
    private String title;
    private Map<String, Double> location;
    private String description;
    private String imgUrl;
    private List<AttractionDomain> attractions;
    private long categorie;

    public DestinationDomain(long id, String title, GeoPoint location, String description, String imgUrl, List<AttractionDomain> attractions, long categorie) {
        this.id = id;
        this.title = title;
        this.location = new HashMap<>();
        this.location.put("latitude", location.getLatitude());
        this.location.put("longitude", location.getLongitude());
        this.description = description;
        this.imgUrl = imgUrl;
        this.attractions = attractions;
        this.categorie = categorie;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Map<String, Double> getLocation() {
        return location;
    }

    public void setLocation(Map<String, Double> location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public List<AttractionDomain> getAttractions() {
        return attractions;
    }

    public void setAttractions(List<AttractionDomain> attractions) {
        this.attractions = attractions;
    }

    public long getCategorie() {
        return categorie;
    }

    public void setCategorie(long categorie) {
        this.categorie = categorie;
    }
}
