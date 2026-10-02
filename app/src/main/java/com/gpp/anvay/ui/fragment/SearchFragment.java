package com.gpp.anvay.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.gpp.anvay.data.PreferenceManager;
import com.gpp.anvay.model.LocationItem;
import com.gpp.anvay.ui.LocationDetailActivity;

import java.util.List;

public class SearchFragment extends Fragment implements LocationAdapter.OnLocationClickListener {

    private EditText etSearch;
    private ImageView ivClear;
    private RecyclerView rvResults;
    private LocationAdapter adapter;
    private LinearLayout layoutEmpty;
    private LinearLayout layoutRecentContainer;
    private ChipGroup chipGroupRecent;
    private ChipGroup chipGroupFilters;
    private PreferenceManager preferenceManager;

    private String activeBuildingFilter = null; // null/All allows campus-wide search across all buildings
    private String activeCategoryFilter = "All";
    private String activeFloorFilter = "All Floors";

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        preferenceManager = new PreferenceManager(requireContext());

        etSearch = view.findViewById(R.id.etSearchQuery);
        ivClear = view.findViewById(R.id.ivClearSearch);
        rvResults = view.findViewById(R.id.rvSearchResults);
        layoutEmpty = view.findViewById(R.id.layoutSearchEmpty);
        layoutRecentContainer = view.findViewById(R.id.layoutRecentSearchesContainer);
        chipGroupRecent = view.findViewById(R.id.chipGroupSearchRecent);
        chipGroupFilters = view.findViewById(R.id.chipGroupSearchFilters);

        rvResults.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new LocationAdapter(requireContext(), null, this);
        rvResults.setAdapter(adapter);

        setupSearchInput();
        setupFilterChips();
        loadRecentSearches();

        view.findViewById(R.id.tvClearSearchHistory).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preferenceManager.clearRecentSearches();
                loadRecentSearches();
            }
        });

        // Check if pre-filled search query or building passed via arguments
        if (getArguments() != null) {
            if (getArguments().containsKey("search_building_id")) {
                activeBuildingFilter = getArguments().getString("search_building_id");
            }
            if (getArguments().containsKey("search_query")) {
                String q = getArguments().getString("search_query");
                if (q != null) {
                    etSearch.setText(q);
                    etSearch.setSelection(q.length());
                }
            }
        }

        performSearch(etSearch.getText().toString());
    }

    private void setupSearchInput() {
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String query = s.toString();
                ivClear.setVisibility(query.isEmpty() ? View.GONE : View.VISIBLE);
                performSearch(query);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        ivClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etSearch.setText("");
                performSearch("");
            }
        });

        etSearch.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                String q = etSearch.getText().toString().trim();
                if (!q.isEmpty()) {
                    preferenceManager.addRecentSearch(q);
                    loadRecentSearches();
                }
                return true;
            }
            return false;
        });
    }

    private void setupFilterChips() {
        chipGroupFilters.setOnCheckedStateChangeListener(new ChipGroup.OnCheckedStateChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull ChipGroup group, @NonNull List<Integer> checkedIds) {
                activeCategoryFilter = "All";
                activeFloorFilter = "All Floors";

                if (!checkedIds.isEmpty()) {
                    int id = checkedIds.get(0);
                    if (id == R.id.chipFilterLabs) activeCategoryFilter = "Laboratory";
                    else if (id == R.id.chipFilterGround) activeFloorFilter = "Ground Floor";
                    else if (id == R.id.chipFilterFirst) activeFloorFilter = "1st Floor";
                    else if (id == R.id.chipFilterSecond) activeFloorFilter = "2nd Floor";
                }
                performSearch(etSearch.getText().toString());
            }
        });
    }

    private void loadRecentSearches() {
        if (chipGroupRecent == null) return;
        chipGroupRecent.removeAllViews();
        List<String> list = preferenceManager.getRecentSearches();

        if (list.isEmpty()) {
            layoutRecentContainer.setVisibility(View.GONE);
            return;
        }

        layoutRecentContainer.setVisibility(View.VISIBLE);
        for (final String item : list) {
            Chip chip = new Chip(requireContext());
            chip.setText(item);
            chip.setCheckable(false);
            chip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    etSearch.setText(item);
                    etSearch.setSelection(item.length());
                    performSearch(item);
                }
            });
            chipGroupRecent.addView(chip);
        }
    }

    public void setSearchQuery(String query) {
        if (etSearch != null && query != null) {
            etSearch.setText(query);
            etSearch.setSelection(query.length());
            performSearch(query);
        }
    }

    public void setActiveBuildingFilter(String buildingId) {
        this.activeBuildingFilter = buildingId;
        if (etSearch != null) {
            performSearch(etSearch.getText().toString());
        }
    }

    public String getActiveBuildingFilter() {
        return activeBuildingFilter;
    }

    private void performSearch(String query) {
        List<LocationItem> results = LocationRepository.getInstance().searchLocations(
                activeBuildingFilter, query, activeCategoryFilter, activeFloorFilter
        );
        adapter.updateList(results);

        if (results.isEmpty()) {
            layoutEmpty.setVisibility(View.VISIBLE);
            rvResults.setVisibility(View.GONE);
        } else {
            layoutEmpty.setVisibility(View.GONE);
            rvResults.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onLocationClick(LocationItem location) {
        if (location != null) {
            preferenceManager.addRecentSearch(location.getName());
            loadRecentSearches();

            Intent intent = new Intent(requireContext(), LocationDetailActivity.class);
            intent.putExtra("location_item", location);
            startActivity(intent);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        loadRecentSearches();
        performSearch(etSearch.getText().toString());
    }
}
