package com.vikendu.theservicesapp.models;

import java.util.ArrayList;

public class ServiceProvider {

    private int adCount;
    private String firstName;
    private String lastName;
    private String rating;
    private String email;
    private ArrayList<Advert> advertArrayList;
    private ArrayList<String> contactList;

    public ServiceProvider(int adCount, String firstName, String lastName, String rating, String email, ArrayList<Advert> advertArrayList, ArrayList<String> contactList) {
        this.adCount = adCount;
        this.firstName = firstName;
        this.lastName = lastName;
        this.rating = rating;
        this.email = email;
        this.advertArrayList = advertArrayList;
        this.contactList = contactList;
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

    public ArrayList<Advert> getAdvertArrayList() {
        return advertArrayList;
    }

    public void setAdvertArrayList(ArrayList<Advert> advertArrayList) {
        this.advertArrayList = advertArrayList;
    }

    public ArrayList<String> getContactList() {
        return contactList;
    }

    public void setContactList(ArrayList<String> contactList) {
        this.contactList = contactList;
    }
}
