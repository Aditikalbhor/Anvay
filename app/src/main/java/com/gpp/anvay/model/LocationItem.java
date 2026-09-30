package com.gpp.anvay.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LocationItem implements Serializable {
    private String id;
    private String roomNumber;
    private String name;
    private String category;
    private String department;
    private String floor;
    private String wing;
    private String description;
    private String inCharge;
    private String operatingHours;
    private List<String> facilities;
    private List<String> nearbyLandmarks;
    private String nearestExit;
    private boolean isBookmarked;

    public LocationItem() {
        this.facilities = new ArrayList<>();
        this.nearbyLandmarks = new ArrayList<>();
    }

    public LocationItem(String id, String roomNumber, String name, String category,
                        String department, String floor, String wing, String description,
                        String inCharge, String operatingHours, List<String> facilities,
                        List<String> nearbyLandmarks, String nearestExit) {
        this.id = id;
        this.roomNumber = roomNumber;
        this.name = name;
        this.category = category;
        this.department = department;
        this.floor = floor;
        this.wing = wing;
        this.description = description;
        this.inCharge = inCharge;
        this.operatingHours = operatingHours;
        this.facilities = facilities != null ? facilities : new ArrayList<>();
        this.nearbyLandmarks = nearbyLandmarks != null ? nearbyLandmarks : new ArrayList<>();
        this.nearestExit = nearestExit;
        this.isBookmarked = false;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getRoomNumber() { return roomNumber; }
    public void setRoomNumber(String roomNumber) { this.roomNumber = roomNumber; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getFloor() { return floor; }
    public void setFloor(String floor) { this.floor = floor; }

    public String getWing() { return wing; }
    public void setWing(String wing) { this.wing = wing; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getInCharge() { return inCharge; }
    public void setInCharge(String inCharge) { this.inCharge = inCharge; }

    public String getOperatingHours() { return operatingHours; }
    public void setOperatingHours(String operatingHours) { this.operatingHours = operatingHours; }

    public List<String> getFacilities() { return facilities; }
    public void setFacilities(List<String> facilities) { this.facilities = facilities; }

    public List<String> getNearbyLandmarks() { return nearbyLandmarks; }
    public void setNearbyLandmarks(List<String> nearbyLandmarks) { this.nearbyLandmarks = nearbyLandmarks; }

    public String getNearestExit() { return nearestExit; }
    public void setNearestExit(String nearestExit) { this.nearestExit = nearestExit; }

    public boolean isBookmarked() { return isBookmarked; }
    public void setBookmarked(boolean bookmarked) { isBookmarked = bookmarked; }
}
