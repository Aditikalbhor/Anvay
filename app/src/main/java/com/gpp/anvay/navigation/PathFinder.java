package com.gpp.anvay.navigation;

import com.gpp.anvay.data.LocationRepository;
import com.gpp.anvay.model.LocationItem;
import com.gpp.anvay.model.position.ARDirection;
import com.gpp.anvay.model.position.NavigationNode;
import com.gpp.anvay.model.position.NavigationRoute;
import com.gpp.anvay.model.position.NavigationStep;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 * Breadth-First Search (BFS) pathfinder for topological indoor navigation.
 */
public class PathFinder {

    private final NavigationGraph graph;

    public PathFinder(NavigationGraph graph) {
        this.graph = graph != null ? graph : new NavigationGraph();
    }

    public NavigationRoute findRoute(String startNodeId, String destinationNodeId) {
        if (startNodeId == null || destinationNodeId == null) {
            return NavigationRoute.unavailable("Invalid start or destination location.");
        }

        NavigationNode startNode = graph.getNode(startNodeId);
        NavigationNode destNode = graph.getNode(destinationNodeId);

        if (startNode == null || destNode == null) {
            return NavigationRoute.unavailable("Navigation nodes could not be located in the building graph.");
        }

        return findRoute(startNode, destNode);
    }

    public NavigationRoute findRoute(NavigationNode startNode, NavigationNode destinationNode) {
        if (startNode == null || destinationNode == null) {
            return NavigationRoute.unavailable("Start or destination is not specified.");
        }

        if (startNode.getNodeId().equals(destinationNode.getNodeId())) {
            List<NavigationNode> singleNodeList = Collections.singletonList(startNode);
            List<String> floors = Collections.singletonList(startNode.getFloor());
            List<NavigationStep> steps = new ArrayList<>();

            String locName = getLocationName(destinationNode);
            steps.add(new NavigationStep(
                    1,
                    startNode.getFloor(),
                    startNode,
                    "You are already at your destination: " + locName + ".",
                    NavigationStep.STEP_ARRIVE,
                    ARDirection.ARRIVAL
            ));

            return new NavigationRoute(startNode, destinationNode, singleNodeList, steps, floors);
        }

        // BFS traversal
        Queue<String> queue = new ArrayDeque<>();
        Map<String, String> parentMap = new HashMap<>();
        Set<String> visited = new HashSet<>();

        queue.add(startNode.getNodeId());
        visited.add(startNode.getNodeId());

        boolean found = false;
        while (!queue.isEmpty()) {
            String currentId = queue.poll();
            if (currentId.equals(destinationNode.getNodeId())) {
                found = true;
                break;
            }

            for (String neighborId : graph.getNeighbors(currentId)) {
                if (!visited.contains(neighborId)) {
                    visited.add(neighborId);
                    parentMap.put(neighborId, currentId);
                    queue.add(neighborId);
                }
            }
        }

        if (!found) {
            return NavigationRoute.unavailable("No connected pathway found between selected locations.");
        }

        // Reconstruct path
        List<NavigationNode> path = new ArrayList<>();
        String curr = destinationNode.getNodeId();
        while (curr != null) {
            NavigationNode node = graph.getNode(curr);
            if (node != null) {
                path.add(0, node);
            }
            curr = parentMap.get(curr);
        }

        // Generate human-readable navigation steps and track traversed floors
        List<NavigationStep> steps = generateSteps(path, startNode, destinationNode);
        List<String> floorsTraversed = extractFloorsTraversed(path);

        return new NavigationRoute(startNode, destinationNode, path, steps, floorsTraversed);
    }

