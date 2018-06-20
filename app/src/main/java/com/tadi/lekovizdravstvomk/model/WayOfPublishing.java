package com.tadi.lekovizdravstvomk.model;

import com.yalantis.filter.model.FilterModel;

import org.jetbrains.annotations.NotNull;

public class WayOfPublishing implements FilterModel {

    private String id;
    private String opis, tip;

    public WayOfPublishing(String id, String opis, String tip) {
        this.id = id;
        this.opis = opis;
        this.tip = tip;
    }

    public WayOfPublishing() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    @NotNull
    @Override
    public String getText() {
        return getTip();
    }
}
