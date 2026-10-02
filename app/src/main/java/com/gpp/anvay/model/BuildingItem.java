package com.gpp.anvay.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BuildingItem implements Serializable {
    private String id;
    private String name;
    private String code;
    private String description;
    private List<String> supportedFloors;
    private boolean isDefault;

    public BuildingItem() {
        this.supportedFloors = new ArrayList<>();
        this.isDefault = false;
    }

    public BuildingItem(String id, String name, String code, String description,
                        List<String> supportedFloors, boolean isDefault) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.description = description;
        this.supportedFloors = supportedFloors != null ? supportedFloors : new ArrayList<>();
        this.isDefault = isDefault;
    }

    public BuildingItem(String id, String name, List<String> supportedFloors) {
        this(id, name, id, "", supportedFloors, false);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getSupportedFloors() {
        return supportedFloors != null ? supportedFloors : new ArrayList<>();
    }

    public void setSupportedFloors(List<String> supportedFloors) {
        this.supportedFloors = supportedFloors != null ? supportedFloors : new ArrayList<>();
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BuildingItem that = (BuildingItem) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return name != null ? name : (id != null ? id : "");
    }
}
