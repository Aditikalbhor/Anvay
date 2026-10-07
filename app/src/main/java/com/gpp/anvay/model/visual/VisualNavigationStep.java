package com.gpp.anvay.model.visual;

import com.gpp.anvay.model.position.ARDirection;
import com.gpp.anvay.model.position.NavigationStep;

import java.io.Serializable;

/**
 * Represents a single visual navigation step combining topological navigation step
 * data with a verified real building photograph / landmark reference.
 */
public class VisualNavigationStep implements Serializable {

    private int stepIndex;
    private NavigationStep navigationStep;
    private VisualLandmark visualLandmark;
    private String instruction;
    private ARDirection direction;
    private String floor;
    private String fromNodeId;
    private String toNodeId;
    private boolean isFloorTransition;
    private boolean isDestination;
    private String transitionMessage;

    public VisualNavigationStep() {}

    public VisualNavigationStep(int stepIndex, NavigationStep navigationStep, VisualLandmark visualLandmark,
                                String instruction, ARDirection direction, String floor,
                                String fromNodeId, String toNodeId, boolean isFloorTransition,
                                boolean isDestination, String transitionMessage) {
        this.stepIndex = stepIndex;
        this.navigationStep = navigationStep;
        this.visualLandmark = visualLandmark;
        this.instruction = instruction;
        this.direction = direction != null ? direction : ARDirection.FORWARD;
        this.floor = floor;
        this.fromNodeId = fromNodeId;
        this.toNodeId = toNodeId;
        this.isFloorTransition = isFloorTransition;
        this.isDestination = isDestination;
        this.transitionMessage = transitionMessage;
    }

    public int getStepIndex() { return stepIndex; }
    public void setStepIndex(int stepIndex) { this.stepIndex = stepIndex; }

    public NavigationStep getNavigationStep() { return navigationStep; }
    public void setNavigationStep(NavigationStep navigationStep) { this.navigationStep = navigationStep; }

    public VisualLandmark getVisualLandmark() { return visualLandmark; }
    public void setVisualLandmark(VisualLandmark visualLandmark) { this.visualLandmark = visualLandmark; }

    public boolean hasVisualPhoto() {
        return visualLandmark != null && visualLandmark.getDrawableResourceName() != null &&
                !visualLandmark.getDrawableResourceName().isEmpty();
    }

    public String getInstruction() { return instruction; }
    public void setInstruction(String instruction) { this.instruction = instruction; }

    public ARDirection getDirection() { return direction; }
    public void setDirection(ARDirection direction) { this.direction = direction; }

    public String getFloor() { return floor; }
    public void setFloor(String floor) { this.floor = floor; }

    public String getFromNodeId() { return fromNodeId; }
    public void setFromNodeId(String fromNodeId) { this.fromNodeId = fromNodeId; }

    public String getToNodeId() { return toNodeId; }
    public void setToNodeId(String toNodeId) { this.toNodeId = toNodeId; }

    public boolean isFloorTransition() { return isFloorTransition; }
    public void setFloorTransition(boolean floorTransition) { isFloorTransition = floorTransition; }

    public boolean isDestination() { return isDestination; }
    public void setDestination(boolean destination) { isDestination = destination; }

    public String getTransitionMessage() { return transitionMessage; }
    public void setTransitionMessage(String transitionMessage) { this.transitionMessage = transitionMessage; }
}
