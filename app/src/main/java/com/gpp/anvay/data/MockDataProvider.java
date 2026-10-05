package com.gpp.anvay.data;

import com.gpp.anvay.model.AlertItem;
import com.gpp.anvay.model.BuildingItem;
import com.gpp.anvay.model.EmergencyContact;
import com.gpp.anvay.model.LocationItem;
import com.gpp.anvay.model.SafetyInstruction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MockDataProvider {

    public static List<BuildingItem> getInitialBuildings() {
        List<BuildingItem> list = new ArrayList<>();
        list.add(new BuildingItem(
                "bldg_comp_it",
                "Computer/IT Building",
                "COMP-IT",
                "Houses Computer Engineering, Information Technology, and Science & Humanities departments across Ground, 1st, and 2nd floors.",
                Arrays.asList("Ground Floor", "1st Floor", "2nd Floor"),
                true
        ));
        return list;
    }

    public static List<LocationItem> getInitialLocations() {
        List<LocationItem> list = new ArrayList<>();

        // =========================================================================
        // GROUND FLOOR LOCATIONS (Verified Section 5)
        // =========================================================================

        list.add(new LocationItem(
                "loc_gf_gwc",
                "GF-GWC",
                "Girls WC",
                "Washrooms",
                "Common / General",
                "Ground Floor",
                "East Wing",
                "Sanitary washroom facility with clean running water, hygiene dispensers, and mirrors.",
                "Sanitation Staff",
                "24/7 Campus Hours",
                Arrays.asList("Hygiene Dispensers", "Mirrors", "Exhaust Fans", "Clean Water Supply"),
                Arrays.asList("Near Staircase S1", "Server Room"),
                "Proceed via Staircase S1 or Ground Floor East Corridor exit."
        ));

        list.add(new LocationItem(
                "loc_gf_server",
                "GF-SRV",
                "Server Room",
                "Server Rooms",
                "Common / General",
                "Ground Floor",
                "East Wing",
                "Central server operations center housing campus network racks, switches, and firewall systems.",
                "SysAdmin: Mr. K. R. Jagtap",
                "Restricted Access (Authorized Only)",
                Arrays.asList("Rack Servers", "Dual AC Cooling", "Fire Suppression System", "Biometric Entry"),
                Arrays.asList("Girls WC", "Lab 1"),
                "Proceed via Ground Floor East Corridor exit."
        ));

        list.add(new LocationItem(
                "loc_gf_lab1",
                "GF-LAB1",
                "Lab 1",
                "Laboratory",
                "Computer Engineering",
                "Ground Floor",
                "East Wing",
                "Computer Engineering practical laboratory equipped for programming and data structures.",
                "Prof. S. N. Joshi",
                "08:00 AM - 05:30 PM",
                Arrays.asList("40 Core i7 Workstations", "Gigabit LAN", "Projector & Smart Screen", "UPS Backup"),
                Arrays.asList("Server Room", "Lab 2"),
                "Proceed via Ground Floor East Corridor exit."
        ));

        list.add(new LocationItem(
                "loc_gf_lab2",
                "GF-LAB2",
                "Lab 2",
                "Laboratory",
                "Computer Engineering",
                "Ground Floor",
                "East Wing",
                "Computing laboratory for Object-Oriented Programming and Software Engineering practicals.",
                "Prof. N. K. Bagul",
                "08:30 AM - 05:00 PM",
                Arrays.asList("35 Desktop Workstations", "Overhead Projector", "Whiteboard", "High-speed Wi-Fi"),
                Arrays.asList("Lab 1", "Lab 3"),
                "Proceed via Ground Floor East Corridor exit."
        ));

        list.add(new LocationItem(
                "loc_gf_lab3",
                "GF-LAB3",
                "Lab 3",
                "Laboratory",
                "Computer Engineering",
                "Ground Floor",
                "Central Corridor",
                "Hardware & Networking laboratory configured for microprocessors and digital electronics.",
                "Prof. A. V. Kulkarni",
                "08:30 AM - 05:00 PM",
                Arrays.asList("30 PC Systems", "Microprocessor Trainer Kits", "Logic Analyzers", "LAN"),
                Arrays.asList("Lab 2", "Lab 4"),
                "Proceed via Ground Floor Central Lobby exit."
        ));

        list.add(new LocationItem(
                "loc_gf_lab4",
                "GF-LAB4",
                "Lab 4",
                "Laboratory",
                "Computer Engineering",
                "Ground Floor",
                "Central Corridor",
                "Advanced Software Systems and Database Development laboratory.",
                "Prof. T. D. Gaikwad",
                "08:30 AM - 05:00 PM",
                Arrays.asList("35 Core i5 Workstations", "Linux Dual Boot", "Interactive Board", "AC"),
                Arrays.asList("Lab 3", "Boys WC"),
                "Proceed via Ground Floor Central Lobby exit."
        ));

        list.add(new LocationItem(
                "loc_gf_bwc",
                "GF-BWC",
                "Boys WC",
                "Washrooms",
                "Common / General",
                "Ground Floor",
                "Central Corridor",
                "Sanitized restroom facility for male students and faculty members.",
                "Sanitation Staff",
                "24/7 Campus Hours",
                Arrays.asList("Hand Dryers", "Mirror", "Exhaust System", "Sanitary Stations"),
                Arrays.asList("Lab 4", "CM Staff Room 2"),
                "Proceed via Ground Floor Central Corridor exit."
        ));

        list.add(new LocationItem(
                "loc_gf_cm_staff2",
                "GF-CMS2",
                "CM Staff Room 2",
                "Staff Rooms",
                "Computer Engineering",
                "Ground Floor",
                "Central Corridor",
                "Faculty consultation room and workstations for Computer Engineering professors.",
                "Prof. G. R. Shinde",
                "08:30 AM - 05:30 PM",
                Arrays.asList("Faculty Desks", "Consultation Area", "Intercom", "Wi-Fi"),
                Arrays.asList("Boys WC", "IT Lab 1"),
                "Proceed via Ground Floor Central Corridor exit."
        ));

        list.add(new LocationItem(
                "loc_gf_it_lab1",
                "GF-ITL1",
                "IT Lab 1",
                "Laboratory",
                "Information Technology",
                "Ground Floor",
                "West Wing",
                "Information Technology introductory programming and scripting laboratory.",
                "Prof. M. B. Patil",
                "08:30 AM - 05:00 PM",
                Arrays.asList("35 Workstations", "Smart Projector", "Wi-Fi 6 Router", "Centralized Storage"),
                Arrays.asList("CM Staff Room 2", "IT Lab 2"),
                "Proceed via Ground Floor West Corridor exit."
        ));

        list.add(new LocationItem(
                "loc_gf_it_lab2",
                "GF-ITL2",
                "IT Lab 2",
                "Laboratory",
                "Information Technology",
                "Ground Floor",
                "West Wing",
                "Multimedia and Web Technologies laboratory for IT Engineering students.",
                "Prof. R. T. Shinde",
                "08:30 AM - 05:00 PM",
                Arrays.asList("30 PC Systems", "High Resolution Displays", "Web Design Suite", "LAN"),
                Arrays.asList("IT Lab 1", "IT Lab 3"),
                "Proceed via Ground Floor West Corridor exit."
        ));

        list.add(new LocationItem(
                "loc_gf_it_lab3",
                "GF-ITL3",
                "IT Lab 3",
                "Laboratory",
                "Information Technology",
                "Ground Floor",
                "West Wing",
                "Database Systems and Cloud Infrastructure laboratory.",
                "Prof. V. N. Bhende",
                "08:30 AM - 05:00 PM",
                Arrays.asList("35 GPU Nodes", "Oracle / PostgreSQL Server", "Projector", "AC"),
                Arrays.asList("IT Lab 2", "IT Lab 4"),
                "Proceed via Ground Floor West Corridor exit."
        ));

        list.add(new LocationItem(
                "loc_gf_it_lab4",
                "GF-ITL4",
                "IT Lab 4",
                "Laboratory",
                "Information Technology",
                "Ground Floor",
                "West Wing",
                "Mobile Application Development and Network Security practical laboratory.",
                "Prof. D. K. Pawar",
                "08:30 AM - 05:00 PM",
                Arrays.asList("30 Workstations", "Android SDK Suite", "Switch Racks", "LAN"),
                Arrays.asList("IT Lab 3", "TPO Office"),
                "Proceed via Ground Floor West Corridor exit."
        ));

        list.add(new LocationItem(
                "loc_gf_tpo",
                "GF-TPO",
                "TPO Office",
                "HOD & Offices",
                "Common / General",
                "Ground Floor",
                "West Wing",
                "Training & Placement Office for student internships, placement drives, and career guidance.",
                "TPO In-Charge: Prof. S. A. Kadam",
                "09:00 AM - 05:00 PM",
                Arrays.asList("Interview Cabins", "Conference Table", "Student Notice Board", "Printer Desk"),
                Arrays.asList("IT Lab 4", "Lab 5"),
                "Proceed via Ground Floor West Corridor exit."
        ));

        list.add(new LocationItem(
                "loc_gf_lab5",
                "GF-LAB5",
                "Lab 5",
                "Laboratory",
                "Computer Engineering",
                "Ground Floor",
                "West Wing",
                "Final Year Capstone Project Development and Innovation laboratory.",
                "Prof. V. S. Mane",
                "08:30 AM - 05:30 PM",
                Arrays.asList("25 Development Nodes", "IoT Kits", "Soldering Station", "Smart Screen"),
                Arrays.asList("TPO Office", "CM HOD and Staff Room 1"),
                "Proceed via Ground Floor West Corridor exit."
        ));

        list.add(new LocationItem(
                "loc_gf_cm_hod_staff1",
                "GF-CMHOD",
                "CM HOD and Staff Room 1",
                "HOD & Offices",
                "Computer Engineering",
                "Ground Floor",
                "Central Wing",
                "Head of Department Office and main faculty chamber for Computer Engineering.",
                "Dr. M. S. Patil (HOD)",
                "09:00 AM - 05:00 PM",
                Arrays.asList("HOD Chamber", "Faculty Desks", "Meeting Lounge", "Department Archive"),
                Arrays.asList("Lab 5", "IT HOD & Staff Room"),
                "Proceed via Ground Floor Central Lobby exit."
        ));

        list.add(new LocationItem(
                "loc_gf_it_hod_staff",
                "GF-ITHOD",
                "IT HOD & Staff Room",
                "HOD & Offices",
                "Information Technology",
                "Ground Floor",
                "Central Wing",
                "Head of Department Office and main faculty chamber for Information Technology.",
                "Dr. P. R. Deshmukh (HOD)",
                "09:00 AM - 05:00 PM",
                Arrays.asList("HOD Chamber", "Faculty Cabins", "Consultation Table", "Intercom"),
                Arrays.asList("CM HOD and Staff Room 1", "Staircase S2"),
                "Proceed via Ground Floor Central Lobby exit."
        ));

        list.add(new LocationItem(
                "loc_gf_s1",
                "GF-S1",
                "Staircase S1",
                "Facilities",
                "Common / General",
                "Ground Floor",
                "East Wing",
                "Vertical staircase S1 providing access from Ground Floor to 1st Floor.",
                "Campus Facility",
                "24/7 Campus Hours",
                Arrays.asList("Wide Steps", "Handrails", "Emergency Illumination"),
                Arrays.asList("Girls WC", "Server Room"),
                "Direct Ground Floor East exit."
        ));

        list.add(new LocationItem(
                "loc_gf_s2",
                "GF-S2",
                "Staircase S2",
                "Facilities",
                "Common / General",
                "Ground Floor",
                "Central Wing",
                "Vertical staircase S2 providing access from Ground Floor to 1st Floor.",
                "Campus Facility",
                "24/7 Campus Hours",
                Arrays.asList("Wide Steps", "Handrails", "Emergency Illumination"),
                Arrays.asList("IT HOD & Staff Room", "Central Lobby"),
                "Direct Ground Floor Central exit."
        ));

        list.add(new LocationItem(
                "loc_gf_s3",
                "GF-S3",
                "Staircase S3",
                "Facilities",
                "Common / General",
                "Ground Floor",
                "West Wing",
                "Vertical staircase S3 providing access from Ground Floor to 1st Floor.",
                "Campus Facility",
                "24/7 Campus Hours",
                Arrays.asList("Wide Steps", "Handrails", "Emergency Illumination"),
                Arrays.asList("Lab 5", "West Corridor"),
                "Direct Ground Floor West exit."
        ));

        // =========================================================================
        // 1ST FLOOR LOCATIONS (Verified Section 6)
        // =========================================================================

        list.add(new LocationItem(
                "loc_ff_gwc",
                "FF-GWC",
                "Girls WC",
                "Washrooms",
                "Common / General",
                "1st Floor",
                "East Wing",
                "Sanitary washroom for female students and staff on the 1st Floor.",
                "Sanitation Staff",
                "24/7 Campus Hours",
                Arrays.asList("Hygiene Dispensers", "Mirrors", "Clean Water Supply"),
                Arrays.asList("Near Staircase S1", "Male Staff Room"),
                "Take Staircase S1 down to Ground Floor."
        ));

        list.add(new LocationItem(
                "loc_ff_male_staff",
                "FF-MSR",
                "Male Staff Room",
                "Staff Rooms",
                "Common / General",
                "1st Floor",
                "East Wing",
                "Staff room and consultation area for faculty members.",
                "Faculty In-Charge",
                "08:30 AM - 05:30 PM",
                Arrays.asList("Faculty Workstations", "Tea Station", "Wi-Fi", "Intercom"),
                Arrays.asList("Girls WC", "Server Room"),
                "Take Staircase S1 down to Ground Floor."
        ));

        list.add(new LocationItem(
                "loc_ff_server",
                "FF-SRV",
                "Server Room",
                "Server Rooms",
                "Common / General",
                "1st Floor",
                "East Wing",
                "First floor network distribution room and switch hub.",
                "SysAdmin",
                "Restricted Access",
                Arrays.asList("Switch Racks", "AC", "UPS Backup"),
                Arrays.asList("Male Staff Room", "CET Lab 1"),
                "Take Staircase S1 down to Ground Floor."
        ));

        list.add(new LocationItem(
                "loc_ff_cet_lab1",
                "FF-CET1",
                "CET Lab 1",
                "Laboratory",
                "Computer Engineering",
                "1st Floor",
                "East Wing",
                "Computer Engineering Technology practical laboratory 1.",
                "Prof. P. B. Mane",
                "08:30 AM - 05:00 PM",
                Arrays.asList("35 Workstations", "Smart Screen", "Gigabit LAN"),
                Arrays.asList("Server Room", "CET Lab 2"),
                "Take Staircase S1 down to Ground Floor."
        ));

        list.add(new LocationItem(
                "loc_ff_cet_lab2",
                "FF-CET2",
                "CET Lab 2",
                "Laboratory",
                "Computer Engineering",
                "1st Floor",
                "East Wing",
                "Computer Engineering Technology practical laboratory 2.",
                "Prof. K. N. Patil",
                "08:30 AM - 05:00 PM",
                Arrays.asList("35 Workstations", "Projector", "LAN"),
                Arrays.asList("CET Lab 1", "CET Lab 3"),
                "Take Staircase S1 down to Ground Floor."
        ));

        list.add(new LocationItem(
                "loc_ff_cet_lab3",
                "FF-CET3",
                "CET Lab 3",
                "Laboratory",
                "Computer Engineering",
                "1st Floor",
                "Central Corridor",
                "Computer Engineering Technology practical laboratory 3.",
                "Prof. S. R. Joshi",
                "08:30 AM - 05:00 PM",
                Arrays.asList("30 Workstations", "Whiteboard", "LAN"),
                Arrays.asList("CET Lab 2", "CET Lab 4"),
                "Take Staircase S2 down to Ground Floor."
        ));

        list.add(new LocationItem(
                "loc_ff_cet_lab4",
                "FF-CET4",
                "CET Lab 4",
                "Laboratory",
                "Information Technology",
                "1st Floor",
                "Central Corridor",
                "Computer Engineering Technology practical laboratory 4.",
                "Prof. D. M. Shinde",
                "08:30 AM - 05:00 PM",
                Arrays.asList("30 Workstations", "Smart Display", "LAN"),
                Arrays.asList("CET Lab 3", "CET Lab 5"),
                "Take Staircase S2 down to Ground Floor."
        ));

        list.add(new LocationItem(
                "loc_ff_cet_lab5",
                "FF-CET5",
                "CET Lab 5",
                "Laboratory",
                "Information Technology",
                "1st Floor",
                "Central Corridor",
                "Computer Engineering Technology practical laboratory 5.",
                "Prof. A. R. Kadam",
                "08:30 AM - 05:00 PM",
                Arrays.asList("35 Workstations", "Projector", "LAN"),
                Arrays.asList("CET Lab 4", "CET Lab 6"),
                "Take Staircase S2 down to Ground Floor."
        ));

        list.add(new LocationItem(
                "loc_ff_cet_lab6",
                "FF-CET6",
                "CET Lab 6",
                "Laboratory",
                "Information Technology",
                "1st Floor",
                "Central Corridor",
                "Computer Engineering Technology practical laboratory 6.",
                "Prof. V. T. Pawar",
                "08:30 AM - 05:00 PM",
                Arrays.asList("35 Workstations", "High Speed Internet", "LAN"),
                Arrays.asList("CET Lab 5", "Water Cooler"),
                "Take Staircase S2 down to Ground Floor."
        ));

        list.add(new LocationItem(
                "loc_ff_water_cooler",
                "FF-WC",
                "Water Cooler",
                "Facilities",
                "Common / General",
                "1st Floor",
                "Central Corridor",
                "Purified drinking water station with cooling and filtration unit.",
                "Campus Facility",
                "24/7 Campus Hours",
                Arrays.asList("RO Water Purifier", "Cooling Unit", "Sanitized Dispenser"),
                Arrays.asList("CET Lab 6", "Boys WC"),
                "Take Staircase S2 down to Ground Floor."
        ));

        list.add(new LocationItem(
                "loc_ff_bwc",
                "FF-BWC",
                "Boys WC",
                "Washrooms",
                "Common / General",
                "1st Floor",
                "Central Corridor",
                "Sanitary restroom facility for male students and staff on 1st Floor.",
                "Sanitation Staff",
                "24/7 Campus Hours",
                Arrays.asList("Hand Dryers", "Mirror", "Exhaust System"),
                Arrays.asList("Water Cooler", "CR 20"),
                "Take Staircase S2 down to Ground Floor."
        ));

        list.add(new LocationItem(
                "loc_ff_cr20",
                "CR 20",
                "CR 20",
                "Classrooms",
                "Computer Engineering",
                "1st Floor",
                "West Wing",
                "Lecture classroom 20 with audio-visual projection and interactive blackboard.",
                "Prof. B. S. Deshmukh",
                "08:00 AM - 04:30 PM",
                Arrays.asList("75 Seating Capacity", "Full HD Projector", "Podium Mic", "Wi-Fi"),
                Arrays.asList("Boys WC", "CR 19"),
                "Take Staircase S3 down to Ground Floor."
        ));

        list.add(new LocationItem(
                "loc_ff_cr19",
                "CR 19",
                "CR 19",
                "Classrooms",
                "Computer Engineering",
                "1st Floor",
                "West Wing",
                "Lecture classroom 19 for departmental lectures and seminars.",
                "Prof. M. K. Kulkarni",
                "08:00 AM - 04:30 PM",
                Arrays.asList("75 Seating Capacity", "Interactive Screen", "PA System"),
                Arrays.asList("CR 20", "Tutorial Room"),
                "Take Staircase S3 down to Ground Floor."
        ));

        list.add(new LocationItem(
                "loc_ff_tutorial",
                "FF-TUT",
                "Tutorial Room",
                "Classrooms",
                "Common / General",
                "1st Floor",
                "West Wing",
                "Dedicated tutorial and remedial classroom for small-group discussions.",
                "Faculty In-Charge",
                "08:30 AM - 04:30 PM",
                Arrays.asList("40 Seating Capacity", "Whiteboard", "Discussion Round Tables"),
                Arrays.asList("CR 19", "CR 18"),
                "Take Staircase S3 down to Ground Floor."
        ));

        list.add(new LocationItem(
                "loc_ff_cr18",
                "CR 18",
                "CR 18",
                "Classrooms",
                "Information Technology",
                "1st Floor",
                "West Wing",
                "Lecture classroom 18 positioned in corrected sequence adjacent to Tutorial Room and CR 17.",
                "Prof. S. N. Patil",
                "08:00 AM - 04:30 PM",
                Arrays.asList("75 Seating Capacity", "Projector", "Podium", "Wi-Fi"),
                Arrays.asList("Tutorial Room", "CR 17"),
                "Take Staircase S3 down to Ground Floor."
        ));

        list.add(new LocationItem(
                "loc_ff_cr17",
                "CR 17",
                "CR 17",
                "Classrooms",
                "Information Technology",
                "1st Floor",
                "West Wing",
                "Lecture classroom 17 positioned in corrected sequence (directly below CR 21).",
                "Prof. R. P. Shinde",
                "08:00 AM - 04:30 PM",
                Arrays.asList("75 Seating Capacity", "Projector", "Smart Board", "Wi-Fi"),
                Arrays.asList("CR 18", "CR 16"),
                "Take Staircase S3 down to Ground Floor."
        ));

        list.add(new LocationItem(
                "loc_ff_cr16",
                "CR 16",
                "CR 16",
                "Classrooms",
                "Computer Engineering",
                "1st Floor",
                "West Wing",
                "Lecture classroom 16 for theoretical engineering courses.",
                "Prof. V. A. Jagtap",
                "08:00 AM - 04:30 PM",
                Arrays.asList("75 Seating Capacity", "Full HD Projector", "Wi-Fi"),
                Arrays.asList("CR 17", "CR 15"),
                "Take Staircase S3 down to Ground Floor."
        ));

        list.add(new LocationItem(
                "loc_ff_cr15",
                "CR 15",
                "CR 15",
                "Classrooms",
                "Computer Engineering",
                "1st Floor",
                "West Wing",
                "Lecture classroom 15 for degree and diploma engineering batches.",
                "Prof. T. G. Thorat",
                "08:00 AM - 04:30 PM",
                Arrays.asList("75 Seating Capacity", "Projector", "PA System"),
                Arrays.asList("CR 16", "CR 14"),
                "Take Staircase S3 down to Ground Floor."
        ));

        list.add(new LocationItem(
                "loc_ff_cr14",
                "CR 14",
                "CR 14",
                "Classrooms",
                "Computer Engineering",
                "1st Floor",
                "West Wing",
                "Lecture classroom 14 at the west corridor end.",
                "Prof. N. D. Bagul",
                "08:00 AM - 04:30 PM",
                Arrays.asList("75 Seating Capacity", "Digital Board", "Wi-Fi"),
                Arrays.asList("CR 15", "Staircase S3"),
                "Take Staircase S3 down to Ground Floor."
        ));

        list.add(new LocationItem(
                "loc_ff_s1",
                "FF-S1",
                "Staircase S1",
                "Facilities",
                "Common / General",
                "1st Floor",
                "East Wing",
                "Vertical staircase S1 providing access between Ground Floor, 1st Floor, and 2nd Floor.",
                "Campus Facility",
                "24/7 Campus Hours",
                Arrays.asList("Wide Steps", "Handrails", "Emergency Illumination"),
                Arrays.asList("Girls WC", "Male Staff Room"),
                "Take Staircase S1 down to Ground Floor East exit."
        ));

        list.add(new LocationItem(
                "loc_ff_s2",
                "FF-S2",
                "Staircase S2",
                "Facilities",
                "Common / General",
                "1st Floor",
                "Central Wing",
                "Vertical staircase S2 providing access between Ground Floor, 1st Floor, and 2nd Floor.",
                "Campus Facility",
                "24/7 Campus Hours",
                Arrays.asList("Wide Steps", "Handrails", "Emergency Illumination"),
                Arrays.asList("Water Cooler", "Boys WC"),
                "Take Staircase S2 down to Ground Floor Central exit."
        ));

        list.add(new LocationItem(
                "loc_ff_s3",
                "FF-S3",
                "Staircase S3",
                "Facilities",
                "Common / General",
                "1st Floor",
                "West Wing",
                "Vertical staircase S3 providing access between Ground Floor, 1st Floor, and 2nd Floor.",
                "Campus Facility",
                "24/7 Campus Hours",
                Arrays.asList("Wide Steps", "Handrails", "Emergency Illumination"),
                Arrays.asList("CR 14", "CR 15"),
                "Take Staircase S3 down to Ground Floor West exit."
        ));

        // =========================================================================
        // 2ND FLOOR LOCATIONS (Verified Section 7)
        // =========================================================================

        list.add(new LocationItem(
                "loc_sf_gwc",
                "2F-GWC",
                "Girls WC",
                "Washrooms",
                "Common / General",
                "2nd Floor",
                "East Wing",
                "Sanitary restroom facility for female students and staff on 2nd Floor.",
                "Sanitation Staff",
                "24/7 Campus Hours",
                Arrays.asList("Hygiene Dispensers", "Mirrors", "Clean Water Supply"),
                Arrays.asList("Near Staircase S1", "Science & Humanities Staff Room"),
                "Take Staircase S1 down to Ground Floor."
        ));

        list.add(new LocationItem(
                "loc_sf_sh_staff1",
                "2F-SHS1",
                "Science & Humanities Staff Room",
                "Staff Rooms",
                "Science & Humanities",
                "2nd Floor",
                "East Wing",
                "Science and Humanities Department faculty consultation and staff workspace (East).",
                "Prof. S. K. Joshi",
                "08:30 AM - 05:30 PM",
                Arrays.asList("Faculty Cubicles", "Intercom", "Wi-Fi"),
                Arrays.asList("Girls WC", "CET 7"),
                "Take Staircase S1 down to Ground Floor."
        ));

        list.add(new LocationItem(
                "loc_sf_cet7",
                "2F-CET7",
                "CET 7",
                "Laboratory",
                "Computer Engineering",
                "2nd Floor",
                "East Wing",
                "Computer Engineering Technology advanced practical laboratory 7.",
                "Prof. D. K. Pawar",
                "08:30 AM - 05:00 PM",
                Arrays.asList("35 Workstations", "Smart Screen", "LAN"),
                Arrays.asList("Science & Humanities Staff Room", "CET 8"),
                "Take Staircase S1 down to Ground Floor."
        ));

        list.add(new LocationItem(
                "loc_sf_cet8",
                "2F-CET8",
                "CET 8",
                "Laboratory",
                "Information Technology",
                "2nd Floor",
                "East Wing",
                "Computer Engineering Technology advanced practical laboratory 8.",
                "Prof. R. T. Shinde",
                "08:30 AM - 05:00 PM",
                Arrays.asList("35 Workstations", "Full HD Projector", "LAN"),
                Arrays.asList("CET 7", "Language Lab"),
                "Take Staircase S1 down to Ground Floor."
        ));

        list.add(new LocationItem(
                "loc_sf_lang_lab",
                "2F-LL",
                "Language Lab",
                "Laboratory",
                "Science & Humanities",
                "2nd Floor",
                "Central Corridor",
                "Digital Language and Communication Skills laboratory with acoustic headsets.",
                "Prof. M. B. Patil",
                "08:30 AM - 05:00 PM",
                Arrays.asList("40 Multimedia PCs", "Noise Canceling Headsets", "Pronunciation Software"),
                Arrays.asList("CET 8", "Chemistry Lab"),
                "Take Staircase S2 down to Ground Floor."
        ));

        list.add(new LocationItem(
                "loc_sf_chem_lab",
                "2F-CHEM",
                "Chemistry Lab",
                "Laboratory",
                "Science & Humanities",
                "2nd Floor",
                "Central Corridor",
                "Engineering Chemistry experimental laboratory with safety eyewash and fume exhausts.",
                "Prof. V. S. Mane",
                "08:30 AM - 05:00 PM",
                Arrays.asList("Reagent Racks", "Fume Hoods", "Safety First Aid Kit", "Eyewash"),
                Arrays.asList("Language Lab", "Boys WC"),
                "Take Staircase S2 down to Ground Floor."
        ));

        list.add(new LocationItem(
                "loc_sf_bwc",
                "2F-BWC",
                "Boys WC",
                "Washrooms",
                "Common / General",
                "2nd Floor",
                "Central Corridor",
                "Sanitary restroom facility for male students and faculty on 2nd Floor.",
                "Sanitation Staff",
                "24/7 Campus Hours",
                Arrays.asList("Hand Dryers", "Mirror", "Exhaust Fans"),
                Arrays.asList("Chemistry Lab", "Dark Room"),
                "Take Staircase S2 down to Ground Floor."
        ));

        list.add(new LocationItem(
                "loc_sf_dark_room",
                "2F-DRK",
                "Dark Room",
                "Facilities",
                "Science & Humanities",
                "2nd Floor",
                "Central Corridor",
                "Specialized optical and photometric testing dark room.",
                "Lab Tech",
                "09:00 AM - 04:30 PM",
                Arrays.asList("Monochromatic Light Sources", "Spectrometer Stations", "Optical Benches"),
                Arrays.asList("Boys WC", "CR 21"),
                "Take Staircase S2 down to Ground Floor."
        ));

        list.add(new LocationItem(
                "loc_sf_cr21",
                "CR 21",
                "CR 21",
                "Classrooms",
                "Information Technology",
                "2nd Floor",
                "West Wing",
                "Lecture classroom 21 positioned vertically above CR 17.",
                "Prof. T. D. Gaikwad",
                "08:00 AM - 04:30 PM",
                Arrays.asList("75 Seating Capacity", "Full HD Projector", "Smart Podium", "Wi-Fi"),
                Arrays.asList("Dark Room", "CR 22"),
                "Take Staircase S3 down to Ground Floor."
        ));

        list.add(new LocationItem(
                "loc_sf_cr22",
                "CR 22",
                "CR 22",
                "Classrooms",
                "Information Technology",
                "2nd Floor",
                "West Wing",
                "Lecture classroom 22 positioned vertically above CR 18.",
                "Prof. A. V. Kulkarni",
                "08:00 AM - 04:30 PM",
                Arrays.asList("75 Seating Capacity", "Interactive Board", "PA Audio System", "Wi-Fi"),
                Arrays.asList("CR 21", "CR 23"),
                "Take Staircase S3 down to Ground Floor."
        ));

        list.add(new LocationItem(
                "loc_sf_cr23",
                "CR 23",
                "CR 23",
                "Classrooms",
                "Computer Engineering",
                "2nd Floor",
                "West Wing",
                "Lecture classroom 23 for advanced degree engineering sessions.",
                "Prof. G. R. Shinde",
                "08:00 AM - 04:30 PM",
                Arrays.asList("75 Seating Capacity", "Projector", "Podium", "Wi-Fi"),
                Arrays.asList("CR 22", "Admission Room"),
                "Take Staircase S3 down to Ground Floor."
        ));

        list.add(new LocationItem(
                "loc_sf_admission",
                "2F-ADM",
                "Admission Room",
                "Facilities",
                "Common / General",
                "2nd Floor",
                "West Wing",
                "Centralized student counseling and document verification admission center.",
                "Admission Officer",
                "09:00 AM - 05:00 PM",
                Arrays.asList("Verification Counters", "Waiting Area", "LAN Desks", "Printer / Scanner"),
                Arrays.asList("CR 23", "Science & Humanities Staff Room"),
                "Take Staircase S3 down to Ground Floor."
        ));

        list.add(new LocationItem(
                "loc_sf_sh_staff2",
                "2F-SHS2",
                "Science & Humanities Staff Room",
                "Staff Rooms",
                "Science & Humanities",
                "2nd Floor",
                "West Wing",
                "Science and Humanities Department faculty consultation and staff workspace (West).",
                "Prof. N. K. Bagul",
                "08:30 AM - 05:30 PM",
                Arrays.asList("Faculty Desks", "Reference Library", "Wi-Fi"),
                Arrays.asList("Admission Room", "Physics Lab"),
                "Take Staircase S3 down to Ground Floor."
        ));

        list.add(new LocationItem(
                "loc_sf_physics_lab",
                "2F-PHYS",
                "Physics Lab",
                "Laboratory",
                "Science & Humanities",
                "2nd Floor",
                "West Wing",
                "Engineering Physics practical laboratory equipped with spectrometers, laser sets, and optical sensors.",
                "Prof. S. A. Kadam",
                "08:30 AM - 05:00 PM",
                Arrays.asList("Laser Bench Sets", "Diffraction Gratings", "Digital Galvanometers", "Dark Storage"),
                Arrays.asList("Science & Humanities Staff Room", "Staircase S3"),
                "Take Staircase S3 down to Ground Floor."
        ));

        list.add(new LocationItem(
                "loc_sf_s1",
                "SF-S1",
                "Staircase S1",
                "Facilities",
                "Common / General",
                "2nd Floor",
                "East Wing",
                "Vertical staircase S1 providing access between 1st Floor and 2nd Floor.",
                "Campus Facility",
                "24/7 Campus Hours",
                Arrays.asList("Wide Steps", "Handrails", "Emergency Illumination"),
                Arrays.asList("Girls WC", "Science & Humanities Staff Room"),
                "Take Staircase S1 down to 1st Floor and Ground Floor East exit."
        ));

        list.add(new LocationItem(
                "loc_sf_s2",
                "SF-S2",
                "Staircase S2",
                "Facilities",
                "Common / General",
                "2nd Floor",
                "Central Wing",
                "Vertical staircase S2 providing access between 1st Floor and 2nd Floor.",
                "Campus Facility",
                "24/7 Campus Hours",
                Arrays.asList("Wide Steps", "Handrails", "Emergency Illumination"),
                Arrays.asList("Chemistry Lab", "Boys WC"),
                "Take Staircase S2 down to 1st Floor and Ground Floor Central exit."
        ));

        list.add(new LocationItem(
                "loc_sf_s3",
                "SF-S3",
                "Staircase S3",
                "Facilities",
                "Common / General",
                "2nd Floor",
                "West Wing",
                "Vertical staircase S3 providing access between 1st Floor and 2nd Floor.",
                "Campus Facility",
                "24/7 Campus Hours",
                Arrays.asList("Wide Steps", "Handrails", "Emergency Illumination"),
                Arrays.asList("Physics Lab", "Science & Humanities Staff Room"),
                "Take Staircase S3 down to 1st Floor and Ground Floor West exit."
        ));

        return list;
    }

    public static List<AlertItem> getInitialAlerts() {
        List<AlertItem> list = new ArrayList<>();

        list.add(new AlertItem(
                "alert_01",
                "Corridor Tile Repair: 2nd Floor East Wing",
                "Floor maintenance in progress near Room CET 7. Please use Staircase S2 for reaching 2nd floor safely.",
                "Maintenance",
                "Urgent",
                "15 mins ago",
                "2nd Floor East Corridor",
                true
        ));

        list.add(new AlertItem(
                "alert_02",
                "Lab Shift Notice: Operating Systems Practical",
                "Due to network maintenance in Lab 4, today's afternoon practical session is shifted to Lab 1 (GF-LAB1).",
                "Relocation",
                "Medium",
                "1 hour ago",
                "Lab 4 -> Lab 1",
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
                        "Evacuate immediately via designated staircases (Staircase S1, S2, or S3).",
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
                        "Once shaking ceases, calmly exit building via staircases.",
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
                        "First Aid kits are stationed in Lab 1, Lab 5, and Staff Rooms.",
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
