package com.gpp.anvay.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.gpp.anvay.R;
import com.gpp.anvay.adapter.LocationAdapter;
import com.gpp.anvay.data.LocationRepository;
import com.gpp.anvay.model.BuildingItem;
import com.gpp.anvay.model.LocationItem;
import com.gpp.anvay.ui.LocationDetailActivity;

import java.util.List;

public class DirectoryFragment extends Fragment implements LocationAdapter.OnLocationClickListener {

    private RecyclerView rvLocations;
    private LocationAdapter adapter;
    private TextView tvDirectoryCount;
    private LinearLayout layoutEmpty;

    private ChipGroup chipGroupCategories;
    private ChipGroup chipGroupFloors;

    private String selectedBuildingId = LocationItem.DEFAULT_BUILDING_ID;
    private String selectedCategory = "All";
    private String selectedFloor = "All Floors";

    public DirectoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_directory, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvLocations = view.findViewById(R.id.rvDirectoryLocations);
        tvDirectoryCount = view.findViewById(R.id.tvDirectoryCount);
        layoutEmpty = view.findViewById(R.id.layoutDirEmpty);
        chipGroupCategories = view.findViewById(R.id.chipGroupDirCategories);
        chipGroupFloors = view.findViewById(R.id.chipGroupDirFloors);

        rvLocations.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new LocationAdapter(requireContext(), null, this);
        rvLocations.setAdapter(adapter);

        selectedBuildingId = LocationRepository.getInstance().getSelectedBuildingId();

        setupCategoryFilters();
        setupFloorChips();

        // Check if preselected category or building passed via bundle
        if (getArguments() != null) {
            if (getArguments().containsKey("selected_building_id")) {
                String preBldg = getArguments().getString("selected_building_id");
                if (preBldg != null) {
                    selectedBuildingId = preBldg;
                    LocationRepository.getInstance().setSelectedBuildingId(preBldg);
                    setupFloorChips();
                }
            }
            if (getArguments().containsKey("selected_category")) {
                String preCat = getArguments().getString("selected_category");
                if (preCat != null) {
                    selectedCategory = preCat;
                    selectCategoryChip(preCat);
                }
            }
        }

