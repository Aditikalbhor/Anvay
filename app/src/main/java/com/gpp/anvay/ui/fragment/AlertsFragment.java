package com.gpp.anvay.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.ChipGroup;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.gpp.anvay.R;
import com.gpp.anvay.adapter.AlertAdapter;
import com.gpp.anvay.data.AlertRepository;
import com.gpp.anvay.model.AlertItem;

import java.util.List;

public class AlertsFragment extends Fragment implements AlertAdapter.OnAlertClickListener {

    private RecyclerView rvAlerts;
    private AlertAdapter adapter;
    private LinearLayout layoutEmpty;
    private ChipGroup chipGroupFilters;
    private String selectedFilter = "All";

    public AlertsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_alerts, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvAlerts = view.findViewById(R.id.rvAlertsList);
        layoutEmpty = view.findViewById(R.id.layoutAlertsEmpty);
        chipGroupFilters = view.findViewById(R.id.chipGroupAlertFilters);

        rvAlerts.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new AlertAdapter(requireContext(), null, this);
        rvAlerts.setAdapter(adapter);

        setupFilterChips();
        loadAlerts();
    }

    private void setupFilterChips() {
        chipGroupFilters.setOnCheckedStateChangeListener(new ChipGroup.OnCheckedStateChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull ChipGroup group, @NonNull List<Integer> checkedIds) {
                if (checkedIds.isEmpty()) return;
                int id = checkedIds.get(0);
                if (id == R.id.chipAlertUrgent) selectedFilter = "Urgent";
                else if (id == R.id.chipAlertMaintenance) selectedFilter = "Maintenance";
                else if (id == R.id.chipAlertNotices) selectedFilter = "Notice";
                else selectedFilter = "All";

                loadAlerts();
            }
        });
    }

    private void loadAlerts() {
        List<AlertItem> list = AlertRepository.getInstance().getAlertsByCategory(selectedFilter);
        adapter.updateList(list);

        if (list.isEmpty()) {
            layoutEmpty.setVisibility(View.VISIBLE);
            rvAlerts.setVisibility(View.GONE);
        } else {
            layoutEmpty.setVisibility(View.GONE);
            rvAlerts.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onAlertClick(AlertItem alert) {
        new MaterialAlertDialogBuilder(requireContext())
                .setTitle(alert.getTitle())
                .setMessage(alert.getMessage() + "\n\n" +
                        "Affected Area: " + (alert.getLocationAffected() != null ? alert.getLocationAffected() : "N/A") + "\n" +
                        "Priority: " + alert.getPriority() + " • Posted: " + alert.getTimestamp())
                .setPositiveButton("Close", null)
                .show();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadAlerts();
    }
}
