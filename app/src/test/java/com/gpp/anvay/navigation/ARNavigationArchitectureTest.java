package com.gpp.anvay.navigation;

import com.gpp.anvay.model.position.ARDirection;
import com.gpp.anvay.model.position.NavigationRoute;
import com.gpp.anvay.model.position.NavigationStep;

import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.Assert.*;

public class ARNavigationArchitectureTest {

    private NavigationManager navManager;
    private NavigationRepository repository;

    @Before
    public void setUp() {
        navManager = NavigationManager.getInstance();
        repository = NavigationRepository.getInstance();
        navManager.clearListeners();
        navManager.cancelNavigation();
    }

    @Test
    public void testValidNavigationRouteReachesAR() {
        NavigationRoute route = navManager.startNavigation("loc_gf_lab1", "loc_sf_cr21");

        assertNotNull(route);
        assertTrue(route.isRouteAvailable());
        assertTrue(navManager.isNavigating());
        assertNotNull(navManager.getCurrentStep());
        assertEquals("Ground Floor", navManager.getCurrentFloor());
    }

    @Test
    public void testMoveStepProvidesForwardGuidance() {
        NavigationRoute route = navManager.startNavigation("loc_gf_lab1", "loc_gf_lab2");
        NavigationStep firstStep = route.getStep(0);

        assertEquals(NavigationStep.STEP_MOVE, firstStep.getStepType());
        assertEquals(ARDirection.FORWARD, firstStep.getArDirection());
    }

    @Test
    public void testStaircaseStepProvidesStaircaseGuidance() {
        NavigationRoute route = navManager.startNavigation("loc_gf_lab1", "loc_sf_cr21");

        NavigationStep staircaseStep = null;
        for (NavigationStep step : route.getSteps()) {
            if (step.isStaircaseTransition()) {
                staircaseStep = step;
                break;
            }
        }

        assertNotNull("Multi-floor route must contain a staircase step", staircaseStep);
        assertEquals(NavigationStep.STEP_STAIRCASE, staircaseStep.getStepType());
        assertEquals(ARDirection.STAIRCASE, staircaseStep.getArDirection());
        assertNotNull(staircaseStep.getStaircaseName());
        assertNotNull(staircaseStep.getTargetFloor());
    }

    @Test
    public void testArrivalStepProvidesArrivalGuidance() {
        NavigationRoute route = navManager.startNavigation("loc_gf_lab1", "loc_gf_lab2");
        NavigationStep lastStep = route.getStep(route.getTotalSteps() - 1);

        assertEquals(NavigationStep.STEP_ARRIVE, lastStep.getStepType());
        assertEquals(ARDirection.ARRIVAL, lastStep.getArDirection());
        assertTrue(lastStep.isArrival());
    }

    @Test
    public void testMultiFloorStepProgressionUpdatesCurrentFloor() {
        navManager.startNavigation("loc_gf_lab1", "loc_sf_cr21");

        assertEquals("Ground Floor", navManager.getCurrentFloor());

        // Step forward until we reach second floor
        while (navManager.nextStep()) {
            NavigationStep currentStep = navManager.getCurrentStep();
            assertNotNull(currentStep);
        }

        assertTrue(navManager.hasArrived());
        assertEquals("2nd Floor", navManager.getCurrentFloor());
    }

    @Test
    public void testCR21RouteIncludesCorrectStaircaseTransition() {
        NavigationRoute route = navManager.startNavigation("loc_gf_lab1", "loc_sf_cr21");
        assertTrue(route.isRouteAvailable());

        boolean hasStaircase = false;
        for (NavigationStep step : route.getSteps()) {
            if (step.isStaircaseTransition()) {
                hasStaircase = true;
                break;
            }
        }
        assertTrue(hasStaircase);
    }

    @Test
    public void testCR22RouteIncludesCorrectStaircaseTransition() {
        NavigationRoute route = navManager.startNavigation("loc_gf_lab1", "loc_sf_cr22");
        assertTrue(route.isRouteAvailable());

        boolean hasStaircase = false;
        for (NavigationStep step : route.getSteps()) {
            if (step.isStaircaseTransition()) {
                hasStaircase = true;
                break;
            }
        }
        assertTrue(hasStaircase);
    }

    @Test
    public void testNavigationManagerListenerCallbacks() {
        final AtomicReference<NavigationRoute> calculated = new AtomicReference<>();
        final AtomicInteger stepChanges = new AtomicInteger(0);
        final AtomicReference<NavigationStep> arrivalRef = new AtomicReference<>();

        navManager.addListener(new NavigationManager.NavigationListener() {
            @Override
            public void onRouteCalculated(NavigationRoute route) {
                calculated.set(route);
            }

            @Override
            public void onStepChanged(int stepIndex, NavigationStep step) {
                stepChanges.incrementAndGet();
            }

            @Override
            public void onArrival(NavigationStep arrivalStep) {
                arrivalRef.set(arrivalStep);
            }

            @Override
            public void onNavigationCancelled() {}
        });

        navManager.startNavigation("loc_gf_lab1", "loc_gf_lab2");

        assertNotNull(calculated.get());
        assertTrue(stepChanges.get() >= 1);

        while (navManager.nextStep()) {
            // progressing
        }

        assertNotNull(arrivalRef.get());
        assertTrue(navManager.hasArrived());
    }

    @Test
    public void testEmptyAndInvalidRouteHandling() {
        NavigationRoute route = navManager.startNavigation(null, null);
        assertFalse(route.isRouteAvailable());
        assertFalse(navManager.isNavigating());
        assertFalse(navManager.nextStep());
        assertFalse(navManager.previousStep());
        assertFalse(navManager.hasArrived());
    }
}
