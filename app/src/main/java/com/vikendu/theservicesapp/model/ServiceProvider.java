package com.vikendu.theservicesapp.model;

public class ServiceProvider {

    private int adCount;
    private String firstName;
    private String lastName;
    private String rating;
    private String email;
    private Advert advert;

    public ServiceProvider(int adCount, String firstName, String lastName, String rating, String email, Advert advert) {
        this.adCount = adCount;
        this.firstName = firstName;
        this.lastName = lastName;
        this.rating = rating;
        this.email = email;
        this.advert = advert;
    }

    public ServiceProvider() {
    }

    public int getAdCount() {
        return adCount;
    }

    public void setAdCount(int adCount) {
        this.adCount = adCount;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public Advert getAdvert() {
        return advert;
    }

    public void setAdvert() {
        this.advert = advert;
    }
}
