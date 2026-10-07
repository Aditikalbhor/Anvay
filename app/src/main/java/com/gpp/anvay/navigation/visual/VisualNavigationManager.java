package com.gpp.anvay.navigation.visual;

import com.gpp.anvay.data.visual.VisualNavigationRepository;
import com.gpp.anvay.model.position.ARDirection;
import com.gpp.anvay.model.position.NavigationNode;
import com.gpp.anvay.model.position.NavigationRoute;
import com.gpp.anvay.model.position.NavigationStep;
import com.gpp.anvay.model.visual.VisualLandmark;
import com.gpp.anvay.model.visual.VisualNavigationStep;

import java.util.ArrayList;
import java.util.List;

/**
 * Visual Navigation Manager that consumes a NavigationRoute and resolves each
 * step to verified photo-grounded landmarks in the Computer/IT Building.
 *
 * It does NOT calculate routes or mutate the NavigationGraph.
 */
public class VisualNavigationManager {

    public interface VisualNavigationListener {
        void onVisualRouteLoaded(List<VisualNavigationStep> steps);
        void onVisualStepChanged(int stepIndex, VisualNavigationStep step);
        void onVisualArrival(VisualNavigationStep arrivalStep);
    }

    private static VisualNavigationManager instance;

    private final VisualNavigationRepository visualRepo;
    private final List<VisualNavigationListener> listeners;

    private NavigationRoute currentRoute;
    private List<VisualNavigationStep> visualSteps;
    private int currentStepIndex;

    private VisualNavigationManager() {
        this.visualRepo = VisualNavigationRepository.getInstance();
        this.listeners = new ArrayList<>();
        this.visualSteps = new ArrayList<>();
        this.currentStepIndex = 0;
    }

    public static synchronized VisualNavigationManager getInstance() {
        if (instance == null) {
            instance = new VisualNavigationManager();
        }
        return instance;
    }

