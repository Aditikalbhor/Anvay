package com.gpp.anvay.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SafetyInstruction implements Serializable {
    private String id;
    private String title;
    private String summary;
    private List<String> steps;
    private String assemblyPoint;
    private String iconType;

    public SafetyInstruction() {
        this.steps = new ArrayList<>();
    }

    public SafetyInstruction(String id, String title, String summary, List<String> steps, String assemblyPoint, String iconType) {
        this.id = id;
        this.title = title;
        this.summary = summary;
        this.steps = steps != null ? steps : new ArrayList<>();
        this.assemblyPoint = assemblyPoint;
        this.iconType = iconType;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getSummary() { return summary; }
    public void setSummary(String summary) { this.summary = summary; }

    public List<String> getSteps() { return steps; }
    public void setSteps(List<String> steps) { this.steps = steps; }

    public String getAssemblyPoint() { return assemblyPoint; }
    public void setAssemblyPoint(String assemblyPoint) { this.assemblyPoint = assemblyPoint; }

    public String getIconType() { return iconType; }
    public void setIconType(String iconType) { this.iconType = iconType; }
}
