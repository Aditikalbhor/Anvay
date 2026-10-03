package com.gpp.anvay.model.position;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a node within an indoor navigation graph structure (e.g. room doorway,
 * corridor waypoint, staircase landing, or emergency exit).
 * Coordinates and connected neighbor nodes are optional placeholders for future graph construction.
 */
public class NavigationNode implements Serializable {
    private String nodeId;
    private String buildingId;
    private String floor;
    private String linkedLocationId; // Optional reference to a LocationItem id
    private String nodeType; // e.g. "ROOM_ENTRY", "CORRIDOR_JUNCTION", "STAIRCASE", "EXIT", "CHECKPOINT"
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
