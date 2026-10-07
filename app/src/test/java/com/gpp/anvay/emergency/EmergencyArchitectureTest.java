package com.gpp.anvay.emergency;

import com.gpp.anvay.data.EmergencyRepository;
import com.gpp.anvay.data.LocationRepository;
import com.gpp.anvay.data.MockDataProvider;
import com.gpp.anvay.model.BuildingItem;
import com.gpp.anvay.model.EmergencyContact;
import com.gpp.anvay.model.SafetyInstruction;
import com.gpp.anvay.model.position.NavigationNode;
import com.gpp.anvay.navigation.NavigationGraph;

import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

public class EmergencyArchitectureTest {

    private EmergencyRepository emergencyRepo;
    private NavigationGraph navigationGraph;
    private LocationRepository locationRepo;

    @Before
    public void setUp() {
        emergencyRepo = EmergencyRepository.getInstance();
        navigationGraph = new NavigationGraph();
        locationRepo = LocationRepository.getInstance();
    }

    @Test
    public void testEmergencyContactsExistAndNotEmpty() {
        List<EmergencyContact> contacts = emergencyRepo.getEmergencyContacts();
        assertNotNull("Emergency contacts list should not be null", contacts);
        assertFalse("Emergency contacts list should not be empty", contacts.isEmpty());
        assertTrue("Should have at least 5 emergency contacts", contacts.size() >= 5);
    }

    @Test
    public void testRequiredEmergencyContactCategoriesExist() {
        List<EmergencyContact> contacts = emergencyRepo.getEmergencyContacts();

        boolean hasCampusSecurity = false;
        boolean hasMedicalFirstAid = false;
        boolean hasFireSafety = false;
        boolean hasDeptEmergency = false;
        boolean hasAmbulance108 = false;

        for (EmergencyContact contact : contacts) {
            String title = contact.getTitle().toLowerCase();
            String phone = contact.getPhoneNumber();

            if (title.contains("campus security") || title.contains("security")) {
                hasCampusSecurity = true;
            }
            if (title.contains("medical") || title.contains("first aid") || title.contains("health")) {
                hasMedicalFirstAid = true;
            }
            if (title.contains("fire")) {
                hasFireSafety = true;
            }
            if (title.contains("department") || title.contains("college")) {
                hasDeptEmergency = true;
            }
            if (title.contains("ambulance") || phone.contains("108")) {
                hasAmbulance108 = true;
            }
        }

        assertTrue("Must have Campus Security contact", hasCampusSecurity);
        assertTrue("Must have Medical / First Aid contact", hasMedicalFirstAid);
        assertTrue("Must have Fire / Fire Safety contact", hasFireSafety);
        assertTrue("Must have Department / College Emergency Contact", hasDeptEmergency);
        assertTrue("Must have Ambulance (108) contact", hasAmbulance108);
    }

    @Test
    public void testEmergencyContactDataDisplayedCorrectly() {
        List<EmergencyContact> contacts = emergencyRepo.getEmergencyContacts();

        for (EmergencyContact contact : contacts) {
            assertNotNull("Contact ID should not be null", contact.getId());
            assertFalse("Contact ID should not be empty", contact.getId().trim().isEmpty());

            assertNotNull("Contact title should not be null", contact.getTitle());
            assertFalse("Contact title should not be empty", contact.getTitle().trim().isEmpty());

            assertNotNull("Contact person should not be null", contact.getContactPerson());
            assertFalse("Contact person should not be empty", contact.getContactPerson().trim().isEmpty());

            assertNotNull("Phone number should not be null", contact.getPhoneNumber());
            assertFalse("Phone number should not be empty", contact.getPhoneNumber().trim().isEmpty());

            assertNotNull("Location should not be null", contact.getLocation());
            assertFalse("Location should not be empty", contact.getLocation().trim().isEmpty());

            assertNotNull("Icon type should not be null", contact.getIconType());
            assertFalse("Icon type should not be empty", contact.getIconType().trim().isEmpty());

            // Check phone cleaning for dialer intent
            String cleanPhone = contact.getPhoneNumber().replaceAll("[^0-9+]", "");
            assertFalse("Cleaned phone should contain digits", cleanPhone.isEmpty());
        }
    }

    @Test
    public void testEmergencyContactDemoSampleMarking() {
        List<EmergencyContact> contacts = emergencyRepo.getEmergencyContacts();

        for (EmergencyContact contact : contacts) {
            String phone = contact.getPhoneNumber().trim();
            String person = contact.getContactPerson();

            // Standard public emergency numbers 108 and 101 don't require demo flag
            if (!phone.equals("108") && !phone.equals("101")) {
                assertTrue("Non-public contact should be marked as Demo or Sample: " + contact.getTitle(),
                        person.toLowerCase().contains("demo") ||
                        person.toLowerCase().contains("sample") ||
                        contact.getTitle().toLowerCase().contains("demo") ||
                        contact.getTitle().toLowerCase().contains("sample"));
            }
        }
    }

