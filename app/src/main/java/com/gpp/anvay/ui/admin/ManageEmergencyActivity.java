package com.gpp.anvay.ui.admin;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.gpp.anvay.R;
import com.gpp.anvay.adapter.EmergencyContactAdapter;
import com.gpp.anvay.data.EmergencyRepository;

public class ManageEmergencyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_emergency);

        MaterialToolbar toolbar = findViewById(R.id.toolbarManageEmergency);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        RecyclerView rvContacts = findViewById(R.id.rvAdminEmergencyContacts);
        rvContacts.setLayoutManager(new LinearLayoutManager(this));
        EmergencyContactAdapter adapter = new EmergencyContactAdapter(this, EmergencyRepository.getInstance().getEmergencyContacts());
        rvContacts.setAdapter(adapter);

        MaterialButton btnBroadcast = findViewById(R.id.btnSimulateBroadcast);
        btnBroadcast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialAlertDialogBuilder(ManageEmergencyActivity.this)
                        .setTitle("📢 Emergency Broadcast Broadcasted")
                        .setMessage("Simulation alert sent to all connected campus app clients:\n\n" +
                                "\"EMERGENCY: Immediate building evacuation test in progress. Please move towards nearest staircases S1, S2, or S3.\"")
                        .setPositiveButton("OK", null)
                        .show();
            }
        });
    }
}
