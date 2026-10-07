package com.gpp.anvay.ui;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.gpp.anvay.R;
import com.gpp.anvay.data.LocationRepository;
import com.gpp.anvay.model.LocationItem;
import com.gpp.anvay.model.position.ARDirection;
import com.gpp.anvay.model.position.NavigationRoute;
import com.gpp.anvay.model.position.UserPosition;
import com.gpp.anvay.model.visual.VisualLandmark;
import com.gpp.anvay.model.visual.VisualNavigationStep;
import com.gpp.anvay.navigation.NavigationManager;
import com.gpp.anvay.navigation.NavigationRepository;
import com.gpp.anvay.navigation.visual.VisualNavigationManager;
import com.gpp.anvay.position.UserPositionManager;

import java.util.List;

/**
 * Photo-Grounded Visual Indoor Navigation Activity.
 * Displays real Computer/IT Building photographs aligned to the active NavigationRoute.
 */
public class VisualNavigationActivity extends AppCompatActivity implements VisualNavigationManager.VisualNavigationListener {

    private VisualNavigationManager visualNavManager;
    private NavigationRepository navRepo;
    private LocationRepository locationRepo;
    private UserPositionManager positionManager;

    private LocationItem destinationItem;
    private String destinationId;
    private String startId;
    private NavigationRoute activeRoute;

    // UI Views
    private LinearLayout panelFloorGf;
    private LinearLayout panelFloorFf;
    private LinearLayout panelFloorSf;
    private TextView tvFloorGfLabel;
    private TextView tvFloorFfLabel;
    private TextView tvFloorSfLabel;
    private LinearProgressIndicator progressRoute;

    private TextView tvVisualDestName;
    private TextView tvVisualDestBadge;
    private TextView tvVisualStartPoint;
    private TextView tvVisualStepCount;

    private MaterialCardView cardFloorTransition;
    private TextView tvFloorTransitionMessage;

    private MaterialCardView cardArrival;
    private TextView tvArrivalMessage;

    private ImageView ivLandmarkPhoto;
    private LinearLayout layoutPhotoFallback;
    private TextView tvFallbackSubtext;
    private TextView tvLandmarkTypeBadge;
    private LinearLayout badgeVerified;
    private TextView tvLandmarkTitle;
    private TextView tvLandmarkDescription;

    private ImageView ivStepDirectionIcon;
    private TextView tvStepFloorIndicator;
    private TextView tvStepInstruction;
    private TextView tvNextStepHint;

