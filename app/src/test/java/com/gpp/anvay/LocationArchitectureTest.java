package com.gpp.anvay;

import com.gpp.anvay.data.LocationRepository;
import com.gpp.anvay.data.MockDataProvider;
import com.gpp.anvay.model.BuildingItem;
import com.gpp.anvay.model.LocationItem;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class LocationArchitectureTest {

    @Test
    public void testInitialBuildingConfiguration() {
        List<BuildingItem> buildings = MockDataProvider.getInitialBuildings();
        assertNotNull("Buildings list should not be null", buildings);
        assertEquals("There should be exactly 1 building initially (Computer/IT Building)", 1, buildings.size());

        BuildingItem compIt = buildings.get(0);
        assertEquals("bldg_comp_it", compIt.getId());
        assertEquals("Computer/IT Building", compIt.getName());
        assertTrue("Computer/IT Building should be default", compIt.isDefault());
        assertEquals(4, compIt.getSupportedFloors().size());
        assertTrue(compIt.getSupportedFloors().contains("Ground Floor"));
        assertTrue(compIt.getSupportedFloors().contains("1st Floor"));
        assertTrue(compIt.getSupportedFloors().contains("2nd Floor"));
        assertTrue(compIt.getSupportedFloors().contains("3rd Floor"));
    }

    @Test
    public void testLocationItemDefaultsAndBackwardCompatibility() {
        LocationItem item = new LocationItem();
        assertEquals("bldg_comp_it", item.getBuildingId());
        assertEquals("Computer/IT Building", item.getBuildingName());

        LocationItem customItem = new LocationItem(
                "loc_test", "101", "Test Room", "Classrooms",
                "Computer Engineering", "1st Floor", "East Wing",
                "Description", "In Charge", "Hours", null, null, "Exit"
        );
        assertEquals("bldg_comp_it", customItem.getBuildingId());
        assertEquals("Computer/IT Building", customItem.getBuildingName());
    }

    @Test
    public void testLocationRepositoryBuildingQueries() {
        LocationRepository repo = LocationRepository.getInstance();
        assertNotNull("Repository instance should not be null", repo);

        BuildingItem selectedBuilding = repo.getSelectedBuilding();
        assertNotNull("Default selected building should not be null", selectedBuilding);
        assertEquals("bldg_comp_it", selectedBuilding.getId());

        List<LocationItem> compLocations = repo.getLocationsByBuilding("bldg_comp_it");
        assertFalse("Computer/IT building should have locations", compLocations.isEmpty());

        List<LocationItem> allLocations = repo.getAllLocations();
        assertEquals(compLocations.size(), allLocations.size());

        // Search within building
        List<LocationItem> searchResults = repo.searchLocations("bldg_comp_it", "Lab", "All", "All Floors");
        assertFalse(searchResults.isEmpty());

        // Search by category and floor
        List<LocationItem> labsGround = repo.searchLocations("bldg_comp_it", "", "Laboratory", "Ground Floor");
        assertFalse(labsGround.isEmpty());
        for (LocationItem loc : labsGround) {
            assertEquals("Laboratory", loc.getCategory());
            assertEquals("Ground Floor", loc.getFloor());
            assertEquals("bldg_comp_it", loc.getBuildingId());
        }
    }
}
