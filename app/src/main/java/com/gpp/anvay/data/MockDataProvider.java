package com.gpp.anvay.data;

import com.gpp.anvay.model.AlertItem;
import com.gpp.anvay.model.EmergencyContact;
import com.gpp.anvay.model.LocationItem;
import com.gpp.anvay.model.SafetyInstruction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MockDataProvider {

    public static List<LocationItem> getInitialLocations() {
        List<LocationItem> list = new ArrayList<>();

        // Ground Floor Locations
        list.add(new LocationItem(
                "loc_g_01",
                "G-01",
                "Computer Center Lab 1",
                "Laboratory",
                "Computer Engineering",
                "Ground Floor",
                "East Wing",
                "Primary computing facility with 40 high-performance workstations for programming and simulations.",
                "Prof. S. N. Joshi",
                "08:00 AM - 05:30 PM",
                Arrays.asList("40 Core i7 PCs", "Gigabit LAN", "Projector & Smart Board", "AC", "10kVA UPS"),
                Arrays.asList("Main Entrance Gate (15m)", "Drinking Water Station (10m East)", "Staircase A (20m)"),
                "Direct exit via Ground Floor East Main Gate"
        ));

        list.add(new LocationItem(
                "loc_g_02",
                "G-02",
                "Programming & Data Structures Lab",
                "Laboratory",
                "Information Technology",
                "Ground Floor",
                "East Wing",
                "Dedicated lab for C, C++, Java and Data Structures practical sessions.",
                "Prof. N. K. Bagul",
                "08:30 AM - 05:00 PM",
                Arrays.asList("35 Desktop Workstations", "Overhead Projector", "Whiteboard", "High-speed Wi-Fi"),
                Arrays.asList("Lab G-01 (Adjacent)", "Staff Room G-04 (15m)"),
                "Ground Floor East Exit to Quadrangle"
        ));

        list.add(new LocationItem(
                "loc_g_03",
                "G-03 / HOD-IT",
                "HOD Office - Information Technology",
                "HOD & Offices",
                "Information Technology",
                "Ground Floor",
                "Central Corridor",
                "Head of Department office for IT Engineering academic administration and student counseling.",
                "Dr. P. R. Deshmukh",
                "09:00 AM - 05:00 PM",
                Arrays.asList("Meeting Lounge", "Department Notice Board", "Printer & Scanner", "Telephone Desk"),
                Arrays.asList("Department Notice Board (Front)", "Main Lobby (10m)", "Staircase A (15m)"),
                "Main Central Lobby Exit"
        ));

        list.add(new LocationItem(
                "loc_g_04",
                "G-04",
                "IT Department Staff Cabin",
                "Staff Rooms",
                "Information Technology",
                "Ground Floor",
                "East Wing",
                "Faculty workstations and consultation cabins for Information Technology professors.",
                "Staff In-Charge: Prof. M. B. Patil",
                "08:30 AM - 05:30 PM",
                Arrays.asList("12 Faculty Desks", "Reference Bookshelf", "Consultation Table", "LAN & Wi-Fi"),
                Arrays.asList("HOD Office G-03 (10m)", "Lab G-02 (15m)"),
                "East Exit Door directly leading to Open Courtyard"
        ));

        list.add(new LocationItem(
                "loc_g_05",
                "G-05",
                "Network & Server Operations Center",
                "Server Rooms",
                "Common / General",
                "Ground Floor",
                "West Wing",
                "Central college networking hub housing core switches, firewall racks, and intranet servers.",
                "SysAdmin: Mr. K. R. Jagtap",
                "Restricted Access (Authorized Only)",
                Arrays.asList("Rack Servers", "Fire Suppression System", "Dedicated Dual AC", "Biometric Access"),
                Arrays.asList("Staircase B (10m)", "Security Control Desk (20m)"),
                "West Corridor Emergency Exit"
        ));

        list.add(new LocationItem(
                "loc_g_w1",
                "G-W1",
                "Gents Restroom - Ground Floor",
                "Washrooms",
                "Common / General",
                "Ground Floor",
                "West Wing",
                "Clean, sanitized washroom facility with automated dispensers and water supply.",
                "Maintenance Staff",
                "24/7 Campus Hours",
                Arrays.asList("Hand Dryers", "Mirror", "Exhaust System", "Wheelchair Accessible"),
                Arrays.asList("Staircase B (5m)", "Server Room G-05 (15m)"),
                "Staircase B Ground Exit"
        ));

        list.add(new LocationItem(
                "loc_g_w2",
                "G-W2",
                "Ladies Restroom - Ground Floor",
                "Washrooms",
                "Common / General",
                "Ground Floor",
                "East Wing",
                "Sanitary washroom with hygiene station and mirrors.",
                "Maintenance Staff",
                "24/7 Campus Hours",
                Arrays.asList("Hygiene Dispensers", "Mirrors", "Exhaust Fans", "Clean Water Supply"),
                Arrays.asList("Lab G-02 (10m)", "Staircase A (15m)"),
                "East Wing Ground Exit"
        ));

        // 1st Floor Locations
        list.add(new LocationItem(
                "loc_1_101",
                "C-101",
                "Classroom C-101 (First Year Comp Div A)",
                "Classrooms",
                "Computer Engineering",
                "1st Floor",
                "East Wing",
                "Spacious smart lecture hall equipped with digital acoustic podium and audio-visual projection.",
                "Class Teacher: Prof. V. S. Mane",
                "08:00 AM - 04:30 PM",
                Arrays.asList("80 Seating Capacity", "Interactive Projector", "PA Audio System", "Dual Greenboard"),
                Arrays.asList("Staircase A (8m Left)", "Water Dispenser 1st Floor (12m)"),
                "Staircase A down to Ground Floor Main Exit"
        ));

        list.add(new LocationItem(
                "loc_1_102",
                "C-102",
                "Classroom C-102 (First Year Comp Div B)",
                "Classrooms",
                "Computer Engineering",
                "1st Floor",
                "East Wing",
                "Airy lecture room for theoretical courses with modern ergonomic benches and ceiling fans.",
                "Class Teacher: Prof. S. A. Kadam",
                "08:00 AM - 04:30 PM",
                Arrays.asList("75 Seating Capacity", "Full HD Projector", "Broadband Wi-Fi", "Podium Mic"),
                Arrays.asList("Classroom C-101 (Adjacent)", "Staff Cabin 105 (20m)"),
                "Staircase A -> Ground Exit"
        ));

        list.add(new LocationItem(
                "loc_1_103",
                "C-103",
                "Web Technology & Cloud Computing Lab",
                "Laboratory",
                "Computer Engineering",
                "1st Floor",
                "Central Corridor",
                "Advanced practical lab tailored for Full-Stack development, Cloud setups, and API testing.",
                "Prof. A. V. Kulkarni",
                "08:30 AM - 05:00 PM",
                Arrays.asList("35 Core i5 Workstations", "Smart Screen", "1 Gbps Fiber Link", "Centralized Storage"),
                Arrays.asList("HOD Office 106 (Opposite)", "Staircase A (18m)"),
                "Staircase A down to Ground Floor"
        ));

        list.add(new LocationItem(
                "loc_1_104",
                "C-104",
                "Database & Operating Systems Lab",
                "Laboratory",
                "Computer Engineering",
                "1st Floor",
                "West Wing",
                "Equipped with Linux and Oracle database environments for student system programming experiments.",
                "Prof. T. D. Gaikwad",
                "08:30 AM - 05:00 PM",
                Arrays.asList("30 PC Systems", "Linux Dual-Boot", "Laser Printer", "Wall Projector"),
                Arrays.asList("Staircase B (12m Right)", "Restroom 1st Floor (25m)"),
                "Staircase B down to Ground Floor Exit"
        ));

        list.add(new LocationItem(
                "loc_1_106",
                "HOD-CO",
                "HOD Office - Computer Engineering",
                "HOD & Offices",
                "Computer Engineering",
                "1st Floor",
                "Central Corridor",
                "Office of the Head of Department, Computer Engineering. Academic governance and approvals.",
                "Dr. M. S. Patil",
                "09:00 AM - 05:00 PM",
                Arrays.asList("Conference Table", "Visitor Waiting Area", "Document Archive", "Direct Line"),
                Arrays.asList("Web Tech Lab 103 (Opposite)", "Staff Cabin 105 (10m)"),
                "Staircase A to Ground Floor Lobby"
        ));

        list.add(new LocationItem(
                "loc_1_105",
                "105",
                "Computer Dept Faculty Room",
                "Staff Rooms",
                "Computer Engineering",
                "1st Floor",
                "East Wing",
                "Staff workspace for Computer Engineering teaching faculty and laboratory assistants.",
                "Staff Coordinator: Prof. G. R. Shinde",
                "08:30 AM - 05:30 PM",
                Arrays.asList("15 Personal Cubicles", "Intercom System", "Tea/Coffee Station", "Department Library"),
                Arrays.asList("HOD Office 106 (10m)", "Classroom C-102 (15m)"),
                "Staircase A to Ground Floor"
        ));

        list.add(new LocationItem(
                "loc_1_108",
                "108",
                "Department Seminar & Presentation Room",
                "Facilities",
                "Common / General",
                "1st Floor",
                "West Wing",
                "Acoustic seminar hall for mini-project presentations, guest webinars, and technical seminars.",
                "Seminar Coordinator",
                "09:00 AM - 05:00 PM",
                Arrays.asList("100 Tiered Seats", "Surround Sound", "Dual Laser Projectors", "Video Conferencing"),
                Arrays.asList("Database Lab 104 (Adjacent)", "Staircase B (15m)"),
                "Staircase B down to West Exit"
        ));

        // 2nd Floor Locations
        list.add(new LocationItem(
                "loc_2_201",
                "IT-201",
                "Classroom IT-201 (Second Year IT)",
                "Classrooms",
                "Information Technology",
                "2nd Floor",
                "East Wing",
                "Standard lecture room equipped with hybrid learning aids and high-lumen digital projector.",
                "Prof. D. K. Pawar",
                "08:00 AM - 04:30 PM",
                Arrays.asList("70 Capacity", "Smart Interactive Board", "Wi-Fi 6 Router", "Emergency Alarm Node"),
                Arrays.asList("Staircase A (10m)", "AI/ML Lab 203 (15m)"),
                "Staircase A -> Down to Ground Level"
        ));

        list.add(new LocationItem(
                "loc_2_203",
                "IT-203",
                "Artificial Intelligence & ML Lab",
                "Laboratory",
                "Information Technology",
                "2nd Floor",
                "Central Corridor",
                "Specialized laboratory with GPU-enabled computing nodes for Deep Learning and Vision projects.",
                "Prof. R. T. Shinde",
                "08:30 AM - 05:30 PM",
                Arrays.asList("25 RTX-GPU Nodes", "High Resolution Display", "TensorFlow/PyTorch Suite", "AC"),
                Arrays.asList("Cyber Security Lab 204 (Opposite)", "Drinking Water 2nd Floor (8m)"),
                "Staircase A (East) or Staircase B (West)"
        ));

        list.add(new LocationItem(
                "loc_2_204",
                "IT-204",
                "Cyber Security & Network Forensics Lab",
                "Laboratory",
                "Information Technology",
                "2nd Floor",
                "Central Corridor",
                "Isolated sandbox network lab for ethical hacking, cryptography drills, and vulnerability testing.",
                "Prof. V. N. Bhende",
                "09:00 AM - 05:00 PM",
                Arrays.asList("Isolated Switch Racks", "30 Forensic Workstations", "Fire Extinguisher Box"),
                Arrays.asList("AI/ML Lab 203 (Opposite)", "Staff Meeting Cabin 207 (12m)"),
                "Staircase B towards West Evacuation Gate"
        ));

        list.add(new LocationItem(
                "loc_2_205",
                "IT-205",
                "IoT & Embedded Systems Workshop",
                "Laboratory",
                "Information Technology",
                "2nd Floor",
                "West Wing",
                "Hardware prototyping space with Arduino, Raspberry Pi, sensor kits, and oscilloscope stations.",
                "Lab Tech: Mr. A. B. Thorat",
                "08:30 AM - 05:00 PM",
                Arrays.asList("Soldering Stations", "Oscilloscopes", "Sensor Inventory", "Safety First Aid Kit"),
                Arrays.asList("Staircase B (6m Left)", "Boys Washroom 2nd Floor (14m)"),
                "Staircase B -> West Exit Door"
        ));

        list.add(new LocationItem(
                "loc_2_w1",
                "2-W1",
                "Gents Restroom - 2nd Floor",
                "Washrooms",
                "Common / General",
                "2nd Floor",
                "West Wing",
                "Restroom facility with automated sensor faucets and exhaust ventilation.",
                "Maintenance Staff",
                "Campus Hours",
                Arrays.asList("Sanitary Utilities", "Mirrors", "Sensor Taps"),
                Arrays.asList("IoT Lab 205 (Adjacent)", "Staircase B (10m)"),
                "Staircase B down to Ground"
        ));

        // 3rd Floor Locations
        list.add(new LocationItem(
                "loc_3_301",
                "C-301",
                "Classroom C-301 (Third Year Comp A)",
                "Classrooms",
                "Computer Engineering",
                "3rd Floor",
                "East Wing",
                "Final-year classroom with advanced seminar display and recording equipment.",
                "Prof. P. M. Joshi",
                "08:00 AM - 04:30 PM",
                Arrays.asList("80 Seating", "Lecture Capture Camera", "Smart Projector", "Wi-Fi"),
                Arrays.asList("Staircase A (8m)", "Project Incubation Lab 303 (20m)"),
                "Staircase A down to Ground Floor"
        ));

        list.add(new LocationItem(
                "loc_3_303",
                "303",
                "Project & Startup Incubation Hub",
                "Facilities",
                "Common / General",
                "3rd Floor",
                "Central Corridor",
                "Collaborative co-working laboratory for student final-year capstone projects and hackathon teams.",
                "Incubation Officer: Dr. K. S. Jadhav",
                "08:00 AM - 07:00 PM",
                Arrays.asList("Modular Whiteboards", "Discussion Pods", "3D Printer", "High-speed Mesh Wi-Fi"),
                Arrays.asList("Auditorium 304 (Opposite)", "Staircase A (15m)"),
                "Staircase A -> Ground Level"
        ));

        list.add(new LocationItem(
                "loc_3_304",
                "304",
                "Auditorium & Smart Seminar Hall",
                "Facilities",
                "Common / General",
                "3rd Floor",
                "West Wing",
                "Grand auditorium with 250 capacity for national conferences, cultural seminars, and symposiums.",
                "Auditorium Manager",
                "Event-based / 08:30 AM - 06:00 PM",
                Arrays.asList("250 Plush Seats", "Central AC", "Digital Line Array Audio", "Motorized Screen"),
                Arrays.asList("Staircase B (8m)", "Incubation Hub 303 (Opposite)"),
                "Staircase B Fire Evacuation Route"
        ));

        return list;
    }

    public static List<AlertItem> getInitialAlerts() {
        List<AlertItem> list = new ArrayList<>();

        list.add(new AlertItem(
                "alert_01",
                "Corridor Tile Repair: 2nd Floor East Wing",
                "Floor maintenance in progress near Room IT-201. Please use Staircase B for reaching 2nd and 3rd floors safely.",
                "Maintenance",
                "Urgent",
                "15 mins ago",
                "2nd Floor East Corridor",
                true
        ));

        list.add(new AlertItem(
                "alert_02",
                "Lab Shift Notice: Operating Systems Practical",
                "Due to network maintenance in Lab C-104, today's afternoon practical session is shifted to Computer Center Lab 1 (G-01).",
                "Relocation",
                "Medium",
                "1 hour ago",
                "Room C-104 -> Room G-01",
                true
        ));

        list.add(new AlertItem(
                "alert_03",
                "Campus Fire Safety Drill Simulation",
                "Annual emergency evacuation and fire drill will be held on Friday at 03:00 PM. Follow evacuation markers to the Sports Ground.",
                "Emergency",
                "Normal",
                "Today, 09:00 AM",
                "All Floors - Comp/IT Building",
                true
        ));

        list.add(new AlertItem(
                "alert_04",
                "Water Dispenser Maintenance Notice",
                "Filter replacement scheduled on 1st Floor drinking water station between 11:30 AM and 01:00 PM.",
                "Notice",
                "Normal",
                "Yesterday",
                "1st Floor Water Station",
                false
        ));

        return list;
    }

    public static List<EmergencyContact> getInitialEmergencyContacts() {
        List<EmergencyContact> list = new ArrayList<>();

        list.add(new EmergencyContact(
                "emg_01",
                "Campus Security Main Gate Post",
                "Mr. R. D. Shinde (Head Guard)",
                "+91 20 2567 6801",
                "Main Gate Security Cabin",
                "police"
        ));

        list.add(new EmergencyContact(
                "emg_02",
                "GPP Medical Health Room",
                "Dr. S. K. Joshi (Campus Doctor)",
                "+91 20 2567 6825",
                "Admin Block, Ground Floor Room 12",
                "medical"
        ));

        list.add(new EmergencyContact(
                "emg_03",
                "Fire Safety & Evacuation Marshall",
                "Prof. P. B. Mane",
                "+91 98220 54321",
                "Comp/IT Building, Room 105",
                "fire"
        ));

        list.add(new EmergencyContact(
                "emg_04",
                "Comp Engineering Dept Emergency Desk",
                "Dr. M. S. Patil (HOD)",
                "+91 20 2567 6850",
                "1st Floor HOD Office",
                "admin"
        ));

        list.add(new EmergencyContact(
                "emg_05",
                "Pune Municipal Fire Station (Shivajinagar)",
                "Emergency Control Room",
                "101 / +91 20 2550 6333",
                "Shivajinagar Fire Station",
                "fire"
        ));

        list.add(new EmergencyContact(
                "emg_06",
                "City Ambulance Services",
                "Emergency Medical Dispatch",
                "108",
                "Pune Central",
                "medical"
        ));

        return list;
    }

    public static List<SafetyInstruction> getInitialSafetyInstructions() {
        List<SafetyInstruction> list = new ArrayList<>();

        list.add(new SafetyInstruction(
                "safe_01",
                "Fire Evacuation Protocol",
                "Immediate actions to take upon hearing the fire alarm or discovering smoke/flames in the building.",
                Arrays.asList(
                        "Activate nearest fire pull station if alarm is not ringing.",
                        "Evacuate immediately via designated staircases (Staircase A or B).",
                        "DO NOT use elevators or lift shafts during a fire emergency.",
                        "Stay low if there is smoke; cover mouth and nose with a damp cloth.",
                        "Assemble at Open Sports Ground opposite the IT Wing for headcount."
                ),
                "Open Sports Ground (Main Quadrangle)",
                "fire"
        ));

        list.add(new SafetyInstruction(
                "safe_02",
                "Earthquake Safety Protocol (Drop, Cover & Hold)",
                "Safety measures during active seismic tremors and subsequent post-quake evacuation.",
                Arrays.asList(
                        "DROP onto your hands and knees immediately.",
                        "COVER your head and neck under sturdy desks or interior walls away from glass windows.",
                        "HOLD ON until the shaking completely stops.",
                        "Once shaking ceases, calmly exit building via emergency stairs.",
                        "Stay clear of high-voltage power lines and brick facades outside."
                ),
                "Central Campus Open Quadrangle",
                "shield"
        ));

        list.add(new SafetyInstruction(
                "safe_03",
                "Medical Emergency & First Aid Response",
                "Steps to assist an injured student, faculty member, or visitor before professional medics arrive.",
                Arrays.asList(
                        "Call Campus Medical Room (+91 20 2567 6825) or 108 immediately.",
                        "First Aid kits are stationed in Lab G-01, Lab 103, and Room 205.",
                        "Do not move unconscious persons unless there is imminent environmental hazard.",
                        "Assign a volunteer to guide the emergency ambulance from Main Gate."
                ),
                "GPP Medical Health Room (Admin Ground Floor)",
                "medical"
        ));

        list.add(new SafetyInstruction(
                "safe_04",
                "Severe Weather & Power Grid Outage",
                "Guidance during severe thunderstorm, flash rain, or sudden power breakdown.",
                Arrays.asList(
                        "Emergency backup battery lights will activate along all main stairwells and corridors.",
                        "Disconnect sensitive lab equipment from power sockets.",
                        "Remain inside designated safe rooms until weather clearance is broadcasted by Admin."
                ),
                "Building Ground Floor Central Lobby",
                "stairs"
        ));

        return list;
    }
}