    private MaterialButton btnVisualPrev;
    private MaterialButton btnVisualNext;
    private MaterialButton btnLaunchArFromVisual;
    private MaterialButton btnVisualRestart;
    private MaterialButton btnVisualExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visual_navigation);

        visualNavManager = VisualNavigationManager.getInstance();
        navRepo = NavigationRepository.getInstance();
        locationRepo = LocationRepository.getInstance();
        positionManager = UserPositionManager.getInstance();

        initViews();
        setupToolbar();
        extractIntentData();
        setupListeners();
        loadAndDisplayVisualRoute();
    }

    private void initViews() {
        panelFloorGf = findViewById(R.id.panelFloorGf);
        panelFloorFf = findViewById(R.id.panelFloorFf);
        panelFloorSf = findViewById(R.id.panelFloorSf);
        tvFloorGfLabel = findViewById(R.id.tvFloorGfLabel);
        tvFloorFfLabel = findViewById(R.id.tvFloorFfLabel);
        tvFloorSfLabel = findViewById(R.id.tvFloorSfLabel);
        progressRoute = findViewById(R.id.progressRoute);

        tvVisualDestName = findViewById(R.id.tvVisualDestName);
        tvVisualDestBadge = findViewById(R.id.tvVisualDestBadge);
        tvVisualStartPoint = findViewById(R.id.tvVisualStartPoint);
        tvVisualStepCount = findViewById(R.id.tvVisualStepCount);

        cardFloorTransition = findViewById(R.id.cardFloorTransition);
        tvFloorTransitionMessage = findViewById(R.id.tvFloorTransitionMessage);

        cardArrival = findViewById(R.id.cardArrival);
        tvArrivalMessage = findViewById(R.id.tvArrivalMessage);

        ivLandmarkPhoto = findViewById(R.id.ivLandmarkPhoto);
        layoutPhotoFallback = findViewById(R.id.layoutPhotoFallback);
        tvFallbackSubtext = findViewById(R.id.tvFallbackSubtext);
        tvLandmarkTypeBadge = findViewById(R.id.tvLandmarkTypeBadge);
        badgeVerified = findViewById(R.id.badgeVerified);
        tvLandmarkTitle = findViewById(R.id.tvLandmarkTitle);
        tvLandmarkDescription = findViewById(R.id.tvLandmarkDescription);

        ivStepDirectionIcon = findViewById(R.id.ivStepDirectionIcon);
        tvStepFloorIndicator = findViewById(R.id.tvStepFloorIndicator);
        tvStepInstruction = findViewById(R.id.tvStepInstruction);
        tvNextStepHint = findViewById(R.id.tvNextStepHint);

        btnVisualPrev = findViewById(R.id.btnVisualPrev);
        btnVisualNext = findViewById(R.id.btnVisualNext);
        btnLaunchArFromVisual = findViewById(R.id.btnLaunchArFromVisual);
        btnVisualRestart = findViewById(R.id.btnVisualRestart);
        btnVisualExit = findViewById(R.id.btnVisualExit);
    }

    private void setupToolbar() {
        MaterialToolbar toolbar = findViewById(R.id.toolbarVisualNav);
        toolbar.setNavigationOnClickListener(v -> finish());
    }

    private void extractIntentData() {
        destinationItem = (LocationItem) getIntent().getSerializableExtra("location_item");
        destinationId = getIntent().getStringExtra("destination_id");
        startId = getIntent().getStringExtra("start_id");

        if (destinationItem != null) {
            destinationId = destinationItem.getId();
        } else if (destinationId != null) {
            destinationItem = locationRepo.getLocationById(destinationId);
        }

        if (destinationItem == null) {
            List<LocationItem> all = locationRepo.getAllLocations();
            if (!all.isEmpty()) {
                destinationItem = all.get(0);
                destinationId = destinationItem.getId();
            }
        }

        if (startId == null) {
            UserPosition currPos = positionManager.getCurrentPosition();
            if (currPos != null && currPos.hasAnchor()) {
                startId = currPos.getAnchorLocationId();
            } else {
                startId = "loc_gf_lab1";
            }
        }
    }

    private void setupListeners() {
        btnVisualNext.setOnClickListener(v -> {
            if (visualNavManager.isFinished()) {
                Toast.makeText(this, "You have reached your destination!", Toast.LENGTH_SHORT).show();
            } else {
                visualNavManager.moveToNextStep();
            }
        });

        btnVisualPrev.setOnClickListener(v -> visualNavManager.moveToPreviousStep());

        btnVisualRestart.setOnClickListener(v -> {
            visualNavManager.restart();
            Toast.makeText(this, "Visual navigation restarted from beginning.", Toast.LENGTH_SHORT).show();
        });

        btnVisualExit.setOnClickListener(v -> finish());

        btnLaunchArFromVisual.setOnClickListener(v -> {
            Intent arIntent = new Intent(VisualNavigationActivity.this, ARNavigationActivity.class);
            arIntent.putExtra("destination_id", destinationId);
            arIntent.putExtra("start_id", startId);
            if (destinationItem != null) {
                arIntent.putExtra("location_item", destinationItem);
            }
            startActivity(arIntent);
        });
    }

    private void loadAndDisplayVisualRoute() {
        // Reuse NavigationManager route if available and matching, or calculate using NavigationRepository
        NavigationManager navManager = NavigationManager.getInstance();
        if (navManager.isNavigating() && navManager.getCurrentRoute() != null) {
            activeRoute = navManager.getCurrentRoute();
        } else {
            activeRoute = navRepo.findRouteByLocationIds(startId, destinationId);
        }

        if (activeRoute != null && activeRoute.isRouteAvailable()) {
            visualNavManager.loadRoute(activeRoute);
        } else {
            Toast.makeText(this, "Unable to calculate route to destination.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        visualNavManager.addListener(this);
        VisualNavigationStep currentStep = visualNavManager.getCurrentVisualStep();
        if (currentStep != null) {
            renderVisualStep(visualNavManager.getCurrentStepIndex(), currentStep);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        visualNavManager.removeListener(this);
    }

    @Override
    public void onVisualRouteLoaded(List<VisualNavigationStep> steps) {
        if (destinationItem != null) {
            tvVisualDestName.setText(destinationItem.getName());
            tvVisualDestBadge.setText(destinationItem.getRoomNumber());
        }

        LocationItem startLoc = locationRepo.getLocationById(startId);
        String startName = startLoc != null ? startLoc.getName() : startId;
        tvVisualStartPoint.setText("From: " + startName);
    }

    @Override
    public void onVisualStepChanged(int stepIndex, VisualNavigationStep step) {
        renderVisualStep(stepIndex, step);
    }

    @Override
    public void onVisualArrival(VisualNavigationStep arrivalStep) {
        Toast.makeText(this, "You have arrived at " + (destinationItem != null ? destinationItem.getName() : "destination") + "!", Toast.LENGTH_LONG).show();
    }

    private void renderVisualStep(int stepIndex, VisualNavigationStep step) {
        if (step == null) return;

        int total = visualNavManager.getTotalSteps();
        tvVisualStepCount.setText("Step " + (stepIndex + 1) + " of " + total);

        // Update 3D-style floor perspective tabs
        updateFloorVisualizer(step.getFloor());

        // Update progress bar
        progressRoute.setProgress(visualNavManager.getProgressPercentage());

        // Floor transition handling
        if (step.isFloorTransition()) {
            cardFloorTransition.setVisibility(View.VISIBLE);
            tvFloorTransitionMessage.setText(step.getTransitionMessage() != null ?
                    step.getTransitionMessage() : "Proceed to next floor via staircase");
        } else {
            cardFloorTransition.setVisibility(View.GONE);
        }

        // Arrival handling
        if (step.isDestination()) {
            cardArrival.setVisibility(View.VISIBLE);
            String destTitle = destinationItem != null ? destinationItem.getName() : "Destination";
            tvArrivalMessage.setText("You have arrived at " + destTitle + " on " + step.getFloor() + ".");
            btnVisualNext.setText("Arrived ✔");
            btnVisualNext.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.success_green)));
        } else {
            cardArrival.setVisibility(View.GONE);
            btnVisualNext.setText("Next Step →");
            btnVisualNext.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.primary)));
        }

        // Photo landmark rendering with graceful fallback
        VisualLandmark landmark = step.getVisualLandmark();
        if (landmark != null && landmark.getDrawableResourceName() != null && !landmark.getDrawableResourceName().isEmpty()) {
            int resId = getResources().getIdentifier(landmark.getDrawableResourceName(), "drawable", getPackageName());
            if (resId != 0) {
                ivLandmarkPhoto.setImageResource(resId);
                ivLandmarkPhoto.setVisibility(View.VISIBLE);
                layoutPhotoFallback.setVisibility(View.GONE);

                tvLandmarkTypeBadge.setText(formatLandmarkType(landmark.getLandmarkType()));
                tvLandmarkTypeBadge.setVisibility(View.VISIBLE);
                badgeVerified.setVisibility(landmark.isVerified() ? View.VISIBLE : View.GONE);

                tvLandmarkTitle.setText(landmark.getTitle());
                tvLandmarkDescription.setText(landmark.getDescription());
            } else {
                showPhotoFallback(step, landmark);
            }
        } else {
            showPhotoFallback(step, landmark);
        }

        // Directional icon & instruction
        ARDirection dir = step.getDirection();
        if (dir == ARDirection.STAIRCASE || step.isFloorTransition()) {
            ivStepDirectionIcon.setImageResource(R.drawable.ic_stairs);
        } else if (dir == ARDirection.ARRIVAL || step.isDestination()) {
            ivStepDirectionIcon.setImageResource(R.drawable.ic_check_circle);
        } else {
            ivStepDirectionIcon.setImageResource(R.drawable.ic_navigation);
        }

        tvStepFloorIndicator.setText(step.getFloor() != null ? step.getFloor().toUpperCase() : "COMPUTER/IT BUILDING");
        tvStepInstruction.setText(step.getInstruction());

        // Next step hint
        VisualNavigationStep next = visualNavManager.getNextVisualStep();
        if (next != null) {
            tvNextStepHint.setVisibility(View.VISIBLE);
            tvNextStepHint.setText("Next: " + next.getInstruction());
        } else {
            tvNextStepHint.setVisibility(View.GONE);
        }

        btnVisualPrev.setEnabled(stepIndex > 0);
    }

    private void showPhotoFallback(VisualNavigationStep step, VisualLandmark landmark) {
        ivLandmarkPhoto.setVisibility(View.GONE);
        layoutPhotoFallback.setVisibility(View.VISIBLE);
        tvLandmarkTypeBadge.setVisibility(View.GONE);
        badgeVerified.setVisibility(View.GONE);

        if (landmark != null) {
            tvLandmarkTitle.setText(landmark.getTitle());
            tvLandmarkDescription.setText(landmark.getDescription());
            tvFallbackSubtext.setText("Topological route guidance for " + landmark.getTitle());
        } else {
            tvLandmarkTitle.setText("Corridor Navigation Section");
            tvLandmarkDescription.setText("Follow indoor corridor pathway on " + step.getFloor() + ".");
            tvFallbackSubtext.setText("Proceed along corridor to next waypoint");
        }
    }

    private void updateFloorVisualizer(String floor) {
        int colorActiveBg = ContextCompat.getColor(this, R.color.primary);
        int colorActiveText = ContextCompat.getColor(this, R.color.text_on_dark);
        int colorInactiveBg = ContextCompat.getColor(this, R.color.surface_variant);
        int colorInactiveText = ContextCompat.getColor(this, R.color.text_secondary);

        boolean isGf = "Ground Floor".equalsIgnoreCase(floor);
        boolean isFf = "1st Floor".equalsIgnoreCase(floor);
        boolean isSf = "2nd Floor".equalsIgnoreCase(floor);

        panelFloorGf.setBackgroundTintList(ColorStateList.valueOf(isGf ? colorActiveBg : colorInactiveBg));
        tvFloorGfLabel.setTextColor(isGf ? colorActiveText : colorInactiveText);

        panelFloorFf.setBackgroundTintList(ColorStateList.valueOf(isFf ? colorActiveBg : colorInactiveBg));
        tvFloorFfLabel.setTextColor(isFf ? colorActiveText : colorInactiveText);

        panelFloorSf.setBackgroundTintList(ColorStateList.valueOf(isSf ? colorActiveBg : colorInactiveBg));
        tvFloorSfLabel.setTextColor(isSf ? colorActiveText : colorInactiveText);
    }

    private String formatLandmarkType(String type) {
        if (type == null) return "BUILDING VIEW";
        return type.replace('_', ' ');
    }
}
