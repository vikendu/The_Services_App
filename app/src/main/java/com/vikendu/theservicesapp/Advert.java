package com.vikendu.theservicesapp;

public class Advert {

    private String category;
    private String tagLine;
    private String adDescription;
    private String adPrice;
    private float providersRating;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

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
        return "₹ "+adPrice;
    }

    public void setAdPrice(String adPrice) {
        this.adPrice = adPrice;
    }

    public float getProvidersRating() {
        return providersRating;
    }

    public void setProvidersRating(float providersRating) {
        this.providersRating = providersRating;
    }

    public Advert(String category, String tagLine, String adDescription, String adPrice, float providersRating){
        this.category = category;
        this.tagLine = tagLine;
        this.adDescription = adDescription;
        this.adPrice = adPrice;
        this.providersRating = providersRating;
    }
}
