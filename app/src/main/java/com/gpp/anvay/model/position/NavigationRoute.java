package com.gpp.anvay.model.position;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Encapsulates a calculated indoor navigation route between two nodes.
 */
public class NavigationRoute implements Serializable {

    private NavigationNode startNode;
    private NavigationNode destinationNode;
    private List<NavigationNode> nodes;
    private List<NavigationStep> steps;
    private List<String> floorsTraversed;
    private boolean routeAvailable;
    private String errorMessage;

    public NavigationRoute() {
        this.nodes = new ArrayList<>();
        this.steps = new ArrayList<>();
        this.floorsTraversed = new ArrayList<>();
        this.routeAvailable = false;
    }

    public NavigationRoute(NavigationNode startNode, NavigationNode destinationNode,
                           List<NavigationNode> nodes, List<NavigationStep> steps,
                           List<String> floorsTraversed) {
        this.startNode = startNode;
        this.destinationNode = destinationNode;
        this.nodes = nodes != null ? nodes : new ArrayList<>();
        this.steps = steps != null ? steps : new ArrayList<>();
        this.floorsTraversed = floorsTraversed != null ? floorsTraversed : new ArrayList<>();
        this.routeAvailable = true;
        this.errorMessage = null;
    }

    public static NavigationRoute unavailable(String message) {
        NavigationRoute route = new NavigationRoute();
        route.routeAvailable = false;
        route.errorMessage = message != null ? message : "Route unavailable";
        return route;
    }

    public NavigationNode getStartNode() {
        return startNode;
    }

    public void setStartNode(NavigationNode startNode) {
        this.startNode = startNode;
    }

    public NavigationNode getDestinationNode() {
        return destinationNode;
    }

    public void setDestinationNode(NavigationNode destinationNode) {
        this.destinationNode = destinationNode;
    }

    public List<NavigationNode> getNodes() {
        return nodes != null ? nodes : new ArrayList<>();
    }

    public void setNodes(List<NavigationNode> nodes) {
        this.nodes = nodes != null ? nodes : new ArrayList<>();
    }

    public List<NavigationStep> getSteps() {
        return steps != null ? steps : new ArrayList<>();
    }

    public void setSteps(List<NavigationStep> steps) {
        this.steps = steps != null ? steps : new ArrayList<>();
    }

    public List<String> getFloorsTraversed() {
        return floorsTraversed != null ? floorsTraversed : new ArrayList<>();
    }

    public void setFloorsTraversed(List<String> floorsTraversed) {
        this.floorsTraversed = floorsTraversed != null ? floorsTraversed : new ArrayList<>();
    }

    public boolean isRouteAvailable() {
        return routeAvailable && nodes != null && !nodes.isEmpty();
    }

    public void setRouteAvailable(boolean routeAvailable) {
        this.routeAvailable = routeAvailable;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public int getTotalSteps() {
        return steps != null ? steps.size() : 0;
    }

    public NavigationStep getStep(int index) {
        if (steps != null && index >= 0 && index < steps.size()) {
            return steps.get(index);
        }
        return null;
    }

    public String getStartFloor() {
        return startNode != null ? startNode.getFloor() : "";
    }

    public String getDestinationFloor() {
        return destinationNode != null ? destinationNode.getFloor() : "";
    }

    public boolean isMultiFloor() {
        return floorsTraversed != null && floorsTraversed.size() > 1;
    }

    public List<String> getInstructions() {
        List<String> list = new ArrayList<>();
        if (steps != null) {
            for (NavigationStep step : steps) {
                list.add(step.getInstruction());
            }
        }
        return list;
    }
}