        applyFilters();
    }

    private void setupCategoryFilters() {
        chipGroupCategories.setOnCheckedStateChangeListener(new ChipGroup.OnCheckedStateChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull ChipGroup group, @NonNull List<Integer> checkedIds) {
                if (checkedIds.isEmpty()) return;
                int id = checkedIds.get(0);
                if (id == R.id.chipDirAll) selectedCategory = "All";
                else if (id == R.id.chipDirLabs) selectedCategory = "Laboratory";
                else if (id == R.id.chipDirClassrooms) selectedCategory = "Classrooms";
                else if (id == R.id.chipDirStaff) selectedCategory = "Staff Rooms";
                else if (id == R.id.chipDirHOD) selectedCategory = "HOD & Offices";
                else if (id == R.id.chipDirWashrooms) selectedCategory = "Washrooms";
                else if (id == R.id.chipDirServer) selectedCategory = "Server Rooms";
                else if (id == R.id.chipDirFacilities) selectedCategory = "Facilities";

                applyFilters();
            }
        });
    }

    private void setupFloorChips() {
        if (chipGroupFloors == null) return;
        chipGroupFloors.removeAllViews();

        BuildingItem currentBuilding = LocationRepository.getInstance().getBuildingById(selectedBuildingId);
        if (currentBuilding == null) {
            currentBuilding = LocationRepository.getInstance().getSelectedBuilding();
        }

        // Add 'All Floors' chip
        Chip allChip = new Chip(requireContext());
        allChip.setId(View.generateViewId());
        allChip.setText(getString(R.string.floor_all));
        allChip.setCheckable(true);
        chipGroupFloors.addView(allChip);

        if (currentBuilding != null && currentBuilding.getSupportedFloors() != null) {
            for (String floor : currentBuilding.getSupportedFloors()) {
                Chip floorChip = new Chip(requireContext());
                floorChip.setId(View.generateViewId());
                floorChip.setText(floor);
                floorChip.setCheckable(true);
                chipGroupFloors.addView(floorChip);
            }
        }

        // Select the active floor chip
        boolean matched = false;
        for (int i = 0; i < chipGroupFloors.getChildCount(); i++) {
            Chip c = (Chip) chipGroupFloors.getChildAt(i);
            if (c.getText().toString().equalsIgnoreCase(selectedFloor)) {
                chipGroupFloors.check(c.getId());
                matched = true;
                break;
            }
        }
        if (!matched && chipGroupFloors.getChildCount() > 0) {
            chipGroupFloors.check(allChip.getId());
            selectedFloor = "All Floors";
        }

        chipGroupFloors.setOnCheckedStateChangeListener(new ChipGroup.OnCheckedStateChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull ChipGroup group, @NonNull List<Integer> checkedIds) {
                if (checkedIds.isEmpty()) return;
                int id = checkedIds.get(0);
                Chip checkedChip = group.findViewById(id);
                if (checkedChip != null) {
                    selectedFloor = checkedChip.getText().toString();
                    applyFilters();
                }
            }
        });
    }

    private void selectCategoryChip(String category) {
        if (chipGroupCategories == null) return;
        if ("Laboratory".equalsIgnoreCase(category)) chipGroupCategories.check(R.id.chipDirLabs);
        else if ("Classrooms".equalsIgnoreCase(category)) chipGroupCategories.check(R.id.chipDirClassrooms);
        else if ("Staff Rooms".equalsIgnoreCase(category)) chipGroupCategories.check(R.id.chipDirStaff);
        else if ("HOD & Offices".equalsIgnoreCase(category)) chipGroupCategories.check(R.id.chipDirHOD);
        else if ("Washrooms".equalsIgnoreCase(category)) chipGroupCategories.check(R.id.chipDirWashrooms);
        else if ("Server Rooms".equalsIgnoreCase(category)) chipGroupCategories.check(R.id.chipDirServer);
        else if ("Facilities".equalsIgnoreCase(category)) chipGroupCategories.check(R.id.chipDirFacilities);
        else chipGroupCategories.check(R.id.chipDirAll);
    }

    public void setCategoryFilter(String category) {
        this.selectedCategory = category;
        selectCategoryChip(category);
        applyFilters();
    }

    public void setSelectedBuilding(String buildingId) {
        this.selectedBuildingId = buildingId;
        LocationRepository.getInstance().setSelectedBuildingId(buildingId);
        setupFloorChips();
        applyFilters();
    }

    private void applyFilters() {
        List<LocationItem> filtered = LocationRepository.getInstance().searchLocations(
                selectedBuildingId, "", selectedCategory, selectedFloor
        );
        adapter.updateList(filtered);

        BuildingItem building = LocationRepository.getInstance().getBuildingById(selectedBuildingId);
        String buildingName = building != null ? building.getName() : "Computer/IT Building";

        if (filtered.isEmpty()) {
            layoutEmpty.setVisibility(View.VISIBLE);
            rvLocations.setVisibility(View.GONE);
            tvDirectoryCount.setText(buildingName + " • No locations found");
        } else {
            layoutEmpty.setVisibility(View.GONE);
            rvLocations.setVisibility(View.VISIBLE);
            tvDirectoryCount.setText(buildingName + " • Showing " + filtered.size() + " locations");
        }
    }

    @Override
    public void onLocationClick(LocationItem location) {
        Intent intent = new Intent(requireContext(), LocationDetailActivity.class);
        intent.putExtra("location_item", location);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        selectedBuildingId = LocationRepository.getInstance().getSelectedBuildingId();
        setupFloorChips();
        applyFilters();
    }
}
