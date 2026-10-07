package com.gpp.anvay.visual;

import com.gpp.anvay.data.visual.VisualNavigationRepository;
import com.gpp.anvay.model.position.ARDirection;
import com.gpp.anvay.model.position.NavigationNode;
import com.gpp.anvay.model.position.NavigationRoute;
import com.gpp.anvay.model.position.NavigationStep;
import com.gpp.anvay.model.visual.VisualLandmark;
import com.gpp.anvay.model.visual.VisualNavigationStep;
import com.gpp.anvay.navigation.NavigationGraph;
import com.gpp.anvay.navigation.PathFinder;
import com.gpp.anvay.navigation.visual.VisualNavigationManager;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Comprehensive Unit Tests for Photo-Grounded Visual Indoor Navigation.
 */
public class VisualNavigationArchitectureTest {

    private VisualNavigationRepository visualRepo;
    private VisualNavigationManager visualManager;
    private NavigationGraph graph;
    private PathFinder pathFinder;

    @Before
    public void setUp() {
        visualRepo = VisualNavigationRepository.getInstance();
        visualManager = VisualNavigationManager.getInstance();
        graph = new NavigationGraph();
        pathFinder = new PathFinder(graph);
    }

    @Test
    public void testControlledVisualLandmarkTypes() {
        assertTrue(VisualLandmark.isValidLandmarkType(VisualLandmark.TYPE_ROOM_ENTRANCE));
        assertTrue(VisualLandmark.isValidLandmarkType(VisualLandmark.TYPE_CORRIDOR_VIEW));
        assertTrue(VisualLandmark.isValidLandmarkType(VisualLandmark.TYPE_STAIRCASE_VIEW));
        assertTrue(VisualLandmark.isValidLandmarkType(VisualLandmark.TYPE_STAIRCASE_LANDING));
        assertTrue(VisualLandmark.isValidLandmarkType(VisualLandmark.TYPE_JUNCTION_VIEW));
        assertTrue(VisualLandmark.isValidLandmarkType(VisualLandmark.TYPE_FLOOR_TRANSITION));
        assertTrue(VisualLandmark.isValidLandmarkType(VisualLandmark.TYPE_DESTINATION_VIEW));

        // Invalid types
        assertFalse(VisualLandmark.isValidLandmarkType("ARBITRARY_TYPE"));
        assertFalse(VisualLandmark.isValidLandmarkType("EXIT_VIEW"));
        assertFalse(VisualLandmark.isValidLandmarkType(null));
    }

    @Test
    public void testVisualRepositoryLoadsLandmarksAcrossAllThreeFloors() {
        List<VisualLandmark> allLandmarks = visualRepo.getAllLandmarks();
        assertNotNull(allLandmarks);
        assertTrue("Repository must contain verified visual landmarks", allLandmarks.size() >= 30);

        List<VisualLandmark> gfLandmarks = visualRepo.getLandmarksByFloor("Ground Floor");
        assertFalse("Ground Floor visual landmarks must exist", gfLandmarks.isEmpty());

        List<VisualLandmark> ffLandmarks = visualRepo.getLandmarksByFloor("1st Floor");
        assertFalse("1st Floor visual landmarks must exist", ffLandmarks.isEmpty());

        List<VisualLandmark> sfLandmarks = visualRepo.getLandmarksByFloor("2nd Floor");
        assertFalse("2nd Floor visual landmarks must exist", sfLandmarks.isEmpty());

        // Strictly verify NO 3rd Floor landmarks
        List<VisualLandmark> thirdFloor = visualRepo.getLandmarksByFloor("3rd Floor");
        assertTrue("No 3rd Floor visual landmarks allowed", thirdFloor.isEmpty());
    }

