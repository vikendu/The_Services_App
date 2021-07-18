package com.vikendu.theservicesapp.models;

import java.util.ArrayList;

public class ServiceReceiver {

    private String firstName;
    private String lastName;
    private ArrayList<String> contactList;

    public ServiceReceiver(String firstName, String lastName, ArrayList<String> contactList) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.contactList = contactList;
    }

    public ServiceReceiver() { }

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

    public ArrayList<String> getContactList() {
        return contactList;
    }

    public void setContactList(ArrayList<String> contactList) {
        this.contactList = contactList;
    }
}