    public void addListener(VisualNavigationListener listener) {
        if (listener != null && !listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    public void removeListener(VisualNavigationListener listener) {
        if (listener != null) {
            listeners.remove(listener);
        }
    }

    public void clearListeners() {
        listeners.clear();
    }

    /**
     * Translates a NavigationRoute into an ordered list of photo-grounded VisualNavigationSteps.
     */
    public List<VisualNavigationStep> loadRoute(NavigationRoute route) {
        this.currentRoute = route;
        this.visualSteps = new ArrayList<>();
        this.currentStepIndex = 0;

        if (route == null || !route.isRouteAvailable() || route.getSteps().isEmpty()) {
            notifyRouteLoaded(visualSteps);
            return visualSteps;
        }

        List<NavigationStep> navSteps = route.getSteps();
        List<NavigationNode> nodes = route.getNodes();

        for (int i = 0; i < navSteps.size(); i++) {
            NavigationStep navStep = navSteps.get(i);
            NavigationNode currNode = navStep.getNode();
            NavigationNode prevNode = (i > 0 && i < nodes.size()) ? nodes.get(i - 1) : null;
            NavigationNode nextNode = (i + 1 < nodes.size()) ? nodes.get(i + 1) : null;

            String fromNodeId = prevNode != null ? prevNode.getNodeId() : (currNode != null ? currNode.getNodeId() : null);
            String toNodeId = currNode != null ? currNode.getNodeId() : null;

            boolean isStaircaseTransition = navStep.isStaircaseTransition();
            boolean isArrival = navStep.isArrival() || (i == navSteps.size() - 1);

            // 1. Resolve Landmark: Check transition landmark first if staircase transition, otherwise node landmark
            VisualLandmark landmark = null;
            if (isStaircaseTransition && fromNodeId != null && toNodeId != null) {
                landmark = visualRepo.getLandmarkForTransition(fromNodeId, toNodeId);
            }
            if (landmark == null && toNodeId != null) {
                landmark = visualRepo.getLandmarkForNode(toNodeId);
            }

            // 2. Resolve ARDirection
            ARDirection arDirection = navStep.getArDirection();
            if (arDirection == null) {
                if (isArrival) {
                    arDirection = ARDirection.ARRIVAL;
                } else if (isStaircaseTransition) {
                    arDirection = ARDirection.STAIRCASE;
                } else {
                    arDirection = ARDirection.FORWARD;
                }
            }

            // 3. Build transition message
            String transitionMessage = null;
            if (isStaircaseTransition) {
                String stairName = navStep.getStaircaseName() != null ? navStep.getStaircaseName() : "Staircase";
                String targetFloor = navStep.getTargetFloor() != null ? navStep.getTargetFloor() : navStep.getFloor();
                transitionMessage = "Take " + stairName + " to " + targetFloor;
            } else if (isArrival) {
                transitionMessage = "Destination Reached";
            }

            VisualNavigationStep visualStep = new VisualNavigationStep(
                    i,
                    navStep,
                    landmark,
                    navStep.getInstruction(),
                    arDirection,
                    navStep.getFloor(),
                    fromNodeId,
                    toNodeId,
                    isStaircaseTransition,
                    isArrival,
                    transitionMessage
            );

            visualSteps.add(visualStep);
        }

        notifyRouteLoaded(visualSteps);
        if (!visualSteps.isEmpty()) {
            notifyStepChanged(0, visualSteps.get(0));
        }

        return visualSteps;
    }

    public boolean moveToNextStep() {
        if (visualSteps == null || visualSteps.isEmpty()) return false;
        if (currentStepIndex < visualSteps.size() - 1) {
            currentStepIndex++;
            VisualNavigationStep step = visualSteps.get(currentStepIndex);
            notifyStepChanged(currentStepIndex, step);
            if (step.isDestination()) {
                notifyArrival(step);
            }
            return true;
        }
        return false;
    }

    public boolean moveToPreviousStep() {
        if (visualSteps == null || visualSteps.isEmpty()) return false;
        if (currentStepIndex > 0) {
            currentStepIndex--;
            VisualNavigationStep step = visualSteps.get(currentStepIndex);
            notifyStepChanged(currentStepIndex, step);
            return true;
        }
        return false;
    }

    public boolean jumpToStep(int stepIndex) {
        if (visualSteps == null || visualSteps.isEmpty()) return false;
        if (stepIndex >= 0 && stepIndex < visualSteps.size()) {
            currentStepIndex = stepIndex;
            VisualNavigationStep step = visualSteps.get(currentStepIndex);
            notifyStepChanged(currentStepIndex, step);
            if (step.isDestination()) {
                notifyArrival(step);
            }
            return true;
        }
        return false;
    }

    public void restart() {
        if (visualSteps != null && !visualSteps.isEmpty()) {
            currentStepIndex = 0;
            notifyStepChanged(0, visualSteps.get(0));
        }
    }

    public int getCurrentStepIndex() {
        return currentStepIndex;
    }

    public int getTotalSteps() {
        return visualSteps != null ? visualSteps.size() : 0;
    }

    public VisualNavigationStep getCurrentVisualStep() {
        if (visualSteps != null && currentStepIndex >= 0 && currentStepIndex < visualSteps.size()) {
            return visualSteps.get(currentStepIndex);
        }
        return null;
    }

    public VisualNavigationStep getNextVisualStep() {
        if (visualSteps != null && currentStepIndex + 1 < visualSteps.size()) {
            return visualSteps.get(currentStepIndex + 1);
        }
        return null;
    }

    public VisualNavigationStep getPreviousVisualStep() {
        if (visualSteps != null && currentStepIndex - 1 >= 0) {
            return visualSteps.get(currentStepIndex - 1);
        }
        return null;
    }

    public int getProgressPercentage() {
        if (visualSteps == null || visualSteps.isEmpty()) return 0;
        if (visualSteps.size() == 1) return 100;
        return (int) (((float) currentStepIndex / (visualSteps.size() - 1)) * 100);
    }

    public boolean isFinished() {
        VisualNavigationStep curr = getCurrentVisualStep();
        return curr != null && curr.isDestination();
    }

    public NavigationRoute getCurrentRoute() {
        return currentRoute;
    }

    public List<VisualNavigationStep> getVisualSteps() {
        return new ArrayList<>(visualSteps);
    }

    private void notifyRouteLoaded(List<VisualNavigationStep> steps) {
        for (VisualNavigationListener l : new ArrayList<>(listeners)) {
            l.onVisualRouteLoaded(steps);
        }
    }

    private void notifyStepChanged(int index, VisualNavigationStep step) {
        for (VisualNavigationListener l : new ArrayList<>(listeners)) {
            l.onVisualStepChanged(index, step);
        }
    }

    private void notifyArrival(VisualNavigationStep step) {
        for (VisualNavigationListener l : new ArrayList<>(listeners)) {
            l.onVisualArrival(step);
        }
    }
}
