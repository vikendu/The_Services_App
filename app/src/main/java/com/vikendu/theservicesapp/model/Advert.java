package com.vikendu.theservicesapp.model;

public class Advert {

    private String category0;
    private String category1;
    private String tagLine;
    private String adDescription;
    private String adPrice;
    private boolean approval;
    private boolean isLive;

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

    public boolean isApproval() {
        return approval;
    }

    public void setApproval(boolean approval) {
        this.approval = approval;
    }

    public boolean isLive() {
        return isLive;
    }

    public void setLive(boolean live) {
        isLive = live;
    }

    public Advert(String category0, String category1, String tagLine, String adDescription, String adPrice, boolean approval, boolean isLive) {
        this.category0 = category0;
        this.category1 = category1;
        this.tagLine = tagLine;
        this.adDescription = adDescription;
        this.adPrice = adPrice;
        this.approval = approval;
        this.isLive = isLive;
    }
    public Advert() { }
}
