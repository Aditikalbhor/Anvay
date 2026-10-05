package com.gpp.anvay.navigation;

import com.gpp.anvay.model.position.NavigationRoute;
import com.gpp.anvay.model.position.NavigationStep;
import com.gpp.anvay.model.position.UserPosition;
import com.gpp.anvay.position.UserPositionManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Central orchestrator managing active indoor navigation sessions and step progression.
 */
public class NavigationManager {

    public interface NavigationListener {
        void onRouteCalculated(NavigationRoute route);
        void onStepChanged(int stepIndex, NavigationStep step);
        void onArrival(NavigationStep arrivalStep);
        void onNavigationCancelled();
    }

    private static NavigationManager instance;

    private final NavigationRepository repository;
    private final UserPositionManager positionManager;
    private final List<NavigationListener> listeners;

    private NavigationRoute currentRoute;
    private int currentStepIndex;
    private boolean isNavigating;
    private String destinationLocationId;
    private String startLocationId;

    private NavigationManager() {
        this.repository = NavigationRepository.getInstance();
        this.positionManager = UserPositionManager.getInstance();
        this.listeners = new ArrayList<>();
        this.currentStepIndex = 0;
        this.isNavigating = false;
    }

    public static synchronized NavigationManager getInstance() {
        if (instance == null) {
            instance = new NavigationManager();
        }
        return instance;
    }

    public void addListener(NavigationListener listener) {
        if (listener != null && !listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    public void removeListener(NavigationListener listener) {
        if (listener != null) {
            listeners.remove(listener);
        }
    }

    public void clearListeners() {
        listeners.clear();
    }

    public NavigationRoute startNavigation(String destLocId) {
        String startLocId = null;
        UserPosition currentPos = positionManager.getCurrentPosition();
        if (currentPos != null && currentPos.hasAnchor()) {
            startLocId = currentPos.getAnchorLocationId();
        }

        return startNavigation(startLocId, destLocId);
    }

    public NavigationRoute startNavigation(String startLocId, String destLocId) {
        this.startLocationId = startLocId;
        this.destinationLocationId = destLocId;
        this.currentStepIndex = 0;

        if (destLocId == null || destLocId.isEmpty()) {
            this.currentRoute = NavigationRoute.unavailable("Destination is required.");
            this.isNavigating = false;
            notifyRouteCalculated(currentRoute);
            return currentRoute;
        }

        // If no start location is selected, default to ground floor entrance lobby or first room
        if (startLocId == null || startLocId.isEmpty()) {
            startLocId = "loc_gf_lab1";
            this.startLocationId = startLocId;
        }

        this.currentRoute = repository.findRouteByLocationIds(startLocId, destLocId);
        this.isNavigating = currentRoute.isRouteAvailable();
        this.currentStepIndex = 0;

        notifyRouteCalculated(currentRoute);

        if (isNavigating && currentRoute.getTotalSteps() > 0) {
            notifyStepChanged(0, currentRoute.getStep(0));
        }

        return currentRoute;
    }

    public NavigationRoute recalculateRoute() {
        if (destinationLocationId == null) {
            return NavigationRoute.unavailable("No active destination.");
        }
        return startNavigation(startLocationId, destinationLocationId);
    }

    public boolean nextStep() {
        if (!isNavigating || currentRoute == null || !currentRoute.isRouteAvailable()) {
            return false;
        }

        if (currentStepIndex < currentRoute.getTotalSteps() - 1) {
            currentStepIndex++;
            NavigationStep step = currentRoute.getStep(currentStepIndex);
            notifyStepChanged(currentStepIndex, step);

            if (step != null && step.isArrival()) {
                notifyArrival(step);
            }
            return true;
        }
        return false;
    }

    public boolean previousStep() {
        if (!isNavigating || currentRoute == null || !currentRoute.isRouteAvailable()) {
            return false;
        }

        if (currentStepIndex > 0) {
            currentStepIndex--;
            NavigationStep step = currentRoute.getStep(currentStepIndex);
            notifyStepChanged(currentStepIndex, step);
            return true;
        }
        return false;
    }

    public void cancelNavigation() {
        isNavigating = false;
        currentRoute = null;
        currentStepIndex = 0;
        destinationLocationId = null;
        startLocationId = null;
        notifyNavigationCancelled();
    }

    public boolean isNavigating() {
        return isNavigating && currentRoute != null && currentRoute.isRouteAvailable();
    }

    public boolean hasArrived() {
        if (currentRoute == null || !currentRoute.isRouteAvailable()) return false;
        NavigationStep currentStep = getCurrentStep();
        return currentStep != null && currentStep.isArrival();
    }

    public NavigationRoute getCurrentRoute() {
        return currentRoute;
    }

    public int getCurrentStepIndex() {
        return currentStepIndex;
    }

    public NavigationStep getCurrentStep() {
        if (currentRoute != null && currentStepIndex >= 0 && currentStepIndex < currentRoute.getTotalSteps()) {
            return currentRoute.getStep(currentStepIndex);
        }
        return null;
    }

    public String getCurrentFloor() {
        NavigationStep step = getCurrentStep();
        if (step != null && step.getFloor() != null) {
            return step.getFloor();
        }
        if (currentRoute != null && currentRoute.getStartFloor() != null) {
            return currentRoute.getStartFloor();
        }
        return "Ground Floor";
    }

    public String getDestinationLocationId() {
        return destinationLocationId;
    }

    public String getStartLocationId() {
        return startLocationId;
    }

    private void notifyRouteCalculated(NavigationRoute route) {
        for (NavigationListener l : new ArrayList<>(listeners)) {
            l.onRouteCalculated(route);
        }
    }

    private void notifyStepChanged(int index, NavigationStep step) {
        for (NavigationListener l : new ArrayList<>(listeners)) {
            l.onStepChanged(index, step);
        }
    }

    private void notifyArrival(NavigationStep step) {
        for (NavigationListener l : new ArrayList<>(listeners)) {
            l.onArrival(step);
        }
    }

    private void notifyNavigationCancelled() {
        for (NavigationListener l : new ArrayList<>(listeners)) {
            l.onNavigationCancelled();
        }
    }
}