    @Test
    public void testStaircaseTransitionsMappedCorrectly() {
        // Test S3 ascent from Ground Floor to 1st Floor
        VisualLandmark transGfFfS3 = visualRepo.getLandmarkForTransition("node_gf_s3", "node_ff_s3");
        assertNotNull("S3 GF to 1F transition must be mapped", transGfFfS3);
        assertEquals(VisualLandmark.TYPE_FLOOR_TRANSITION, transGfFfS3.getLandmarkType());
        assertTrue(transGfFfS3.getTitle().contains("S3"));

        // Test S3 ascent from 1st Floor to 2nd Floor
        VisualLandmark transFfSfS3 = visualRepo.getLandmarkForTransition("node_ff_s3", "node_sf_s3");
        assertNotNull("S3 1F to 2F transition must be mapped", transFfSfS3);
        assertEquals(VisualLandmark.TYPE_FLOOR_TRANSITION, transFfSfS3.getLandmarkType());
        assertTrue(transFfSfS3.getTitle().contains("S3"));

        // Test S1 ascent from Ground Floor to 1st Floor
        VisualLandmark transGfFfS1 = visualRepo.getLandmarkForTransition("node_gf_s1", "node_ff_s1");
        assertNotNull("S1 GF to 1F transition must be mapped", transGfFfS1);
        assertEquals(VisualLandmark.TYPE_FLOOR_TRANSITION, transGfFfS1.getLandmarkType());

        // Test S2 ascent from Ground Floor to 1st Floor
        VisualLandmark transGfFfS2 = visualRepo.getLandmarkForTransition("node_gf_s2", "node_ff_s2");
        assertNotNull("S2 GF to 1F transition must be mapped", transGfFfS2);
        assertEquals(VisualLandmark.TYPE_FLOOR_TRANSITION, transGfFfS2.getLandmarkType());
    }

    @Test
    public void testKnownRoomToPhotoMappings() {
        // Ground Floor
        VisualLandmark itLab1 = visualRepo.getLandmarkForNode("node_gf_it_lab1");
        assertNotNull(itLab1);
        assertEquals("visual_ref_06", itLab1.getDrawableResourceName());

        VisualLandmark itLab3 = visualRepo.getLandmarkForNode("node_gf_it_lab3");
        assertNotNull(itLab3);
        assertEquals("visual_ref_08", itLab3.getDrawableResourceName());

        VisualLandmark itStaff = visualRepo.getLandmarkForNode("node_gf_it_hod_staff");
        assertNotNull(itStaff);
        assertEquals("visual_ref_10", itStaff.getDrawableResourceName());

        // 1st Floor
        VisualLandmark cr18 = visualRepo.getLandmarkForNode("node_ff_cr18");
        assertNotNull(cr18);
        assertEquals("visual_ref_44", cr18.getDrawableResourceName());

        VisualLandmark cr20 = visualRepo.getLandmarkForNode("node_ff_cr20");
        assertNotNull(cr20);
        assertEquals("visual_ref_41", cr20.getDrawableResourceName());

        // 2nd Floor
        VisualLandmark cr21 = visualRepo.getLandmarkForNode("node_sf_cr21");
        assertNotNull(cr21);
        assertEquals("visual_ref_68", cr21.getDrawableResourceName());

        VisualLandmark cr22 = visualRepo.getLandmarkForNode("node_sf_cr22");
        assertNotNull(cr22);
        assertEquals("visual_ref_69", cr22.getDrawableResourceName());

        VisualLandmark admission = visualRepo.getLandmarkForNode("node_sf_admission");
        assertNotNull(admission);
        assertEquals("visual_ref_71", admission.getDrawableResourceName());
    }

    @Test
    public void testVisualNavigationConsumesNavigationRouteWithoutMutation() {
        // Route from CR18 (1st Floor) to CR22 (2nd Floor)
        NavigationRoute route = pathFinder.findRoute("node_ff_cr18", "node_sf_cr22");
        assertTrue(route.isRouteAvailable());
        int originalStepCount = route.getTotalSteps();
        assertTrue(originalStepCount > 0);

        List<VisualNavigationStep> visualSteps = visualManager.loadRoute(route);
        assertEquals("Visual step count must match topological route step count", originalStepCount, visualSteps.size());

        // Underlying route remains identical
        assertSame(route, visualManager.getCurrentRoute());
        assertEquals(originalStepCount, route.getTotalSteps());

        // Verify CR18 start step
        VisualNavigationStep startStep = visualSteps.get(0);
        assertEquals("node_ff_cr18", startStep.getToNodeId());
        assertEquals("1st Floor", startStep.getFloor());

        // Verify CR22 arrival step
        VisualNavigationStep lastStep = visualSteps.get(visualSteps.size() - 1);
        assertTrue(lastStep.isDestination());
        assertEquals("2nd Floor", lastStep.getFloor());
        assertEquals("node_sf_cr22", lastStep.getToNodeId());
        assertNotNull(lastStep.getVisualLandmark());
        assertEquals("visual_ref_69", lastStep.getVisualLandmark().getDrawableResourceName());
    }

