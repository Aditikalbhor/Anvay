package com.gpp.anvay.model;

import java.io.Serializable;

public class EmergencyContact implements Serializable {
    private String id;
    private String title;
    private String contactPerson;
    private String phoneNumber;
    private String location;
    private String iconType; // medical, fire, police, admin

    public EmergencyContact() {}

    public EmergencyContact(String id, String title, String contactPerson, String phoneNumber, String location, String iconType) {
        this.id = id;
        this.title = title;
        this.contactPerson = contactPerson;
        this.phoneNumber = phoneNumber;
        this.location = location;
        this.iconType = iconType;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContactPerson() { return contactPerson; }
    public void setContactPerson(String contactPerson) { this.contactPerson = contactPerson; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getIconType() { return iconType; }
    public void setIconType(String iconType) { this.iconType = iconType; }
}
