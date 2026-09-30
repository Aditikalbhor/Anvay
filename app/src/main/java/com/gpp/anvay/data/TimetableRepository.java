package com.gpp.anvay.data;

import android.content.Context;
import android.content.SharedPreferences;

import com.gpp.anvay.model.TimetableSlot;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TimetableRepository {
    private static final String PREF_NAME = "anvay_timetable_prefs";
    private static final String KEY_SLOTS_JSON = "timetable_slots_json";

    private static TimetableRepository instance;
    private final List<TimetableSlot> slots = new ArrayList<>();
    private final SharedPreferences prefs;

    public static final List<String> DAYS_OF_WEEK = Arrays.asList(
            "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"
    );

    public static final List<String> CLASSES_LIST = Arrays.asList(
            "All Classes",
            "TY Computer (CO-5I)",
            "SY Computer (CO-3I)",
            "FY Computer (CO-1I)",
            "TY IT (IF-5I)",
            "SY IT (IF-3I)",
            "FY IT (IF-1I)"
    );

    public static final List<String> SESSION_TYPES = Arrays.asList(
            "Lecture", "Practical / Lab", "Tutorial", "Seminar"
    );

    private TimetableRepository(Context context) {
        prefs = context.getApplicationContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        loadSlots();
    }

    public static synchronized TimetableRepository getInstance(Context context) {
        if (instance == null) {
            instance = new TimetableRepository(context);
        }
        return instance;
    }

    private void loadSlots() {
        slots.clear();
        String jsonStr = prefs.getString(KEY_SLOTS_JSON, null);
        if (jsonStr != null && !jsonStr.trim().isEmpty()) {
            try {
                JSONArray array = new JSONArray(jsonStr);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject obj = array.getJSONObject(i);
                    TimetableSlot slot = new TimetableSlot(
                            obj.optString("id"),
                            obj.optString("dayOfWeek"),
                            obj.optString("startTime"),
                            obj.optString("endTime"),
                            obj.optString("subjectName"),
                            obj.optString("subjectCode"),
                            obj.optString("roomCode"),
                            obj.optString("roomName"),
                            obj.optString("department"),
                            obj.optString("targetClass"),
                            obj.optString("batch"),
                            obj.optString("facultyName"),
                            obj.optString("sessionType"),
                            obj.optString("notes")
                    );
                    slots.add(slot);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (slots.isEmpty()) {
            slots.addAll(createInitialSlots());
            saveSlots();
        }
    }

    public synchronized void saveSlots() {
        try {
            JSONArray array = new JSONArray();
            for (TimetableSlot slot : slots) {
                JSONObject obj = new JSONObject();
                obj.put("id", slot.getId());
                obj.put("dayOfWeek", slot.getDayOfWeek());
                obj.put("startTime", slot.getStartTime());
                obj.put("endTime", slot.getEndTime());
                obj.put("subjectName", slot.getSubjectName());
                obj.put("subjectCode", slot.getSubjectCode());
                obj.put("roomCode", slot.getRoomCode());
                obj.put("roomName", slot.getRoomName());
                obj.put("department", slot.getDepartment());
                obj.put("targetClass", slot.getTargetClass());
                obj.put("batch", slot.getBatch());
                obj.put("facultyName", slot.getFacultyName());
                obj.put("sessionType", slot.getSessionType());
                obj.put("notes", slot.getNotes());
                array.put(obj);
            }
            prefs.edit().putString(KEY_SLOTS_JSON, array.toString()).apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized List<TimetableSlot> getAllSlots() {
        return new ArrayList<>(slots);
    }

    public synchronized List<TimetableSlot> getSlotsByDay(String day) {
        List<TimetableSlot> result = new ArrayList<>();
        for (TimetableSlot slot : slots) {
            if (day == null || day.equalsIgnoreCase("All") || slot.getDayOfWeek().equalsIgnoreCase(day)) {
                result.add(slot);
            }
        }
        return result;
    }

    public synchronized List<TimetableSlot> getSlotsFiltered(String day, String targetClass, String department, String searchQuery) {
        List<TimetableSlot> result = new ArrayList<>();
        String query = searchQuery != null ? searchQuery.trim().toLowerCase() : "";

        for (TimetableSlot slot : slots) {
            boolean matchesDay = (day == null || day.equalsIgnoreCase("All") || slot.getDayOfWeek().equalsIgnoreCase(day));
            boolean matchesClass = (targetClass == null || targetClass.equalsIgnoreCase("All Classes") || targetClass.equalsIgnoreCase("All") || slot.getTargetClass().equalsIgnoreCase(targetClass));
            boolean matchesDept = (department == null || department.equalsIgnoreCase("All Departments") || department.equalsIgnoreCase("All") || slot.getDepartment().equalsIgnoreCase(department));

            boolean matchesQuery = query.isEmpty() ||
                    slot.getSubjectName().toLowerCase().contains(query) ||
                    slot.getSubjectCode().toLowerCase().contains(query) ||
                    slot.getRoomCode().toLowerCase().contains(query) ||
                    slot.getFacultyName().toLowerCase().contains(query);

            if (matchesDay && matchesClass && matchesDept && matchesQuery) {
                result.add(slot);
            }
        }
        return result;
    }

    public synchronized List<TimetableSlot> getSlotsByRoom(String roomCode) {
        List<TimetableSlot> result = new ArrayList<>();
        if (roomCode == null) return result;
        String cleanRoom = roomCode.trim().toLowerCase();

        for (TimetableSlot slot : slots) {
            if (slot.getRoomCode() != null && slot.getRoomCode().toLowerCase().contains(cleanRoom)) {
                result.add(slot);
            }
        }
        return result;
    }

    public synchronized List<TimetableSlot> getSlotsByFaculty(String facultyName) {
        List<TimetableSlot> result = new ArrayList<>();
        if (facultyName == null) return result;
        String cleanName = facultyName.trim().toLowerCase();

        for (TimetableSlot slot : slots) {
            if (slot.getFacultyName() != null && slot.getFacultyName().toLowerCase().contains(cleanName)) {
                result.add(slot);
            }
        }
        return result;
    }

    public synchronized TimetableSlot getSlotById(String id) {
        if (id == null) return null;
        for (TimetableSlot slot : slots) {
            if (id.equals(slot.getId())) {
                return slot;
            }
        }
        return null;
    }

    public synchronized void addSlot(TimetableSlot slot) {
        if (slot == null) return;
        if (slot.getId() == null || slot.getId().isEmpty()) {
            slot.setId("slot_" + System.currentTimeMillis());
        }
        slots.add(slot);
        saveSlots();
    }

    public synchronized void updateSlot(TimetableSlot updatedSlot) {
        if (updatedSlot == null || updatedSlot.getId() == null) return;
        for (int i = 0; i < slots.size(); i++) {
            if (slots.get(i).getId().equals(updatedSlot.getId())) {
                slots.set(i, updatedSlot);
                saveSlots();
                return;
            }
        }
    }

    public synchronized boolean deleteSlot(String id) {
        if (id == null) return false;
        for (int i = 0; i < slots.size(); i++) {
            if (slots.get(i).getId().equals(id)) {
                slots.remove(i);
                saveSlots();
                return true;
            }
        }
        return false;
    }

    public synchronized void resetToDefaults() {
        slots.clear();
        slots.addAll(createInitialSlots());
        saveSlots();
    }

    private List<TimetableSlot> createInitialSlots() {
        List<TimetableSlot> list = new ArrayList<>();

        // --- MONDAY ---
        list.add(new TimetableSlot(
                "tt_m_01", "Monday", "09:00 AM", "10:00 AM",
                "Operating Systems", "CO-501", "C-101", "Classroom C-101",
                "Computer Engineering", "TY Computer (CO-5I)", "All",
                "Prof. Anand Kulkarni", "Lecture", "Process scheduling and memory management"
        ));
        list.add(new TimetableSlot(
                "tt_m_02", "Monday", "10:00 AM", "11:00 AM",
                "Database Management Systems", "CO-502", "C-101", "Classroom C-101",
                "Computer Engineering", "TY Computer (CO-5I)", "All",
                "Prof. S. N. Joshi", "Lecture", "Relational algebra & SQL optimization"
        ));
        list.add(new TimetableSlot(
                "tt_m_03", "Monday", "11:15 AM", "01:15 PM",
                "Advanced Java Programming Lab", "CO-503", "G-01", "Computer Center Lab 1",
                "Computer Engineering", "TY Computer (CO-5I)", "Batch A1",
                "Prof. Anand Kulkarni", "Practical / Lab", "JDBC connection pooling and Servlets"
        ));
        list.add(new TimetableSlot(
                "tt_m_04", "Monday", "11:15 AM", "01:15 PM",
                "Database Systems Lab", "CO-504", "G-02", "Programming Lab",
                "Computer Engineering", "TY Computer (CO-5I)", "Batch A2",
                "Prof. S. N. Joshi", "Practical / Lab", "PL/SQL Triggers and Stored Procedures"
        ));
        list.add(new TimetableSlot(
                "tt_m_05", "Monday", "02:00 PM", "03:00 PM",
                "Software Engineering", "CO-505", "C-101", "Classroom C-101",
                "Computer Engineering", "TY Computer (CO-5I)", "All",
                "Prof. M. R. Patil", "Lecture", "Agile sprint cycles and UML modeling"
        ));
        list.add(new TimetableSlot(
                "tt_m_06", "Monday", "03:00 PM", "04:00 PM",
                "Data Communication & Networks", "IF-501", "IT-204", "Classroom IT-204",
                "Information Technology", "TY IT (IF-5I)", "All",
                "Prof. N. K. Bagul", "Lecture", "OSI reference model & TCP/IP subnetting"
        ));
        list.add(new TimetableSlot(
                "tt_m_07", "Monday", "04:00 PM", "05:00 PM",
                "Web Development Tutorial", "IF-502", "IT-204", "Classroom IT-204",
                "Information Technology", "TY IT (IF-5I)", "All",
                "Mr. Ramesh Jagtap", "Tutorial", "Frontend frameworks and responsive layouts"
        ));

        // --- TUESDAY ---
        list.add(new TimetableSlot(
                "tt_t_01", "Tuesday", "09:00 AM", "10:00 AM",
                "Mobile Application Development", "CO-601", "C-102", "Classroom C-102",
                "Computer Engineering", "TY Computer (CO-5I)", "All",
                "Prof. Anand Kulkarni", "Lecture", "Android Activity Lifecycle & Intents"
        ));
        list.add(new TimetableSlot(
                "tt_t_02", "Tuesday", "10:00 AM", "11:00 AM",
                "Software Engineering", "CO-505", "C-101", "Classroom C-101",
                "Computer Engineering", "TY Computer (CO-5I)", "All",
                "Prof. M. R. Patil", "Lecture", "Software testing strategies & QA"
        ));
        list.add(new TimetableSlot(
                "tt_t_03", "Tuesday", "11:15 AM", "01:15 PM",
                "Mobile App Lab (Android Studio)", "CO-602", "G-01", "Computer Center Lab 1",
                "Computer Engineering", "TY Computer (CO-5I)", "Batch A1",
                "Prof. Anand Kulkarni", "Practical / Lab", "Building Material 3 XML layouts and Room DB"
        ));
        list.add(new TimetableSlot(
                "tt_t_04", "Tuesday", "02:00 PM", "03:00 PM",
                "Data Structures & Algorithms", "CO-301", "C-201", "Classroom C-201",
                "Computer Engineering", "SY Computer (CO-3I)", "All",
                "Prof. P. V. Joshi", "Lecture", "Binary Search Trees and Graph Traversals"
        ));
        list.add(new TimetableSlot(
                "tt_t_05", "Tuesday", "03:00 PM", "04:00 PM",
                "Object Oriented Programming (C++)", "CO-302", "C-201", "Classroom C-201",
                "Computer Engineering", "SY Computer (CO-3I)", "All",
                "Prof. S. N. Joshi", "Lecture", "Polymorphism, virtual functions and templates"
        ));
        list.add(new TimetableSlot(
                "tt_t_06", "Tuesday", "04:00 PM", "05:00 PM",
                "Cloud Computing & DevOps", "CO-605", "2-05", "Seminar Hall",
                "Computer Engineering", "TY Computer (CO-5I)", "All",
                "Dr. P. R. Deshmukh", "Seminar", "Guest lecture on AWS & Kubernetes architecture"
        ));

        // --- WEDNESDAY ---
        list.add(new TimetableSlot(
                "tt_w_01", "Wednesday", "09:00 AM", "10:00 AM",
                "Artificial Intelligence & ML", "CO-603", "C-101", "Classroom C-101",
                "Computer Engineering", "TY Computer (CO-5I)", "All",
                "Prof. Anand Kulkarni", "Lecture", "Supervised learning and decision trees"
        ));
        list.add(new TimetableSlot(
                "tt_w_02", "Wednesday", "10:00 AM", "11:00 AM",
                "Operating Systems", "CO-501", "C-101", "Classroom C-101",
                "Computer Engineering", "TY Computer (CO-5I)", "All",
                "Prof. Anand Kulkarni", "Lecture", "Deadlock avoidance algorithms and Banker's algorithm"
        ));
        list.add(new TimetableSlot(
                "tt_w_03", "Wednesday", "11:15 AM", "01:15 PM",
                "AI & Machine Learning Lab", "CO-604", "1-02", "IoT & Embedded Lab",
                "Computer Engineering", "TY Computer (CO-5I)", "Batch A2",
                "Prof. Anand Kulkarni", "Practical / Lab", "Python scikit-learn model evaluation"
        ));
        list.add(new TimetableSlot(
                "tt_w_04", "Wednesday", "02:00 PM", "03:00 PM",
                "Network Security", "IF-503", "IT-204", "Classroom IT-204",
                "Information Technology", "TY IT (IF-5I)", "All",
                "Prof. N. K. Bagul", "Lecture", "Symmetric encryption & public key cryptography"
        ));
        list.add(new TimetableSlot(
                "tt_w_05", "Wednesday", "03:00 PM", "05:00 PM",
                "Network Security Lab", "IF-504", "2-02", "Hardware & Network Lab",
                "Information Technology", "TY IT (IF-5I)", "Batch B1",
                "Prof. N. K. Bagul", "Practical / Lab", "Wireshark packet sniffing and firewall setup"
        ));

        // --- THURSDAY ---
        list.add(new TimetableSlot(
                "tt_th_01", "Thursday", "09:00 AM", "10:00 AM",
                "Database Management Systems", "CO-502", "C-101", "Classroom C-101",
                "Computer Engineering", "TY Computer (CO-5I)", "All",
                "Prof. S. N. Joshi", "Lecture", "Database indexing, B-Trees and Normalization"
        ));
        list.add(new TimetableSlot(
                "tt_th_02", "Thursday", "10:00 AM", "11:00 AM",
                "Mobile Application Development", "CO-601", "C-102", "Classroom C-102",
                "Computer Engineering", "TY Computer (CO-5I)", "All",
                "Prof. Anand Kulkarni", "Lecture", "RecyclerViews, Fragments, and Navigation components"
        ));
        list.add(new TimetableSlot(
                "tt_th_03", "Thursday", "11:15 AM", "01:15 PM",
                "Operating Systems Lab (Linux)", "CO-506", "G-01", "Computer Center Lab 1",
                "Computer Engineering", "TY Computer (CO-5I)", "Batch A1",
                "Prof. Anand Kulkarni", "Practical / Lab", "Shell scripting, IPC semaphores & fork system calls"
        ));
        list.add(new TimetableSlot(
                "tt_th_04", "Thursday", "02:00 PM", "03:00 PM",
                "Digital Techniques & Microprocessors", "CO-303", "C-201", "Classroom C-201",
                "Computer Engineering", "SY Computer (CO-3I)", "All",
                "Prof. P. V. Joshi", "Lecture", "8086 architecture and assembly programming"
        ));
        list.add(new TimetableSlot(
                "tt_th_05", "Thursday", "03:00 PM", "05:00 PM",
                "Data Structures Lab (C++)", "CO-304", "G-02", "Programming Lab",
                "Computer Engineering", "SY Computer (CO-3I)", "Batch A1",
                "Prof. P. V. Joshi", "Practical / Lab", "Stack & Queue implementation using linked lists"
        ));

        // --- FRIDAY ---
        list.add(new TimetableSlot(
                "tt_f_01", "Friday", "09:00 AM", "10:00 AM",
                "Software Engineering", "CO-505", "C-101", "Classroom C-101",
                "Computer Engineering", "TY Computer (CO-5I)", "All",
                "Prof. M. R. Patil", "Lecture", "Design patterns and clean code principles"
        ));
        list.add(new TimetableSlot(
                "tt_f_02", "Friday", "10:00 AM", "11:00 AM",
                "Artificial Intelligence & ML", "CO-603", "C-101", "Classroom C-101",
                "Computer Engineering", "TY Computer (CO-5I)", "All",
                "Prof. Anand Kulkarni", "Lecture", "Neural networks & computer vision fundamentals"
        ));
        list.add(new TimetableSlot(
                "tt_f_03", "Friday", "11:15 AM", "01:15 PM",
                "Capstone Project Phase 1", "CO-507", "2-03", "Project & Research Lab",
                "Computer Engineering", "TY Computer (CO-5I)", "All",
                "Prof. Anand Kulkarni", "Practical / Lab", "Project synopsis and architectural blueprint review"
        ));
        list.add(new TimetableSlot(
                "tt_f_04", "Friday", "02:00 PM", "03:00 PM",
                "Environmental Studies & Green IT", "CO-508", "C-101", "Classroom C-101",
                "Computer Engineering", "TY Computer (CO-5I)", "All",
                "Prof. M. R. Patil", "Lecture", "E-waste management and energy efficient computing"
        ));
        list.add(new TimetableSlot(
                "tt_f_05", "Friday", "03:00 PM", "04:00 PM",
                "Professional Communication", "CO-509", "C-101", "Classroom C-101",
                "Computer Engineering", "TY Computer (CO-5I)", "All",
                "Prof. Sneha Kadam", "Tutorial", "Technical report writing & mock interview presentations"
        ));

        // --- SATURDAY ---
        list.add(new TimetableSlot(
                "tt_s_01", "Saturday", "09:00 AM", "11:00 AM",
                "Remedial & Problem Solving Session", "CO-510", "C-101", "Classroom C-101",
                "Computer Engineering", "TY Computer (CO-5I)", "All",
                "Prof. Anand Kulkarni", "Tutorial", "Weekly doubt clearing and exam question analysis"
        ));
        list.add(new TimetableSlot(
                "tt_s_02", "Saturday", "11:15 AM", "01:15 PM",
                "Open Source & Linux Workshop", "CO-511", "G-01", "Computer Center Lab 1",
                "Computer Engineering", "SY Computer (CO-3I)", "All",
                "Mr. Ramesh Jagtap", "Seminar", "Git version control and open source contribution guide"
        ));
        list.add(new TimetableSlot(
                "tt_s_03", "Saturday", "02:00 PM", "04:00 PM",
                "Campus Hackathon & Coding Club", "CO-512", "G-01", "Computer Center Lab 1",
                "Computer Engineering", "TY Computer (CO-5I)", "All",
                "Prof. Anand Kulkarni", "Practical / Lab", "Competitive programming and mobile app build session"
        ));

        return list;
    }
}
