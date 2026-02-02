package com.example.marhaba.Domains;

import com.google.firebase.Timestamp;

import java.io.Serializable;


public class WishListItemDomain implements Serializable {
    private AttractionDomain attr;
    private Timestamp date;

    public AttractionDomain getAttr() {
        return attr;
    }

    public void setAttr(AttractionDomain attr) {
        this.attr = attr;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public WishListItemDomain(AttractionDomain attr, Timestamp date) {
        this.attr = attr;
        this.date = date;
    }
}
