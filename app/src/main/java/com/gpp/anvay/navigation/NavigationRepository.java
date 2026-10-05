package com.gpp.anvay.navigation;

import com.gpp.anvay.data.LocationRepository;
import com.gpp.anvay.model.LocationItem;
import com.gpp.anvay.model.position.NavigationNode;
import com.gpp.anvay.model.position.NavigationRoute;

import java.util.ArrayList;
import java.util.List;

/**
 * Central repository managing the building navigation graph and route queries.
 */
public class NavigationRepository {

    private static NavigationRepository instance;
    private final NavigationGraph graph;
    private final PathFinder pathFinder;

    private NavigationRepository() {
        this.graph = new NavigationGraph();
        this.pathFinder = new PathFinder(this.graph);
    }

    public static synchronized NavigationRepository getInstance() {
        if (instance == null) {
            instance = new NavigationRepository();
        }
        return instance;
    }

    public NavigationGraph getGraph() {
        return graph;
    }

    public PathFinder getPathFinder() {
        return pathFinder;
    }

    public NavigationNode getNodeForLocation(String locationId) {
        if (locationId == null) return null;
        return graph.getNodeByLocationId(locationId);
    }

    public NavigationRoute findRouteByLocationIds(String startLocationId, String destinationLocationId) {
        if (startLocationId == null || destinationLocationId == null) {
            return NavigationRoute.unavailable("Please select both a starting location and a destination.");
        }

        NavigationNode startNode = getNodeForLocation(startLocationId);
        NavigationNode destNode = getNodeForLocation(destinationLocationId);

        if (startNode == null) {
            return NavigationRoute.unavailable("Start location is not in the navigation graph.");
        }
        if (destNode == null) {
            return NavigationRoute.unavailable("Destination location is not in the navigation graph.");
        }

        return pathFinder.findRoute(startNode, destNode);
    }

    public NavigationRoute findRoute(String startNodeId, String destNodeId) {
        return pathFinder.findRoute(startNodeId, destNodeId);
    }

    public List<LocationItem> getNavigableLocations() {
        return LocationRepository.getInstance().getAllLocations();
    }
}
