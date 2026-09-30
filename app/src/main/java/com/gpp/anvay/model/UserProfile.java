package com.gpp.anvay.model;

import java.io.Serializable;

public class UserProfile implements Serializable {
    private String name;
    private String role;           // Student, Faculty, Staff, Visitor, Administrator
    private String department;
    private String idNumber;
    private String email;
    private String emergencyPhone;
    private boolean pushNotifications;
    private boolean highContrast;
    private boolean offlineCache;

    public UserProfile() {
        this.name = "Aditi Kulkarni";
        this.role = "Student";
        this.department = "Computer Engineering";
        this.idNumber = "GPP/CO/2024/042";
        this.email = "aditi.kulkarni@gppune.ac.in";
        this.emergencyPhone = "+91 98221 54321";
        this.pushNotifications = true;
        this.highContrast = false;
        this.offlineCache = true;
    }

    public UserProfile(String name, String role, String department, String idNumber, String emergencyPhone) {
        this(name, role, department, idNumber, "user@gppune.ac.in", emergencyPhone);
    }

    public UserProfile(String name, String role, String department, String idNumber, String email, String emergencyPhone) {
        this.name = name;
        this.role = role;
        this.department = department;
        this.idNumber = idNumber;
        this.email = email;
        this.emergencyPhone = emergencyPhone;
        this.pushNotifications = true;
        this.highContrast = false;
        this.offlineCache = true;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getIdNumber() { return idNumber; }
    public void setIdNumber(String idNumber) { this.idNumber = idNumber; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getEmergencyPhone() { return emergencyPhone; }
    public void setEmergencyPhone(String emergencyPhone) { this.emergencyPhone = emergencyPhone; }

    public boolean isPushNotifications() { return pushNotifications; }
    public void setPushNotifications(boolean pushNotifications) { this.pushNotifications = pushNotifications; }

    public boolean isHighContrast() { return highContrast; }
    public void setHighContrast(boolean highContrast) { this.highContrast = highContrast; }

    public boolean isOfflineCache() { return offlineCache; }
    public void setOfflineCache(boolean offlineCache) { this.offlineCache = offlineCache; }
}
