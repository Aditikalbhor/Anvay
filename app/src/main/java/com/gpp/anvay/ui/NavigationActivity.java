package com.gpp.anvay.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.gpp.anvay.R;
import com.gpp.anvay.adapter.NavigationStepAdapter;
import com.gpp.anvay.data.LocationRepository;
import com.gpp.anvay.model.LocationItem;
import com.gpp.anvay.model.position.NavigationRoute;
import com.gpp.anvay.model.position.NavigationStep;
import com.gpp.anvay.model.position.UserPosition;
import com.gpp.anvay.navigation.NavigationManager;
import com.gpp.anvay.position.UserPositionManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Standard indoor navigation screen showing topological turn-by-turn guidance.
 */
public class NavigationActivity extends AppCompatActivity implements NavigationManager.NavigationListener {

    private NavigationManager navManager;
    private UserPositionManager positionManager;
    private LocationRepository locationRepo;

    private LocationItem destinationItem;
    private String destinationId;
    private String startId;

    private TextView tvDestName;
    private TextView tvDestCode;
    private TextView tvStartPoint;
    private TextView tvFloorFlow;
    private ImageView ivActiveStepIcon;
    private TextView tvActiveStepCounter;
    private TextView tvActiveStepInstruction;
    private MaterialButton btnPrevStep;
    private MaterialButton btnNextStep;
    private MaterialButton btnLaunchVisualGuidance;
    private MaterialButton btnLaunchAR;
    private MaterialButton btnRecalculate;
    private MaterialButton btnCancel;
    private MaterialButton btnChangeStart;

    private RecyclerView rvSteps;
    private NavigationStepAdapter stepAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        navManager = NavigationManager.getInstance();
        positionManager = UserPositionManager.getInstance();
        locationRepo = LocationRepository.getInstance();

        initViews();
        setupToolbar();

