package com.tadi.lekovizdravstvomk.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

public class ReviewForDrugs {

    private int id;

    private int idDrug;
    private String idUser;
    private String reviewBody;
    private String date;

    public ReviewForDrugs(int id, int idDrug, String idUser, String reviewBody, String date) {
        this.id = id;
        this.idDrug = idDrug;
        this.idUser = idUser;
        this.reviewBody = reviewBody;
        this.date = date;
    }

    public ReviewForDrugs() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdDrug() {
        return idDrug;
    }

    public void setIdDrug(int idDrug) {
        this.idDrug = idDrug;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getReviewBody() {
        return reviewBody;
    }

    public void setReviewBody(String reviewBody) {
        this.reviewBody = reviewBody;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
