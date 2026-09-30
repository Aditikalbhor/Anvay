package com.gpp.anvay.ui.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;
import com.gpp.anvay.R;
import com.gpp.anvay.data.AlertRepository;
import com.gpp.anvay.data.EmergencyRepository;
import com.gpp.anvay.data.LocationRepository;
import com.gpp.anvay.data.TimetableRepository;
import com.gpp.anvay.ui.TimetableActivity;

public class AdminDashboardActivity extends AppCompatActivity {

    private TextView tvTotalRooms;
    private TextView tvTotalAlerts;
    private TextView tvTotalSlots;
    private TextView tvTotalContacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        MaterialToolbar toolbar = findViewById(R.id.toolbarAdmin);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tvTotalRooms = findViewById(R.id.tvAdminTotalRooms);
        tvTotalAlerts = findViewById(R.id.tvAdminTotalAlerts);
        tvTotalSlots = findViewById(R.id.tvAdminTotalSlots);
        tvTotalContacts = findViewById(R.id.tvAdminTotalContacts);

        // Manage Rooms Card
        findViewById(R.id.cardAdminRooms).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminDashboardActivity.this, ManageRoomsActivity.class));
            }
        });

        // Manage Alerts Card
        findViewById(R.id.cardAdminAlerts).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminDashboardActivity.this, ManageAlertsActivity.class));
            }
        });

        // Manage Timetables & Schedules Card
        findViewById(R.id.cardAdminTimetable).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminDashboardActivity.this, TimetableActivity.class));
            }
        });

        // Manage Emergency Card
        findViewById(R.id.cardAdminEmergency).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminDashboardActivity.this, ManageEmergencyActivity.class));
            }
        });

        // Manage Routes Card
        findViewById(R.id.cardAdminRoutes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminDashboardActivity.this, ManageRoutesActivity.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateStats();
    }

    private void updateStats() {
        tvTotalRooms.setText(String.valueOf(LocationRepository.getInstance().getAllLocations().size()));
        tvTotalAlerts.setText(String.valueOf(AlertRepository.getInstance().getAllAlerts().size()));
        tvTotalSlots.setText(String.valueOf(TimetableRepository.getInstance(this).getAllSlots().size()));
        tvTotalContacts.setText(String.valueOf(EmergencyRepository.getInstance().getEmergencyContacts().size()));
    }
}
