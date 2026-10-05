package com.gpp.anvay.navigation;

import com.gpp.anvay.data.MockDataProvider;
import com.gpp.anvay.model.LocationItem;
import com.gpp.anvay.model.position.NavigationNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Topological navigation graph representing the Computer/IT Building across
 * Ground Floor, 1st Floor, and 2nd Floor.
 *
 * Supported node types:
 * - ROOM_ENTRY
 * - CORRIDOR_JUNCTION
 * - STAIRCASE
 * - CHECKPOINT
 *
 * Staircase mappings:
 * GF-S1 <-> FF-S1 <-> SF-S1
 * GF-S2 <-> FF-S2 <-> SF-S2
 * GF-S3 <-> FF-S3 <-> SF-S3
 */
public class NavigationGraph {

    public static final String BUILDING_ID = "bldg_comp_it";
    public static final String FLOOR_GROUND = "Ground Floor";
    public static final String FLOOR_FIRST = "1st Floor";
    public static final String FLOOR_SECOND = "2nd Floor";

    private final Map<String, NavigationNode> nodeMap;
    private final Map<String, String> locationToNodeMap;

    public NavigationGraph() {
        this.nodeMap = new HashMap<>();
        this.locationToNodeMap = new HashMap<>();
        buildGraph();
    }

