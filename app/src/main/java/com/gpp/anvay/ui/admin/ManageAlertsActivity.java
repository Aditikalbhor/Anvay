package com.gpp.anvay.ui.admin;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.gpp.anvay.R;
import com.gpp.anvay.adapter.AlertAdapter;
import com.gpp.anvay.data.AlertRepository;
import com.gpp.anvay.model.AlertItem;

import java.util.UUID;

public class ManageAlertsActivity extends AppCompatActivity implements AlertAdapter.OnAlertClickListener {

    private RecyclerView rvAlerts;
    private AlertAdapter adapter;

    private final String[] priorities = {"Urgent", "Medium", "Normal"};
    private final String[] categories = {"Maintenance", "Relocation", "Emergency", "Notice"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_alerts);

        MaterialToolbar toolbar = findViewById(R.id.toolbarManageAlerts);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        rvAlerts = findViewById(R.id.rvAdminAlerts);
        ExtendedFloatingActionButton fabAdd = findViewById(R.id.fabAddAlert);

        rvAlerts.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AlertAdapter(this, AlertRepository.getInstance().getAllAlerts(), this);
        rvAlerts.setAdapter(adapter);

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddAlertDialog();
            }
        });
    }

    private void showAddAlertDialog() {
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_alert, null);

        TextInputEditText etTitle = dialogView.findViewById(R.id.etAlertTitle);
        TextInputEditText etLocation = dialogView.findViewById(R.id.etAlertLocation);
        TextInputEditText etMessage = dialogView.findViewById(R.id.etAlertMessage);
        Spinner spPriority = dialogView.findViewById(R.id.spinnerPriority);
        Spinner spCategory = dialogView.findViewById(R.id.spinnerAlertCategory);

        spPriority.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, priorities));
        spCategory.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, categories));

        new MaterialAlertDialogBuilder(this)
                .setView(dialogView)
                .setPositiveButton("Publish", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String title = etTitle.getText().toString().trim();
                        String location = etLocation.getText().toString().trim();
                        String message = etMessage.getText().toString().trim();
                        String priority = spPriority.getSelectedItem().toString();
                        String category = spCategory.getSelectedItem().toString();

                        if (title.isEmpty() || message.isEmpty()) {
                            Toast.makeText(ManageAlertsActivity.this, "Title and message are required", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        AlertItem newAlert = new AlertItem(
                                "alert_" + UUID.randomUUID().toString().substring(0, 8),
                                title,
                                message,
                                category,
                                priority,
                                "Just now",
                                location.isEmpty() ? "Comp/IT Building" : location,
                                true
                        );

                        AlertRepository.getInstance().addAlert(newAlert);
                        adapter.updateList(AlertRepository.getInstance().getAllAlerts());
                        Toast.makeText(ManageAlertsActivity.this, "Alert published successfully", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    @Override
    public void onAlertClick(final AlertItem alert) {
        new MaterialAlertDialogBuilder(this)
                .setTitle(alert.getTitle())
                .setMessage(alert.getMessage() + "\n\n" +
                        "Status: " + (alert.isActive() ? "Active" : "Archived") + "\n" +
                        "Category: " + alert.getCategory() + " • Priority: " + alert.getPriority())
                .setPositiveButton(alert.isActive() ? "Mark as Resolved" : "Re-activate", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AlertRepository.getInstance().toggleAlertStatus(alert.getId());
                        adapter.updateList(AlertRepository.getInstance().getAllAlerts());
                    }
                })
                .setNeutralButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AlertRepository.getInstance().deleteAlert(alert.getId());
                        adapter.updateList(AlertRepository.getInstance().getAllAlerts());
                        Toast.makeText(ManageAlertsActivity.this, "Alert deleted", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Close", null)
                .show();
    }
}