    @Test
    public void testDemoMultiFloorNavigationCR18ToCR22() {
        // Generate route via BFS PathFinder
        NavigationRoute route = pathFinder.findRoute("node_ff_cr18", "node_sf_cr22");
        assertTrue("Route must be found", route.isRouteAvailable());

        List<VisualNavigationStep> steps = visualManager.loadRoute(route);
        assertFalse(steps.isEmpty());

        // Traverse steps manually
        assertEquals(0, visualManager.getCurrentStepIndex());
        assertEquals(0, visualManager.getProgressPercentage());

        boolean sawFloorTransition = false;
        for (int i = 0; i < steps.size(); i++) {
            VisualNavigationStep step = steps.get(i);
            if (step.isFloorTransition()) {
                sawFloorTransition = true;
                assertNotNull(step.getTransitionMessage());
                assertTrue(step.getTransitionMessage().contains("Staircase"));
            }
        }
        assertTrue("CR18 -> CR22 multi-floor route must encounter a staircase floor transition", sawFloorTransition);

        // Move to completion
        while (!visualManager.isFinished()) {
            boolean moved = visualManager.moveToNextStep();
            if (!moved) break;
        }

        assertTrue(visualManager.isFinished());
        assertEquals(100, visualManager.getProgressPercentage());
    }

    @Test
    public void testMissingPhotoGracefulFallback() {
        // Create an ad-hoc NavigationRoute with an unmapped junction node
        NavigationNode customNode = new NavigationNode("node_unmapped_junction", "bldg_comp_it", "Ground Floor", null, NavigationNode.TYPE_CORRIDOR_JUNCTION);
        NavigationStep customStep = new NavigationStep(1, "Ground Floor", customNode, "Continue along hallway", NavigationStep.STEP_MOVE, ARDirection.FORWARD);

        NavigationRoute mockRoute = new NavigationRoute();
        mockRoute.setRouteAvailable(true);
        mockRoute.getNodes().add(customNode);
        mockRoute.getSteps().add(customStep);

        List<VisualNavigationStep> visualSteps = visualManager.loadRoute(mockRoute);
        assertEquals(1, visualSteps.size());

        VisualNavigationStep step = visualSteps.get(0);
        assertFalse("Unmapped node must safely report false for hasVisualPhoto", step.hasVisualPhoto());
        assertNotNull(step.getInstruction());
        assertEquals(ARDirection.FORWARD, step.getDirection());
    }

    @Test
    public void testGroundFloorToSecondFloorS3Route() {
        // Route from IT Lab 1 (GF) to CR21 (2nd Floor)
        NavigationRoute route = pathFinder.findRoute("node_gf_it_lab1", "node_sf_cr21");
        assertTrue(route.isRouteAvailable());
        assertTrue(route.isMultiFloor());

        List<VisualNavigationStep> steps = visualManager.loadRoute(route);
        assertFalse(steps.isEmpty());

        VisualNavigationStep firstStep = steps.get(0);
        assertEquals("Ground Floor", firstStep.getFloor());
        assertNotNull(firstStep.getVisualLandmark());
        assertEquals("visual_ref_06", firstStep.getVisualLandmark().getDrawableResourceName());

        VisualNavigationStep lastStep = steps.get(steps.size() - 1);
        assertEquals("2nd Floor", lastStep.getFloor());
        assertTrue(lastStep.isDestination());
        assertNotNull(lastStep.getVisualLandmark());
        assertEquals("visual_ref_68", lastStep.getVisualLandmark().getDrawableResourceName());
    }

    @Test
    public void testStepProgressionAndRestart() {
        NavigationRoute route = pathFinder.findRoute("node_gf_it_lab1", "node_gf_it_lab4");
        visualManager.loadRoute(route);

        assertEquals(0, visualManager.getCurrentStepIndex());
        visualManager.moveToNextStep();
        assertEquals(1, visualManager.getCurrentStepIndex());

        visualManager.moveToPreviousStep();
        assertEquals(0, visualManager.getCurrentStepIndex());

        visualManager.moveToNextStep();
        visualManager.restart();
        assertEquals(0, visualManager.getCurrentStepIndex());
    }
}
