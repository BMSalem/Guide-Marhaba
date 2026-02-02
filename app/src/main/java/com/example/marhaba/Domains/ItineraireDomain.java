package com.example.marhaba.Domains;

import java.io.Serializable;
import java.util.Date;

public class ItineraireDomain implements Serializable {
    private int id;
    private int attraction;
    private String horaire;
    private String imgUrl;
    private double perso_score;
    private String perso_comment;

    public ItineraireDomain(int id, int attraction, String horaire, String imgUrl, double perso_score, String perso_comment) {
        this.id = id;
        this.attraction = attraction;
        this.horaire = horaire;
        this.imgUrl = imgUrl;
        this.perso_score = perso_score;
        this.perso_comment = perso_comment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAttraction() {
        return attraction;
    }

    public void setAttraction(int attraction) {
        this.attraction = attraction;
    }

    public String getHoraire() {
        return horaire;
    }

    public void setHoraire(String horaire) {
        this.horaire = horaire;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public double getPerso_score() {
        return perso_score;
    }

    public void setPerso_score(double perso_score) {
        this.perso_score = perso_score;
    }

    public String getPerso_comment() {
        return perso_comment;
    }

    public void setPerso_comment(String perso_comment) {
        this.perso_comment = perso_comment;
    }
}
