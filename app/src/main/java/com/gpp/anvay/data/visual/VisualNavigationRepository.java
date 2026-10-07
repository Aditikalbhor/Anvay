package com.gpp.anvay.data.visual;

import com.gpp.anvay.model.visual.VisualLandmark;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Repository storing photo-grounded visual landmarks for the Computer/IT Building
 * across Ground Floor, 1st Floor, and 2nd Floor.
 *
 * This repository associates topological NavigationNodes with real building photographs.
 * It does NOT calculate routes (NavigationGraph remains the routing source of truth).
 */
public class VisualNavigationRepository {

    private static VisualNavigationRepository instance;
    private final Map<String, VisualLandmark> nodeLandmarkMap;
    private final Map<String, VisualLandmark> transitionLandmarkMap;
    private final List<VisualLandmark> allLandmarks;

    private VisualNavigationRepository() {
        nodeLandmarkMap = new HashMap<>();
        transitionLandmarkMap = new HashMap<>();
        allLandmarks = new ArrayList<>();
        initializeVerifiedLandmarks();
    }

    public static synchronized VisualNavigationRepository getInstance() {
        if (instance == null) {
            instance = new VisualNavigationRepository();
        }
        return instance;
    }

    private void initializeVerifiedLandmarks() {
        // =========================================================================
        // GROUND FLOOR VISUAL LANDMARKS
        // =========================================================================

        addLandmark(new VisualLandmark(
                "vl_gf_dir", "bldg_comp_it", "Ground Floor",
                "Computer & IT Building Main Directory",
                "Departmental and floor directory signboard at main ground floor entrance.",
                "visual_ref_01", "junc_gf_central", VisualLandmark.TYPE_JUNCTION_VIEW, true
        ));

        addLandmark(new VisualLandmark(
                "vl_gf_lobby", "bldg_comp_it", "Ground Floor",
                "Ground Floor Central Entrance Lobby",
                "Main lobby corridor view with central circulation and notice boards.",
                "visual_ref_02", "junc_gf_central", VisualLandmark.TYPE_JUNCTION_VIEW, true
        ));

        addLandmark(new VisualLandmark(
                "vl_gf_cm_hod", "bldg_comp_it", "Ground Floor",
                "Computer Engineering HOD & Department Office",
                "Entrance door and department chamber of Computer Engineering.",
                "visual_ref_03", "node_gf_cm_hod_staff1", VisualLandmark.TYPE_ROOM_ENTRANCE, true
        ));

        addLandmark(new VisualLandmark(
                "vl_gf_cm_staff2", "bldg_comp_it", "Ground Floor",
                "Computer Staff Consultation Room 2",
                "Entrance to Computer Engineering faculty workstations and staff room 2.",
                "visual_ref_04", "node_gf_cm_staff2", VisualLandmark.TYPE_ROOM_ENTRANCE, true
        ));

        addLandmark(new VisualLandmark(
                "vl_gf_west_corridor", "bldg_comp_it", "Ground Floor",
                "Ground Floor West Wing Corridor",
                "Connecting corridor from central lobby toward IT Engineering labs.",
                "visual_ref_05", "junc_gf_west", VisualLandmark.TYPE_CORRIDOR_VIEW, true
        ));

        addLandmark(new VisualLandmark(
                "vl_gf_it_lab1", "bldg_comp_it", "Ground Floor",
                "IT Lab 1 Entrance",
                "Introductory programming and software development laboratory entrance.",
                "visual_ref_06", "node_gf_it_lab1", VisualLandmark.TYPE_ROOM_ENTRANCE, true
        ));

        addLandmark(new VisualLandmark(
                "vl_gf_it_lab2", "bldg_comp_it", "Ground Floor",
                "IT Lab 2 Entrance",
                "Web technologies and multimedia computing practical laboratory.",
                "visual_ref_07", "node_gf_it_lab2", VisualLandmark.TYPE_ROOM_ENTRANCE, true
        ));

        addLandmark(new VisualLandmark(
                "vl_gf_it_lab3", "bldg_comp_it", "Ground Floor",
                "IT Lab 3 Entrance",
                "Database management and cloud infrastructure computing laboratory.",
                "visual_ref_08", "node_gf_it_lab3", VisualLandmark.TYPE_ROOM_ENTRANCE, true
        ));

        addLandmark(new VisualLandmark(
                "vl_gf_it_lab4", "bldg_comp_it", "Ground Floor",
                "IT Lab 4 Entrance",
                "Mobile application and network security development laboratory.",
                "visual_ref_09", "node_gf_it_lab4", VisualLandmark.TYPE_ROOM_ENTRANCE, true
        ));

        addLandmark(new VisualLandmark(
                "vl_gf_it_hod", "bldg_comp_it", "Ground Floor",
                "IT HOD & Department Staff Room",
                "Chamber of Information Technology Head of Department.",
                "visual_ref_10", "node_gf_it_hod_staff", VisualLandmark.TYPE_ROOM_ENTRANCE, true
        ));

        addLandmark(new VisualLandmark(
                "vl_gf_tpo", "bldg_comp_it", "Ground Floor",
                "Training & Placement Office (TPO)",
                "Centralized campus recruitment, internship, and career guidance cell.",
                "visual_ref_11", "node_gf_tpo", VisualLandmark.TYPE_ROOM_ENTRANCE, true
        ));

        addLandmark(new VisualLandmark(
                "vl_gf_lab5", "bldg_comp_it", "Ground Floor",
                "Computer Lab 5 (Project & Innovation)",
                "Capstone project development and IoT experimentation laboratory.",
                "visual_ref_12", "node_gf_lab5", VisualLandmark.TYPE_ROOM_ENTRANCE, true
        ));

        addLandmark(new VisualLandmark(
                "vl_gf_s3", "bldg_comp_it", "Ground Floor",
                "Staircase S3 Ground Floor Landing",
                "West wing vertical staircase S3 connecting Ground Floor to 1st and 2nd Floors.",
                "visual_ref_13", "node_gf_s3", VisualLandmark.TYPE_STAIRCASE_VIEW, true
        ));

        addLandmark(new VisualLandmark(
                "vl_gf_s3_flight", "bldg_comp_it", "Ground Floor",
                "Staircase S3 Ascending Flight",
                "Stairwell flight ascending from Ground Floor to 1st Floor.",
                "visual_ref_14", "node_gf_s3", VisualLandmark.TYPE_FLOOR_TRANSITION, true
        ));

        addLandmark(new VisualLandmark(
                "vl_gf_east_corridor", "bldg_comp_it", "Ground Floor",
                "Ground Floor East Wing Corridor",
                "Corridor leading toward Computer Labs 1-4, Server Room, and Staircase S1.",
                "visual_ref_15", "junc_gf_east", VisualLandmark.TYPE_CORRIDOR_VIEW, true
        ));

        addLandmark(new VisualLandmark(
                "vl_gf_lab1", "bldg_comp_it", "Ground Floor",
                "Computer Lab 1 Entrance",
                "Data structures and core programming laboratory.",
                "visual_ref_16", "node_gf_lab1", VisualLandmark.TYPE_ROOM_ENTRANCE, true
        ));

        addLandmark(new VisualLandmark(
                "vl_gf_lab2", "bldg_comp_it", "Ground Floor",
                "Computer Lab 2 Entrance",
                "Object oriented programming and software engineering laboratory.",
                "visual_ref_17", "node_gf_lab2", VisualLandmark.TYPE_ROOM_ENTRANCE, true
        ));

        addLandmark(new VisualLandmark(
                "vl_gf_lab3", "bldg_comp_it", "Ground Floor",
                "Computer Lab 3 Entrance",
                "Microprocessors and digital electronics hardware laboratory.",
                "visual_ref_18", "node_gf_lab3", VisualLandmark.TYPE_ROOM_ENTRANCE, true
        ));

        addLandmark(new VisualLandmark(
                "vl_gf_lab4", "bldg_comp_it", "Ground Floor",
                "Computer Lab 4 Entrance",
                "Software systems and relational database development laboratory.",
                "visual_ref_19", "node_gf_lab4", VisualLandmark.TYPE_ROOM_ENTRANCE, true
        ));

        addLandmark(new VisualLandmark(
                "vl_gf_server", "bldg_comp_it", "Ground Floor",
                "Central Server Room",
                "Central networking racks and campus switch infrastructure center.",
                "visual_ref_20", "node_gf_server", VisualLandmark.TYPE_ROOM_ENTRANCE, true
        ));

        addLandmark(new VisualLandmark(
                "vl_gf_s1", "bldg_comp_it", "Ground Floor",
                "Staircase S1 Ground Floor Landing",
                "East wing vertical staircase S1 providing multi-floor access.",
                "visual_ref_21", "node_gf_s1", VisualLandmark.TYPE_STAIRCASE_VIEW, true
        ));

        addLandmark(new VisualLandmark(
                "vl_gf_gwc", "bldg_comp_it", "Ground Floor",
                "Girls WC Ground Floor",
                "Sanitary restroom facility located near East Wing.",
                "visual_ref_22", "node_gf_gwc", VisualLandmark.TYPE_ROOM_ENTRANCE, true
        ));

        addLandmark(new VisualLandmark(
                "vl_gf_bwc", "bldg_comp_it", "Ground Floor",
                "Boys WC Ground Floor",
                "Restroom facility located near Central Corridor.",
                "visual_ref_23", "node_gf_bwc", VisualLandmark.TYPE_ROOM_ENTRANCE, true
        ));

        addLandmark(new VisualLandmark(
                "vl_gf_s2", "bldg_comp_it", "Ground Floor",
                "Staircase S2 Central Landing",
                "Central staircase S2 positioned behind main entrance lobby.",
                "visual_ref_24", "node_gf_s2", VisualLandmark.TYPE_STAIRCASE_VIEW, true
        ));

        // =========================================================================
        // 1ST FLOOR VISUAL LANDMARKS
        // =========================================================================

        addLandmark(new VisualLandmark(
                "vl_ff_s1", "bldg_comp_it", "1st Floor",
                "Staircase S1 1st Floor East Landing",
                "Staircase S1 landing opening to 1st floor East Corridor.",
                "visual_ref_26", "node_ff_s1", VisualLandmark.TYPE_STAIRCASE_LANDING, true
        ));

        addLandmark(new VisualLandmark(
                "vl_ff_east_corridor", "bldg_comp_it", "1st Floor",
                "1st Floor East Corridor",
                "East wing corridor connecting CET Labs 1-2 and Male Staff Room.",
                "visual_ref_27", "junc_ff_east", VisualLandmark.TYPE_CORRIDOR_VIEW, true
        ));

        addLandmark(new VisualLandmark(
                "vl_ff_male_staff", "bldg_comp_it", "1st Floor",
                "Male Staff Room Entrance",
                "Faculty consultation and departmental workspace.",
                "visual_ref_28", "node_ff_male_staff", VisualLandmark.TYPE_ROOM_ENTRANCE, true
        ));

        addLandmark(new VisualLandmark(
                "vl_ff_server", "bldg_comp_it", "1st Floor",
                "1st Floor Network Server Room",
                "Network distribution hub and switch rack room for 1st floor.",
                "visual_ref_29", "node_ff_server", VisualLandmark.TYPE_ROOM_ENTRANCE, true
        ));

        addLandmark(new VisualLandmark(
                "vl_ff_cet1", "bldg_comp_it", "1st Floor",
                "CET Lab 1 Entrance",
                "Computer Engineering Technology practical laboratory 1.",
                "visual_ref_30", "node_ff_cet_lab1", VisualLandmark.TYPE_ROOM_ENTRANCE, true
        ));

        addLandmark(new VisualLandmark(
                "vl_ff_cet2", "bldg_comp_it", "1st Floor",
                "CET Lab 2 Entrance",
                "Computer Engineering Technology practical laboratory 2.",
                "visual_ref_31", "node_ff_cet_lab2", VisualLandmark.TYPE_ROOM_ENTRANCE, true
        ));

        addLandmark(new VisualLandmark(
                "vl_ff_central_corridor", "bldg_comp_it", "1st Floor",
                "1st Floor Central Corridor",
                "Central corridor along CET Labs 3-6 and drinking water station.",
                "visual_ref_32", "junc_ff_central", VisualLandmark.TYPE_CORRIDOR_VIEW, true
        ));

        addLandmark(new VisualLandmark(
                "vl_ff_cet3", "bldg_comp_it", "1st Floor",
                "CET Lab 3 Entrance",
                "Computer Engineering Technology practical laboratory 3.",
                "visual_ref_33", "node_ff_cet_lab3", VisualLandmark.TYPE_ROOM_ENTRANCE, true
        ));

        addLandmark(new VisualLandmark(
                "vl_ff_cet4", "bldg_comp_it", "1st Floor",
                "CET Lab 4 Entrance",
                "Computer Engineering Technology practical laboratory 4.",
                "visual_ref_34", "node_ff_cet_lab4", VisualLandmark.TYPE_ROOM_ENTRANCE, true
        ));

        addLandmark(new VisualLandmark(
                "vl_ff_s2", "bldg_comp_it", "1st Floor",
                "Staircase S2 1st Floor Central Landing",
                "Central staircase landing connecting Ground, 1st, and 2nd Floors.",
                "visual_ref_35", "node_ff_s2", VisualLandmark.TYPE_STAIRCASE_LANDING, true
        ));

        addLandmark(new VisualLandmark(
                "vl_ff_cet5", "bldg_comp_it", "1st Floor",
                "CET Lab 5 Entrance",
                "Computer Engineering Technology practical laboratory 5.",
                "visual_ref_36", "node_ff_cet_lab5", VisualLandmark.TYPE_ROOM_ENTRANCE, true
        ));

        addLandmark(new VisualLandmark(
                "vl_ff_cet6", "bldg_comp_it", "1st Floor",
                "CET Lab 6 Entrance",
                "Computer Engineering Technology practical laboratory 6.",
                "visual_ref_37", "node_ff_cet_lab6", VisualLandmark.TYPE_ROOM_ENTRANCE, true
        ));

        addLandmark(new VisualLandmark(
                "vl_ff_water_cooler", "bldg_comp_it", "1st Floor",
                "1st Floor Drinking Water Station",
                "RO purified drinking water facility.",
                "visual_ref_38", "node_ff_water_cooler", VisualLandmark.TYPE_ROOM_ENTRANCE, true
        ));

        addLandmark(new VisualLandmark(
                "vl_ff_bwc", "bldg_comp_it", "1st Floor",
                "Boys WC 1st Floor",
                "Male restroom facility on 1st Floor.",
                "visual_ref_39", "node_ff_bwc", VisualLandmark.TYPE_ROOM_ENTRANCE, true
        ));

        addLandmark(new VisualLandmark(
                "vl_ff_west_junction", "bldg_comp_it", "1st Floor",
                "1st Floor West Wing Corridor Junction",
                "Main junction leading to lecture classrooms CR 14 through CR 20.",
                "visual_ref_40", "junc_ff_west", VisualLandmark.TYPE_CORRIDOR_VIEW, true
        ));

        addLandmark(new VisualLandmark(
                "vl_ff_cr20", "bldg_comp_it", "1st Floor",
                "Classroom CR 20 Entrance",
                "Lecture classroom 20 with smart projector and audio podium.",
                "visual_ref_41", "node_ff_cr20", VisualLandmark.TYPE_ROOM_ENTRANCE, true
        ));

        addLandmark(new VisualLandmark(
                "vl_ff_cr19", "bldg_comp_it", "1st Floor",
                "Classroom CR 19 Entrance",
                "Lecture classroom 19 for engineering lectures.",
                "visual_ref_42", "node_ff_cr19", VisualLandmark.TYPE_ROOM_ENTRANCE, true
        ));

        addLandmark(new VisualLandmark(
                "vl_ff_tutorial", "bldg_comp_it", "1st Floor",
                "Tutorial Room Entrance",
                "Small group discussion and remedial classroom.",
                "visual_ref_43", "node_ff_tutorial", VisualLandmark.TYPE_ROOM_ENTRANCE, true
        ));

        addLandmark(new VisualLandmark(
                "vl_ff_cr18", "bldg_comp_it", "1st Floor",
                "Classroom CR 18 Entrance",
                "Lecture classroom 18 (positioned adjacent to Tutorial Room).",
                "visual_ref_44", "node_ff_cr18", VisualLandmark.TYPE_ROOM_ENTRANCE, true
        ));

        addLandmark(new VisualLandmark(
                "vl_ff_cr17", "bldg_comp_it", "1st Floor",
                "Classroom CR 17 Entrance",
                "Lecture classroom 17 (positioned directly beneath CR 21).",
                "visual_ref_45", "node_ff_cr17", VisualLandmark.TYPE_ROOM_ENTRANCE, true
        ));

        addLandmark(new VisualLandmark(
                "vl_ff_cr16", "bldg_comp_it", "1st Floor",
                "Classroom CR 16 Entrance",
                "Lecture classroom 16 with interactive digital board.",
                "visual_ref_46", "node_ff_cr16", VisualLandmark.TYPE_ROOM_ENTRANCE, true
        ));

        addLandmark(new VisualLandmark(
                "vl_ff_cr15", "bldg_comp_it", "1st Floor",
                "Classroom CR 15 Entrance",
                "Lecture classroom 15.",
                "visual_ref_47", "node_ff_cr15", VisualLandmark.TYPE_ROOM_ENTRANCE, true
        ));

        addLandmark(new VisualLandmark(
                "vl_ff_cr14", "bldg_comp_it", "1st Floor",
                "Classroom CR 14 Entrance",
                "Lecture classroom 14 positioned at west corridor end.",
                "visual_ref_48", "node_ff_cr14", VisualLandmark.TYPE_ROOM_ENTRANCE, true
        ));

        addLandmark(new VisualLandmark(
                "vl_ff_s3", "bldg_comp_it", "1st Floor",
                "Staircase S3 1st Floor West Landing",
                "Staircase S3 landing between 1st Floor and 2nd Floor.",
                "visual_ref_49", "node_ff_s3", VisualLandmark.TYPE_STAIRCASE_LANDING, true
        ));

        addLandmark(new VisualLandmark(
                "vl_ff_west_corridor", "bldg_comp_it", "1st Floor",
                "1st Floor West Classrooms Corridor",
                "Long straight corridor passing CR 14 through CR 20.",
                "visual_ref_50", "junc_ff_west", VisualLandmark.TYPE_CORRIDOR_VIEW, true
        ));

        addLandmark(new VisualLandmark(
                "vl_ff_gwc", "bldg_comp_it", "1st Floor",
                "Girls WC 1st Floor",
                "Female sanitary restroom on 1st Floor East Wing.",
                "visual_ref_51", "node_ff_gwc", VisualLandmark.TYPE_ROOM_ENTRANCE, true
        ));

        addLandmark(new VisualLandmark(
                "vl_ff_s3_flight", "bldg_comp_it", "1st Floor",
                "Staircase S3 Flight to 2nd Floor",
                "Stairwell flight ascending from 1st Floor to 2nd Floor.",
                "visual_ref_52", "node_ff_s3", VisualLandmark.TYPE_FLOOR_TRANSITION, true
        ));

        addLandmark(new VisualLandmark(
                "vl_ff_s1_flight", "bldg_comp_it", "1st Floor",
                "Staircase S1 Flight to 2nd Floor",
                "East wing stairwell ascending to 2nd Floor.",
                "visual_ref_53", "node_ff_s1", VisualLandmark.TYPE_FLOOR_TRANSITION, true
        ));

        // =========================================================================
        // 2ND FLOOR VISUAL LANDMARKS
        // =========================================================================

        addLandmark(new VisualLandmark(
                "vl_sf_s1", "bldg_comp_it", "2nd Floor",
                "Staircase S1 2nd Floor East Landing",
                "Top landing of Staircase S1 opening to 2nd Floor East Wing.",
                "visual_ref_55", "node_sf_s1", VisualLandmark.TYPE_STAIRCASE_LANDING, true
        ));

        addLandmark(new VisualLandmark(
                "vl_sf_east_corridor", "bldg_comp_it", "2nd Floor",
                "2nd Floor East Wing Corridor",
                "Corridor leading to Science & Humanities Staff Room and CET 7-8.",
                "visual_ref_56", "junc_sf_east", VisualLandmark.TYPE_CORRIDOR_VIEW, true
        ));

        addLandmark(new VisualLandmark(
                "vl_sf_sh_staff1", "bldg_comp_it", "2nd Floor",
                "Science & Humanities Staff Room (East)",
                "Departmental faculty chambers for Science & Humanities (East).",
                "visual_ref_57", "node_sf_sh_staff1", VisualLandmark.TYPE_ROOM_ENTRANCE, true
        ));

        addLandmark(new VisualLandmark(
                "vl_sf_cet7", "bldg_comp_it", "2nd Floor",
                "CET 7 Advanced Laboratory Entrance",
                "Computer Engineering Technology advanced practical laboratory 7.",
                "visual_ref_58", "node_sf_cet7", VisualLandmark.TYPE_ROOM_ENTRANCE, true
        ));

        addLandmark(new VisualLandmark(
                "vl_sf_cet8", "bldg_comp_it", "2nd Floor",
                "CET 8 Advanced Laboratory Entrance",
                "Computer Engineering Technology practical laboratory 8.",
                "visual_ref_59", "node_sf_cet8", VisualLandmark.TYPE_ROOM_ENTRANCE, true
        ));

        addLandmark(new VisualLandmark(
                "vl_sf_lang_lab", "bldg_comp_it", "2nd Floor",
                "Language & Communication Lab Entrance",
                "Digital communication and multimedia phonetics laboratory.",
                "visual_ref_61", "node_sf_lang_lab", VisualLandmark.TYPE_ROOM_ENTRANCE, true
        ));

        addLandmark(new VisualLandmark(
                "vl_sf_chem_lab", "bldg_comp_it", "2nd Floor",
                "Engineering Chemistry Laboratory Entrance",
                "Chemistry experimental laboratory with safety eyewash and fume exhausts.",
                "visual_ref_62", "node_sf_chem_lab", VisualLandmark.TYPE_ROOM_ENTRANCE, true
        ));

        addLandmark(new VisualLandmark(
                "vl_sf_central_corridor", "bldg_comp_it", "2nd Floor",
                "2nd Floor Central Corridor Landing",
                "Central corridor connecting Chemistry Lab, Dark Room, and Staircase S2.",
                "visual_ref_63", "junc_sf_central", VisualLandmark.TYPE_CORRIDOR_VIEW, true
        ));

        addLandmark(new VisualLandmark(
                "vl_sf_s2", "bldg_comp_it", "2nd Floor",
                "Staircase S2 2nd Floor Central Landing",
                "Top landing of central staircase S2.",
                "visual_ref_64", "node_sf_s2", VisualLandmark.TYPE_STAIRCASE_LANDING, true
        ));

        addLandmark(new VisualLandmark(
                "vl_sf_dark_room", "bldg_comp_it", "2nd Floor",
                "Dark Room (Photometry & Optics)",
                "Optical spectroscopy and photometric dark room facility.",
                "visual_ref_66", "node_sf_dark_room", VisualLandmark.TYPE_ROOM_ENTRANCE, true
        ));

        addLandmark(new VisualLandmark(
                "vl_sf_bwc", "bldg_comp_it", "2nd Floor",
                "Boys WC 2nd Floor",
                "Male restroom facility on 2nd Floor.",
                "visual_ref_67", "node_sf_bwc", VisualLandmark.TYPE_ROOM_ENTRANCE, true
        ));

        addLandmark(new VisualLandmark(
                "vl_sf_cr21", "bldg_comp_it", "2nd Floor",
                "Classroom CR 21 Entrance",
                "Lecture classroom 21 (positioned vertically above CR 17).",
                "visual_ref_68", "node_sf_cr21", VisualLandmark.TYPE_ROOM_ENTRANCE, true
        ));

        addLandmark(new VisualLandmark(
                "vl_sf_cr22", "bldg_comp_it", "2nd Floor",
                "Classroom CR 22 Entrance",
                "Lecture classroom 22 (positioned vertically above CR 18).",
                "visual_ref_69", "node_sf_cr22", VisualLandmark.TYPE_ROOM_ENTRANCE, true
        ));

        addLandmark(new VisualLandmark(
                "vl_sf_cr23", "bldg_comp_it", "2nd Floor",
                "Classroom CR 23 Entrance",
                "Advanced lecture classroom 23.",
                "visual_ref_70", "node_sf_cr23", VisualLandmark.TYPE_ROOM_ENTRANCE, true
        ));

        addLandmark(new VisualLandmark(
                "vl_sf_admission", "bldg_comp_it", "2nd Floor",
                "Admission & Counseling Center Entrance",
                "Student admission counseling and document verification center.",
                "visual_ref_71", "node_sf_admission", VisualLandmark.TYPE_ROOM_ENTRANCE, true
        ));

        addLandmark(new VisualLandmark(
                "vl_sf_sh_staff2", "bldg_comp_it", "2nd Floor",
                "Science & Humanities Staff Room (West)",
                "Departmental faculty chambers for Science & Humanities (West).",
                "visual_ref_72", "node_sf_sh_staff2", VisualLandmark.TYPE_ROOM_ENTRANCE, true
        ));

        addLandmark(new VisualLandmark(
                "vl_sf_physics_lab", "bldg_comp_it", "2nd Floor",
                "Engineering Physics Laboratory Entrance",
                "Physics practical laboratory with spectrometers and optical benches.",
                "visual_ref_73", "node_sf_physics_lab", VisualLandmark.TYPE_ROOM_ENTRANCE, true
        ));

        addLandmark(new VisualLandmark(
                "vl_sf_s3", "bldg_comp_it", "2nd Floor",
                "Staircase S3 2nd Floor West Landing",
                "Top landing of Staircase S3 on 2nd Floor West Wing.",
                "visual_ref_74", "node_sf_s3", VisualLandmark.TYPE_STAIRCASE_LANDING, true
        ));

        addLandmark(new VisualLandmark(
                "vl_sf_west_corridor", "bldg_comp_it", "2nd Floor",
                "2nd Floor West Classrooms Corridor",
                "West corridor passing CR 21, CR 22, CR 23, and Physics Lab.",
                "visual_ref_75", "junc_sf_west", VisualLandmark.TYPE_CORRIDOR_VIEW, true
        ));

        addLandmark(new VisualLandmark(
                "vl_sf_gwc", "bldg_comp_it", "2nd Floor",
                "Girls WC 2nd Floor",
                "Female sanitary restroom on 2nd Floor East Wing.",
                "visual_ref_76", "node_sf_gwc", VisualLandmark.TYPE_ROOM_ENTRANCE, true
        ));

        // =========================================================================
        // TRANSITION LANDMARKS (FLOOR TO FLOOR VIA S1, S2, S3)
        // =========================================================================

        addTransitionLandmark("node_gf_s3", "node_ff_s3", new VisualLandmark(
                "vl_trans_gf_ff_s3", "bldg_comp_it", "Ground Floor -> 1st Floor",
                "Ascend Staircase S3 to 1st Floor",
                "Take Staircase S3 from Ground Floor up to 1st Floor West Landing.",
                "visual_ref_14", "node_ff_s3", VisualLandmark.TYPE_FLOOR_TRANSITION, true
        ));

        addTransitionLandmark("node_ff_s3", "node_sf_s3", new VisualLandmark(
                "vl_trans_ff_sf_s3", "bldg_comp_it", "1st Floor -> 2nd Floor",
                "Ascend Staircase S3 to 2nd Floor",
                "Take Staircase S3 from 1st Floor up to 2nd Floor West Landing.",
                "visual_ref_52", "node_sf_s3", VisualLandmark.TYPE_FLOOR_TRANSITION, true
        ));

        addTransitionLandmark("node_gf_s1", "node_ff_s1", new VisualLandmark(
                "vl_trans_gf_ff_s1", "bldg_comp_it", "Ground Floor -> 1st Floor",
                "Ascend Staircase S1 to 1st Floor",
                "Take Staircase S1 from Ground Floor up to 1st Floor East Landing.",
                "visual_ref_21", "node_ff_s1", VisualLandmark.TYPE_FLOOR_TRANSITION, true
        ));

        addTransitionLandmark("node_ff_s1", "node_sf_s1", new VisualLandmark(
                "vl_trans_ff_sf_s1", "bldg_comp_it", "1st Floor -> 2nd Floor",
                "Ascend Staircase S1 to 2nd Floor",
                "Take Staircase S1 from 1st Floor up to 2nd Floor East Landing.",
                "visual_ref_53", "node_sf_s1", VisualLandmark.TYPE_FLOOR_TRANSITION, true
        ));

        addTransitionLandmark("node_gf_s2", "node_ff_s2", new VisualLandmark(
                "vl_trans_gf_ff_s2", "bldg_comp_it", "Ground Floor -> 1st Floor",
                "Ascend Staircase S2 to 1st Floor",
                "Take Central Staircase S2 from Ground Floor up to 1st Floor Central Landing.",
                "visual_ref_24", "node_ff_s2", VisualLandmark.TYPE_FLOOR_TRANSITION, true
        ));

        addTransitionLandmark("node_ff_s2", "node_sf_s2", new VisualLandmark(
                "vl_trans_ff_sf_s2", "bldg_comp_it", "1st Floor -> 2nd Floor",
                "Ascend Staircase S2 to 2nd Floor",
                "Take Central Staircase S2 from 1st Floor up to 2nd Floor Central Landing.",
                "visual_ref_35", "node_sf_s2", VisualLandmark.TYPE_FLOOR_TRANSITION, true
        ));

        addTransitionLandmark("node_sf_s3", "node_ff_s3", new VisualLandmark(
                "vl_trans_sf_ff_s3", "bldg_comp_it", "2nd Floor -> 1st Floor",
                "Descend Staircase S3 to 1st Floor",
                "Take Staircase S3 down to 1st Floor West Landing.",
                "visual_ref_78", "node_ff_s3", VisualLandmark.TYPE_FLOOR_TRANSITION, true
        ));

        addTransitionLandmark("node_ff_s3", "node_gf_s3", new VisualLandmark(
                "vl_trans_ff_gf_s3", "bldg_comp_it", "1st Floor -> Ground Floor",
                "Descend Staircase S3 to Ground Floor",
                "Take Staircase S3 down to Ground Floor West Corridor.",
                "visual_ref_13", "node_gf_s3", VisualLandmark.TYPE_FLOOR_TRANSITION, true
        ));

        addTransitionLandmark("node_sf_s2", "node_ff_s2", new VisualLandmark(
                "vl_trans_sf_ff_s2", "bldg_comp_it", "2nd Floor -> 1st Floor",
                "Descend Staircase S2 to 1st Floor",
                "Take Central Staircase S2 down to 1st Floor Central Landing.",
                "visual_ref_77", "node_ff_s2", VisualLandmark.TYPE_FLOOR_TRANSITION, true
        ));

        addTransitionLandmark("node_ff_s2", "node_gf_s2", new VisualLandmark(
                "vl_trans_ff_gf_s2", "bldg_comp_it", "1st Floor -> Ground Floor",
                "Descend Staircase S2 to Ground Floor",
                "Take Central Staircase S2 down to Ground Floor Central Lobby.",
                "visual_ref_24", "node_gf_s2", VisualLandmark.TYPE_FLOOR_TRANSITION, true
        ));

        addTransitionLandmark("node_sf_s1", "node_ff_s1", new VisualLandmark(
                "vl_trans_sf_ff_s1", "bldg_comp_it", "2nd Floor -> 1st Floor",
                "Descend Staircase S1 to 1st Floor",
                "Take Staircase S1 down to 1st Floor East Landing.",
                "visual_ref_26", "node_ff_s1", VisualLandmark.TYPE_FLOOR_TRANSITION, true
        ));

        addTransitionLandmark("node_ff_s1", "node_gf_s1", new VisualLandmark(
                "vl_trans_ff_gf_s1", "bldg_comp_it", "1st Floor -> Ground Floor",
                "Descend Staircase S1 to Ground Floor",
                "Take Staircase S1 down to Ground Floor East Corridor.",
                "visual_ref_21", "node_gf_s1", VisualLandmark.TYPE_FLOOR_TRANSITION, true
        ));
    }

