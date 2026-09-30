package com.gpp.anvay.model;

import java.io.Serializable;

public class TimetableSlot implements Serializable {
    private String id;
    private String dayOfWeek;      // Monday, Tuesday, Wednesday, Thursday, Friday, Saturday
    private String startTime;      // e.g. "09:00 AM"
    private String endTime;        // e.g. "10:00 AM"
    private String subjectName;    // e.g. "Operating Systems"
    private String subjectCode;    // e.g. "CO-501"
    private String roomCode;       // e.g. "C-101"
    private String roomName;       // e.g. "Classroom C-101"
    private String department;     // e.g. "Computer Engineering"
    private String targetClass;    // e.g. "TY Computer (CO-5I)"
    private String batch;          // e.g. "All", "Batch A1", "Batch A2"
    private String facultyName;    // e.g. "Prof. Anand Kulkarni"
    private String sessionType;    // Lecture, Practical / Lab, Tutorial, Seminar
    private String notes;          // e.g. "Bring lab journal"

    public TimetableSlot() {
    }

    public TimetableSlot(String id, String dayOfWeek, String startTime, String endTime,
                         String subjectName, String subjectCode, String roomCode, String roomName,
                         String department, String targetClass, String batch,
                         String facultyName, String sessionType, String notes) {
        this.id = id;
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
        this.subjectName = subjectName;
        this.subjectCode = subjectCode;
        this.roomCode = roomCode;
        this.roomName = roomName;
        this.department = department;
        this.targetClass = targetClass;
        this.batch = batch;
        this.facultyName = facultyName;
        this.sessionType = sessionType;
        this.notes = notes;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getDayOfWeek() { return dayOfWeek; }
    public void setDayOfWeek(String dayOfWeek) { this.dayOfWeek = dayOfWeek; }

    public String getStartTime() { return startTime; }
    public void setStartTime(String startTime) { this.startTime = startTime; }

    public String getEndTime() { return endTime; }
    public void setEndTime(String endTime) { this.endTime = endTime; }

    public String getSubjectName() { return subjectName; }
    public void setSubjectName(String subjectName) { this.subjectName = subjectName; }

    public String getSubjectCode() { return subjectCode; }
    public void setSubjectCode(String subjectCode) { this.subjectCode = subjectCode; }

    public String getRoomCode() { return roomCode; }
    public void setRoomCode(String roomCode) { this.roomCode = roomCode; }

    public String getRoomName() { return roomName; }
    public void setRoomName(String roomName) { this.roomName = roomName; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getTargetClass() { return targetClass; }
    public void setTargetClass(String targetClass) { this.targetClass = targetClass; }

    public String getBatch() { return batch; }
    public void setBatch(String batch) { this.batch = batch; }

    public String getFacultyName() { return facultyName; }
    public void setFacultyName(String facultyName) { this.facultyName = facultyName; }

    public String getSessionType() { return sessionType; }
    public void setSessionType(String sessionType) { this.sessionType = sessionType; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public String getTimeRange() {
        return startTime + " - " + endTime;
    }
}
