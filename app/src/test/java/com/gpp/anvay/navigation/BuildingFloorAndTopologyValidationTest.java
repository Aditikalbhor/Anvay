package com.gpp.anvay.navigation;

import com.gpp.anvay.data.LocationRepository;
import com.gpp.anvay.data.MockDataProvider;
import com.gpp.anvay.model.BuildingItem;
import com.gpp.anvay.model.LocationItem;
import com.gpp.anvay.model.position.NavigationNode;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class BuildingFloorAndTopologyValidationTest {

    private LocationRepository locationRepo;
    private NavigationGraph graph;

    @Before
    public void setUp() {
        locationRepo = LocationRepository.getInstance();
        graph = new NavigationGraph();
    }

    @Test
    public void testExactlyOneBuildingComputerIT() {
        List<BuildingItem> buildings = locationRepo.getAllBuildings();
        assertEquals(1, buildings.size());

        BuildingItem building = buildings.get(0);
        assertEquals("bldg_comp_it", building.getId());
        assertEquals("Computer/IT Building", building.getName());
    }

    @Test
    public void testExactlyThreeSupportedFloors() {
        BuildingItem building = locationRepo.getSelectedBuilding();
        assertNotNull(building);

        List<String> floors = building.getSupportedFloors();
        assertEquals(3, floors.size());
        assertTrue(floors.contains("Ground Floor"));
        assertTrue(floors.contains("1st Floor"));
        assertTrue(floors.contains("2nd Floor"));

        // Confirm 3rd Floor is NOT present
        assertFalse(floors.contains("3rd Floor"));
        assertFalse(floors.contains("Third Floor"));
        assertFalse(floors.contains("4th Floor"));
    }

    @Test
    public void testOnlyFourValidNavigationNodeTypes() {
        assertTrue(NavigationNode.isValidNodeType(NavigationNode.TYPE_ROOM_ENTRY));
        assertTrue(NavigationNode.isValidNodeType(NavigationNode.TYPE_CORRIDOR_JUNCTION));
        assertTrue(NavigationNode.isValidNodeType(NavigationNode.TYPE_STAIRCASE));
        assertTrue(NavigationNode.isValidNodeType(NavigationNode.TYPE_CHECKPOINT));

        // Rejection of EXIT / EMERGENCY_EXIT
        assertFalse(NavigationNode.isValidNodeType("EXIT"));
        assertFalse(NavigationNode.isValidNodeType("EMERGENCY_EXIT"));
        assertFalse(NavigationNode.isValidNodeType("DOOR"));
        assertFalse(NavigationNode.isValidNodeType(null));
    }

    @Test
    public void testGraphContainsZeroExitNodes() {
        List<NavigationNode> allNodes = graph.getAllNodes();
        assertFalse(allNodes.isEmpty());

        for (NavigationNode node : allNodes) {
            assertTrue("Node must be one of the 4 valid types", NavigationNode.isValidNodeType(node.getNodeType()));
            assertNotEquals("EXIT", node.getNodeType());
            assertNotEquals("EMERGENCY_EXIT", node.getNodeType());
            assertFalse(node.getNodeId().toLowerCase().contains("exit"));
        }
    }

    @Test
    public void testAllVerifiedGroundFloorLocationsExistInGraph() {
        List<LocationItem> gfLocations = locationRepo.getLocationsByFloor("Ground Floor");
        assertTrue(gfLocations.size() >= 15);

        for (LocationItem loc : gfLocations) {
            NavigationNode node = graph.getNodeByLocationId(loc.getId());
            assertNotNull("Graph must have node for " + loc.getName(), node);
            assertEquals("Ground Floor", node.getFloor());
        }
    }

    @Test
    public void testAllVerifiedFirstFloorLocationsExistInGraph() {
        List<LocationItem> ffLocations = locationRepo.getLocationsByFloor("1st Floor");
        assertTrue(ffLocations.size() >= 15);

        for (LocationItem loc : ffLocations) {
            NavigationNode node = graph.getNodeByLocationId(loc.getId());
            assertNotNull("Graph must have node for " + loc.getName(), node);
            assertEquals("1st Floor", node.getFloor());
        }
    }

    @Test
    public void testAllVerifiedSecondFloorLocationsExistInGraph() {
        List<LocationItem> sfLocations = locationRepo.getLocationsByFloor("2nd Floor");
        assertTrue(sfLocations.size() >= 15);

        for (LocationItem loc : sfLocations) {
            NavigationNode node = graph.getNodeByLocationId(loc.getId());
            assertNotNull("Graph must have node for " + loc.getName(), node);
            assertEquals("2nd Floor", node.getFloor());
        }
    }
}