    private void buildGraph() {
        nodeMap.clear();
        locationToNodeMap.clear();

        // 1. Build Room Entry Nodes for all locations
        List<LocationItem> locations = MockDataProvider.getInitialLocations();
        for (LocationItem loc : locations) {
            String nodeId = "node_" + loc.getId().replace("loc_", "");
            String floor = loc.getFloor() != null ? loc.getFloor() : FLOOR_GROUND;

            String nodeType = NavigationNode.TYPE_ROOM_ENTRY;
            if (loc.getName().toLowerCase().contains("staircase") || loc.getId().contains("_s1") ||
                    loc.getId().contains("_s2") || loc.getId().contains("_s3")) {
                nodeType = NavigationNode.TYPE_STAIRCASE;
            }

            NavigationNode node = new NavigationNode(nodeId, BUILDING_ID, floor, loc.getId(), nodeType);
            addNode(node);
            locationToNodeMap.put(loc.getId(), nodeId);
        }

        // Support legacy IDs mapping to nodes
        locationToNodeMap.put("loc_g_01", "node_gf_lab1");
        locationToNodeMap.put("loc_g_02", "node_gf_lab2");
        locationToNodeMap.put("loc_1_101", "node_ff_cet_lab1");
        locationToNodeMap.put("loc_1_102", "node_ff_cet_lab2");
        locationToNodeMap.put("loc_1_103", "node_ff_cet_lab3");
        locationToNodeMap.put("loc_1_104", "node_ff_cet_lab4");
        locationToNodeMap.put("loc_1_105", "node_ff_male_staff");
        locationToNodeMap.put("loc_2_201", "node_sf_cr21");
        locationToNodeMap.put("loc_2_203", "node_sf_cet7");
        locationToNodeMap.put("loc_2_204", "node_sf_cet8");
        locationToNodeMap.put("loc_2_205", "node_sf_lang_lab");

        // 2. Build Corridor Junction Nodes for topological routing

        // Ground Floor Junctions
        addNode(new NavigationNode("junc_gf_east", BUILDING_ID, FLOOR_GROUND, null, NavigationNode.TYPE_CORRIDOR_JUNCTION));
        addNode(new NavigationNode("junc_gf_central", BUILDING_ID, FLOOR_GROUND, null, NavigationNode.TYPE_CORRIDOR_JUNCTION));
        addNode(new NavigationNode("junc_gf_west", BUILDING_ID, FLOOR_GROUND, null, NavigationNode.TYPE_CORRIDOR_JUNCTION));

        // 1st Floor Junctions
        addNode(new NavigationNode("junc_ff_east", BUILDING_ID, FLOOR_FIRST, null, NavigationNode.TYPE_CORRIDOR_JUNCTION));
        addNode(new NavigationNode("junc_ff_central", BUILDING_ID, FLOOR_FIRST, null, NavigationNode.TYPE_CORRIDOR_JUNCTION));
        addNode(new NavigationNode("junc_ff_west", BUILDING_ID, FLOOR_FIRST, null, NavigationNode.TYPE_CORRIDOR_JUNCTION));

        // 2nd Floor Junctions
        addNode(new NavigationNode("junc_sf_east", BUILDING_ID, FLOOR_SECOND, null, NavigationNode.TYPE_CORRIDOR_JUNCTION));
        addNode(new NavigationNode("junc_sf_central", BUILDING_ID, FLOOR_SECOND, null, NavigationNode.TYPE_CORRIDOR_JUNCTION));
        addNode(new NavigationNode("junc_sf_west", BUILDING_ID, FLOOR_SECOND, null, NavigationNode.TYPE_CORRIDOR_JUNCTION));

        // 3. Connect Ground Floor topology
        connectBidirectional("node_gf_s1", "junc_gf_east");
        connectBidirectional("node_gf_gwc", "junc_gf_east");
        connectBidirectional("node_gf_server", "junc_gf_east");
        connectBidirectional("node_gf_lab1", "junc_gf_east");
        connectBidirectional("node_gf_lab2", "junc_gf_east");

        connectBidirectional("junc_gf_east", "junc_gf_central");

        connectBidirectional("node_gf_s2", "junc_gf_central");
        connectBidirectional("node_gf_lab3", "junc_gf_central");
        connectBidirectional("node_gf_lab4", "junc_gf_central");
        connectBidirectional("node_gf_bwc", "junc_gf_central");
        connectBidirectional("node_gf_cm_staff2", "junc_gf_central");
        connectBidirectional("node_gf_cm_hod_staff1", "junc_gf_central");
        connectBidirectional("node_gf_it_hod_staff", "junc_gf_central");

        connectBidirectional("junc_gf_central", "junc_gf_west");

        connectBidirectional("node_gf_s3", "junc_gf_west");
        connectBidirectional("node_gf_it_lab1", "junc_gf_west");
        connectBidirectional("node_gf_it_lab2", "junc_gf_west");
        connectBidirectional("node_gf_it_lab3", "junc_gf_west");
        connectBidirectional("node_gf_it_lab4", "junc_gf_west");
        connectBidirectional("node_gf_tpo", "junc_gf_west");
        connectBidirectional("node_gf_lab5", "junc_gf_west");

        // 4. Connect 1st Floor topology
        connectBidirectional("node_ff_s1", "junc_ff_east");
        connectBidirectional("node_ff_gwc", "junc_ff_east");
        connectBidirectional("node_ff_male_staff", "junc_ff_east");
        connectBidirectional("node_ff_server", "junc_ff_east");
        connectBidirectional("node_ff_cet_lab1", "junc_ff_east");
        connectBidirectional("node_ff_cet_lab2", "junc_ff_east");

        connectBidirectional("junc_ff_east", "junc_ff_central");

        connectBidirectional("node_ff_s2", "junc_ff_central");
        connectBidirectional("node_ff_cet_lab3", "junc_ff_central");
        connectBidirectional("node_ff_cet_lab4", "junc_ff_central");
        connectBidirectional("node_ff_cet_lab5", "junc_ff_central");
        connectBidirectional("node_ff_cet_lab6", "junc_ff_central");
        connectBidirectional("node_ff_water_cooler", "junc_ff_central");
        connectBidirectional("node_ff_bwc", "junc_ff_central");

        connectBidirectional("junc_ff_central", "junc_ff_west");

        connectBidirectional("node_ff_s3", "junc_ff_west");
        connectBidirectional("node_ff_cr20", "junc_ff_west");
        connectBidirectional("node_ff_cr19", "junc_ff_west");
        connectBidirectional("node_ff_tutorial", "junc_ff_west");
        connectBidirectional("node_ff_cr18", "junc_ff_west"); // Corrected sequence
        connectBidirectional("node_ff_cr17", "junc_ff_west"); // Corrected sequence
        connectBidirectional("node_ff_cr16", "junc_ff_west");
        connectBidirectional("node_ff_cr15", "junc_ff_west");
        connectBidirectional("node_ff_cr14", "junc_ff_west");

        // 5. Connect 2nd Floor topology
        connectBidirectional("node_sf_s1", "junc_sf_east");
        connectBidirectional("node_sf_gwc", "junc_sf_east");
        connectBidirectional("node_sf_sh_staff1", "junc_sf_east");
        connectBidirectional("node_sf_cet7", "junc_sf_east");
        connectBidirectional("node_sf_cet8", "junc_sf_east");

        connectBidirectional("junc_sf_east", "junc_sf_central");

        connectBidirectional("node_sf_s2", "junc_sf_central");
        connectBidirectional("node_sf_lang_lab", "junc_sf_central");
        connectBidirectional("node_sf_chem_lab", "junc_sf_central");
        connectBidirectional("node_sf_bwc", "junc_sf_central");
        connectBidirectional("node_sf_dark_room", "junc_sf_central");

        connectBidirectional("junc_sf_central", "junc_sf_west");

        connectBidirectional("node_sf_s3", "junc_sf_west");
        connectBidirectional("node_sf_cr21", "junc_sf_west"); // Above CR 17
        connectBidirectional("node_sf_cr22", "junc_sf_west"); // Above CR 18
        connectBidirectional("node_sf_cr23", "junc_sf_west");
        connectBidirectional("node_sf_admission", "junc_sf_west");
        connectBidirectional("node_sf_sh_staff2", "junc_sf_west");
        connectBidirectional("node_sf_physics_lab", "junc_sf_west");

        // 6. Connect Staircases strictly across adjacent floors
        // Staircase S1: GF-S1 <-> FF-S1 <-> SF-S1
        connectBidirectional("node_gf_s1", "node_ff_s1");
        connectBidirectional("node_ff_s1", "node_sf_s1");

        // Staircase S2: GF-S2 <-> FF-S2 <-> SF-S2
        connectBidirectional("node_gf_s2", "node_ff_s2");
        connectBidirectional("node_ff_s2", "node_sf_s2");

        // Staircase S3: GF-S3 <-> FF-S3 <-> SF-S3
        connectBidirectional("node_gf_s3", "node_ff_s3");
        connectBidirectional("node_ff_s3", "node_sf_s3");
    }

