package com.gpp.anvay.navigation;

import com.gpp.anvay.model.position.ARDirection;
import com.gpp.anvay.model.position.NavigationNode;
import com.gpp.anvay.model.position.NavigationRoute;
import com.gpp.anvay.model.position.NavigationStep;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class NavigationPathfindingTest {

    private NavigationRepository repository;
    private NavigationGraph graph;
    private PathFinder pathFinder;

    @Before
    public void setUp() {
        repository = NavigationRepository.getInstance();
        graph = repository.getGraph();
        pathFinder = repository.getPathFinder();
    }

    @Test
    public void testSameFloorRouteGroundFloor() {
        // Route from Lab 1 to Lab 5 on Ground Floor
        NavigationRoute route = repository.findRouteByLocationIds("loc_gf_lab1", "loc_gf_lab5");

        assertNotNull(route);
        assertTrue(route.isRouteAvailable());
        assertEquals("Ground Floor", route.getStartFloor());
        assertEquals("Ground Floor", route.getDestinationFloor());
        assertFalse(route.isMultiFloor());
        assertTrue(route.getTotalSteps() >= 2);

        // Verify start and arrival steps
        NavigationStep firstStep = route.getStep(0);
        assertEquals(NavigationStep.STEP_MOVE, firstStep.getStepType());

        NavigationStep lastStep = route.getStep(route.getTotalSteps() - 1);
        assertEquals(NavigationStep.STEP_ARRIVE, lastStep.getStepType());
        assertEquals(ARDirection.ARRIVAL, lastStep.getArDirection());
    }

    @Test
    public void testSameFloorRouteFirstFloor() {
        // Route from CET Lab 1 to CR 14 on 1st Floor
        NavigationRoute route = repository.findRouteByLocationIds("loc_ff_cet_lab1", "loc_ff_cr14");

        assertNotNull(route);
        assertTrue(route.isRouteAvailable());
        assertEquals("1st Floor", route.getStartFloor());
        assertEquals("1st Floor", route.getDestinationFloor());
        assertFalse(route.isMultiFloor());
    }

    @Test
    public void testSameFloorRouteSecondFloor() {
        // Route from CET 7 to Physics Lab on 2nd Floor
        NavigationRoute route = repository.findRouteByLocationIds("loc_sf_cet7", "loc_sf_physics_lab");

        assertNotNull(route);
        assertTrue(route.isRouteAvailable());
        assertEquals("2nd Floor", route.getStartFloor());
        assertEquals("2nd Floor", route.getDestinationFloor());
        assertFalse(route.isMultiFloor());
    }

    @Test
    public void testGroundToFirstFloorRouting() {
        // Route from Ground Floor Lab 1 to 1st Floor CET Lab 1
        NavigationRoute route = repository.findRouteByLocationIds("loc_gf_lab1", "loc_ff_cet_lab1");

        assertNotNull(route);
        assertTrue(route.isRouteAvailable());
        assertEquals("Ground Floor", route.getStartFloor());
        assertEquals("1st Floor", route.getDestinationFloor());
        assertTrue(route.isMultiFloor());
        assertTrue(route.getFloorsTraversed().contains("Ground Floor"));
        assertTrue(route.getFloorsTraversed().contains("1st Floor"));

        // Verify staircase step exists
        boolean hasStaircaseStep = false;
        for (NavigationStep step : route.getSteps()) {
            if (step.isStaircaseTransition()) {
                hasStaircaseStep = true;
                assertEquals(ARDirection.STAIRCASE, step.getArDirection());
                break;
            }
        }
        assertTrue("Route must contain a staircase transition step", hasStaircaseStep);
    }

    @Test
    public void testFirstToSecondFloorRouting() {
        // Route from 1st Floor CR 17 to 2nd Floor CR 21
        NavigationRoute route = repository.findRouteByLocationIds("loc_ff_cr17", "loc_sf_cr21");

        assertNotNull(route);
        assertTrue(route.isRouteAvailable());
        assertEquals("1st Floor", route.getStartFloor());
        assertEquals("2nd Floor", route.getDestinationFloor());
        assertTrue(route.isMultiFloor());
    }

    @Test
    public void testGroundToSecondFloorMultiFloorRouting() {
        // Route from Ground Floor Lab 1 to 2nd Floor CR 21
        NavigationRoute route = repository.findRouteByLocationIds("loc_gf_lab1", "loc_sf_cr21");

        assertNotNull(route);
        assertTrue(route.isRouteAvailable());
        assertEquals("Ground Floor", route.getStartFloor());
        assertEquals("2nd Floor", route.getDestinationFloor());
        assertTrue(route.isMultiFloor());
        assertEquals(3, route.getFloorsTraversed().size());
        assertTrue(route.getFloorsTraversed().contains("Ground Floor"));
        assertTrue(route.getFloorsTraversed().contains("1st Floor"));
        assertTrue(route.getFloorsTraversed().contains("2nd Floor"));
    }

    @Test
    public void testStaircaseS1Mapping() {
        NavigationNode gfS1 = graph.getNode("node_gf_s1");
        NavigationNode ffS1 = graph.getNode("node_ff_s1");
        NavigationNode sfS1 = graph.getNode("node_sf_s1");

        assertNotNull(gfS1);
        assertNotNull(ffS1);
        assertNotNull(sfS1);

        // GF-S1 <-> FF-S1
        assertTrue(gfS1.getConnectedNodeIds().contains("node_ff_s1"));
        assertTrue(ffS1.getConnectedNodeIds().contains("node_gf_s1"));

        // FF-S1 <-> SF-S1
        assertTrue(ffS1.getConnectedNodeIds().contains("node_sf_s1"));
        assertTrue(sfS1.getConnectedNodeIds().contains("node_ff_s1"));

        // GF-S1 must NOT directly connect to SF-S1
        assertFalse(gfS1.getConnectedNodeIds().contains("node_sf_s1"));
    }

    @Test
    public void testStaircaseS2Mapping() {
        NavigationNode gfS2 = graph.getNode("node_gf_s2");
        NavigationNode ffS2 = graph.getNode("node_ff_s2");
        NavigationNode sfS2 = graph.getNode("node_sf_s2");

        assertNotNull(gfS2);
        assertNotNull(ffS2);
        assertNotNull(sfS2);

        // GF-S2 <-> FF-S2
        assertTrue(gfS2.getConnectedNodeIds().contains("node_ff_s2"));
        assertTrue(ffS2.getConnectedNodeIds().contains("node_gf_s2"));

        // FF-S2 <-> SF-S2
        assertTrue(ffS2.getConnectedNodeIds().contains("node_sf_s2"));
        assertTrue(sfS2.getConnectedNodeIds().contains("node_ff_s2"));

        // GF-S2 must NOT directly connect to SF-S2
        assertFalse(gfS2.getConnectedNodeIds().contains("node_sf_s2"));
    }

    @Test
    public void testStaircaseS3Mapping() {
        NavigationNode gfS3 = graph.getNode("node_gf_s3");
        NavigationNode ffS3 = graph.getNode("node_ff_s3");
        NavigationNode sfS3 = graph.getNode("node_sf_s3");

        assertNotNull(gfS3);
        assertNotNull(ffS3);
        assertNotNull(sfS3);

        // GF-S3 <-> FF-S3
        assertTrue(gfS3.getConnectedNodeIds().contains("node_ff_s3"));
        assertTrue(ffS3.getConnectedNodeIds().contains("node_gf_s3"));

        // FF-S3 <-> SF-S3
        assertTrue(ffS3.getConnectedNodeIds().contains("node_sf_s3"));
        assertTrue(sfS3.getConnectedNodeIds().contains("node_ff_s3"));

        // GF-S3 must NOT directly connect to SF-S3
        assertFalse(gfS3.getConnectedNodeIds().contains("node_sf_s3"));
    }

    @Test
    public void testVerticalRoomRelationshipsCR17AndCR21() {
        NavigationNode nodeCR17 = graph.getNode("node_ff_cr17");
        NavigationNode nodeCR21 = graph.getNode("node_sf_cr21");

        assertNotNull(nodeCR17);
        assertNotNull(nodeCR21);
        assertEquals("1st Floor", nodeCR17.getFloor());
        assertEquals("2nd Floor", nodeCR21.getFloor());

        // Route between CR17 and CR21 must resolve cleanly via nearest staircase S3
        NavigationRoute route = repository.findRouteByLocationIds("loc_ff_cr17", "loc_sf_cr21");
        assertTrue(route.isRouteAvailable());
    }

    @Test
    public void testVerticalRoomRelationshipsCR18AndCR22() {
        NavigationNode nodeCR18 = graph.getNode("node_ff_cr18");
        NavigationNode nodeCR22 = graph.getNode("node_sf_cr22");

        assertNotNull(nodeCR18);
        assertNotNull(nodeCR22);
        assertEquals("1st Floor", nodeCR18.getFloor());
        assertEquals("2nd Floor", nodeCR22.getFloor());

        // Route between CR18 and CR22 must resolve cleanly
        NavigationRoute route = repository.findRouteByLocationIds("loc_ff_cr18", "loc_sf_cr22");
        assertTrue(route.isRouteAvailable());
    }

    @Test
    public void testInvalidAndMissingLocationsHandling() {
        NavigationRoute nullStart = repository.findRouteByLocationIds(null, "loc_gf_lab1");
        assertFalse(nullStart.isRouteAvailable());
        assertNotNull(nullStart.getErrorMessage());

        NavigationRoute nullDest = repository.findRouteByLocationIds("loc_gf_lab1", null);
        assertFalse(nullDest.isRouteAvailable());
        assertNotNull(nullDest.getErrorMessage());

        NavigationRoute invalidId = repository.findRouteByLocationIds("loc_invalid_xyz", "loc_gf_lab1");
        assertFalse(invalidId.isRouteAvailable());
    }

    @Test
    public void testSameStartAndDestination() {
        NavigationRoute route = repository.findRouteByLocationIds("loc_gf_lab1", "loc_gf_lab1");
        assertNotNull(route);
        assertTrue(route.isRouteAvailable());
        assertEquals(1, route.getTotalSteps());
        assertTrue(route.getStep(0).isArrival());
    }
}