        // Extract destination from intent
        destinationItem = (LocationItem) getIntent().getSerializableExtra("location_item");
        destinationId = getIntent().getStringExtra("destination_id");

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
            } else {
                Toast.makeText(this, "No valid destination selected.", Toast.LENGTH_SHORT).show();
                finish();
                return;
            }
        }

        // Determine starting location
        startId = getIntent().getStringExtra("start_id");
        if (startId == null) {
            UserPosition currPos = positionManager.getCurrentPosition();
            if (currPos != null && currPos.hasAnchor()) {
                startId = currPos.getAnchorLocationId();
            } else {
                startId = "loc_gf_lab1"; // Default entrance location
            }
        }

        setupRecyclerView();
        setupListeners();
        calculateAndDisplayRoute();
    }

    private void initViews() {
        tvDestName = findViewById(R.id.tvNavDestinationName);
        tvDestCode = findViewById(R.id.tvNavDestinationCode);
        tvStartPoint = findViewById(R.id.tvNavStartPoint);
        tvFloorFlow = findViewById(R.id.tvNavFloorFlow);
        ivActiveStepIcon = findViewById(R.id.ivActiveStepIcon);
        tvActiveStepCounter = findViewById(R.id.tvActiveStepCounter);
        tvActiveStepInstruction = findViewById(R.id.tvActiveStepInstruction);
        btnPrevStep = findViewById(R.id.btnNavPrevStep);
        btnNextStep = findViewById(R.id.btnNavNextStep);
        btnLaunchVisualGuidance = findViewById(R.id.btnLaunchVisualGuidance);
        btnLaunchAR = findViewById(R.id.btnLaunchAR);
        btnRecalculate = findViewById(R.id.btnNavRecalculate);
        btnCancel = findViewById(R.id.btnNavCancel);
        btnChangeStart = findViewById(R.id.btnChangeStartLocation);
        rvSteps = findViewById(R.id.rvNavSteps);
    }

    private void setupToolbar() {
        MaterialToolbar toolbar = findViewById(R.id.toolbarNav);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setupRecyclerView() {
        rvSteps.setLayoutManager(new LinearLayoutManager(this));
        stepAdapter = new NavigationStepAdapter(this, new ArrayList<>(), new NavigationStepAdapter.OnStepClickListener() {
            @Override
            public void onStepClick(int position, NavigationStep step) {
                // Clicking on a step jumps to it
                while (navManager.getCurrentStepIndex() < position) {
                    navManager.nextStep();
                }
                while (navManager.getCurrentStepIndex() > position) {
                    navManager.previousStep();
                }
            }
        });
        rvSteps.setAdapter(stepAdapter);
    }

    private void setupListeners() {
        btnNextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (navManager.hasArrived()) {
                    Toast.makeText(NavigationActivity.this, "You have reached your destination!", Toast.LENGTH_SHORT).show();
                } else {
                    navManager.nextStep();
                }
            }
        });

        btnPrevStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navManager.previousStep();
            }
        });

        btnRecalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateAndDisplayRoute();
                Toast.makeText(NavigationActivity.this, "Route recalculated.", Toast.LENGTH_SHORT).show();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navManager.cancelNavigation();
                finish();
            }
        });

        btnChangeStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLocationPickerDialog();
            }
        });

        btnLaunchVisualGuidance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent visualIntent = new Intent(NavigationActivity.this, VisualNavigationActivity.class);
                visualIntent.putExtra("destination_id", destinationId);
                visualIntent.putExtra("start_id", startId);
                if (destinationItem != null) {
                    visualIntent.putExtra("location_item", destinationItem);
                }
                startActivity(visualIntent);
            }
        });

        btnLaunchAR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent arIntent = new Intent(NavigationActivity.this, ARNavigationActivity.class);
                arIntent.putExtra("destination_id", destinationId);
                arIntent.putExtra("start_id", startId);
                if (destinationItem != null) {
                    arIntent.putExtra("location_item", destinationItem);
                }
                startActivity(arIntent);
            }
        });
    }

    private void calculateAndDisplayRoute() {
        navManager.startNavigation(startId, destinationId);
    }

    private void showLocationPickerDialog() {
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_select_location, null);
        Spinner spFloor = dialogView.findViewById(R.id.spinnerFloorFilter);
        Spinner spLoc = dialogView.findViewById(R.id.spinnerLocationSelect);

        String[] floors = {"All Floors", "Ground Floor", "1st Floor", "2nd Floor"};
        ArrayAdapter<String> floorAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, floors);
        spFloor.setAdapter(floorAdapter);

        final List<LocationItem> currentLocList = new ArrayList<>();
        final ArrayAdapter<String> locAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, new ArrayList<>());
        spLoc.setAdapter(locAdapter);

        spFloor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedFloor = floors[position];
                List<LocationItem> filtered = locationRepo.getLocationsByFloor(selectedFloor);
                currentLocList.clear();
                currentLocList.addAll(filtered);

                List<String> names = new ArrayList<>();
                for (LocationItem item : filtered) {
                    names.add(item.getRoomNumber() + " - " + item.getName() + " (" + item.getFloor() + ")");
                }
                locAdapter.clear();
                locAdapter.addAll(names);
                locAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        new MaterialAlertDialogBuilder(this)
                .setView(dialogView)
                .setPositiveButton("Set Start Location", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int selectedIdx = spLoc.getSelectedItemPosition();
                        if (selectedIdx >= 0 && selectedIdx < currentLocList.size()) {
                            LocationItem selected = currentLocList.get(selectedIdx);
                            startId = selected.getId();
                            positionManager.setCurrentPosition(new UserPosition("bldg_comp_it", selected.getFloor(), selected.getId(), "ManualSelection"));
                            calculateAndDisplayRoute();
                            Toast.makeText(NavigationActivity.this, "Start point updated to " + selected.getName(), Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        navManager.addListener(this);
        if (navManager.isNavigating()) {
            onRouteCalculated(navManager.getCurrentRoute());
            NavigationStep curr = navManager.getCurrentStep();
            if (curr != null) {
                onStepChanged(navManager.getCurrentStepIndex(), curr);
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        navManager.removeListener(this);
    }

    // ==========================================
    // NavigationManager Callbacks
    // ==========================================

    @Override
    public void onRouteCalculated(NavigationRoute route) {
        if (route == null || !route.isRouteAvailable()) {
            tvActiveStepInstruction.setText(route != null ? route.getErrorMessage() : "Route unavailable.");
            tvFloorFlow.setText("Floor flow: Unavailable");
            return;
        }

        if (destinationItem != null) {
            tvDestName.setText(destinationItem.getName());
            tvDestCode.setText(destinationItem.getRoomNumber());
        }

        LocationItem startLoc = locationRepo.getLocationById(startId);
        String startName = startLoc != null ? startLoc.getName() : startId;
        tvStartPoint.setText("Start: " + startName + " (" + route.getStartFloor() + ")");

        // Build floor transition breadcrumb
        List<String> floors = route.getFloorsTraversed();
        if (floors != null && floors.size() > 1) {
            StringBuilder flow = new StringBuilder("Route Path: ");
            for (int i = 0; i < floors.size(); i++) {
                flow.append(floors.get(i).toUpperCase());
                if (i < floors.size() - 1) {
                    flow.append(" ➔ STAIRCASE ➔ ");
                }
            }
            tvFloorFlow.setText(flow.toString());
        } else {
            tvFloorFlow.setText("Same-Floor Route: " + route.getStartFloor());
        }

        stepAdapter.updateSteps(route.getSteps(), navManager.getCurrentStepIndex());
    }

    @Override
    public void onStepChanged(int stepIndex, NavigationStep step) {
        if (step == null) return;

        int total = navManager.getCurrentRoute() != null ? navManager.getCurrentRoute().getTotalSteps() : 0;
        tvActiveStepCounter.setText("Step " + (stepIndex + 1) + " of " + total + " • " + step.getFloor());
        tvActiveStepInstruction.setText(step.getInstruction());

        if (NavigationStep.STEP_STAIRCASE.equals(step.getStepType())) {
            ivActiveStepIcon.setImageResource(R.drawable.ic_stairs);
        } else if (NavigationStep.STEP_ARRIVE.equals(step.getStepType())) {
            ivActiveStepIcon.setImageResource(R.drawable.ic_check_circle);
            btnNextStep.setText("Destination Reached ✔");
        } else {
            ivActiveStepIcon.setImageResource(R.drawable.ic_navigation);
            btnNextStep.setText("Next Step →");
        }

        btnPrevStep.setEnabled(stepIndex > 0);
        stepAdapter.setActiveStepIndex(stepIndex);
        rvSteps.smoothScrollToPosition(stepIndex);
    }

    @Override
    public void onArrival(NavigationStep arrivalStep) {
        Toast.makeText(this, "You have arrived at " + (destinationItem != null ? destinationItem.getName() : "destination") + "!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNavigationCancelled() {
        finish();
    }
}
