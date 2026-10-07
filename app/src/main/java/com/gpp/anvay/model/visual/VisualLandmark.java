package com.gpp.anvay.model.visual;

import java.io.Serializable;

/**
 * Represents a photo-grounded visual landmark anchored to a topological node
 * in the Computer/IT Building.
 */
public class VisualLandmark implements Serializable {

    // Controlled landmark types
    public static final String TYPE_ROOM_ENTRANCE = "ROOM_ENTRANCE";
    public static final String TYPE_CORRIDOR_VIEW = "CORRIDOR_VIEW";
    public static final String TYPE_STAIRCASE_VIEW = "STAIRCASE_VIEW";
    public static final String TYPE_STAIRCASE_LANDING = "STAIRCASE_LANDING";
    public static final String TYPE_JUNCTION_VIEW = "JUNCTION_VIEW";
    public static final String TYPE_FLOOR_TRANSITION = "FLOOR_TRANSITION";
    public static final String TYPE_DESTINATION_VIEW = "DESTINATION_VIEW";

    private String id;
    private String buildingId;
    private String floor;
    private String title;
    private String description;
    private String drawableResourceName; // e.g., "visual_ref_06"
    private String nodeId; // Topological NavigationNode id (e.g., "node_gf_it_lab1")
    private String landmarkType;
    private boolean isVerified;

    public VisualLandmark() {
        this.buildingId = "bldg_comp_it";
        this.isVerified = true;
    }

    public VisualLandmark(String id, String buildingId, String floor, String title,
                          String description, String drawableResourceName, String nodeId,
                          String landmarkType, boolean isVerified) {
        this.id = id;
        this.buildingId = buildingId != null ? buildingId : "bldg_comp_it";
        this.floor = floor;
        this.title = title;
        this.description = description;
        this.drawableResourceName = drawableResourceName;
        this.nodeId = nodeId;
        this.landmarkType = landmarkType;
        this.isVerified = isVerified;
    }

    public static boolean isValidLandmarkType(String type) {
        if (type == null) return false;
        return TYPE_ROOM_ENTRANCE.equals(type) ||
                TYPE_CORRIDOR_VIEW.equals(type) ||
                TYPE_STAIRCASE_VIEW.equals(type) ||
                TYPE_STAIRCASE_LANDING.equals(type) ||
                TYPE_JUNCTION_VIEW.equals(type) ||
                TYPE_FLOOR_TRANSITION.equals(type) ||
                TYPE_DESTINATION_VIEW.equals(type);
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getBuildingId() { return buildingId; }
    public void setBuildingId(String buildingId) { this.buildingId = buildingId; }

    public String getFloor() { return floor; }
    public void setFloor(String floor) { this.floor = floor; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getDrawableResourceName() { return drawableResourceName; }
    public void setDrawableResourceName(String drawableResourceName) { this.drawableResourceName = drawableResourceName; }

    public String getNodeId() { return nodeId; }
    public void setNodeId(String nodeId) { this.nodeId = nodeId; }

    public String getLandmarkType() { return landmarkType; }
    public void setLandmarkType(String landmarkType) { this.landmarkType = landmarkType; }

    public boolean isVerified() { return isVerified; }
    public void setVerified(boolean verified) { isVerified = verified; }
}
