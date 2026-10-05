package com.gpp.anvay.model.position;

import java.io.Serializable;

/**
 * Represents an individual topological navigation instruction within a NavigationRoute.
 */
public class NavigationStep implements Serializable {

    public static final String STEP_MOVE = "MOVE";
    public static final String STEP_TURN = "TURN";
    public static final String STEP_STAIRCASE = "STAIRCASE";
    public static final String STEP_ARRIVE = "ARRIVE";

    private int stepNumber;
    private String floor;
    private NavigationNode node;
    private String instruction;
    private String stepType;
    private ARDirection arDirection;
    private String targetFloor;
    private String staircaseName;

    public NavigationStep() {
    }

    public NavigationStep(int stepNumber, String floor, NavigationNode node,
                          String instruction, String stepType, ARDirection arDirection) {
        this(stepNumber, floor, node, instruction, stepType, arDirection, null, null);
    }

    public NavigationStep(int stepNumber, String floor, NavigationNode node,
                          String instruction, String stepType, ARDirection arDirection,
                          String targetFloor, String staircaseName) {
        this.stepNumber = stepNumber;
        this.floor = floor;
        this.node = node;
        this.instruction = instruction;
        this.stepType = stepType;
        this.arDirection = arDirection;
        this.targetFloor = targetFloor;
        this.staircaseName = staircaseName;
    }

    public int getStepNumber() {
        return stepNumber;
    }

    public void setStepNumber(int stepNumber) {
        this.stepNumber = stepNumber;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public NavigationNode getNode() {
        return node;
    }

    public void setNode(NavigationNode node) {
        this.node = node;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public String getStepType() {
        return stepType;
    }

    public void setStepType(String stepType) {
        this.stepType = stepType;
    }

    public ARDirection getArDirection() {
        return arDirection;
    }

    public void setArDirection(ARDirection arDirection) {
        this.arDirection = arDirection;
    }

    public String getTargetFloor() {
        return targetFloor;
    }

    public void setTargetFloor(String targetFloor) {
        this.targetFloor = targetFloor;
    }

    public String getStaircaseName() {
        return staircaseName;
    }

    public void setStaircaseName(String staircaseName) {
        this.staircaseName = staircaseName;
    }

    public boolean isStaircaseTransition() {
        return STEP_STAIRCASE.equals(stepType) || (targetFloor != null && !targetFloor.isEmpty());
    }

    public boolean isArrival() {
        return STEP_ARRIVE.equals(stepType);
    }

    @Override
    public String toString() {
        return "NavigationStep{" +
                "stepNumber=" + stepNumber +
                ", floor='" + floor + '\'' +
                ", stepType='" + stepType + '\'' +
                ", instruction='" + instruction + '\'' +
                '}';
    }
}
