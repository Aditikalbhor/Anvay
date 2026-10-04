package com.gpp.anvay.position;

import com.gpp.anvay.model.position.NavigationNode;
import com.gpp.anvay.model.position.UserPosition;

import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.Assert.*;

public class UserPositionArchitectureTest {

    private UserPositionManager positionManager;
    private MockIndoorPositionProvider mockProvider;

    @Before
    public void setUp() {
        positionManager = UserPositionManager.getInstance();
        positionManager.clearListeners();
        positionManager.resetPosition();

        mockProvider = new MockIndoorPositionProvider();
        positionManager.setPositionProvider(mockProvider);
    }

    @Test
    public void testUserPositionCreationAndAnchorDefaults() {
        UserPosition pos = new UserPosition("bldg_comp_it", "Ground Floor", "loc_g_01", "Mock");

        assertEquals("bldg_comp_it", pos.getBuildingId());
        assertEquals("Ground Floor", pos.getFloor());
        assertEquals("loc_g_01", pos.getAnchorLocationId());
        assertEquals("Mock", pos.getProviderSource());
        assertNull(pos.getX());
        assertNull(pos.getY());
        assertFalse(pos.hasCoordinates());
        assertTrue(pos.hasAnchor());
        assertTrue(pos.getTimestamp() > 0);
    }

    @Test
    public void testUserPositionWithCoordinates() {
        UserPosition pos = new UserPosition("bldg_comp_it", "1st Floor", "loc_1_101", 12.5, 34.0, 1.5f, 1000L, "Test");

        assertEquals(Double.valueOf(12.5), pos.getX());
        assertEquals(Double.valueOf(34.0), pos.getY());
        assertEquals(1.5f, pos.getAccuracy(), 0.001f);
        assertEquals(1000L, pos.getTimestamp());
        assertTrue(pos.hasCoordinates());
    }

    @Test
    public void testNavigationNodeCreation() {
        NavigationNode node = new NavigationNode("node_01", "bldg_comp_it", "Ground Floor", "loc_g_01", NavigationNode.TYPE_ROOM_ENTRY);

        assertEquals("node_01", node.getNodeId());
        assertEquals("bldg_comp_it", node.getBuildingId());
        assertEquals("Ground Floor", node.getFloor());
        assertEquals("loc_g_01", node.getLinkedLocationId());
        assertEquals(NavigationNode.TYPE_ROOM_ENTRY, node.getNodeType());
        assertNull(node.getX());
        assertNull(node.getY());
        assertNotNull(node.getConnectedNodeIds());
        assertTrue(node.getConnectedNodeIds().isEmpty());

        node.addConnectedNode("node_02");
        node.addConnectedNode("node_03");
        assertEquals(2, node.getConnectedNodeIds().size());
        assertTrue(node.getConnectedNodeIds().contains("node_02"));
        assertTrue(node.getConnectedNodeIds().contains("node_03"));

        // Verify only the 4 allowed navigation topology node types are valid
        assertTrue(NavigationNode.isValidNodeType(NavigationNode.TYPE_ROOM_ENTRY));
        assertTrue(NavigationNode.isValidNodeType(NavigationNode.TYPE_CORRIDOR_JUNCTION));
        assertTrue(NavigationNode.isValidNodeType(NavigationNode.TYPE_STAIRCASE));
        assertTrue(NavigationNode.isValidNodeType(NavigationNode.TYPE_CHECKPOINT));

        // Verify EXIT is NOT a valid navigation node type
        assertFalse(NavigationNode.isValidNodeType("EXIT"));
        assertFalse(NavigationNode.isValidNodeType("EMERGENCY_EXIT"));
    }

    @Test
    public void testMockIndoorPositionProviderLifecycle() {
        assertFalse(mockProvider.isTracking());
        assertNull(mockProvider.getLastKnownPosition());

        mockProvider.startTracking();
        assertTrue(mockProvider.isTracking());

        final AtomicReference<UserPosition> received = new AtomicReference<>();
        mockProvider.setPositionUpdateListener(received::set);

        UserPosition testPos = new UserPosition("bldg_comp_it", "2nd Floor", "loc_2_203", "Mock");
        mockProvider.emitPosition(testPos);

        assertNotNull(received.get());
        assertEquals("loc_2_203", received.get().getAnchorLocationId());
        assertEquals(testPos, mockProvider.getLastKnownPosition());

        mockProvider.stopTracking();
        assertFalse(mockProvider.isTracking());

        mockProvider.clearPosition();
        assertNull(mockProvider.getLastKnownPosition());
    }