    public void addLandmark(VisualLandmark landmark) {
        if (landmark != null && landmark.getId() != null) {
            allLandmarks.add(landmark);
            if (landmark.getNodeId() != null) {
                nodeLandmarkMap.put(landmark.getNodeId(), landmark);
            }
        }
    }

    public void addTransitionLandmark(String fromNodeId, String toNodeId, VisualLandmark landmark) {
        if (fromNodeId != null && toNodeId != null && landmark != null) {
            String key = fromNodeId + "->" + toNodeId;
            transitionLandmarkMap.put(key, landmark);
            allLandmarks.add(landmark);
        }
    }

    public VisualLandmark getLandmarkForNode(String nodeId) {
        if (nodeId == null) return null;
        return nodeLandmarkMap.get(nodeId);
    }

    public VisualLandmark getLandmarkForTransition(String fromNodeId, String toNodeId) {
        if (fromNodeId == null || toNodeId == null) return null;
        return transitionLandmarkMap.get(fromNodeId + "->" + toNodeId);
    }

    public List<VisualLandmark> getLandmarksByFloor(String floor) {
        List<VisualLandmark> list = new ArrayList<>();
        if (floor == null) return list;
        for (VisualLandmark vl : allLandmarks) {
            if (floor.equalsIgnoreCase(vl.getFloor())) {
                list.add(vl);
            }
        }
        return list;
    }

    public List<VisualLandmark> getAllLandmarks() {
        return new ArrayList<>(allLandmarks);
    }
}