    private List<NavigationStep> generateSteps(List<NavigationNode> path, NavigationNode startNode, NavigationNode destinationNode) {
        List<NavigationStep> steps = new ArrayList<>();
        if (path == null || path.isEmpty()) return steps;

        int stepNum = 1;
        String startLocName = getLocationName(startNode);
        String destLocName = getLocationName(destinationNode);

        // Step 1: Start
        steps.add(new NavigationStep(
                stepNum++,
                path.get(0).getFloor(),
                path.get(0),
                "Start from " + startLocName + " on " + path.get(0).getFloor() + ".",
                NavigationStep.STEP_MOVE,
                ARDirection.FORWARD
        ));

        for (int i = 1; i < path.size(); i++) {
            NavigationNode prevNode = path.get(i - 1);
            NavigationNode currNode = path.get(i);

            // Check if this step is a staircase floor transition
            if (NavigationNode.TYPE_STAIRCASE.equals(prevNode.getNodeType()) &&
                    NavigationNode.TYPE_STAIRCASE.equals(currNode.getNodeType()) &&
                    !prevNode.getFloor().equalsIgnoreCase(currNode.getFloor())) {

                String staircaseName = getStaircaseDisplayName(currNode);
                steps.add(new NavigationStep(
                        stepNum++,
                        currNode.getFloor(),
                        currNode,
                        "Take " + staircaseName + " to the " + currNode.getFloor() + ".",
                        NavigationStep.STEP_STAIRCASE,
                        ARDirection.STAIRCASE,
                        currNode.getFloor(),
                        staircaseName
                ));
            } else if (i == path.size() - 1) {
                // Final Arrival Step
                steps.add(new NavigationStep(
                        stepNum++,
                        currNode.getFloor(),
                        currNode,
                        "You have arrived at " + destLocName + ".",
                        NavigationStep.STEP_ARRIVE,
                        ARDirection.ARRIVAL
                ));
            } else if (NavigationNode.TYPE_CORRIDOR_JUNCTION.equals(currNode.getNodeType())) {
                // Corridor progression
                steps.add(new NavigationStep(
                        stepNum++,
                        currNode.getFloor(),
                        currNode,
                        "Proceed through the corridor on the " + currNode.getFloor() + ".",
                        NavigationStep.STEP_MOVE,
                        ARDirection.FORWARD
                ));
            } else if (NavigationNode.TYPE_STAIRCASE.equals(currNode.getNodeType()) &&
                    !NavigationNode.TYPE_STAIRCASE.equals(prevNode.getNodeType())) {
                // Approaching a staircase
                String staircaseName = getStaircaseDisplayName(currNode);
                steps.add(new NavigationStep(
                        stepNum++,
                        currNode.getFloor(),
                        currNode,
                        "Head toward " + staircaseName + ".",
                        NavigationStep.STEP_MOVE,
                        ARDirection.FORWARD
                ));
            } else {
                // General waypoint / room transition
                String roomName = getLocationName(currNode);
                steps.add(new NavigationStep(
                        stepNum++,
                        currNode.getFloor(),
                        currNode,
                        "Continue toward " + roomName + " on " + currNode.getFloor() + ".",
                        NavigationStep.STEP_MOVE,
                        ARDirection.FORWARD
                ));
            }
        }

        return steps;
    }

    private List<String> extractFloorsTraversed(List<NavigationNode> path) {
        List<String> floors = new ArrayList<>();
        if (path == null) return floors;
        for (NavigationNode node : path) {
            if (node.getFloor() != null && !floors.contains(node.getFloor())) {
                floors.add(node.getFloor());
            }
        }
        return floors;
    }

    private String getLocationName(NavigationNode node) {
        if (node == null) return "Location";
        if (node.getLinkedLocationId() != null) {
            LocationItem item = LocationRepository.getInstance().getLocationById(node.getLinkedLocationId());
            if (item != null && item.getName() != null) {
                return item.getName();
            }
        }
        if (NavigationNode.TYPE_CORRIDOR_JUNCTION.equals(node.getNodeType())) {
            return "Corridor Junction";
        }
        if (NavigationNode.TYPE_STAIRCASE.equals(node.getNodeType())) {
            return getStaircaseDisplayName(node);
        }
        return node.getNodeId();
    }

    private String getStaircaseDisplayName(NavigationNode node) {
        if (node == null) return "Staircase";
        String id = node.getNodeId();
        if (id.contains("s1") || id.contains("S1")) return "Staircase S1";
        if (id.contains("s2") || id.contains("S2")) return "Staircase S2";
        if (id.contains("s3") || id.contains("S3")) return "Staircase S3";
        return "Staircase";
    }
}
