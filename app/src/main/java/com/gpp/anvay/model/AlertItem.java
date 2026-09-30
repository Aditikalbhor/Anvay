package com.gpp.anvay.model;

import java.io.Serializable;

public class AlertItem implements Serializable {
    private String id;
    private String title;
    private String message;
    private String category;    // Emergency, Maintenance, Relocation, Notice
    private String priority;    // Urgent, Medium, Normal
    private String timestamp;
    private String locationAffected;
    private boolean isActive;

    public AlertItem() {}

    public AlertItem(String id, String title, String message, String category,
                     String priority, String timestamp, String locationAffected, boolean isActive) {
        this.id = id;
        this.title = title;
        this.message = message;
        this.category = category;
        this.priority = priority;
        this.timestamp = timestamp;
        this.locationAffected = locationAffected;
        this.isActive = isActive;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getPriority() { return priority; }
    public void setPriority(String priority) { this.priority = priority; }

    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }

    public String getLocationAffected() { return locationAffected; }
    public void setLocationAffected(String locationAffected) { this.locationAffected = locationAffected; }

    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }
}
