package com.gpp.anvay.model.position;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a topological node within the indoor navigation graph.
 *
 * Supported node types in the ANVAY navigation topology:
 * 1. ROOM_ENTRY - Entrance doorway of a classroom, lab, office, or facility.
 * 2. CORRIDOR_JUNCTION - Waypoint/intersection along corridor paths.
 * 3. STAIRCASE - Staircase transition landing between supported floors.
 * 4. CHECKPOINT - Physical positioning anchor / marker location.
 *
 * Note: Emergency exits are not part of the navigation graph topology.
 */
public class NavigationNode implements Serializable {

    public static final String TYPE_ROOM_ENTRY = "ROOM_ENTRY";
    public static final String TYPE_CORRIDOR_JUNCTION = "CORRIDOR_JUNCTION";
    public static final String TYPE_STAIRCASE = "STAIRCASE";
    public static final String TYPE_CHECKPOINT = "CHECKPOINT";

    private String nodeId;
    private String buildingId;
    private String floor;
    private String linkedLocationId; // Optional reference to a LocationItem id
    private String nodeType; // TYPE_ROOM_ENTRY, TYPE_CORRIDOR_JUNCTION, TYPE_STAIRCASE, or TYPE_CHECKPOINT
    private Double x; // Optional spatial coordinate placeholder (nullable)
    private Double y; // Optional spatial coordinate placeholder (nullable)
    private List<String> connectedNodeIds; // Adjacency list for graph pathfinding

    public NavigationNode() {
        this.connectedNodeIds = new ArrayList<>();
    }

    public NavigationNode(String nodeId, String buildingId, String floor, String linkedLocationId, String nodeType) {
        this(nodeId, buildingId, floor, linkedLocationId, nodeType, null, null, new ArrayList<>());
    }

    public NavigationNode(String nodeId, String buildingId, String floor, String linkedLocationId,
                          String nodeType, Double x, Double y, List<String> connectedNodeIds) {
        this.nodeId = nodeId;
        this.buildingId = buildingId;
        this.floor = floor;
        this.linkedLocationId = linkedLocationId;
        this.nodeType = nodeType;
        this.x = x;
        this.y = y;
        this.connectedNodeIds = connectedNodeIds != null ? connectedNodeIds : new ArrayList<>();
    }

    public static boolean isValidNodeType(String type) {
        if (type == null) return false;
        return TYPE_ROOM_ENTRY.equals(type) ||
                TYPE_CORRIDOR_JUNCTION.equals(type) ||
                TYPE_STAIRCASE.equals(type) ||
                TYPE_CHECKPOINT.equals(type);
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
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

    public String getLinkedLocationId() {
        return linkedLocationId;
    }

    public void setLinkedLocationId(String linkedLocationId) {
        this.linkedLocationId = linkedLocationId;
    }

    public String getNodeType() {
        return nodeType;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
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

    public List<String> getConnectedNodeIds() {
        return connectedNodeIds != null ? connectedNodeIds : new ArrayList<>();
    }

    public void setConnectedNodeIds(List<String> connectedNodeIds) {
        this.connectedNodeIds = connectedNodeIds != null ? connectedNodeIds : new ArrayList<>();
    }

    public void addConnectedNode(String neighborNodeId) {
        if (neighborNodeId != null && !neighborNodeId.isEmpty()) {
            if (this.connectedNodeIds == null) {
                this.connectedNodeIds = new ArrayList<>();
            }
            if (!this.connectedNodeIds.contains(neighborNodeId)) {
                this.connectedNodeIds.add(neighborNodeId);
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NavigationNode that = (NavigationNode) o;
        return Objects.equals(nodeId, that.nodeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nodeId);
    }

    @Override
    public String toString() {
        return "NavigationNode{" +
                "nodeId='" + nodeId + '\'' +
                ", buildingId='" + buildingId + '\'' +
                ", floor='" + floor + '\'' +
                ", linkedLocationId='" + linkedLocationId + '\'' +
                ", nodeType='" + nodeType + '\'' +
                '}';
    }
}