    public void addNode(NavigationNode node) {
        if (node != null && node.getNodeId() != null) {
            nodeMap.put(node.getNodeId(), node);
        }
    }

    public void connectBidirectional(String nodeIdA, String nodeIdB) {
        NavigationNode nodeA = nodeMap.get(nodeIdA);
        NavigationNode nodeB = nodeMap.get(nodeIdB);
        if (nodeA != null && nodeB != null) {
            nodeA.addConnectedNode(nodeIdB);
            nodeB.addConnectedNode(nodeIdA);
        }
    }

    public NavigationNode getNode(String nodeId) {
        if (nodeId == null) return null;
        return nodeMap.get(nodeId);
    }

    public NavigationNode getNodeByLocationId(String locationId) {
        if (locationId == null) return null;
        String nodeId = locationToNodeMap.get(locationId);
        if (nodeId != null) {
            return nodeMap.get(nodeId);
        }
        // Direct matching if locationId is actually a nodeId
        return nodeMap.get(locationId);
    }

    public List<NavigationNode> getAllNodes() {
        return new ArrayList<>(nodeMap.values());
    }

    public List<NavigationNode> getNodesByFloor(String floor) {
        List<NavigationNode> list = new ArrayList<>();
        if (floor == null) return list;
        for (NavigationNode n : nodeMap.values()) {
            if (floor.equalsIgnoreCase(n.getFloor())) {
                list.add(n);
            }
        }
        return list;
    }

    public List<String> getNeighbors(String nodeId) {
        NavigationNode node = nodeMap.get(nodeId);
        if (node != null) {
            return node.getConnectedNodeIds();
        }
        return Collections.emptyList();
    }
}
