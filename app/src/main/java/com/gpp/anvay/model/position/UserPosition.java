package com.gpp.anvay.model.position;

import java.io.Serializable;
import java.util.Objects;

/**
 * Represents the user's current estimated position inside a building.
 * Supports anchor-based localization (e.g. at a specific room or checkpoint)
 * as well as optional coordinate-based representation for future spatial models.
 */
public class UserPosition implements Serializable {
    private String buildingId;
    private String floor;
    private String anchorLocationId;
    private Double x; // Optional spatial coordinate placeholder (nullable)
    private Double y; // Optional spatial coordinate placeholder (nullable)
    private float accuracy; // Estimated accuracy in meters
    private long timestamp; // Epoch timestamp in milliseconds
    private String providerSource; // e.g. "Mock", "QR", "BLE", "Manual"

    public UserPosition() {
        this.timestamp = System.currentTimeMillis();
        this.providerSource = "Unknown";
    }

    public UserPosition(String buildingId, String floor, String anchorLocationId, String providerSource) {
        this(buildingId, floor, anchorLocationId, null, null, 0.0f, System.currentTimeMillis(), providerSource);
    }

    public UserPosition(String buildingId, String floor, String anchorLocationId,
                        Double x, Double y, float accuracy, long timestamp, String providerSource) {
        this.buildingId = buildingId;
        this.floor = floor;
        this.anchorLocationId = anchorLocationId;
        this.x = x;
        this.y = y;
        this.accuracy = accuracy;
        this.timestamp = timestamp > 0 ? timestamp : System.currentTimeMillis();
        this.providerSource = providerSource != null ? providerSource : "Unknown";
    }

    public String getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(String buildingId) {
        this.buildingId = buildingId;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getAnchorLocationId() {
        return anchorLocationId;
    }

    public void setAnchorLocationId(String anchorLocationId) {
        this.anchorLocationId = anchorLocationId;
    }

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public float getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(float accuracy) {
        this.accuracy = accuracy;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getProviderSource() {
        return providerSource;
    }

    public void setProviderSource(String providerSource) {
        this.providerSource = providerSource;
    }

    public boolean hasCoordinates() {
        return x != null && y != null;
    }

    public boolean hasAnchor() {
        return anchorLocationId != null && !anchorLocationId.isEmpty();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserPosition that = (UserPosition) o;
        return timestamp == that.timestamp &&
                Objects.equals(buildingId, that.buildingId) &&
                Objects.equals(floor, that.floor) &&
                Objects.equals(anchorLocationId, that.anchorLocationId) &&
                Objects.equals(x, that.x) &&
                Objects.equals(y, that.y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(buildingId, floor, anchorLocationId, x, y, timestamp);
    }

    @Override
    public String toString() {
        return "UserPosition{" +
                "buildingId='" + buildingId + '\'' +
                ", floor='" + floor + '\'' +
                ", anchorLocationId='" + anchorLocationId + '\'' +
                ", x=" + x +
                ", y=" + y +
                ", accuracy=" + accuracy +
                ", providerSource='" + providerSource + '\'' +
                '}';
    }
}
