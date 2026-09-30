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

        setupFilters(view);

        // Check if preselected category passed via bundle
        if (getArguments() != null && getArguments().containsKey("selected_category")) {
            String preCat = getArguments().getString("selected_category");
            if (preCat != null) {
                selectedCategory = preCat;
                selectCategoryChip(preCat);
            }
        }

        applyFilters();
    }

    private void setupFilters(View root) {
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

        chipGroupFloors.setOnCheckedStateChangeListener(new ChipGroup.OnCheckedStateChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull ChipGroup group, @NonNull List<Integer> checkedIds) {
                if (checkedIds.isEmpty()) return;
                int id = checkedIds.get(0);
                if (id == R.id.chipFloorAll) selectedFloor = "All Floors";
                else if (id == R.id.chipFloorGround) selectedFloor = "Ground Floor";
                else if (id == R.id.chipFloor1) selectedFloor = "1st Floor";
                else if (id == R.id.chipFloor2) selectedFloor = "2nd Floor";
                else if (id == R.id.chipFloor3) selectedFloor = "3rd Floor";

                applyFilters();
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

    private void applyFilters() {
        List<LocationItem> filtered = LocationRepository.getInstance().searchLocations("", selectedCategory, selectedFloor);
        adapter.updateList(filtered);

        if (filtered.isEmpty()) {
            layoutEmpty.setVisibility(View.VISIBLE);
            rvLocations.setVisibility(View.GONE);
            tvDirectoryCount.setText("No locations found");
        } else {
            layoutEmpty.setVisibility(View.GONE);
            rvLocations.setVisibility(View.VISIBLE);
            tvDirectoryCount.setText("Showing " + filtered.size() + " locations");
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
        applyFilters();
    }
}