    @Test
    public void testEmergencyRepositoryCRUD() {
        EmergencyContact testContact = new EmergencyContact(
                "emg_test_99",
                "Test Security",
                "Security Officer (Demo)",
                "+91 20 0000 9999",
                "Gate 2",
                "police"
        );

        int originalCount = emergencyRepo.getEmergencyContacts().size();
        emergencyRepo.addEmergencyContact(testContact);
        assertEquals(originalCount + 1, emergencyRepo.getEmergencyContacts().size());

        testContact.setTitle("Updated Test Security");
        boolean updated = emergencyRepo.updateEmergencyContact(testContact);
        assertTrue(updated);

        boolean deleted = emergencyRepo.deleteEmergencyContact("emg_test_99");
        assertTrue(deleted);
        assertEquals(originalCount, emergencyRepo.getEmergencyContacts().size());
    }

    @Test
    public void testSafetyInstructionsExistAndIntegrity() {
        List<SafetyInstruction> instructions = emergencyRepo.getSafetyInstructions();
        assertNotNull("Safety instructions should not be null", instructions);
        assertFalse("Safety instructions should not be empty", instructions.isEmpty());

        for (SafetyInstruction instruction : instructions) {
            assertNotNull("Instruction ID should not be null", instruction.getId());
            assertNotNull("Instruction title should not be null", instruction.getTitle());
            assertNotNull("Instruction summary should not be null", instruction.getSummary());
            assertNotNull("Instruction steps should not be null", instruction.getSteps());
            assertFalse("Instruction steps should not be empty", instruction.getSteps().isEmpty());
            assertNotNull("Icon type should not be null", instruction.getIconType());
        }
    }

    @Test
    public void testNoExitOrEmergencyExitNodeTypes() {
        // Only 4 valid types
        assertTrue(NavigationNode.isValidNodeType(NavigationNode.TYPE_ROOM_ENTRY));
        assertTrue(NavigationNode.isValidNodeType(NavigationNode.TYPE_CORRIDOR_JUNCTION));
        assertTrue(NavigationNode.isValidNodeType(NavigationNode.TYPE_STAIRCASE));
        assertTrue(NavigationNode.isValidNodeType(NavigationNode.TYPE_CHECKPOINT));

        // Rejection of invalid / exit node types
        assertFalse(NavigationNode.isValidNodeType("EXIT"));
        assertFalse(NavigationNode.isValidNodeType("EMERGENCY_EXIT"));
        assertFalse(NavigationNode.isValidNodeType("SAFE_EXIT"));
        assertFalse(NavigationNode.isValidNodeType("FIRE_EXIT"));
        assertFalse(NavigationNode.isValidNodeType("ASSEMBLY_ZONE"));
        assertFalse(NavigationNode.isValidNodeType(null));
        assertFalse(NavigationNode.isValidNodeType(""));
    }

    @Test
    public void testNoUnsupportedEmergencyDestinationsInGraph() {
        List<NavigationNode> allNodes = navigationGraph.getAllNodes();
        assertFalse(allNodes.isEmpty());

        for (NavigationNode node : allNodes) {
            assertTrue("Node must have valid topology type", NavigationNode.isValidNodeType(node.getNodeType()));
            assertNotEquals("EXIT", node.getNodeType());
            assertNotEquals("EMERGENCY_EXIT", node.getNodeType());
            assertFalse("Node ID must not contain exit: " + node.getNodeId(),
                    node.getNodeId().toLowerCase().contains("exit"));
        }
    }

    @Test
    public void testComputerITBuildingHasOnlyThreeFloors() {
        BuildingItem building = locationRepo.getSelectedBuilding();
        assertNotNull(building);
        assertEquals("bldg_comp_it", building.getId());

        List<String> floors = building.getSupportedFloors();
        assertEquals("Computer/IT Building must have exactly 3 floors", 3, floors.size());
        assertTrue("Must contain Ground Floor", floors.contains("Ground Floor"));
        assertTrue("Must contain 1st Floor", floors.contains("1st Floor"));
        assertTrue("Must contain 2nd Floor", floors.contains("2nd Floor"));

        assertFalse("Must not contain 3rd Floor", floors.contains("3rd Floor"));
        assertFalse("Must not contain Third Floor", floors.contains("Third Floor"));
        assertFalse("Must not contain 4th Floor", floors.contains("4th Floor"));
        assertFalse("Must not contain Basement", floors.contains("Basement"));
    }
}
