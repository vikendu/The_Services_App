package com.vikendu.theservicesapp.admin;

public class Admin {

    private boolean active;

    public Admin() { }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Admin(boolean active) {
        this.active = active;
    }
}
