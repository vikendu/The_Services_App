package com.vikendu.theservicesapp.models;

public class ChatContact {

    //TODO: Add a node like follows when someone decides to chat: (S2WP+Gdw2)+(9JzL+kH42) = S2WPGdw29JzLkH42
    //Someone decides to chat = creating a ChatContact object from ProviderDetails.java

    private String providerName;
    private String receiverName;

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public ChatContact(String providerName, String receiverName) {
        this.providerName = providerName;
        this.receiverName = receiverName;
    }

    public ChatContact() { }
}