package com.gpp.anvay.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.gpp.anvay.R;
import com.gpp.anvay.adapter.EmergencyContactAdapter;
import com.gpp.anvay.adapter.SafetyInstructionAdapter;
import com.gpp.anvay.data.EmergencyRepository;

public class EmergencyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency);

        setupToolbar();
        setupContacts();
        setupProtocols();

        MaterialButton btnStartGuidance = findViewById(R.id.btnStartEmergencyGuidance);
        btnStartGuidance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEmergencyGuidanceOverview();
            }
        });
    }

    private void setupToolbar() {
        MaterialToolbar toolbar = findViewById(R.id.toolbarEmergency);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setupContacts() {
        RecyclerView rvContacts = findViewById(R.id.rvEmergencyContacts);
        rvContacts.setLayoutManager(new LinearLayoutManager(this));
        EmergencyContactAdapter adapter = new EmergencyContactAdapter(this, EmergencyRepository.getInstance().getEmergencyContacts());
        rvContacts.setAdapter(adapter);
    }

    private void setupProtocols() {
        RecyclerView rvProtocols = findViewById(R.id.rvSafetyProtocols);
        rvProtocols.setLayoutManager(new LinearLayoutManager(this));
        SafetyInstructionAdapter adapter = new SafetyInstructionAdapter(this, EmergencyRepository.getInstance().getSafetyInstructions());
        rvProtocols.setAdapter(adapter);
    }

    private void showEmergencyGuidanceOverview() {
        new MaterialAlertDialogBuilder(this)
                .setTitle("🚨 Emergency & Evacuation Guidance")
                .setMessage("1. Stay calm and follow all building safety instructions.\n" +
                        "2. Move towards the nearest verified vertical staircase (Staircase S1, S2, or S3).\n" +
                        "3. Descend carefully to the Ground Floor.\n" +
                        "4. Follow standard building corridors to exit to open grounds.\n" +
                        "5. Contact emergency services or campus security if assistance is required.\n\n" +
                        "Note: ANVAY Computer/IT Building routes cover Ground, 1st, and 2nd floors connected via Staircases S1, S2, and S3.")
                .setIcon(R.drawable.ic_emergency)
                .setPositiveButton("I Am Safe / Acknowledged", null)
                .show();
    }
}
