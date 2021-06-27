package com.vikendu.theservicesapp.model;

public class Message {

    private String message;
    private String author;

    public Message(String message, String author) {
        this.message = message;
        this.author = author;
    }

    public Message() {
    }

    public String getMessage() {
        return message;
    }

    public String getAuthor() {
        return author;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
