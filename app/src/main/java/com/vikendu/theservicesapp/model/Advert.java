package com.vikendu.theservicesapp.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Advert implements Serializable {

    private String category0;
    private String category1;
    private String tagLine;
    private String adDescription;
    private String adPrice;
    private String adId;
    private String uid;
    private boolean approved;
    private boolean isLive;
    // TODO: Add location here
    // TODO: Add String cachedRating variable here

    public String getTagLine() {
        return tagLine;
    }

    public void setTagLine(String tagLine) {
        this.tagLine = tagLine;
    }

    public String getAdDescription() {
        return adDescription;
    }

    public void setAdDescription(String adDescription) {
        this.adDescription = adDescription;
    }

    public String getAdPrice() {
        return adPrice;
    }

    public void setAdPrice(String adPrice) {
        this.adPrice = adPrice;
    }

    public String getCategory0() {
        return category0;
    }

    public void setCategory0(String category0) {
        this.category0 = category0;
    }

    public String getCategory1() {
        return category1;
    }

    public void setCategory1(String category1) {
        this.category1 = category1;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public boolean isLive() {
        return isLive;
    }

    public void setLive(boolean live) {
        isLive = live;
    }

    public String getAdId() {
        return adId;
    }

    public void setAdId(String adId) {
        this.adId = adId;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Advert(String category0, String category1, String tagLine, String adDescription, String adPrice, String adId, String uid, boolean approved, boolean isLive) {
        this.category0 = category0;
        this.category1 = category1;
        this.tagLine = tagLine;
        this.adDescription = adDescription;
        this.adPrice = adPrice;
        this.adId = adId;
        this.uid = uid;
        this.approved = approved;
        this.isLive = isLive;
    }

    public Advert() { }
}