    @Test
    public void testUserPositionManagerForwarding() {
        final AtomicReference<UserPosition> managerReceived = new AtomicReference<>();
        final AtomicInteger callCount = new AtomicInteger(0);

        UserPositionManager.UserPositionListener listener = position -> {
            managerReceived.set(position);
            callCount.incrementAndGet();
        };

        positionManager.addListener(listener);
        positionManager.startPositioning();
        assertTrue(positionManager.isPositioningActive());

        UserPosition testPos = new UserPosition("bldg_comp_it", "Ground Floor", "loc_g_02", "Mock");
        mockProvider.emitPosition(testPos);

        assertEquals(1, callCount.get());
        assertNotNull(managerReceived.get());
        assertEquals("loc_g_02", managerReceived.get().getAnchorLocationId());
        assertEquals(testPos, positionManager.getCurrentPosition());
        assertEquals("Ground Floor", positionManager.getCurrentFloor());
        assertEquals("bldg_comp_it", positionManager.getCurrentBuildingId());
        assertTrue(positionManager.hasValidPosition());
    }

    @Test
    public void testUserPositionManagerListenerManagement() {
        final AtomicInteger countA = new AtomicInteger(0);
        final AtomicInteger countB = new AtomicInteger(0);

        UserPositionManager.UserPositionListener listenerA = pos -> countA.incrementAndGet();
        UserPositionManager.UserPositionListener listenerB = pos -> countB.incrementAndGet();

        positionManager.addListener(listenerA);
        positionManager.addListener(listenerB);

        UserPosition p1 = new UserPosition("bldg_comp_it", "1st Floor", "loc_1_103", "Mock");
        mockProvider.emitPosition(p1);

        assertEquals(1, countA.get());
        assertEquals(1, countB.get());

        positionManager.removeListener(listenerA);

        UserPosition p2 = new UserPosition("bldg_comp_it", "1st Floor", "loc_1_104", "Mock");
        mockProvider.emitPosition(p2);

        assertEquals(1, countA.get());
        assertEquals(2, countB.get());

        positionManager.clearListeners();

        UserPosition p3 = new UserPosition("bldg_comp_it", "1st Floor", "loc_1_105", "Mock");
        mockProvider.emitPosition(p3);

        assertEquals(1, countA.get());
        assertEquals(2, countB.get());
    }

    @Test
    public void testUserPositionManagerProviderReplacement() {
        positionManager.startPositioning();
        assertTrue(mockProvider.isTracking());

        MockIndoorPositionProvider newProvider = new MockIndoorPositionProvider();
        positionManager.setPositionProvider(newProvider);

        // Previous provider should be stopped, new provider should take over tracking
        assertFalse(mockProvider.isTracking());
        assertTrue(newProvider.isTracking());
        assertEquals(newProvider, positionManager.getPositionProvider());

        final AtomicReference<UserPosition> updateFromNew = new AtomicReference<>();
        positionManager.addListener(updateFromNew::set);

        UserPosition newPos = new UserPosition("bldg_comp_it", "2nd Floor", "loc_2_201", "MockNew");
        newProvider.emitPosition(newPos);

        assertNotNull(updateFromNew.get());
        assertEquals("loc_2_201", updateFromNew.get().getAnchorLocationId());
        assertEquals("MockNew", updateFromNew.get().getProviderSource());
    }

    @Test
    public void testUserPositionManagerNullAndUnknownHandling() {
        positionManager.setPositionProvider(null);
        assertNull(positionManager.getCurrentPosition());
        assertFalse(positionManager.hasValidPosition());
        assertNull(positionManager.getCurrentFloor());
        assertNull(positionManager.getCurrentBuildingId());
        assertFalse(positionManager.isPositioningActive());

        // Calling start/stop with null provider should not crash
        positionManager.startPositioning();
        positionManager.stopPositioning();
        positionManager.resetPosition();
    }
}
