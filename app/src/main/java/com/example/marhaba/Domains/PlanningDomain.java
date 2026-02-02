package com.example.marhaba.Domains;

import java.io.Serializable;
import java.util.List;
import java.util.Date;

public class PlanningDomain implements Serializable {
    private int id;
    private String title;
    private int destination;
    private Date startDate;
    private Date endDate;
    private double score;
    private String comment;
    private List<ItineraireDomain> itineraires;
    private boolean finished;

    public PlanningDomain(int id, String title, int destination, Date startDate, Date endDate, double score, String comment, List<ItineraireDomain> itineraires) {
        this.id = id;
        this.title = title;
        this.destination = destination;
        this.startDate = startDate;
        this.endDate = endDate;
        this.score = score;
        this.comment = comment;
        this.itineraires = itineraires;
        this.finished = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDestination() {
        return destination;
    }

    public void setDestination(int destination) {
        this.destination = destination;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public List<ItineraireDomain> getItineraires() {
        return itineraires;
    }

    public void setItineraires(List<ItineraireDomain> itineraires) {
        this.itineraires = itineraires;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }
}
